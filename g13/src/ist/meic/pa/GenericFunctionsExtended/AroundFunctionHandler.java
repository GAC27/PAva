package ist.meic.pa.GenericFunctionsExtended;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ist.meic.pa.GenericFunctions.Exceptions.GenericFunctionIllegalArgumentException;

public class AroundFunctionHandler extends FunctionHandler {

	/**
	 * Mapa de GFMethods auxiliares(around) desta fun��o gen�rica
	 * Chave � o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsAround = new HashMap<Integer,ArrayList<GFMethod>>();
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado antes do prim�rio 
	 * Verifica se j� existe um metodo com o numero e tipos de argumentos iguais. Se existir n�o adiciona o metodo, se n�o existir adiciona
	 * @param newGFMethod
	 */
	public void addAroundMethod(GFMethod newGFMethod){
		ArrayList<GFMethod> gfMethods=gfMethodsAround.get(newGFMethod.getArgs().size());
		if(gfMethods == null){
			gfMethods=new ArrayList<GFMethod>();
			gfMethods.add(newGFMethod);
			gfMethodsAround.put(newGFMethod.getArgs().size(), gfMethods) ;
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
	 * Retorna uma lista com os metodos auxiliares do tipo around aplicaveis aos argumentos passados na chamada da fun��o.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	public ArrayList<GFMethod> getAroundAplicableMethods(ArrayList<Class> argsType){
		ArrayList<GFMethod> gfmAround=gfMethodsAround.get(argsType.size());
		if(gfmAround!=null){
			ArrayList<GFMethod> aplicableGFMethods= getAplicableMethods(argsType, gfmAround);

			return aplicableGFMethods;
		}
		
		else{
			return new ArrayList<GFMethod>();
		}
		
	}
	
	

	/**
	 * Retorna os metodos around ordenados por especificidade.
	 * Se n�o existir um metodo around retorna null.
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	public ArrayList<GFMethod> sortAroundAplicableMethods(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		if(gfMethods.size() == 0) {
			return null;
		}
		// Se o gfMethods s� tiver 1 metodo entao ser� esse o efectivo
		if(gfMethods.size() == 1) {
			ArrayList<GFMethod> retOrdered= new ArrayList<GFMethod>();
			retOrdered.add(gfMethods.get(0));
			return retOrdered;
		}
		return sortMethods(0, gfMethods, argsType);
	}
}


