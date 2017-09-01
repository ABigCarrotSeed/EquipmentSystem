package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.domain.Equipment;
import com.hp.domain.Users;

@Service
public class EquipmentService {
	@Resource
	private SessionFactory sessionFactory;
	
	public List<Equipment> getAllEquipment(Users user){
		String hql = "FROM Equipment e WHERE e.line.department.id=?";
		int id = user.getDepartment().getId();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		List<Equipment> list = query.list();
		return list;
	}
	
	public Equipment getEquipmentByEId(String Eid){
		String hql = "FROM Equipment e WHERE e.eid=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, Eid);
		List list = query.list();
		if(list!=null && list.size()==1){
			return (Equipment) list.get(0);
		}
		return null;
	}
}
