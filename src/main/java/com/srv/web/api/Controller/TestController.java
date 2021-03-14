package com.srv.web.api.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    private int index = 0;

    @RequestMapping(value="/test.do")
    public String testData(){

        String result = String.valueOf(index++);

        log.info("result : " + result);

        return result;

    }
}
