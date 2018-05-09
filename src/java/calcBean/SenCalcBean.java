/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcBean;

import sqlBean.CalcSqlBean;
import sqlBean.DataSqlBean;
import structBean.ManaPepBean;
import structBean.PntListBean;
import structBean.SenAreaBean;
import structBean.SenScoreBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 根据目标轨迹点与敏感区域坐标点计算敏感分数
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class SenCalcBean {
    private int areaEveRan = 100;       //敏感区域计分范围(m)
    private int minTimeLen = 20;       //最小计分时长(min)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   // 设置传入的时间格式


    /**
     * 计算某台设备某日的敏感评分
     * @param pepID pepID
     * @param pDate 日期
     * @return
     */
    public int calcPepScore(int pepID, String pDate)
    {
        int Score = 0;
        
        try
        {
            //读取数据库
            DataSqlBean dataSql = new DataSqlBean();
            
            List <SenAreaBean> senAreaList = GetSenAreaBean.getSenAreaList();
            List <PntListBean> PepDayTrack = dataSql.returnPepDayTrack(pepID,pDate);
            
            if(PepDayTrack.isEmpty()){
                return -1;
            } 
                
            for (SenAreaBean senArea : senAreaList)
            {
                int calcPntGra = senArea.getAreaTypeGra();
                Date startTime = new Date();
                Date endTime;
                int countTime = 0;

                //轨迹点与敏感点距离遍历
                int i = 0;
                int flag = 0;
                for (PntListBean Point : PepDayTrack)
                {
                    Point.setAreaID(0);
                    double disTemp = MapDisBean.getDistance(Point.getsCoordX(),Point.getsCoordY(),senArea.getaCoordX(),senArea.getaCoordY());
                    if (disTemp <= areaEveRan)    /*距离范围*/
                    {
                        Point.setAreaID(senArea.getaID());
                        if(i!=0 && PepDayTrack.get(i-1).getAreaID()==0){
                            startTime = dateFormat.parse(Point.getpTime());
                            flag = 1;
                        }
                        else if(i==0){
                            startTime = dateFormat.parse(Point.getpTime());
                            flag = 1;
                        }
                    }
                    if (Point.getAreaID()==0 && flag==1){
                        endTime = dateFormat.parse(Point.getpTime());
                        countTime += (startTime.getTime()-endTime.getTime())/(1000*60);
                        flag = 0;
                    }
                    i++;
                }

                //分数计算
                if (countTime >= minTimeLen)
                {
                    Score += calcPntGra * countTime;
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
     * @param pepID
     * @param pDate
     * @param pTime
     * @return
     */

    public int calcPepScore(int pepID, String pDate, String pTime)
    {
        int Score = 0;
        try
        {
            //读取数据库
            DataSqlBean dataSql = new DataSqlBean();

            List <SenAreaBean> senAreaList = GetSenAreaBean.getSenAreaList();
            List <PntListBean> PepDayTrack = dataSql.returnPepDayTrack(pepID,pDate);

            for (SenAreaBean senArea : senAreaList)
            {
                int countTime = 0;
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
                        if (disTemp <= areaEveRan)   /*距离范围*/
                        {
                            calcPntNum++;
                            countTime += (dateFormat.parse(Point.getpTime()).getTime()-dateFormat.parse(pDate).getTime())/(1000*60);
                        }
                    }
                }

                //分数计算
                if (countTime >= minTimeLen)
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
    public Boolean calcHourScore(String pDate, String pTime)
    {

        try
        {
            //读取数据库
            CalcSqlBean calcSql = new CalcSqlBean();

            List <SenScoreBean> senScoreList = new ArrayList();
            List <ManaPepBean> manaPepList = calcSql.readManaPep();


            for(ManaPepBean manaPep : manaPepList){
                int pepID = manaPep.getPepID();
                SenScoreBean senScore = new SenScoreBean();
                int Score = calcPepScore(pepID,pDate,pTime);

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
     * @param pDate
     * @return
     */
    public Boolean calcDayScore(String pDate)
    {
        
        try
        {
            //读取数据库
            CalcSqlBean calcSql = new CalcSqlBean();
            
            List <SenScoreBean> senScoreList = new ArrayList();
            List <ManaPepBean> manaPepList = calcSql.readManaPep();

            for(ManaPepBean manaPep : manaPepList){
                int pepID = manaPep.getPepID();
                
                SenScoreBean senScore = new SenScoreBean(); 
                int Score = calcPepScore(pepID,pDate);
                
                senScore.setPepID(pepID);
                senScore.setCalcScore(Score);
                senScore.setCalcTime(dateFormat.format(new Date()));
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
