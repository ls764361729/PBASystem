/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcBean;

import java.util.List;
import java.util.ArrayList;
import sqlBean.*;
import structBean.*;

/**
 * 根据目标轨迹点与敏感区域坐标点计算敏感分数
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class SenCalcBean {
    private int areaEveRan = 50;       //敏感区域计分范围(m)
    private int tkTimeLag = 5;        //轨迹点间隔时长(min)
    private int minTimeLen = 30;       //最小计分时长(min)
    
    /**
     *
     * @return
     */
    public int getAreaEveRan() {
        return areaEveRan;
    }

    /**
     *
     * @param areaEveRan
     */
    public void setAreaEveRan(int areaEveRan) {
        this.areaEveRan = areaEveRan;
    }

    /**
     *
     * @return
     */
    public int getTkTimeLag() {
        return tkTimeLag;
    }

    /**
     *
     * @param tkTimeLag
     */
    public void setTkTimeLag(int tkTimeLag) {
        this.tkTimeLag = tkTimeLag;
    }

    /**
     *
     * @return
     */
    public int getMinTimeLen() {
        return minTimeLen;
    }

    /**
     *
     * @param minTimeLen
     */
    public void setMinTimeLen(int minTimeLen) {
        this.minTimeLen = minTimeLen;
    }

    /**
     * 计算某台设备某日的敏感评分
     * @param deviceID 设备ID
     * @param pDate 日期
     * @return
     */
    public int calcPepScore(String deviceID,String pDate)
    {
        int Score = 0;
        try
        {
            //读取数据库
            DataSqlBean dataSql = new DataSqlBean();
            
            List <SenAreaBean> senAreaList = GetSenAreaBean.getSenAreaList();
            List <PntListBean> PepDayTrack = dataSql.returnPepDayTrack(deviceID,pDate);

            for (SenAreaBean senArea : senAreaList)
            {
                int calcPntNum = 0;
                int calcPntGra = senArea.getAreaTypeGra();

                //轨迹点与敏感点距离遍历
                for (PntListBean Point : PepDayTrack)
                {
                    double disTemp = MapDisBean.getDistance(Point.getsCoordX(),Point.getsCoordY(),senArea.getaCoordX(),senArea.getaCoordY());
                    if (disTemp <= areaEveRan)    /*距离范围*/
                    {
                        calcPntNum++;
                    }
                }

                //分数计算
                if (calcPntNum >= (minTimeLen/tkTimeLag))   /*假设轨迹点时间间隔为10分钟,故30分钟时间范围为3个点*/
                {
                    Score += calcPntGra * calcPntNum;
                }
            }
            return Score;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    

    /**
     * 计算某台设备上一小时的敏感评分
     * @param deviceID
     * @param pDate
     * @param pTime
     * @return
     */
    
    public int calcPepScore(String deviceID,String pDate,String pTime)
    {
        int Score = 0;
        try
        {
            //读取数据库
            DataSqlBean dataSql = new DataSqlBean();
            
            List <SenAreaBean> senAreaList = GetSenAreaBean.getSenAreaList();
            List <PntListBean> PepDayTrack = dataSql.returnPepDayTrack(deviceID,pDate);

            for (SenAreaBean senArea : senAreaList)
            {
                int calcPntNum = 0;
                int calcPntGra = senArea.getAreaTypeGra();

                //轨迹点与敏感点距离遍历
                for (PntListBean Point : PepDayTrack)
                {
                    String pStrTime = Point.getpTime().replace(":","");
                    String hStrTime = pTime.replace(":","");
                    
                    int pIntTime = Integer.valueOf(pStrTime);
                    int hIntTime = Integer.valueOf(hStrTime);
                    
                    if(pIntTime >= (hIntTime - 100)){
                        
                        double disTemp = MapDisBean.getDistance(Point.getsCoordX(),Point.getsCoordY(),senArea.getaCoordX(),senArea.getaCoordY());
                        if (disTemp <= areaEveRan)    /*距离范围*/
                        {
                            calcPntNum++;
                        }
                    }
                }

                //分数计算
                if (calcPntNum >= (minTimeLen/tkTimeLag))   /*假设轨迹点时间间隔为10分钟,故30分钟时间范围为3个点*/
                {
                    Score += calcPntGra * calcPntNum;
                }
            }
            return Score;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 计算上一个小时的敏感评分（自动存储）
     * @return
     */
    public Boolean calcHourAutoScore()
    {
        
        try
        {
            //读取数据库
            CalcSqlBean calcSql = new CalcSqlBean();
            
            List <SenScoreBean> senScoreList = new ArrayList();
            List <ManaPepBean> manaPepList = calcSql.readManaPep();
            String pDate = GetTimeBean.getDate();
            String pTime = GetTimeBean.getTime();
            
            for(ManaPepBean manaPep : manaPepList){
                String deviceID = manaPep.getDeviceID();
                SenScoreBean senScore = new SenScoreBean(); 
                int Score = calcPepScore(deviceID,pDate,pTime);
                
                senScore.setPepID(manaPep.getPepID());
                senScore.setCalcScore(Score);
                senScore.setCalcTime(pTime);
                senScore.setCalcDate(pDate);
                senScoreList.add(senScore);
            }
            return calcSql.insertAutoCalcScore(senScoreList, pDate, pTime);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 计算昨日敏感评分（自动存储）
     * @return
     */
    public Boolean calcDayAutoScore()
    {
        
        try
        {
            //读取数据库
            CalcSqlBean calcSql = new CalcSqlBean();
            
            List <SenScoreBean> senScoreList = new ArrayList();
            List <ManaPepBean> manaPepList = calcSql.readManaPep();
            
            String pDate = GetTimeBean.getTomorrow();
            String calcTime = GetTimeBean.getTime();

            for(ManaPepBean manaPep : manaPepList){
                String deviceID = manaPep.getDeviceID();
                
                SenScoreBean senScore = new SenScoreBean(); 
                int Score = calcPepScore(deviceID,pDate);
                
                senScore.setCalcScore(Score);
                senScore.setCalcTime(calcTime);
                senScoreList.add(senScore);
            }
            
            return calcSql.insertAutoCalcScore(senScoreList, pDate);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
