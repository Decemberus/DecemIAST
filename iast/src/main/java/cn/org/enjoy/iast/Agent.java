package cn.org.enjoy.iast;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Base64;

public class Agent {
  //tomcat的agent配置如下
  //-Dfile.encoding=GBK -noverify -Xbootclasspath/p:D:/Code_Project/Java/DecemIAST/iast/target/agent.jar -javaagent:D:/Code_Project/Java/DecemIAST/iast/target/agent.jar
  public static void premain(String agentArgs, Instrumentation inst)
          throws UnmodifiableClassException, ClassNotFoundException {
    inst.addTransformer(new AgentTransform(), true);
    //在JVM启动前就加载的类还需要中心加载一边
    inst.retransformClasses(Runtime.class);
    inst.retransformClasses(Base64.class);

  }
}
