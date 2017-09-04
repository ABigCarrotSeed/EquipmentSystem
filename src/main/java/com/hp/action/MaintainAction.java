package com.hp.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hp.base.BaseAction;
import com.hp.domain.ConfirmInfo;
import com.hp.domain.Equipment;
import com.hp.domain.MaintainItems;
import com.hp.domain.MaintainRecord;
import com.hp.domain.Users;
import com.hp.service.LineService;
import com.hp.service.MaintainItemsService;
import com.hp.service.MaintainRecordService;
import com.hp.serviceInter.EquipmentServiceInter;
import com.hp.serviceInter.LineServiceInter;
import com.hp.serviceInter.MaintainItemsServiceInter;
import com.hp.serviceInter.MaintainRecordServiceInter;
import com.hp.utils.DateManger;
import com.hp.service.EquipmentService;

@Controller
@Scope("prototype")
public class MaintainAction extends BaseAction<MaintainItems>{

	private String lineName;//线别名
	private String equipmentEId;//设备码
	private String dateType;//保养周期
	private int year;
	private int month;
	//保养记录
	private List<MaintainRecord> recodeList;
	//确认信息
	private List<ConfirmInfo> confirmList;
	private Map<String,Object> session;
	private Map<String,Object> request;
	
	/**
	 * 跳转到选择线别页面
	 * @return
	 */
	public String goSelectLineUI(){
		request.put("type", "选择线别");
		Users user = (Users) session.get("user");
		List<String> lineName = lineService.getLineNameByUser(user);
		request.put("LineName", lineName);
		return "goSelectLineUI";
	}
	/**
	 * 跳转到输入设备码页面
	 * @return
	 */
	public String goInputEquipmentInfoUI(){
		request.put("type", "保养设备码");
		request.put("lineName", lineName);
		Users user = (Users) session.get("user");
		//便于测试  将该用户部门下的所有设备信息显示出来
		List<Equipment> list = equipmentService.getAllEquipment(user);
		request.put("equipmentList", list);
		return "goInputEquipmentInfoUI";
	}

	/**
	 * 跳转到选择保养周期页面
	 * @return
	 */
	public String goSelectTypeUI(){
		request.put("type", "ѡ��������");
		Equipment equipment = equipmentService.getEquipmentByEId(equipmentEId);
		request.put("equipment", equipment);
		
		//保养周期类型
		String[] type = {"day","week","month","quarter","halfyear","year"};
		Timestamp[]  lastTime =  new Timestamp[6];//用于存放各保养周期的最后一次保养时间
		for(int i=0;i<type.length;i++){
			lastTime[i] = maintainRecordService.getLastMaintainTime(equipmentEId,type[i]);
		}
		Timestamp[] nextTime = DateManger.getNextTime(lastTime);
		request.put("lastTime", lastTime);
		request.put("nextTime", nextTime);
		int[] runTimeOfHour = DateManger.getRunTimeOfHour(lastTime);
		int[] runTimeOfDay = DateManger.getRunTimeOfDay(runTimeOfHour);
		request.put("runTimeOfDay", runTimeOfDay);
		request.put("runTimeOfHour", runTimeOfHour);
		return "goSelectTypeUI";
	}
	
	/**
	 * 跳转到保养项目页面
	 * @return
	 */
	public String goMaintainItemsUI(){
		request.put("type", "保养项目");
		Equipment equipment = equipmentService.getEquipmentByEId(equipmentEId);
		request.put("equipment", equipment);
		List<MaintainItems> list = maintainItemsService.getMaintainItemsByEid(equipmentEId, dateType);
		request.put("maintainItems", list);
		return "goMaintainItemsUI";
	}
	
	/**
	 * 保存保养记录 并跳转到 选择保养周期界面
	 * @return
	 */
	public String saveMaintainResult(){
		Users user = (Users) session.get("user");
		for(MaintainRecord maintainRecord:recodeList){
			maintainRecord.setUsersByUId(user);
			maintainRecord.setEquipment(equipmentService.getEquipmentByEId(equipmentEId));
			maintainRecord.setMaintaintime(new Timestamp(new Date().getTime()));
		}
		maintainRecordService.saveAll(recodeList);
		return "goSelectTypeUIAction";
	}
	
	/**
	 * 跳转到保养确认UI
	 */
	public String goConfirmUI(){
		request.put("type", "保养确认");
		return "goConfirmUI";
	}
	/**
	 * 查询待确认项目
	 * @return
	 */
	public String getQueryConfirmObject(){
		request.put("type", "保养确认");
		Users user = (Users) session.get("user");
		List<String[]> maintianList = maintainRecordService.getConfirmObject(year, month, dateType, user);
		request.put("maintainList", maintianList);
		return "getQueryConfirmObject";
	}
	
	public String confirmOkUI(){
		Users user = (Users) session.get("user");
		MaintainRecord maintainRecord;
		for(ConfirmInfo confirmInfo : confirmList){
			if("true".equals(confirmInfo.getFlag())){
				maintainRecord = maintainRecordService.getMaintainRecordById(confirmInfo.getRecordId());
				maintainRecord.setUsersByEnId(user);
				maintainRecordService.saveMaintainRecord(maintainRecord);
			}
		}
		return "confirmOkUI";
	}
	
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getEquipmentEId() {
		return equipmentEId;
	}

	public void setEquipmentEId(String equipmentEId) {
		this.equipmentEId = equipmentEId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public List<MaintainRecord> getRecodeList() {
		return recodeList;
	}

	public void setRecodeList(List<MaintainRecord> recodeList) {
		this.recodeList = recodeList;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public List<ConfirmInfo> getConfirmList() {
		return confirmList;
	}
	public void setConfirmList(List<ConfirmInfo> confirmList) {
		this.confirmList = confirmList;
	}
}
