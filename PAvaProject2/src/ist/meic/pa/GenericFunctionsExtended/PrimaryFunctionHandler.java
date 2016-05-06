package ist.meic.pa.GenericFunctionsExtended;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ist.meic.pa.GenericFunctions.Exceptions.GenericFunctionIllegalArgumentException;

public class PrimaryFunctionHandler extends FunctionHandler{
	
	/**
	 * Mapa de GFMethods primários desta função genérica 
	 * Chave é o numero de argumentos
	 */
	Map<Integer,ArrayList<GFMethod>> gfMethodsPrimary = new HashMap<Integer,ArrayList<GFMethod>>();
	
	
	/**
	 * Adiciona um novo metodo primário 
	 * Verifica se já existe um metodo com o numero e tipos de argumentos iguais. Se existir não adiciona o metodo, se não existir adiciona
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
	 * Retorna uma lista com os metodos aplicaveis aos argumentos passados na chamada da função.
	 * @param argsType
	 * @return
	 * @throws GenericFunctionIllegalArgumentException 
	 */
	public ArrayList<GFMethod> getPrimaryAplicableMethods(ArrayList<Class> argsType) throws GenericFunctionIllegalArgumentException{
		//Se nao houver nenhum metodo aplicavel retornamos uma mensagem de erro (System.err) ou criamos uma excepçao e imprimimos o trace
		ArrayList<GFMethod> gfmPrimary=gfMethodsPrimary.get(argsType.size());
		if(gfmPrimary!=null){
			ArrayList<GFMethod> aplicableGFMethods= getAplicableMethods(argsType, gfmPrimary);
			
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
	 * Retorna o metodo primário mais especifico
	 * @param argsType
	 * @param gfMethods
	 * @return
	 */
	public GFMethod getEffectivePrimaryMethod(ArrayList<Class> argsType,ArrayList<GFMethod> gfMethods){
		// Se o gfMethods só tiver 1 metodo entao será esse o efectivo
		if(gfMethods.size() == 1) {
			return gfMethods.get(0);
		}

		return sortMethods(0, gfMethods, argsType).get(0);
	}
}
