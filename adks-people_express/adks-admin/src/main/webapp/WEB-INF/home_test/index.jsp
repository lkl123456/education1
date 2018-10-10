<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>政府云学堂后台管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
   
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/menuUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/menu.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/login.js" ></script>
   
    <script type="text/javascript">    	
	    $(function() {	
			//初始化左侧菜单
			var userPermisstions = '${userPermisstions}';
			InitLeftMenu(userPermisstions);
	    });


    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">	
	
	<!-- 顶部 -->
	<div data-options="region:'north',border:false" style="height:50px;background:#D2E0F2;padding:10px">
		<span style="padding-left:10px; font-size: 25px; ">学堂云管理平台</span>
		<span style="float:right; padding-right:20px;" class="head">
			<form id ="logout"  action="${pageContext.request.contextPath}/sso/logout" >
						 ${username } <a href="#" onclick="logout();" id="loginOut" class="easyui-linkbutton" iconCls="icon-ok">安全退出</a>
			</form>
		</span>    
	</div>

	<!-- 左侧菜单 -->
	<div id="accordion" data-options="region:'west',split:true,title:'导航菜单'" style="width:260px;">
		
	</div>

	<div id="tabs" data-options="region:'center',split:true" class="easyui-tabs">
			<div title="主页" data-options="iconCls:'icon-tip'">
				<img src="/static/admin/images/indexbg.jpg" width="1320px;" height="600px;" />
			</div>
	</div>
	<!--
	<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">
	
	</div>
	-->
	<div data-options="region:'south',border:false" style="height:35px;background:#D2E0F2;padding:10px;">
		<div class="footer">京ICP备xxxx号  Copyright 2016-2020   版权所有北京爱迪科森教育科技股份有限公司</div>
	</div>
   <div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>