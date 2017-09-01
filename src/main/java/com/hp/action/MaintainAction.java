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

import com.hp.domain.ConfirmInfo;
import com.hp.domain.Equipment;
import com.hp.domain.MaintainItems;
import com.hp.domain.MaintainRecord;
import com.hp.domain.Users;
import com.hp.service.LineService;
import com.hp.service.MaintainItemsService;
import com.hp.service.MaintainRecordService;
import com.hp.utils.DateManger;
import com.hp.service.EquipmentService;

@Controller
@Scope("prototype")
public class MaintainAction implements SessionAware,RequestAware{

	private String lineName;//�߱���
	private String equipmentEId;//�豸��
	private String dateType;//������������
	private int year;//����ȷ��ʱ��ȡ�����
	private int month;//����ȷ��ʱ��ȡ���·�
	//��¼�������
	private List<MaintainRecord> recodeList;
	//��¼ȷ�Ͻ��
	private List<ConfirmInfo> confirmList;
	@Resource
	private LineService lineService;
	@Resource
	private EquipmentService equipmentService;
	@Resource
	private MaintainRecordService maintainRecordService;
	@Resource
	private MaintainItemsService maintainItemsService;
	private Map<String,Object> session;
	private Map<String,Object> request;
	
	/**
	 * ��ת��ѡ���߱�ҳ��
	 * @return
	 */
	public String goSelectLineUI(){
		request.put("type", "ѡ���߱�");
		Users user = (Users) session.get("user");
		List<String> lineName = lineService.getLineNameByUser(user);
		request.put("LineName", lineName);
		return "goSelectLineUI";
	}
	/**
	 * ��ת�������豸��ҳ��
	 * @return
	 */
	public String goInputEquipmentInfoUI(){
		request.put("type", "�����豸��");
		request.put("lineName", lineName);
		Users user = (Users) session.get("user");
		//���ڲ���  �����û������µ������豸��Ϣ��ʾ����
		List<Equipment> list = equipmentService.getAllEquipment(user);
		request.put("equipmentList", list);
		return "goInputEquipmentInfoUI";
	}

	/**
	 * ��ת��ѡ��������ҳ��
	 * @return
	 */
	public String goSelectTypeUI(){
		request.put("type", "ѡ��������");
		Equipment equipment = equipmentService.getEquipmentByEId(equipmentEId);
		request.put("equipment", equipment);
		
		//������������
		String[] type = {"day","week","month","quarter","halfyear","year"};
		Timestamp[]  lastTime =  new Timestamp[6];//���ڴ�Ÿ��������ڵ����һ�α���ʱ��
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
	 * ��ת��������Ŀҳ��
	 * @return
	 */
	public String goMaintainItemsUI(){
		request.put("type", "������Ŀ");
		Equipment equipment = equipmentService.getEquipmentByEId(equipmentEId);
		request.put("equipment", equipment);
		List<MaintainItems> list = maintainItemsService.getMaintainItemsByEid(equipmentEId, dateType);
		request.put("maintainItems", list);
		return "goMaintainItemsUI";
	}
	
	/**
	 * ���汣����¼ ����ת�� ѡ�������ڽ���
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
	 * ��ת������ȷ��UI
	 */
	public String goConfirmUI(){
		request.put("type", "����ȷ��");
		return "goConfirmUI";
	}
	/**
	 * ��ѯ��ȷ����Ŀ
	 * @return
	 */
	public String getQueryConfirmObject(){
		request.put("type", "����ȷ��");
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
