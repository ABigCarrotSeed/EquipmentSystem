package com.hp.serviceInter;

import com.hp.domain.Users;

public interface UserServiceInter {

	//登陆
	public Users checkLogin(Users users);
	
	//修改密码
	public Boolean changePassword(Users user);
}
