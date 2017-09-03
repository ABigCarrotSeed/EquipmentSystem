package com.hp.domain;
/**
 * 保养确认信息
 * @author hupi
 *
 */
public class ConfirmInfo {

	private String flag;//true表示确认/false表示没确认
	private int recordId;//保养项目ID
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	
}
