/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 监控区域数据结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class AtAreaBean {
    private String alertAreaName;
    private int alertAreaID;
    private String[] atCoordXs;
    private String[] atCoordYs;

    /**
     *
     * @return
     */
    public String getAlertAreaName() {
        return alertAreaName;
    }

    /**
     *
     * @param alertAreaName
     */
    public void setAlertAreaName(String alertAreaName) {
        this.alertAreaName = alertAreaName;
    }

    /**
     *
     * @return
     */
    public int getAlertAreaID() {
        return alertAreaID;
    }

    /**
     *
     * @param alertAreaID
     */
    public void setAlertAreaID(int alertAreaID) {
        this.alertAreaID = alertAreaID;
    }

    /**
     *
     * @return
     */
    public String[] getAtCoordXs() {
        return atCoordXs;
    }

    /**
     *
     * @param atCoordXs
     */
    public void setAtCoordXs(String[] atCoordXs) {
        this.atCoordXs = atCoordXs;
    }

    /**
     *
     * @return
     */
    public String[] getAtCoordYs() {
        return atCoordYs;
    }

    /**
     *
     * @param atCoordYs
     */
    public void setAtCoordYs(String[] atCoordYs) {
        this.atCoordYs = atCoordYs;
    }
    
}
