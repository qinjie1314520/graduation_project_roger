package com.common.utils;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单的工具类，用户小程序使用
 *
 * @author: qinjie
 **/
public class CommonUtils {

    public static String getCurrentTimeAs20201010() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    /**
     * 功能描述: 得到当前时间
     *
     * @return: 当前时间，2017-08-08.。。。
     * @auther: 秦杰
     * @date: 2019/8/29 11:39
     */
    public static String getCurrentTime() {
        return TimeStampToTime(System.currentTimeMillis());
    }

    /**
     * 功能描述: 向右补位
     *
     * @param: src原字符串，len补位后的总的长度，ch补位字符
     * @return:
     * @auther: 秦杰
     * @date: 2019/8/29 11:41
     */
    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    /**
     * 功能描述: 向左边补位
     *
     * @param: [src, len, ch] src：原字符串   len：补足的长度，  ch：补位的字符
     * @return: java.lang.String
     * @auther: 秦杰
     * @date: 2019/7/31 14:56
     */
    public static String padLeft(String src, int len, char ch) {

        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    /**
     * 功能描述: 得到指定长度的随机字符串，存在重复的可能性
     *
     * @param: [length]生成随机字符串的长度
     * @return: java.lang.String
     * @auther: 秦杰
     * @date: 2019/7/27 16:10
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机生成指定长度的字符串数字
     *
     * @param length 长度
     * @return
     */
    public static String getRandomNum(int length) { //length表示生成字符串的长度

        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return boolean
     */
    public static boolean isEmail(String email) {

        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述: 判断是否位手机号
     *
     * @param: [str]字符串
     * @return: boolean
     * @auther: 秦杰
     * @date: 2019/7/27 16:13
     */
    public static boolean isPhone(String str) {

        if (str == null || str.length() == 0) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(1[3,4,5,7,8][0-9])\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        boolean b = matcher.matches();
        return b;
    }

    /**
     * 功能描述: Date转换成定时器中Cron表达式
     *
     * @param: [time]Date类型的时间
     * @return: java.lang.String
     * @auther: 秦杰
     * @date: 2019/7/27 16:14
     */
    public static String getCron(Long time) {

        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (time != null) {
            formatTimeStr = sdf.format(time);
        }
        return formatTimeStr;
    }

    /**
     * 功能描述: 得到当前时间
     *
     * @return: 时间的字符串，20170810
     * @auther: 秦杰
     * @date: 2019/8/29 11:42
     */
    public static String getDateTime() {
        String strDate = "";
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            strDate = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    public static String TimeStampToTime(Long t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.parseLong(String.valueOf(t))));      // 时间戳转换成时间
    }

    public static void sortByAsc(Integer num[]) {
        for (int i = 0; i < num.length - 1; i++) {
            for (int j = i + 1; j < num.length; j++) {
                if (num[i] > num[j]) {
                    int a = num[i];
                    num[i] = num[j];
                    num[j] = a;
                }
            }
        }
    }

    /**
     * 功能描述:
     *
     * @param timeStamp 当前时间戳
     * @param timeZone  GMT+8:00
     * @return java.lang.Long
     */
    public static Long getMonthStartTime(Long timeStamp, String timeZone) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @param timeZone  如 GMT+8:00
     * @return
     */
    public static Long getMonthEndTime(Long timeStamp, String timeZone) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        // 获取当前月最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述: 得到指定月的开始结束时间戳,返回list，0-开始时间，1-结束时间
     *
     * @param timeStamp 毫秒级时间戳
     * @param month     月份
     * @param timeZone  如 GMT+8:00
     * @return java.lang.Long
     */
    public static List<Long> getMonthStartAndEndTime(Long timeStamp, Integer month, String timeZone) {
        List<Long> res = new ArrayList<Long>();
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.MONTH, month - 1);

        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        res.add(calendar.getTimeInMillis());
        // 获取指定月最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        res.add(calendar.getTimeInMillis());
        return res;
    }


    public static void main(String args[]) {
        System.out.println(isPhone("12345678911"));
    }
}
