package pw.oxcafebabe.marcusant.eventbus;

/**
 * Listener that prints "Hello world!" with a working event manager
 * @author Marcus
 */
public class TestListener {
	
	@EventListener // Will listen at default priority
	public void listenEvent1(SimpleEvent event /*Will listen for TestEvent*/) {
		throw new RuntimeException("Event Received");
	}
}
