package pw.oxcafebabe.marcusant.eventbus;

import java.util.Comparator;
import java.util.List;

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
    void eventReceived(ET event);

    /**
     * Returns a value describing the ordering that the event would prefer
     *
     * @return priority
     */
    int getPriorityValue();

    /**
     * Get a list of filters for the subscriber
     *
     * @return filters
     */
    List<ListenerFilter<ET>> getFilters();

    /**
     * Compares two subscribers by priority
     */
    class SubscriberComparator implements Comparator<Subscriber> {
        @Override
        public int compare(Subscriber o1, Subscriber o2) {
            return o2.getPriorityValue() - o1.getPriorityValue();
        }
    }
}
