/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import calcBean.GetObjinfBean;
import calcBean.GetTimeBean;
import structBean.LoadDataBean;
import com.alibaba.fastjson.JSON;
import sqlBean.*;
import java.util.ArrayList;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Struct2 Action 后台功能数据读取
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class DivDataLoadAction extends ActionSupport implements ServletRequestAware{

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
        String requestType = request.getParameter("requestType");
        String pushJson = null;
        CalcSqlBean calcSQL=new CalcSqlBean();
        DataSqlBean dataSQL=new DataSqlBean();
        UserSqlBean userSQL=new UserSqlBean();
        switch (requestType){
            case "ArchListLoad":
                ArrayList archList = calcSQL.readManaPep();
                if(archList!=null){
                    pushJson = JSON.toJSONString(archList);
                }
                break;
            case "ArchListQuery":
                String queryType = request.getParameter("queryType");
                String queryInput = request.getParameter("queryInput");
                ArrayList archQuery = calcSQL.queryManaPep(queryType,queryInput);
                if(archQuery!=null){
                    pushJson = JSON.toJSONString(archQuery);
                }
                break;
            case "Loading":
                LoadDataBean loadData = new LoadDataBean();
                loadData.setDatabasePep(calcSQL.loadDatabasePep());
                loadData.setOnlinePep(dataSQL.loadOnlinePep(GetTimeBean.getDate()));
                pushJson = JSON.toJSONString(loadData);
                break;
            case "SenScoreDayLoad":
                ArrayList senScoreDayList = GetObjinfBean.getObjSenScore();
                if(senScoreDayList!=null){
                    pushJson = JSON.toJSONString(senScoreDayList);
                }
                break;
            case "UserListLoad":
                ArrayList userList = userSQL.getUserList();
                if(userList!=null){
                    pushJson = JSON.toJSONString(userList);
                }
                break;
            case "AtAreaListLoad":
                ArrayList atAreaList = calcSQL.readAtArea();
                if(atAreaList!=null){
                    pushJson = JSON.toJSONString(atAreaList);
                }
                break;
        }
        System.out.println(pushJson);
        if(pushJson!=null){
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(pushJson);
            message=SUCCESS;
        }
        return null;
    }
    
}
