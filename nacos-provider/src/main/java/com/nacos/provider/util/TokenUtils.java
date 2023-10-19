package com.nacos.provider.util;


import com.alibaba.fastjson.JSONObject;
import com.nacos.provider.config.Constant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TokenUtils
 * @Description TODO
 * @Author yao
 * @Date 2023/2/14
 * @Version 1.0
 **/
@Slf4j
public class TokenUtils {


    /**
     * token数据的兼容性映射
     */
    private static Map<String,String> userAttributes = new HashMap<>();
    static {
        //String[] keys = {"userId","userName","deptId","deptName","tenantId","tenantName","mobile","token"};
        userAttributes.put( Constant.Account.accountId,"userId");
        userAttributes.put(Constant.Account.accountName,"userName");
        userAttributes.put(Constant.Account.accountCode,"userId");
        userAttributes.put(Constant.Account.organizeId,"deptId");
        userAttributes.put(Constant.Account.organizeCode,"deptId");
        userAttributes.put(Constant.Account.organizeName,"deptName");
        userAttributes.put(Constant.Account.tenantId,"tenantId");
        userAttributes.put(Constant.Account.tenantName,"tenantName");
        userAttributes.put(Constant.Tokens.token.name(),"token");
        userAttributes.put(Constant.Account.accountType,"userType");
        userAttributes.put("mobile","mobile");
        userAttributes.put("address", "addr");
    }

    /**
     * 当前线程会话数据.
     */
    public static final ThreadLocal<JSONObject> holder = new ThreadLocal<>();

    public static void setCurrentUser(JSONObject user){
        holder.set(user);
    }
    public static JSONObject getCurrentUser(){
        return holder.get();
    }

    public static String getToken(){
        JSONObject user = getCurrentUser();
        if(null == user){
            return null;
        }
        return user.getString("token");
    }

    public static boolean isAdmin()
    {
        return (null != getCurrentUser() ? Constant.Account.isAdmin(getCurrentUser()) : false);
    }
    public static boolean isMaster()
    {
        return (null != getCurrentUser() ? Constant.Account.isMaster(getCurrentUser()) : false);
    }
    public static boolean isAdminMaster(){
        return (null != getCurrentUser() ? Constant.Account.isAdminMaster(getCurrentUser()) : false);
    }

    /**
     * 从http session中构建登录token信息对象
     * @param userAttributes
     * @param session
     */
    public static JSONObject buildToken(final Map<String,String> userAttributes,final HttpSession session){
        HashMap<String, Boolean> values = new HashMap<>();
        JSONObject user = new JSONObject();
        //兼容token数据
        for(Map.Entry<String, String > entry : userAttributes.entrySet()){
            String key = entry.getKey();
            values.put(entry.getValue(), true);
            Object value = session.getAttribute(entry.getValue());
            user.put(key,value);
        }
        //session中的其他变量一起组装
        Enumeration<String> attrs =  session.getAttributeNames();
        while(attrs.hasMoreElements()){
            String attr = attrs.nextElement();
            if(!user.containsKey(attr) && !values.containsKey(attr)){
                user.put(attr, session.getAttribute(attr));
            }
        }
        log.debug("token - session : {}",user.toJSONString());
        //设置到ThreadLocal中
        setCurrentUser(user);
        return user;
    }

    public static Map<String,String> getUserAttributes(){
        return userAttributes;
    }


    /**
     * 从source中构建登录account信息对象
     * @param source
     * @param account
     */
    public static void buildAccount(final JSONObject source,JSONObject account){
        if(null == source || null == account){
            return;
        }

        //兼容token数据
        for(Map.Entry<String, String > entry : userAttributes.entrySet()){
            String key = entry.getKey();
            if(source.containsKey(entry.getValue())) {
                Object value = source.get(entry.getValue());
                account.put(key, value);
            }
        }

        log.debug("account : {}",account.toJSONString());
    }
}
