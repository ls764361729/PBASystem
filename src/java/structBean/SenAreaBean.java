/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 敏感区域数据结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class SenAreaBean {
    private int aID;
    private String areaName;
    private int areaTypeID;
    private String aCoordX;
    private String aCoordY;
    private String areaTypeName;
    private int areaTypeGra;
    
    /**
     *
     */
    public SenAreaBean(){
        
    }

    /**
     *
     * @return
     */
    public int getaID() {
        return aID;
    }

    /**
     *
     * @param aID
     */
    public void setaID(int aID) {
        this.aID = aID;
    }

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
    public String getAreaName() {
        return areaName;
    }

    /**
     *
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     *
     * @return
     */
    public String getaCoordX() {
        return aCoordX;
    }

    /**
     *
     * @param aCoordX
     */
    public void setaCoordX(String aCoordX) {
        this.aCoordX = aCoordX;
    }

    /**
     *
     * @return
     */
    public String getaCoordY() {
        return aCoordY;
    }

    /**
     *
     * @param aCoordY
     */
    public void setaCoordY(String aCoordY) {
        this.aCoordY = aCoordY;
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
