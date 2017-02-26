/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * 后台服务器定时任务调度Servlet
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class ContextListener extends HttpServlet implements ServletContextListener {

    private static final long serialVersionUID = 1L;
    private java.util.Timer timer = null;

    /**
     *
     */
    public ContextListener() {
        
    }

    /**
     *
     * @param event
     */
    public void contextInitialized(ServletContextEvent event) {
        timer = new java.util.Timer(true);
        event.getServletContext().log("定时器已启动");
        timer.schedule(new calcHourSenScore(event.getServletContext()), 0, 60 * 60 * 1000); //后边最后一个参数代表监视器的监视周期,现在为一小时
        timer.schedule(new calcDaySenScore(event.getServletContext()), 0, 24 * 60 * 60 * 1000);
        event.getServletContext().log("已经添加任务调度表");

    }

    /**
     *
     * @param event
     */
    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        System.out.println("定时器销毁");
        event.getServletContext().log("定时器销毁");
    }
}
