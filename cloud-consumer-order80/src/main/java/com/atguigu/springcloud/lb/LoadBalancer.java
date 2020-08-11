package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
//自己试着写一个本地负载均衡器试试
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> instances);

}
