package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ist.meic.pa.GenericFunctions.Exceptions.GenericFunctionIllegalArgumentException;

public class AfterFunctionHandler extends FunctionHandler{
	/**
	 * Mapa de GFMethods auxiliares(after) desta função genérica
	 * Chave é o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsAfter = new HashMap<Integer,ArrayList<GFMethod>>();
	
	/**
	 * Adiciona um novo metodo auxiliario a ser executado depois do primário 
	 * Verifica se já existe um metodo com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
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
	
	/**
	 * Retorna uma lista com os metodos auxiliares do tipo before aplicaveis aos argumentos passados na chamada da função.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	public ArrayList<GFMethod> getAfterAplicableMethods(ArrayList<Class> argsType){
		ArrayList<GFMethod> gfmAfter=gfMethodsAfter.get(argsType.size());
		if(gfmAfter!=null){
			ArrayList<GFMethod> aplicableGFMethods= getAplicableMethods(argsType, gfmAfter);
			
			return aplicableGFMethods;
		}
		
		else{
			return new ArrayList<GFMethod>();
		}
	}

	

	/**
	 * Retorna uma lista de GFMethods ordenada do menos especifico para o mais especifico.
	 * Retorna null se a lista gfMethods recebida estiver vazia
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	public ArrayList<GFMethod> sortAfterAplicableMethods(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		ArrayList<GFMethod> retOrdered= new ArrayList<GFMethod>();
		
		if(gfMethods.size() == 0) {
			return null;
		}
		// Se o gfMethods só tiver 1 metodo entao será esse o efectivo
		if(gfMethods.size() == 1) {
			retOrdered.add(gfMethods.get(0));
			return retOrdered;
		}
		retOrdered=sortMethods(0, gfMethods, argsType);
		Collections.reverse(retOrdered);
		return retOrdered;
	}
}
