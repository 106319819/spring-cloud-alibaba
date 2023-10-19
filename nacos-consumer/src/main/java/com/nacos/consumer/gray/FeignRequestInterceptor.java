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

import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Map;

@Slf4j
/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    private GrayRule grayRule;

    public FeignRequestInterceptor(GrayRule grayRule) {
        this.grayRule = grayRule;
    }

    @Override
    public void apply(RequestTemplate template) {


        //获取源请求属性对象
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        //在此做灰度规则处理
        template.header("Gray", grayRule.parseGray(attributes));

        //取出上游认证头信息，加入到下游调用的头上，省得每个feign调用接口都需要进行注解标记
        String authorization = attributes.getRequest().getHeader("Authorization");
        template.header("Authorization",authorization);


        Map<String, Collection<String>> headers = template.headers();
        for(Map.Entry<String, Collection<String>> entry : headers.entrySet()){
            log.info("{} : {}",entry.getKey(),entry.getValue());
        }
    }
}
