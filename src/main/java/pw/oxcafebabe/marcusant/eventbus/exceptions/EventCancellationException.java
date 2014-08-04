package pw.oxcafebabe.marcusant.eventbus.exceptions;

/**
 * Date: 8/3/14
 * Time: 3:33 PM
 * @author Kazukuta
 */
public class EventCancellationException extends EventException {

    private final String errorMessage;

    public EventCancellationException(String message) {
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
