package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ist.meic.pa.GenericFunctions.Exceptions.GenericFunctionIllegalArgumentException;
import ist.meic.pa.GenericFunctions.util.ClassPrecedence;
import ist.meic.pa.GenericFunctions.util.GenericFunctionUtil;

/**
 * 
 *A funçao generica tem de conter todas as especificações do metodo generico
 *Para escolher o metodo a executar(effective Method) temos de fazer os seguintes passos:
 *-Selecionar os metodos adequados aos argumentos passados(cujos argumentos façam "wrap")
 *-Ordenar os metodos por ordem de precedencia do mais especifico para o menos especifico
 *-Combinar os metodos aplicaveis(1 primário , multiplos auxiliares) e produzir o effective method
 *
 *usando como argumentos a funçao generica original e os argumentos da chamada a funçao
 *Se existir um Effective Method executamo-lo, caso contrário, chamamos "no-applicable-method"
 *
 *NOTA: No nosso caso a GenericFunction pode ter um número arbitrário de args
 * 
 */
public class GenericFunction {
	/**
	 * Mapa de GFMethods primários desta função genérica 
	 * Chave é o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsPrimary = new HashMap<Integer,ArrayList<GFMethod>>();
	
	/**
	 * Mapa de GFMethods auxiliares(before) desta função genérica
	 * Chave é o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsBefore = new HashMap<Integer,ArrayList<GFMethod>>();
		
	/**
	 * Mapa de GFMethods auxiliares(after) desta função genérica
	 * Chave é o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsAfter = new HashMap<Integer,ArrayList<GFMethod>>();

	/**
	 * Nome do metodo Generico
	 * Chave é o numero de argumentos
	 */
	final String GFName;
	
	/**
	 * Construtor que recebe o nome da função generica
	 * @param GenFunName
	 */
	public GenericFunction(String GenFunName){
		GFName=GenFunName;
	}
	
	/**
	 * Adiciona um novo metodo primário 
	 * Verifica se já existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
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
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum já existente no array
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
	 * Adiciona um novo metodo auxiliario a ser executado antes do primário 
	 * Verifica se já existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
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
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum já existente no array
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
	 * Adiciona um novo metodo auxiliario a ser executado depois do primário 
	 * Verifica se já existe um metodoPrimario com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
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
//			Verificar aqui se o metodo a ser inserido tem os argumentos iguais a algum já existente no array
			for(int i=0; i<gfMethods.size(); i++){
				if(newGFMethod.getArgsString().equals(gfMethods.get(i).getArgsString())){
					gfMethods.set(i, newGFMethod);
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
	 * Retorna uma lista com os metodos auxiliares do tipo before aplicaveis aos argumentos passados na chamada da função.
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
	 * Retorna uma lista com os metodos auxiliares do tipo before aplicaveis aos argumentos passados na chamada da função.
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
	 * Retorna uma lista com os metodos aplicaveis aos argumentos passados na chamada da função.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	private ArrayList<GFMethod> getPrimaryAplicableMethods(ArrayList<Class> argsType) throws GenericFunctionIllegalArgumentException{
		//Se nao houver nenhum metodo aplicavel retornamos uma mensagem de erro (System.err) ou criamos uma excepçao e imprimimos o trace
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
		if(gfMethods.size() == 0) {
			return null;
		}
		// Se o gfMethods só tiver 1 metodo entao será esse o efectivo
		if(gfMethods.size() == 1) {
			ArrayList<GFMethod> retOrdered= new ArrayList<GFMethod>();
			retOrdered.add(gfMethods.get(0));
			return retOrdered;
		}
		return GenericFunctionUtil.sortMethods(0, gfMethods, argsType);
	}
	
	/**
	 * Retorna uma lista de GFMethods ordenada do menos especifico para o mais especifico.
	 * Retorna null se a lista gfMethods recebida estiver vazia
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	private ArrayList<GFMethod> sortAfterAplicableMethods(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		ArrayList<GFMethod> retOrdered= new ArrayList<GFMethod>();
		
		if(gfMethods.size() == 0) {
			return null;
		}
		// Se o gfMethods só tiver 1 metodo entao será esse o efectivo
		if(gfMethods.size() == 1) {
			retOrdered.add(gfMethods.get(0));
			return retOrdered;
		}
		retOrdered=GenericFunctionUtil.sortMethods(0, gfMethods, argsType);
		Collections.reverse(retOrdered);
		return retOrdered;
	}
	
	/**
	 * Retorna o metodo primário mais especifico
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	private GFMethod getEffectivePrimaryMethod(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		// Se o gfMethods só tiver 1 metodo entao será esse o efectivo
		if(gfMethods.size() == 1) {
			return gfMethods.get(0);
		}

		return GenericFunctionUtil.sortMethods(0, gfMethods, argsType).get(0);
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
		return  GenericFunctionUtil.printPrettyResult(primaryReturnValue);
	}
	
}
	
	
	
