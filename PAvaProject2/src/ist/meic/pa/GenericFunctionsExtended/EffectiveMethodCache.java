package ist.meic.pa.GenericFunctionsExtended;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ist.meic.pa.GenericFunctionsExtended.GFMethod;

public class EffectiveMethodCache {

	private Map<Integer, Map<String, EffectiveMethodCacheItem>> cache;
	private static EffectiveMethodCache _instance;

	public EffectiveMethodCache() {
		cache = new HashMap<Integer, Map<String, EffectiveMethodCacheItem>>();
	}
	
	public EffectiveMethodCacheItem getItem(Object... args) {
		int n = args.length;
		String key = generateKey(args);

		for (Object o : args) {
			key += o.getClass();
		}
		
		try {
			return cache.get(n).get(key);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public void setItem(List<GFMethod> beforeMethods, GFMethod primary, List<GFMethod> afterMethods, List<GFMethod> aroundMethods, Object... args) {
		int n = args.length;
		String key = generateKey(args);
		
		if(cache.get(n) == null) {
			cache.put(n, new HashMap<String, EffectiveMethodCacheItem>());
			cache.get(n).put(key, new EffectiveMethodCacheItem(beforeMethods,primary,afterMethods,aroundMethods,args));
		} 
	}

	private String generateKey(Object... args) {
		String key = "";

		for (Object o : args) {
			key += o.getClass();
		}
		return key;
	}
	
	public void invalidateCache(Object... args){
		int n = args.length;
		
		cache.put(n, new HashMap<String, EffectiveMethodCacheItem>());
	}
}
