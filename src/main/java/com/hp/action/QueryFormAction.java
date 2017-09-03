package com.hp.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hp.domain.Equipment;
import com.hp.domain.MaintainItems;
import com.hp.domain.Users;
import com.hp.service.EquipmentService;
import com.hp.service.MaintainItemsService;
import com.hp.service.MaintainRecordService;

@Controller
@Scope("prototype")
public class QueryFormAction implements RequestAware,SessionAware{

	private String dateType;
	private int year;
	private int month;
	private String equipmentEid;
	private String lineName;
	
	//用于分页
	private int page = 1;
	private int maxPage;
	
	private Map<String,Object> request ;
	private Map<String,Object> session;
	
	@Resource
	private EquipmentService equipmentService;
	@Resource
	private MaintainItemsService maintainItemsService;
	@Resource
	private MaintainRecordService maintainRecordService;
	
	public String goQueryUI(){
		request.put("type", "保养记录查询");
		return "goQueryUI";
	}

	public String getEquipmentList(){
		request.put("type", "保养记录查询");
		Users user = (Users) session.get("user");
		//总页数
		maxPage = equipmentService.getPageTotal(user, 5);
		//获取前五项设备信息，用于分页
		List<Equipment> list = equipmentService.getEquipmentByPage(user, page, 5);
		request.put("equipmentList", list);
		return "getEquipmentList";
	}
	
	public String goFormUI(){
		List<MaintainItems> maintainItems = maintainItemsService.getMaintainItemsByEid(equipmentEid, dateType);
		request.put("maintainItems", maintainItems);
		List<String> record = null;//保养结果信息
		List<String> maintancePerson = null;//保养人信息
		List<String> confirmPerson = null;//确认人信息

		for(int i=0 ; i < maintainItems.size() ;i++){
			record = maintainRecordService.getRecord(equipmentEid, maintainItems.get(i).getId(), year, month,dateType);
			request.put("record"+i,record);
		}
		maintancePerson = maintainRecordService.getmaintainPerson(equipmentEid, year, month, 0, dateType);
		confirmPerson = maintainRecordService.getmaintainPerson(equipmentEid, year, month, 1, dateType);
		request.put("maintancePerson", maintancePerson);
		request.put("confirmPerson", confirmPerson);
		request.put("count", record.size());
		if("day".equals(dateType)){
			return"goDayFormUI";
		}else if("week".equals(dateType)){
			return "goWeekFormUI";
		}else if("month".equals(dateType)){
			return "goMonthFormUI";
		}else if("quarter".equals(dateType)){
			return "goQuarterFormUI";
		}else if("halfyear".equals(dateType)){
			return "goHalfyearFormUI";
		}else if("year".equals(dateType)){
			return "goYearFormUI";
		}

		return "ERROR";
	}
	
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
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

	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public String getEquipmentEid() {
		return equipmentEid;
	}

	public void setEquipmentEid(String equipmentEid) {
		this.equipmentEid = equipmentEid;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
}
