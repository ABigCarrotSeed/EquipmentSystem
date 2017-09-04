package com.hp.action;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hp.base.BaseAction;
import com.hp.domain.Users;
import com.hp.service.UserService;
import com.hp.serviceInter.UserServiceInter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class LoginAction  extends BaseAction<Users>{
	
	public String login(){
		model = userService.checkLogin(model);
		if(model != null){
			ActionContext.getContext().getSession().put("user", model);
			return "success";
		}
		return "error";
	}

	public String logOut(){
		ActionContext.getContext().getSession().clear();
		return "logOut";
	}
}
