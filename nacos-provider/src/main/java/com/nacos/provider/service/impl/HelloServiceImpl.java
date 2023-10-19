package com.nacos.provider.service.impl;

import com.nacos.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName HelloServiceImpl
 * @Description TODO
 * @Author yao
 * @Date 2023/9/27
 * @Version 1.0
 **/
@Service
@DubboService(version = "1.0")
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String message) {
        InetAddress host;
        try {
            host = InetAddress.getLocalHost();
            return String.format(" host %s [%s] : %s" ,host.getHostName(), host.getHostAddress(), message);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
