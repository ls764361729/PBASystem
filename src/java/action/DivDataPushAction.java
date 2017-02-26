/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import structBean.*;
import sqlBean.*;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Struct2 Action 后台功能数据存储
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */
public class DivDataPushAction extends ActionSupport implements ServletRequestAware{

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
        HttpSession session=request.getSession();
        String type = request.getParameter("pushType");
        Boolean flag = false;
        CalcSqlBean calcSQL=new CalcSqlBean();
        UserSqlBean userSQL=new UserSqlBean();
        switch (type){
            case "ManaPepAdd":
                ManaPepBean manaPep = new ManaPepBean();
                manaPep.setDeviceID(request.getParameter("deviceID"));
                manaPep.setPepName(request.getParameter("pepName"));
                manaPep.setPepIDcardNum(request.getParameter("pepIDcardNum"));
                manaPep.setPepAge(request.getParameter("pepAge"));
                manaPep.setPepSex(request.getParameter("pepSex"));
                manaPep.setStartDate(request.getParameter("startDate"));
                manaPep.setManaDayLimit(Integer.parseInt(request.getParameter("manaDayLimit")));
                flag = calcSQL.insertManaPep(manaPep);
                break;
            case "SenAreaAdd":
                SenAreaBean senArea = new SenAreaBean();
                senArea.setAreaName(request.getParameter("areaName"));
                senArea.setAreaTypeID(Integer.valueOf(request.getParameter("areaTypeID")));
                senArea.setaCoordX(request.getParameter("aCoordX"));
                senArea.setaCoordY(request.getParameter("aCoordY"));
                flag = calcSQL.insertSenArea(senArea);
                break;
            case "LogOut":
                session.setAttribute("userName", null);
                break;
            case "PasswordChange":
                String userName = session.getAttribute("userName").toString();
                if(userName!=null){
                    flag = userSQL.updatePass(request, userName, request.getParameter("oldPassword"), request.getParameter("newPassword"));
                }
                break;
            case "AtAreaAdd":
                AtAreaBean atArea = new AtAreaBean();
                atArea.setAlertAreaName(request.getParameter("atAreaName"));
                atArea.setAtCoordXs(request.getParameterValues("atCoordXs[]"));
                atArea.setAtCoordYs(request.getParameterValues("atCoordYs[]"));
                System.out.println("atCoordX"+Arrays.toString(request.getParameterValues("atCoordXs[]")));
                flag = calcSQL.insertAtArea(atArea);
                break;
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(String.valueOf(flag));
        if(flag) {
            message=SUCCESS;
        }
        return null;
    }
    
}
