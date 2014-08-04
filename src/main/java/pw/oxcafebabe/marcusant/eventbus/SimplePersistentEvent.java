package pw.oxcafebabe.marcusant.eventbus;

import pw.oxcafebabe.marcusant.eventbus.exceptions.EventCancellationException;

/**
 * Date: 8/3/14
 * Time: 3:29 PM
 * @author Kazukuta
 */
public class SimplePersistentEvent extends SimpleEvent {

    public final void cancel() throws EventCancellationException {
        throw new EventCancellationException("Event cannot be cancelled");
    }

}
