import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.Dto.Cust;
import test.dao.TestDao;
import test.service.TestService;

/**
 * Created by yexiaoxin on 2017/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
//@Ignore
public class TestGet {
//    @Autowired
//    private TestDao testDao;
    @Autowired
    private TestService testService;
  //  private volatile static BeanFactory factory;

//    @Before
//    public void init() {
//        factory = new ClassPathXmlApplicationContext("applicationContext.xml");
//    }

    @Test
    public  void test(){
        //TestDao testDao = (TestDao)factory.getBean("testDao");
        //System.out.println(testDao.getName().size());
        System.out.println(testService.getName().size());
        System.out.println(testService.getSize().size());
    }

}
