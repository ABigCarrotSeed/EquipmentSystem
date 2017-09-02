<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>日保养记录表</title> 
  </head>
  
  <body>
  <s:debug></s:debug>
  	<table border="1" cellspacing="0" style="border:#A9A9A9 1px solid;">
  		<tr>
  			<s:if test="#request.count==31">
	  			<td colspan="34" align="center">
	  			<span>${machineVersion }日保养记录表　</span>
	  			<span>${year }年　${month }月  ${equipmentEid}机台   ${lineName }线别</span>
	  			</td>
  			</s:if>
  			<s:elseif test="#request.count==30">
	  			<td colspan="33" align="center">
	  			<span>${machineVersion }日保养记录表　</span>
	  			<span>${year }年　${month }月  ${equipmentEid}机台   ${lineName }线别</span>
	  			</td>
  			</s:elseif>
  			<s:elseif test="#request.count==28">
	  			<td colspan="31" align="center">
	  			<span>${machineVersion }日保养记录表　</span>
	  			<span>${year }年　${month }月  ${equipmentEid}机台   ${lineName }线别</span>
	  			</td>
  			</s:elseif>
  			<s:elseif test="#request.count==29">
	  			<td colspan="32" align="center">
	  			<span>${machineVersion }日保养记录表　</span>
	  			<span>${year }年　${month }月  ${equipmentEid}机台   ${lineName }线别</span>
	  			</td>
  			</s:elseif>
  		</tr>
  		<tr>
  			<s:if test="#request.count==31">
	  			<td colspan="3">保养项目</td>
	  			<td colspan="31">日期</td>
  			</s:if>
  			<s:elseif test="#request.count==30">
  				<td colspan="3">保养项目</td>
	  			<td colspan="30">日期</td>
  			</s:elseif>
  			<s:elseif test="#request.count==29">
  				<td colspan="3">保养项目</td>
	  			<td colspan="29">日期</td>
  			</s:elseif>
  			<s:elseif test="#request.count==28">
  				<td colspan="3">保养项目</td>
	  			<td colspan="28">日期</td>
  			</s:elseif>
  			
  		</tr>
  		<tr>
  			<td>NO</td>
  			<td>执行动作</td>
  			<td>确认方法及标准</td>
  			<s:iterator value="%{#request.record0}" status="i">
  			<td width="25px"><s:property value="#i.index+1"/> </td>
  			</s:iterator>
  		</tr>
  		<s:iterator value="%{#request.maintainItems}" id="maintainItems" status="i">
  		<tr>
  			<td><s:property value="#i.index+1"/></td>
  			<td><s:property value="#maintainItems.project"/></td>
  			<td><s:property value="#maintainItems.norm" /> </td>
  			<s:if test="#i.index==0">
	  			<s:iterator value="%{#request.record0}" id="dayRecord0">
	  				<td>
	  				<s:property value="dayRecord0"/>
	  				</td>
	  			</s:iterator>
  			</s:if>
  			<s:elseif test="#i.index==1">
  				<s:iterator value="%{#request.record1}" id="dayRecord1">
	  				<td>
	  				<s:property value="dayRecord1"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==2">
  				<s:iterator value="%{#request.record2}" id="dayRecord2">
	  				<td>
	  				<s:property value="dayRecord2"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==3">
  				<s:iterator value="%{#request.record3}" id="dayRecord3">
	  				<td>
	  				<s:property value="dayRecord3"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==4">
  				<s:iterator value="%{#request.record4}" id="dayRecord4">
	  				<td>
	  				<s:property value="dayRecord4"/>
	  				</td>
	  			</s:iterator>
  			</s:elseif>
  			<s:elseif test="#i.index==5">
  				<s:iterator value="%{#request.record5}" id="dayRecord5">
	  				<td>
	  				<s:property value="dayRecord5"/>
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
