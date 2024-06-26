package cn.org.enjoy.iast.visitor.handler;

import cn.org.enjoy.iast.visitor.Handler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.reflect.Modifier;



public class HttpClassVisitorHandler implements Handler {

	private static final String METHOD_DESC = "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";

	public MethodVisitor ClassVisitorHandler(MethodVisitor mv, final String className, int access,
											 String name, String desc, String signature, String[] exceptions) {
		if ("service".equals(name) && METHOD_DESC.equals(desc)) {
			final boolean isStatic = Modifier.isStatic(access);
			final Type argsType = Type.getType(Object[].class);

			System.out.println(
					"HTTP Process 类名是: " + className + ",方法名是: " + name + "方法的描述符是:" + desc + ",签名是:"
							+ signature + ",exceptions:" + exceptions);
			try {
				return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
					@Override
					protected void onMethodEnter() {
						loadArgArray();
						int argsIndex = newLocal(argsType);
						storeLocal(argsIndex, argsType);
						loadLocal(argsIndex);

						if (isStatic) {
							push((Type) null);
						} else {
							loadThis();
						}

						loadLocal(argsIndex);

						mv.visitMethodInsn(INVOKESTATIC, "cn/org/enjoy/iast/core/Http", "enterHttp",
								"([Ljava/lang/Object;)V", false);

					}

					@Override
					protected void onMethodExit(int i) {
						super.onMethodExit(i);
						mv.visitMethodInsn(INVOKESTATIC, "cn/org/enjoy/iast/core/Http", "leaveHttp", "()V",
								false);
					}
				};
			}catch (Exception e) {
				System.err.println("处理HTTP请求时发生异常: " + e.getMessage());
				throw new RuntimeException("处理HTTP请求时发生错误", e);
			}

		}
		return mv;
	}
}


