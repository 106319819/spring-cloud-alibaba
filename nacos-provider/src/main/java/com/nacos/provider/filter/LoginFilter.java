package com.nacos.provider.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nacos.provider.config.Constant;
import com.nacos.provider.util.TokenUtils;
import com.nacos.provider.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 会话过滤器
 *
 */
@Slf4j
@Component
public class LoginFilter implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
			throws Exception {
		String authorization = request.getHeader(Constant.Tokens.Authorization.name());
		log.info("Authorization: {} ",authorization);
		final HttpSession session = request.getSession();
		if(null == session){
			return true;
		}

		JSONObject user = TokenUtils.buildToken(TokenUtils.getUserAttributes(), session);

		if(null == user.get(Constant.Account.organizeCode)){
			String text = (String) session.getAttribute("deptInfo");
			if(Util.isNvl(text)){
				user.put(Constant.Account.organizeCode,user.get(Constant.Account.tenantId));
				user.put(Constant.Account.organizeName,user.get(Constant.Account.tenantName));
				user.put(Constant.Account.organizeId,user.get(Constant.Account.tenantId));
			}else{
				JSONArray depts = JSONArray.parseArray(text);
				if(Util.isNvl(depts)){
					user.put(Constant.Account.organizeCode,user.get(Constant.Account.tenantId));
					user.put(Constant.Account.organizeName,user.get(Constant.Account.tenantName));
					user.put(Constant.Account.organizeId,user.get(Constant.Account.tenantId));
				}else{
					JSONObject dept = depts.getJSONObject(0);
					user.put(Constant.Account.organizeCode,dept.get("deptId"));
					user.put(Constant.Account.organizeName,dept.get("deptName"));
					user.put(Constant.Account.organizeId,dept.get("deptId"));
				}

			}
		}
//
//		user.put(Constant.Account.accountCode,baumsUser.getString("mobile"));
//		user.put(Constant.Account.accountName,baumsUser.getString("userName"));
//		user.put(Constant.Account.accountId,baumsUser.getString("userId"));
//		user.put(Constant.Account.organizeCode, Util.nvl(baumsUser.getString("deptId"),baumsUser.getString("tenantId")));
//		user.put(Constant.Account.organizeName,Util.nvl(baumsUser.getString("deptName"),baumsUser.getString("tenantName")));
//		user.put(Constant.Account.tenantId, baumsUser.getString("tenantId"));
//		user.put(Constant.Account.tenantName,baumsUser.getString("tenantName"));
//		user.put(Constant.Account.accountType, Util.nvl(baumsUser.getString("userType"),"0"));
//		TokenUtils.setCurrentUser(user);
		return true;
	}

}
