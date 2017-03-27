package sqlBean;

import structBean.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;


/**
 * User Sql Bean
 * xwfxdb 数据库的操作类 用于支持用户数据库的数据操作
 * @author Meteor
 * @version 0.6.2 2016/8/21
 */

public class UserSqlBean implements ServletRequestAware{
    
    private String uri = "jdbc:mysql://localhost:3306/pbas";
    private String driver = "com.mysql.jdbc.Driver";
    private String user = "pbas";
    private String password = "19960724";

    private static Connection con = null;
    private static Statement st = null;
    private static ResultSet rs = null;
    private static HttpServletRequest request;
    
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
    public String getUri() {
        return uri;
    }

    /**
     *
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     *
     * @return
     */
    public String getDriver() {
        return driver;
    }

    /**
     *
     * @param driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    //连接数据库：生成Statment容器并返回之

    /**
     *
     * @return
     */
    public Statement getStatement(){
        try {
            Class.forName(driver);//指定连接类型
            con = DriverManager.getConnection(uri, user, password);//获取连接
            return con.createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //完成注册，把用户的注册信息录入到数据库中

    /**
     *
     * @param request
     * @param userName
     * @param passWord
     * @param from
     * @param realName
     * @param userType
     * @param userAuths
     * @return
     */
    public String insertMess(HttpServletRequest request,String userName,String passWord,String from,String realName,String userType,String userAuths){
        try{
            String flag=null;
            rs=selectMess(request,userName);
            //判断是否用户名已存在，如果存在返回one
            if(rs.next()){
                flag="one";
            }else{
                String sql="insert into user"+"(userName,passWord,from,realName,userType,userAuths)"+"values("+"'"+userName+"'"+","+"'"+passWord+"'"+","+"'"+from+"'"+","+"'"+realName+"'"+","+"'"+userAuths+"'"+","+"'"+userType+"'"+")";
                st=getStatement();
                int row=st.executeUpdate(sql);
                if(row==1){
                    //调用，myMessage方法，更新session中保存的用户信息
                    String mess=myMessage(request,userName);
                    if(mess.equals("ok")){
                        flag="ok";
                    }else{
                        flag=null;
                    }
                }else{
                    flag=null;
                }
            }
            closeCon();
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    //更新注册的个人信息

    /**
     *
     * @param request
     * @param userName
     * @param passWord
     * @param from
     * @param realName
     * @param userType
     * @param userAuths
     * @return
     */
    public String updateMess(HttpServletRequest request,String userName,String passWord,String from,String realName,String userType,String userAuths){
        try{
            String flag=null;
            String sql="update user set realName='"+realName+"',userType='"+userType+"',userAuths='"+userAuths+"' where userName='"+userName+"'";
            st=getStatement();
            int row=st.executeUpdate(sql);
            if(row==1){
                //调用，myMessage方法，更新session中保存的用户信息
                String mess=myMessage(request,userName);
                if(mess.equals("ok")){
                    flag="ok";
                }
                else{
                    flag=null;
                }
            }
            else{
                flag=null;
            }
            closeCon();
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    //把个人信息通过MyMessBean，保存到session对象中

    /**
     *
     * @param request
     * @param userName
     * @return
     */
    public String myMessage(HttpServletRequest request,String userName){
        try{
            HttpSession session=request.getSession();
            rs=selectMess(request,userName);
            while(rs.next()){
                session.setAttribute("userRealName",rs.getString("realName"));
                session.setAttribute("userFrom",rs.getString("from"));
                session.setAttribute("userActivity",rs.getBoolean("activity"));
                session.setAttribute("userType",rs.getString("userType"));
                session.setAttribute("userAuth",rs.getString("userAuths"));
            }
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    //查询个人信息，并返回 ResultSet

    /**
     *
     * @param request
     * @param userName
     * @return
     */
    public ResultSet selectMess(HttpServletRequest request,String userName){
        try{
            String sql="select * from user where userName='"+userName+"'";
            st=getStatement();
            return st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    //检查用户名与密码

    /**
     *
     * @param request
     * @param userName
     * @param passWord
     * @return
     */
    public ResultSet checkLogin(HttpServletRequest request,String userName,String passWord){
        try{
            String sql="select * from user where userName= '"+userName+"' and passWord= '"+passWord+"'";
            st=getStatement();
            closeCon();
            return st.executeQuery(sql);
        }
        catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    //把登录人的信息保存到session对象中

    /**
     *
     * @param request
     * @param userName
     * @return
     */
    public String loginMes(HttpServletRequest request,String userName){
        try{
            ArrayList userMesList=null;
            HttpSession session=request.getSession();
            userMesList=new ArrayList();
            rs=selectMess(request,userName);
            if(rs.next()){
                rs=selectMess(request,userName);
                while(rs.next()){
                    session.setAttribute("userName",rs.getString("userName"));
                }
            }else{
                session.setAttribute("userName", null);
            }
            closeCon();
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    //返回登录用户的用户名

    /**
     *
     * @param request
     * @return
     */
    public String returnLogin(HttpServletRequest request){
        String LoginName=null;
        HttpSession session=request.getSession();
        ArrayList login=(ArrayList)session.getAttribute("userName");
            if(login==null||login.size()==0){
                LoginName=null;
            }else{
                for(int i=login.size()-1;i>=0;i--){
                    UserNameBean userName=(UserNameBean)login.get(i);
                    LoginName=userName.getUserName();
                }
            }
            return LoginName;
    }
    
    //调用myLogin、myMessage方法，把所有的和用户有关的信息全部保存到session对象中（登录成功后调用该方法）

    /**
     *
     * @param request
     * @param userName
     * @return
     */
    public String addList(HttpServletRequest request,String userName){
        String flag=null;
        String login=loginMes(request,userName);
        String mess=myMessage(request,userName);

        if(login.equals("ok")&&mess.equals("ok")){
            flag="ok";
        }else{
            flag=null;
        }
        closeCon();
        return flag;
    }
    
    //修改用户密码

    /**
     *
     * @param request
     * @param userName
     * @param oldPassword
     * @param newPassWord
     * @return
     */
    public Boolean updatePass(HttpServletRequest request,String userName,String oldPassword,String newPassWord){
        try{
            Boolean flag = false;
            String oPassword = null;
            st=getStatement();
            
            String sql = "select password from user where userName='"+userName+"'";
            rs = st.executeQuery(sql);
            if(rs.next()){
                if(!oldPassword.equals(rs.getString(1))){
                    closeCon();
                    return false;
                }
            }
            else{
                closeCon();
                return false;
            }
            
            sql = "update user set passWord='"+newPassWord+"' where userName='"+userName+"'";
            int row=st.executeUpdate(sql);
            if(row==1){
                String mess=loginMes(request,userName);
                if(mess.equals("ok")){
                    flag=true;
                }else{
                    flag=false;
                }
            }else{
                flag=false;
            }
            closeCon();
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return false;
        }
    }
    
    //获取用户列表

    /**
     *
     * @return
     */
    public ArrayList getUserList(){
        try{
            String sql="select * from user";
            ArrayList userList = new ArrayList();
            st=getStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                UserMessBean userMess = new UserMessBean();
                userMess.setuID(rs.getInt("uID"));
                userMess.setUserName(rs.getString("userName"));
                userMess.setRealName(rs.getString("realName"));
                userMess.setFrom(rs.getString("from"));
                userMess.setUserAuths(rs.getString("userAuths"));
                userMess.setUserType(rs.getString("userType"));
                userMess.setActivity(rs.getBoolean("activity"));
                userList.add(userMess);
            }
            closeCon();
            return userList;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    //关闭数据库连接

    /**
     *
     */
    public static void closeCon() {

        try {
            if (rs != null) {//如果返回的结果集对象不能为空,就关闭连接
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (st != null) {
                st.close();//关闭预编译对象
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (con != null) {
                con.close();//关闭结果集对象
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //一个带参数的错误处理信息提示框

    /**
     *
     * @param msg
     */
    public void message(String msg){
        int type=JOptionPane.YES_NO_OPTION;
        String title="信息提示";
        JOptionPane.showMessageDialog(null,msg,title,type);
    }
    
}  
