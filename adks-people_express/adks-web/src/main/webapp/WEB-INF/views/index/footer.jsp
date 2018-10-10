<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<input type="hidden" id="userName" value="${user.userName}" />
	<dl class="footer">
		<dt>
			<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a>
			<a href="<%=contextPath%>/cms/newsIndex/15/${orgId }.shtml">通知公告</a>
			<a href="<%=contextPath%>/course/courseIndex/${orgId }.shtml">课程中心</a>
		<!-- 
			<a href="<%=contextPath%>/author/allAuthor.shtml">师资库</a>
			<a href="<%=contextPath%>/cms/subjectIndex.shtml">专题学习</a>
		--> 
			<a href="<%=contextPath%>/registGrade/toRegisterGradeList/${orgId }.shtml">专题培训</a>
		
			<a href="<%=contextPath%>/index/centerIndex/${orgId }.shtml">学习中心</a>
		<!-- 
			<a href="<%=contextPath%>/index/downloadIndex.shtml">下载专区</a>
		 -->
			<a href="<%=contextPath%>/index/contactUs/${orgId }.shtml">学习支持</a>
		
			<a href="<%=contextPath%>/index/download/${orgId }.shtml">移动端</a>
					
		</dt>
		<dd>
			<c:if test="${orgConfig!=null && orgConfig.copyRight!=null}">
				${orgConfig.copyRight }
			</c:if>
			<c:if test="${orgConfig==null || orgConfig.copyRight==null}">
				主办单位：中国民用航空局    承办单位：中共民航局党校    技术支持：北京爱迪科森教育科技股份有限公司
				<br />
				网站备案/许可证号： 京ICP备15013317号
			</c:if>
		</dd>
	</dl>