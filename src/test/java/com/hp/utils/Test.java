package com.hp.utils;

import java.sql.Timestamp;
import java.util.Date;

public class Test {

	public static void main(String[] args){
	Timestamp start=null;
	Timestamp end=null;
	
	Object[] para = {start,end};
	
	 start = new Timestamp(new Date().getTime());
	 end = new Timestamp(new Date().getTime());
	 
	 System.out.println(para[0]);
	}
	
}
