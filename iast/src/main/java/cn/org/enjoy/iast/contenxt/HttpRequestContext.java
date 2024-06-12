package cn.org.enjoy.iast.contenxt;

import cn.org.enjoy.iast.http.IASTServletRequest;
import cn.org.enjoy.iast.http.IASTServletResponse;

import java.util.LinkedList;


public class HttpRequestContext {

	private final IASTServletRequest servletRequest;

	private final IASTServletResponse servletResponse;

	private LinkedList<CallChain> callChain;

	public HttpRequestContext(IASTServletRequest servletRequest, IASTServletResponse servletResponse) {
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
		this.callChain = new LinkedList<>();
	}


	public IASTServletRequest getServletRequest() {
		return servletRequest;
	}

	public LinkedList<CallChain> getCallChain() {
		return callChain;
	}

	public void addCallChain(CallChain callChain) {
		this.callChain.add(callChain);
	}

}
