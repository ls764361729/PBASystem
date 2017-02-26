/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structBean;

/**
 * 初始化数据读取结构
 * @since 2016/09/10 
 * @version 0.8.1
 * @author 孙晨星
 */

public class LoadDataBean {
    
    private int databasePep;
    private int onlinePep;

    /**
     *
     * @return
     */
    public int getDatabasePep() {
        return databasePep;
    }

    /**
     *
     * @param databasePep
     */
    public void setDatabasePep(int databasePep) {
        this.databasePep = databasePep;
    }

    /**
     *
     * @return
     */
    public int getOnlinePep() {
        return onlinePep;
    }

    /**
     *
     * @param onlinePep
     */
    public void setOnlinePep(int onlinePep) {
        this.onlinePep = onlinePep;
    }
    
}
