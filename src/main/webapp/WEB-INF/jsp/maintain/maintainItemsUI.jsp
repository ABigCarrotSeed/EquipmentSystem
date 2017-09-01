<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/public/commos.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>Maintain.jsp</title> 
  
  </head>
  
  <body>
   <jsp:include page="/WEB-INF/jsp/public/head.jsp" />
   	
   	<div class="common">
   		<div class="inputInfo">
   			<table>
   				<tr>
   					<td>设备码:</td>
   					<td><input class="text1" type="text" value="${equipment.eid }" disabled="disabled"/></td>
   					<td>保养类型:</td>
   					<td><input class="text1" type="text" value="${dateType }" disabled="disabled"/></td>
   					<td>设备名称:</td>
   					<td><input class="text1" type="text" value="${equipment.machine.name }" disabled="disabled"/></td>
   				</tr>
   				<tr>
   					<td>设备型号:</td>
   					<td><input class="text1" type="text" value="${equipment.machine.version }" disabled="disabled"/></td>
   					<td>设备线别:</td>
   					<td><input class="text1" type="text" value="${equipment.line.name }" disabled="disabled"/></td>
   					<td>设备位置:</td>
   					<td><input class="text1" type="text" value="${equipment.sit }" disabled="disabled"/></td>
   				</tr>
   			</table>
   		</div>
   			
   		<div class="maintainTable">
   			<form action="${pageContext.request.contextPath}/maintain_saveMaintainResult.do" method="post">
   				<table border="1" cellspacing="0" style="border:#A9A9A9 1px solid;">
   					<tr>
   						<th>NO</th>
   						<th>项目</th>
   						<th>标准</th>
   						<th>结果</th>
   						<th>备注</th>
   					</tr>
   					
   					<s:iterator value="%{#request.maintainItems}" id="maintainItems" status="i">
   					<tr>
   						<td><s:property value="#i.index+1"/></td>
   						<td><s:property value="#maintainItems.project" /></td>
   						<td><s:property value="#maintainItems.norm"/></td>
   					
   						
   						<s:if test="#maintainItems.selection==0">
   						<td name="result">
   							<s:radio list="#{'OK':'OK','NG':'NG' }" name="%{'recodeList['+#i.index+'].firstResult'}" theme="simple"></s:radio>
   						</td>
   						</s:if>
   						<s:elseif test="#maintainItems.selection==1">
   						<td name="result">
   							<s:textfield name="%{'recodeList['+#i.index+'].secResult'}" theme="simple"/>
   						</td>
   						</s:elseif>
   						<td><s:textfield name="%{'recodeList['+#i.index+'].tip'}" theme="simple"/></td>
   					</tr>
   					<s:hidden name="%{'recodeList['+#i.index+'].maintainItems.id'}" value="%{#maintainItems.id}"/>
   					</s:iterator>
					<input type="hidden" name="equipmentEId" value="${equipment.eid }"/>
					<input type="hidden" name="dateType" value="${dateType }"/>
   					<tr>
   						<td colspan="5" id="submit">
   						<input type="submit" value="提交" id="submit"/>
   						</td>
   						
   					</tr>
   				</table>
   			</form>
   			${lossInfo }
   		</div>	
   			
   	</div>
   	
  </body>
</html>
