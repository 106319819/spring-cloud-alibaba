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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Data
@ConfigurationProperties(prefix = "gray.rule")
@RefreshScope
public class GrayRule {

    private String type;

    private String name;

    private String value;

    public String parseGray(ServletRequestAttributes attributes){
        String value = null;
        //在此做灰度规则处理
        if("header".equals(getType())){
            value = attributes.getRequest().getHeader(getName());
        }else if("param".equals(getType())){
            value = attributes.getRequest().getParameter(getName());
        }
        return getValue().equalsIgnoreCase(value) ? "true" : "false";
    }

}
