package pw.oxcafebabe.marcusant.eventbus.managers.iridium;

import java.lang.reflect.Method;

import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.Priority;

class ListeningMethod {
	
	private final Object owner;
	private final Method method;
	private final Priority priority;
	private final Class<? extends Event> event; //TODO since this is stored in a hashmap with the listenermethod in the IridiumEvent implementation, this is unused and should possibly be removed
	
	/**
	 * Creates a new ListeningMethod
	 * @param owner Object who's class contains the listening method
	 * @param method Method that is listening
	 * @param priority Priority of the listening method
	 */
	public ListeningMethod(Object owner, Method method, Priority priority, Class<? extends Event> event) {
		this.owner = owner;
		this.method = method;
		this.priority = priority;
		this.event = event;
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

}
