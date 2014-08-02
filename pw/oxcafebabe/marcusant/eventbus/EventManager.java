package pw.oxcafebabe.marcusant.eventbus;

import pw.oxcafebabe.marcusant.eventbus.exceptions.InvalidListenerException;

/**
 * Manages the sending of events to registered listeners
 * @author Marcus
 */
public interface EventManager {
	
	/**
	 * Registers an object as a listener for events.
	 * @param listeningObject Object to fire events to
	 * @throws InvalidListenerException A method in the listening object's class is marked as a listener but is invalid.
	 */
	public void register(Object listeningObject) throws InvalidListenerException;
	
	/**
	 * Unregisters an object from listening for events.
	 * @param listeningObject Object to unregister
	 * @return Whether the object existed and was successfully removed
	 */
	public boolean unregister(Object listeningObject);
	
	/**
	 * Publishes an event to all registered listening methods.
	 * @param event Event to publish
	 */
	public void push(Event event);

}
