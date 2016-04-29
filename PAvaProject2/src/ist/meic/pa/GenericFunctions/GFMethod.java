package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GFMethod {
	
	List<Class> parameters = new ArrayList<Class>(); 
	
	/**
	 * Guarda o tipo dos argumentos da função call na lista parameters
	 */
	public GFMethod(){
		for(Method m : this.getClass().getDeclaredMethods() ) {
			if(m.getName().equals("call")){
				for(Class c : m.getParameterTypes()){
					parameters.add(c);
				}
			}
		}
	}
	
	/**
	 * Executa uma chamada ao metodo "call" usando reflexão 
	 * @param args
	 * @return 
	 */
	public Object proxyCall(Object... args){
//		Class[] params = (Class[])parameters.toArray();
		Class[] params = new Class[parameters.size()];
		for(int i =0; i<parameters.size(); i++){
			params[i]=parameters.get(i);
		}
		try {
			return this.getClass().getDeclaredMethod("call",  params).invoke(this, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			// this should never happen
		}
		return null;
	}
	
	/**
	 * Devolve um String que é a concatenação do tipo de cada argumento que o metodo recebe.
	 * @return key
	 */
	public String getArgsString(){
		String key="";
		for(Class c : parameters){
			key+=c+" ";
		}
		return key;
	}

	/**
	 * Devolve uma lista com o tipo dos parametros da funçao.
	 * @return 
	 */
	public List<Class> getArgs(){
		return parameters;
	}
	
	/**
	 * Devolve uma Classe que corresponde ao tipo do argumento na posição index da lista dos argumentos da função
	 * @param index
	 * @return
	 */
	public Class getArg(int index){
		return parameters.get(index);
	}
	
}
