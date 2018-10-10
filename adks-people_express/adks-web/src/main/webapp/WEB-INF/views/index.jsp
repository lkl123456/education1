<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@page import="java.util.Random"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="../static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中共民航局党校</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/xcConfirm.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/index.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/bootstrap-theme.min.css">
<style>
#guidImg1 a {
	color: #225b88;
	text-decoration: underline;
	display: inline-block;
	height: 28px;
	line-height: 28px;
	margin: 2px 0;
}

#guidImg1 a img {
	position: relative;
	top: 4px;
}

#guidImg1 a:hover {
	color: #225b88;
	text-decoration: none;
}
</style>
</head>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/index.js"></script>
<script src="${pageContext.request.contextPath}/static/js/des.js"></script>
<script src="${pageContext.request.contextPath}/static/js/md5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/login.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script type="text/javascript">    	
		var contextPath = '${pageContext.request.contextPath}';
	</script>
<script type="text/javascript">
		//图片滚动 调用方法 imgscroll({speed: 30,amount: 1,dir: "up"});
		$.fn.imgscroll = function(o){
			var defaults = {
				speed: 40,
				amount: 0,
				width: 1,
				dir: "left"
			};
			o = $.extend(defaults, o);
			
			return this.each(function(){
				var _li = $("li", this);
				_li.parent().parent().css({overflow: "hidden", position: "relative"}); //div
				_li.parent().css({margin: "0", padding: "0", overflow: "hidden", position: "relative", "list-style": "none"}); //ul
				_li.css({position: "relative", overflow: "hidden"}); //li
				if(o.dir == "left") _li.css({float: "left"});
				
				//初始大小
				var _li_size = 0;
				for(var i=0; i<_li.size(); i++)
					_li_size += o.dir == "left" ? _li.eq(i).outerWidth(true) : _li.eq(i).outerHeight(true);
				
				//循环所需要的元素
				if(o.dir == "left") _li.parent().css({width: (_li_size*3)+"px"});
				_li.parent().empty().append(_li.clone()).append(_li.clone()).append(_li.clone());
				_li = $("li", this);
		
				//滚动
				var _li_scroll = 0;
				function gotoScore(){
					_li_scroll += o.width;
					if(_li_scroll > _li_size)
					{
						_li_scroll = 0;
						_li.parent().css(o.dir == "left" ? { left : -_li_scroll } : { top : -_li_scroll });
						_li_scroll += o.width;
					}
						_li.parent().animate(o.dir == "left" ? { left : -_li_scroll } : { top : -_li_scroll }, o.amount);
				}
				
				//开始
				var move = setInterval(function(){ gotoScore(); }, o.speed);
				_li.parent().hover(function(){
					clearInterval(move);
				},function(){
					clearInterval(move);
					move = setInterval(function(){ gotoScore(); }, o.speed);
				});
			});
		};
	</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/foucs.js"></script>
