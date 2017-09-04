package com.hp.base;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public  class BaseDao implements BaseDaoInter{
	
	@Resource
	private SessionFactory sessionFactory;
	
	public List getResult(String hql, Object[] parameters) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(parameters != null && parameters.length>0){
			for(int i=0 ; i<parameters.length ;i++){
				query.setParameter(i, parameters[i]);
			}
		}
		return query.list();
	}
	
	public List getResultBySql(String sql, Object[] parameters) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if(parameters != null && parameters.length>0){
			for(int i=0 ; i<parameters.length ; i++){
				query.setParameter(i, parameters[i]);
			}
		}
		return query.list();
	}
	
	public void save(Object obj) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(obj);
	}
	
	public void update(Object obj) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(obj);
	}
	
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(obj);
	}
	
	public Object getById(Serializable id, Class clazz) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().get(clazz, id);
	}
	
	public List getByPage(String hql, Object[] parameters, int page, int pageSize) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(parameters != null && parameters.length>0){
			for(int i=0 ; i<parameters.length ; i++){
				query.setParameter(i, parameters[i]);
			}
		}
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
}
