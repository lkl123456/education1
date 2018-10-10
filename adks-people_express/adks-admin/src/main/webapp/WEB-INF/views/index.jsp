<%@page import="com.adks.commons.util.PropertiesFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>中共民航局党校-后台管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" id="easyuiTheme" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/global.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/menuUtil.js"></script>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/menu.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/login.js" ></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.fullcalendar.js?v=0.7"></script>
   	
	
    <script type="text/javascript"> 
    	var menus='';
	    $(function() {	
			//初始化左侧菜单
			var userPermisstions = '1,2,3,4,5,6,7,8,9';
			var web=$("#web").val();
			$.ajax({
				type: "POST",
				url: web+"/userIndex/userMenuList",
				async: false,
				success: function(data){
					menus=$.parseJSON(data);
				}
			});
			$.ajax({
				type: "POST",
				url: web+"/userIndex/userMenu",
				async: false,
				success: function(data){
				  userPermisstions=data;
				}
			});
			InitLeftMenu(userPermisstions);
	    });
	    
	    function InitLeftMenu(userPermisstions) {
	        $(".easyui-accordion").empty();
	        var menulist = '<div class="easyui-accordion" data-options="fit:true,border:false" >';
	        //根据权限重新整理menu数据
	        menus = operationMenu(menus,userPermisstions);
	        $.each(menus, function(i, n) {
	    		if(n.menuname != null && n.menuname != '' && n.menuname != undefined ){
	    			
	    			menulist += '<div title="'+n.menuname+'" style="overflow:auto;"><ul>';
	    			$.each(n.menus, function(j, o) {
	    				if(o.menuname != null && o.menuname != '' && o.menuname != undefined ){
	    					menulist += '<li><div><a ref="'+o.menuid+'" href="#" rel="<%=request.getContextPath() %>' + o.url + '" ><span class="icon icon-lock" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ';
	    				}
	    			})
	    			menulist += '</ul></div>';
	    		}
	    				
	        })
	        
	    	menulist += "</div>";
	    	$("#accordion").append(menulist);
	    	
	    	$('.easyui-accordion li a').click(function(){
	    		var tabTitle = $(this).text();
	    		tabTitle = $.trim(tabTitle); 
	    		var url = $(this).attr("rel");
	    		var menuid = $(this).attr("ref");
	    		var icon = getIcon(menuid,icon);
	    		addTab(tabTitle,url,icon);
	    		$('.easyui-accordion li div').removeClass("selected");
	    		$(this).parent().addClass("selected");
	    	}).hover(function(){
	    		$(this).parent().addClass("hover");
	    	},function(){
	    		$(this).parent().removeClass("hover");
	    	});
	    		
	    	$(".easyui-accordion").accordion();
	    }
	    
		function tohref(tabTitle,url){
	    		addTab(tabTitle,url,"");
	    		$('.easyui-accordion li div').removeClass("selected");
	    		$(this).parent().addClass("selected");
		}
    </script>
	
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">	
	<input type="hidden" id="web" value="${pageContext.request.contextPath}">
	<!-- 顶部开始 -->
	<div data-options="region:'north',border:false" style="overflow:hidden;line-height:30px;height:70px; background:url(${pageContext.request.contextPath}/static/admin/images/logobg.jpg) repeat-x;color: #fff; font-family: Verdana, 微软雅黑,黑体; font-weight:blod;">
		<!-- logo开始 -->
		<div style="padding:0 0 0 5px; width:263px; display:inline; float:left;position: relative;top: 7px;"><img src="${pageContext.request.contextPath}/static/admin/images/logo1.png" width="316" height="52" /></div>
		<!-- logo结束 -->
		<!-- 时间开始 -->
		<div style="padding:0 0 0 5px; display:inline; float:left;position: relative;top: 15px;">
			<div id="showtimes">
				<script language="javascript">show_cur_times();</script>
			</div>	
		</div>
		<!-- 时间结束 -->
		
		<!-- 退出开始 -->
		<span style="float:right; padding-right:20px; padding-top:15px;" class="head">
			<form id ="logout"  action="${pageContext.request.contextPath}/userIndex/loginOut.do" method="post">
				<input type="hidden" id="userId" name="userId" value="${user.userId }">
				<input type="hidden" id="userName" name="userName" value="${user.username }">
				<a href="#" onclick="javascript:updatePw();" id="loginOut" class="easyui-linkbutton" iconCls="icon-lock">密码修改</a>
				<a href="#" onclick="logout();" id="loginOut" class="easyui-linkbutton" iconCls="icon-undo">安全退出</a>
			</form>
		</span>
		<!-- 退出结束-->
		  
	</div>
	<!-- 顶部结束-->
	
	<!-- 左侧菜单开始 -->
	<div id="accordion" data-options="region:'west',split:true,title:'导航菜单'" style="width:260px;">	</div>
	<!-- 左侧菜单结束 -->
	
	<!-- 右侧内容开始 -->
	<div id="tabs" data-options="region:'center',split:true" class="easyui-tabs" >
		<div title="主页"  data-options="iconCls:'icon-house'">
			<div class="easyui-layout" style="width:100%;height:100%;">
				<!-- 中间开始 -->
				<div data-options="region:'center',title:'系统消息'">
						<div class="l_box">
					        <div class="t_item"><span>开通站点</span></div>
					        <div class="f_lists">
					            <ul>
					                <c:forEach var="orgConfig" items="${orgConfigList }">
					                	<li>
						                    <a href="${orgConfig.orgUrl}" target="_blank">
						                    	<c:if test="${orgConfig.orgLogoPath!=null&&orgConfig.orgLogoPath!='' }">
						                    		<img src="${ossResource}${orgConfig.orgLogoPath}" width="200" height="52" border="0"}/>
						                    	</c:if>
						                    	<c:if test="${orgConfig.orgLogoPath==null}">
						                    		<img src="${pageContext.request.contextPath}/static/admin/images/icon13.png" width="200" height="52" border="0"/>
						                    	</c:if>
						                        <p>${orgConfig.orgName}</p>
						                    </a>
					               	 	</li>
					                </c:forEach>
					            </ul>
					        </div>
					        <div class="t_item"><span>已开通移动</span></div>
					        <div class="f_lists">
					            <ul>
					                <li>
				                    	<img src="${pageContext.request.contextPath}/static/admin/images/qrcode.png" width="160" height="160"/>
				                        <p>民航局移动版</p>
					                </li>
					            </ul>
					        </div>
					    </div>
				</div>
				<!-- 中间结束 -->
				
				<!-- 右侧开始 -->
				<div data-options="region:'east',split:true" title="账户信息" style="width:250px;">
					<div id="p" class="easyui-panel" style="width:240px;height:320px;padding:10px;background:#fafafa;">
		                <p align="center">
		                	<c:if test="${user.headPhoto!=null&&user.headPhoto!='' }">
		                		<img src="${ossResource}${user.headPhoto}"  width="100" height="120" />
		                	</c:if>
		                	<c:if test="${user.headPhoto==null||user.headPhoto=='' }">
		                		<img src="${pageContext.request.contextPath}/static/admin/images/userImg.jpg"  width="100" height="120" />
		                	</c:if>
		                </p>
		                <p align="center">${user.userRealName}</p>
					    <p>登录名称：${user.username}</p>
					    <p>用户角色：${user.roleName}</p>
					    <p>所属机构：${user.orgName}</p>
					</div>
					
					<div class="easyui-calendar" style="width:240px;height:230px;"></div>
				</div>
				<!-- 右侧结束 -->
			</div>
		</div>
	</div>
	
	<!-- 右侧内容结束 -->

	<div data-options="region:'south',border:false" style="height:35px;background:#D2E0F2;padding:10px;">
		<div class="footer">主办单位：中国民用航空局 承办单位：中共民航党校 技术支持：北京爱迪科森教育科技股份有限公司 运营维护：北京爱迪科森教育科技股份有限公司</div>
	</div>
   <!--  <div id="mm" class="easyui-menu" style="width:150px;">
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
	</div> -->
</body>
</html>