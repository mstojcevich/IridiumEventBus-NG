package pw.oxcafebabe.marcusant.eventbus.filters;

import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.ListenerFilter;

/**
 * Checks if a cancellable event should be cancelled
 * @author marcusant
 */
public class Cancellable implements ListenerFilter<Event> {

	@Override
	public boolean shouldSend(Event event) {
		return !event.isCancelled();
	}

}
