package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 *NOTA: Possivelmente faria sentido em vez de termos um array de metodos termos um mapa cuja chave �
 *o numero de argumentos do GFMethod e cujo valor � um array de GFMethods
 * 
 */
public class GenericFunction {
	/**
	 * Mapa de GFMethods prim�rios desta fun��o gen�rica
	 */
	Map<String,GFMethod> gfMethodsPrimary = new HashMap<String,GFMethod>();
	
	/**
	 * Mapa de GFMethods auxiliares(before) desta fun��o gen�rica
	 */
	Map<String,GFMethod> gfMethodsBefore = new HashMap<String,GFMethod>();
		
	/**
	 * Mapa de GFMethods auxiliares(after) desta fun��o gen�rica
	 */
	Map<String,GFMethod> gfMethodsAfter = new HashMap<String,GFMethod>();

	/**
	 * Nome do metodo Generico
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
	public boolean addMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
//			if(j� existe com args iguais ){
//			System.err.println("funcao ja existe");
//		}
		}
//		return gfMethodsPrimary.add(newGFMethod);
		return false;
	}
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado antes do prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	public boolean addBeforeMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
//			if(j� existe com args iguais ){
//				System.err.println("funcao ja existe");
//			}
		}
		
//		return gfMethodsBefore.add(newGFMethod);
		return false;

	}
	
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado depois do prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */

	public boolean addAfterMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
//			if(j� existe com args iguais ){
//			System.err.println("funcao ja existe");
//		}
		}
//		return gfMethodsAfter.add(newGFMethod);
		return false;
	}
		

	public Object call(Object... args){
		ArrayList<Class> argsType = new ArrayList<Class>();

		for(Object o: args){
			//Ver tipo de cada argumento e meter ordenados os tipos dos args para dentro do Array List.
			argsType.add(o.getClass());
		}

		ArrayList<GFMethod> beforeAMethods=getBeforeAplicableMethods(argsType);
		ArrayList<GFMethod> afterAMethods=getAfterAplicableMethods(argsType);
//		try{
			ArrayList<GFMethod> primaryAMethods=getPrimaryAplicableMethods(argsType);
//		}
//		catch(A nossa excep��o){
//			System.out.println("Exception in thread "+ Thread.currentThread().getName()+ "java.lang.IllegalArgumentException:
//				No methods for generic function"  with args [[1, 2], 3]
//				of classes [class [Ljava.lang.Object;, class java.lang.Integer]
//
//		}
		beforeAMethods= sortBeforeAplicableMethods(argsType);
		afterAMethods= sortAfterAplicableMethods(argsType);
		GFMethod primaryEffectiveMethod= getEffectivePrimaryMethod(argsType);
		
		return callEffectiveMethod(beforeAMethods,primaryEffectiveMethod, afterAMethods, args);

	}
	
	
	
	
	private ArrayList<GFMethod> getBeforeAplicableMethods(ArrayList<Class> argsType){
		return null;
	}
	
	private ArrayList<GFMethod> getAfterAplicableMethods(ArrayList<Class> argsType){
		return null;
	}
	
	private ArrayList<GFMethod> getPrimaryAplicableMethods(ArrayList<Class> argsType){
		//Se nao houver nenhum metodo aplicavel retornamos uma mensagem de erro (System.err) ou criamos uma excep�ao e imprimimos o trace
		return null;
	}
	
	private ArrayList<GFMethod> sortBeforeAplicableMethods(ArrayList<Class> argsType){
		return null;
	}
	
	private ArrayList<GFMethod> sortAfterAplicableMethods(ArrayList<Class> argsType){
		return null;
	}
	
	private GFMethod getEffectivePrimaryMethod(ArrayList<Class> argsType){
		return null;
	}
	
	private Object callEffectiveMethod(List<GFMethod> beforeMethods, GFMethod primary, List<GFMethod> afterMethods, Object... args){
		//Thread.cur
		for(GFMethod method : beforeMethods){
			method.proxyCall(args);
		}
		
		Object primaryReturnValue = primary.proxyCall(args);
		
		for(GFMethod method : afterMethods){
			method.proxyCall(args);
		}
		
		return primaryReturnValue;
	}
	
}
	
	
	
