<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="../static/common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="news">
	<img style="display:block; margin:50px auto;" src="<%=basePath%>/static/images/notice.gif" />
</div>
