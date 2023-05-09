package com.guigu.cn;

/**
 * ClassName:SychnoizedDemo
 * Package:com.guigu.cn
 * description:
 * 演示notify是否是随机唤醒线程么？
 * 不是，hotspot是按顺序释放，notify则按照LIFO,先进等待池中的线程，最后释放，与notify相反
 * Author:crj
 * Create:2023/5/9 15:07
 */
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotifyTest{

    //等待列表, 用来记录等待的顺序
    private static List<String> waitList = new LinkedList<>();
    //唤醒列表, 用来唤醒的顺序
    private static List<String> notifyList = new LinkedList<>();

    private static Object lock = new Object();


    public static void main(String[] args) throws InterruptedException{

        //创建50个线程
        for(int i=0;i<50;i++){
            String threadName = Integer.toString(i);
            new Thread(() -> {
                synchronized (lock) {
                    String cthreadName = Thread.currentThread().getName();
//                    System.out.println("获取到的锁名称："+lock);
                    System.out.println("线程 ["+cthreadName+"] 正在等待.");
                    waitList.add(cthreadName);
                    try {
                        lock.wait();
//                        System.out.println("获得锁："+lock);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程 ["+cthreadName+"] 被唤醒了.");
                    notifyList.add(cthreadName);
                }
            },threadName).start();

            TimeUnit.MILLISECONDS.sleep(50);
        }

        TimeUnit.SECONDS.sleep(1);

        for(int i=0;i<50;i++){
            synchronized (lock) {
                lock.notify();
//                lock.notifyAll();
//                TimeUnit.MILLISECONDS.sleep(10);
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("wait顺序:"+waitList.toString());
        System.out.println("唤醒顺序:"+notifyList.toString());
    }
}
