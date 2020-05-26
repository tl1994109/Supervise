package com.datcent.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author datcent
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 获取当前时间 向前半年的月份
     * @return
     */
    public static List getSixMonth(){
        List<Object> list=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        Calendar f = Calendar.getInstance();
        a.setTime(new Date());
        b.setTime(new Date());
        c.setTime(new Date());
        d.setTime(new Date());
        e.setTime(new Date());
        f.setTime(new Date());

        a.add(Calendar.MONTH, -5);
        b.add(Calendar.MONTH, -4);
        c.add(Calendar.MONTH, -3);
        d.add(Calendar.MONTH, -2);
        e.add(Calendar.MONTH, -1);
        f.add(Calendar.MONTH, 0);
        list.add(sdf.format(a.getTime()));
        list.add(sdf.format(b.getTime()));
        list.add(sdf.format(c.getTime()));
        list.add(sdf.format(d.getTime()));
        list.add(sdf.format(e.getTime()));
        list.add(sdf.format(f.getTime()));
       return list;
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    /**
     *   毫秒转小时
     * @param time
     * @return
     */
    public static String getGapTime(Long time) {
        long day = time / (24*60*60*1000);
        long hour = time / (60*60*1000) - day * 24;
        long min = (time / (60*1000)) - day *24*60 - hour * 60;
        long s = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min *60);
        return day+"天"+hour+"小时"+min+"分"+s+"秒";
    }

    /**
     * 按照指定的类型计算两个时间的时间差
     * @param past 较小的时间参数
     * @param future 较大的时间参数
     * @param type 计算时间差的类型
     * @return 返回的指定类型下的时间差值
     *  <br/>默认按毫秒,type=0按毫秒计算时间差
     *  <br/>type=1按照秒计算时间差
     *  <br/>type=2按照分钟计算时间差
     *  <br/>type=3按照小时计算时间差
     *  <br/>type=4按照天计算时间差
     *  <br/>type=5按照年计算时间差(约定一年365天)
     */
    public static String dateDiff(Date past,Date future) {
        long from = past.getTime();
        long to = future.getTime();
        int diff = (int) (to - from);//默认按毫秒计算时间差
        long day = diff / (24*60*60*1000);
        long hour = diff / (60*60*1000) - day * 24;
        long min = (diff / (60*1000)) - day *24*60 - hour * 60;
        long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min *60);
        return day+"天"+hour+"小时"+min+"分"+s+"秒";
    }

    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)
     * format 为null时，默认值为yyyy-MM-dd HH:mm:ss
     * @param   time format
     * @param format  日期格式：yyyy-MM-dd HH:mm:ss
     * @return String
     * @throws ParseException
     */
    public static  String convertDate( Date time, String format) throws ParseException{
        if(time == null){
            return null;
        }
        if("".equals(format)|null==format){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat (format);
        String ctime = formatter.format(time);
        return  ctime;
    }

    /**
     * 将字符串日期转换为日期格式
     *
     *
     * @param datestr
     * @return
     *
     */
    public static Date stringToDate(String datestr) {

        if(datestr ==null ||datestr.equals("")){
            return null;
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            date=DateUtils.stringToDate(datestr,YYYY_MM_DD);
        }
        return date;
    }

    /**
     * 将字符串日期转换为日期格式
     * 自定義格式
     *
     * @param datestr
     * @return
     *
     */
    public static Date stringToDate(String datestr, String dateformat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(String d1,String d2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse(d1);
        Date date2 = simpleDateFormat.parse(d2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }
}
