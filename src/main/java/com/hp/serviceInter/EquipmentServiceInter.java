package com.hp.serviceInter;

import java.util.List;

import com.hp.domain.Equipment;
import com.hp.domain.Users;

public interface EquipmentServiceInter {

	public List<Equipment> getAllEquipment(Users user);
	
	public Equipment getEquipmentByEId(String Eid);
	
	public List<Equipment> getEquipmentByPage(Users user,int page,int pageSize);
	
	public int getPageTotal(Users user,int pageSize);
}
