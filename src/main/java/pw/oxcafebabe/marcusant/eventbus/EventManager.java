package pw.oxcafebabe.marcusant.eventbus;

import pw.oxcafebabe.marcusant.eventbus.exceptions.EventException;
import pw.oxcafebabe.marcusant.eventbus.exceptions.InvalidListenerException;

/**
 * Manages the sending of events to registered listeners
 * @author Marcus
 */
public interface EventManager {

    /**
     * Register a subscriber
     *
     * @param eventType     event type
     * @param subscriber    subscriber
     * @param <ET>          event generic
     */
    <ET extends Event> void registerSubscriber(Class<ET> eventType, Subscriber<ET> subscriber);

    /**
     * Unregister a subscriber
     *
     * @param eventType     event type
     * @param subscriber    subscriber
     */
    void unregisterSubscriber(Class<? extends Event> eventType, Subscriber<?> subscriber);

    /**
	 * Registers an object as a listener for events.
	 * @param listeningObject Object to fire events to
	 * @throws InvalidListenerException A method in the listening object's class is marked as a listener but is invalid.
     * @deprecated in favour of {@link #registerObject(Object)}
	 */
    @Deprecated
    void register(Object listeningObject) throws EventException;

    /**
     * Registers an object as a listener for events.
     * @param listeningObject Object to fire events to
     * @throws InvalidListenerException A method in the listening object's class is marked as a listener but is invalid.
     */
    void registerObject(Object listeningObject) throws EventException;

    /**
     * @deprecated in favour of {@link #unregisterObject(Object)}
     */
    @Deprecated
    boolean unregister(Object listeningObject);

	/**
	 * Unregisters an object from listening for events.
	 * @param listeningObject Object to unregister
	 * @return Whether the object existed and was successfully removed
	 */
    boolean unregisterObject(Object listeningObject);
	
	/**
	 * Publishes an event to all registered listening methods.
	 * @param event Event to publish
	 */
    <ET extends Event> void push(ET event);

}
