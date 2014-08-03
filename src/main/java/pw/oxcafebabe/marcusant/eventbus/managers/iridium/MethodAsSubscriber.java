package pw.oxcafebabe.marcusant.eventbus.managers.iridium;

import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.Subscriber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Bridges reflective method calls to the {@link pw.oxcafebabe.marcusant.eventbus.Subscriber} interface
 * Date: 8/3/14
 * Time: 3:52 PM
 * @author Kazukuta
 */
public final class MethodAsSubscriber<ET extends Event> implements Subscriber<ET> {

    private final int priority;
    private final Object methodParent;
    private final Method bridgedMethod;

    /**
     * Default constructor
     * @param methodParent  instance of class to which the method belongs
     * @param bridgedMethod method that will be called when an event is received
     * @param priority      priority of the subscriber
     */
    public MethodAsSubscriber(Object methodParent, Method bridgedMethod, int priority){
        this.priority       = priority;
        this.methodParent   = methodParent;
        this.bridgedMethod  = bridgedMethod;
    }

    @Override
    public void eventReceived(ET event) {
        try {
            bridgedMethod.invoke(methodParent, event);
        } catch (IllegalAccessException e) {
            // There is no reason an error occuring during subscriber invocation should take down the entire bus
            // TODO SLFJ
        } catch (InvocationTargetException e) {
            // TODO SLFJ
        }
    }

    @Override
    public int priorityValue() {
        return priority;
    }
}
