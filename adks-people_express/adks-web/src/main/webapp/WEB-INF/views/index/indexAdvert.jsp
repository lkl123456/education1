
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%
	
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	
%>
<c:choose>
<c:when test="${empty advert}">

	<%-- <c:if test = "${adLocation == '1'}">
		<a href="#">
		<img src="<%=basePath %>static/images/head.jpg" width="1000" height="90" />
	</a>
	</c:if>
	<c:if test = "${adLocation == '2'}">
		<a href="#">
		<img src="<%=basePath %>static/images/center.jpg" width="1000" height="90" />
	</a>
	</c:if>
	<c:if test = "${adLocation == '5'}">
		<a href="#">
		<img src="<%=basePath %>static/images/bottom.jpg" width="1000" height="90" />
	</a>
	</c:if>	 --%>
	<a href="#">
		<img src="<%=basePath %>static/images/head.jpg" width="1000" height="90" />
	</a>
	
</c:when>
<c:otherwise>
	<a href="${advert.adHref }" target="_blank">
		<img src="<%=imgServerPath %>${advert.adImgPath}" width="1000" height="90" />
	</a>
</c:otherwise>
</c:choose>