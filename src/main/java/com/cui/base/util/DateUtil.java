package com.cui.base.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 日期工具
 *
 * @author cuishixiang
 * @date 2018-05-11
 */
@Slf4j
public class DateUtil {
    // 日期常见格式化：
    public static final String FMT_YM = "yyyyMM";
    public static final String FMT_Y_M = "yyyy-MM";

    public static final String FMT_YMD = "yyyyMMdd";
    public static final String FMT_Y_M_D = "yyyy-MM-dd";
    // M 月份数字。一位数的月份没有前导零。
    // d 月中的某一天。一位数的日期没有前导零。
    public static final String FMT_Y_M_D_by_Diagonal = "yyyy/M/d";

    public static final String FMT_YMD_HMS = "yyyyMMddHHmmss";
    public static final String FMT_Y_M_D_H_M = "yyyy-MM-dd HH:mm";
    public static final String FMT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_UTC_Y_M_D_H_M_S = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FMT_UTC_Y_M_D_H_M_S_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String FMT_YMD_HMS_S = "yyyyMMddHHmmssSSS";
    public static final String FMT_Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.SSS";


    public static final String FMT_HMS = "HHmmss";
    public static final String FMT_H_M_S = "HH:mm:ss";

    public static final String FMT_HMS_S = "HHmmssSSS";
    public static final String FMT_H_M_S_S = "HH:mm:ss.SSS";

    public static final String FMT_CHINESE_Y_M_D = "yyyy年MM月dd日";
    public static final String FMT_CHINESE_Y_M_D_H_M_S = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FMT_CHINESE_Y_M_D_H_M_S_S = "yyyy年MM月dd日 HH:mm:ss.SSS";

    public static final String FMT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static final String FMT_WEEK_OF_YEAR = "yy年w周";
    public static final String FMT_MONTH_OF_YEAR = "yy年MM月";
    public static final String FMT_YEAR = "yyyy年";
    /**
     * 一天的时长，单位：秒
     */
    public static final Long SECONDS_ONE_DAY = 24 * 60 * 60L;

    /**
     * 周岁计算：必须过了生日，才表示满周岁。
     * <p>
     * 1、先看“年”，用当前年份减去生日年份得出年龄age
     * 2、再看“月”，如果当前月份小于生日月份，说明未满周岁age，年龄age需减1；如果当前月份大于等于生日月份，则说明满了周岁age，计算over！
     * 3、最后"日"，如果月份相等并且当前日小于等于出生日，说明仍未满周岁，年龄age需减1；反之满周岁age，over！
     * <p>
     * 示例：
     * 2000-10-01出生，当前日期是2000-11-01，未满1周岁，算0周岁！
     * 2000-10-01出生，当前日期是2005-10-01，未满5周岁，算4周岁（生日当天未过完）！
     * 2000-10-01出生，当前日期是2005-10-02，已满5周岁了！
     *
     * @param birthday 生日
     * @return 周岁
     */
    public static int getAge(LocalDate birthday) {
        LocalDate now = LocalDate.now();

        int age = now.getYear() - birthday.getYear();
        if (age <= 0) {
            return 0;
        }

        int currentMonth = now.getMonthValue();
        int currentDay = now.getDayOfMonth();
        int bornMonth = birthday.getMonthValue();
        int bornDay = birthday.getDayOfMonth();
        if (currentMonth < bornMonth || (currentMonth == bornMonth && currentDay <= bornDay)) {
            age--;
        }

        return age < 0 ? 0 : age;
    }

    /**
     * 周岁计算
     *
     * @param birthday 生日
     * @return 周岁
     * @see DateUtil#getAge(LocalDate)
     */
    public static int getAge(Date birthday) {
        LocalDate birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return getAge(birthdayDate);
    }

    /**
     * 周岁计算
     *
     * @param birthday 生日（字符串）
     * @param format   字符串格式
     * @return 周岁
     * @see DateUtil#getAge(LocalDate)
     */
    public static int getAge(String birthday, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate birthdayDate = LocalDate.parse(birthday, formatter);
        return getAge(birthdayDate);
    }

