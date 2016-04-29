package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ist.meic.pa.GenericFunctions.Exceptions.GenericFunctionIllegalArgumentException;

/**
 * 
 *A fun�ao generica tem de conter todas as especifica��es do metodo generico
 *Para escolher o metodo a executar(effective Method) temos de fazer os seguintes passos:
 *-Selecionar os metodos adequados aos argumentos passados(cujos argumentos fa�am "wrap")
 *-Ordenar os metodos por ordem de precedencia do mais especifico para o menos especifico
 *-Combinar os metodos aplicaveis(1 prim�rio , multiplos auxiliares) e produzir o effective method
 *
 *usando como argumentos a fun�ao generica original e os argumentos da chamada a fun�ao
 *Se existir um Effective Method executamo-lo, caso contr�rio, chamamos "no-applicable-method"
 *
 *NOTA: No nosso caso a GenericFunction pode ter um n�mero arbitr�rio de args
 * 
 */
public class GenericFunction {
	/**
	 * Mapa de GFMethods prim�rios desta fun��o gen�rica 
	 * Chave � o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsPrimary = new HashMap<Integer,ArrayList<GFMethod>>();
	
	/**
	 * Mapa de GFMethods auxiliares(before) desta fun��o gen�rica
	 * Chave � o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsBefore = new HashMap<Integer,ArrayList<GFMethod>>();
		
	/**
	 * Mapa de GFMethods auxiliares(after) desta fun��o gen�rica
	 * Chave � o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsAfter = new HashMap<Integer,ArrayList<GFMethod>>();

	/**
	 * Nome do metodo Generico
	 * Chave � o numero de argumentos
	 */
	final String GFName;
	
	/**
	 * Construtor que recebe o nome da fun��o generica
	 * @param GenFunName
	 */
	public GenericFunction(String GenFunName){
		GFName=GenFunName;
	}
	
	/**
	 * Adiciona um novo metodo prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	public void addMethod(GFMethod newGFMethod){
		ArrayList<GFMethod> gfMethods=gfMethodsPrimary.get(newGFMethod.getArgs().size());
		if(gfMethods == null){
			gfMethods=new ArrayList<GFMethod>();
			gfMethods.add(newGFMethod);
			gfMethodsPrimary.put(newGFMethod.getArgs().size(), gfMethods) ;
		}
		else{
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
			for(int i=0; i<gfMethods.size(); i++){
				if(newGFMethod.getArgsString().equals(gfMethods.get(i).getArgsString())){
					gfMethods.set(i, newGFMethod);
					return;
				}
			}
			gfMethods.add(newGFMethod);
		}		
	}
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado antes do prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	public void addBeforeMethod(GFMethod newGFMethod){
		ArrayList<GFMethod> gfMethods=gfMethodsBefore.get(newGFMethod.getArgs().size());
		if(gfMethods == null){
			gfMethods=new ArrayList<GFMethod>();
			gfMethods.add(newGFMethod);
			gfMethodsBefore.put(newGFMethod.getArgs().size(), gfMethods) ;
		}
		else{
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
			for(GFMethod temp: gfMethods){
				if(newGFMethod.getArgsString().equals(temp.getArgsString())){
					System.err.println("Funcao ja existe para esse tipo de argumentos: "+newGFMethod.getArgsString());
					return;
				}
			}
			gfMethods.add(newGFMethod);
		}
	}
	
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado depois do prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */

	public void addAfterMethod(GFMethod newGFMethod){
		ArrayList<GFMethod> gfMethods=gfMethodsAfter.get(newGFMethod.getArgs().size());
		if(gfMethods == null){
			gfMethods=new ArrayList<GFMethod>();
			gfMethods.add(newGFMethod);
			gfMethodsAfter.put(newGFMethod.getArgs().size(), gfMethods) ;
		}
		else{
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
			for(GFMethod temp: gfMethods){
				if(newGFMethod.getArgsString().equals(temp.getArgsString())){
					System.err.println("Funcao ja existe para esse tipo de argumentos: "+newGFMethod.getArgsString());
					return;
				}
			}
			gfMethods.add(newGFMethod);
		}
	}
		

