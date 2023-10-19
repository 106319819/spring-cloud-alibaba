package com.nacos.consumer.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.nacos.consumer.feign.ConfigService;
import com.nacos.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @ClassName HelloController
 * @Description TODO
 * @Author yao
 * @Date 2023/9/20
 * @Version 1.0
 **/
@Slf4j
@RestController
public class HelloController {

    private String serviceId = "nacos-provider";
    @Autowired
    private DiscoveryClient discoveryClient;

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;
//
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ConfigService configService;

    @DubboReference(version = "1.0")
    private HelloService helloService;

    @GetMapping("/instances")
    public String instances(){
        StringBuilder sb = new StringBuilder();
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        instances.forEach(instance -> {
            sb.append("host ").append(instance.getHost()).append(":").append(instance.getPort()).append("<br/>");
        });
        return sb.toString();
    }

    @GetMapping("/hello")
    public String hello(){

//        ServiceInstance instance = loadBalancerClient.choose(serviceId);
        String url = String.format("http://%s/hello/echo/cloud alibaba",serviceId);
        // 可以使用注入的 restTemplate，因为通过服务名获得的实例可以有多个，因此可以负载均衡
//        ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.8.142:8080/hello/echo/cloud alibaba",String.class);
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        return  String.format("%s - %s ",url,  response.getBody());
    }


    @GetMapping("/hello/dubbo")
    public String helloDubbo(){
       return helloService.hello("hello.dubbo");
    }

    @GetMapping("/is-gray")
    public String isGray(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
//        if (StringUtils.isNotEmpty(request.getHeader("Gray"))) {
//            headers.add("Gray", request.getHeader("Gray").equals("true") ? "true" : "false");
//        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

//        ServiceInstance instance = loadBalancerClient.choose(serviceId);
        String url = String.format("http://%s/config/is-gray",serviceId);
        // 可以使用注入的 restTemplate，因为通过服务名获得的实例可以有多个，因此可以负载均衡
//        ResponseEntity<String> response = restTemplate.getForEntity("http://192.168.8.142:8080/hello/echo/cloud alibaba",String.class);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
        return  String.format("%s - %s ",url,  response.getBody());
    }

    @GetMapping("/feign/is-gray")
    public String gray(HttpServletRequest request){
        log.info("header Gray: {} ",request.getHeader("Gray"));
        return configService.isGray(request.getHeader("Gray"));
    }
}
