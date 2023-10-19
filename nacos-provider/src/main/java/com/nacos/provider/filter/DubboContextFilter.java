package com.nacos.provider.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @ClassName DubboClientFilter
 * @Description TODO
 * @Author yao
 * @Date 2023/2/14
 * @Version 1.0
 **/

@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class DubboContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//       RpcContext context = RpcContext.getContext();
//        boolean isProvider = context.isProviderSide();
//        log.info("provider -> {} ",isProvider);
//        String attachement = context.getAttachment("Authorization");
//        log.info("attachement-> {}",attachement);
//
//        return invoker.invoke(invocation);

        String attachement = RpcContext.getServerAttachment().getAttachment("Authorization");
        log.info("attachement-> {}",attachement);
        return invoker.invoke(invocation);
    }
}
