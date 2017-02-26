/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 敏感区域评分数据结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class SenAreaGraBean {
    private int areaTypeID;
    private String areaTypeName;
    private int areaTypeGra;

    /**
     *
     * @return
     */
    public int getAreaTypeID() {
        return areaTypeID;
    }

    /**
     *
     * @param areaTypeID
     */
    public void setAreaTypeID(int areaTypeID) {
        this.areaTypeID = areaTypeID;
    }

    /**
     *
     * @return
     */
    public String getAreaTypeName() {
        return areaTypeName;
    }

    /**
     *
     * @param areaTypeName
     */
    public void setAreaTypeName(String areaTypeName) {
        this.areaTypeName = areaTypeName;
    }

    /**
     *
     * @return
     */
    public int getAreaTypeGra() {
        return areaTypeGra;
    }

    /**
     *
     * @param areaTypeGra
     */
    public void setAreaTypeGra(int areaTypeGra) {
        this.areaTypeGra = areaTypeGra;
    }
    
}
