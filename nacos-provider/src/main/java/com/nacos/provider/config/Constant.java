package com.nacos.provider.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName Constant
 * @Description TODO
 * @Author yao
 * @Date 2023/10/17
 * @Version 1.0
 **/
public class Constant {
    public static class Account {

        public static String[] properties = {"accountId", "accountCode", "accountName", "accountType", "organizeId", "organizeCode", "organizeName", "personId", "personCode", "personName"};
        public static String accountId = "accountId";
        public static String accountCode = "accountCode";
        public static String accountType = "accountType";
        public static String accountName = "accountName";
        public static String organizeId = "organizeId";
        public static String organizeCode = "organizeCode";
        public static String organizeName = "organizeName";
        public static String tenantId = "tenantId";
        public static String tenantName = "tenantName";
        public static final String SYS_ROLE_PREFIX = "sys_role_";


        public static boolean isAdminMaster(String accountType) {
            return isAdmin(accountType) || isMaster(accountType);
        }

        // 是否系统管理员或管理员
        public static boolean isAdminMaster(JSONObject user) {
            return isAdminMaster(user.getString("accountType"));
        }

        //是否系统管理员
        public static boolean isAdmin(String accountType) {
            if ("1".equals(accountType) || isMaster(accountType)) {
                return true;
            }

            return false;
        }

        // 是否系统管理员
        public static boolean isAdmin(JSONObject account) {
            String accountType = account.getString("accountType");
            return isAdmin(accountType);
        }

        // 是否超级管理员
        public static boolean isMaster(JSONObject account) {
            String accountType = account.getString("accountType");
            return isMaster(accountType);
        }

        public static boolean isMaster(String accountType) {
            if ("9".equals(accountType)) {
                return true;
            }

            return false;
        }

        public static String getAccountCode(JSONObject account) {
            return account.getString(accountCode);
        }

        public static String getAccountName(JSONObject account) {
            return account.getString(accountName);
        }

        public static String getOrganizeName(JSONObject account) {
            return account.getString(organizeName);
        }


        public static String getOrganizeCode(JSONObject account) {
            return account.getString(organizeCode);
        }

        public static JSONArray getProperties(JSONObject account) {
            return account.getJSONArray("com/shuj/unify/base");
        }
    }


    public enum Tokens{
        Authorization,//http标准认证头名
        token,//token key
        expiration,//有效剩余时间 s
        password,//密码key
        authority,//授权key
        authorities,//权限列表key
        interval,//前端刷新token的时间周期s
        user,
        exclusive,//独占
        shared,//共享
        sign,//签名
        sm2,//国密算法
    }


}
