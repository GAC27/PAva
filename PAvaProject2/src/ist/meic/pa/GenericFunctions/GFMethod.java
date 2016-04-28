package ist.meic.pa.GenericFunctions;

import java.lang.reflect.Method;

public class GFMethod {
	
	
	public GFMethod(){
		for(Method m : this.getClass().getDeclaredMethods() ) {
			System.out.println(m.getName());
			if(m.getName().equals("call")){
				for(Class c : m.getParameterTypes()){
				System.out.println(c.getName());
				}
			}
		}
	}
}
