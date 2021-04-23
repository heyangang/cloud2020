package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.AccountDao;
import com.atguigu.springcloud.alibaba.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao dao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        //feign的默认返回时间是1秒中，睡眠20秒是为了制造feign的异常，验证分布式事务是否生效
       /* try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        dao.decrease(userId,money);
    }
}
