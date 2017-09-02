<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/public/commos.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>queryList</title> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.1.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/publicJs.js"></script>
	
  
  </head>
  
  <body>
   	<jsp:include page="/WEB-INF/jsp/public/head.jsp" />
   	<div class="common">
   		<!-- 查询条件 -->
   		<div class="inputInfo">
   			<form action="${pageContext.request.contextPath}/query_getEquipmentList.do" method="post">
   				<table >
   					<tr>
   						<td>周期类型</td>
   						<td>
   							<select name="dateType" id="dateType">
   								<option value="nochioce"></option>
   								<option value="day">day</option>
   								<option value="week">week</option>
   								<option value="month">month</option>
   								<option value="quarter">quarter</option>
   								<option value="halfyear">half-year</option>
   								<option value="year">year</option>
   							</select>
   						</td>
   						<td>年份</td>
   						<td>
   							<select name="year" id="year">
   								<option value="-1"></option>
   								<option value="2015">2015</option>
   								<option value="2016">2016</option>
   								<option value="2017">2017</option>
   							</select>
   						</td>
   						<td>月份</td>
   						<td>
   							<select name="month" id="month">
   								<option value="-1"></option>
   								<option value="1">1</option>
   								<option value="2">2</option>
   								<option value="3">3</option>
   								<option value="4">4</option>
   								<option value="5">5</option>
   								<option value="6">6</option>
   								<option value="7">7</option>
   								<option value="8">8</option>
   								<option value="9">9</option>
   								<option value="10">10</option>
   								<option value="11">11</option>
   								<option value="12">12</option>
   							</select>
   						</td>
   						<td><input type="submit" value="查询"/>${info }</td>
   					</tr>
   				</table>
   			</form>
   		</div>
   		
   		<!-- 查询结果 -->
   		<div class="maintainTable">
   			<form action="#" method="post">
   				<table border="1" cellspacing="0" style="border:#A9A9A9 1px solid;">
   					<tr>
   						<th>总报表</th>
   						<th>RowId</th>
   						<th>设备序号</th>
   						<th>设备种类</th>
   						<th>设备型号</th>
   						<th>周期类型</th>
   						<th>线别</th>
   					</tr>
   					<s:iterator value="%{#request.equipmentList}" id="equipment" status="i">
   					<tr>
   						<td>
   						<s:a target="_blank" href="query_goFormUI.do?year=%{year}&month=%{month}&dateType=%{dateType}&equipmentId=%{id}&machineVersion=%{machine.version}&equipmentEid=%{eid}&lineName=%{line.name}" theme="simple">查看</s:a>
   						</td>			
   						<td><s:property value="#i.index+1"/> </td>
   						<td><s:property value="eid"/></td>
   						<td><s:property value="machine.type"/></td>
   						<td><s:property value="machine.version"/></td>
   						<td>${dateType }</td>
   						<td><s:property value="line.name"/></td>
   					</tr>
   					</s:iterator>
   					<input type="hidden" value="${year }" class="checkYear"/>
   					<input type="hidden" value="${month }"/>
   				</table>
   			</form>
   		</div>
   	</div>
   		
  </body>
</html>
