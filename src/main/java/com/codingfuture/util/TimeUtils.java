package com.codingfuture.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    //获取当前时间(yyyy-mm)
    public static String timeYM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String nowDate = sdf.format(date);
        return nowDate;
    }

    //获取当前时间(yyyy-MM-dd HH:mm:ss)
    public static String timeymdhms() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String nowDate = sdf.format(date);
        return nowDate;
    }
}
