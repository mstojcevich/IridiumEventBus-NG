package pw.oxcafebabe.marcusant.eventbus.managers.iridium;

import java.lang.reflect.Method;

import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.ListenerFilter;
import pw.oxcafebabe.marcusant.eventbus.Priority;

class ListeningMethod {
	
	private final Object owner;
	private final Method method;
	private final Priority priority;
	private final Class<? extends Event> event; //TODO since this is stored in a hashmap with the listenermethod in the IridiumEvent implementation, this is unused and should possibly be removed
	private final ListenerFilter[] filters;
	/**
	 * Whether there are filters to check before firing an event
	 */
	public final boolean hasFilters; //Because this is accessed every event fire, this is public since invoking a getter method adds additional overhead
	
	/**
	 * Creates a new ListeningMethod
	 * @param owner Object who's class contains the listening method
	 * @param method Method that is listening
	 * @param priority Priority of the listening method
	 * @param filters Filters to check before firing events to the method
	 */
	public ListeningMethod(Object owner, Method method, Priority priority, Class<? extends Event> event, ListenerFilter[] filters) {
		this.owner = owner;
		this.method = method;
		this.priority = priority;
		this.event = event;
		this.filters = filters;
		this.hasFilters = filters.length > 0;
	}
	
	/**
	 * @return The object that contains the listening method.
	 */
	public Object getOwner() {
		return this.owner;
	}
	
	/**
	 * @return The method that is listening for events.
	 */
	public Method getMethod() {
		return this.method;
	}
	
	/**
	 * @return The priority of the listening method.
	 */
	public Priority getPriority() {
		return this.priority;
	}
	
	/**
	 * @deprecated The event that the method is listening for is stored in a map in IridiumEventManager
	 * @return The event that the method is listening for
	 */
	public Class<? extends Event> getEvent() {
		return this.event;
	}
	
	/**
	 * Get every filter to check
	 * @return Array of filters to check
	 */
	public ListenerFilter[] getFilters() {
		return this.filters;
	}
	
	/**
	 * Checks whether there are any filters to check
	 * @return Whether the listening method has any filters to check
	 */
	public boolean hasFilters() {
		return this.hasFilters;
	}

}
