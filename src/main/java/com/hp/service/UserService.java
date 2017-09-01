package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.domain.Users;

@Service
public class UserService {

	@Resource
	private SessionFactory sessionFactory;
	
	public Users checkLogin(Users users){
		String hql="from Users where jobId=? and password=?";
		Query query= sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, users.getJobId());
		query.setString(1, users.getPassword());
		List list = query.list();
		
		if(list != null && list.size()==1){
			return (Users) list.get(0);
		}
		return null;
	}
	
	public Boolean changePassword(Users user){
		System.out.println(user.getPassword()+" "+ user.getId());
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.update(user);
		session.flush();
		session.getTransaction().commit();
		return true;
	}

}
