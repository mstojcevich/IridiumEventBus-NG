package pw.oxcafebabe.marcusant.eventbus;

import java.util.Comparator;

/**
 * Receives events
 * Date: 8/3/14
 * Time: 3:44 PM
 * @author Kazukuta
 */
public interface Subscriber<ET extends Event> {

    /**
     * Called upon receipt of an event
     * @param event event
     */
    public void eventReceived(ET event);

    /**
     * Returns a value describing the ordering that the event would prefer
     *
     * @return priority
     */
    public int priorityValue();

    /**
     * Compares two subscribers by priority
     */
    public static class SubscriberComparator implements Comparator<Subscriber> {
        @Override
        public int compare(Subscriber o1, Subscriber o2) {
            return o2.priorityValue() - o1.priorityValue();
        }
    }

}
