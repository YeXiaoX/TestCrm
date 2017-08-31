package test.service.Impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.Dto.Cust;
import test.Dto.DistributeTable;
import test.dao.DistributeTableDao;
import test.dao.TestDao;
import test.query.DistributeTableQuery;
import test.service.TestService;

import java.util.List;

/**
 * Created by yexiaoxin on 2017/7/27.
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    @Autowired
    private DistributeTableDao distributeTableDao;

    public List<Cust> getName() {
        return testDao.getName(new Cust());
    }

    public List<Cust> getSize() {
        return testDao.getSize();
    }

    public void insertCust(Cust cust) {
        DistributeTableQuery query = new DistributeTableQuery();
        query.setTableName("zz_t_name");
        int count = distributeTableDao.getTableCount(query);
        String tableName = "";
        if (count >= 3) {
            List<DistributeTable> tables = distributeTableDao.getTableByCondition(query);
            String initSql = "zz_t_name_";
            String temp = "";
            int num = 0;
            int parentId = 0;
            for (DistributeTable table : tables) {
                if (table.getParentId() == -1) {
                    temp = table.getTableInitSql();
                    parentId = table.getId();
                } else {
                    num++;
                    query.setTableName(table.getTableName());
                    count = distributeTableDao.getTableCount(query);
                    if (count < 3) {
                        tableName = table.getTableName();
                    }
                }
            }
            if (StringUtils.isEmpty(tableName)) {
                tableName = initSql + num;
                distributeTableDao.createTable(tableName+" "+temp);
                DistributeTable table = new DistributeTable();
                table.setParentId(parentId);
                table.setTableName(tableName);
                distributeTableDao.insertTable(table);
            }
            cust.setTableName(tableName);
            testDao.insertCust(cust);
        } else {
            cust.setTableName("zz_t_name");
            testDao.insertCust(cust);
        }
    }
}
