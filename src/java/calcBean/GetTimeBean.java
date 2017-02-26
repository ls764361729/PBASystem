/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcBean;

import java.util.Calendar;

/**
 * 系统时间获取 Bean
 * 结果为String格式
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class GetTimeBean {
    
    private static String date;
    private static String time;
    
    /**
     * 获取当前日期与时间
     */
    public static void GetTime(){
        
        Calendar calender = Calendar.getInstance();
        
        int[] iDate = new int[7];
        String[] sDate = new String[7];
        
        iDate[2] = calender.get(Calendar.MONTH) + 1;
        iDate[3] = calender.get(Calendar.DATE);
        iDate[4] = calender.get(Calendar.HOUR_OF_DAY);
        iDate[5] = calender.get(Calendar.MINUTE);
        iDate[6] = calender.get(Calendar.SECOND);
        
        sDate[1] = calender.get(Calendar.YEAR) + "";
        
        for(int i=2;i<=6;i++){
            if(iDate[i]<10)
                sDate[i] = "0" + iDate[i];
            else
                sDate[i] = Integer.toString(iDate[i]);
        }
        
        date = sDate[1] + sDate[2] + sDate[3];
        time = sDate[4] + sDate[5] + sDate[6];  
    }
    
    /**
     * 获取昨天的日期
     * 返回值为String格式
     * @return
     */
    public static String getTomorrow(){
        
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DAY_OF_MONTH, -1);  
        
        int[] iDate = new int[7];
        String[] sDate = new String[7];
        
        iDate[2] = calender.get(Calendar.MONTH) + 1;
        iDate[3] = calender.get(Calendar.DATE);
        
        sDate[1] = calender.get(Calendar.YEAR) + "";
        
        for(int i=2;i<=3;i++){
            if(iDate[i]<10)
                sDate[i] = "0" + iDate[i];
            else
                sDate[i] = Integer.toString(iDate[i]);
        }
        
        return sDate[1] + sDate[2] + sDate[3];
    }

    /**
     *
     * @return
     */
    public static String getDate() {
        GetTime();
        return date;
    }

    /**
     *
     * @return
     */
    public static String getTime() {
        GetTime();
        return time;
    }
    
}
