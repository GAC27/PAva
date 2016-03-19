package ist.meic.pa;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BoxingProfiler {
	private static String insertData2 = "static void insertData(String method, String type, String action);";
//	private static String body ="{}";
	private static String insertData = "{"			
			+"		java.util.TreeMap types;"
			+"		Integer count;"
			+"		java.util.TreeMap methods = (java.util.TreeMap) data.get($1);"
			+"		if(methods == null){"
			+"			methods=new java.util.TreeMap();"
			+"			types = new java.util.TreeMap();"
			+"			types.put($3, new Integer(1));"
			+"			methods.put($2, types);"
			+"			data.put($1, methods);"
			+"			return;"
			+"		}"
			+"		types = (java.util.TreeMap) methods.get($2);"
			+"		if(types == null){"
			+"			types = new java.util.TreeMap();"
			+"			types.put($3, new Integer(1));"
			+"			methods.put($2, types);"
			+"			return;"
			+"		}"
			+"		count =  (Integer) types.get($3);"
			+"		if(count == null){"
			+"			types.put($3, new Integer(1));"
			+"			return;"
			+"		}"
			+"		count = new Integer(count.intValue() + 1);"
			+"		types.put($3, count);"
			+"	}";
	public static String printData2 ="static void printData();";
	public static String printData ="{"
			+"		java.util.Set methods= data.keySet();"
			+"		for(java.util.Iterator i=methods.iterator(); i.hasNext();){"
			+"			String method = (String) i.next();"
			+"			java.util.TreeMap mapTypes = (java.util.TreeMap) data.get(method);	"
			+"			java.util.Set keyTypes=mapTypes.keySet();"
			+"			for(java.util.Iterator v=keyTypes.iterator(); v.hasNext();){"
			+"				String type = (String) v.next();"
			+"				java.util.TreeMap mapAction = (java.util.TreeMap) mapTypes.get(type);	"
			+"				java.util.Set keyAction=mapAction.keySet();"
			+"				for(java.util.Iterator j=keyAction.iterator(); j.hasNext();){"
			+"					String action = (String) j.next();"
			+"					System.out.println(method + \" \" + action + \" \" + (Integer) mapAction.get(action) + \" \" + type);"
			+"				}	"			
			+"			}"
			+"		}"
			+"	}";
	
		
	private static String getAction(String clazz, String methodName){
		switch( clazz ) {
		case "java.lang.Integer":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("intValue")){
				return "unboxed";
			}
		case "java.lang.Boolean":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("booleanValue")){
				return "unboxed";
			}
		case "java.lang.Float":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("floatValue")){
				return "unboxed";
			}
		case "java.lang.Double":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("doubleValue")){
				return "unboxed";
			}
		case "java.lang.Byte":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("byteValue")){
				return "unboxed";
			}
		case "java.lang.Short":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("shortValue")){
				return "unboxed";
			}
		case "java.lang.Long":
			if (methodName.equals("valueOf")) {
				return "boxed";
			}else if(methodName.equals("longValue")){
				return "unboxed";
			}
		}
		
		return "NON_BOXING_FUNCTION";
	}

//	public static void injectInsertData(CtMethod m2) throws CannotCompileException{
//		m2.instrument(new ExprEditor() {
//    	    public void edit(final MethodCall m) throws CannotCompileException {   
//    	        //Ver se a funçao chamada é uma funçao que faz boxing ou unboxing
//    	    	String methodBoxingType=getAction(m.getClassName(),m.getMethodName());
//    	    	if(!methodBoxingType.equals("NON_BOXING_FUNCTION")){
//    	    		m.replace("{$_ = $proceed($$);"
//    	        			+ "insertData(\""+ m2.getLongName()+"\",\""+ m.getClassName() + "\",\"" + methodBoxingType +"\");"
//    	        			+ "System.out.println(\":::data\"+data+\":::\");}");
//    	    		
//    	    		  		
//    	    	}
//    	    }
//		});
//	}
	
	public static void injectInsertDataAndReturnData(CtMethod m2) throws CannotCompileException{
		
		m2.instrument(new ExprEditor() {
    	    public void edit(final MethodCall m) throws CannotCompileException {   
    	        //Ver se a funçao chamada é uma funçao que faz boxing ou unboxing
    	    	String methodBoxingType=getAction(m.getClassName(),m.getMethodName());
    	    	
    	    	if(!methodBoxingType.equals("NON_BOXING_FUNCTION")){
    	    		m.replace("{$_ = $proceed($$);"+ "insertData(\""+ m2.getLongName()+"\",\""+ m.getClassName() 
    	    			+ "\",\"" + methodBoxingType +"\");"
    	    			+ "System.out.println(\":::data\"+data+\":::\");}");	  		
    	    	}
    	    	else{
        	    	//Se o metodo chamado for um return então injectamos codigo antes para ele retornar o data antes de terminar o programa
    	    		System.out.println(m.getMethodName());
    	    		if(m.getMethodName().equals("return") && m2.getName().equals("main")){
    	    			m.replace("{ printData(); $_ = $proceed($$);}");    	    	
    	    		}
    	    	}

    	    }
		});
	}
	
	
	
	public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		ClassPool cp = ClassPool.getDefault();

		CtClass ct = cp.get(args[0]);
		
		CtField ctField = CtField.make("static java.util.TreeMap data = new java.util.TreeMap();",ct);
		ct.addField(ctField);
		
		CtMethod ctMethodID = CtMethod.make(insertData2, ct);
		ct.addMethod(ctMethodID);
		CtMethod ctMethodPD = CtMethod.make(printData2, ct);
		ct.addMethod(ctMethodPD);
		
		for(CtMethod m2 : ct.getDeclaredMethods()){
			injectInsertDataAndReturnData(m2);
		}
		ctMethodID = ct.getDeclaredMethod("insertData");
		ctMethodID.setBody(insertData);	
		
		ctMethodID = ct.getDeclaredMethod("printData");
		ctMethodID.setBody(printData);	

		Class clazz = ct.toClass();
		clazz.getMethod("main", String[].class).invoke(null,(Object) new String[0]);
		
	}
	
}
