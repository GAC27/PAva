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
//		types.put(action, count + 1);
//	}
	
	private static long sumOfIntegerUptoN(Integer n) {
		Long sum = 0L;
		for (int i = 0; i < n; i++) {
			sum += i;
		}
		return sum;
	}

	private static long printSum(Long n) {
		System.out.println("Sum: " + n);
		return n;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		printSum(sumOfIntegerUptoN(10));
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
	}
}