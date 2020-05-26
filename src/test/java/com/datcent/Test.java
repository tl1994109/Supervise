package com.datcent;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {



        String [] stringsArray=new String[3];

        stringsArray[0]="one";
        stringsArray[1]="two";
        stringsArray[2]="third";

        System.out.println(stringsArray[0]);
        List<String> list= Arrays.asList(stringsArray);
        list.set(0,"oneList");

        System.out.println(stringsArray[0]);
//        list.add("four");
//        list.remove(2);
//        list.clear();

    }
}
