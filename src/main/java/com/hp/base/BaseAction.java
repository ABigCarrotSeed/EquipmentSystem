package com.hp.base;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.hp.serviceInter.EquipmentServiceInter;
import com.hp.serviceInter.LineServiceInter;
import com.hp.serviceInter.MaintainItemsServiceInter;
import com.hp.serviceInter.MaintainRecordServiceInter;
import com.hp.serviceInter.UserServiceInter;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,RequestAware,SessionAware{

	protected T model;
	protected Map<String, Object> request;
	protected Map<String, Object> session;
	
	public BaseAction() {
		// TODO Auto-generated constructor stub
		Class<T> clazz;
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
		try {
			model = clazz.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
	
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
	
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
	@Resource
	protected EquipmentServiceInter equipmentService;
	
	@Resource
	protected LineServiceInter lineService;
	
	@Resource
	protected MaintainItemsServiceInter maintainItemsService;
	
	@Resource
	protected MaintainRecordServiceInter maintainRecordService;
	
	@Resource
	protected UserServiceInter userService;
}
