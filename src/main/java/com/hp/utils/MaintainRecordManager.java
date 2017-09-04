package com.hp.utils;

import java.util.List;

import com.hp.domain.MaintainRecord;

public class MaintainRecordManager {

	public static String getResult(List<MaintainRecord> list){
	
		if(list.isEmpty()){
			return null;
		}else if(list.get(0).getMaintainItems().getSelection()==0){
			return list.get(0).getFirstResult();
		}else {
			return list.get(0).getSecResult();
		}
	}
	
	public static String getPerson(List<MaintainRecord> list,int flag){
		if(list.isEmpty()){
			return null;
		}else{
			MaintainRecord maintainRecord = list.get(0);
			if(0==flag){
					return maintainRecord.getUsersByUId().getName();
			}else if(1==flag){
				if(maintainRecord.getUsersByEnId()!=null)
					return maintainRecord.getUsersByEnId().getName();
				else{
					return null;
				}
			}
			return null;
		}
	}
}