<body>
	<!-- Button trigger modal -->
	<button id="loginClick" style="display: none;" type="button"
		class="btn btn-primary btn-lg" data-toggle="modal"
		data-target="#myModal">Launch demo modal</button>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">在线课堂学习流程-温馨提示</h4>
				</div>
				<div class="modal-body">
					<div id="guidImg1">
						1.为了保证平台的课程学习过程中计时的准确性，请尽量使用主流浏览器进行课程播放学习，推荐谷歌的chrome浏览器，火狐浏览器，360浏览器极速模式，搜狗浏览器极速模式；<br />
						<br /> 2.浏览器下载链接：<br /> <a
							href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html"
							target="_blank"><img width="20" height="20"
							src="${pageContext.request.contextPath}/static/images/chrome.png">Chrome浏览器下载链接</a><br />
						<a href="http://www.firefox.com.cn" target="_blank"><img
							width="20" height="20"
							src="${pageContext.request.contextPath}/static/images/firefox.png">火狐浏览器下载链接
						</a><br /> <a href="http://se.360.cn/" target="_blank"><img
							width="20" height="20"
							src="${pageContext.request.contextPath}/static/images/360.png">360浏览器下载链接</a><br />
						<a href="http://www.iesogou.com/" target="_blank"><img
							width="20" height="20"
							src="${pageContext.request.contextPath}/static/images/sougou.png">搜狗浏览器下载链接</a><br />
						<br /> 3.平台学习流程请点击下一步进行查看；
					</div>
					<div id="guidImg2" style="display: none;">
						<c:choose>
							<c:when test="${sessionScope.logoType == 'kgj'}">
								<img width="750" height="350"
									src="${pageContext.request.contextPath}/static/images/w_part_1_kgj.jpg">
							</c:when>
							<c:when test="${sessionScope.logoType == 'xhj'}">
								<img width="750" height="350"
									src="${pageContext.request.contextPath}/static/images/w_part_1_xhj.jpg">
							</c:when>
							<c:otherwise>
								<img width="750" height="350"
									src="${pageContext.request.contextPath}/static/images/w_part_1_mhj.jpg">
							</c:otherwise>
						</c:choose>
					</div>
					<div id="guidImg3" style="display: none;">
						<img width="750" height="350"
							src="${pageContext.request.contextPath}/static/images/w_part_2.jpg">
					</div>
					<div id="guidImg4" style="display: none;">
						<img width="750" height="350"
							src="${pageContext.request.contextPath}/static/images/w_part_3.jpg">
					</div>
					<div id="guidImg5" style="display: none;">
						<img width="750" height="350"
							src="${pageContext.request.contextPath}/static/images/w_part_4.jpg">
					</div>
					<div id="guidImg6" style="display: none;">
						<img width="750" height="350"
							src="${pageContext.request.contextPath}/static/images/w_part_5.jpg">
					</div>
					<div id="guidImg7" style="display: none;">
						<img width="750" height="350"
							src="${pageContext.request.contextPath}/static/images/w_part_6.jpg">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="closeGuid" class="btn btn-default"
						data-dismiss="modal">关闭</button>
					<button type="button" onclick="nextGuid();" class="btn btn-primary">下一步</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="orgId" value="${orgId }" />
	<!-- Button trigger modal -->
	<!--引入头部文件  -->
	<div class="header" id="header">
		<script type="text/javascript">
				var orgId=$("#orgId").val();
			     $(document).ready(function(){
				   	 $.ajax({
						async:false,
						url:'<%=path%>/index/top.do?orgId='+orgId,
							type:"post", 
							success:function(data){
								$("#header").html(data);
							}
						});
				   }); 
		   </script>
	</div>
	<div id="mainIndex">
		<div class="bingxin">
			<div class="bingx_l">
				<div class="jiaodian">
					<!-- 代码 开始 -->
					<div class="mod_focus_show" id="divimgplay">
						<ul class="mod_focus_pic" id="divimginfog_imgPlayer">
							<c:choose>
								<c:when test="${empty tpxwList }">
								</c:when>
								<c:otherwise>
									<c:forEach items="${tpxwList}" var="tpxw" varStatus="tpxwIndex">
										<li><c:if test="${tpxw.newsType==1 }">
												<a
													href="<%=basePath%>cms/newsIndex/${tpxw.newsId }_${tpxw.newsSortId }_${tpxw.newsSortType }/${orgId }.shtml"><img
													src="<%=imgServerPath%>${tpxw.newsFocusPic}" alt=""
													width="283" height="228" title="${tpxw.newsTitle}" /></a>
											</c:if> <c:if test="${tpxw.newsType==2 }">
												<a href="javascript: toLinkUrl('${tpxw.linkUrl }')"><img
													src="<%=imgServerPath%>${tpxw.newsFocusPic}" alt=""
													width="283" height="228" title="${tpxw.newsTitle}" /></a>
											</c:if> <span>${tpxw.newsTitle}</span></li>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</ul>
						<ul class="mod_focus_title" style="display: none"
							id="ptitleg_imgPlayer">
							<li class="current">
								<h3>
									<a href="http://www.lanrentuku.com/"></a>
								</h3>
								<p></p>
							</li>
						</ul>
						<div class="focus_switch">
							<a href="javascript:;" class="icon_prev"></a><a
								href="javascript:;" class="icon_next"></a>
						</div>
						<ul class="mod_focus_list" id="divpageinfog_imgPlayer">
							<c:choose>
								<c:when test="${empty tpxwList }">
								</c:when>
								<c:otherwise>
									<c:forEach items="${tpxwList}" var="tpxw" varStatus="tpxwIndex">
										<c:if test="${tpxwIndex.index==0}">
											<li class="current"><c:if test="${tpxw.newsType==1 }">
													<a
														href="<%=basePath%>cms/newsIndex/${tpxw.newsId }_${tpxw.newsSortId }_${tpxw.newsSortType }/${orgId }.shtml"><img
														src="<%=imgServerPath%>${tpxw.newsFocusPic}"
														alt="${tpxw.newsTitle}" width="66" height="50" /><span
														class="border"></span> </a>
												</c:if> <c:if test="${tpxw.newsType==2 }">
													<a href="javascript: toLinkUrl('${tpxw.linkUrl }')"><img
														src="<%=imgServerPath%>${tpxw.newsFocusPic}"
														alt="${tpxw.newsTitle}" width="66" height="50" /><span
														class="border"></span> </a>
												</c:if></li>
										</c:if>
										<c:if test="${tpxwIndex.index!=0 && fs:length(tpxwList)>1}">
											<li><c:if test="${tpxw.newsType==1 }">
													<a
														href="<%=basePath%>cms/newsIndex/${tpxw.newsId }_${tpxw.newsSortId }_${tpxw.newsSortType }/${orgId }.shtml"><img
														src="<%=imgServerPath%>${tpxw.newsFocusPic}"
														alt="${tpxw.newsTitle}" width="66" height="50" /><span
														class="border"></span> </a>
												</c:if> <c:if test="${tpxw.newsType==2 }">
													<a href="javascript: toLinkUrl('${tpxw.linkUrl }')"><img
														src="<%=imgServerPath%>${tpxw.newsFocusPic}"
														alt="${tpxw.newsTitle}" width="66" height="50" /><span
														class="border"></span> </a>
												</c:if></li>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
					<script type="text/javascript">
				        foucsbox(2500);
				    </script>
					<!-- 代码 结束 -->
				</div>

				<dl class="bjcl">
					<dt>
						<ol>
							<li class="bjcl_li">培训动态</li>
							<li>新闻资讯</li>
						</ol>
						<a href="<%=basePath%>cms/newsIndex/31/${orgId }.shtml">更多</a> <a
							style="display: none;"
							href="<%=basePath%>cms/newsIndex/32/${orgId }.shtml">更多</a>
					</dt>
					<dd>
						<c:choose>
							<c:when test="${empty pxdtList }">
							</c:when>
							<c:otherwise>
								<c:forEach items="${pxdtList}" var="pxdt" varStatus="pxdtIndex"
									begin="0" end="0">
									<div>
										<h2 title="${pxdt.newsTitle }">
											<c:if test="${fn:length(pxdt.newsTitle)>20 }">
												${fn:substring(pxdt.newsTitle, 0, 20)}...
											</c:if>
											<c:if test="${fn:length(pxdt.newsTitle)<=20 }">
												${pxdt.newsTitle}
											</c:if>
										</h2>
										<p>
											${fn:substring(pxdtContent, 0, 55)}
											<c:if test="${pxdt.newsType==1 }">
												<a
													href="<%=basePath%>cms/newsIndex/${pxdt.newsId }_${pxdt.newsSortId }_${pxdt.newsSortType }/${orgId }.shtml"
													title="${pxdt.newsTitle}">[详细]</a>
											</c:if>
											<c:if test="${pxdt.newsType==2 }">
												<a href="javascript: toLinkUrl('${pxdt.linkUrl }')"
													title="${pxdt.newsTitle}">[详细]</a>
											</c:if>
										</p>
									</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty pxdtList }">
							</c:when>
							<c:otherwise>
								<c:if test="${fn:length(pxdtList) > 1}">
									<ul>
										<c:forEach items="${pxdtList}" var="pxdt"
											varStatus="pxdtIndex" begin="1">
											<li><c:if test="${pxdt.newsType==1 }">
													<a
														href="<%=basePath%>cms/newsIndex/${pxdt.newsId }_${pxdt.newsSortId }_${pxdt.newsSortType }/${orgId }.shtml"
														title="${pxdt.newsTitle}">${pxdt.newsTitle}</a>
													<span><fmt:formatDate value="${pxdt.createTime }"
															pattern="MM-dd" /> </span>
												</c:if> <c:if test="${pxdt.newsType==2 }">
													<a href="javascript: toLinkUrl('${pxdt.linkUrl }')"
														title="${pxdt.newsTitle}">${pxdt.newsTitle}</a>
													<span><fmt:formatDate value="${pxdt.createTime }"
															pattern="MM-dd" /> </span>
												</c:if></li>
										</c:forEach>
									</ul>
								</c:if>
							</c:otherwise>
						</c:choose>
					</dd>
					<dd style="display: none;">
						<c:choose>
							<c:when test="${empty xwzxList }">
							</c:when>
							<c:otherwise>
								<c:forEach items="${xwzxList}" var="xwzx" varStatus="xwzxIndex"
									begin="0" end="0">
									<div>
										<h2 title="${xwzx.newsTitle }">
											<c:if test="${fn:length(xwzx.newsTitle)>20 }">
												${fn:substring(xwzx.newsTitle, 0, 20)}...
											</c:if>
											<c:if test="${fn:length(xwzx.newsTitle)<=20 }">
												${xwzx.newsTitle}
											</c:if>
										</h2>
										<p>
											${fn:substring(xwzxContent, 0, 55)}
											<c:if test="${xwzx.newsType==1 }">
												<a
													href="<%=basePath%>cms/newsIndex/${xwzx.newsId }_${xwzx.newsSortId }_${xwzx.newsSortType }/${orgId }.shtml"
													title="${xwzx.newsTitle}">[详细]</a>
											</c:if>
											<c:if test="${xwzx.newsType==2 }">
												<a href="javascript: toLinkUrl('${xwzx.linkUrl }')"
													title="${xwzx.newsTitle}">[详细]</a>
											</c:if>
										</p>
									</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty xwzxList }">
							</c:when>
							<c:otherwise>
								<c:if test="${fn:length(xwzxList) > 1}">
									<ul>
										<c:forEach items="${xwzxList}" var="xwzx"
											varStatus="xwzxIndex" begin="1">
											<li><c:if test="${xwzx.newsType==1 }">
													<a
														href="<%=basePath%>cms/newsIndex/${xwzx.newsId }_${xwzx.newsSortId }_${xwzx.newsSortType }/${orgId }.shtml"
														title="${xwzx.newsTitle}">${xwzx.newsTitle}</a>
													<span><fmt:formatDate value="${xwzx.createTime }"
															pattern="MM-dd" /> </span>
												</c:if> <c:if test="${xwzx.newsType==2 }">
													<a href="javascript: toLinkUrl('${xwzx.linkUrl }')"
														title="${xwzx.newsTitle}">${xwzx.newsTitle}</a>
													<span><fmt:formatDate value="${xwzx.createTime }"
															pattern="MM-dd" /> </span>
												</c:if></li>
										</c:forEach>
									</ul>
								</c:if>
							</c:otherwise>
						</c:choose>
					</dd>
				</dl>
			</div>
			<div class="bingx_r">
				<dl class="bin_top">
					<form id="loginForm" action="<%=contextPath%>/user/toUserLogin.do"
						method="post" onsubmit="return onlogin();">
						<dt>学员登录</dt>
						<dd style="position: relative;">
							<c:if test="${empty sessionScope.user}">
								<%
									//生成随机类
										Random random = new Random();
										String sRand = "";
										for (int i = 0; i < 4; i++) {
											String rand = String.valueOf(random.nextInt(10));
											sRand += rand;
										}
										//sRand = "2017";
								%>
								<ul>
									<li><span>用户名：</span><input name="userNameTemp"
										id="userNameTemp" type="text" placeholder="用户名或绑定的手机号码" /></li>
									<li><span>密 码：</span><input name="passWordTemp"
										id="passWordTemp" type="password" placeholder="密码" /></li>
									<li class="biany_1"><span>验证码：</span><input name="randNum"
										id="randNum" maxlength="4" type="text" placeholder="验证码" /><img
										id="checkcode"
										src="${pageContext.request.contextPath}/common/image.jsp?sRand=<%=sRand%>"
										onclick="javascript: getYanZhengMa('${pageContext.request.contextPath}');"
										width="78" height="26" /></li>
									<input type="hidden" id="sRandNum" name="sRandNum"
										value="<%=sRand%>" />
									<input type="hidden" name="userName" id="userName" />
									<input type="hidden" name="passWord" id="passWord" />
									<input type="hidden" name="loginFrom" value="index" />
									<li class="biany_2"><input name="" type="submit" value="" />
										<a href="#">忘记密码？</a></li>
								</ul>
							</c:if>
							<c:if test="${!empty sessionScope.user}">
								<div
									style="padding: 10px 10px; font-size: 12px; line-height: 24px;">
									<span
										style="display: block; position: relative; font-size: 14px; text-align: center; height: 45px; line-height: 40px; border-bottom: #d5d5d5 dotted 1px;">
										<strong
										style="font-weight: normal; text-align: right; width: 115px; float: left;">你好！<b
											style="color: #b00007">${sessionScope.user.userRealName}</b></strong>
										<div
											style="background:url(${pageContext.request.contextPath}/static/images/shi_2.gif) repeat-x; position: absolute; left: 50%; margin-left:5px;  top:10px; height: 23px; line-height: 23px; ">
											<strong
												style="font-weight: normal; background:url(${pageContext.request.contextPath}/static/images/shi_1.gif) no-repeat; height:23px; float:left; padding: 0 5px 0 9px;"><c:if
													test="${xs_totle_str != null}">
													<b style="font-size: 18px; color: rgb(233, 78, 36);">${xs_totle_str}</b>
													<span
														style="color: #666; font-size: 12px; line-height: 22px;">学时</span>
												</c:if></strong>
										</div>
									</span>
									<p
										style="line-height: 52px; text-align: center; color: #666; font-size: 14px">
										<c:choose>
											<c:when test="${sessionScope.logoType == 'kgj'}">
									欢迎来到空管党员在线课堂！
									</c:when>
											<c:when test="${sessionScope.logoType == 'xhj'}">
									欢迎来到疆航党员在线课堂！
									</c:when>
											<c:otherwise>
									欢迎来到民航党员在线课堂！
									</c:otherwise>
										</c:choose>
									</p>
									<div
										style="width: 261px; height: 48px; position: absolute; background: rgb(238, 247, 254); left: 0; bottom: 0">
										<a
											style="width: 87px; height: 27px; position: absolute; top: 10px; left: 40px; background: url(../../static/images/wodek1.gif) no-repeat;"
											href="<%=basePath%>index/centerIndex/${orgId }.shtml"></a> <a
											style="width: 87px; height: 27px; position: absolute; top: 10px; right: 40px; background: url(../../static/images/wodek2.gif) no-repeat;"
											href="<%=basePath%>user/loginOut/${orgId }.shtml"
											onclick="loginOut()"></a>
									</div>
								</div>
							</c:if>
						</dd>
					</form>
				</dl>
				<!-- -->
				<dl class="bin_bot">
					<dt>
						通知公告 <a href="<%=basePath%>cms/newsIndex/30/${orgId }.shtml">更多</a>
					</dt>
					<dd>
						<ul>
							<c:choose>
								<c:when test="${empty tzggList }">
								</c:when>
								<c:otherwise>
									<ul>
										<c:forEach items="${tzggList}" var="tzgg"
											varStatus="tzggIndex">
											<li><c:if test="${tzgg.newsType==1 }">
													<a
														href="<%=basePath%>cms/newsIndex/${tzgg.newsId}_${tzgg.newsSortId}_${tzgg.newsSortType}/${orgId }.shtml"
														title="${tzgg.newsTitle}">${tzgg.newsTitle}</a>
													<span><fmt:formatDate value="${tzgg.createTime }"
															pattern="MM-dd" /> </span>
												</c:if> <c:if test="${tzgg.newsType==2 }">
													<a href="javascript: toLinkUrl('${tzgg.linkUrl }')"
														title="${tzgg.newsTitle}">${tzgg.newsTitle}</a>
													<span><fmt:formatDate value="${tzgg.createTime }"
															pattern="MM-dd" /> </span>
												</c:if></li>
										</c:forEach>
									</ul>
								</c:otherwise>
							</c:choose>
						</ul>
					</dd>
					<script type="text/javascript">
	$(document).ready(function(){
		$(".scrollop").imgscroll({
			speed: 40,    //图片滚动速度
			amount: 0,    //图片滚动过渡时间
			width: 1,     //图片滚动步数
			dir: "up"   // "left" 或 "up" 向左或向上滚动
		});
		
	});
