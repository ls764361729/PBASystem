/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 定位点数据结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class PntListBean {
    private String deviceID;
    private String pTime;
    private String sCoordX;
    private String sCoordY;
    
    //构造函数

    /**
     *
     */
    public PntListBean(/*String deviceID,String pTime,String sCoordX,String sCoordY*/){
//        this.deviceID = deviceID;
//        this.pTime = pTime;
//        this.sCoordX = sCoordX;
//        this.sCoordY = sCoordY
    }

    /**
     *
     * @return
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     *
     * @param deviceID
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    /**
     *
     * @return
     */
    public String getpTime() {
        return pTime;
    }

    /**
     *
     * @param pTime
     */
    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    /**
     *
     * @return
     */
    public String getsCoordX() {
        return sCoordX;
    }

    /**
     *
     * @param sCoordX
     */
    public void setsCoordX(String sCoordX) {
        this.sCoordX = sCoordX;
    }

    /**
     *
     * @return
     */
    public String getsCoordY() {
        return sCoordY;
    }

    /**
     *
     * @param sCoordY
     */
    public void setsCoordY(String sCoordY) {
        this.sCoordY = sCoordY;
    }
    
}
