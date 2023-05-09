package com.guigu.cn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName:ThreadDemo
 * Package:com.guigu.cn
 * description:
 *      多线程编程步骤
 *      1）创建一个资源类，定义方法和属性
 *      2）在定义的方法中，判断、干活（具体业务）、通知（唤醒）
 *      3）调用多线程，执行资源类中的方法
 *      4）防止虚假唤醒问题
 * 虚假唤醒：
 *   举个例子：比如四个线程 AA实现变量number加一（number初始为0）【先判断，不是0就等待wait/await，是0，就执行number++,然后唤醒其他线程】
 *                    线程 BB实现变量number减一【先判断，不是1就等待wait/await，是1，就执行number--,然后唤醒其他线程notifiall/singall】
 *                    线程 CC实现变量number加一【先判断，不是0就等待wait/await，是0，就执行number++,然后唤醒其他线程】
 *                    线程 DD实现变量number减一【先判断，不是1就等待wait/await，是1，就执行number--,然后唤醒其他线程notifiall/singall】
 *  1)首先四个线程是并行执行的
 *  2)当第一次AA线程获取到synchronized锁/lock锁后，第一次number初始为0，执行++操作，number变为1，
 *  3)这个时候CC线程开始获取，发现number不是0就开始等待，然后AA线程又获取到了，这个时候发现number也不是0,也进入了等待
 *  4）这个时候DD开始获取锁，判断发现number是1，开始执行numer--，number变为0，并执行唤醒方法，唤醒其他等待线程
 *  5）好了，这个时候有两个等待线程AA和CC被唤醒，然后他们都往下执行，相当于执行了两次numer++操作，结果变为2，发生了问题。
 *  PS:wait/await方法，永远是在哪等待就在哪里唤醒，所以，加个while循环就能解决。
 *
 *  下面通过lock(重入锁来演示)
 *
 *  我们想要的结果，永远是
 *
 *
 *  lock和synchronized的区别
 *  1）lock是一个接口，而synchronized是一个Java关键字
 *  2）最重要的一个区别，synchronized是自动释放锁，lock是手动释放锁，同时为了防止出现异常导致死锁，通常使用try{}finally{}
 *  3）lock可以让等待锁的线程响应中断，而synchronized不行
 *  4）lock可以知道有没有成功获取到锁，而synchronized不行
 *  5）lock可以提高多个线程进行读操作的过程。（在大量线程同时竞争的同时明显）
 *
 *  AA：1，BB:0,CC:1,DD:0
 * Author:crj
 * Create:2023/5/9 13:00
 */

//1、创建一个资源类，定义方法和属性
class LockDemo{
    private int number = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void incr() throws InterruptedException {
        try {
            lock.lock();
            //2、在资源类的方法上进行判断
            while (number != 0 )
            {
                condition.await();//和wait方法一样，在哪等待，在哪唤醒，意味着，要是这个等待的线程在这被唤醒了，它从这里开始往下执行。
            }
            //3、干活
            number++;
            System.out.println(Thread.currentThread().getName()+"::"+number);
            //4、通知其他线程
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void dcre() throws InterruptedException {
        try {
            lock.lock();
            //2、在资源类的方法上进行判断
            while (number != 1 )
            {
                condition.await();//和wait方法一样，在哪等待，在哪唤醒，意味着，要是这个等待的线程在这被唤醒了，它从这里开始往下执行。
            }
            //3、干活
            number--;
            System.out.println(Thread.currentThread().getName()+"::"+number);
            //4、通知其他线程
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}


public class ThreadDemo {
    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();

        new Thread(()->{
            for (int i = 0; i <= 4; i++) {
                try {
                    lockDemo.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();//AA方法负责加一

        new Thread(()->{
            for (int i = 0; i <= 4; i++) {
                try {
                    lockDemo.dcre();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();//BB方法负责减一

        new Thread(()->{
            for (int i = 0; i <= 4; i++) {
                try {
                    lockDemo.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();//CC方法负责加一

        new Thread(()->{
            for (int i = 0; i <= 4; i++) {
                try {
                    lockDemo.dcre();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();//DD方法负责减一

    }
}
