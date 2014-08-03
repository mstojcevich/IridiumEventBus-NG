package pw.oxcafebabe.marcusant.eventbus.filters;

import java.lang.reflect.Method;

import pw.oxcafebabe.marcusant.eventbus.CancellableEvent;
import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.ListenerFilter;

/**
 * Checks if a cancellable event should be cancelled
 * @author marcusant
 */
public class Cancellable implements ListenerFilter<Event> {

	@Override
	public boolean shouldSend(Event event, Method method) {
		return !event.isCancelled();
	}

}
