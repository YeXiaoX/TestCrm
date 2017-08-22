package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import test.service.TestService;

/**
 * Created by yexiaoxin on 2017/8/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/tt")
    @ResponseBody
    public String tt() {
        String result = "";
        try {
            result = "test:" + testService.getName().size();
        } catch (Exception e) {
            System.out.println("error");
        }

        return result;
    }
}
