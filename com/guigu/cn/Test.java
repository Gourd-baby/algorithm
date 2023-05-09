package com.guigu.cn;

/**
 * ClassName:test
 * Package:com.guigu.cn
 * description:
 * Author:crj
 * Create:2023/3/9 21:51
 */
public class Test {
    public static void main(String[] args) {
        CaculatorImpl caculator = new CaculatorImpl();
        FacoryProxy facoryProxy = new FacoryProxy(caculator);
        Caculator proxy = (Caculator) facoryProxy.getProxy();
        System.out.println(proxy.add(1, 2));
    }
}
