package com.nacos.consumer.config;

import com.nacos.consumer.gray.FeignRequestInterceptor;
import com.nacos.consumer.gray.GrayRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 激活 @FeignClient
public class FeignClientConfiguration {
    @Bean
    public FeignRequestInterceptor requestInterceptor(GrayRule rule) {
        return new FeignRequestInterceptor(rule);
    }

}
