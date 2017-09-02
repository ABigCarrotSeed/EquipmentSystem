package com.hp.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateManger {

	/**
	 * 
	 * @param lastTime ��ǰ���һ�α���ʱ��
	 * @return �´α���ʱ��
	 */
	public static Timestamp[] getNextTime(Timestamp[] lastTime){
		Timestamp[] nextTime = new Timestamp[6];
		int[] type = {1,7,30,30*3,30*6,356};//�������ڵ�����
		for(int i=0 ; i<lastTime.length ; i++){
			if(lastTime[i] != null){
				nextTime[i] = new Timestamp(lastTime[i].getTime()+86400000L*type[i]);
			}else{
				nextTime[i] = null;
			}
		}
		return nextTime;
	}
	
	/**
	 * ��ȡ��������ʱ�䣬���ڵ�ʱ���뱣��ʱ��֮��
	 * @param lastTime
	 * @return
	 */
	public static int[] getRunTimeOfHour(Timestamp[] lastTime){
		int[] runTimeOfHour = new int[6];
		Date now = new Date();
		
		for(int i=0 ; i<lastTime.length ; i++){
			if(lastTime[i]!=null){
				runTimeOfHour[i] = (int)((now.getTime() - lastTime[i].getTime())/(60*60*1000));
			}else{
				runTimeOfHour[i] = 0;
			}
		}
		return runTimeOfHour;
	}
	
	/**
	 * ��ȡ��������
	 * @param runTimeOfHour
	 * @return
	 */
	public static int[] getRunTimeOfDay(int[] runTimeOfHour){
		int[] runTimeOfDay = new int[6];
		
		for(int i=0 ; i<runTimeOfHour.length ; i++){
			if(runTimeOfHour[i]==0){
				runTimeOfDay[i] = 0;
			}else{
				runTimeOfDay[i] = runTimeOfHour[i]/24 + 1;
			}
		}
		return runTimeOfDay;
	}
	
	/**
	 * ��ȡ���µ�һ���������Ǽ���
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getFirstSundayOfMonth(int year, int month)
	{
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1); // ��Ϊ��һ��

		int i=1;
		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			i++;
			cal.add(Calendar.DATE, 1);
		}

		return i;
	}
}
