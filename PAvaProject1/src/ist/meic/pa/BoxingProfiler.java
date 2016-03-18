package ist.meic.pa;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BoxingProfiler {
	private static String insertData2 = "static void insertData(String method, String type, String action){}";
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
			+"		types = (java.util.TreeMap)methods.get($2);"
			+"		if(types == null){"
			+"			types = new java.util.TreeMap();"
			+"			types.put($3, new Integer(1));"
			+"			methods.put($2, types);"
			+"			return;"
			+"		}"
			+"		count = (Integer)types.get($3);"
			+"		if(count == null){"
			+"			types.put($3, new Integer(1));"
			+"			return;"
			+"		}"
			+"		types.put($3, count + 1);"
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

	
	public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		ClassPool cp = ClassPool.getDefault();
//		System.out.println(args[0]);
		
		
		CtClass ct = cp.get(args[0]);
		
		CtField ctField = CtField.make("static java.util.TreeMap data = new java.util.TreeMap();",ct);
		ct.addField(ctField);
//		CtField ctField = CtField.make("static TreeMap<String, TreeMap<String,TreeMap<String,Integer>>> data = new TreeMap<String, TreeMap<String,TreeMap<String,Integer>>>();",ct);

		
		CtMethod ctMethod = CtMethod.make(insertData2, ct);
		ct.addMethod(ctMethod);

		
		for(CtMethod m2 : ct.getDeclaredMethods()){
		m2.instrument(new ExprEditor() {
    	    public void edit(final MethodCall m) throws CannotCompileException {   
    	        //Ver se a funçao chamada é uma funçao que faz boxing ou unboxing
    	    	String methodBoxingType=getAction(m.getClassName(),m.getMethodName());
    	    	
    	    	if(!methodBoxingType.equals("NON_BOXING_FUNCTION")){
    	    		m.replace("{$_ = $proceed($$); "
//    	        			+ "System.out.println(\""+m.getMethodName()+"\");"
    	        			+ "insertData(\""+ m2.getLongName()+"\",\""+ m.getClassName() + "\",\"" + methodBoxingType +"\");"
    	        			+ "System.out.println(\":::data\"+data+\":::\");}");
    	    	}
    	    		
    	    	
    	    }
		});
		}
	
		ctMethod = ct.getDeclaredMethod("insertData");
		ctMethod.setBody(insertData);		

		Class clazz = ct.toClass();
		
		clazz.getMethod("main", String[].class).invoke(null,(Object) new String[0]);
		
	}
	
}