	public Object call(Object... args){
		ArrayList<Class> argsType = new ArrayList<Class>();

		for(Object o: args){
			//Ver tipo de cada argumento e meter ordenados os tipos dos args para dentro do Array List.
			argsType.add(o.getClass());
		}

		ArrayList<GFMethod> beforeAMethods=getBeforeAplicableMethods(argsType);
		ArrayList<GFMethod> afterAMethods=getAfterAplicableMethods(argsType);
		try{
		
			ArrayList<GFMethod> primaryAMethods=getPrimaryAplicableMethods(argsType);
			beforeAMethods= sortBeforeAplicableMethods(argsType,beforeAMethods);
			afterAMethods= sortAfterAplicableMethods(argsType,afterAMethods);
			GFMethod primaryEffectiveMethod= getEffectivePrimaryMethod(argsType,primaryAMethods);
			
			return callEffectiveMethod(beforeAMethods,primaryEffectiveMethod, afterAMethods, args);
		
		}
		catch(GenericFunctionIllegalArgumentException e){
			System.out.print("Exception in thread "+ Thread.currentThread().getName()+ " java.lang.IllegalArgumentException:" 
				+"No methods for generic function" + GFName + "with args [");
			for(Object o: args){
				System.out.print(o + ",");
			}
			System.out.print(" of classes [");
			for(Object o: args){
				System.out.print(o.getClass() + ",");
			}
			System.out.println("]");
			return null;
		}
		
	
	}
	
	
	
