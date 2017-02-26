/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import sqlBean.UserSqlBean;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Struct2 Action 登录功能实现
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class LoginAction extends ActionSupport implements ServletRequestAware{
    
    private String userName;
    private String passWord;
    
    private ResultSet rs = null;
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
    
    /**
     *
     * @return
     */
    public String getUserName(){
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName){
        this.userName=userName;
    }
    
    /**
     *
     * @return
     */
    public String getPassWord(){
        return passWord;
    }

    /**
     *
     * @param passWord
     */
    public void setPassWord(String passWord){
        this.passWord=passWord;
    }
    
    /**
     *
     */
    @Override
    public void validate(){
        if(this.getUserName()==null||this.getUserName().length()==0){
            addFieldError("userName","请输入用户名！");
        }
        else{
            try{
                UserSqlBean mysql=new UserSqlBean();
                rs=mysql.selectMess(request, this.getUserName());
                if(!rs.next()){
                    addFieldError("userName","此用户尚未注册！");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(this.getPassWord()==null||this.getPassWord().length()==0){
            addFieldError("passWord","请输入登录密码！");
        }
        else{
            try{
                UserSqlBean mysql=new UserSqlBean();
                rs=mysql.checkLogin(request, this.getUserName(), this.getPassWord());
                if(!rs.next()){
                    addFieldError("passWord","登录密码错误！");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    //实例化对数据操作的封装类

    /**
     *
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
        UserSqlBean mysql=new UserSqlBean();
        String add=mysql.addList(request, this.getUserName());
        if(add.equals("ok")){
            message=SUCCESS;
        }
        return message;
    }
//    public void message(String msg){
//        int type=JOptionPane.YES_NO_OPTION;
//        String title="信息提示";
//        JOptionPane.showMessageDialog(null,msg,title,type);
//    }
   
}
