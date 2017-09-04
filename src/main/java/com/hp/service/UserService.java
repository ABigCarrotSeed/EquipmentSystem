package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.base.BaseDao;
import com.hp.base.BaseDaoInter;
import com.hp.domain.Users;
import com.hp.serviceInter.UserServiceInter;

@Service
@Transactional
public class UserService implements UserServiceInter{
	
	@Resource
	private BaseDaoInter baseDao;
	
	public Users checkLogin(Users user){
		String hql="from Users where jobId=? and password=?";
		Object[] parameters = {user.getJobId(),user.getPassword()};
		List list = baseDao.getResult(hql, parameters);		
		if(list != null && list.size()==1){
			return (Users) list.get(0);
		}
		return null;
	}
	
	public Boolean changePassword(Users user){
		baseDao.update(user);
		return true;
	}

}
