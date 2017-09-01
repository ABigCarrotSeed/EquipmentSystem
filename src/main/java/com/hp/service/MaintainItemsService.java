package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.domain.MaintainItems;

@Service
public class MaintainItemsService {

	@Resource
	private SessionFactory sessionFactory;
	
	public List<MaintainItems> getMaintainItemsByEid(String eId,String dateType){
		String hql = "FROM MaintainItems m WHERE m.machine.id = (select e.machine.id from Equipment e where e.eid=?)"
				+ " and m.datecycle.type=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, eId);
		query.setString(1, dateType);
		List<MaintainItems> list = query.list();
		return list;
	}
}
