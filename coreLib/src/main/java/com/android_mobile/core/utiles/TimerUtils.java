package com.android_mobile.core.utiles;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.System.currentTimeMillis;

/**
 * 时间戳格式化处理工具类
 *
 * @author mxh
 */
public class TimerUtils {
    public static final long ONE_DAY_TO_S = 86400;//一天等于86400s
    public static final long ONE_DAY_TO_MS = 86400000;//一天等于86400ms

    public static final String FORMAT_MM_DD = "MM-dd";

    public static final String FORMAT_MM_DD_NO1 = "M月d日";

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_YYYY_MM_DD_NO1 = "yyyy年M月d日";

    public static final String FORMAT_YYYY_MM_DD_NO_2 = "yyyy/MM/dd";

    public static final String FORMAT_YYYY_MM_DD_NO_3 = "yyyy.MM.dd";

    public static final String FORMAT_YYYY$POINT$MM = "yyyy.MM";

    public static final String FORMAT_MM_DD$BLANK$HH$COLON$MM = "MM-dd HH:mm";

    public static final String FORMAT_HH$COLON$MM = "HH:mm";

    public static final String FORMAT_MM_DD$BLANK$WEEKLY$HH$COLON$MM = "MM-dd 每周(期) HH:mm";

    public static final String FORMAT_YYYY_MM_DD$BLANK$WEEKLY$HH$COLON$MM = "yyyy-MM-dd 每周(期) 开始 至 HH:mm";

    public static final String FORMAT_YYYY_MM_DD$BLANK$HH$COLON$MM = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_YYYY_MM_DD$LINE$HH$COLON$MM = "yyyy/MM/dd HH:mm";

    public static final String FORMAT_YYYY_MM_DDTHH$MM$SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    public static final String FORMAT_DD_MM_YYYY$HH$MM$SS = "dd/MM/yyyy HH:mm:ss";


    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        return formatTime(currentTimeMillis(), FORMAT_YYYY_MM_DDTHH$MM$SS);
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime(String temp) {
        return formatTime(currentTimeMillis(), temp);
    }

    /**
     * 获取现在时间
     *
     * @return 返回长时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDateLongLen() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }

    /**
     * 时间戳格式化为指定 格式
     */
    public static String formatTime(long timestamp, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(new Date(timestamp));
    }

    /**
     * 时间戳格式化为指定 格式
     */
    public static long formatTime(String DateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date dt2 = sdf.parse(DateString);
            return dt2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String formatTime(long timestamp) {
        return formatTime(timestamp, FORMAT_YYYY_MM_DDTHH$MM$SS);
    }

    public static long formatTime(String timestamp) {
        return formatTime(timestamp, FORMAT_YYYY_MM_DDTHH$MM$SS);
    }

    /**
     * 获取前天时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getBeforeYesterday() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 2);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取昨天时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getYesterday() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取明天时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getTomorrow() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取后天时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getDayAfterTomorrow() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 2);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }


    /**
     * 检测两个时间是否为同一天
     */
    public static boolean equalsDay(long time1, long time2) {
        String t1 = formatTime(time1, FORMAT_YYYY_MM_DD);
        String t2 = formatTime(time2, FORMAT_YYYY_MM_DD);
        return TextUtils.equals(t1, t2);
    }

    /**
     * 月日时分秒，0-9前补0
     *
     * @param number the number
     * @return the string
     */
    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * Calculate days in month int.
     *
     * @param month the month
     * @return the int
     */
    public static int calculateDaysInMonth(int month) {
        return calculateDaysInMonth(0, month);
    }

    /**
     * Calculate days in month int.
     *
     * @param year  the year
     * @param month the month
     * @return the int
     */
    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    /**
     * @author HuWeiLiang
     * @date 16/3/2
     * @time 下午2:28
     * @method 获取选择的日期Calendar
     * @methodParams
     */
    public static Calendar getCalendarByDateStr(String dateString, String format) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        try {
            Date dt = sdf.parse(dateString);
            c.setTime(dt);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }

    /**
     * 判断是否是同年
     *
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isSameYear(long firstTime, long secondTime) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(firstTime);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(secondTime);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    /**
     * 判断是否是同月
     *
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isSameMonth(long firstTime, long secondTime) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(firstTime);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(secondTime);
        return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }


    /**
     * 判断时间是否属于本周
     *
     * @return
     */
    public static int getWeekOfDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        sdf.format(cal.getTime());
        String fileTime = sdf.format(cal.getTime());
        cal.setTime(new Date());
        String currentTime = sdf.format(cal.getTime());
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // Logs.e("所在周星期一的日期是:"+imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // Logs.e("所在周星期日的日期是:"+imptimeEnd);
        // Logs.e("文件的日期是:"+fileTime);
        // Logs.e("今天的日期是:"+currentTime);
        int result = fileTime.compareTo(currentTime);
        // Logs.e("比较结果:"+result);
        if (result != 0) {
            result = fileTime.compareTo(imptimeBegin);
            if (result == 0) {
                result = 2;
            }
            // Logs.i("比较结果:"+result);
        }
        return result;
    }

