package com.datcent.common.utils.simlier;/**
 * @创建人 wj
 * @创建时间 2019/5/9
 * @描述
 */

/**
 * 计算两个字符串的相识度
 */
public class Similarity {

    String s1="郭航柳你好，今天是星期五";
    String s2="恶趣味企鹅企鹅去问问，今天额郭外全文是星期";

    public static final  String content1="郭航柳你好，今天是星期五";

    public static final  String content2="恶趣味企鹅企鹅去问问，今天额郭外全文是星期";


    public static void main(String[] args) {

        double  score=CosineSimilarity.getSimilarity(content1,content2);
        System.out.println("相似度："+score);

        score=CosineSimilarity.getSimilarity(content1,content1);
        System.out.println("相似度："+score);
    }

}
