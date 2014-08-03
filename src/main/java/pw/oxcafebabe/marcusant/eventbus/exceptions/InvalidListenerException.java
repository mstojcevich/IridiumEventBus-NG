package pw.oxcafebabe.marcusant.eventbus.exceptions;

import java.lang.reflect.Method;

/**
 * Exception thrown when a method marked as a listener is not a valid event listener.
 * @author Marcus
 */
public final class InvalidListenerException extends EventException {
	
	/**
	 * Class that contains the invalid method
	 */
	private final Class<?> containingClass;
	/**
	 * Invalid method
	 */
	private final Method method;

	/**
	 * @param containingClass Class that contains the invalid method.
	 * @param method Method that is an invalid listener.
	 */
	public InvalidListenerException(Class<?> containingClass, Method method) {
		this.containingClass = containingClass;
		this.method = method;
	}
	
	/**
	 * @return Class that contains the invalid listener
	 */
	public Class<?> getContainingClass() {
		return this.containingClass;
	}
	
	/**
	 * @return Method that is an invalid listener
	 */
	public Method getMethod() {
		return this.method;
	}

}
