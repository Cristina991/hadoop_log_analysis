package cn.wust.com.demo;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
//
//        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long lt = new Long("1511544070");
//        Date date = new Date(lt*1000);
//        System.out.println(date);
//        res = simpleDateFormat.format(date);
//        String date1 = "2017-12-04";
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = dateformat.parse("2017-12-03");
        Date date2 = dateformat.parse("2017-11-25");

        Date date = dateformat.parse("2017-11-24");

        if(date.compareTo(date1)>0||date.compareTo(date2)<0)
            System.out.println("yes");
        else
            System.out.println("no");




    }
}
