package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {

    private static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    // OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/testA")
    public String testA(){
        return "----------testA";
    }

    @GetMapping(value = "/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback")   //没有配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallback")   //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler")   //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",blockHandler = "blockHandler",fallback = "handlerFallback")   //blockHandler只负责sentinel控制台配置违规
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return result;
    }
    //fallback
    public CommonResult handlerFallback(@PathVariable("id") Long id,Throwable e){
        Payment payment = new Payment(id, "null");
        return new CommonResult(444,"兜底异常handlerFallback,exception内容" + e.getMessage(),payment);
    }

    //blockHandler
    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException exception){
        Payment payment = new Payment(id, "null");
        return new CommonResult(445,"blockHandler-sentinel限流,无此流水: blockException" + exception.getMessage(),payment);
    }

    //---------------------------使用openfeign远程调用rest接口-------------------
    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult paymentSQL(@PathVariable("id") Long id){
        System.out.println("aaaaaaaaaaaaaaaaaaaa");
        return paymentService.paymentSQL(id);
    }

}
