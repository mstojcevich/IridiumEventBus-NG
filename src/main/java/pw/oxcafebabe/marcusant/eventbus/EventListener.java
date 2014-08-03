package pw.oxcafebabe.marcusant.eventbus;

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
	 */
	public Priority value() default Priority.NORMAL;
	
	/**
	 * Filters to apply to the event before firing to the listener
	 * @return A list of filters to run the event and method through before firing
	 */
	public Class<? extends ListenerFilter>[] filters() default {};

}