    /**
     * 根据年和月算出该月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthOfDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, 1);
        long time = c.getTimeInMillis();

        c.set(Calendar.MONTH, (month + 1));
        long nexttime = c.getTimeInMillis();

        long cha = nexttime - time;
        int s = (int) (cha / (24 * 60 * 60 * 1000));

        return s;
    }

    /**
     * 今天的时间显示 HH:mm
     * 昨天的时间显示 昨天
     * 其他的时间显示 yyyy-MM-dd HH:mm
     *
     * @param time time str
     * @return
     */
    public static String simplifyTime(String time) {
        try {
            String ret = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long create = sdf.parse(time).getTime();
            Calendar now = Calendar.getInstance();
            long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));//毫秒数
            long ms_now = now.getTimeInMillis();
            if (ms_now - create < ms) {
                SimpleDateFormat today = new SimpleDateFormat("HH:mm");
                ret = today.format(new Date(create));
                //ret = "今天";
            } else if (ms_now - create < (ms + 24 * 3600 * 1000)) {
                ret = "昨天";
            } else if (ms_now - create < (ms + 24 * 3600 * 1000 * 2)) {
                ret = "前天";
            } else {
                SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
                ret = today.format(new Date(create));
                //ret = "更早";
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 計算時間差
     *
     * @param endTime     結束時間
     * @param currentTime 當前時間
     * @return
     */
    public static String calculateDaysGap(long endTime, long currentTime) {
        Date d1 = new Date(endTime);
        Date d2 = new Date(currentTime);
        long diff = d1.getTime() - d2.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (((diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60))) - minutes * (1000 * 60)) / 1000;
        return days + "日" + hours + ":" + minutes + ":" + second + " 後結束";
    }

    /**
     * 轉換為時間差
     *
     * @param l 毫秒值
     * @return
     */
    public static String transformToTime(long l) {
        DecimalFormat df = new DecimalFormat("00");
        long days = l / (24 * 60 * 60 * 1000);
        long hours = (l / (60 * 60 * 1000) - days * 24);
        long minutes = ((l / (60 * 1000)) - days * 24 * 60 - hours * 60);
        long second = (l / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);

        if (l < (1000 * 60 * 60 * 24)) { //小於24小時
            return df.format(hours) + ":" + df.format(minutes) + ":" + df.format(second) + " 後結束";
        } else {//大於24小時
            return days + "天" + df.format(hours) + "時" + " 後結束";
        }
    }

