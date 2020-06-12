package com.datcent.project.thread;

public class Test {

    public static void main(String[] args) {
        Thread1 thread1=new Thread1();
        Thread t1=new Thread(thread1,"t1线程");
        Thread2 thread2=new Thread2();
        Thread t2=new Thread(thread2,"t2线程");
        Thread3 thread3=new Thread3();
        Thread t3=new Thread(thread3,"t3线程");

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
class  Thread1 implements Runnable{



    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+">>>"+i);
        }
    }
}

class  Thread2 implements Runnable{

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+">>>"+i);
        }
    }
}

class  Thread3 implements Runnable{


    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+">>>"+i);
        }
    }
}