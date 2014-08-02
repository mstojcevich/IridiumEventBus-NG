package pw.oxcafebabe.marcusant.eventbus;

/**
 * Simple implementation of a cancellable event
 * @author marcusant
 */
public class SimpleCancellableEvent implements CancellableEvent {
	
	private boolean cancelled;

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
