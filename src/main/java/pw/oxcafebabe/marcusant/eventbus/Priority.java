package pw.oxcafebabe.marcusant.eventbus;

/**
 * The priority used to process the firing of events. 
 * 
 * Event listeners marked with a higher priority are processed first.
 *
 * @author Marcus
 */
public enum Priority {
	/**
	 * Called before any other event listening method is called.
	 * Usually used to cancel an event. Use only if you have to.
	 */
	FIRST,
	/**
	 * Called after the FIRST prioritized methods.
	 * Usually used to change event information before the event is handled by other listeners.
	 */
	HIGH,
	/**
	 * The default priority.
	 */
	NORMAL,
	/**
	 * Called after normal events and higher. 
	 * Usually used to get the last say on what happens.
	 */
	LOW,
	/**
	 * Called after every other listening method.
	 * Usually used to monitor changes made by listeners. Do not make any changes to the event or related objects at this stage unless you have to.
	 */
	LAST
}
