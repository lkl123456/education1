<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%-- <%@ include file="/common/script.jsp"%> --%>
<div class="tux">
</div>
<div class="news">
	<ul class="news_l">
		<%-- <li>
			<a href="<%=contextPath%>/user/feedbackIndex/${orgId }.shtml">在线反馈</a>
		</li> --%>
		<li>
			<a href="<%=contextPath%>/index/onlineHelp/xxlc/${orgId }.shtml">在线帮助</a>
		</li>
		<dl>
			<dd id="xxlc">
				<a href="<%=contextPath%>/index/onlineHelp/xxlc/${orgId }.shtml">学习流程</a>
			</dd>
			<dd id="czzn">
				<a href="<%=contextPath%>/index/onlineHelp/czzn/${orgId }.shtml">操作指南</a>
			</dd>
			<dd id="cjwt">
				<a href="<%=contextPath%>/index/onlineHelp/cjwt/${orgId }.shtml">常见问题</a>
			</dd>
			<dd id="kqbd">
				<a href="<%=contextPath%>/index/onlineHelp/kqbd/${orgId }.shtml">考前必读</a>
			</dd>
		</dl>
		<li class="ne_li">
			<a href="<%=contextPath%>/index/contactUs/${orgId }.shtml">学习支持</a>
		</li>
	</ul>

	<dl class="news_r">
		<dt>
			<b>学习支持</b>
			<span>前位置：<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a> > 学习支持</span>
		</dt>
		<dd class="lchen2">
			<br />
			<br />
			<h2>
				维护人员:
			</h2>
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="background: #aaa;">
				<tr>
					<td width="116" height="36" align="center" valign="middle" bgcolor="#efefef">
						<strong>负 责 人</strong>
					</td>
					<td width="116" height="36" align="center" valign="middle" bgcolor="#efefef">
						<strong>负责内容</strong>
					</td>
					<td width="180" height="36" align="center" valign="middle" bgcolor="#efefef">
						<strong>电话</strong>
					</td>
					<td width="200" height="36" align="center" valign="middle" bgcolor="#efefef">
						<strong>QQ</strong>
					</td>
				</tr>
			<c:choose>
				<c:when test="${orgId==2}">
				<tr>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						杨晓天
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						意见汇总
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						01087786155
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						1016727215

					</td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						王　海
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						功能/内容
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						01058250662
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						1619845056
					</td>
				</tr>
				</c:otherwise>
			</c:choose>
				<tr>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						孟　攀
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						技术支持
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						18611778542
					</td>
					<td height="36" align="center" valign="middle" bgcolor="#FFFFFF">
						381453618
					</td>
				</tr>
			</table>
			<br />
			<!-- 
			<h2>
				客服维护:
			</h2>
			客服中心：31615008
			<br />
			传 真：31615008
			<br />
			 -->
		</dd>
	</dl>
</div>
<script type="text/javascript">
// JavaScript Document
$(document).ready(function(){
	$('.news_l li').click(function(){
		$neli=$(this).index();
		$('.news_l li').removeClass('ne_li');
		$(this).addClass('ne_li');
		$('.news_r').hide();
		$('.news_r').eq($neli).show();
	})
})
</script>

