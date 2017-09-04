package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.base.BaseDao;
import com.hp.base.BaseDaoInter;
import com.hp.domain.Equipment;
import com.hp.domain.Users;
import com.hp.serviceInter.EquipmentServiceInter;

@Service
public class EquipmentService implements EquipmentServiceInter{
	@Resource
	private BaseDaoInter baseDao;
	
	public List<Equipment> getAllEquipment(Users user){
		String hql = "FROM Equipment e WHERE e.line.department.id=?";
		Object[] parameters = {user.getDepartment().getId()};
		List list=baseDao.getResult(hql, parameters);
		return list;
	}
	
	public Equipment getEquipmentByEId(String Eid){
		String hql = "FROM Equipment e WHERE e.eid=?";
		Object[] parameters = {Eid};
		List list = baseDao.getResult(hql, parameters); 
		if(list!=null && list.size()==1){
			return (Equipment) list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param user
	 * @param page 当前页
	 * @param pageSize 页面显示数
	 * @return
	 */
	public List<Equipment> getEquipmentByPage(Users user,int page,int pageSize){
		String hql = "FROM Equipment e WHERE e.line.department.id=?";
		Object[] parameters = {user.getDepartment().getId()};
		List list = baseDao.getByPage(hql, parameters, page, pageSize);
		return list;
	}
	
	public int getPageTotal(Users user,int pageSize){
		String hql = "FROM Equipment e WHERE e.line.department.id=?";
		Object[] parameters = {user.getDepartment().getId()};
		List list = baseDao.getResult(hql, parameters);
		return list.size()%pageSize==0?(list.size()/pageSize):(list.size()/pageSize+1) ;
	}
}
