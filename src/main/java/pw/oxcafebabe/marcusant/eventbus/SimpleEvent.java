package pw.oxcafebabe.marcusant.eventbus;

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
            cancel();
        } else {
            // TODO throw event exception
        }
    }

    @Override
    public void cancel() {
        eventCancelled = true;
    }
}