package com.nacos.provider;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @ClassName ConfigClientTest
 * @Description TODO
 * @Author yao
 * @Date 2023/9/22
 * @Version 1.0
 **/
public class ConfigClientTest {
    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "192.168.8.142:8848";
        String dataId = "application-dev";
        String group = "cloud-provider-dev";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);

        Thread.sleep(3000);
        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);

        boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
        Thread.sleep(3000);

        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(300000);

    }
}
