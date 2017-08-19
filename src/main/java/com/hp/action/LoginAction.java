package com.hp.action;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hp.domain.Users;
import com.hp.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class LoginAction  implements ModelDriven<Users>{

	private Users user;
	public Users getModel() {
		// TODO Auto-generated method stub
		if(user == null){
			user = new Users();
		}
		return user;
	}
	
	@Resource
	private UserService userService;
	
	public String login(){
		user = userService.checkLogin(user);
		if(user != null){
			ActionContext.getContext().getSession().put("user", user);
			return "success";
		}
		return "error";
	}

	public String logOut(){
		ActionContext.getContext().getSession().clear();
		return "logOut";
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public Users getUsers() {
		return user;
	}



	public void setUsers(Users user) {
		this.user = user;
	}
}
