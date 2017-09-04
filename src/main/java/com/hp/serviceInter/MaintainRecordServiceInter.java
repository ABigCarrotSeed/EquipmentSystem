package com.hp.serviceInter;

import java.sql.Timestamp;
import java.util.List;

import com.hp.domain.MaintainRecord;
import com.hp.domain.Users;

public interface MaintainRecordServiceInter {

	public Timestamp getLastMaintainTime(String eId,String type);
	
	public void saveMaintainRecord(MaintainRecord maintainRecord);
	
	public void saveAll(List<MaintainRecord> list);
	
	public List<String[]> getConfirmObject(int year,int month,String dateType,Users user);
	
	public MaintainRecord getMaintainRecordById(int id);
	
	public List<String> getRecord(String equipmentEid,int maintainItermsId,int year,int month,String type);
	
	public List<String> getmaintainPerson(String equipmentEid,int year,int month,int flag,String type);
}
