package com.hp.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hp.base.BaseAction;

@Controller
@Scope("prototype")
public class HomeAction{

	public String head(){
		
		return "head";
	}
	
	public String left(){
		
		return "left";
	}
	
	public String right(){
		
		return "right";
	}
}
