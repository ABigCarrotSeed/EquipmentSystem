package com.hp.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.CascadeType;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.base.BaseDaoInter;
import com.hp.domain.MaintainRecord;
import com.hp.domain.Users;
import com.hp.serviceInter.MaintainRecordServiceInter;
import com.hp.utils.DateManger;
import com.hp.utils.MaintainRecordManager;

import javassist.expr.NewArray;

@Service
@Transactional
public class MaintainRecordService implements MaintainRecordServiceInter{

	@Resource
	private BaseDaoInter baseDao;
	
	//月天数
	private int[] monthCount = {31,0,31,30,31,30,31,31,30,31,30,31};
	
	/**
	 * 
	 * @param eId 设备码
	 * @param type 保养周期
	 * @return 最后一次保养时间
	 */
	public Timestamp getLastMaintainTime(String eId,String type){
		String hql = "From MaintainRecord m where m.maintainItems.datecycle.type=? AND m.equipment.eid=? AND"+
					" m.maintaintime >= (SELECT MAX(mm.maintaintime) FROM MaintainRecord mm where mm.maintainItems.datecycle.type=? AND mm.equipment.eid=?)";
		Object[] parameters = {type,eId,type,eId};
		List list = baseDao.getResult(hql, parameters);
		if(list != null && list.size()>0){
			MaintainRecord maintainRecord = (MaintainRecord) list.get(0);
			return maintainRecord.getMaintaintime();
		}
		return null;
	}
	
	/**
	 * 保存一条记录
	 * @param maintainRecord
	 */
	public void saveMaintainRecord(MaintainRecord maintainRecord){
		baseDao.save(maintainRecord);
		return;
	}
	
	/**
	 * 保存一系列记录
	 * @param list
	 */
	public void saveAll(List<MaintainRecord> list){
		for(MaintainRecord maintainRecord:list){
			baseDao.save(maintainRecord);
		}
	}
	
