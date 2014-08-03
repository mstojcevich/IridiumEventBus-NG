package pw.oxcafebabe.marcusant.eventbus;

import pw.oxcafebabe.marcusant.eventbus.exceptions.EventCancellationException;

/**
 * Pre-implemented event that may be used when implementing an interface is not required
 * @author Kazukuta
 */
public class SimpleEvent implements Event {

    private boolean eventCancelled = false;

    @Override
    public boolean isCancelled() {
        return eventCancelled;
    }

    @Override
    public final void setCancelled(boolean cancelled) {
        if(cancelled) {

            // Design decisions, eh?
            // Let's catch this and bridge it with an UnsupportedOperationException
            // this way the compiler will skip the check to see that #setCancelled is explicitly capable of throwing
            // EventCancellationException
            try {
                cancel();
            } catch (EventCancellationException cancellation) {
                throw new UnsupportedOperationException(cancellation);
            }

        }

        // In order that we may not break APIs, we will ignore an attempt to set the event as not cancelled
        // instead of throwing an exception
    }

    @Override
    public void cancel() throws EventCancellationException {
        eventCancelled = true;
    }
}