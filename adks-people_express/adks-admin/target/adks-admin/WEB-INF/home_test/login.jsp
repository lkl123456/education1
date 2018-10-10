<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/login/login.js"></script>
	<script language="JavaScript">
		var context = "${pageContext.request.contextPath}";
	</script>
</head>
<body>	
<!--
	<div style="margin:20px 0;">  
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">Open</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">Close</a>  
    </div>
-->
    <div id="w" class="easyui-window" title="请您登录" data-options="modal:true,iconCls:'Lockgo',maximizable:false,collapsible:false,shadow:false,closable:false,minimizable:false" style="width:400px;padding:20px 70px 20px 70px;">  
           
        <div style="margin-bottom:10px">  
            <input class="easyui-textbox" id="username" style="width:100%;height:30px;padding:12px" data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38,required:true">  
        </div>  
        <div style="margin-bottom:20px">  
            <input class="easyui-textbox" id="password" type="password" style="width:100%;height:30px;padding:12px" data-options="prompt:'登录密码',iconCls:'icon-lock',iconWidth:38,required:true">  
        </div>  
		<!--
        <div style="margin-bottom:20px">  
            <input class="easyui-textbox" type="text" id="logyzm" style="width:50%;height:30px;padding:12px" data-options="prompt:'验证码',required:true"> <a href="javascript:;" class="showcode" onclick="changeVeryfy()"><img style=" margin:0 0 0 3px ; vertical-align:middle; height:26px;" src="#"></a>  
        </div>
		<div style="margin-bottom:20px">  
            <input type="checkbox" checked="checked" id="logrem">  
            <span>Remember me</span>  
        </div>
		-->
		<div style="margin-bottom:20px;display:hidden;">  
            <span id="message"></span>  
        </div>
        <div>  
            <a href="javascript:;" onclick="dologin()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:100%;">  
                <span style="font-size:14px;">登录</span>  
            </a>  
        </div>  
           
    </div> 
	
</body>
</html>