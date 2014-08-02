package pw.oxcafebabe.marcusant.eventbus.filters;

import java.lang.reflect.Method;

import pw.oxcafebabe.marcusant.eventbus.CancellableEvent;
import pw.oxcafebabe.marcusant.eventbus.ListenerFilter;

/**
 * Checks if a cancellable event should be cancelled
 * @author marcusant
 */
public class Cancellable implements ListenerFilter<CancellableEvent> {

	@Override
	public boolean shouldSend(CancellableEvent event, Method method) {
		if(event.isCancelled()) {
			return false;
		} else {
			return true;
		}
	}

}
