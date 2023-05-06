package com.guigu.cn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName:FacoryProxy
 * Package:com.guigu.cn
 * description:
 * Author:crj
 * Create:2023/3/26 3:53
 */
public class FacoryProxy {
    private Object target;
    public FacoryProxy(Object target){
        this.target = target;
    }
    public Object getProxy(){
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                System.out.println("执行每个方法 之前 加一个日志(输出该方法名字)：" + method.getName());
                result = method.invoke(target, args);
                System.out.println("proxy对象："+proxy);
                System.out.println("args: "+args);
                return result;
            }
        });
        return proxyInstance;
    }

}
