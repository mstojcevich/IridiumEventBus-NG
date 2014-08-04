package pw.oxcafebabe.marcusant.eventbus;

import pw.oxcafebabe.marcusant.eventbus.filters.Cancellable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method for event listening
 * @author Marcus
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
	
	/**
	 * Gets the priority that dictates when to invoke the method
	 * @return Priority of the listening method
     * @deprecated as 'value' does not imply that this is in fact a priority value
	 */
    @Deprecated
	public Priority value() default Priority.NORMAL;

    /**
     * Priority of event subscriber
     *
     * @return subscriber priority
     */
    public Priority priority() default Priority.NORMAL;
	
	/**
	 * Filters to apply to the event before firing to the listener
	 * @return A list of filters to run the event and method through before firing
	 */
	public Class<? extends ListenerFilter<?>>[] filters() default { Cancellable.class };

}
