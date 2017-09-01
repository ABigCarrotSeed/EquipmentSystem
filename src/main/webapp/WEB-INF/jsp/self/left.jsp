<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/public/commos.jsp" %>
<html>
	
	<body>
    <div id="left">
    	
    	<div id="head1">
    		<table id="table1">
    			<tr>
    			<td><img id="img1" src="${pageContext.request.contextPath}/images/default.gif"/></td>
    			<td>
    				<table id="table2">
    					<tr>
    						<td>用户:</td>
    						<td>${user.name}</td>
    					</tr>
    					<tr >
    						<td colspan="2"><a target="_parent" href="${pageContext.request.contextPath}/log_logOut.do" style="color: #333333;">退出</a></td>
    					</tr>
    				</table>
    			</td>
    			<tr>
    		</table>
    	</div>
    	
    	<div id="head2">
    		<span id="text2">管理系统</span>
    	</div>
    	
    	<div id="info1">
    	<ul>
    	<li><a target="right" href="${pageContext.request.contextPath}/change_goChangePasswordUI.do">用户设置</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/maintain_goSelectLineUI.do">设备保养</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/maintain_goConfirmUI.do">保养确认</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/query_goQueryUI.do">保养报表查询</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/user_goList.do">用户管理</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/equipment_goEqupimentList.do">设备管理</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/machine_goMachineList.do">机器管理</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/department_goDepartmentList.do">部门管理</a></li>
    	<li><a target="right" href="${pageContext.request.contextPath}/line_goLineList.do">线别管理</a></li>
    	
    	</ul>
    	</div>
    </div>
   </body> 
 </html>
