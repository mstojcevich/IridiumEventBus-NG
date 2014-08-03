package pw.oxcafebabe.marcusant.eventbus.example;

import pw.oxcafebabe.marcusant.eventbus.EventListener;
import pw.oxcafebabe.marcusant.eventbus.Priority;
import pw.oxcafebabe.marcusant.eventbus.SimpleEvent;

/**
 * Listener that prints "Hello world!" with a working event manager
 * @author Marcus
 */
public class TestListener {
	
	@EventListener //Will listen at default priority
	public void listenEvent1(SimpleEvent event /*Will listen for TestEvent*/) {
		System.out.println("world!");
	}
	
	@EventListener(Priority.HIGH) //Will listen at high priority
	public void listenEvent2(SimpleEvent event /*Will listen for TestEvent*/) {
		System.out.print("Hello ");
	}
	

}
