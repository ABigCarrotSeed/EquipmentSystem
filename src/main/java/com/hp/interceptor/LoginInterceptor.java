package com.hp.interceptor;

import com.hp.domain.Users;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		Users user = (Users) ActionContext.getContext().getSession().get("user");
		if(user == null){
			return "goLogin";
		}
		return arg0.invoke();
	}

}
