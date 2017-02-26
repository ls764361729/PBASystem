/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 监控对象数据结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class ManaPepBean {
    private int pepID;
    private String pepName;
    private String deviceID;
    private String pepIDcardNum;
    private String pepSex;
    private String pepAge;
    private String startDate;
    private int manaDayLimit;
    
    /**
     *
     */
    public ManaPepBean(){
        
    }

    /**
     *
     * @return
     */
    public int getPepID() {
        return pepID;
    }

    /**
     *
     * @param pepID
     */
    public void setPepID(int pepID) {
        this.pepID = pepID;
    }

    /**
     *
     * @return
     */
    public String getPepName() {
        return pepName;
    }

    /**
     *
     * @param pepName
     */
    public void setPepName(String pepName) {
        this.pepName = pepName;
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
    public String getPepIDcardNum() {
        return pepIDcardNum;
    }

    /**
     *
     * @param pepIDcardNum
     */
    public void setPepIDcardNum(String pepIDcardNum) {
        this.pepIDcardNum = pepIDcardNum;
    }

    /**
     *
     * @return
     */
    public String getPepSex() {
        return pepSex;
    }

    /**
     *
     * @param pepSex
     */
    public void setPepSex(String pepSex) {
        this.pepSex = pepSex;
    }

    /**
     *
     * @return
     */
    public String getPepAge() {
        return pepAge;
    }

    /**
     *
     * @param pepAge
     */
    public void setPepAge(String pepAge) {
        this.pepAge = pepAge;
    }

    /**
     *
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     */
    public int getManaDayLimit() {
        return manaDayLimit;
    }

    /**
     *
     * @param manaDayLimit
     */
    public void setManaDayLimit(int manaDayLimit) {
        this.manaDayLimit = manaDayLimit;
    }
    
}
