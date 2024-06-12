package cn.org.enjoy.iast.core;

import cn.org.enjoy.iast.contenxt.CallChain;
import cn.org.enjoy.iast.contenxt.RequestContext;


public class Source {

	/**
	 * 进入Source点
	 *
	 * @param argumentArray  参数数组
	 * @param javaClassName  类名
	 * @param javaMethodName 方法名
	 * @param javaMethodDesc 方法描述符
	 * @param isStatic       是否为静态方法
	 */
	public static void enterSource(Object[] argumentArray,
	                               String javaClassName,
	                               String javaMethodName,
	                               String javaMethodDesc,
	                               boolean isStatic) {
		if (Http.haveEnterHttp()) {
			CallChain callChain = new CallChain();
			callChain.setChainType("enterSource");
			callChain.setArgumentArray(argumentArray);
			callChain.setJavaClassName(javaClassName);
			callChain.setJavaMethodName(javaMethodName);
			callChain.setJavaMethodDesc(javaMethodDesc);
			callChain.setStatic(isStatic);
			RequestContext.getHttpRequestContextThreadLocal().addCallChain(callChain);
		}
	}

	/**
	 * 离开Source点
	 *
	 * @param returnObject   返回值对象
	 * @param javaClassName  类名
	 * @param javaMethodName 方法名
	 * @param javaMethodDesc 方法描述符
	 * @param isStatic       是否为静态方法
	 */
	public static void leaveSource(Object returnObject,
	                               String javaClassName,
	                               String javaMethodName,
	                               String javaMethodDesc,
	                               boolean isStatic) {
		if (Http.haveEnterHttp()) {
			CallChain callChain = new CallChain();
			callChain.setChainType("leaveSource");
			callChain.setReturnObject(returnObject);
			callChain.setJavaClassName(javaClassName);
			callChain.setJavaMethodName(javaMethodName);
			callChain.setJavaMethodDesc(javaMethodDesc);
			callChain.setStatic(isStatic);
			RequestContext.getHttpRequestContextThreadLocal().addCallChain(callChain);
		}
	}
}
