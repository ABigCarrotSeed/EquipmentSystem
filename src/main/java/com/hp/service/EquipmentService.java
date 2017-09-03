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
	
	/**
	 * 
	 * @param user
	 * @param page 当前页
	 * @param pageSize 每页显示的数量 
	 * @return
	 */
	public List<Equipment> getEquipmentByPage(Users user,int page,int pageSize){
		String hql = "FROM Equipment e WHERE e.line.department.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, user.getDepartment().getId());
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(pageSize);
		List list = query.list();
		return list;
	}
	
	public int getPageTotal(Users user,int pageSize){
		String hql = "FROM Equipment e WHERE e.line.department.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, user.getDepartment().getId());
		List list = query.list();
		return list.size()%pageSize==0?(list.size()/pageSize):(list.size()/pageSize+1) ;
	}
}
