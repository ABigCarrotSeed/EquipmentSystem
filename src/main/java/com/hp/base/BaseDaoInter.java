package com.hp.base;

import java.io.Serializable;
import java.util.List;

/**
 * service 基类
 * @author hupi
 *
 */
public interface BaseDaoInter {

	//通过hql方式获取结果集
	public List getResult(String hql,Object[] parameters);
	
	//通过sql方式获取结果集
	public List getResultBySql(String sql,Object[] parameters);

	//保存
	public void save(Object obj);
	//更新
	public void update(Object obj);
	//删除
	public void delete(Object obj);
	//通过id及类型获取结果
	public Object getById(Serializable id,Class clazz );
	//获取分页信息
	public List getByPage(String hql,Object[] parameters,int page,int pageSize);
}
