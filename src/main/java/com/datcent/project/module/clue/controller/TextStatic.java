package com.datcent.project.module.clue.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public  class TextStatic {


    static int i;
    public TextStatic(){
        i = 4;
    }
    public TextStatic(int j){
        i = j;
    }


    public static Integer mix(){


     Integer param =12345678;
     
     String str=param+"";
     
     List<Integer> list=new ArrayList<Integer>();
     
     char [] chars=str.toCharArray();
      for (int i = 0; i < chars.length; i++) {
          char ch=chars[i];
          int num=Integer.parseInt(String.valueOf(ch));
          num=num-1;
          list.add(num);
      }
      StringBuffer sb=new StringBuffer();
      for (int i = list.size() - 1; i >= 0; i--) {
          sb.append(list.get(i));
      }

     Integer integer=Integer.parseInt(sb.toString());



      return  integer;
  }


    public static void main(String[] args) {


//        int s=0;
//
//        System.out.println((s+9)%10);
//        System.out.println(mix());

        TextStatic t = new TextStatic(5); //声明对象引用，并实例化

        TextStatic tt = new TextStatic(); //声明对象引用，并实例化
        System.out.println(t.i);
        System.out.println(tt.i);
        System.out.println(t.i);

    }

}
