<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>main</title> 

  </head>
  
  
    <frameset rows="108,*" framespacing=0 border=0 frameborder="0">
    	<frame noresize name="head" scrolling="no" src="${pageContext.request.contextPath}/home_head.action"></frame>
    	<frameset cols="300,*" framespacing=0 border=0 frameborder="0">
    		<frame noresize name="left" scrolling="no" src="${pageContext.request.contextPath}/home_left.action"></frame>
    		<frame noresize name="right" scrolling="yes" src="${pageContext.request.contextPath}/home_right.action"></frame>
    	</frameset>
    </frameset>
   <body>
  </body>
</html>
