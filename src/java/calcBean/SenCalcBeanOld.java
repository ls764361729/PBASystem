///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package calcBean;
//
//import java.util.List;
//import java.util.ArrayList;
//import sqlBean.*;
//import structBean.*;
//
///**
// * 根据目标轨迹点与敏感区域坐标点计算敏感分数
// * @since 2016/09/10
// * @version 0.8.1
// * @author 孙晨星
// */
//
//public class SenCalcBean {
//    private int areaEveRan = 50;       //敏感区域计分范围(m)
//    private int tkTimeLag = 5;        //轨迹点间隔时长(min)
//    private int minTimeLen = 30;       //最小计分时长(min)
//
//    /**
//     * 计算某台设备某日的敏感评分
//     * @param pepID pepID
//     * @param pDate 日期
//     * @return
//     */
//    public int calcPepScore(int pepID, String pDate)
//    {
//        int Score = 0;
//        try
//        {
//            //读取数据库
//            DataSqlBean dataSql = new DataSqlBean();
//
//            List <SenAreaBean> senAreaList = GetSenAreaBean.getSenAreaList();
//            List <PntListBean> PepDayTrack = dataSql.returnPepDayTrack(pepID,pDate);
//
//            for (SenAreaBean senArea : senAreaList)
//            {
//                int calcPntNum = 0;
//                int calcPntGra = senArea.getAreaTypeGra();
//
//                //轨迹点与敏感点距离遍历
//                for (PntListBean Point : PepDayTrack)
//                {
//                    double disTemp = MapDisBean.getDistance(Point.getsCoordX(),Point.getsCoordY(),senArea.getaCoordX(),senArea.getaCoordY());
//                    if (disTemp <= areaEveRan)    /*距离范围*/
//                    {
//                        calcPntNum++;
//                    }
//                }
//
//                //分数计算
//                if (calcPntNum >= (minTimeLen/tkTimeLag))   /*假设轨迹点时间间隔为10分钟,故30分钟时间范围为3个点*/
//                {
//                    Score += calcPntGra * calcPntNum;
//                }
//            }
//            return Score;
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//
//    /**
//     * 计算某台设备上一小时的敏感评分
//     * @param pepID
//     * @param pDate
//     * @param pTime
//     * @return
//     */
//
//    public int calcPepScore(int pepID, String pDate, String pTime)
//    {
//        int Score = 0;
//        try
//        {
//            //读取数据库
//            DataSqlBean dataSql = new DataSqlBean();
//
//            List <SenAreaBean> senAreaList = GetSenAreaBean.getSenAreaList();
//            List <PntListBean> PepDayTrack = dataSql.returnPepDayTrack(pepID,pDate);
//
//            for (SenAreaBean senArea : senAreaList)
//            {
//                int calcPntNum = 0;
//                int calcPntGra = senArea.getAreaTypeGra();
//
//                //轨迹点与敏感点距离遍历
//                for (PntListBean Point : PepDayTrack)
//                {
//                    String pStrTime = Point.getpTime().replace(":","");
//                    String hStrTime = pTime.replace(":","");
//
//                    int pIntTime = Integer.valueOf(pStrTime);
//                    int hIntTime = Integer.valueOf(hStrTime);
//
//                    if(pIntTime >= (hIntTime - 100)){
//
//                        double disTemp = MapDisBean.getDistance(Point.getsCoordX(),Point.getsCoordY(),senArea.getaCoordX(),senArea.getaCoordY());
//                        if (disTemp <= areaEveRan)    /*距离范围*/
//                        {
//                            calcPntNum++;
//                        }
//                    }
//                }
//
//                //分数计算
//                if (calcPntNum >= (minTimeLen/tkTimeLag))   /*假设轨迹点时间间隔为10分钟,故30分钟时间范围为3个点*/
//                {
//                    Score += calcPntGra * calcPntNum;
//                }
//            }
//            return Score;
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    /**
//     * 计算上一个小时的敏感评分（自动存储）
//     * @return
//     */
//    public Boolean calcHourAutoScore()
//    {
//
//        try
//        {
//            //读取数据库
//            CalcSqlBean calcSql = new CalcSqlBean();
//
//            List <SenScoreBean> senScoreList = new ArrayList();
//            List <ManaPepBean> manaPepList = calcSql.readManaPep();
//            String pDate = GetTimeBean.getDate();
//            String pTime = GetTimeBean.getTime();
//
//            for(ManaPepBean manaPep : manaPepList){
//                int pepID = manaPep.getPepID();
//                SenScoreBean senScore = new SenScoreBean();
//                int Score = calcPepScore(pepID,pDate,pTime);
//
//                senScore.setPepID(manaPep.getPepID());
//                senScore.setCalcScore(Score);
//                senScore.setCalcTime(pTime);
//                senScore.setCalcDate(pDate);
//                senScoreList.add(senScore);
//            }
//            return calcSql.insertAutoCalcScore(senScoreList, pDate, pTime);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 计算昨日敏感评分（自动存储）
//     * @return
//     */
//    public Boolean calcDayAutoScore()
//    {
//
//        try
//        {
//            //读取数据库
//            CalcSqlBean calcSql = new CalcSqlBean();
//
//            List <SenScoreBean> senScoreList = new ArrayList();
//            List <ManaPepBean> manaPepList = calcSql.readManaPep();
//
//            String pDate = GetTimeBean.getTomorrow();
//            String calcTime = GetTimeBean.getTime();
//
//            for(ManaPepBean manaPep : manaPepList){
//                int pepID = manaPep.getPepID();
//
//                SenScoreBean senScore = new SenScoreBean();
//                int Score = calcPepScore(pepID,pDate);
//
//                senScore.setCalcScore(Score);
//                senScore.setCalcTime(calcTime);
//                senScoreList.add(senScore);
//            }
//
//            return calcSql.insertAutoCalcScore(senScoreList, pDate);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
