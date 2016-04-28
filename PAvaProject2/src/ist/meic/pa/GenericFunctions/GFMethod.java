package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GFMethod {
	
	List<Class> parameters = new ArrayList<Class>(); 
	
	public GFMethod(){
		for(Method m : this.getClass().getDeclaredMethods() ) {
			System.out.println(m.getName());
			if(m.getName().equals("call")){
				for(Class c : m.getParameterTypes()){
					parameters.add(c);
					System.out.println(c.getName());
				}
			}
		}
	}
	
	public Object proxyCall(Object... args){
		Class[] params = (Class[])parameters.toArray();
		try {
			return this.getClass().getDeclaredMethod("call", params).invoke(this, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			// this should never happen
		}
		return null;
	}
	
}
