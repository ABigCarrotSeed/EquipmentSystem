package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.domain.Users;

@Service
public class UserService {

	@Resource
	private SessionFactory sessionFactory;
	
	public Boolean checkLogin(Users users){
		String hql="from Users where jobId=? and password=?";
		Query query= sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, users.getJobId());
		query.setString(1, users.getPassword());
		List list = query.list();
		
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
}
