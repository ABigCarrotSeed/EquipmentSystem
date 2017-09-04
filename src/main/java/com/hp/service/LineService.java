package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.base.BaseDaoInter;
import com.hp.domain.Users;
import com.hp.serviceInter.LineServiceInter;

@Service
public class LineService  implements LineServiceInter{

	@Resource
	private BaseDaoInter baseDao;
	
	public List<String> getLineNameByUser(Users user){
		String hql = "SELECT line.name FROM Line line where line.department.id=?";
		Object[] parameters = {user.getDepartment().getId()};
		List list = baseDao.getResult(hql, parameters);
		return list;
	}
}
