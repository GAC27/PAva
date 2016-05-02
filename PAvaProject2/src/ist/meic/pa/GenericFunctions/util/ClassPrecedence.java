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
//	public static List<Class> getSuperClasses(Class clazz) {
//	  List<Class> clazzList = new ArrayList<Class>();
//	  Class superclazz = clazz.getSuperclass();
//	  clazzList.add(clazz);
//	  Class[] interfaces= clazz.getInterfaces();
//	  for(Class c : interfaces){
//		  clazzList.add(c);
//	  }
//	  while (superclazz != null) {   
//		clazzList.add(superclazz);
//		interfaces= superclazz.getInterfaces();
//		for(Class c : interfaces){
//			if(!clazzList.contains(c)){
//				clazzList.add(c);
//			}
//			
//		}
//		clazz = superclazz;
//		superclazz = clazz.getSuperclass();
//	  }
//	  return clazzList;
//	}
	
	public static List<Class> getSuperClasses(Class clazz) {
		int i=1;
		List<Class> clazzList = new ArrayList<Class>();
		clazzList.add(clazz);
		clazzList.add(clazz.getSuperclass());
		Class[] interfaces= clazz.getInterfaces();
		for(Class c : interfaces){
		 clazzList.add(c);
		}
		if(clazzList.size()==1){
			return clazzList;
		}
		while (true) { 
			clazz=clazzList.get(i);
			if(clazz.getSuperclass()!=null){
				clazzList.add(clazz.getSuperclass());
			}
			interfaces= clazz.getInterfaces();
			for(Class c : interfaces){
				if(!clazzList.contains(c)){
					clazzList.add(c);
				}
				
			}
			i++;
			if(i== clazzList.size()-1){
				break;
			}
		 }
		return clazzList;
	}
}
