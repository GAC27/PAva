package ist.meic.pa.GenericFunctions.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ist.meic.pa.GenericFunctions.*;

public class GenericFunctionUtil {

	public static String printPrettyResult(Object result) {
		String prettyResult = "";
		if(result.getClass().isArray()) {
			prettyResult += "[";
			Object[] resultArray = (Object[]) result;
			for(int i = 0 ; i < resultArray.length ; i++){
				prettyResult += printPrettyResult(resultArray[i]);
				if(i < resultArray.length - 1){
					prettyResult += ", ";
				}
			}
			prettyResult += "]";
		}else{
			prettyResult = result.toString();
		}
		return prettyResult;
	}
	
	
}
