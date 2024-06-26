package cn.org.enjoy.iast;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;


public class AgentTransform implements ClassFileTransformer {

	/**
	 * @param loader
	 * @param className
	 * @param classBeingRedefined
	 * @param protectionDomain
	 * @param classfileBuffer
	 * @return
	 * @throws IllegalClassFormatException
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className,
	                        Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
	                        byte[] classfileBuffer) throws IllegalClassFormatException {

		className = className.replace("/", ".");
		if (className.contains("cn.org.enjoy")) {
			System.out.println("Skip class: " + className);
			return classfileBuffer;
		}

		if (className.contains("java.lang.invoke")) {
			System.out.println("Skip class: " + className);
			return classfileBuffer;
		}
		byte[] originalClassfileBuffer = classfileBuffer;

		ClassReader  classReader  = new ClassReader(classfileBuffer);
		ClassWriter  classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
		ClassVisitor classVisitor = new IASTClassVisitor(className, classWriter);

		classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

		classfileBuffer = classWriter.toByteArray();
		className = className.replace("/", ".");


		String regexp = "(Decoder|Servlet|connector|Request|Parameters|Base64|Runtime|ProcessBuilder)";

		if (Pattern.compile(regexp).matcher(className).find()) {
			ClassUtils.dumpClassFile("D:\\Code_Project\\Java\\DecemIAST\\iast\\src\\main\\java\\cn\\org\\enjoy\\test\\test", className, classfileBuffer, originalClassfileBuffer);
		}

		return classfileBuffer;
	}

}
