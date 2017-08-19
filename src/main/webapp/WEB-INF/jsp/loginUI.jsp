<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.login{
	width:100%;
	margin: 0 auto; 
}

.title{
	width: 100%;
	height: 200px;
	flaot:left;
}

.img1{
	width: 20%;
	height: 200px;
	float: left;
}

.img2{
	width: 60%;
	height: 50%;
	float: left;
}

.form{
	width: 100%;
	height: 300px;
	float: left;
}

.img3{
	width: 35%;
	height: 100%;
	float: left;
}


/*表单table中的第一列元素*/
.form .td1{
	width: 80px;
	color: blue;
	font-size: 18px;
	letter-spacing: 2px;/*字间距*/
}
/*表单中 table的行间距*/
.form table{
	border-collapse: separate;
	border-spacing: 10px;
}
.form select,.form input{
	width: 180px;
	border: 2px solid #A0A0A0;
}
</style>
<title>login</title>
</head>
<body>
	<div class="login">
		<!-- 图标栏 -->
		<div class="title">
			<img class="img1" src="${pageContext.request.contextPath}/images/white.gif">
			<img class="img2" src="${pageContext.request.contextPath}/images/BigTitle.gif">
		</div>
		<div class="form">
			<img class="img3" src="${pageContext.request.contextPath}/images/white.gif">
			<form action="${pageContext.request.contextPath}/log_login.action" method="post">
				<table>
					<tr>
						<td class="td1">工号：</td>
						<td class="td2"><input type="text" name="jobId"/></td>
					</tr>
					<tr>
						<td class="td1">密码：</td>
						<td class="td2"><input type="password" name="password"/></td>
					</tr>
					<tr>
						<td class="td1"></td>
						<td class="td2"><input type="submit" value="提交"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>