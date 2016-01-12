package pw.oxcafebabe.marcusant.eventbus.managers.iridium;

import pw.oxcafebabe.marcusant.eventbus.*;
import pw.oxcafebabe.marcusant.eventbus.exceptions.EventException;
import pw.oxcafebabe.marcusant.eventbus.exceptions.InvalidListenerException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An event manager targeted toward rapidly managing large amount of event pushes at the cost of slightly slower registering of listeners.
 *
 * @author Marcus
 */
public class IridiumEventManager implements EventManager {

    /**
     * Internal logic for verification of method signatures that will receive events
     *
     * @param methodParent class containing the method
     * @param method       method
     * @throws InvalidListenerException if the method signature is invalid
     */
    private static void validateMethodSignature(Class methodParent, Method method) throws InvalidListenerException {
        // Firstly, the method should be unary
        if (method.getParameterCount() != 1) throw new InvalidListenerException(methodParent, method);

        // Check assignable
        if (!Event.class.isAssignableFrom(method.getParameterTypes()[0]))
            throw new InvalidListenerException(methodParent, method);
    }

    /**
     * Map that maps events to an ArrayList of all methods listening for it, in order by priority.
     */
    private final Map<Class<? extends Event>, List<Subscriber<?>>> registeredSubscribers = new HashMap<>();

    @Override
    public synchronized <ET extends Event> void registerSubscriber(Class<ET> eventType, Subscriber<ET> subscriber) {
        if (!registeredSubscribers.containsKey(eventType)) {
            registeredSubscribers.put(eventType, new ArrayList<>());
        }

        List<Subscriber<?>> subsList = registeredSubscribers.get(eventType);
        if (!subsList.contains(subscriber)) {
            subsList.add(subscriber);
        }
        subsList.sort(new Subscriber.SubscriberComparator());
    }

    @Override
    public synchronized void unregisterSubscriber(Class<? extends Event> eventType, Subscriber<?> subscriber) {
        if (!registeredSubscribers.containsKey(eventType)) {
            registeredSubscribers.get(eventType).remove(subscriber);
        }
    }

    @Override
    public final void register(Object listeningObject) throws EventException {
        registerObject(listeningObject);
    }

    /**
     * Register all methods in one class that are annotated with {@link pw.oxcafebabe.marcusant.eventbus.EventListener}
     *
     * @param listeningObject Object to fire events to
     * @throws InvalidListenerException if the listener annotation is incomplete or contains illegal values
     */
    @Override
    public void registerObject(Object listeningObject) throws EventException {
        Map<Class<? extends Event>, List<Subscriber<?>>> eventsDelta = new HashMap<>();

        for (Method clazzMethod : listeningObject.getClass().getDeclaredMethods()) {
            if (clazzMethod.isAnnotationPresent(EventListener.class)) {
                EventListener listenerAnnotation = clazzMethod.getAnnotation(EventListener.class);

                // After this line, it is OK to cast parameter 0 of clazzMethod to Event
                validateMethodSignature(listeningObject.getClass(), clazzMethod);

                // This cast is implicitly safe, because the method would have excepted during signature validation
                // noinspection unchecked
                Class<Event> eventType = (Class<Event>) clazzMethod.getParameterTypes()[0];

                // Use the MethodAsSubscriber factory to create a subscriber
                Subscriber<?> subscriber = MethodAsSubscriber.createFromMethod(eventType, listeningObject, clazzMethod);

                if (!eventsDelta.containsKey(eventType)) {
                    eventsDelta.put(eventType, new ArrayList<>());
                }

                eventsDelta.get(eventType).add(subscriber);
            }
        }

        // Merge registeredSubscribers and sort subscribers
        synchronized (registeredSubscribers) {
            for (Map.Entry<Class<? extends Event>, List<Subscriber<?>>> eventEntry : eventsDelta.entrySet()) {
                Class<? extends Event> eventType = eventEntry.getKey();
                List<Subscriber<?>> subscribers = eventEntry.getValue();
                if (registeredSubscribers.containsKey(eventType)) {
                    registeredSubscribers.get(eventType).addAll(subscribers);
                } else {
                    registeredSubscribers.put(eventType, new ArrayList<>(subscribers));
                }

                registeredSubscribers.get(eventType).sort(new Subscriber.SubscriberComparator());
            }
        }
    }

    @Override
    public final boolean unregister(Object listeningObject) {
        return unregisterObject(listeningObject);
    }

    @Override
    public boolean unregisterObject(Object listeningObject) {
        /*
         * It is possible to implement this with the Subscriber API based system.
         * The reason for my not doing so is that they would create a bit of bloat,
         * and there is no reason to detach an entire class at runtime given the intended use for this library.
         */
        throw new UnsupportedOperationException("Runtime detach is not supported for classes");
    }


    @Override
    public <ET extends Event> void push(ET event) {
        List<Subscriber<ET>> subscribers;

        synchronized (registeredSubscribers) {
            if (!registeredSubscribers.containsKey(event.getClass())) return;

            // Inspection disabled because all inserts in to registeredSubscribers are type insured
            // due to type erasure, we can not have per-pair types.
            // noinspection unchecked
            subscribers = (List<Subscriber<ET>>) new ArrayList(registeredSubscribers.get(event.getClass()));
        }

        for (Subscriber<ET> subscriber : subscribers) {
            boolean txDeterminate = true;
            for (ListenerFilter filter : subscriber.getFilters()) {
                // This can only occur if someone fucks something up in an annotation, as listeners should be typesafe when passed to subscribers
                // noinspection unchecked
                txDeterminate = txDeterminate && filter.shouldSend(event);
            }

            if (txDeterminate) subscriber.eventReceived(event);
        }
    }

}
