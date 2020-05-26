package com.datcent.common.utils;

import java.text.NumberFormat;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/23 16:24
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
public class DoubleUtils {

    public static String percent(int num1,int num2){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) num1 / (float) num2 * 100)+"%";
        return result;
    }

}
