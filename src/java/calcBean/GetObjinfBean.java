/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcBean;

import java.util.ArrayList;
import java.util.List;
import sqlBean.*;
import structBean.*;

/**
 * 人员定位点信息读取Bean
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class GetObjinfBean {
    
    /**
     *
     * @return
     */
    public static ArrayList getObjInf(){
        DataSqlBean dataSQL=new DataSqlBean();
        CalcSqlBean calcSQL=new CalcSqlBean();
        
        List <PntListBean> pntList = null;
        List <ManaPepBean> manaPepList = null;
        List <SenScoreBean> senScoreList = null;

        try{
            pntList = dataSQL.returnLastCoord();
            manaPepList = calcSQL.readManaPep();
            senScoreList = calcSQL.readLastHourSenScore();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        ArrayList objinfList = new ArrayList();
        
        if(pntList!=null){
            for(PntListBean pnt : pntList){
                ObjInfBean objInf = new ObjInfBean();
                objInf.setsCoordX(pnt.getsCoordX());
                objInf.setsCoordY(pnt.getsCoordY());
                objInf.setPepID(pnt.getPepID());
                objInf.setpTime(pnt.getpTime());

                if(manaPepList!=null){
                    for(ManaPepBean manaPep : manaPepList){
                        if(manaPep.getPepID()==(pnt.getPepID())){

                            objInf.setPepID(manaPep.getPepID());
                            objInf.setPepName(manaPep.getPepName());
                            objInf.setDeviceID(manaPep.getDeviceID());
                            objInf.setPepIDcardNum(manaPep.getPepIDcardNum());
                            objInf.setPepSex(manaPep.getPepSex());
                            objInf.setPepAge(manaPep.getPepAge());
                            objInf.setStartDate(manaPep.getStartDate());
                            objInf.setManaDayLimit(manaPep.getManaDayLimit());

                            if(senScoreList!=null){
                                for(SenScoreBean senScore : senScoreList){
                                    if(manaPep.getPepID()==senScore.getPepID()){
                                        objInf.setSenScore(senScore.getCalcScore());
                                        objInf.setCalcTime(senScore.getCalcTime());
                                    }
                                    else{
                                        objInf.setSenScore(-1);
                                        objInf.setCalcTime("null");
                                    }
                                }
                            }

                        }
                    }
                }
                
                objinfList.add(objInf);
            }
            return objinfList;
        }
        else 
            return null;
    }
    
    //获取敏感评分信息

    /**
     *
     * @return
     */
    public static ArrayList getObjSenScore(){
        CalcSqlBean calcSQL=new CalcSqlBean();
        
        String pDate = GetTimeBean.getTomorrow();
        List<ManaPepBean> manaPepList = null;
        List<SenScoreBean> senScoreList =null;
        
        try{
            manaPepList = calcSQL.readManaPep();
            senScoreList = calcSQL.readDaySenScore(pDate);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        ArrayList objSenScoreList = new ArrayList();
            
        if(manaPepList != null){
            for(ManaPepBean manaPep : manaPepList){

                ObjInfBean objInf = new ObjInfBean();
                objInf.setCalcDate(pDate);

                objInf.setPepID(manaPep.getPepID());
                objInf.setPepName(manaPep.getPepName());
                objInf.setDeviceID(manaPep.getDeviceID());
                objInf.setPepIDcardNum(manaPep.getPepIDcardNum());
                objInf.setPepSex(manaPep.getPepSex());
                objInf.setPepAge(manaPep.getPepAge());
                objInf.setStartDate(manaPep.getStartDate());
                objInf.setManaDayLimit(manaPep.getManaDayLimit());

                if(senScoreList != null) {
                    for(SenScoreBean senScore : senScoreList){
                        if(manaPep.getPepID()==senScore.getPepID()){
                            objInf.setSenScore(senScore.getCalcScore());
                            objInf.setCalcTime(senScore.getCalcTime());
                        }
                    }
                }
                else{
                    objInf.setSenScore(-1);
                    objInf.setCalcTime(null);
                }

                objSenScoreList.add(objInf);
            }
        }
        
        return objSenScoreList;
    }
}
