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

import javassist.expr.NewArray;

@Service
@Transactional
public class MaintainRecordService {

	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 
	 * @param eId 设备码
	 * @param type 保养周期类型
	 * @return 最后一次保养时间
	 */
	public Timestamp getLastMaintainTime(String eId,String type){
		String hql = "From MaintainRecord m where m.maintainItems.datecycle.type=? AND m.equipment.eid=? AND"+
					" m.maintaintime >= (SELECT MAX(mm.maintaintime) FROM MaintainRecord mm where mm.maintainItems.datecycle.type=?)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, type);
		query.setString(1, eId);
		query.setString(2, type);
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
}
