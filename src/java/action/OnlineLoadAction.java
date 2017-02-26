/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import calcBean.GetObjinfBean;
import calcBean.GetSenAreaBean;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import sqlBean.CalcSqlBean;
import structBean.SenAreaBean;

/**
 * Struct2 Action 系统初始化数据读取
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class OnlineLoadAction extends ActionSupport implements ServletRequestAware{

    private String message = ERROR;
    private HttpServletRequest request;
    
    /**
     *
     * @param hsr
     */
    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        request = hsr;
    }
    
    //实例化对数据操作的封装类

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        CalcSqlBean calcSQL = new CalcSqlBean();
        String getFlag = request.getParameter("getFlag");
        String pushJson = null;
        switch (getFlag) {
            case "loadOnlineObj":
                ArrayList ObjectList = GetObjinfBean.getObjInf();
                pushJson = JSON.toJSONString(ObjectList);
                break;
            case "loadSenArea":
                List <SenAreaBean> AreaList = GetSenAreaBean.getSenAreaList();
                pushJson = JSON.toJSONString(AreaList);
                break;
            case "loadAtArea":
                ArrayList atAreaList = calcSQL.readAtArea();
                if(atAreaList!=null){
                    pushJson = JSON.toJSONString(atAreaList);
                }
                break;
        }

        System.out.println(pushJson);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(pushJson);
        message=SUCCESS;
        return null;
    }
    
}
