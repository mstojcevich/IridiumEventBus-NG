package pw.oxcafebabe.marcusant.eventbus;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 8/3/14
 * Time: 7:12 PM
 * @author Kazukuta
 */
public abstract class SimpleSubscriber<ET extends Event> implements Subscriber<ET> {

    private final int priority;
    private final List<ListenerFilter<ET>> listenerFilters;

    public SimpleSubscriber(int priority, List<ListenerFilter<ET>> listenerFilters) {
        this.priority = priority;
        this.listenerFilters = listenerFilters;
    }

    public SimpleSubscriber(int priority) {
        this(priority, new ArrayList<>());
    }

    public SimpleSubscriber() {
        this(0);
    }


    @Override
    public final int getPriorityValue() {
        return priority;
    }

    @Override
    public final List<ListenerFilter<ET>> getFilters() {
        return listenerFilters;
    }

}
