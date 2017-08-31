package test.query;

/**
 * Created by yexiaoxin on 2017/8/31.
 */
public class DistributeTableQuery {
    private String tableName;
    private Integer id;
    private Integer parentId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
