<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../public/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>学习档案</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/grade/css/userStudyPortfolio.css" />
</head>
<body style="background: none" class="qing">
	<div style="position: relative; margin-left: 50%; left: -500px;"
		class="all1">
		<div class="all_top">
			<c:if test="${empty grade.gradeImg }">
				<img src="${pageContext.request.contextPath}/static/admin/images/gradeLogoImg.jpg" width="122" height="122" />
			</c:if>
			<c:if test="${!empty grade.gradeImg  }">
				<img src="${ossResource}/${grade.gradeImg}" width="82" height="82" />
			</c:if>
			<p>
				${grade.gradeName} 
				<span class="lv"> 
					(
						<c:if test="${gradeUser.isGraduate==2}">未结业</c:if>
					 	<c:if test="${gradeUser.isGraduate==1}">已结业</c:if>
					 )
				</span>
			</p>
			<div>
				考核要求： <b class="cheng">${grade.graduationDesc }</b><br/>
				培训时间：${grade.startDate}--${grade.endDate} <br/>
				培训目标：${grade.gradeTarget }
			</div>
		</div>
		<div class="all_main">
			<div class="huan huan1">
				<div>
					<b class="lan">个人信息</b>
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="13%" class="tian">姓名</td>
						<td width="37%">${user.userRealName }</td>
						<td width="13%" class="tian">性别</td>
						<td width="37%">
							<c:if test="${ user.userSex == 1 }">男</c:if> 
							<c:if test="${ user.userSex == 2 }">女</c:if>
						</td>
					</tr>
					<tr>
						<td class="tian">电话</td>
						<td>${user.userPhone }</td>
						<td class="tian">民族</td>
						<td>${userNationality }</td>
					</tr>
					<tr>
						<td class="tian">职级</td>
						<td colspan="3">${user.rankName }</td>
					</tr>
				</table>

			</div>
			<div class="huan huan1">
				<div>
					<b class="lan">必修课</b>
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" class="tian">课程名称</td>
						<td width="13%" class="tian">进度</td>
						<td width="12%" class="tian">时间</td>
						<td width="13%" class="tian">获得学时</td>
					</tr>
					<c:if test="${ empty requiredCourseUserList }">
						<tr>
							<td colspan="5" class="tdNull">暂无信息</td>
						</tr>
					</c:if>
					<c:if test="${ !empty requiredCourseUserList }">
						<c:forEach var="courseuser" items="${requiredCourseUserList}">
							<tr>
								<td>${courseuser.courseName }</td>
								<td align="center" class="liqi">${courseuser.jindu }</td>
								<td align="center" class="liqi">${courseuser.xkDate }</td>
								<td align="center">
									<b class="cheng">
										<c:if test="${courseuser.isOver==1 }">${courseuser.credit}</c:if>
										<c:if test="${courseuser.isOver==2 }">0</c:if>
									</b>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
			<div class="huan huan1">
				<div>
					<b class="lan">选修课</b>
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" class="tian">课程名称</td>
						<td width="13%" class="tian">进度</td>
						<td width="12%" class="tian">时间</td>
						<td width="13%" class="tian">获得学时</td>
					</tr>
					<c:if test="${ empty optionalCourseUserList }">
						<tr>
							<td colspan="5" class="tdNull">暂无信息</td>
						</tr>
					</c:if>
					<c:if test="${ !empty optionalCourseUserList }">
						<c:forEach var="courseUser" items="${optionalCourseUserList}">
							<tr>
								<td>${courseUser.courseName }</td>
								<td align="center" class="liqi">${courseUser.jindu }</td>
								<td align="center" class="liqi">${courseUser.xkDate }</td>
								<td align="center">
									<b class="cheng">
										<c:if test="${courseUser.isOver==1 }">${courseUser.credit }</c:if>
										<c:if test="${courseUser.isOver==2 }">0</c:if>
									</b>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
			<!-- 
			<div class="huan huan1">
				<div>
					<b class="lan">讨论</b>
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="87%" class="tian">主题</td>
						<td width="13%" class="tian">获得学分</td>
					</tr>
					<c:if test="${ empty replyScoreList }">
						<tr>
							<td colspan="2" class="tdNull">暂无信息</td>
						</tr>
					</c:if>
					<c:if test="${ !empty replyScoreList }">
						<c:forEach items="${replyScoreList}" var="discussReplyScore">
							<tr>
								<td>${discussReplyScore.discussTitle }</td>
								<td align="center"><b class="cheng">${discussReplyScore.credit }</b>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>

			</div>
			 -->
			<div class="huan huan1">
				<div>
					<b class="lan">论文</b>
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="87%" class="tian">主题</td>
						<td width="13%" class="tian">获得学分</td>
					</tr>
					<c:if test="${ empty gradeWorkReplyList }">
						<tr>
							<td colspan="2" class="tdNull">暂无信息</td>
						</tr>
					</c:if>
					<c:if test="${!empty gradeWorkReplyList }">
						<c:forEach items="${gradeWorkReplyList}" var="gradeWorkReply">
							<tr>
								<td>${gradeWorkReply.workTitle }</td>
								<td align="center"><b class="cheng">${gradeWorkReply.workScore }</b>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
			<div class="huan huan1">
				<div>
					<b class="lan">考试</b>
				</div>
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" class="tian">考试名称</td>
						<td width="13%" class="tian">考试时间</td>
						<td width="12%" class="tian">答题用时</td>
						<td width="12%" class="tian">成绩</td>
						<!-- 
						<td width="13%" class="tian">获得学分</td>
						 -->
					</tr>
					<c:if test="${ empty examSocreList }">
						<tr>
							<td colspan="5" class="tdNull">暂无信息</td>
						</tr>
					</c:if>
					<c:if test="${ !empty examSocreList }">
						<c:forEach items="${examSocreList}" var="examScore">
							<tr>
								<td>${examScore.examName }</td>
								<td class="liqi" align="center">${examScore.submitDate }</td>
								<td class="liqi" align="center">${examScore.useTime }</td>
								<td align="center" class="liqi">${examScore.score }</td>
								<!-- 
								<td align="center"><b class="cheng">${examScore.credit/100 }</b>
								 -->
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>

			</div>
			<!-- 合并统计: -->
			<div class="huan huan1">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td width="12%" rowspan="2" align="center" class="heji">合计</td>
						<c:if
							test="${!empty grade.requiredPeriod && grade.requiredPeriod >0}">
							<td width="13%" class="tian">必修学时</td>
						</c:if>
						<c:if
							test="${!empty grade.optionalPeriod && grade.optionalPeriod >0}">
							<td width="12%" class="tian">选修学时</td>
						</c:if>
						<!-- 
							<td width="13%" class="tian">讨论学分</td>
						 -->
						<td width="13%" class="tian">论文学分</td>
						<td width="13%" class="tian">考试学分</td>
					</tr>
					<tr>
						<c:if test="${!empty grade.requiredPeriod && grade.requiredPeriod >0}">
							<td align="center">
								<b class="cheng">${gradeUser.requiredPeriod }</b>
							</td>
						</c:if>
						<c:if test="${!empty grade.optionalPeriod && grade.optionalPeriod >0}">
							<td align="center">
								<b class="cheng">${gradeUser.optionalPeriod }</b>
							</td>
						</c:if>
						<!-- 
						<td align="center"><b class="cheng">${gradeUser.discussReplyCredit/100 }</b>
						</td>
						 -->
						<td align="center"><b class="cheng">${gradeUser.workScore }</b>
						</td>
						<td align="center"><b class="cheng">${gradeUser.examScore }</b>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>