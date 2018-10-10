<%@page import="com.adks.commons.util.PropertiesFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/verification.js"></script>
	<script type="text/javascript">
		var contextpath = '${pageContext.request.contextPath}';
	</script>
	<%
		String ossResource=PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
	%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />

