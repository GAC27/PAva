package ist.meic.pa.GenericFunctionsExtended;

import java.util.List;

import ist.meic.pa.GenericFunctions.GFMethod;

public class EffectiveMethodCacheItem {
	
	private List<GFMethod> beforeMethods;
	private GFMethod primary;
	private List<GFMethod> afterMethods;
	private Object[] args;
	
	public EffectiveMethodCacheItem(List<GFMethod> beforeMethods, GFMethod primary, List<GFMethod> afterMethods, Object[] args) {
		this.beforeMethods = beforeMethods;
		this.primary = primary;
		this.afterMethods = afterMethods;
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
	
	

}
