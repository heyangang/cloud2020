package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sun.deploy.security.BlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        log.info("-----------testA");
        return "---------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info("-----------testB");
        return "---------testB";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required=false) String p1,
                             @RequestParam(value = "p2",required=false) String p2)  {
        log.info(p1);
        return "----------testHotKey";
    }
    public String deal_testHotKey(String p1 , String p2, BlockException exception){
        return "--------deal_testHotKey%>_<%";
    }
}
