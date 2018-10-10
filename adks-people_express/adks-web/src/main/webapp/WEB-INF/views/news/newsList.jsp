<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="../../static/common/common.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/static/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/des.js"></script>
<script type="text/javascript">
$(function(){
$(".subNav").click(function(){
			$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd")
			$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt")
			
			// 修改数字控制速度， slideUp(500)控制卷起速度
			$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
	})
	
})
</script>
<div class="tux">
</div>
<div class="news">
	<div class="subNavBox">
		<c:choose>
			<c:when test="${empty sessionScope.newsTypeList }">
			</c:when>
			<c:otherwise>
				<c:forEach items="${newsTypeList}" var="newsType" varStatus="newsTypeIndex">
					<c:if	test="${newsType.newsSortId==newsSortId }">
						<a href="#" class="current" onclick="getNewsList(${newsType.newsSortId },${orgId })" >
							<div class="subNav currentDd currentDt" id="ne_li${newsType.newsSortId}"><em></em>${newsType.newsSortName}</div>
						</a>
					</c:if>
					<c:if	test="${newsType.newsSortId!=newsSortId }">
						<a href="#" onclick="getNewsList(${newsType.newsSortId },${orgId })">
							<div class="subNav" id="ne_li${newsType.newsSortId}"><em></em>${newsType.newsSortName}</div>
						</a>
					</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	<dl class="news_r">
		<c:choose>
			<c:when test="${empty sessionScope.newsTypeList }">
			</c:when>
			<c:otherwise>
				<dt>
					<b id="bigTitle"></b>
					<span>当前位置：<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a> > <a href="#">通知公告</a> > <a id="smallTitle">新闻资讯</a> </span>
				</dt>
				<c:choose>
					<c:when test="${page == null || page.rows== null || page.total == 0  }">
					</c:when>
					<c:otherwise>
						<form id="formSearch" name="formSearch" method="post" action="<%=contextPath%>/cms/newsList.do?newsSortId=${page.rows[0].newsSortId}&orgId=${orgId}">
							<input type="hidden" id="ajaxSubmit" value="mainIndex" />
							<c:forEach items="${page.rows}" var="map">
								<dd class="diguo">
									<c:if test="${map.newsType==1 }">
										<%-- <a href="javascript:" onclick="getNewsDetail(${map.newsId },${map.newsSortId },${orgId },${newsSortType })">${map.newsTitle }</a> --%>
										<a href="javascript:" onclick="getNewsDetail('${map.newsHtmlAdress}',${map.newsId },${map.newsSortId },${orgId })">${map.newsTitle }</a>
									</c:if>
									<c:if test="${map.newsType==2 }">
										<a href="javascript: toToUrl('${map.newsLink }')">${map.newsTitle }</a>
									</c:if>
									<span><fmt:formatDate value="${map.createTime }" pattern="yyyy-MM-dd" />
									</span>
								</dd>
							</c:forEach>

							<com:pageCom />
					</c:otherwise>
				</c:choose>
				</form>
			</c:otherwise>
		</c:choose>
	</dl>
</div>
<input type="hidden" id="newsSortId" value="${newsSortId }">
<script type="text/javascript">
		$(document).ready(function(){ 
				var newsSortId = $("#newsSortId").val();
				$("#ne_li"+newsSortId).addClass("ne_li");
				$("#bigTitle").html($("#ne_li"+newsSortId).html());
				$("#smallTitle").html($("#ne_li"+newsSortId).html());
		});
		function toToUrl(linkUrl){
			if(escape(linkUrl).indexOf("%u")!=-1){
		   		linkUrl=encodeURI(linkUrl);
		    	window.open(linkUrl);
		    }else{
		    	window.open(linkUrl);
		    }
		}
		function openChild(newsSortId){
			$("ul[id='child_li*']").hide();
			$("#child_li"+newSortId).show();
		}
</script>