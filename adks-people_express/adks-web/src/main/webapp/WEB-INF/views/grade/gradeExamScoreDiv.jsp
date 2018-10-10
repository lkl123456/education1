<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>

<dl class="bg_biao">
	<dt>
		<b>${examS.examName}</b> <span class="lv1">(<c:choose>
				<c:when test="${passFlag == 'true'}">已通过</c:when>
				<c:otherwise>未通过</c:otherwise>
			</c:choose>)
		</span><em onclick="javascript: gradeExamScoreHide()"></em>
	</dt>
	<dd>
		<table width="100%" border="1" cellspacing="0" cellpadding="0">
			<tr>
				<th scope="col">总分</th>
				<th scope="col">及格</th>
				<th scope="col">考试时限</th>
				<th scope="col">答题用时</th>
				<th scope="col">交卷时间</th>
				<th scope="col">批改状态</th>
				<th scope="col">成绩</th>
			</tr>
			<c:set var="esNum" value="1" />
			<c:if test="${ esList != null }">
				<c:forEach items="${esList}" var="es">
					<tr>
						<td><span class="jidu"><fmt:formatNumber
									value="${examS.scoreSum}" /></span></td>
						<td><fmt:formatNumber value="${examS.passScore}" /></td>
						<td>${examS.examDate}分钟</td>
						<td>${es.useTimeStr }</td>
						<td><fmt:formatDate value="${es.submitDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<c:choose>
							<c:when test="${es.isCorrent != null && es.isCorrent == '1'}">
								<td>未批改</td>
							</c:when>
							<c:otherwise>
								<td class="cheng">已批改</td>
							</c:otherwise>
						</c:choose>
						<td><fmt:formatNumber value="${es.score }" /></td>
					</tr>
					<c:set var="esNum" value="${esNum+1 }" />
				</c:forEach>
			</c:if>

		</table>
		<div class="zaikao">
			<a href="javascript: againGradeExam('${examS.examId}')"
				class="kaoshi_1">再考一次</a><a href="javascript: gradeExamScoreHide()">关闭</a>
		</div>
	</dd>
</dl>




