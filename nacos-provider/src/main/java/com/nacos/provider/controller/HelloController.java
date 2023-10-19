package com.nacos.provider.controller;

import com.nacos.provider.config.NacosDiscoveryConfiguration;
import com.nacos.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author yao
 * @Date 2023/9/19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;


    @Autowired
    private NacosDiscoveryConfiguration discoveryConfiguration;
    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return helloService.hello(message);
    }

    @GetMapping("/group")
    public String group(){
        return discoveryConfiguration.getGroup();
    }

}

