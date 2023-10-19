package com.nacos.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "nacos-provider")
public interface ConfigService {


    @GetMapping("/config/is-gray")
    String isGray(@RequestHeader("Gray") String gray );
}
