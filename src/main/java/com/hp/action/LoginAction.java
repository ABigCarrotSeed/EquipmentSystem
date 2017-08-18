package com.hp.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hp.domain.Users;
import com.hp.service.UserService;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class LoginAction  implements ModelDriven<Users>{

	private Users users;
	public Users getModel() {
		// TODO Auto-generated method stub
		if(users == null){
			users = new Users();
		}
		return users;
	}
	
	@Resource
	private UserService userService;
	
	public String login(){
		if(userService.checkLogin(users)){
			return "success";
		}
		return "error";
	}



	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
