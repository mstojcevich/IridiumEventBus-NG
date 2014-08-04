package pw.oxcafebabe.marcusant.eventbus;

import pw.oxcafebabe.marcusant.eventbus.exceptions.EventCancellationException;

/**
 * Object that can be sent to all listening methods.
 * @author Marcus
 */
public interface Event {

    /**
     * Gets whether the event was cancelled
     * @return Whether the event was cancelled
     */
    public boolean isCancelled();

    /**
     * Sets whether the event was cancelled
     * @param cancelled Whether the event was cancelled
     * @deprecated in favour of {@link #cancel()}
     */
    @Deprecated
    public void setCancelled(boolean cancelled);

    /**
     * Cancel the event
     */
    public void cancel() throws EventCancellationException;

}
