package test.Dto;

/**
 * Created by yexiaoxin on 2017/8/31.
 */
public class DistributeTable {
    private Integer id;
    private String tableName;
    private String tableInitSql;
    private Integer parentId;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableInitSql() {
        return tableInitSql;
    }

    public void setTableInitSql(String tableInitSql) {
        this.tableInitSql = tableInitSql;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
