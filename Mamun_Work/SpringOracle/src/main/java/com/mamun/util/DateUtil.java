package com.mamun.util;

import java.util.Date;

/**
 * Created by ab9ma on 4/23/2017.
 */
public class DateUtil {
    public static Date DateFromString(String date, String format) {

        if(format.equals("MM/DD/YYYY")){
            try {
                int month = Integer.parseInt(date.substring(0, 2));
                int day = Integer.parseInt(date.substring(3, 5));
                int year = Integer.parseInt(date.substring(6, 10));
                return new Date(year - 1900, month - 1, day);
            }catch (Exception e){
                e.printStackTrace();
                return (Date) error();
            }
        }

        System.err.println("Invalid arguments in "+DateUtil.class.getCanonicalName());
        return (Date) error();
    }

    private static Object error(){
        System.err.println("Invalid arguments in "+DateUtil.class.getCanonicalName());
        return null;
    };
}
