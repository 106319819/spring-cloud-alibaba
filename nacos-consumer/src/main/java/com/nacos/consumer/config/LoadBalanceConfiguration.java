package com.nacos.consumer.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName LoadBalanceConfiguration
 * @Description TODO
 * @Author yao
 * @Date 2023/10/13
 * @Version 1.0
 **/
@Configuration
@LoadBalancerClient(name = "nacos-provider", configuration = GrayLoadBalancerConfig.class)
public class LoadBalanceConfiguration {
}
