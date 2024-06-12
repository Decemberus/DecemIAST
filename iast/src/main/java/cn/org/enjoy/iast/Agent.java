package cn.org.enjoy.iast;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Base64;

public class Agent {

  public static void premain(String agentArgs, Instrumentation inst)
          throws UnmodifiableClassException, ClassNotFoundException {
    inst.addTransformer(new AgentTransform(), true);
    inst.retransformClasses(Runtime.class);
    inst.retransformClasses(Base64.class);

  }
}
