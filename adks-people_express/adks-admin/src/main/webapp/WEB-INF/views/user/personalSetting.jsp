<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>网校开通管理</title>
    <%@include file="../public/header.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/login/login.js" ></script>
    
</head>
<body class="easyui-layout">	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto; ">
		<div style="margin-bottom:5px">
			
		</div>
	</div>
	<input type="hidden" id="userId" value="${userId }">
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="数据列表信息" data-options="iconCls:'icon icon-nav',fit:true">
			<form id="zhijiForm" method="post" action="${pageContext.request.contextPath}/user/updatePwd">
		    	<table cellpadding="5" style="width:40%;height:40%">
		    		<tr>
		    			<td width="30%">用户名:</td>
		    			<td><input id="userName" readonly="readonly" type="text" value="${username }"></input></td>
		    		</tr>
		    		<tr>
		    			<td width="30%">原密码:</td>
		    			<td><input class="easyui-textbox" id="password" type="password"  maxlength="6"></input></td>
		    		</tr>
		    		<tr>
		    			<td width="30%">新密码:</td>
		    			<td><input class="easyui-textbox" id="passwordNew" type="password"  maxlength="6" ></input></td>
		    		</tr>
		    		<tr>
		    			<td width="30%">确认密码:</td>
		    			<td><input class="easyui-textbox" id="passwordNew2" type="password"  maxlength="6"></input></td>
		    		</tr>
		    		<tr>
		    			<td colspan="2">
		    				<input type="button" value="保存" onclick="saveChange();" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="取消" onclick="cancelChange();" />
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		</div>
	</div>
</body>
</html>