package com.guigu.cn;

/**
 * ClassName:CaculatorImpl
 * Package:com.guigu.cn
 * description:
 * Author:crj
 * Create:2023/3/26 3:52
 */
public class CaculatorImpl  implements Caculator,CaculatorExtend{
    @Override
    public int add(int a, int b) {
        System.out.println("执行了计算器的加法方法：");
        return a + b;
    }

    @Override
    public int delete(int a, int b) {
        System.out.println("执行了计算器的减法方法：");
        return a - b;
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

    @Override
    public int division(int a, int b) {
        return a / b;
    }
}
