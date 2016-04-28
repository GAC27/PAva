package ist.meic.pa.GenericFunctions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
	GenericFunction(String GenFunName){
		GFName=GenFunName;
	}
	
	/**
	 * Adiciona um novo metodo prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	boolean addMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
		}
		
		return gfMethodsPrimary.add(newGFMethod);
	}
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado antes do prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	boolean addBeforeMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
		}
		
		return gfMethodsBefore.add(newGFMethod);
	}
	
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado depois do prim�rio 
	 * Verifica se j� existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	boolean addAfterMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum j� existente no array
		}
		
		return gfMethodsAfter.add(newGFMethod);
	}
		
	Object call(Object... args){
		ArrayList<Class> argsType=new ArrayList<Class>();
		for(Object o: args){
			//Ver tipo de cada argumento e meter ordenados os tipos dos args para dentro do Array List.
			argsType.add(o.getClass());
		}
		ArrayList<GFMethod> beforeAMethods=getBeforeAplicableMethods(argsType);
		ArrayList<GFMethod> afterAMethods=getAfterAplicableMethods(argsType);
		ArrayList<GFMethod> primaryAMethods=getPrimaryAplicableMethods(argsType);
		return callEffectiveMethod();
	}
	
	
	
	
	private ArrayList<GFMethod> getBeforeAplicableMethods(/*ArrayList tipo_dos_args*/){
		return null;
	}
	
	private ArrayList<GFMethod> getAfterAplicableMethods(/*ArrayList tipo_dos_args*/){
		return null;
	}
	
	private ArrayList<GFMethod> getPrimaryAplicableMethods(/*ArrayList tipo_dos_args*/){
		return null;
	}
	
	private ArrayList<GFMethod> sortBeforeAplicableMethods(/*ArrayList tipo_dos_args*/){
		return null;
	}
	
	private ArrayList<GFMethod> sortAfterAplicableMethods(/*ArrayList tipo_dos_args*/){
		return null;
	}
	
	private ArrayList<GFMethod> getMostSpecificPrimaryMethod(/*ArrayList tipo_dos_args*/){
		return null;
	}
	
	private Object callEffectiveMethod(/*ArrayList beforeMethods, GFMethod primary, ArrayList afterMethods*/){
		return null;
	}
	
}
	
	
	
