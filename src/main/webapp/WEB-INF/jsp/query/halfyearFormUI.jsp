<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>半年保养记录表</title> 
  </head>
  
  <body>
  <s:debug></s:debug>
  	<table border="1" cellspacing="0" style="border:#A9A9A9 1px solid;">
  		<tr>

  			<td colspan="5" align="center">
  			<span>${machineVersion }半年保养记录表　</span>
  			<span>${year }年 　${equipmentEid}机台    ${lineName }线别</span>
  			</td>

  		</tr>
  		<tr>

  			<td colspan="3" width="500px">保养项目</td>
  			<td colspan="2">周期</td>

  		</tr>
  		<tr>
  			<td>NO</td>
  			<td>执行动作</td>
  			<td>确认方法及标准</td>
  			<td width="300px">上半年 </td>
  			<td width="300px">下半年</td>
  		</tr>
  		<s:iterator value="%{#request.maintainItems}" id="maintainItems" status="i">
  		<tr>
  			<td><s:property value="#i.index+1"/></td>
  			<td><s:property value="project"/></td>
  			<td><s:property value="norm" /> </td>
  			
  			<s:if test="#i.index==0">
	  			<s:iterator value="%{#request.record0}" id="halfyearRecord0">
	  				<td>
	  				<s:property value="halfyearRecord0"/>
	  				</td>
	  			</s:iterator>
  			</s:if>
  			<s:elseif test="#i.index==1">
  				<s:iterator value="%{#request.record1}" id="halfyearRecord1">
	  				<td>
	  				<s:property value="halfyearRecord1"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==2">
  				<s:iterator value="%{#request.record2}" id="halfyearRecord2">
	  				<td>
	  				<s:property value="halfyearRecord2"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==3">
  				<s:iterator value="%{#request.record3}" id="halfyearRecord3">
	  				<td>
	  				<s:property value="halfyearRecord3"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==4">
  				<s:iterator value="%{#request.record4}" id="halfyearRecord4">
	  				<td>
	  				<s:property value="halfyearRecord4"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==5">
  				<s:iterator value="%{#request.record5}" id="halfyearRecord5">
	  				<td>
	  				<s:property value="halfyearRecord5"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>

  		</tr>
  		</s:iterator>
  		<tr>
  			<td rowspan="6">注意事项</td>
  			<td>1.xxxxxxx</td>
  			<td rowspan="3">保养者签名</td>
  			<s:iterator value="#request.maintancePerson" id="maintancePerson">
  			<td rowspan="3"><s:property value="#maintancePerson"/></td>
  			</s:iterator>
  		</tr>
  		<tr>
  			<td>2.xxxxxxxx</td>
  		</tr>
  		<tr>
  			<td>3.xxxxxxxx</td>
  		</tr>
  		<tr>
  			<td>1.xxxxxxx</td>
  			<td rowspan="3">工程师签名</td>
  			<s:iterator value="#request.confirmPerson" id="confirmPerson">
  			<td rowspan="3"><s:property value="#confirmPerson"/></td>
  			</s:iterator>
  		</tr>
  		<tr>
  			<td>2.xxxxxxxx</td>
  		</tr>
  		<tr>
  			<td>3.xxxxxxxx</td>
  		</tr>
  	</table>
  </body>
</html>

