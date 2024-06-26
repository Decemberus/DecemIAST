package cn.org.enjoy.iast.core;

import cn.org.enjoy.iast.contenxt.CallChain;
import cn.org.enjoy.iast.contenxt.RequestContext;

import static cn.org.enjoy.iast.core.Http.haveEnterHttp;


public class Sink {

	/**
	 * 进入Sink点
	 *
	 * @param argumentArray  参数数组
	 * @param javaClassName  类名
	 * @param javaMethodName 方法名
	 * @param javaMethodDesc 方法描述符
	 * @param isStatic       是否为静态方法
	 */
	public static void enterSink(Object[] argumentArray,
	                             String javaClassName,
	                             String javaMethodName,
	                             String javaMethodDesc,
	                             boolean isStatic) {
		if (haveEnterHttp()) {
			CallChain callChain = new CallChain();
			callChain.setChainType("enterSink");
			callChain.setArgumentArray(argumentArray);
			callChain.setJavaClassName(javaClassName);
			callChain.setJavaMethodName(javaMethodName);
			callChain.setJavaMethodDesc(javaMethodDesc);
			callChain.setStatic(isStatic);
			callChain.setStackTraceElement(Thread.currentThread().getStackTrace());
			RequestContext.getHttpRequestContextThreadLocal().addCallChain(callChain);
		}
	}
}
