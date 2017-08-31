package test.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import test.Dto.Cust;
import test.service.TestService;

/**
 * Created by yexiaoxin on 2017/8/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LogManager.getLogger(TestController.class);
    @Autowired
    private TestService testService;

    @RequestMapping("/tt")
    @ResponseBody
    public String tt() {
        String result = "";
        try {
            result = "test:" + testService.getName().size();
            log.info("test success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

        return result;
    }

    @RequestMapping("/createCust")
    @ResponseBody
    public String createCust() {
        String result = "";
        try {
            Cust cust = new Cust();
            cust.setCustId(888);
            cust.setCustName("tgsgsgs");
            testService.insertCust(cust);
            result = "success";
            log.info("createCust success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return result;
    }
}
