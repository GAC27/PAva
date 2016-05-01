package ist.meic.pa.GenericFunctions.util;

import java.util.ArrayList;
import java.util.List;

public class ClassPrecedence{
	/**
	 * Cria a lista de precendencias da classe do objecto "o"
	 * @param o
	 * @return
	 */
	public static List<Class> getSuperClasses(Object o) {
		  Class clazz= o.getClass();
		  return getSuperClasses(clazz);
	}
	/**
	 * Cria a lista de precendencias da classe  "clazz"
	 * @param clazz
	 * @return
	 */
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
