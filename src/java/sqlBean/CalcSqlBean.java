package sqlBean;

import structBean.*;
import calcBean.GetTimeBean;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Calc Sql Bean
 * xwfxdb 与 xwfxcalcdb 数据库的操作类
 * @author Meteor
 * @version 0.6.2 2016/8/21
 */

public class CalcSqlBean implements ServletRequestAware {

    private String uri = "jdbc:mysql://115.159.187.179:3306/xwfxdb?useUnicode=true&characterEncoding=gbk";
    private String uric = "jdbc:mysql://115.159.187.179:3306/xwfxcalcdb?useUnicode=true&characterEncoding=gbk";
    private String driver = "com.mysql.jdbc.Driver";
    private String user = "gsql";
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
    public String getUric() {
        return uric;
    }

    /**
     *
     * @param uric
     */
    public void setUric(String uric) {
        this.uric = uric;
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

    /**
     * 连接数据库：生成Statment容器并返回之
     * @return
     */
    public Statement getStatement() {
        try {
            Class.forName(driver);//指定连接类型
            con = DriverManager.getConnection(uri, user, password);//获取连接
            return con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calc数据库连接
     * @return
     */
    public Statement getCalcStatement() {
        try {
            Class.forName(driver);//指定连接类型
            con = DriverManager.getConnection(uric, user, password);//获取连接
            return con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 选择基础数据表
     * @param tableName
     * @return
     */
    public ResultSet selectTable(String tableName) {
        try {
            String sql = "SELECT * FROM " + tableName;
            st = getStatement();
            return st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 选择Calc数据表
     * @param tableName
     * @return
     */
    public ResultSet selectCalc(String tableName) {
        try {
            String sql = "SELECT * FROM " + tableName;
            st = getCalcStatement();
            return st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 系统自检:检查数据数据库内容
     * @return
     */
    public int loadDatabasePep() {
        try {
            String sql = "SELECT COUNT(*) FROM manapep";
            int databasePep = 0;
            st = getStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                databasePep = rs.getInt(1);
            }
            closeCon();
            return databasePep;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return 0;
        }
    }

    /**
     * 敏感点评分数据读取
     * @return
     */
    public ArrayList readSenAreaGra() {
        try {
            ArrayList SenAreaGraList = new ArrayList();
            rs = selectTable("senareatype");
            while (rs.next()) {
                SenAreaGraBean SenAreaGra = new SenAreaGraBean();
                SenAreaGra.setAreaTypeID(rs.getInt("areaTypeID"));
                SenAreaGra.setAreaTypeName(rs.getString("areaTypeName"));
                SenAreaGra.setAreaTypeGra(rs.getInt("areaTypeGra"));
                SenAreaGraList.add(SenAreaGra);
            }
            closeCon();
            return SenAreaGraList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * 敏感点数据读取
     * @return
     */
    public ArrayList readSenArea() {
        try {
            ArrayList SenAreaList = new ArrayList();
            rs = selectTable("senarea");
            while (rs.next()) {
                SenAreaBean SenArea = new SenAreaBean();
                SenArea.setaID(rs.getInt("aID"));
                SenArea.setAreaTypeID(rs.getInt("areaTypeID"));
                SenArea.setAreaName(rs.getString("areaName"));
                SenArea.setaCoordX(rs.getString("aCoordX"));
                SenArea.setaCoordY(rs.getString("aCoordY"));
                SenAreaList.add(SenArea);
            }
            closeCon();
            return SenAreaList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * 监控人员数据读取
     * @return
     */
    public ArrayList readManaPep() {
        try {
            ArrayList ManaPepList = new ArrayList();
            rs = selectTable("manapep");
            while (rs.next()) {
                ManaPepBean manaPep = new ManaPepBean();
                manaPep.setPepID(rs.getInt("pepID"));
                manaPep.setPepName(rs.getString("pepName"));
                manaPep.setDeviceID(rs.getString("deviceID"));
                manaPep.setPepIDcardNum(rs.getString("pepIDcardNum"));
                manaPep.setPepSex(rs.getString("pepSex"));
                manaPep.setPepAge(rs.getString("pepAge"));
                manaPep.setStartDate(rs.getString("startDate"));
                manaPep.setManaDayLimit(rs.getInt("manaDayLimit"));
                ManaPepList.add(manaPep);
            }
            closeCon();
            return ManaPepList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * 矫正人员查询
     * @param queryType
     * @param queryInput
     * @return
     */
    public ArrayList queryManaPep(String queryType, String queryInput) {
        try {
            String sql = null;
            ArrayList ManaPepList = new ArrayList();
            if (queryType.equals("pepID") || queryType.equals("manaDayLimit")) {
                sql = "SELECT * FROM manapep WHERE " + queryType + "='" + Integer.valueOf(queryInput) + "'";
            } else {
                sql = "SELECT * FROM manapep WHERE " + queryType + "='" + queryInput + "'";
            }

            st = getStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ManaPepBean manaPep = new ManaPepBean();
                manaPep.setPepID(rs.getInt("pepID"));
                manaPep.setPepName(rs.getString("pepName"));
                manaPep.setDeviceID(rs.getString("deviceID"));
                manaPep.setPepIDcardNum(rs.getString("pepIDcardNum"));
                manaPep.setPepSex(rs.getString("pepSex"));
                manaPep.setPepAge(rs.getString("pepAge"));
                manaPep.setStartDate(rs.getString("startDate"));
                manaPep.setManaDayLimit(rs.getInt("manaDayLimit"));
                ManaPepList.add(manaPep);
            }
            closeCon();
            return ManaPepList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * CreateIf(日)语句
     * @param pDate
     * @return
     */
    public Boolean createIF(String pDate) {

        if (pDate == null) {
            return false;
        }

        // 获取Sql查询语句
        String sql = "create table if not exists scorecalc_dayauto_" + pDate + " like scorecalc_simple;";

        try {
            st = getCalcStatement();
            int row = st.executeUpdate(sql);
            if (row == 0) {
                System.out.println("Table scorecalc_dayauto_" + pDate + "OK!");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * CreatIF(小时)
     * @param pDate
     * @param pTime
     * @return
     */
    public Boolean createIF(String pDate, String pTime) {

        if (pDate == null) {
            return false;
        }

        // 获取Sql查询语句
        String sql = "create table if not exists scorecalc_hourauto_" + pDate + " like scorecalc_simple;";

        try {
            st = getCalcStatement();
            int row = st.executeUpdate(sql);
            if (row == 0) {
                System.out.println("Table scorecalc_hourauto_" + pDate + "OK!");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * (日)敏感评分数据保存
     * @param senScoreList
     * @param pDate
     * @return
     */
    public Boolean insertAutoCalcScore(List<SenScoreBean> senScoreList, String pDate) {
        Boolean flag = false;
        try {
            //准备数据表
            createIF(pDate);

            //添加数据
            for (SenScoreBean senScore : senScoreList) {
                String sql = "insert into scorecalc_dayauto_" + pDate
                        + "(pepID,calcScore,calcTime) "
                        + "values('" + senScore.getPepID() + "','" + senScore.getCalcScore() + "','" + senScore.getCalcTime() + "')";
                st = getCalcStatement();
                int row = st.executeUpdate(sql);
                if (row == 1) {
                    flag = true;
                }
            }
            closeCon();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return flag;
        }
    }

    /**
     * 保存分数计算数据（小时）
     * @param senScoreList
     * @param pDate
     * @param pTime
     * @return
     */
    public Boolean insertAutoCalcScore(List<SenScoreBean> senScoreList, String pDate, String pTime) {
        Boolean flag = false;
        try {
            //准备数据表
            createIF(pDate,pTime);

            //添加数据
            for (SenScoreBean senScore : senScoreList) {
                String sql = "insert into scorecalc_hourauto_" + pDate
                        + "(pepID,calcScore,calcTime) "
                        + "values('" + senScore.getPepID() + "','" + senScore.getCalcScore() + "','" + senScore.getCalcTime() + "')";
                st = getCalcStatement();
                int row = st.executeUpdate(sql);
                if (row == 1) {
                    flag = true;
                }
            }
            closeCon();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return flag;
        }
    }

    /**
     * （前一小时）敏感评分读取
     * @return
     */
    public ArrayList readLastHourSenScore() {
        try {
            ArrayList SenScoreList = new ArrayList();
            String pDate = GetTimeBean.getDate();
            String pTime = GetTimeBean.getTime();
            int lastHour = Integer.valueOf(pTime);
            if (!(lastHour < 10000)) {
                lastHour = (lastHour / 10000);
                pTime = String.valueOf(lastHour);
            } else {
                pTime = "00";
            }
            rs = selectCalc("scorecalc_hourauto_" + pDate + " where calcTime like '"+pTime+"%'");

            while (rs.next()) {
                SenScoreBean senScore = new SenScoreBean();
                senScore.setCalcTime(rs.getString("calcTime"));
                senScore.setCalcScore(rs.getInt("calcScore"));
                senScore.setPepID(rs.getInt("pepID"));
                SenScoreList.add(senScore);
            }
            closeCon();
            return SenScoreList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * 读取日敏感分数数据
     * @param pDate
     * @return
     */
    public ArrayList readDaySenScore(String pDate) {
        try {
            ArrayList SenScoreList = new ArrayList();

            rs = selectCalc("scorecalc_dayauto_" + pDate +" ORDER BY calcScore");

            while (rs.next()) {
                SenScoreBean senScore = new SenScoreBean();
                senScore.setCalcDate(pDate);
                senScore.setCalcTime(rs.getString("calcTime"));
                senScore.setCalcScore(rs.getInt("calcScore"));
                senScore.setPepID(rs.getInt("pepID"));
                SenScoreList.add(senScore);
            }
            closeCon();
            return SenScoreList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * 添加监控对象，保存到manapep数据表中
     * @param manaPep
     * @return
     */
    public Boolean insertManaPep(ManaPepBean manaPep) {
        Boolean flag = false;
        try {
            //添加数据
            st = getStatement();
            String sql = "select * from manapep where pepIDcardNum=" + manaPep.getPepIDcardNum();
            rs = st.executeQuery(sql);
            if (!rs.next()) {
                sql = "insert into manapep(pepName,deviceID,pepIDcardNum,pepSex,pepAge,startDate,manaDayLimit) "
                        + "values('" + manaPep.getPepName() + "','" + manaPep.getDeviceID() + "','" + manaPep.getPepIDcardNum() + "','" + manaPep.getPepSex() + "','" + manaPep.getPepAge() + "','" + manaPep.getStartDate() + "'," + manaPep.getManaDayLimit() + ")";
                st = getStatement();
                int row = st.executeUpdate(sql);
                if (row == 1) {
                    flag = true;
                }
            } else {
                System.out.println("身份证号重复，添加失败。");
            }
            closeCon();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return flag;
        }
    }
    
    /**
     * 添加敏感区域
     * @param senArea
     * @return
     */
    public Boolean insertSenArea(SenAreaBean senArea) {
        Boolean flag = false;
        try {
            //添加数据
            String sql = "insert into senarea(areaName,areaTypeID,aCoordX,aCoordY) "
                    + "values('" + senArea.getAreaName() + "','" + senArea.getAreaTypeID() + "','" + senArea.getaCoordX() + "','" + senArea.getaCoordY() + "')";
            st = getStatement();
            int row = st.executeUpdate(sql);
            if (row == 1) {
                flag = true;
            }
            closeCon();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return flag;
        }
    }

    /**
     * 读取监控区域
     * @return
     */
    public ArrayList readAtArea() {
        try {
            ArrayList AtAreaList = new ArrayList();

            rs = selectTable("atarea");
            int bAtID = 1;
            int atID = 1;
            AtAreaBean atArea = new AtAreaBean();
            ArrayList atCoordXs = new ArrayList();
            ArrayList atCoordYs = new ArrayList();

            while (rs.next()) {
                atID = rs.getInt("alertAreaID");
                if (atID != bAtID) {
                    atArea.setAtCoordXs((String[]) atCoordXs.toArray(new String[atCoordXs.size()]));
                    atArea.setAtCoordYs((String[]) atCoordYs.toArray(new String[atCoordYs.size()]));
                    AtAreaList.add(atArea);

                    atArea = new AtAreaBean();
                    atCoordXs = new ArrayList();
                    atCoordYs = new ArrayList();
                }

                atArea.setAlertAreaName(rs.getString("alertAreaName"));
                atArea.setAlertAreaID(rs.getInt("alertAreaID"));
                atCoordXs.add(rs.getString("atCoordX"));
                atCoordYs.add(rs.getString("atCoordY"));
                bAtID = atID;
            }
            atArea.setAtCoordXs((String[]) atCoordXs.toArray(new String[atCoordXs.size()]));
            atArea.setAtCoordYs((String[]) atCoordYs.toArray(new String[atCoordYs.size()]));
            AtAreaList.add(atArea);

            closeCon();
            return AtAreaList;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return null;
        }
    }

    /**
     * 添加监控区域
     * @param atArea
     * @return
     */
    public Boolean insertAtArea(AtAreaBean atArea) {
        Boolean flag = true;
        String sql = "select alertAreaID from atarea";
        int atAreaID = 1;
        try {
            st = getStatement();
            rs = st.executeQuery(sql);
            if (rs.last()) {
                atAreaID = rs.getInt(1) + 1;
            }
            closeCon();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] atCoordXs = atArea.getAtCoordXs();
        String[] atCoordYs = atArea.getAtCoordYs();

        int atIndexNum = atCoordXs.length;
        for (int i = 0; i < atIndexNum; i++) {
            if (!insertAtAreaPnt(atArea.getAlertAreaName(), atAreaID, i, atCoordXs[i], atCoordYs[i])) {
                flag = false;
            }
        }

        return flag;
    }

    /**
     * 添加监控区域点
     * @param alertAreaName
     * @param alertAreaID
     * @param atpID
     * @param atCoordX
     * @param atCoordY
     * @return
     */
    public Boolean insertAtAreaPnt(String alertAreaName, int alertAreaID, int atpID, String atCoordX, String atCoordY) {
        Boolean flag = false;
        try {
            //添加数据
            String sql = "insert into atarea(alertAreaName,alertAreaID,atpID,atCoordX,atCoordY) "
                    + "values('" + alertAreaName + "','" + alertAreaID + "','" + atpID + "','" + atCoordX + "','" + atCoordY + "')";
            st = getStatement();
            int row = st.executeUpdate(sql);
            if (row == 1) {
                flag = true;
            }
            closeCon();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            closeCon();
            return flag;
        }
    }

    /**
     * 关闭数据库连接
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