    /**
     * 字符串转日期
     *
     * @param dateStr 日期字符串
     * @param format  转换格式
     * @return 日期对象
     */
    public static Date string2Date(String dateStr, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.warn("日期解析错误：{}", dateStr, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * date转日期字符串
     *
     * @param date   日期
     * @param format 转换格式
     * @return 日期字符串
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据日期取得星期几
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    /**
     * 获取今天是今年的第几周
     * 每周默认的第一天是周日
     *
     * @return 最小是 1，最大 52
     */
    public static int getTodayWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 日期字符串 格式转换
     *
     * @param dateStr      日期字符串
     * @param originFormat 源格式
     * @param targetFormat 目标格式
     * @return 日期字符串
     */
    public static String string2String(String dateStr, String originFormat, String targetFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(originFormat);
            Date date = simpleDateFormat.parse(dateStr);
            SimpleDateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            return targetDateFormat.format(date);
        } catch (ParseException e) {
            log.warn("日期解析错误：{}", dateStr, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取两个日期间隔的所有日期字符串,包含起止日期
     * 结束日期 >= 开始日期
     *
     * @param start 格式必须为'2018-01-25'
     * @param end   格式必须为'2018-01-25'
     * @return 返回日期字符串数组：[2020-10-10,2020-10-11,2020-10-12]
     * @see DateUtil#getAllDaysBetweenDate(java.time.LocalDate, java.time.LocalDate)
     */
    public static List<String> getAllDaysBetweenDate(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return getAllDaysBetweenDate(startDate, endDate);
    }

    /**
     * 获取两个日期间隔的所有日期字符串,包含起止日期
     * 结束日期 >= 开始日期
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 返回日期字符串数组：[2020-10-10,2020-10-11,2020-10-12]
     * @see DateUtil#getAllDaysBetweenDate(java.time.LocalDate, java.time.LocalDate)
     */
    public static List<String> getAllDaysBetweenDate(Date start, Date end) {
        LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return getAllDaysBetweenDate(startDate, endDate);
    }

    public static List<String> getAllDaysBetweenDate(LocalDate start, LocalDate end) {
        List<String> list = new ArrayList<>();
        long distance = ChronoUnit.DAYS.between(start, end);
        if (distance < 0) {
            return list;
        }
        Stream.iterate(start, d -> d.plusDays(1)).limit(distance + 1).forEach(localDate -> {
            list.add(localDate.toString());
        });
        return list;
    }

    /**
     * 获取两个日期间隔的所有日期对象,包含起止日期
     * 结束日期 >= 开始日期
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 返回日期对象数组，所有日期的时间均为当天的最小值
     * @see DateUtil#getAllDateBetweenDate(java.time.LocalDate, java.time.LocalDate)
     */
    public static List<Date> getAllDateBetweenDate(Date start, Date end) {
        LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return getAllDateBetweenDate(startDate, endDate);
    }

    public static List<Date> getAllDateBetweenDate(LocalDate start, LocalDate end) {
        List<Date> list = new ArrayList<>();
        long distance = ChronoUnit.DAYS.between(start, end);
        if (distance < 0) {
            return list;
        }
        Stream.iterate(start, d -> d.plusDays(1)).limit(distance + 1).forEach(localDate -> {
            Date date = DateUtil.localDate2Date(localDate);
            list.add(date);
        });
        return list;
    }

    /**
     * 将不带时区的 localDateTime 转为服务器默认时区的 Date 对象
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将不带时区的 localDate 转为服务器默认时区且时间值为起始点 00:00:00 的 Date 对象
     */
    public static Date localDate2Date(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return DateUtil.localDateTime2Date(localDateTime);
    }

    /**
     * 获取今天的零点时间
     */
    public static Date getStartOfToday() {
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        return DateUtil.localDateTime2Date(localDateTime);
    }

    /**
     * 获取今天的23:59:59时间
     */
    public static Date getEndOfToday() {
        LocalDateTime localDateTime = LocalDate.now().atTime(LocalTime.MAX);
        return DateUtil.localDateTime2Date(localDateTime);
    }

    /**
     * 获取指定 date 当天的起始时间
     *
     * @param date 指定 date
     * @return date 对应当天的起始时间，即 00:00:00
     */
    public static Date getStartByDate(Date date) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return DateUtil.localDateTime2Date(startOfDay);
    }

    /**
     * 获取指定 date 当天的结束时间
     *
     * @param date 指定 date
     * @return date 对应当天的结束时间，即 23:59:59.999
     */
    public static Date getEndByDate(Date date) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MAX);
        return DateUtil.localDateTime2Date(startOfDay);
    }

    /**
     * 特定时间是否介于指定时间段之间，不包含前后两个边界时间
     *
     * @param startTime   指定开始时间
     * @param endTime     指定结束时间
     * @param compareTime 待比较时间
     * @return true：startTime< compareTime < endTime
     */
    public static boolean isBetweenTime(Date startTime, Date endTime, Date compareTime) {
        return startTime.before(compareTime) && endTime.after(compareTime);
    }

    /**
     * 特定时间是否介于指定时间段之间,包含前后两个边界时间
     *
     * @param startTime   指定开始时间
     * @param endTime     指定结束时间
     * @param compareTime 待比较时间
     * @return true：startTime< compareTime < endTime
     */
    public static boolean isBetweenAndEqualTime(Date startTime, Date endTime, Date compareTime) {
        return startTime.equals(compareTime) || endTime.equals(compareTime) ||
                startTime.before(compareTime) && endTime.after(compareTime);
    }
}
