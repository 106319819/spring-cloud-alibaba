package com.nacos.consumer.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 在调用service的接口之前，加入登录会话信息
 * @ClassName DubboRpcContextAspect
 * @Description TODO
 * @Author yao
 * @Date 2023/2/14
 * @Version 1.0
 **/
@Slf4j
@Aspect
@Component
public class DubboRpcContextAspect {


    /**
     * 卷宗信息相关修改操作
     */
    @Pointcut("execution(* com.nacos.service.*.*(..))"
    + " || execution(* net.shuj.cib.service.*.*(..))")
    public void servicePointcut() {

    }
//
//    /**
//     * 当事人相关修改操作
//     * @title
//     */
//    @Pointcut("execution(* com.shusi.law.archive.controller.ArchivePersonController.update*(..))"
//            +" || execution(* com.shusi.law.archive.controller.ArchivePersonController.delete*(..))")
//    public void personPointcut() {
//
//    }
//    /**
//     * 模型实例相关修改操作
//     * @title
//     */
//    @Pointcut("execution(* com.shusi.law.model.controller.ModelInstanceController.update*(..))"
//            + " || execution(* com.shusi.law.model.controller.ModelInstanceController.delete*(..))")
//    public void instancePointcut() {
//
//    }


    @Before("servicePointcut() ")
    public void before(JoinPoint point) throws Throwable {
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // taskDefinitionId 任务定义id
        //taskActionId 任务操作id
        //String archiveId = request.getParameter("archiveId");
        //String taskActionId = request.getParameter("taskActionId");
        //String bizValueId = request.getParameter("bizValueId");
        // String stateValueId = request.getParameter("stateValueId");
        Object target = point.getTarget();
        log.info("servicePointcut {} : {}",point.getSignature().getClass().getName(),point.getSignature().getName());
        JSONObject user = JSONObject.parseObject("{" +
                "\"userId\": \"2ffc84e090634942a925cba7b711161d\"," +
                "\"userName\": \"鲁婷\"," +
                "\"deptId\": \"202\"," +
                "\"deptName\": \"罗德岛\"," +
                "\"mobile\": \"14787463583\"," +
                "\"tenantName\": \"广东省江门市江海公证处\"," +
                "\"tenantId\": \"60c1937a9da64515902329283e77a366\"" +
                "}");

        if(null != user) {
            RpcContext.getClientAttachment().setAttachment("Authorization", user.toJSONString());
           // RpcContext.getContext().setAttachment(Constant.Tokens.Authorization.name(),user.toJSONString());
        }
    }

}
