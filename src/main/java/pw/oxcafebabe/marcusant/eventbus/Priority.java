package pw.oxcafebabe.marcusant.eventbus;

/**
 * The priority used to process the firing of events. 
 * 
 * Event listeners marked with a higher priority are processed first.
 *
 * @author Marcus
 *
 * @deprecated because it may be a better idea to use integer values to weigh priority.
 */
@Deprecated
public enum Priority {
	/**
	 * Called before any other event listening method is called.
	 * Usually used to cancel an event. Use only if you have to.
	 */
	FIRST(2),
	/**
	 * Called after the FIRST prioritized methods.
	 * Usually used to change event information before the event is handled by other listeners.
	 */
	HIGH(1),
	/**
	 * The default priority.
	 */
	NORMAL(0),
	/**
	 * Called after normal events and higher. 
	 * Usually used to get the last say on what happens.
	 */
	LOW(-1),
	/**
	 * Called after every other listening method.
	 * Usually used to monitor changes made by listeners. Do not make any changes to the event or related objects at this stage unless you have to.
	 */
	LAST(-2);

    private final int priorityValue;

    Priority(int priorityValue) {
        this.priorityValue = priorityValue;
    }

    /**
     * Get the integer value denoting the priority number assigned to the enumeration
     * @return priority value
     */
    public int getPriorityValue(){ return priorityValue; }
}
