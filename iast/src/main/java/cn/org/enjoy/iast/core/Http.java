package cn.org.enjoy.iast.core;

import cn.org.enjoy.iast.contenxt.HttpRequestContext;
import cn.org.enjoy.iast.contenxt.RequestContext;
import cn.org.enjoy.iast.http.IASTServletRequest;
import cn.org.enjoy.iast.http.IASTServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class Http {

	/**
	 * 在HTTP方法结束前调用，主要是对存在当前上下文的结果进行可视化打印输出
	 */
	public static void leaveHttp() {
		IASTServletRequest request = RequestContext.getHttpRequestContextThreadLocal()
				.getServletRequest();
		if (!request.getRequestURI().contains("favicon.ico")&&!request.getRequestURI().equals("/")) {
			//防止一开始初始化的时候就各种打印信息
			System.out.printf("URL            : %s \n", request.getRequestURL().toString());
			System.out.printf("URI            : %s \n", request.getRequestURI().toString());
			System.out.printf("QueryString    : %s \n", request.getQueryString().toString());
			System.out.printf("HTTP Method    : %s \n", request.getMethod());
		}
		RequestContext.getHttpRequestContextThreadLocal().getCallChain().forEach(item -> {
			if (item.getChainType().contains("leave")) {
				String returnData = null;
				if (item.getReturnObject().getClass().equals(byte[].class)) {
					returnData = new String((byte[]) item.getReturnObject());
				} else if (item.getReturnObject().getClass().equals(char[].class)) {
					returnData = new String((char[]) item.getReturnObject());
				} else {
					returnData = item.getReturnObject().toString();
				}

				System.out
						.printf("Type: %s CALL Method Name: %s CALL Method Return: %s \n", item.getChainType(),
								item.getJavaClassName() +"#"+ item.getJavaMethodName(), returnData);
			} else {
				System.out
						.printf("Type: %s CALL Method Name: %s CALL Method Args: %s \n", item.getChainType(),
								item.getJavaClassName() +"#"+ item.getJavaMethodName(),
								Arrays.asList(item.getArgumentArray()));
			}

			// 如果是Sink类型，则还会输出调用栈信息
			if (item.getChainType().contains("Sink")) {
				FileWriter writer = null;
				try {
					writer = new FileWriter("D:\\Code_Project\\Java\\DecemIAST\\iast\\src\\main\\java\\cn\\org\\enjoy\\result\\callstack.txt", true); // true表示追加到文件末尾
					int depth = 1;
					StackTraceElement[] elements = item.getStackTraceElement();

					for (StackTraceElement element : elements) {
						if (element.getClassName().contains("cn.org.javaweb.iast") ||
								element.getClassName().contains("java.lang.Thread") ||
								element.getClassName().contains("sun.reflect.GeneratedMethodAccessor") ||
								element.getMethodName().equals("invoke")) {
							continue;
						}
						String stackTraceLine = String.format("%" + depth + "s", "") + element.toString() + "\n";
						writer.write(stackTraceLine);
						System.out.println(stackTraceLine);
						depth++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	/**
	 * 判断当前上下文是否缓存了Request请求
	 *
	 * @return boolean
	 */
	public static boolean haveEnterHttp() {
		HttpRequestContext context = RequestContext.getHttpRequestContextThreadLocal();
		return context != null;
	}


	/**
	 * 在HTTP方法进入的时候调用，如果当前上下文为空，
	 * 就将`HttpServletRequest`和`HttpServletResponse`对象存到当前线程的上下文中
	 * 方便后续对数据的调取使用。
	 */
	public static void enterHttp(Object[] objects) {
		if (!haveEnterHttp()) {
			IASTServletRequest  request  = new IASTServletRequest(objects[0]);
			IASTServletResponse response = new IASTServletResponse(objects[1]);

			RequestContext.setHttpRequestContextThreadLocal(request, response);
		}
	}
}
