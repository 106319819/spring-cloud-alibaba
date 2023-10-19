package com.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author yao
 * @Date 2023/9/19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${cloud.provider.group:none}")
    private String group;
    @Value("${spring.cloud.nacos.discovery.metadata.gray:false}")
    private Boolean gray;

    @GetMapping("/group")
    public String group() {
        return group;
    }

    @GetMapping("/is-gray")
    public String isGray(){
        return gray.toString();
    }
}
