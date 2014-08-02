package pw.oxcafebabe.marcusant.eventbus.exceptions;

import java.lang.reflect.Method;

/**
 * Exception thrown when a method marked as a listener is not a valid event listener.
 * @author Marcus
 */
public class InvalidListenerException extends Exception {
	
	private static final long serialVersionUID = -8561771487700556570L;
	
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
