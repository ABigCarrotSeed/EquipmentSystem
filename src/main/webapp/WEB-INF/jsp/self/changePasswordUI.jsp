<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/public/commos.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/public/head.jsp"/>
	   <div class="common">
    	<form action="${pageContext.request.contextPath}/change_setNewPassWord.do" method="post">
    	<table id="changePwd">
    		<tr>
    			<td>原密码:</td>
    			<td><input type="password" name="oldPassword"/></td>
    		</tr>
    		<tr>
    			<td>新密码:</td>
    			<td><input type="password" name="newPassword"/></td>
    		</tr>
    		<tr>
    			<td>请确认:</td>
    			<td><input type="password" name="secondPassword"/></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="submit" value="提交"/></td>
    		</tr>
    	</table>
    	</form>
    </div>
</body>
</html>