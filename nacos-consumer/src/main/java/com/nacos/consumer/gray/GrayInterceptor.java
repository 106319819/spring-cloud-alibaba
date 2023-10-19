/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nacos.consumer.gray;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Slf4j
/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class GrayInterceptor implements ClientHttpRequestInterceptor {

    private GrayRule grayRule;
    public GrayInterceptor(GrayRule grayRule) {
        this.grayRule = grayRule;
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {

        //获取源请求属性对象
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        //在此做灰度规则处理
        request.getHeaders().add("Gray", grayRule.parseGray(attributes));

        //取出上游认证头信息，加入到下游调用的头上，省得每个feign调用接口都需要进行注解标记
        String authorization = attributes.getRequest().getHeader("Authorization");
        request.getHeaders().add("Authorization",authorization);

        return execution.execute(request, body);
    }

}
