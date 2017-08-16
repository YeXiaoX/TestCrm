package test.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.Dto.Cust;
import test.dao.TestDao;
import test.service.TestService;

import java.util.List;

/**
 * Created by yexiaoxin on 2017/7/27.
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    public List<Cust> getName() {
        return testDao.getName(new Cust());
    }

    public List<Cust> getSize() {
        return testDao.getSize();
    }
}
