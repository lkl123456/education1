<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>民航系统后台管理平台</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" id="easyuiTheme" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/login.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/login/login.js"></script>
	<script language="JavaScript">
		var contextpath = "${pageContext.request.contextPath}";
	</script>
</head>
<body>	
    
<div class="all">
	<!-- logo 开始 -->
	<div class="logo">
		<a href="#">
			<img src="${pageContext.request.contextPath}/static/admin/images/logo.png" width="316" height="56" />
		</a>
	</div>
	<!-- logo 结束 -->
	
	<!-- login 开始 -->
    <div class="login">
    	<div style="margin-top:120px;">
    		<span>用户名：</span>
    		<p class="p1">
    			<input class="easyui-textbox" id="username" style="width:100%;height:30px;padding:12px" data-options="prompt:'请输入用户名',iconCls:'icon-man',iconWidth:38,required:true"  missingMessage="用户名不能为空">
    		</p>
    	</div>
        <b class="c"></b>
        <div>
        	<span>密<e>　</e>码：</span>
        	<p class="p2">
        		<input class="easyui-textbox" id="userPassword" type="password" style="width:100%;height:30px;padding:12px" data-options="prompt:'请输入密码',iconCls:'icon-lock',iconWidth:38,required:true" missingMessage="密码不能为空">
        	</p>
        </div>
        <div align="center">
        	<input type="button" class="but"  onclick="dologin()" id="butt" value=""/>
        </div>
       	<div class="msg"> 
            <p id="message"></p>  
       	</div>
         
    </div>
    <!-- login 结束 -->
    
    <!-- 底部 开始 -->
    <div class="bibu">
    	主办单位：中国民用航空局 承办单位：中共民航党校 技术支持：北京爱迪科森教育科技股份有限公司<br />
		运营维护：北京爱迪科森教育科技股份有限公司
	</div>
	<!-- 底部 结束 -->
</div>
	
</body>
</html>