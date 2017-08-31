package test.service.Impl;

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
        if (count > 3) {
            List<DistributeTable> tables = distributeTableDao.getTableByCondition(query);
            String initSql = "zz_t_name_1 ";
            for (DistributeTable table : tables) {
                if (table.getParentId() == -1) {
                    initSql += table.getTableInitSql();
                }
            }
            distributeTableDao.createTable(initSql);
            cust.setTableName("zz_t_name_1");
            testDao.insertCust(cust);
        } else {
            cust.setTableName("zz_t_name");
            testDao.insertCust(cust);
        }
    }
}
