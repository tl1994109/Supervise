package com.datcent.project.thread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class TestThread implements Runnable {


    private  int ticketNumbers=100;

    private Object object=new Object();

    private ReentrantLock reentrantLock=new ReentrantLock();

    @Override
    public void run() {


        while (true){


            try {
                reentrantLock.lock();
                if(this.ticketNumbers > 0){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String msg = Thread.currentThread().getName()+" 售出第   "+(this.ticketNumbers) +"  张票";
                    System.out.println(msg);
                    this.ticketNumbers--;
                }else{
                    System.out.println("票已售完，请下次再来！");
                    System.exit(0);
                }
            }finally{
                reentrantLock.unlock();
            }


            //synchronized (object){

            //}
        }
    }

    public static void main(String[] args) {


//        CopyOnWriteArrayList list=new CopyOnWriteArrayList();


//        List<String> list=new ArrayList<>();
//
//        for (int i = 0; i < 10000; i++) {
//            new Thread(()->{
//                synchronized (list){
//                    list.add(Thread.currentThread().getName());
//                }
//
//            }).start();
//        }
//
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(list.size());

       TestThread testThread=new TestThread();
//
        Thread thread1=new Thread(testThread,"小明");

        thread1.start();
        Thread thread2=new Thread(testThread,"老师");

        thread2.start();
        Thread thread3=new Thread(testThread,"黄牛");

        thread3.start();


    }
}
