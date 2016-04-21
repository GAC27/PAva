package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;

//A fun�ao generica tem de conter todas as especifica��es do metodo generico
//Para escolher o metodo a executar(effective Method) temos de fazer os seguintes passos:
//-Selecionar os metodos adequados aos argumentos passados(cujos argumentos fa�am "wrap")
//-Ordenar os metodos por ordem de precedencia do mais especifico para o menos especifico
//-Combinar os metodos aplicaveis(1 prim�rio , multiplos auxiliares) e produzir o effective method
//
//usando como argumentos a fun�ao generica original e os argumentos da chamada a fun�ao
//Se existir um Effective Method executamo-lo, caso contr�rio, chamamos "no-applicable-method"

//NOTA: No nosso caso a GenericFunction pode ter um n�mero arbitr�rio de args
//NOTA: Possivelmente faria sentido em vez de termos um array de metodos termos um mapa cuja chave �
//o numero de argumentos do GFMethod e cujo valor � um array de GFMethods



/**
 * 
 *A fun�ao generica tem de conter todas as especifica��es do metodo generico
 *Para escolher o metodo a executar(effective Method) temos de fazer os seguintes passos:
 *-Selecionar os metodos adequados aos argumentos passados(cujos argumentos fa�am "wrap")
 *-Ordenar os metodos por ordem de precedencia do mais especifico para o menos especifico
 *-Combinar os metodos aplicaveis(1 prim�rio , multiplos auxiliares) e produzir o effective method
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
	 * Lista de GFMethods prim�rios desta fun��o gen�rica
	 */
	ArrayList<GFMethod> gfMethodsPrimary;
	
	/**
	 * Lista de GFMethods auxiliares(before) desta fun��o gen�rica
	 */
	ArrayList<GFMethod> gfMethodsBefore;
		
	/**
	 * Lista de GFMethods auxiliares(after) desta fun��o gen�rica
	 */
	ArrayList<GFMethod> gfMethodsAfter;
	
	
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
		gfMethodsPrimary=new ArrayList<GFMethod>();
		gfMethodsBefore=new ArrayList<GFMethod>();
		gfMethodsAfter=new ArrayList<GFMethod>();
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
	
	
	
}
	
	
	
