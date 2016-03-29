package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BoxingProfilerExtended {
	private static final String INVALID_ACTION_TYPE = "NON_BOXING_FUNCTION";
	private static final String BOXING_ACTION_TYPE = "boxed";
	private static final String UNBOXING_ACTION_TYPE = "unboxed";
	static java.util.TreeMap replacementData = new java.util.TreeMap();
	
	
	/**
	 * 
	 * Function responsible for inserting the data in the TreeMap of the profiled program
	 * @param method
	 * @param type
	 * @param action
	 */
	private static final String insertFunction = "static void insertData(String method, String type, String action);";
	private static final String insertFunctionBody = "{"			
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
	/**
	 * Function to print the results
	 */
	public static final String printFunction ="static void printData();";
	public static final String printFunctionBody ="{"
			+"		java.util.Set methods= data.keySet();"
			+"		for(java.util.Iterator i=methods.iterator(); i.hasNext();){"
			+"			String method = (String) i.next();"
			+"			java.util.TreeMap mapTypes = (java.util.TreeMap) data.get(method);"
			+"			java.util.Set keyTypes=mapTypes.keySet();"
			+"			for(java.util.Iterator v=keyTypes.iterator(); v.hasNext();){"
			+"				String type = (String) v.next();"
			+"				java.util.TreeMap mapAction = (java.util.TreeMap) mapTypes.get(type);"
			+"				java.util.Set keyAction=mapAction.keySet();"
			+"				for(java.util.Iterator j=keyAction.iterator(); j.hasNext();){"
			+"					String action = (String) j.next();"
			+"					System.err.println(method + \" \" + action + \" \" + (Integer) mapAction.get(action) + \" \" + type);"
			+"				}	"			
			+"			}"
			+"		}"
			+"	}";
	
	
	/**
	 * 
	 * Function responsible for inserting the data in the TreeMap of boxing operations replaced
	 * @param method
	 * @param type
	 * @param action
	 */
	static void insertReplacementData(String method, String type, String action)
	{
			java.util.TreeMap types;
			Integer count;
			java.util.TreeMap methods = (java.util.TreeMap) replacementData.get(method);
			if(methods == null){
				methods=new java.util.TreeMap();
				types = new java.util.TreeMap();
				types.put(action, new Integer(1));
				methods.put(type, types);
				replacementData.put(method, methods);
				return;
			}
			types = (java.util.TreeMap) methods.get(type);
				if(types == null){
					types = new java.util.TreeMap();
					types.put(action, new Integer(1));
					methods.put(type, types);
					return;
				}
				count =  (Integer) types.get(action);
				if(count == null){
					types.put(action, new Integer(1));
					return;
				}
				count = new Integer(count.intValue() + 1);
				types.put(action, count);
			}
	/**
	 * Function to print the results of the replacement
	 */
	static void printReplacementData(){
		java.util.Set methods= replacementData.keySet();
		
		for(java.util.Iterator i=methods.iterator(); i.hasNext();){
			String method = (String) i.next();
			java.util.TreeMap mapTypes = (java.util.TreeMap) replacementData.get(method);	
			java.util.Set keyTypes=mapTypes.keySet();
			
			for(java.util.Iterator v=keyTypes.iterator(); v.hasNext();){
				String type = (String) v.next();
				java.util.TreeMap mapAction = (java.util.TreeMap) mapTypes.get(type);	
				java.util.Set keyAction=mapAction.keySet();
				
				for(java.util.Iterator j=keyAction.iterator(); j.hasNext();){
					String action = (String) j.next();
					System.out.println("Replaced boxing operation in "+ method+ " " + (Integer) mapAction.get(action) + " times for type: " + type);
				}				
			}
		}
		
	}
	
	
	/**
	 * Responsible for determining the action type of a method passed as an argument.
	 * If function is a boxing operation returns BOXING_ACTION_TYPE;
	 * If function is a unboxing operation returns UNBOXING_ACTION_TYPE;
	 * Else returns INVALID_ACTION_TYPE.
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	private static String getAction(String clazz, String methodName) {
		switch (clazz) {
			case "java.lang.Integer":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("intValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Character":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("charValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Boolean":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("booleanValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Float":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("floatValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Double":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("doubleValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Byte":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("byteValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Short":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("shortValue")) {
					return UNBOXING_ACTION_TYPE;
				}
			case "java.lang.Long":
				if (methodName.equals("valueOf")) {
					return BOXING_ACTION_TYPE;
				} else if (methodName.equals("longValue")) {
					return UNBOXING_ACTION_TYPE;
				}
		}
		return INVALID_ACTION_TYPE;
	}

	/**
	 * Replaces all calls to boxing method for the respective creation of a reference object or
	 * Injects a call to the insertData method after the execution of the method passed as an argument
	 * @param ctMethod
	 * @throws CannotCompileException
	 */
	private static void injectInsertDataAndReplaceBoxing(CtMethod ctMethod) throws CannotCompileException {
		ctMethod.instrument(new ExprEditor() {
			public void edit(final MethodCall methodCall) throws CannotCompileException {
				// Ver se a funçao chamada é uma funçao que faz boxing ou unboxing
				String methodBoxingType = getAction(methodCall.getClassName(), methodCall.getMethodName());
				String className=methodCall.getClassName();

				if (!methodBoxingType.equals(INVALID_ACTION_TYPE)) {
					if(methodBoxingType.equals(BOXING_ACTION_TYPE)){
						methodCall.replace("{$_ = new "+className+"($$);}");
						insertReplacementData(ctMethod.getLongName() , className, methodBoxingType);
					}
					else {
						methodCall.replace("{$_ = $proceed($$);" + "insertData(\"" + ctMethod.getLongName() + "\",\"" + className
							+ "\",\"" + methodBoxingType + "\");}");
					}
				}
			}
		});
	}
	
	
	/**
	 * Injects the code for the TreeMap, insertData and printData functions;
	 * Searches for unboxing operations in methods and calls function injectInsertData with that method passed as an argument;
	 * Searches for boxing operations in methods and replaces operation for the respective creation a reference object
	 * @param ctClass
	 */
	private static void intrumentClass(CtClass ctClass) {
		try {
			CtField ctField = CtField.make("static java.util.TreeMap data = new java.util.TreeMap();", ctClass);
			ctClass.addField(ctField);

			CtMethod ctMethodID = CtMethod.make(insertFunction, ctClass);
			ctClass.addMethod(ctMethodID);
			CtMethod ctMethodPD = CtMethod.make(printFunction, ctClass);
			ctClass.addMethod(ctMethodPD);

			for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
				injectInsertDataAndReplaceBoxing(ctMethod);

				if (ctMethod.getName().equals("main")) {
					ctMethod.insertAfter("printData();");
				}
			}
			
			ctMethodID = ctClass.getDeclaredMethod("insertData");
			ctMethodID.setBody(insertFunctionBody);

			ctMethodPD = ctClass.getDeclaredMethod("printData");
			ctMethodPD.setBody(printFunctionBody);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try{

			ClassPool cp = ClassPool.getDefault();
			cp.appendClassPath(".");
			CtClass ctClass = cp.get(args[0]);
			intrumentClass(ctClass);
			
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
	
			Class clazz = ctClass.toClass();
			clazz.getMethod("main", args.getClass()).invoke(null,new Object[]{restArgs});
			printReplacementData();
		
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
}
