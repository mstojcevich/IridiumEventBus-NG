package pw.oxcafebabe.marcusant.eventbus.managers.iridium;

import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.EventListener;
import pw.oxcafebabe.marcusant.eventbus.ListenerFilter;
import pw.oxcafebabe.marcusant.eventbus.Subscriber;
import pw.oxcafebabe.marcusant.eventbus.exceptions.EventException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Bridges reflective method calls to the {@link pw.oxcafebabe.marcusant.eventbus.Subscriber} interface
 * Date: 8/3/14
 * Time: 3:52 PM
 * @author Kazukuta
 */
public final class MethodAsSubscriber<ET extends Event> implements Subscriber<ET> {

    public static <X extends Event> MethodAsSubscriber<X> createFromMethod(Class<X> eventType, Object methodParent, Method method) throws EventException {
        EventListener listenerAnnotation = method.getAnnotation(EventListener.class);

        // Collect filter instances
        List<ListenerFilter<X>> filters = new ArrayList<>();

        for(Class<? extends ListenerFilter> filterClass : listenerAnnotation.filters())
            try {
                filters.add( (ListenerFilter<X>) filterClass.newInstance() );
            } catch (InstantiationException e) {
                throw new EventException(e);
            } catch (IllegalAccessException e) {
                throw new EventException("There is not a visible constructor for filter " + filterClass.getName());
            }

        // Reflective method types are untyped. no matter.
        return  new MethodAsSubscriber<>(methodParent, method, listenerAnnotation.priority().getPriorityValue(), filters);
    }

    private final int priority;
    private final Object methodParent;
    private final Method bridgedMethod;
    private final List<ListenerFilter<ET>> filters;

    /**
     * Default constructor
     * @param methodParent  instance of class to which the method belongs
     * @param bridgedMethod method that will be called when an event is received
     * @param priority      priority of the subscriber
     */
    public MethodAsSubscriber(Object methodParent, Method bridgedMethod, int priority,
                              List<ListenerFilter<ET>> filters) {
        this.priority       = priority;
        this.methodParent   = methodParent;
        this.bridgedMethod  = bridgedMethod;
        this.filters        = filters;
    }

    @Override
    public void eventReceived(ET event) {
        try {
            bridgedMethod.invoke(methodParent, event);
        } catch (IllegalAccessException e) {
            // There is no reason an error occuring during subscriber invocation should take down the entire bus
            throw new RuntimeException("Illegal access exception caught during bridged method invocation in Method Subscriber", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("An exception was thrown during invocation of bridge method", e);
        }
    }

    @Override
    public int getPriorityValue() {
        return priority;
    }

    @Override
    public List<ListenerFilter<ET>> getFilters() {
        return filters;
    }
}
