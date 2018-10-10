<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/jquery.select.js"></script>
<style type="text/css">
.search {
	border: 1px solid #b00007;
	height: 28px;
	width: 278px;
	position: absolute;
	right: 0;
	bottom: 50px;
	z-index: 99;
	background: #fff;
}

.search select {
	display: none;
}

.search .select_box {
	font-size: 12px;
	color: #999999;
	width: 70px;
	line-height: 28px;
	float: left;
	position: relative;
}

.search .select_showbox {
	height: 28px;
	background: url(../../static/images/search_ico.png) no-repeat 50px
		center;
	text-indent: 1.5em;
}

.search .select_showbox.active {
	height: 28px;
}

.search .select_option {
	border: 1px solid #b00007;
	border-top: none;
	display: none;
	left: -1px;
	top: 28px;
	position: absolute;
	z-index: 99;
	background: #fff;
}

.search .select_option li {
	text-indent: 1.5em;
	width: 60px;
	cursor: pointer;
}

.search .select_option li.selected {
	background-color: #F3F3F3;
	color: #999;
}

.search .select_option li.hover {
	background: #BEBEBE;
	color: #fff;
}

.search input.inp_srh, .search input.btn_srh {
	border: none;
	background: none;
	height: 28px;
	line-height: 28px;
	float: left
}

.search input.inp_srh {
	outline: none;
	width: 148px;
}

.search input.btn_srh {
	background: #b00007;
	color: #FFF;
	font-family: "微软雅黑";
	font-size: 15px;
	width: 60px;
	cursor: pointer;
}
</style>
<div class="header_top">
	<input type="hidden" value="<%=basePath%>" id="path" />
	<c:if test="${empty orgConfig}">
		您好，欢迎来到中共民航局党校 民航党员在线课堂！
	</c:if>
	<c:if test="${!empty orgConfig }">
		您好，欢迎来到${orgConfig.orgName } 党员在线课堂！
	</c:if>

	<c:if test="${empty sessionScope.user}">
		<span><a
			href="${pageContext.request.contextPath}/user/toLogin/${orgId }.shtml">登录</a>
			| <a
			href="${pageContext.request.contextPath}/index/contactUs/${orgId }.shtml">帮助中心</a>
		</span>
	</c:if>
	<c:if test="${!empty sessionScope.user}">
		<!-- <span>${sessionScope.user.userRealName}你好！<a href="<%=basePath%>user/loginOut/${orgId }.shtml" onclick="loginOut()">退出</a> | -->
		<span>${sessionScope.user.userRealName}你好！<a
			href="<%=basePath%>user/loginOut/${orgId }.shtml">退出</a> | <a
			href="${pageContext.request.contextPath}/index/contactUs/${orgId }.shtml">帮助中心</a>
		</span>
	</c:if>
</div>
<c:choose>
	<c:when test="${empty orgConfig}">
	</c:when>
	<c:otherwise>
		<div class="heder_min_mhj">
			<c:if test="${orgConfig!=null && orgConfig.orgBanner!=null}">
				<img src="<%=imgServerPath %>${orgConfig.orgBanner }" />
			</c:if>
			<c:if test="${orgConfig==null || orgConfig.orgBanner==null}">
				<img
					src="${pageContext.request.contextPath}/static/images/tou_mhj.png" />
			</c:if>
			<div class="search radius6">
				<input name='ecmsfrom' type='hidden' value='9'> <input
					type="hidden" name="show" value="title,newstext"> <select
					name="classid" id="choose">
					<option value="0">全部</option>
					<option value="1">课程</option>
					<option value="2">新闻</option>
					<option value="3">专题</option>
					<option value="4">讲师</option>
				</select> <input class="inp_srh" name="keyboard" id="searchKey"
					placeholder="请输入关键字"> <input class="btn_srh" type="submit"
					name="submit" value="搜索" onclick="indexSearch('undefined')">
			</div>
		</div>
	</c:otherwise>
</c:choose>
<div class="nav">
	<ul>
		<li id="index" class="nav_li_n"><a
			href="<%=basePath%>index/index/${orgId }.shtml">首页</a></li>
		<li id="newsIndex"><a
			href="<%=basePath%>cms/newsIndex/30/${orgId }.shtml">通知公告</a></li>
		<li id="courseIndex"><a
			href="<%=basePath%>course/courseIndex/${orgId }.shtml">课程中心</a></li>
		<li id="registGradeList"><a
			href="<%=basePath%>registGrade/toRegisterGradeList/${orgId }.shtml">专题培训</a>
		</li>
		<li id="centerIndex"><a
			href="<%=basePath%>index/centerIndex/${orgId }.shtml">学习中心</a></li>
		<li id="contactUs"><a
			href="<%=basePath%>index/contactUs/${orgId }.shtml">学习支持</a></li>
		<li id="subjectIndex"><a
			href="<%=basePath%>index/download/${orgId }.shtml">移动端</a></li>
	</ul>
</div>
<script type="text/javascript">
	function updateCheck() {
		$("#index").addClass("nav_li_n");
	};
</script>
