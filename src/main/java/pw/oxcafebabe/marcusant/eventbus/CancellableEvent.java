package pw.oxcafebabe.marcusant.eventbus;

public interface CancellableEvent extends Event {
	
	/**
	 * Gets whether the event was cancelled
	 * @return Whether the event was cancelled
	 */
	public boolean isCancelled();

	/**
	 * Sets whether the event was cancelled
	 * @param cancelled Whether the event was cancelled
	 */
	public void setCancelled(boolean cancelled);
	
}