</script>
				</dl>
			</div>
		</div>
		<!--  广告位 上部 begin  -->
		<div class="guangao" id="advert_head">
			<c:if test="${empty advertLists1}">
				<a href="#"> <img src="<%=basePath%>static/images/head.jpg"
					width="1000" height="90" />
				</a>
			</c:if>
			<c:if test="${not empty advertLists1}">
				<c:forEach items="${advertLists1}" var="advert" varStatus="status">
					<a href="${advert.adHref }" target="_blank"> <img
						src="<%=imgServerPath %>${advert.adImgPath}" width="1000"
						height="90" />
					</a>
				</c:forEach>
			</c:if>
		</div>
		<!--本周新上,主编推荐,大家正在看-->
		<input type="hidden" id="userId" value="${user.userId}" />
		<div id="indexNewCourseNcommandCourse">
			<p align="center">
				<img src="<%=basePath%>/static/images/loading.gif" />
			</p>
			<script type="text/javascript">
				var orgId=$("#orgId").val();
			    $(document).ready(function(){
				   	 $.ajax({
						async:true,
						url:'<%=path%>/index/indexNewCourseNcommandCourse.do?orgId='+orgId,
							type:"post", 
							success:function(data){
								$("#indexNewCourseNcommandCourse").html(data);
							},
							error:function(XMLResponse){
								//alert(XMLResponse);
							}
						});
				   });
		</script>
		</div>
		<div class="guangao" id="advert_head1">
			<c:if test="${empty advertLists2}">
				<a href="#"> <img src="<%=basePath%>static/images/head.jpg"
					width="1000" height="90" />
				</a>
			</c:if>
			<c:if test="${not empty advertLists2}">
				<c:forEach items="${advertLists2}" var="advert" varStatus="status">
					<a href="${advert.adHref }" target="_blank"> <img
						src="<%=imgServerPath %>${advert.adImgPath}" width="1000"
						height="90" />
					</a>
				</c:forEach>
			</c:if>
		</div>
		<!-- 排行信息 -->
		<div id="indexRankInfo">
			<p align="center">
				<img src="<%=basePath%>/static/images/loading.gif" />
			</p>
			<script type="text/javascript">
				var orgId=$("#orgId").val();
			     $(document).ready(function(){
				   	 $.ajax({
						async:true,
						url:'<%=path%>/index/indexRankInfo.do?orgId='+orgId,
							type:"post", 
							success:function(data){
								$("#indexRankInfo").html(data);
							},
							error:function(XMLResponse){
								//alert(XMLResponse);
							}
						});
				   });
		</script>
		</div>
	</div>

	<div class="footer" id="footer">
		<script type="text/javascript">
			var orgId=$("#orgId").val();
		     $(document).ready(function(){
			   	 $.ajax({
					async:false,
					url:'<%=path%>/index/footer.do?orgId=' + orgId,
					type : "post",
					success : function(data) {
						$("#footer").html(data);
					}
				});
			});
		</script>
	</div>
	<script>
		$(function() {
			var isLogin = '${isLogin}';
			if (isLogin != null && isLogin != '' && isLogin != 'null') {
				$("#loginClick").click();
			}
		});
	</script>
	<!-- 飘窗  Start-->
	<input type="hidden" id="imgServerPath" value="<%=imgServerPath%>" />
	<input type="hidden" id="isShow" value="" />
	<script type="text/javascript">
		var orgId=$("#orgId").val();
		var adUrl;
		var adImg;
		jcode='';
		$.ajax({
			async:false,
			type:"post",
			url:'<%=path%>/index/indexFloat.do?orgId='+orgId,
			dataType: "text",
			success:function(data){
				if(data.substring(0,data.indexOf('$'))!=null&&data.substring(0,data.indexOf('$'))!=''){
					adImg = $('#imgServerPath').val() + data.substring(0,data.indexOf('$'));
				}
				adUrl = data.substring(data.indexOf('$') + 1, data.length);
			}
		});
		if(adUrl!=''){
			$("#isShow").val("1");
			jcode="<div id='img' style='Z-INDEX: 99999999; LEFT: 2px; WIDTH: 59px; POSITION: absolute; TOP: 43px; HEIGHT: 61px;visibility: visible;'><a href='" + adUrl + "' target='_blank'><img src='" +  adImg + "'  width='180px' height='120px'/></a><a href='javascript:closeimg();'><span style='position:relative; bottom:130px; font-family:微软雅黑; left:170px; color:#000; cursor:pointer;display:block;background:#fff;width:20px;height:20px;text-align:center;line-height:20px;border-radius:30px;font-size:12px;' >X</span></a></div>";
			
		}else{
			$("#isShow").val("2");
		}
	</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/float2.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				var isShow=$("#isShow").val();
				if(isShow == 1 || isShow =='1'){
					start();
				}
			});
		</script>
		<!-- 飘窗  End-->
</body>
</html>
<script>
	//alert(logoType);
	var clickNum = 1;
	function nextGuid() {
		clickNum = clickNum + 1;
		if (clickNum <= 7) {
			$("#guidImg" + clickNum).show();
			$("#guidImg" + (clickNum - 1)).hide();

		} else if (clickNum > 7) {
			$("#closeGuid").click();
		}
	}
</script>
