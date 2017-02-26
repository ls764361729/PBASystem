/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 用户信息数据结构
 *
 * @since 2016/09/10
 * @version 0.8.1
 * @author 孙晨星
 */
public class UserMessBean {

    private int uID;
    private String userName;
    private String realName;
    private String userAuths;
    private String userType;
    private String from;
    private Boolean activity;

    /**
     *
     */
    public UserMessBean() {
    }

    /**
     *
     * @return
     */
    public int getuID() {
        return uID;
    }

    /**
     *
     * @param uID
     */
    public void setuID(int uID) {
        this.uID = uID;
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public String getRealName() {
        return realName;
    }

    /**
     *
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     *
     * @return
     */
    public String getUserAuths() {
        return userAuths;
    }

    /**
     *
     * @param userAuths
     */
    public void setUserAuths(String userAuths) {
        this.userAuths = userAuths;
    }

    /**
     *
     * @return
     */
    public String getUserType() {
        return userType;
    }

    /**
     *
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public Boolean getActivity() {
        return activity;
    }

    /**
     *
     * @param activity
     */
    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

}
