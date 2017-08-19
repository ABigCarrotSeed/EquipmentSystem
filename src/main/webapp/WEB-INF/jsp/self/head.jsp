<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/public/commos.jsp" %>
<html>
 <body>
    <div id="head">
    	<img id="img1" src="${pageContext.request.contextPath}/images/title.gif"/>
    	<span id="logInfo1">登录信息: ${user.name }</span>
    </div>
  </body>
 </html>

