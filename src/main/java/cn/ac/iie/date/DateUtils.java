package cn.ac.iie.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtils {
    /**
     * 随机生成指定时间范围内的日期
     *
     * @param minDate
     * @param maxDate
     * @return
     * @author ww
     */
    public static Calendar getRandomCalendar(int minDate, int maxDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());// 设置当前日期
        // 随机设置日期为前maxAge年到前minAge年的任意一天
        int randomDay = 365 * minDate
                + new Random().nextInt(365 * (maxDate - minDate));
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - randomDay);
        return calendar;
    }

    /** 随机生成指定范围内的时间
     *
     * @param beginDate 起始时间  格式为yyyyMMdd
     * @param endDate 结束时间 格式为yyyyMMdd
     * @return Date
     * @author ww
     * */
    public static Date getRandomDateTime(String beginDate,String endDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if(start.getTime() >= end.getTime()){
                return null;
            }
            long date = CalculateUtils.getRandomLong(start.getTime(),end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** calendar ---> long
     * */
    public static Long transferCalendarToLong(Calendar calendar){
        long l = calendar.getTimeInMillis();
        return l;
    }

    /** long ----> calendar
     * */
    public static Calendar transferLongToCalendar(Long l){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return calendar;
    }

    /** calendar ---> date
     * */
    public static Date transferCalendarToDate(Calendar calendar){
        Date date = calendar.getTime();
        return date;
    }

    /** date ---> calendar
     * */
    public static Calendar transferDateToCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /** date ---> long
     * */
    public static Long transferDateToLong(Date date){
        long l = date.getTime();
        return l;
    }

    /** long ---> date
     * */
    public static Date transferLongToDate(Long l){
        Date date = new Date(l);
        return date;
    }

    /** string ---> date
     * */
    public static Date transferStringToDate(String string,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
             date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /** data ---> string
     * */
    public static String transferDateToSting(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String string = simpleDateFormat.format(date);
        return string;
    }

    /** calendar ---> string
     * */
    public static String transferCalendarToString(Calendar calendar,String format){
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String string = simpleDateFormat.format(date);
        return string;
    }

    /** string ---> calendar
     * */
    public static Calendar transferStringToCalendar(String string, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /** 根据出生年月计算年龄
     * @param birthDay  出生日期
     * */
    public static int getAgeByBirth(Date birthDay) {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;//当前日期在生日之前，年龄减一
                }
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
}
