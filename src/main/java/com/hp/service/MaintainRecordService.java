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
	
	//������
	private int[] monthCount = {31,0,31,30,31,30,31,31,30,31,30,31};
	
	/**
	 * 
	 * @param eId �豸��
	 * @param type ������������
	 * @return ���һ�α���ʱ��
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
	 * ����һ����¼
	 * @param maintainRecord
	 */
	public void saveMaintainRecord(MaintainRecord maintainRecord){
		sessionFactory.getCurrentSession().save(maintainRecord);
		return;
	}
	
	/**
	 * ����һϵ�м�¼
	 * @param list
	 */
	public void saveAll(List<MaintainRecord> list){
		for(MaintainRecord maintainRecord:list){
			saveMaintainRecord(maintainRecord);
		}
	}
	
	/**
	 * ��ѯ�� ������¼ID���豸���룬�������ͣ������ͺţ��������ڣ��߱���������ʱ�䣬������Ŀ����׼����������������߹���
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
		if(month != -1){//�գ��ܱ���
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
			messge[0] = maintainRecord.getEquipment().getEid();//�豸��
			messge[1] = maintainRecord.getEquipment().getMachine().getType();//�豸����
			messge[2] = maintainRecord.getEquipment().getMachine().getVersion();//�豸�汾
			messge[3] = maintainRecord.getMaintainItems().getDatecycle().getType();//��������
			messge[4] = maintainRecord.getMaintaintime().toString();//����ʱ��
			messge[5] = maintainRecord.getMaintainItems().getProject();//������Ŀ
			messge[6] = maintainRecord.getMaintainItems().getNorm();//������׼
			if(maintainRecord.getMaintainItems().getSelection()==0){
				messge[7] = maintainRecord.getFirstResult();
			}else{
				messge[7] = maintainRecord.getSecResult();
			}
			messge[8] = maintainRecord.getEquipment().getLine().getName();//�߱�
			messge[9] = maintainRecord.getUsersByUId().getName();//������
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
	 * ����ĳ�豸 ��ĳһ��������Ŀ�ı������
	 * @param equipmentEid
	 * @param maintainItermsId
	 * @param year
	 * @param month
	 * @param type ��������
	 * @return
	 */
	public List<String> getRecord(String equipmentEid,int maintainItermsId,int year,int month,String type){
		List<String> result = new ArrayList<String>();
		//���������
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
		 * �ܱ������ԣ�
		 * 		ÿ���¶�����Ϊ5��
		 * 		��һ�ܴӸ��µĵ�һ�������쿪ʼ
		 * 		�ڶ��ܴӸ��µĵڶ��������쿪ʼ �Դ�����
		 * 		���һ�ܵ����һ����������6 �������µĵ�һ��������Ϊ���һ�ܵĽ���ʱ��
		 */
		if("week".equals(type)){
			//�Ӹ��µĵ�һ�������쿪ʼ������м���  �����һ
			int firstSunday = DateManger.getFirstSundayOfMonth(year, month);
			int count = ((monthCount[month-1]-firstSunday+1)%7==0)?(monthCount[month-1]-firstSunday+1)/7:(monthCount[month-1]-firstSunday+1)/7+1;
			for(int i=0 ; i<count; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday, 0, 0, 0).getTimeInMillis());
				//������ܵĽ������ڴ���ڸ��µ�����,������������Ϊ���µĵ�һ������6
				if(i*7+firstSunday<=monthCount[month-1]){
					endTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday+6, 23, 59, 59).getTimeInMillis());
				}else{//���µĵ�һ������6
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
	 * @param flag flag Ϊ0 ʱȡ ������   Ϊ1ʱȡȷ����
	 * @param type ������������
	 * @return �����˻���ȷ����
	 */
	public List<String> getmaintainPerson(String equipmentEid,int year,int month,int flag,String type){
		List<String> maintainPerson = new ArrayList<String>();
		//�����Ƿ����� ȷ��2�µ�����
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
		//��������Ϊ�ձ���ʱ ȡ ������ �� ȷ����
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
						if(maintainRecord.getUsersByEnId()!=null)//�����û���б���ȷ�ϾͰ�ȷ������Ϣ��Ϊnull
							maintainPerson.add(maintainRecord.getUsersByEnId().getName());
						else
							maintainPerson.add(null);
					}
				}
			}
			return maintainPerson;
		}
		//��������Ϊ�ܱ��� 
		if("week".equals(type)){
			//�Ӹ��µĵ�һ�������쿪ʼ������м���  �����һ
			int firstSunday = DateManger.getFirstSundayOfMonth(year, month);
			int count = ((monthCount[month-1]-firstSunday+1)%7==0)?(monthCount[month-1]-firstSunday+1)/7:(monthCount[month-1]-firstSunday+1)/7+1;
			for(int i=0 ; i<count; i++){
				startTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday, 0, 0, 0).getTimeInMillis());
				//������ܵĽ������ڴ���ڸ��µ�����,������������Ϊ���µĵ�һ������6
				if(i*7+firstSunday<=monthCount[month-1]){
					endTime = new Timestamp(new GregorianCalendar(year, month-1, i*7+firstSunday+6, 23, 59, 59).getTimeInMillis());
				}else{//���µĵ�һ������6
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
						if(maintainRecord.getUsersByEnId()!=null)//�����û���б���ȷ�ϾͰ�ȷ������Ϣ��Ϊnull
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
						if(maintainRecord.getUsersByEnId()!=null)//�����û���б���ȷ�ϾͰ�ȷ������Ϣ��Ϊnull
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
						if(maintainRecord.getUsersByEnId()!=null)//�����û���б���ȷ�ϾͰ�ȷ������Ϣ��Ϊnull
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
						if(maintainRecord.getUsersByEnId()!=null)//�����û���б���ȷ�ϾͰ�ȷ������Ϣ��Ϊnull
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
					if(maintainRecord.getUsersByEnId()!=null)//�����û���б���ȷ�ϾͰ�ȷ������Ϣ��Ϊnull
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
