package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//自己试着写一个本地负载均衡器试试
@Component
public class MyLB implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private final  int getAndIncrement(){
        int current;
        int next;
        do{
            current = atomicInteger.get();
            next =current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current,next));
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> instances) {
        int num = getAndIncrement();
       int index =  num % instances.size(); //得到服务器的下标位置

        return instances.get(index);
    }
}