	/**
	 * 查询出 保养记录ID，设备条码，机器类型，机器型号，保养周期，线别名，保养时间，保养项目，标准，保养结果，保养者工号
	 * @param year
	 * @param month
	 * @param dateType
	 * @param user
	 * @return
	 */
	public List<String[]> getConfirmObject(int year,int month,String dateType,Users user){
		String hql = "FROM MaintainRecord m WHERE m.equipment.line.department.id=? AND "
				+ "m.maintainItems.datecycle.type=? AND "
				+ "m.maintaintime >= ? AND "
				+ "m.maintaintime <= ? AND "
				+ "m.usersByEnId.id IS null";
		Timestamp startTime;
		Timestamp endTime;
		if(month != -1){//日 周 以外的其他周期 month=-1
			int maxDayOfMonth =  new GregorianCalendar(year, month-1, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
			startTime =  new Timestamp(new GregorianCalendar(year, month-1, 1, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, month-1, maxDayOfMonth, 23, 59, 59).getTimeInMillis());
		}else{
			startTime = new Timestamp(new GregorianCalendar(year, 0, 0, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, 11, 31, 23, 59, 59).getTimeInMillis());
		}		
		Object[] parameters = {user.getDepartment().getId(),dateType,startTime,endTime};
		List<MaintainRecord> list = baseDao.getResult(hql, parameters);
		List<String[]> info = new ArrayList<String[]>();
		for(MaintainRecord maintainRecord : list){
			String[] messge = new String[11];
			messge[0] = maintainRecord.getEquipment().getEid();//设备码
			messge[1] = maintainRecord.getEquipment().getMachine().getType();//设备类型
			messge[2] = maintainRecord.getEquipment().getMachine().getVersion();//设备版本
			messge[3] = maintainRecord.getMaintainItems().getDatecycle().getType();//保养周期
			messge[4] = maintainRecord.getMaintaintime().toString();//保养时间
			messge[5] = maintainRecord.getMaintainItems().getProject();//保养项目
			messge[6] = maintainRecord.getMaintainItems().getNorm();//保养标准
			if(maintainRecord.getMaintainItems().getSelection()==0){
				messge[7] = maintainRecord.getFirstResult();
			}else{
				messge[7] = maintainRecord.getSecResult();
			}
			messge[8] = maintainRecord.getEquipment().getLine().getName();//线别
			messge[9] = maintainRecord.getUsersByUId().getName();//保养人
			messge[10] = maintainRecord.getId().toString();
			info.add(messge);
		}
		return info;
	}
	
	/**
	 * 返回保养记录
	 * @param id 记录Id
	 */
	public MaintainRecord getMaintainRecordById(int id){
		String hql = "from MaintainRecord m WHERE m.id=?";
		Object[] parameters = {id};
		List<MaintainRecord> list = baseDao.getResult(hql, parameters);
		if(list !=null && list.size() == 1){
			return list.get(0);
		}
		return null;	
	}
	
	/**
	 *返回某设备 的某一条保养项目的保养结果
	 * @param equipmentEid
	 * @param maintainItermsId
	 * @param year
	 * @param month
	 * @param type 保养周期
	 * @return
	 */
	public List<String> getRecord(String equipmentEid,int maintainItermsId,int year,int month,String type){
		List<String> result = new ArrayList<String>();
		List<MaintainRecord> list = new ArrayList<MaintainRecord>();
		//根据闰年与否来设置2月的天数
		if((year%4==0&&year%100!=0)||(year%400==0)){
			monthCount[1]=29;
		}else{
			monthCount[1]=28;
		}
		Timestamp startTime = null;
		Timestamp endTime = null;
		String hql = "FROM MaintainRecord m WHERE m.equipment.eid=? AND m.maintainItems.id=? AND "
				+ "m.maintaintime>? AND m.maintaintime<? order by m.maintaintime DESC";
		Object[] parameters = {equipmentEid,maintainItermsId,startTime,endTime};
		if("day".equals(type)){
			for(int i=0 ; i<monthCount[month-1] ; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				result.add(MaintainRecordManager.getResult(list));
			}
			return result;
		}
		/**
		 * 周保养策略：
		 * 		第一周从该月的第一个星期天开始
		 * 		第二周从该月的第二个星期天开始 以此类推
		 * 		最后一周的最后一天若不是周6 则以下月的第一个周六作为最后一周的截至时间
		 * 		
		 */
		if("week".equals(type)){
			//从该月的第一个星期天开始算该月有几周  逢余进一
			int firstSunday = DateManger.getFirstSundayOfMonth(year, month);
			int count = ((monthCount[month-1]-firstSunday+1)%7==0)?(monthCount[month-1]-firstSunday+1)/7:(monthCount[month-1]-firstSunday+1)/7+1;
			for(int i=0 ; i<count; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday, 0, 0, 0).getTimeInMillis());
				//假如该周的截至日期大大于该月的天数,将截至日期设为下月的第一个星期6
				if(i*7+firstSunday<=monthCount[month-1]){
					endTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday+6, 23, 59, 59).getTimeInMillis());
				}else{//次月的第一个星期6
					endTime = new Timestamp(new GregorianCalendar(year, month, DateManger.getFirstSundayOfMonth(year, month+1)-1, 23, 59, 59).getTimeInMillis());
				}
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				result.add(MaintainRecordManager.getResult(list));
			}
			return result;
		}
		
		if("month".equals(type)){
			for(int i=0 ; i<12 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i, monthCount[i], 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				result.add(MaintainRecordManager.getResult(list));
			}
			return result;
		}
		
		if("quarter".equals(type)){
			for(int i=0 ; i<4 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*3, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*3+2, monthCount[i*3+2], 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				result.add(MaintainRecordManager.getResult(list));
			}
			return result;
		}
		
		if("halfyear".equals(type)){
			for(int i=0 ; i<2 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*6, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*6+5, monthCount[i*6+5], 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				result.add(MaintainRecordManager.getResult(list));
			}
			return result;
		}
		
		if("year".equals(type)){
			startTime = new Timestamp(new GregorianCalendar(year, 0, 1, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, 11, 31, 23, 59, 59).getTimeInMillis());
			parameters[2] = startTime;
			parameters[3] = endTime;
			list = baseDao.getResult(hql, parameters);
			result.add(MaintainRecordManager.getResult(list));
			return result;
		}
		return null;
	}
	
	/**
	 * 
	 * @param equipmentEid
	 * @param year
	 * @param month
	 * @param flag flag 为0 时取 保养人   为1时取确认人
	 * @param type 保养周期类型
	 * @return 保养人或是确认人
	 */
	public List<String> getmaintainPerson(String equipmentEid,int year,int month,int flag,String type){
		List<String> maintainPerson = new ArrayList<String>();
		List<MaintainRecord> list = new ArrayList<MaintainRecord>();
		//根据是否闰年 确定2月的天数
		if((year%4==0&&year%100!=0)||(year%400==0)){
			monthCount[1]=29;
		}else{
			monthCount[1]=28;
		}
		Timestamp startTime = null;
		Timestamp endTime = null;
		String hql = "FROM MaintainRecord m WHERE m.equipment.eid=? AND m.maintainItems.datecycle.type=? AND "
				+ "m.maintaintime>? AND m.maintaintime<?";
		Object[] parameters = {equipmentEid,type,startTime,endTime};
		//保养周期为日保养时 取 保养人 或 确认人
		if("day".equals(type)){
			for(int i=0 ; i<monthCount[month-1] ; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				maintainPerson.add(MaintainRecordManager.getPerson(list, flag));
			}
			return maintainPerson;
		}
		//保养周期为周保养 
		if("week".equals(type)){
			//从该月的第一个星期天开始算该月有几周  逢余进一
			int firstSunday = DateManger.getFirstSundayOfMonth(year, month);
			int count = ((monthCount[month-1]-firstSunday+1)%7==0)?(monthCount[month-1]-firstSunday+1)/7:(monthCount[month-1]-firstSunday+1)/7+1;
			for(int i=0 ; i<count; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday, 0, 0, 0).getTimeInMillis());
				//假如该周的截至日期大大于该月的天数,将截至日期设为下月的第一个星期6
				if(i*7+firstSunday<=monthCount[month-1]){
					endTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday+6, 23, 59, 59).getTimeInMillis());
				}else{
					endTime = new Timestamp(new GregorianCalendar(year, month, DateManger.getFirstSundayOfMonth(year, month+1)-1, 23, 59, 59).getTimeInMillis());
				}
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				maintainPerson.add(MaintainRecordManager.getPerson(list, flag));
			}
			return maintainPerson;
		}
		
		if("month".equals(type)){
			for(int i=0 ; i<12 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i, monthCount[i], 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				maintainPerson.add(MaintainRecordManager.getPerson(list, flag));
			}
			return maintainPerson;
		}
		
		if("quarter".equals(type)){
			for(int i=0 ; i<4 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*3, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*3+2, monthCount[i*3+2], 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				maintainPerson.add(MaintainRecordManager.getPerson(list, flag));
			}
			return maintainPerson;
		}
		
		if("halfyear".equals(type)){
			for(int i=0 ; i<2 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*6, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*6+5, monthCount[i*6+5], 23, 59, 59).getTimeInMillis());
				parameters[2] = startTime;
				parameters[3] = endTime;
				list = baseDao.getResult(hql, parameters);
				maintainPerson.add(MaintainRecordManager.getPerson(list, flag));
			}
			return maintainPerson;
		}
		
		if("year".equals(type)){
			startTime = new Timestamp(new GregorianCalendar(year, 0, 1, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, 11, 31, 23, 59, 59).getTimeInMillis());
			parameters[2] = startTime;
			parameters[3] = endTime;
			list = baseDao.getResult(hql, parameters);
			maintainPerson.add(MaintainRecordManager.getPerson(list, flag));
			return maintainPerson;
		}
		return null;
	}
}
