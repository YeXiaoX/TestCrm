package test.Dto;

import java.io.Serializable;

/**
 * Created by yexiaoxin on 2017/7/27.
 */
public class Cust implements Serializable {

    private static final long serialVersionUID = -6550898475486088675L;
    private Integer custId;
    private String custName;
    private String tableName;
    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
