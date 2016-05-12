package ist.meic.pa.GenericFunctionsExtended;

import java.util.List;

import ist.meic.pa.GenericFunctionsExtended.GFMethod;

public class EffectiveMethodCacheItem {
	
	private List<GFMethod> beforeMethods;
	private GFMethod primary;
	private List<GFMethod> afterMethods;
	private List<GFMethod> aroundMethods;
	private Object[] args;
	
	public EffectiveMethodCacheItem(List<GFMethod> beforeMethods, GFMethod primary, List<GFMethod> afterMethods,List<GFMethod> aroundMethods, Object[] args) {
		this.beforeMethods = beforeMethods;
		this.primary = primary;
		this.afterMethods = afterMethods;
		this.aroundMethods = aroundMethods;
		this.args = args;
	}
	
	public List<GFMethod> getBeforeMethods() {
		return beforeMethods;
	}
	public void setBeforeMethods(List<GFMethod> beforeMethods) {
		this.beforeMethods = beforeMethods;
	}
	public GFMethod getPrimary() {
		return primary;
	}
	public void setPrimary(GFMethod primary) {
		this.primary = primary;
	}
	public List<GFMethod> getAfterMethods() {
		return afterMethods;
	}
	public void setAfterMethods(List<GFMethod> afterMethods) {
		this.afterMethods = afterMethods;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}

	public List<GFMethod> getAroundMethods() {
		return aroundMethods;
	}

	public void setAroundMethods(List<GFMethod> aroundMethods) {
		this.aroundMethods = aroundMethods;
	}
	
}
