package test.dao;

import org.apache.ibatis.annotations.Param;
import test.Dto.DistributeTable;
import test.annotation.DBRouting;
import test.query.DistributeTableQuery;

import java.util.List;

/**
 * Created by yexiaoxin on 2017/8/31.
 */
public interface DistributeTableDao {
    @DBRouting(name = "create table")
    public void createTable(@Param("tableName") String tableName);

    @DBRouting(name = "getTable")
    public List<DistributeTable> getTableByCondition(DistributeTableQuery query);


    @DBRouting(name = "getTableCount")
    public int getTableCount(DistributeTableQuery query);

    @DBRouting(name = "insertTable")
    public void insertTable(DistributeTable table);
}
