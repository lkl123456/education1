<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="../../static/common/common.jsp"%>
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
	<dl  style="position: relative;" class="news_rv" >
		<iframe width='100%' height='600' name=aa frameborder=0 src='<%=imgServerPath%>${newsHtmlAdress }'></iframe>
    </dl>
</div>
<script type="text/javascript">
		$(document).ready(function(){ 
				var columnId = $("#columnId").val();
				$("#ne_li"+columnId).addClass("ne_li");
				$("#bigTitle").html($("#ne_li"+columnId).html());
				$("#smallTitle").html($("#ne_li"+columnId).html());
		});
</script>