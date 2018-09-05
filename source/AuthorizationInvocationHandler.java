// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

//invocation handler

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* Here authorization process takes place.From client side if any remote method invoked then 
 * it checks that client have that require role to access the method or not. If match 
 * then allow to invoke with the provided parameters otherwise throws exception
*/

public class AuthorizationInvocationHandler implements InvocationHandler, Serializable {
	private Object objectImpl;
 
	public AuthorizationInvocationHandler(Object impl) {
	   this.objectImpl = impl;
	}
	 
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.isAnnotationPresent(RequiresRole.class)) {
			RequiresRole test = method.getAnnotation(RequiresRole.class);
			String session = (String) args[0];

			//System.out.println(session);

			if (session.equals(test.value())) {
				 return method.invoke(objectImpl, args);
            		} else {
            			throw new AuthorizationException(method.getName());
            		}
		} else {
			return method.invoke(objectImpl, args);
		}   
	}
}