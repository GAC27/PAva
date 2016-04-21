package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;

//A funçao generica tem de conter todas as especificações do metodo generico
//Para escolher o metodo a executar(effective Method) temos de fazer os seguintes passos:
//-Selecionar os metodos adequados aos argumentos passados(cujos argumentos façam "wrap")
//-Ordenar os metodos por ordem de precedencia do mais especifico para o menos especifico
//-Combinar os metodos aplicaveis(1 primário , multiplos auxiliares) e produzir o effective method
//
//usando como argumentos a funçao generica original e os argumentos da chamada a funçao
//Se existir um Effective Method executamo-lo, caso contrário, chamamos "no-applicable-method"

//NOTA: No nosso caso a GenericFunction pode ter um número arbitrário de args
//NOTA: Possivelmente faria sentido em vez de termos um array de metodos termos um mapa cuja chave é
//o numero de argumentos do GFMethod e cujo valor é um array de GFMethods



/**
 * 
 *A funçao generica tem de conter todas as especificações do metodo generico
 *Para escolher o metodo a executar(effective Method) temos de fazer os seguintes passos:
 *-Selecionar os metodos adequados aos argumentos passados(cujos argumentos façam "wrap")
 *-Ordenar os metodos por ordem de precedencia do mais especifico para o menos especifico
 *-Combinar os metodos aplicaveis(1 primário , multiplos auxiliares) e produzir o effective method
 *usando como argumentos a funçao generica original e os argumentos da chamada a funçao
 *Se existir um Effective Method executamo-lo, caso contrário, chamamos "no-applicable-method"
 *
 *NOTA: No nosso caso a GenericFunction pode ter um número arbitrário de args
 *NOTA: Possivelmente faria sentido em vez de termos um array de metodos termos um mapa cuja chave é
 *o numero de argumentos do GFMethod e cujo valor é um array de GFMethods
 * 
 */
public class GenericFunction {
	/**
	 * Lista de GFMethods primários desta função genérica
	 */
	ArrayList<GFMethod> gfMethodsPrimary;
	
	/**
	 * Lista de GFMethods auxiliares(before) desta função genérica
	 */
	ArrayList<GFMethod> gfMethodsBefore;
		
	/**
	 * Lista de GFMethods auxiliares(after) desta função genérica
	 */
	ArrayList<GFMethod> gfMethodsAfter;
	
	
	/**
	 * Nome do metodo Generico
	 */
	final String GFName;
	
	/**
	 * Construtor que recebe o nome da função generica
	 * @param GenFunName
	 */
	GenericFunction(String GenFunName){
		GFName=GenFunName;
		gfMethodsPrimary=new ArrayList<GFMethod>();
		gfMethodsBefore=new ArrayList<GFMethod>();
		gfMethodsAfter=new ArrayList<GFMethod>();
	}
	
	/**
	 * Adiciona um novo metodo primário 
	 * Verifica se já existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
	 * @param newGFMethod
	 */
	boolean addMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum já existente no array
		}
		
		return gfMethodsPrimary.add(newGFMethod);
	}
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado antes do primário 
	 * Verifica se já existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
	 * @param newGFMethod
	 */
	boolean addBeforeMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum já existente no array
		}
		
		return gfMethodsBefore.add(newGFMethod);
	}
	
	
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado depois do primário 
	 * Verifica se já existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
	 * @param newGFMethod
	 */
	boolean addAfterMethod(GFMethod newGFMethod){
		for(GFMethod temp: gfMethodsPrimary){
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum já existente no array
		}
		
		return gfMethodsAfter.add(newGFMethod);
	}
	
	
	
}
	
	
	
