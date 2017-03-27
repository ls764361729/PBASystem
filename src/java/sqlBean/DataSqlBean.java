package sqlBean;

import structBean.*;
import calcBean.GetTimeBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Data Sql Bean
 * xwfxdatadb 数据库的操作类
 * @author Meteor
 * @version 0.6.2 2016/8/21
 */

public class DataSqlBean implements ServletRequestAware{
    
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
            return con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    //选择某日(pDate)的所有数据表

    /**
     *
     * @param pDate
     * @return
     */
    public ArrayList selectDayTable(String pDate){
        try{
            String sql="SHOW TABLES LIKE '%" + pDate + "'";
            st = getStatement();
            rs = st.executeQuery(sql);
            ArrayList tableList=new ArrayList();
            while(rs.next()){
                tableList.add(rs.getString(1));
            }
            return tableList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    //选择数据表

    /**
     *
     * @param tableName
     * @return
     */
    public ResultSet selectOBJ(String tableName){
        try{
            String sql="SELECT * FROM " + tableName;
            st=getStatement();
            return st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    //返回最新的定位数据

    /**
     *
     * @return
     */
    public ArrayList returnLastCoord(){
        try{
            ArrayList pntList = new ArrayList();
            String pDate = GetTimeBean.getDate();
            List <String> tableName = selectDayTable(pDate);
            for (String pTable: tableName){
                rs=selectOBJ(pTable);
                if(rs.last()){
                    PntListBean pnt =new PntListBean();
                    pnt.setDeviceID(pTable.substring(0, pTable.indexOf("_"))); //去掉pTable结尾的日期
                    pnt.setpTime(rs.getString("pTime"));
                    pnt.setsCoordX(rs.getString("sCoordX"));
                    pnt.setsCoordY(rs.getString("sCoordY"));
                    pntList.add(pnt);
                }
            }
            closeCon();
            return pntList;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    //数据库自检在线人数

    /**
     *
     * @param pDate
     * @return
     */
    public int loadOnlinePep(String pDate){
        try{
            ArrayList tableName = selectDayTable(pDate);
            closeCon();
            return tableName.size();
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return 0;
        }
    }
    
    //返回某日的所有轨迹数据

    /**
     *
     * @param pDate
     * @return
     */
    public ArrayList returnDayTrack(String pDate){
        try{
            ArrayList pntListCol = new ArrayList();
            ArrayList tableName = selectDayTable(pDate);
            for (Object pTable: tableName){
                ArrayList pntList = new ArrayList();
                rs=selectOBJ(pTable.toString());
                while(rs.next()){
                    PntListBean pnt =new PntListBean();
                    pnt.setDeviceID(pTable.toString());
                    pnt.setpTime(rs.getString("pTime"));
                    pnt.setsCoordX(rs.getString("sCoordX"));
                    pnt.setsCoordY(rs.getString("sCoordY"));
                    pntList.add(pnt);
                }
                pntListCol.add(pntList);
            }
            closeCon();
            return pntListCol;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    //选择某设备(deviceID)某日(pDate)的所有数据表

    /**
     *
     * @param deviceID
     * @param pDate
     * @return
     */
    public ArrayList selectDayTable(String deviceID,String pDate){
        try{
            int i = 0;
            String sql="SELECT * FROM trkpnt_" + deviceID + "_" + pDate;
            st = getStatement();
            rs = st.executeQuery(sql);
            ArrayList tableList=new ArrayList();
            while(rs.next()){
                tableList.add(rs.getString(1));
                i++;
            }
            return tableList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    //返回某设备(deviceID)某日(pDate)的所有数据

    /**
     *
     * @param deviceID
     * @param pDate
     * @return
     */
    public ArrayList returnPepDayTrack(String deviceID,String pDate){
        try{
            String sql="SELECT * FROM trkpnt_" + deviceID + "_" + pDate;
            st = getStatement();
            rs = st.executeQuery(sql);
            ArrayList pntList = new ArrayList();
            while(rs.next()){
                PntListBean pnt =new PntListBean();
                pnt.setDeviceID(deviceID);
                pnt.setpTime(rs.getString("pTime"));
                pnt.setsCoordX(rs.getString("sCoordX"));
                pnt.setsCoordY(rs.getString("sCoordY"));
                pntList.add(pnt);
            }
            closeCon();
            return pntList;
        }catch(Exception e){
            e.printStackTrace();
            closeCon();
            return null;
        }
    }
    
    /**
     *
     * @param request
     * @param userName
     * @return
     */
    public ResultSet selectObjHistory(HttpServletRequest request,String userName){
        try{
            String sql="select * from user where userName='"+userName+"'";
            st=getStatement();
            closeCon();
            return st.executeQuery(sql);
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
                st.close();//关闭Statement
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
}
