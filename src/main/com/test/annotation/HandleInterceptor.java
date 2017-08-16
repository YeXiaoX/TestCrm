package test.annotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by yexiaoxin on 2017/7/31.
 */
public class HandleInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        String name = method.getName();
        DBRouting r = method.getAnnotation(DBRouting.class);
        String ss = r.name();
        System.out.println(name+":"+ss);
        Object retVal = null;
        try {
            retVal = methodInvocation.proceed();
        } catch (Throwable ex) {
            throw ex;
        }

        return retVal;
    }
}
