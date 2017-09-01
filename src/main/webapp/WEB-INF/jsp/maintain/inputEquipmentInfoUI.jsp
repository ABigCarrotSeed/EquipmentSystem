<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/public/commos.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>maintainFrame</title> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.1.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/publicJs.js"></script>
  
  </head>
  <body> 
   	<jsp:include page="/WEB-INF/jsp/public/head.jsp" />
   	
   	<div class="common">
   		<div class="inputInfo">
   			<form action="${pageContext.request.contextPath}/maintain_goSelectTypeUI.do" method="post">
   			<table>
   				<tr>
   					<td>设备码:</td>
   					<td><input class="text1" type="text" name="equipmentEId"/></td>
   					<td><input id="submit" type="submit" value="提交"/></td>
   					<td>保养类型:</td>
   					<td><input class="text1" type="text" /></td>
   					<td>设备名称:</td>
   					<td><input class="text1" type="text" /></td>
   				</tr>
   				<tr>
   					<td>设备型号:</td>
   					<td><input class="text1" type="text" /></td>
   					<td></td>
   					<td>设备线别:</td>
   					<td><input class="text1" type="text" value="${lineName }" name="lineName"/></td>
   					<td>设备位置:</td>
   					<td><input class="text1" type="text" /></td>
   				</tr>
   			</table>
   			</form>
   			<button type="button" id="showequipmentInfo">显示所有设备码</button>
   			<table id="equipmentInfo" >
   				<s:iterator value="%{#request.equipmentList}" id="equipment">
   				<tr>
   					<td>${equipment.eid }</td>
   					<td>${equipment.line.name }</td>
   				</tr>
   				</s:iterator>
   			</table>
   		</div>
   		
   	</div>
  </body>
</html>
