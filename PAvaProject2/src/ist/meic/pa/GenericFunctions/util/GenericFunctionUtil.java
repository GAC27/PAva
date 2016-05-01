package ist.meic.pa.GenericFunctions.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ist.meic.pa.GenericFunctions.*;

public class GenericFunctionUtil {

	public static String printPrettyResult(Object result) {
		String prettyResult = "";
		if(result.getClass().isArray()) {
			prettyResult += "[";
			Object[] resultArray = (Object[]) result;
			for(int i = 0 ; i < resultArray.length ; i++){
				prettyResult += printPrettyResult(resultArray[i]);
				if(i < resultArray.length - 1){
					prettyResult += ", ";
				}
			}
			prettyResult += "]";
		}else{
			prettyResult = result.toString();
		}
		return prettyResult;
	}
	
	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}
	
	/**
	 * Ordena os metodos apartir dum dado indice (respectivo a lista do tipo dos argumentos) os metodos passados como argumento
	 * @param indexArg
	 * @param gfMethods
	 * @param argsType
	 * @return
	 */
	public static ArrayList<GFMethod> sortMethods(int indexArg, ArrayList<GFMethod> gfMethods, ArrayList<Class> argsType)
	{
		ArrayList<GFMethod> retOrdered= new ArrayList<GFMethod>();
		Map<Integer, ArrayList<GFMethod>> gfMethodsOrdered = new HashMap<Integer,ArrayList<GFMethod>>();
		ArrayList<GFMethod> mostSpecific = gfMethods;
		List<Class> classPrecedenceArg;
		ArrayList<GFMethod> ALGFMtemp=null;
		Set<Integer> mapKeys;
		Integer smallestKey;
		
		smallestKey= null;
		gfMethodsOrdered = new HashMap<Integer,ArrayList<GFMethod>>();
		classPrecedenceArg= ClassPrecedence.getSuperClasses(argsType.get(indexArg));
		
		//ordena num mapa por ordem de especificidade do argumento no indice i os GFMethods
		for(GFMethod gfmtemp: mostSpecific){
			ALGFMtemp=gfMethodsOrdered.get(classPrecedenceArg.indexOf(gfmtemp.getArg(indexArg)));
			if(ALGFMtemp==null){
				ALGFMtemp=new ArrayList<GFMethod>();
				ALGFMtemp.add(gfmtemp);
				gfMethodsOrdered.put(new Integer(classPrecedenceArg.indexOf(gfmtemp.getArg(indexArg))), ALGFMtemp);
			}
			else{
				ALGFMtemp.add(gfmtemp);
			}
		}
		
		mapKeys= gfMethodsOrdered.keySet();
		List<Integer> orderedKeys= asSortedList(mapKeys);
		for(int i=0; i< orderedKeys.size(); i++){
			ALGFMtemp=gfMethodsOrdered.get(orderedKeys.get(i));
			if(ALGFMtemp.size()==1){
				retOrdered.addAll(ALGFMtemp);
			}
			else{
				retOrdered.addAll(sortMethods(indexArg++,ALGFMtemp,argsType));
			}
		}
		return retOrdered;
	}
	
}
