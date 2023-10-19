/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nacos.consumer.config;

import com.nacos.consumer.gray.GrayInterceptor;
import com.nacos.consumer.gray.GrayRule;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@EnableDiscoveryClient
@Configuration
public class NacosDiscoveryConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(GrayInterceptor GrayInterceptor){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(GrayInterceptor);
        return restTemplate;
    }

    @Bean
    public GrayInterceptor GrayInterceptor(GrayRule grayRule){
        return new GrayInterceptor(grayRule);
    }

}
