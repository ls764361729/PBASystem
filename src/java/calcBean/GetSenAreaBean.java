/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcBean;

import java.util.List;
import sqlBean.*;
import structBean.*;

/**
 * 人员敏感评分数据读取Bean
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class GetSenAreaBean {
    
    /**
     *
     * @return
     */
    public static List <SenAreaBean> getSenAreaList()
    {
        try
        {
            //读取数据库
            CalcSqlBean calcSql = new CalcSqlBean();
            
            List <SenAreaBean> senAreaList = calcSql.readSenArea();
            List <SenAreaGraBean> senAreaGraList = calcSql.readSenAreaGra();

            for (SenAreaBean senArea : senAreaList)
            {
                //敏感点评分查询
                for (SenAreaGraBean senAreaGra : senAreaGraList)
                {
                    if (senAreaGra.getAreaTypeID() == senArea.getAreaTypeID())
                    {
                        senArea.setAreaTypeGra(senAreaGra.getAreaTypeGra());
                        senArea.setAreaTypeName(senAreaGra.getAreaTypeName());
                        break;
                    }
                }
            }
            return senAreaList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
}