	/**
	 * Retorna uma lista com os metodos auxiliares do tipo before aplicaveis aos argumentos passados na chamada da fun��o.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	private ArrayList<GFMethod> getBeforeAplicableMethods(ArrayList<Class> argsType){
		boolean isAplicable;
		ArrayList<GFMethod> aplicableGFMethods=new ArrayList<GFMethod>();
		ArrayList<GFMethod> gfmBefore=gfMethodsBefore.get(argsType.size());
		if(gfmBefore!=null){
			for(GFMethod gfmTemp: gfmBefore){
				isAplicable=true;
				for(int i=0; i < argsType.size(); i++){
					if (!(gfmTemp.getArg(i)).isAssignableFrom(argsType.get(i))) {
						isAplicable=false;
						break;
					}	
					
				}
				if(isAplicable){
					aplicableGFMethods.add(gfmTemp);
				}
			}
			return aplicableGFMethods;
		}
		
		else{
			return new ArrayList<GFMethod>();
		}
		
	}
	
	/**
	 * Retorna uma lista com os metodos auxiliares do tipo before aplicaveis aos argumentos passados na chamada da fun��o.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	private ArrayList<GFMethod> getAfterAplicableMethods(ArrayList<Class> argsType){
		boolean isAplicable;
		ArrayList<GFMethod> aplicableGFMethods=new ArrayList<GFMethod>();
		ArrayList<GFMethod> gfmAfter=gfMethodsAfter.get(argsType.size());
		if(gfmAfter!=null){
			for(GFMethod gfmTemp:gfmAfter){
				isAplicable=true;
				for(int i=0; i < argsType.size(); i++){
					if (!(gfmTemp.getArg(i)).isAssignableFrom(argsType.get(i))) {
						isAplicable=false;
						break;
					}	
					
				}
				if(isAplicable){
					aplicableGFMethods.add(gfmTemp);
				}
			}
			return aplicableGFMethods;
		}
		
		else{
			return new ArrayList<GFMethod>();
		}
	}
	
	/**
	 * Retorna uma lista com os metodos aplicaveis aos argumentos passados na chamada da fun��o.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	private ArrayList<GFMethod> getPrimaryAplicableMethods(ArrayList<Class> argsType) throws GenericFunctionIllegalArgumentException{
		//Se nao houver nenhum metodo aplicavel retornamos uma mensagem de erro (System.err) ou criamos uma excep�ao e imprimimos o trace
		boolean isAplicable;
		ArrayList<GFMethod> aplicableGFMethods=new ArrayList<GFMethod>();
		ArrayList<GFMethod> gfmPrimary=gfMethodsPrimary.get(argsType.size());
		if(gfmPrimary!=null){
			for(GFMethod gfmTemp:gfmPrimary){
				isAplicable=true;
				for(int i=0; i < argsType.size(); i++){
					if (!(gfmTemp.getArg(i)).isAssignableFrom(argsType.get(i))) {
						isAplicable=false;
						break;
					}	
					
				}
				if(isAplicable){
					aplicableGFMethods.add(gfmTemp);
				}
			}
			
			if(aplicableGFMethods.isEmpty()){
				throw new GenericFunctionIllegalArgumentException();
			}	
			return aplicableGFMethods;
		}
		else{
				throw new GenericFunctionIllegalArgumentException();
		}	
		
	}
	
	
	/**
	 * Retorna uma lista de GFMethods ordenada do mais especifico para o menos especifico.
	 * Retorna null se a lista gfMethods recebida estiver vazia
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	private ArrayList<GFMethod> sortBeforeAplicableMethods(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		return null;
	}
	
	/**
	 * Retorna uma lista de GFMethods ordenada do menos especifico para o mais especifico.
	 * Retorna null se a lista gfMethods recebida estiver vazia
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	private ArrayList<GFMethod> sortAfterAplicableMethods(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		return null;
	}
	
	/**
	 * Retorna o metodo prim�rio mais especifico
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	private GFMethod getEffectivePrimaryMethod(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		//Como nao existem dois metodos genericos com o mesmo tipo de argumentos, se a chamada nao tiver argumentos ent�o apenas existir� no maximo um aplicable method passado para este metodo.
		if(argsType.size()==0){
			return gfMethods.get(0);
		}
			
		
		Map<Integer, ArrayList<GFMethod>> gfMethodsOrdered = new HashMap<Integer,ArrayList<GFMethod>>();
		ArrayList<GFMethod> mostSpecific = gfMethods;
		List<Class> classPrecedenceArg;
		ArrayList<GFMethod> ALGFMtemp=null;
		Set<Integer> mapKeys;
		Integer smallestKey;
		
		for(int i=0; i< argsType.size(); i++){
			smallestKey= null;
			gfMethodsOrdered = new HashMap<Integer,ArrayList<GFMethod>>();
			classPrecedenceArg= ClassPrecedence.getSuperClasses(argsType.get(i));
			
			//ordena num mapa por ordem de especificidade do argumento no indice i os GFMethods
			for(GFMethod gfmtemp: mostSpecific){
				ALGFMtemp=gfMethodsOrdered.get(classPrecedenceArg.indexOf(gfmtemp.getArg(i)));
				if(ALGFMtemp==null){
					ALGFMtemp=new ArrayList<GFMethod>();
					ALGFMtemp.add(gfmtemp);
					gfMethodsOrdered.put(new Integer(classPrecedenceArg.indexOf(gfmtemp.getArg(i))), ALGFMtemp);
				}
				else{
					ALGFMtemp.add(gfmtemp);
				}
			}
			
			mapKeys= gfMethodsOrdered.keySet();
			for(Integer tempInt: mapKeys){
				if(smallestKey==null || smallestKey.compareTo(tempInt)>0){
					smallestKey=tempInt;
				}
			}
			ALGFMtemp=gfMethodsOrdered.get(smallestKey);
			//se s� existir um metodo no mapa na entrada com a chave mais pequena ent�o esse � o mais especifico
			if(ALGFMtemp.size()==1){
				break;
			}
			else{
				mostSpecific=ALGFMtemp;
			}
		}
		return ALGFMtemp.get(0);
	}
	
	/**
	 * Executa a chamada ao metodo effectivo fazendo chamadas aos metodos auxiliares e primario pela ordem do CLOS e retorna o valor retornado pelo metodo primario.
	 * @param beforeMethods
	 * @param primary
	 * @param afterMethods
	 * @param args
	 * @return
	 */
	private Object callEffectiveMethod(List<GFMethod> beforeMethods, GFMethod primary, List<GFMethod> afterMethods, Object... args){
		//Thread.cur
		if(beforeMethods != null){
			for(GFMethod method : beforeMethods){
				method.proxyCall(args);
			}
		}
		
		Object primaryReturnValue = primary.proxyCall(args);
		
		if(afterMethods!= null){
			for(GFMethod method : afterMethods){
				method.proxyCall(args);
			}
		}
		return primaryReturnValue;
	}
	
}
	
	
	
