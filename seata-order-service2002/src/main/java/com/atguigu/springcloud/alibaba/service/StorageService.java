package com.atguigu.springcloud.alibaba.service;

import org.apache.ibatis.annotations.Param;

public interface StorageService {

    //扣减库存信息
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
