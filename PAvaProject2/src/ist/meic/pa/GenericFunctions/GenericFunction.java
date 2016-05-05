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


public class GenericFunction {
	/**
	 * Classe que trata dos metodos primarios da função generica
	 */
	PrimaryFunctionHandler gfMethodsPrimary = new PrimaryFunctionHandler();
	
	/**
	 * Classe que trata dos metodos before da função generica
	 */
	BeforeFunctionHandler gfMethodsBefore = new BeforeFunctionHandler();
		
	/**
	 * Classe que trata dos metodos after da função generica
	 */
	AfterFunctionHandler gfMethodsAfter = new AfterFunctionHandler();

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
	
	public void addMethod(GFMethod newGFMethod){
		gfMethodsPrimary.addMethod(newGFMethod);
	}
	
	public void addBeforeMethod(GFMethod newGFMethod){
		gfMethodsBefore.addBeforeMethod(newGFMethod);
	}
	
	public void addAfterMethod(GFMethod newGFMethod){
		gfMethodsAfter.addAfterMethod(newGFMethod);
	}

	

	
	public Object call(Object... args){
		ArrayList<Class> argsType = new ArrayList<Class>();

		for(Object o: args){
			//Ver tipo de cada argumento e meter ordenados os tipos dos args para dentro do Array List.
			argsType.add(o.getClass());
		}

		ArrayList<GFMethod> beforeAMethods=gfMethodsBefore.getBeforeAplicableMethods(argsType);
		ArrayList<GFMethod> afterAMethods=gfMethodsAfter.getAfterAplicableMethods(argsType);
		try{
		
			ArrayList<GFMethod> primaryAMethods=gfMethodsPrimary.getPrimaryAplicableMethods(argsType);
			beforeAMethods= gfMethodsBefore.sortBeforeAplicableMethods(argsType,beforeAMethods);
			afterAMethods= gfMethodsAfter.sortAfterAplicableMethods(argsType,afterAMethods);
			GFMethod primaryEffectiveMethod= gfMethodsPrimary.getEffectivePrimaryMethod(argsType,primaryAMethods);
			
			return callEffectiveMethod(beforeAMethods,primaryEffectiveMethod, afterAMethods, args);
		
		}
		catch(GenericFunctionIllegalArgumentException e){
			System.err.print("Exception in thread "+ Thread.currentThread().getName()+ " java.lang.IllegalArgumentException:\n\t" 
				+"No methods for generic function" + GFName + "with args [");
			for(Object o: args){
				System.err.print(o + ",");
			}
			System.err.print(" of classes [");
			int i=0;
			for(Object o: args){
				System.err.print(o.getClass());
				if(i < args.length - 1){
					System.err.print(", ");
				}
				i++;
			}
			System.err.println("]");
			return null;
		}
		
	
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
	
	
	
