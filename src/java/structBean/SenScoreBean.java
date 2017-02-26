/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 敏感评分数据结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class SenScoreBean {
    private int pepID;
    private int calcScore;
    private String calcTime;
    private String calcDate;
    
    /**
     *
     */
    public SenScoreBean(){
        
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
    public int getCalcScore() {
        return calcScore;
    }

    /**
     *
     * @param calcScore
     */
    public void setCalcScore(int calcScore) {
        this.calcScore = calcScore;
    }

    /**
     *
     * @return
     */
    public String getCalcTime() {
        return calcTime;
    }

    /**
     *
     * @param calcTime
     */
    public void setCalcTime(String calcTime) {
        this.calcTime = calcTime;
    }

    /**
     *
     * @return
     */
    public String getCalcDate() {
        return calcDate;
    }

    /**
     *
     * @param calcDate
     */
    public void setCalcDate(String calcDate) {
        this.calcDate = calcDate;
    }
    
}
