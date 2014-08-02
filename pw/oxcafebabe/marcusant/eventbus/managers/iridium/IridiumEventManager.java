package pw.oxcafebabe.marcusant.eventbus.managers.iridium;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pw.oxcafebabe.marcusant.eventbus.Event;
import pw.oxcafebabe.marcusant.eventbus.EventListener;
import pw.oxcafebabe.marcusant.eventbus.EventManager;
import pw.oxcafebabe.marcusant.eventbus.Priority;
import pw.oxcafebabe.marcusant.eventbus.exceptions.InvalidListenerException;

/**
 * An event manager targeted toward rapidly managing large amount of event pushes at the cost of slightly slower registering of listeners.
 * @author Marcus
 */
public class IridiumEventManager implements EventManager {

	/**
	 * Map that maps events to an array of all methods listening for it, in order by priority.
	 */
	private final Map<Class<? extends Event>, List<ListeningMethod>> orderedListeningMethods = new HashMap<Class<? extends Event>, List<ListeningMethod>>(); //We assume that these lists will always be ArrayLists
	
	/**
	 * Whether the listening method map is "clean" (sorted properly based on priority)
	 */
	private boolean unclean = false;
	
	@SuppressWarnings("unchecked")
	@Override
	public void register(Object listeningObject) throws InvalidListenerException {
		List<Class<? extends Event>> dirtyEvents = new ArrayList<Class<? extends Event>>();
		
		Class<?> objectClass = listeningObject.getClass();
		for(Method m : objectClass.getDeclaredMethods()) {
			for(Annotation a : m.getDeclaredAnnotations()) {
				if(a.annotationType().equals(EventListener.class)) {
					EventListener eventListener = (EventListener)a;
					if(m.getParameterTypes().length != 1) {
						throw new InvalidListenerException(objectClass, m);
					}
					Class<?> parameter = m.getParameterTypes()[0];
					if(Event.class.isAssignableFrom(parameter)) {
						Class<? extends Event> event = (Class<? extends Event>) parameter;
						
						this.unclean = true;
						dirtyEvents.add(event);
						if(!this.orderedListeningMethods.containsKey(event)) {
							this.orderedListeningMethods.put(event, new ArrayList<ListeningMethod>());
						}
						m.setAccessible(true); //people report speedups when invoking, so why not. Removes overhead from access checks.
						List<ListeningMethod> listeningMethods = this.orderedListeningMethods.get(event);
						ListeningMethod listeningMethod = new ListeningMethod(listeningObject, m, eventListener.value(), event);
						listeningMethods.add(listeningMethod);
					} else {
						throw new InvalidListenerException(objectClass, m);
					}
				}
			}
		}
		
		if(this.unclean) {
			this.sortEventsByPriority(dirtyEvents.toArray(new Class[dirtyEvents.size()]));
			this.unclean = false;
		}
	}

	@Override
	public boolean unregister(Object listeningObject) {
		boolean succeeded = true;
		for(List<ListeningMethod> methodList : this.orderedListeningMethods.values()) {
			for(ListeningMethod m : methodList) {
				if(m.getOwner().equals(listeningObject)) {
					if(!methodList.remove(m)) {
						succeeded = false;
					}
				}
			}
		}
		return succeeded;
	}

	@Override
	public void push(Event event) {
		List<ListeningMethod> listeningMethods = this.orderedListeningMethods.get(event.getClass());
		if(listeningMethods == null)return;
		for(ListeningMethod m : listeningMethods) {
			try {
				m.getMethod().invoke(m.getOwner(), event);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sorts the given events by priority in the ordered listening method map.
	 * @param dirtyEvents Events that need to be resorted
	 */
	private void sortEventsByPriority(Class<? extends Event> ... dirtyEvents) {
		for(Class<? extends Event> event : dirtyEvents) {
			List<ListeningMethod> registeredListeningMethods = orderedListeningMethods.get(event);
			List<ListeningMethod> orderedListeningMethods = new ArrayList<ListeningMethod>();
			for(Priority p : Priority.values()) {
				for(ListeningMethod listeningMethod : registeredListeningMethods) {
					if(listeningMethod.getPriority().equals(p)) {
						orderedListeningMethods.add(listeningMethod);
					}
				}
			}
			this.orderedListeningMethods.put(event, orderedListeningMethods);
		}
	}

}
