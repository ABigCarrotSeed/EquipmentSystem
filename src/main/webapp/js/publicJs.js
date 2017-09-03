/**
 * 
 */

$(document).ready(function(){
	
	//显隐设备码信息
	$("#equipmentInfo").hide();
	$("#showequipmentInfo").click(function(){
		$("#equipmentInfo").toggle();
		
	});
	
	//获取日保养的运行时间  进行判断，小于24小时 显示绿色，24-28小时显示黄色，超过28小时显示红色
	 var timeOfDay = $("#runTimeOfDay").text();
	 if(timeOfDay<=24){
		$("#colorOfDay").css("background-color","green");
	}else if(timeOfDay>24 && timeOfDay<=28){
		$("#colorOfDay").css("background-color","yellow");
	}else if(timeOfDay>28){
		$("#colorOfDay").css("background-color","red");
	}
	 
	//获取周保养的运行时间  进行判断，小于24*7小时 显示绿色，24*7-24*7+4小时显示黄色，超过24*7+4小时显示红色
	 var timeOfWeek = $("#runTimeOfWeek").text();
	 if(timeOfWeek <= 24*7){
		 $("#colorOfWeek").css("background-color","green");
	 }else if(timeOfWeek>24*7 && timeOfWeek<=24*7+4){
		 $("#colorOfWeek").css("background-color","yellow");
	 }else if (timeOfWeek>24*7+4) {
		 $("#colorOfWeek").css("background-color","red");
	}
	 
	//获取月保养的运行时间 
	 var timeOfMonth = $("#runTimeOfMonth").text();
	 if(timeOfMonth <= 24*30){
		 $("#colorOfMonth").css("background-color","green");
	 }else if(timeOfMonth>24*30 && timeOfMonth<=24*30+4){
		 $("#colorOfMonth").css("background-color","yellow");
	 }else if (timeOfMonth>24*30+4) {
		 $("#colorOfMonth").css("background-color","red");
	}
	 
	//获取季保养的运行时间 
	 var timeOfQuarter = $("#runTimeOfQuarter").text();
	 if(timeOfQuarter <= 24*92){
		 $("#colorOfQuarter").css("background-color","green");
	 }else if(timeOfQuarter>24*92 && timeOfQuarter<=24*92+4){
		 $("#colorOfQuarter").css("background-color","yellow");
	 }else if (timeOfQuarter>24*92+4) {
		 $("#colorOfQuarter").css("background-color","red");
	}
	 
	 //获取半年保养的运行时间 
	 var timeOfHalfYear = $("#runTimeOfHalfYear").text();
	 if(timeOfHalfYear <= 24*182){
		 $("#colorOfHalfYear").css("background-color","green");
	 }else if(timeOfHalfYear>24*182 && timeOfHalfYear<=24*182+4){
		 $("#colorOfHalfYear").css("background-color","yellow");
	 }else if (timeOfHalfYear>24*182+4) {
		 $("#colorOfHalfYear").css("background-color","red");
	}
	 
	 //获取年保养的运行时间
	 var timeOfYear = $("#runTimeOfYear").text();
	 if(timeOfYear <= 24*365){
		 $("#colorOfYear").css("background-color","green");
	 }else if(timeOfYear>24*365 && timeOfYear<=24*365+4){
		 $("#colorOfYear").css("background-color","yellow");
	 }else if (timeOfYear>24*365+4) {
		 $("#colorOfYear").css("background-color","red");
	}
	 
	 //全选按钮
	 $("#allCheck").click(function(){
		 $("[type='checkbox']").attr("checked","true");
	 });
	 //全不选
	 $("#moveCheck").click(function(){
		 $("[type='checkbox']").removeAttr("checked");
	 });
	 
	 //选择 非 日 周保养时 月选框变为不可选，选择周期后自动选择当前年 月
	 $("#dateType").change(function(){
		 var type = $("#dateType").val();
		 var nowDate= new Date();
		 var year = nowDate.getFullYear();
		 var month = nowDate.getMonth();
		 $("#year").val(year);
		 if(type=='day' || type == 'week'){
			 $("#month").removeAttr("disabled");
			 $("#month").css("background-color","");
			 $("#month").val(month+1);
		 }else{
			 $("#month").attr("disabled","disabled");
			 $("#month").css("background-color","gray");
		 }
	 });
	 
	 //第一次进入查询页面时 year的值是零 此时隐藏table
	 var year = $(".checkYear").val();
	 if(year==0){
		 $(".maintainTable").hide();
	 }else{
		 $(".maintainTable").show();
	 }
	 
})