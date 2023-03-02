package cn.wust.com.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 获取今日的日期
     *
     * @return
     */
    public static String getYestDate() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 0);
        Date time = instance.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
        return format;

    }
    public static void main(String[] args) {
        System.out.println(getYestDate());

    }

}
