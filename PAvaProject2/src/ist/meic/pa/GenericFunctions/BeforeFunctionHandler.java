package ist.meic.pa.GenericFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ist.meic.pa.GenericFunctions.Exceptions.GenericFunctionIllegalArgumentException;
import ist.meic.pa.GenericFunctions.util.GenericFunctionUtil;

public class BeforeFunctionHandler extends FunctionHandler{

	/**
	 * Mapa de GFMethods auxiliares(before) desta função genérica
	 * Chave é o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsBefore = new HashMap<Integer,ArrayList<GFMethod>>();
	
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
	 * Retorna uma lista com os metodos auxiliares do tipo before aplicaveis aos argumentos passados na chamada da função.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	public ArrayList<GFMethod> getBeforeAplicableMethods(ArrayList<Class> argsType){
		ArrayList<GFMethod> gfmBefore=gfMethodsBefore.get(argsType.size());
		if(gfmBefore!=null){
			ArrayList<GFMethod> aplicableGFMethods= getAplicableMethods(argsType, gfmBefore);

			return aplicableGFMethods;
		}
		
		else{
			return new ArrayList<GFMethod>();
		}
		
	}
	
	

	/**
	 * Retorna uma lista de GFMethods ordenada do mais especifico para o menos especifico.
	 * Retorna null se a lista gfMethods recebida estiver vazia
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	public ArrayList<GFMethod> sortBeforeAplicableMethods(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		if(gfMethods.size() == 0) {
			return null;
		}
		// Se o gfMethods só tiver 1 metodo entao será esse o efectivo
		if(gfMethods.size() == 1) {
			ArrayList<GFMethod> retOrdered= new ArrayList<GFMethod>();
			retOrdered.add(gfMethods.get(0));
			return retOrdered;
		}
		return sortMethods(0, gfMethods, argsType);
	}
}
