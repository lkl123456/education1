<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${grade.gradeName }——${user.userRealName} </title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/static/css/index.css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/static/css/gradeUserDetail.css" />
	</head>

	<body class="qing">
		<div class="all1">
			<div class="all_top">
				<c:if test="${empty grade.gradeImg }">
                   <img src="<%=contextPath%>/static/images/gradeLogoImg.jpg" width="122" height="122" />
                </c:if>
                <c:if test="${!empty grade.gradeImg  }">
                   <img src="<%=imgServerPath%>${grade.gradeImg}" width="82" height="82" />
                </c:if>
				<p>
					${grade.gradeName }
					<span class="lv"> （<c:if test="${gradeUser.isGraduate==2 }">未毕业</c:if><c:if test="${gradeUser.isGraduate==1 }">已毕业</c:if>）</span>
				</p>
				<div>
					班级描述：${grade.gradeDesc }
					<br />
					培训时间：<fmt:formatDate value="${grade.startDate}" type="date" pattern="yyyy-MM-dd" /> - <fmt:formatDate value="${grade.endDate}" type="date" pattern="yyyy-MM-dd" />
					<br />
					培训目标：${grade.gradeTarget }
				</div>
				<!-- <a href="javascript:" onClick="javascript:window.print()">打印</a> -->
			</div>
			<div class="all_main">
				<div class="huan huan1">
					<div>
						<b class="lan">个人信息</b>
					</div>
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td width="13%" class="tian">
								姓名
							</td>
							<td width="37%">
								${user.userRealName }
							</td>
							<td width="13%" class="tian">
								性别
							</td>
							<td width="37%">
								<c:if test="${ user.userSex == 1 }">男</c:if><c:if test="${ user.userSex == 2 }">女</c:if>
							</td>
						</tr>
						<tr>
							<td class="tian">
								电话
							</td>
							<td>
								${user.userPhone }
							</td>
							<td class="tian">
								民族
							</td>
							<td>
								${ationalityName }
							</td>
						</tr>
						<tr>
							<td class="tian">
								职级
							</td>
							<td colspan="3">
								${user.rankName }
							</td>
						</tr>
					</table>

				</div>
				<div class="huan huan1">
					<div>
						<b class="lan">必修课</b>
					</div>
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td width="50%" class="tian">
								课程名称
							</td>
							<td width="13%" class="tian">
								进度
							</td>
							<td width="12%" class="tian">
								时间
							</td>
							<td width="13%" class="tian">
								课程时长
							</td>
						</tr>
						<c:if test="${ empty requriedCourseUserList }">
							<tr>
				       			<td colspan="5" class="tdNull">暂无信息</td>
							</tr>
						</c:if>
						<c:if test="${ !empty requriedCourseUserList }">
							<c:forEach items="${requriedCourseUserList}" var ="courseUser" >
					        	<tr>
									<td>
										${courseUser.courseName }
									</td>
									<td align="center" class="liqi">
										<fmt:formatNumber value="${courseUser.studyAllTimeLong/courseUser.courseDuration}" pattern="#0%"/>
									</td>
									<td align="center" class="liqi">
										<fmt:formatDate pattern="yyyy-MM-dd" value="${courseUser.xkDate }"/>
									</td>
									<td align="center">
										<b class="cheng">${courseUser.courseDurationLong} </b>
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
							<td width="50%" class="tian">
								课程名称
							</td>
							<td width="13%" class="tian">
								进度
							</td>
							<td width="12%" class="tian">
								时间
							</td>
							<td width="13%" class="tian">
								课程时长
							</td>
						</tr>
						<c:if test="${ empty optionalCourseUserList }">
							<tr>
				       			<td colspan="5" class="tdNull">暂无信息</td>
							</tr>
						</c:if>
						<c:if test="${ !empty optionalCourseUserList }">
							<c:forEach items="${optionalCourseUserList}" var ="courseUser" >
					        	<tr>
									<td>
										${courseUser.courseName }
									</td>
									<td align="center" class="liqi">
										<fmt:formatNumber value="${courseUser.studyAllTimeLong/courseUser.courseDuration}" pattern="#0%"/>
									</td>
									<td align="center" class="liqi">
										<fmt:formatDate pattern="yyyy-MM-dd" value="${courseUser.xkDate }"/>
									</td>
									<td align="center">
										<b class="cheng">${courseUser.courseDurationLong} </b>
									</td>
								</tr>  
							</c:forEach>
						</c:if>
					</table>

				</div>
				<div class="huan huan1">
					<div>
						<b class="lan">论文</b>
					</div>
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td width="87%" class="tian">
								主题
							</td>
							<td width="13%" class="tian">
								获得学分
							</td>
						</tr>
						<c:if test="${ empty studentWorkList }">
							<tr>
				       			<td colspan="2" class="tdNull">暂无信息</td>
							</tr>
						</c:if>
						<c:if test="${ !empty studentWorkList }">
							<c:forEach items="${studentWorkList}" var ="studentWork" >
					        	<tr>
									<td>
										${studentWork.workTitle }
									</td>
									<td  align="center">
										<b class="cheng">${courseUser.workScore }</b>
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
							<td width="50%" class="tian">
								考试名称
							</td>
							<td width="13%" class="tian">
								考试时间
							</td>
							<td width="12%" class="tian">
								答题用时
							</td>
							<td width="12%" class="tian">
								成绩
							</td>
						</tr>
						<c:if test="${ empty examScoreList }">
							<tr>
				       			<td colspan="5" class="tdNull">暂无信息</td>
							</tr>
						</c:if>
						<c:if test="${ !empty examScoreList }">
							<c:forEach items="${examScoreList}" var ="examScore" >
								<tr>
									<td>
										${examScore.examName }
									</td>
									<td align="center" class="liqi">
										${examScore.examDate }
									</td>
									<td align="center" class="liqi">
										${examScore.useTime }
									</td>
									<td align="center" class="liqi">
										${examScore.score }
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>

				</div>
				<div class="huan huan1">
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12%" rowspan="2" align="center" class="heji">
								合计
							</td>
							<td width="13%" class="tian">
								必修学时
							</td>
							<td width="12%" class="tian">
								选修学时
							</td>
							<td width="13%" class="tian">
								讨论学分
							</td>
							<td width="13%" class="tian">
								论文学分
							</td>
							<td width="13%" class="tian">
								考试学分
							</td>
						</tr>
						<tr>
							<td align="center">
								<b class="cheng">${gradeUser.requiredPeriod }</b>
							</td>
							<td align="center">
								<b class="cheng">${gradeUser.optionalPeriod }</b>
							</td>
							<td align="center">
								<b class="cheng">0.0</b>
							</td>
							<td align="center">
								<b class="cheng">0.0</b>
							</td>
							<td align="center">
								<b class="cheng">0.0</b>
							</td>
						</tr>
					</table>
				</div>

			</div>
		</div>
	</body>
</html>