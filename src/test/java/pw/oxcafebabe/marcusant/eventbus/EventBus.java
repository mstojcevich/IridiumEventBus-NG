package pw.oxcafebabe.marcusant.eventbus;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pw.oxcafebabe.marcusant.eventbus.exceptions.EventCancellationException;
import pw.oxcafebabe.marcusant.eventbus.exceptions.EventException;
import pw.oxcafebabe.marcusant.eventbus.managers.iridium.IridiumEventManager;

public class EventBus {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAttachSubscriber() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Test Succeeded");

		EventManager eventManager = new IridiumEventManager();

		eventManager.registerSubscriber(SimpleEvent.class, new SimpleSubscriber<SimpleEvent>() {
            @Override
            public void eventReceived(SimpleEvent event) {
                throw new RuntimeException("Test Succeeded");
            }
        });

		eventManager.push(new SimpleEvent());
	}

    @Test
    public void testCancelEvent() {
        EventManager manager = new IridiumEventManager();

        manager.registerSubscriber(SimpleEvent.class, new SimpleSubscriber<SimpleEvent>(1) {
            @Override
            public void eventReceived(SimpleEvent event) {
                try {
                    event.cancel();
                } catch (EventCancellationException e) {
                    throw new RuntimeException("Was unable to cancel an event that should be cancellable");
                }
            }
        });

        manager.registerSubscriber(SimpleEvent.class, new SimpleSubscriber<SimpleEvent>(0) {
            @Override
            public void eventReceived(SimpleEvent event) {
                throw new RuntimeException("Received an event should have been cancelled");
            }
        });
    }

    @Test
    public void testUncancellableEvent() {
        EventManager manager = new IridiumEventManager();

        exception.expect(RuntimeException.class);
        exception.expectMessage("Event did not allow us to cancel it!");

        manager.registerSubscriber(SimplePersistentEvent.class, new SimpleSubscriber<SimplePersistentEvent>() {
            @Override
            public void eventReceived(SimplePersistentEvent event) {
                try {
                    event.cancel();
                } catch (EventCancellationException e) {
                    throw new RuntimeException("Event did not allow us to cancel it!");
                }
            }
        });

        manager.push(new SimplePersistentEvent());
    }

    @Test
    public void registerClass() throws EventException {
        EventManager manager = new IridiumEventManager();

        exception.expect(RuntimeException.class);
        exception.expectMessage("An exception was thrown during invocation of bridge method");

        manager.registerObject(new TestListener());

        manager.push(new SimpleEvent());
    }

    @Test
    public void printLoadTest() {
        EventManager manager = new IridiumEventManager();

        for(int i = 0; i <= 10; i++) {
            manager.registerSubscriber(SimpleEvent.class, new SimpleSubscriber<SimpleEvent>() {
                @Override
                public void eventReceived(SimpleEvent event) {
                    // nothing
                }
            });
        }

        long startTime = System.nanoTime();

        for(int i = 0; i <= 500_000; i++){
            manager.push(new SimpleEvent());
        }

        long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;

        System.out.println("Published 500.000 events in " + elapsedTime +"ms");
    }

}
