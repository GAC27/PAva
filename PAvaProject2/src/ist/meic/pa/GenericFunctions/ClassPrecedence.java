package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;
import java.util.List;

public class ClassPrecedence {
	public static List<Class> getSuperClasses(Object o) {
		  Class clazz= o.getClass();
		  return getSuperClasses(clazz);
	}
	
	public static List<Class> getSuperClasses(Class clazz) {
	  List<Class> clazzList = new ArrayList<Class>();
	  Class superclazz = clazz.getSuperclass();
	  clazzList.add(clazz);
	  Class[] interfaces= clazz.getInterfaces();
	  for(Class c : interfaces){
		  clazzList.add(c);
	  }
	  while (superclazz != null) {   
		clazzList.add(superclazz);
		interfaces= superclazz.getInterfaces();
		for(Class c : interfaces){
			clazzList.add(c);
		}
		clazz = superclazz;
		superclazz = clazz.getSuperclass();
	  }
	  return clazzList;
	}
}
