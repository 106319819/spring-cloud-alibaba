package com.nacos.consumer.gray;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName LoadBalanceConfig
 * @Description TODO
 * @Author yao
 * @Date 2023/10/13
 * @Version 1.0
 **/
@Slf4j
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {


    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final String serviceId;

    private final Random random;

    public GrayLoadBalancer(
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
        this.random = new Random();
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
//        supplier.get(request).next().map(this::getInstanceResponse);
        log.info("request context: {}",request.getContext().toString());
        RequestDataContext context = (RequestDataContext)request.getContext();
        RequestData clientRequest = context.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        String gray = headers.getFirst("Gray");
        log.info("header gray {}",gray);
        return supplier.get(request).next().map( instances -> {
                    return getInstanceResponse(request,instances);       }
        );
    }

    private Response<ServiceInstance> getInstanceResponse(Request request,
            List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }

        RequestDataContext context = (RequestDataContext)request.getContext();
        RequestData clientRequest = context.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        String gray = headers.getFirst("Gray");
        log.info("header gray {}",gray);

        List<ServiceInstance> grayInstances = new ArrayList<>();
        List<ServiceInstance> normalInstances = new ArrayList<>();
        for(ServiceInstance instance : instances){
            Map<String,String> metadata = instance.getMetadata();
            if(null != metadata
                    && "true".equalsIgnoreCase(metadata.get("gray"))){
                log.info("gray instance {}:{}",instance.getHost(),instance.getPort());
                grayInstances.add(instance);
            }else{
                normalInstances.add(instance);
            }
        }

        ServiceInstance instance = null;
        if("true".equalsIgnoreCase(gray)){
            int randomNum =  random.nextInt(grayInstances.size());
            instance = grayInstances.get(randomNum);
        }else{
            int randomNum =  random.nextInt(normalInstances.size());
            instance = normalInstances.get(randomNum);
        }
        if(null == instance) {
            int randomNum = random.nextInt(instances.size());
            System.out.println("random: " + randomNum);
            instance = instances.get(randomNum);
        }
        return new DefaultResponse(instance);

    }
}
