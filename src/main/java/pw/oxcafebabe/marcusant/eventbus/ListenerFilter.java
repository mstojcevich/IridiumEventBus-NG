package pw.oxcafebabe.marcusant.eventbus;

import java.lang.reflect.Method;

/**
 * Filters events by checking whether an invocation of a method with a specific event is valid
 * @author marcusant
 *
 * @param <T> Event to filter
 */
public interface ListenerFilter<T extends Event> {
	
	/**
	 * Checks whether an event invocation is valid and follows the filter
	 * @param event Event to be sent
	 * @param method Method to be invoked with the event
	 * @return Whether the event should be sent
	 */
	public boolean shouldSend(T event, Method method);

}
