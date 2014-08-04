package pw.oxcafebabe.marcusant.eventbus.exceptions;

/**
 * Date: 8/3/14
 * Time: 3:30 PM
 */
public class EventException extends Exception {

    public EventException() {}

    public EventException(String message){ super(message); }

    public EventException(Throwable cause) { super(cause); }

}