    public static String transformToNearlyTime(long l) {
        DecimalFormat df = new DecimalFormat("00");
        long days = l / (24 * 60 * 60 * 1000);
        long hours = (l / (60 * 60 * 1000) - days * 24);
        long minutes = ((l / (60 * 1000)) - days * 24 * 60 - hours * 60);
        long second = (l / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);

        if (l < (1000 * 60 * 60 * 24)) { //小於24小時
            return "尚餘 " + df.format(hours) + ":" + df.format(minutes) + ":" + df.format(second) + " 開賣";
        } else {//大於24小時
            return "尚餘 " + df.format(days) + "天" + df.format(hours) + "時 開賣";
        }
    }

    public static String utc2Local(String utcTime, String localTimePatten) {
        if (StringUtils.isEmpty(utcTime)) {
            return "";
        }
        Lg.print("webber", "UTC 格式化前：" + utcTime);
        String utcTimePatten;
        if (utcTime.split(":").length < 3) {
            utcTimePatten = "yyyy-MM-dd'T'HH:mm'Z'";
        } else {
            utcTimePatten = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        }
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        //時區計算
        //localFormater.setTimeZone(TimeZone.getDefault());
        String afterUtcTime = localFormater.format(gpsUTCDate.getTime());
        Lg.print("webber", "UTC 格式化后：" + afterUtcTime);
        return afterUtcTime;
    }

    public static String utc2Local(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return "";
        }
        Lg.print("webber", "UTC 格式化前：" + utcTime);
        String utcTimePatten;
        String localTimePatten;
        if (utcTime.split(":").length < 3) {
            utcTimePatten = "yyyy-MM-dd'T'HH:mm'Z'";
            localTimePatten = FORMAT_YYYY_MM_DD$BLANK$HH$COLON$MM;
        } else {
            utcTimePatten = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            localTimePatten = FORMAT_YYYY_MM_DDTHH$MM$SS;
        }
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        //時區計算
        //  localFormater.setTimeZone(TimeZone.getDefault());
        String afterUtcTime = localFormater.format(gpsUTCDate.getTime());
        Lg.print("webber", "UTC 格式化后：" + afterUtcTime);
        return afterUtcTime;
    }

    /**
     * 返回指定的日期
     */
    public static String getSpecificDate(String date, int flag) {
        int sperator;
        if (StringUtils.isEmpty(date)) {
            return "";
        } else {
            if (0 == flag) {//年
                String year = date.substring(0, 4);
//				System.out.println("年是:" + year);
                return year;
            } else if (1 == flag) {//月
                sperator = date.indexOf("-");
                String month = date.substring(sperator + 1, sperator + 3);
//				System.out.println("月是:" + month);
                return month;
            } else if (2 == flag) {//日
                sperator = date.lastIndexOf("-");
                String day = date.substring(sperator + 1, sperator + 3);
//				System.out.println("日是:" + day);
                return day;
            } else if (3 == flag) {//时
                sperator = date.indexOf("T");
                String hour = date.substring(sperator + 1, sperator + 3);
//				System.out.println("时是:" + hour);
                return hour;
            } else if (4 == flag) {//分
                sperator = date.indexOf(":");
                String minute = date.substring(sperator + 1, sperator + 3);
//				System.out.println("分是:" + minute);
                return minute;
            } else if (5 == flag) {//秒
                sperator = date.lastIndexOf(":");
                String second = date.substring(sperator + 1, sperator + 3);
//				System.out.println("秒是:" + second);
                return second;
            } else {
                return date.replace('T', ' ');
            }
        }
    }

    /**
     * 截取掉前缀0以便转换为整数
     *
     * @see #fillZero(int)
     */
    public static int trimZero(@NonNull String text) {
        try {
            if (text.startsWith("0")) {
                text = text.substring(1);
            }
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取距今时间
     */
    public static long getTimeFromToday(int modeUnite, int time) {
        Calendar c = Calendar.getInstance();
        //过去一年
        c.setTime(new Date());
        c.add(modeUnite, time);
        Date y = c.getTime();
        return y.getTime();
    }
}