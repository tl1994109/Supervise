package com.datcent.project.thread;

import com.datcent.project.thread.entity.User;
import com.datcent.project.thread.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class BatchSms {

    public static void main(String[] args) {
        //1.初始化数据
        List<User> initUser = initUser();
        //2.定义每个线程分批发送大小
        int userCount = 2;
        //3.计算每个线程需要分批跑的数据
        List<List<User>> splitLists = ListUtils.splitList(initUser, userCount);
        for (int i = 0; i < splitLists.size(); i++) {
            List<User> list = splitLists.get(i);
            UserSendThread userSendThread = new UserSendThread(list);
            Thread thread = new Thread(userSendThread, "线程" + i);
            thread.start();
            System.out.println();
        }
        //4.分配发送
    }

    static private List<User> initUser() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(new User("userId:" + i, "userName:" + i));
        }
        return list;
    }
}

class UserSendThread implements Runnable {
    private List<User> listUser;
    public UserSendThread(List<User> listUser) {
        this.listUser = listUser;
    }
    @Override
    public void run() {
        for (User user : listUser) {
            System.out.println(Thread.currentThread().getName() + "," + user.toString());
        }
    }
}
