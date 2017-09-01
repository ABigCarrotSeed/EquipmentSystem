package com.hp.utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateManger {

	/**
	 * 
	 * @param lastTime 当前最后一次保养时间
	 * @return 下次保养时间
	 */
	public static Timestamp[] getNextTime(Timestamp[] lastTime){
		Timestamp[] nextTime = new Timestamp[6];
		int[] type = {1,7,30,30*3,30*6,356};//各种周期的天数
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
	 * 获取机器运行时间，现在的时间与保养时间之差
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
}
