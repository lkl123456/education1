<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="begin('<%=contextPath %>');">
<script type="text/javascript">
	function begin(url){
		//window.location.href=contextPath+"/user/toLogin/1.shtml";
		window.location.href=url+"/user/toLogin/1.shtml";
	}
</script>
</body>
</html>