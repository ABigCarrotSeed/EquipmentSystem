package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.domain.Users;

@Service
public class LineService {

	@Resource
	private SessionFactory sessionFactory;
	
	public List<String> getLineNameByUser(Users user){
		String hql = "SELECT line.name FROM Line line where line.department.id=?";
		int departmentId = user.getDepartment().getId();
	    Query query = sessionFactory.getCurrentSession().createQuery(hql);
	    query.setInteger(0, departmentId);
	    List<String> lineName = query.list();
		return lineName;
	}
}
