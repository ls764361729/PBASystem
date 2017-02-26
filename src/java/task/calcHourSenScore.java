/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import calcBean.*;
import java.util.Calendar;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 定时计算敏感分数（小时）
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class calcHourSenScore extends TimerTask {
    
    private static boolean isRunning = false;
    private ServletContext context = null;
    
    /**
     *
     */
    public calcHourSenScore() {
        super();
    }

    /**
     *
     * @param context
     */
    public calcHourSenScore(ServletContext context) {
        this.context = context;
    }

    public void run() {
        Boolean flag = false;
        if (!isRunning) {
            isRunning = true;
            context.log("开始执行敏感分数计算任务（小时）" + GetTimeBean.getTime());

            //任务代码
            SenCalcBean senCalc = new SenCalcBean();
            flag = senCalc.calcHourAutoScore();

            isRunning = false;
            if(flag)
                context.log("计算任务执行成功");
            else
                context.log("计算任务执行失败");
        } else {
            context.log("上一次任务执行还未结束");
        }
    }

    /**
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
