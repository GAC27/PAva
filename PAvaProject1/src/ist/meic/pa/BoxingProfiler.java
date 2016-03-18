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
			+"		java.util.TreeMap methods = (java.util.TreeMap)data.get($1);"
			+"		if(methods == null){"
			+"			((java.util.TreeMap)((java.util.TreeMap)data.put($1, new java.util.TreeMap())).put($2, new java.util.TreeMap())).put($2, new Integer(1));"
			+"			return;"
			+"		}"
			+"		java.util.TreeMap types = (java.util.TreeMap)methods.get($2);"
			+"		if(types == null){"
			+"			((java.util.TreeMap)methods.put($2, new java.util.TreeMap())).put($2, new Integer(1));"
			+"			return;"
			+"		}"
			+"		Integer count = (Integer)types.get($2);"
			+"		if(count == null){"
			+"			types.put($2, new Integer(1));"
			+"			return;"
			+"		}"
			+"		types.put($2, count + 1);"
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
		
		return "BOX";
	}

	
	public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		ClassPool cp = ClassPool.getDefault();
		System.out.println(args[0]);
		
		
		CtClass ct = cp.get(args[0]);
		
		CtField ctField = CtField.make("static java.util.TreeMap data = new java.util.TreeMap();",ct);
		ct.addField(ctField);
//		CtField ctField = CtField.make("static TreeMap<String, TreeMap<String,TreeMap<String,Integer>>> data = new TreeMap<String, TreeMap<String,TreeMap<String,Integer>>>();",ct);

		
		CtMethod ctMethod = CtMethod.make(insertData2, ct);
		ct.addMethod(ctMethod);

		
		for(CtMethod m2 : ct.getDeclaredMethods()){
//			System.out.println(m2);
		m2.instrument(new ExprEditor() {
    	    public void edit(final MethodCall m) throws CannotCompileException {
//    	        System.out.println(m2.getDeclaringClass().getName() + " : " + m2.getMethodInfo().getName()   + " : " + m.getClassName() + " : " +  m.getMethodName() + " : " + m2.getLongName());
    	        m.replace("{$_ = $proceed($$); "
    	        		+ "System.out.println(\"Executed in ms: \" + \"" + m2.getDeclaringClass().getName() + " : " + m2.getMethodInfo().getName()   + " : " + m.getClassName() + " : " +  m.getMethodName() + " : " + m2.getLongName() +"\");"
    	        				+ "insertData(\""+ m2.getLongName()+"\",\""+ m.getClassName() + "\",\"" + getAction(m.getClassName(),m2.getMethodInfo().getName()) +"\");}");
    	    }
		});
		}
	
		ctMethod = ct.getDeclaredMethod("insertData");
		ctMethod.setBody(insertData);		//compila o codigo que vai no src e substitui pelo corpo do metodo original
//		ct.addMethod(ctMethod);			//Não é necessario fazer addMethod porque o metodo já existe e portanto ao fazer addMethod esta-se a escrever duas vezes o mesmo metodo no bytecode
		
		
//		ct.getDeclaredMethod("printSum").insertAfter("{ System.err.println(\"BOXING3\"); }");
//		addTiming(ct,"printSum");
		
		
		Class clazz = ct.toClass();
		
		clazz.getMethod("main", String[].class).invoke(null,(Object) new String[0]);
		
	}
	
}
