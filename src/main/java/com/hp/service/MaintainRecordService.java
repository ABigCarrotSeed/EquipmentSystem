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

import com.hp.domain.MaintainRecord;
import com.hp.domain.Users;
import com.hp.utils.DateManger;

import javassist.expr.NewArray;

@Service
@Transactional
public class MaintainRecordService {

	@Resource
	private SessionFactory sessionFactory;
	
	//月天数
	private int[] monthCount = {31,0,31,30,31,30,31,31,30,31,30,31};
	
	/**
	 * 
	 * @param eId 设备码
	 * @param type 保养周期类型
	 * @return 最后一次保养时间
	 */
	public Timestamp getLastMaintainTime(String eId,String type){
		String hql = "From MaintainRecord m where m.maintainItems.datecycle.type=? AND m.equipment.eid=? AND"+
					" m.maintaintime >= (SELECT MAX(mm.maintaintime) FROM MaintainRecord mm where mm.maintainItems.datecycle.type=? AND mm.equipment.eid=?)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, type);
		query.setString(1, eId);
		query.setString(2, type);
		query.setString(3, eId);
		List list = query.list();
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
		sessionFactory.getCurrentSession().save(maintainRecord);
		return;
	}
	
	/**
	 * 保存一系列记录
	 * @param list
	 */
	public void saveAll(List<MaintainRecord> list){
		for(MaintainRecord maintainRecord:list){
			saveMaintainRecord(maintainRecord);
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
		if(month != -1){//日，周保养
			int maxDayOfMonth =  new GregorianCalendar(year, month-1, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
			startTime =  new Timestamp(new GregorianCalendar(year, month-1, 1, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, month-1, maxDayOfMonth, 23, 59, 59).getTimeInMillis());
		}else{
			startTime = new Timestamp(new GregorianCalendar(year, 0, 0, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, 11, 31, 23, 59, 59).getTimeInMillis());
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, user.getDepartment().getId());
		query.setString(1, dateType);
		query.setTime(2, startTime);
		query.setTime(3, endTime);
		List<MaintainRecord> list = query.list();
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
	
	public MaintainRecord getMaintainRecordById(int id){
		String hql = "from MaintainRecord m WHERE m.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		List<MaintainRecord> list = query.list();
		if(list !=null && list.size() == 1){
			return list.get(0);
		}
		return null;	
	}
	
	/**
	 * 返回某设备 的某一条保养项目的保养结果
	 * @param equipmentEid
	 * @param maintainItermsId
	 * @param year
	 * @param month
	 * @param type 保养周期
	 * @return
	 */
	public List<String> getRecord(String equipmentEid,int maintainItermsId,int year,int month,String type){
		List<String> result = new ArrayList<String>();
		//如果是闰年
		if((year%4==0&&year%100!=0)||(year%400==0)){
			monthCount[1]=29;
		}else{
			monthCount[1]=28;
		}
		Timestamp startTime;
		Timestamp endTime;
		String hql = "FROM MaintainRecord m WHERE m.equipment.eid=? AND m.maintainItems.id=? AND "
				+ "m.maintaintime>? AND m.maintaintime<? order by m.maintaintime DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, equipmentEid);
		query.setParameter(1, maintainItermsId);
		if("day".equals(type)){
			for(int i=0 ; i<monthCount[month-1] ; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					result.add(null);
				}else if(list.get(0).getMaintainItems().getSelection()==0){
					result.add(list.get(0).getFirstResult());
				}else {
					result.add(list.get(0).getSecResult());
				}
				
			}
			return result;
		}
		/**
		 * 周保养策略：
		 * 		每个月都定义为5周
		 * 		第一周从该月的第一个星期天开始
		 * 		第二周从该月的第二个星期天开始 以此类推
		 * 		最后一周的最后一天若不是周6 则以下月的第一个周六作为最后一周的截至时间
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
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					result.add(null);
				}else if(list.get(0).getMaintainItems().getSelection()==0){
					result.add(list.get(0).getFirstResult());
				}else {
					result.add(list.get(0).getSecResult());
				}
			}
			return result;
		}
		
		if("month".equals(type)){
			for(int i=0 ; i<12 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i, monthCount[i], 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					result.add(null);
				}else if(list.get(0).getMaintainItems().getSelection()==0){
					result.add(list.get(0).getFirstResult());
				}else {
					result.add(list.get(0).getSecResult());
				}
			}
			return result;
		}
		
		if("quarter".equals(type)){
			for(int i=0 ; i<4 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*3, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*3+2, monthCount[i*3+2], 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					result.add(null);
				}else if(list.get(0).getMaintainItems().getSelection()==0){
					result.add(list.get(0).getFirstResult());
				}else {
					result.add(list.get(0).getSecResult());
				}
			}
			return result;
		}
		
		if("halfyear".equals(type)){
			for(int i=0 ; i<2 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*6, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*6+5, monthCount[i*6+5], 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					result.add(null);
				}else if(list.get(0).getMaintainItems().getSelection()==0){
					result.add(list.get(0).getFirstResult());
				}else {
					result.add(list.get(0).getSecResult());
				}
			}
			return result;
		}
		
		if("year".equals(type)){
			startTime = new Timestamp(new GregorianCalendar(year, 0, 1, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, 11, 31, 23, 59, 59).getTimeInMillis());
			query.setParameter(2, startTime);
			query.setParameter(3, endTime);
			List<MaintainRecord> list = query.list();
			if(list.isEmpty()){
				result.add(null);
			}else if(list.get(0).getMaintainItems().getSelection()==0){
				result.add(list.get(0).getFirstResult());
			}else {
				result.add(list.get(0).getSecResult());
			}
		
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
		//根据是否闰年 确定2月的天数
		if((year%4==0&&year%100!=0)||(year%400==0)){
			monthCount[1]=29;
		}else{
			monthCount[1]=28;
		}
		Timestamp startTime;
		Timestamp endTime;
		String hql = "FROM MaintainRecord m WHERE m.equipment.eid=? AND m.maintainItems.datecycle.type=? AND "
				+ "m.maintaintime>? AND m.maintaintime<?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, equipmentEid);
		query.setParameter(1, type);
		//保养周期为日保养时 取 保养人 或 确认人
		if("day".equals(type)){
			for(int i=0 ; i<monthCount[month-1] ; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, month-1, i+1, 23, 59, 59).getTimeInMillis());
				query.setParameter(2,startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					maintainPerson.add(null);
				}else{
					MaintainRecord maintainRecord = list.get(0);
					if(0==flag){
							maintainPerson.add(maintainRecord.getUsersByUId().getName());
					}else if(1==flag){
						if(maintainRecord.getUsersByEnId()!=null)//如果还没进行保养确认就把确认人信息置为null
							maintainPerson.add(maintainRecord.getUsersByEnId().getName());
						else
							maintainPerson.add(null);
					}
				}
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
				}else{//次月的第一个星期6
					endTime = new Timestamp(new GregorianCalendar(year, month, DateManger.getFirstSundayOfMonth(year, month+1)-1, 23, 59, 59).getTimeInMillis());
				}
				query.setParameter(2,startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					maintainPerson.add(null);
				}else{
					MaintainRecord maintainRecord = list.get(0);
					if(0==flag){
							maintainPerson.add(maintainRecord.getUsersByUId().getName());
					}else if(1==flag){
						if(maintainRecord.getUsersByEnId()!=null)//如果还没进行保养确认就把确认人信息置为null
							maintainPerson.add(maintainRecord.getUsersByEnId().getName());
						else
							maintainPerson.add(null);
					}
				}
			}
			return maintainPerson;
		}
		
		if("month".equals(type)){
			for(int i=0 ; i<12 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i, monthCount[i], 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					maintainPerson.add(null);
				}else{
					MaintainRecord maintainRecord = list.get(0);
					if(0==flag){
							maintainPerson.add(maintainRecord.getUsersByUId().getName());
					}else if(1==flag){
						if(maintainRecord.getUsersByEnId()!=null)//如果还没进行保养确认就把确认人信息置为null
							maintainPerson.add(maintainRecord.getUsersByEnId().getName());
						else
							maintainPerson.add(null);
					}
				}
			}
			return maintainPerson;
		}
		
		if("quarter".equals(type)){
			for(int i=0 ; i<4 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*3, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*3+2, monthCount[i*3+2], 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					maintainPerson.add(null);
				}else{
					MaintainRecord maintainRecord = list.get(0);
					if(0==flag){
							maintainPerson.add(maintainRecord.getUsersByUId().getName());
					}else if(1==flag){
						if(maintainRecord.getUsersByEnId()!=null)//如果还没进行保养确认就把确认人信息置为null
							maintainPerson.add(maintainRecord.getUsersByEnId().getName());
						else
							maintainPerson.add(null);
					}
				}
			}
			return maintainPerson;
		}
		
		if("halfyear".equals(type)){
			for(int i=0 ; i<2 ;i++){
				startTime = new Timestamp(new GregorianCalendar(year, i*6, 1, 0, 0, 0).getTimeInMillis());
				endTime = new Timestamp(new GregorianCalendar(year, i*6+5, monthCount[i*6+5], 23, 59, 59).getTimeInMillis());
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				List<MaintainRecord> list = query.list();
				if(list.isEmpty()){
					maintainPerson.add(null);
				}else{
					MaintainRecord maintainRecord = list.get(0);
					if(0==flag){
							maintainPerson.add(maintainRecord.getUsersByUId().getName());
					}else if(1==flag){
						if(maintainRecord.getUsersByEnId()!=null)//如果还没进行保养确认就把确认人信息置为null
							maintainPerson.add(maintainRecord.getUsersByEnId().getName());
						else
							maintainPerson.add(null);
					}
				}
			}
			return maintainPerson;
		}
		
		if("year".equals(type)){
			startTime = new Timestamp(new GregorianCalendar(year, 0, 1, 0, 0, 0).getTimeInMillis());
			endTime = new Timestamp(new GregorianCalendar(year, 11, 31, 23, 59, 59).getTimeInMillis());
			query.setParameter(2, startTime);
			query.setParameter(3, endTime);
			List<MaintainRecord> list = query.list();
			if(list.isEmpty()){
				maintainPerson.add(null);
			}else{
				MaintainRecord maintainRecord = list.get(0);
				if(0==flag){
						maintainPerson.add(maintainRecord.getUsersByUId().getName());
				}else if(1==flag){
					if(maintainRecord.getUsersByEnId()!=null)//如果还没进行保养确认就把确认人信息置为null
						maintainPerson.add(maintainRecord.getUsersByEnId().getName());
					else
						maintainPerson.add(null);
				}
			}
			return maintainPerson;
		}
		return null;
	}
}
