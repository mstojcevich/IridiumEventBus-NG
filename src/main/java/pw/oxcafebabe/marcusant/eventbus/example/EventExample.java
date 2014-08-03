package pw.oxcafebabe.marcusant.eventbus.example;

import pw.oxcafebabe.marcusant.eventbus.EventManager;
import pw.oxcafebabe.marcusant.eventbus.managers.iridium.IridiumEventManager;

public class EventExample {
		
	public static void main(String[] args) throws Exception {
		EventManager eventManager = new IridiumEventManager(); //Create a new Iridium event manager, currently the only event manager and best for most scenarios
		eventManager.register(new TestListener()); //Register a class as a listener. All marked listening methods will be registered.
		
		eventManager.push(new TestEvent()); //Push a new event. This event will be passed to all of its listeners.
	}

}
