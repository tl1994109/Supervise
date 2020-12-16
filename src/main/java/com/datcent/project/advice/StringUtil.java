package com.datcent.project.advice;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author xusi
 * @Description 文本处理
 * @Date 10:26 2018/1/11
 * @Param
 * @return
 **/
public class StringUtil {

    private static Set<String> regblackSet = new HashSet<>();

    /**
     * @Author xusi
     * @Description 判断字符串非空
     * @Date 2019、6/19
     * @Param
     * @return boolean
     **/
    public static boolean availableStr(String str){
        if(str == null){
            return false;
        }else{
            str = str.trim();
            if("".equals(str) || "null".equalsIgnoreCase(str)){
                return false;
            }else{
                return true;
            }
        }
    }


    /**
     * 判定一个对象是否为null or empty
     *
     * @param o
     *            对象
     * @return true or false
     */
    public static boolean isNullOrEmpty(Object o) {
        return o == null || String.valueOf(o).trim().length() == 0
                || String.valueOf(o).trim().equals("null");
    }


    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
//    public static String underscoreName(String name) {
//        StringBuilder result = new StringBuilder();
//        if (name != null && name.length() > 0) {
//            // 将第一个字符处理成大写
//            result.append(name.substring(0, 1).toUpperCase());
//            // 循环处理其余字符
//            for (int i = 1; i < name.length(); i++) {
//                String s = name.substring(i, i + 1);
//                // 在大写字母前添加下划线
//                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
//                    result.append("_");
//                }
//                // 其他字符直接转成大写
//                result.append(s.toUpperCase());
//            }
//        }
//        return result.toString();
//    }

    /**
     * 添加正则表达式黑名单校验
     * @param reg
     * @return
     */
    public static boolean checkBlackRegex(String reg){
        regblackSet.add("(e+)+");
        regblackSet.add("([a-zA-Z]+)*");
        regblackSet.add("(e|ee)+");
        if(regblackSet.contains(reg)){
            return false;
        }else{
            return true;
        }
    }
}
