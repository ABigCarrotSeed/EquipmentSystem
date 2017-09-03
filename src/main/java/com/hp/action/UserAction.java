package com.hp.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hp.domain.Users;
import com.hp.service.UserService;

@Controller
@Scope("prototype")
public class UserAction implements RequestAware,SessionAware{

	private String oldPassword;
	private String newPassword;
	private String secondPassword;
	
	private Map<String,Object> request;
	private Map<String, Object> session;

	@Resource
	private UserService userService;
	public String goChangePasswordUI(){
		request.put("type", "用户设置");
		return "goChangePasswordUI";
	}
	
	public String setNewPassWord(){
		Users user=(Users)session.get("user");
		if(user.getPassword().equals(oldPassword)){
			if(secondPassword.equals(newPassword)){
				user.setPassword(newPassword);
				userService.changePassword(user);
				request.put("info", "修改完成");
				return "success";
			}
			request.put("info", "密码输入不一致");
			return "success";
		}
		request.put("info", "密码输入错误");
		return "success";
	}
	
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getSecondPassword() {
		return secondPassword;
	}
	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
}
