package com.hp.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hp.base.BaseDaoInter;
import com.hp.domain.MaintainItems;
import com.hp.serviceInter.MaintainItemsServiceInter;

@Service
public class MaintainItemsService implements MaintainItemsServiceInter{

	@Resource
	private BaseDaoInter baseDao;
	
	public List<MaintainItems> getMaintainItemsByEid(String eId,String dateType){
		String hql = "FROM MaintainItems m WHERE m.machine.id = (select e.machine.id from Equipment e where e.eid=?)"
				+ " and m.datecycle.type=?";
		Object[] parameters = {eId,dateType};
		List<MaintainItems> list = baseDao.getResult(hql, parameters);
		return list;
	}
}
