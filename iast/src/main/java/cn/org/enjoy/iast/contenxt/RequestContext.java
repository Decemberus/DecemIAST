package cn.org.enjoy.iast.contenxt;

import cn.org.enjoy.iast.http.IASTServletRequest;
import cn.org.enjoy.iast.http.IASTServletResponse;


public class RequestContext {

	private static final ThreadLocal<HttpRequestContext> HTTP_REQUEST_CONTEXT_THREAD_LOCAL = new ThreadLocal<HttpRequestContext>();

	public static HttpRequestContext getHttpRequestContextThreadLocal() {
		return HTTP_REQUEST_CONTEXT_THREAD_LOCAL.get();
	}

	public static void setHttpRequestContextThreadLocal(IASTServletRequest request, IASTServletResponse response) {
		IASTServletRequest  iastServletRequest  = request;
		IASTServletResponse iastServletResponse = response;
		HTTP_REQUEST_CONTEXT_THREAD_LOCAL.set(new HttpRequestContext(iastServletRequest, iastServletResponse));
	}

}