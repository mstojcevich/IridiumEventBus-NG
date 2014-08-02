package pw.oxcafebabe.marcusant.eventbus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a method for event listening
 * @author Marcus
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
	
	/**
	 * Gets the priority that dictates when to invoke the method
	 * @return Priority of the listening method
	 */
	public Priority value() default Priority.NORMAL;

}
