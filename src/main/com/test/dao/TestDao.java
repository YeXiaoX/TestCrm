package test.dao;



import org.apache.ibatis.annotations.Param;
import test.Dto.Cust;
import test.annotation.DBRouting;

import java.util.List;

/**
 * Created by yexiaoxin on 2017/8/14.
 */

public interface TestDao {

    @DBRouting(name = "haha")
    public List<Cust> getName(Cust cust);

    @DBRouting(name = "hehe")
    public List<Cust> getSize();

    @DBRouting(name = "insertCust")
    public void insertCust(Cust cust);



}
