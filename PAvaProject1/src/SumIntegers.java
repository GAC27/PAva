import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class SumIntegers {
	
//	static java.util.TreeMap data = new java.util.TreeMap();
//	
//	static void insertData(String method, String type, String action){
//		java.util.TreeMap types;
//		Integer count;
//		java.util.TreeMap methods = (java.util.TreeMap) data.get(method);
//		if(methods == null){
//			methods=new java.util.TreeMap();
//			types = new java.util.TreeMap();
//			types.put(action, new Integer(1));
//			methods.put(type, types);
//			data.put(method, methods);
//			return;
//		}
//		types = (java.util.TreeMap)methods.get(type);
//		if(types == null){
//			types = new java.util.TreeMap();
//			types.put(action, new Integer(1));
//			methods.put(type, types);
//			return;
//		}
//		count = (Integer)types.get(action);
//		if(count == null){
//			types.put(action, new Integer(1));
//			return;
//		}
//		types.put(action, count + new Integer(1));
//	}
	
//	static void printData(){
//		java.util.Set methods= data.keySet();
//		
//		for(java.util.Iterator i=methods.iterator(); i.hasNext();){
//			String method = (String) i.next();
//			java.util.TreeMap mapTypes = (java.util.TreeMap) data.get(method);	
//			java.util.Set keyTypes=mapTypes.keySet();
//			
//			for(java.util.Iterator v=keyTypes.iterator(); v.hasNext();){
//				String type = (String) v.next();
//				java.util.TreeMap mapAction = (java.util.TreeMap) mapTypes.get(type);	
//				java.util.Set keyAction=mapAction.keySet();
//				
//				for(java.util.Iterator j=keyAction.iterator(); j.hasNext();){
//					String action = (String) j.next();
//					System.out.println(method + " " + action + " " + (Integer) mapAction.get(action) + " " + type);
//				}				
//			}
//		}
//		
//	}
	
	private static long sumOfIntegerUptoN(Integer n) {
		Long sum = 0L;
//		insertData("SumIntegers.sumOfIntegerUptoN(java.lang.Integer)","java.lang.Long","boxed");
		for (int i = 0; i < n; i++) {
//			insertData("SumIntegers.sumOfIntegerUptoN(java.lang.Integer)","java.lang.Integer","unboxed");
//			insertData("SumIntegers.sumOfIntegerUptoN(java.lang.Integer)","java.lang.Long","unboxed");
//			insertData("SumIntegers.sumOfIntegerUptoN(java.lang.Integer)","java.lang.Long","boxed");
			sum += i;
//			System.out.println("iteration:" + i);
		}

		return sum;
	}

	private static long printSum(Long n) {
		System.out.println("Sum: " + n);
		return n;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
//		insertData("SumIntegers.main(java.lang.String[]","java.lang.Integer","boxed");
		//int value =Integer.parseInt(args[0]);
		
		printSum(sumOfIntegerUptoN(10));
		
		//System.out.println("Test: " + args[1]);
//		if(true){
//			return;
//		}
		Integer i=1;
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
//		printData();
//		System.out.println(new Integer(1) + new Integer(2));
	}
	
	
		
}