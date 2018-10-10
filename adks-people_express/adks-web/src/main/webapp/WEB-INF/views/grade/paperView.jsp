<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>预览试卷</title>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/static/css/papercss.css" />
<script type="text/javascript"
	src="<%=contextPath%>/static/grade/gradeExamInfo.js"></script>
</head>
<body>
	<form id="examInfoFrom" name="examInfoFrom" method="post"
		action="<%=contextPath%>/gradeExam/gradeExamSave.do">
		<input type="hidden" name="examId" id="idd" value="${exam.examId }" />
		<input type="hidden" value="${userId }" /> <input type="hidden"
			value="${exam.examDate }" id="testTime" name="testTime" /> <input
			type="hidden" value="${exam.examId }" id="examId" /><input
			type="hidden" value="${gradeId}" name="gradeId" id="gradeId" /> <input
			type="hidden" value="${paper.paperId }" name="paperId" id="paperId" />
		<input type="hidden" name="jsTime" id="jsTime" value="00:00:00" />

		<dl class="zidao">
			<dt>《${paper.paperName }》</dt>
			<dd>
				考试时间：${exam.examDate} 分钟<br />全卷题数：${paper.qsNum }道题<br />
				卷面总分：${paper.score }分
			</dd>
		</dl>
		<div class="shiti">
			<div style="width: 720px; float: left;">
				<c:if test="${not empty dQuestion}">
					<div class="shiti_l">
						<h2>一、单选题（共${dQuestion.size() }道题，下列各题选项中，只有一个正确答案）</h2>
						<c:forEach var="dq" items="${dQuestion }" varStatus="ds">
							<dl class="danxuan">
								<dt>
									<span>${ds.index+1}.</span>${dq.questionName }
									（${dq.questionValue }分）
								</dt>
								<c:if test="${not empty dq.optionA }">
									<dd>A.${dq.optionA }</dd>
								</c:if>
								<c:if test="${not empty dq.optionB }">
									<dd>B.${dq.optionB }</dd>
								</c:if>
								<c:if test="${not empty dq.optionC }">
									<dd>C.${dq.optionC }</dd>
								</c:if>
								<c:if test="${not empty dq.optionD }">
									<dd>D.${dq.optionD }</dd>
								</c:if>
								<c:if test="${not empty dq.optionE }">
									<dd>E.${dq.optionE }</dd>
								</c:if>
								<c:if test="${not empty dq.optionF }">
									<dd>F.${dq.optionF }</dd>
								</c:if>
								<c:if test="${not empty dq.optionG }">
									<dd>G.${dq.optionG }</dd>
								</c:if>
								<c:if test="${not empty dq.optionH }">
									<dd>F.${dq.optionH }</dd>
								</c:if>
							</dl>
							<div class="xuxiang">
								<c:if test="${not empty dq.optionA }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="A"
										onclick="danxOnClick('${dq.questionId }')" />A</label>
								</c:if>
								<c:if test="${not empty dq.optionB }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="B"
										onclick="danxOnClick('${dq.questionId }')" />B</label>
								</c:if>
								<c:if test="${not empty dq.optionC }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="C"
										onclick="danxOnClick('${dq.questionId }')" />C</label>
								</c:if>
								<c:if test="${not empty dq.optionD }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="D"
										onclick="danxOnClick('${dq.questionId }')" />D</label>
								</c:if>
								<c:if test="${not empty dq.optionE }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="E"
										onclick="danxOnClick('${dq.questionId }')" />E</label>
								</c:if>
								<c:if test="${not empty dq.optionF }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="F"
										onclick="danxOnClick('${dq.questionId }')" />F</label>
								</c:if>
								<c:if test="${not empty dq.optionG }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="G"
										onclick="danxOnClick('${dq.questionId }')" />G</label>
								</c:if>
								<c:if test="${not empty dq.optionH }">
									<label><input type="radio"
										name="danxuan${dq.questionId}" value="H"
										onclick="danxOnClick('${dq.questionId }')" />H</label>
								</c:if>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty dxQuestion}">
					<div class="shiti_l shiti_2">
						<h2>二、多选题（共${dxQuestion.size() }道题，下列各题选项中，可能有多个正确答案）</h2>
						<c:forEach var="dxq" items="${dxQuestion }" varStatus="dxs">
							<dl class="danxuan">
								<dt>
									<span>${dxs.index+1}.</span>${dxq.questionName }
									（${dxq.questionValue }分）
								</dt>
								<c:if test="${not empty dxq.optionA }">
									<dd>A.${dxq.optionA }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionB }">
									<dd>B.${dxq.optionB }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionC }">
									<dd>C.${dxq.optionC }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionD }">
									<dd>D.${dxq.optionD }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionE }">
									<dd>E.${dxq.optionE }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionF }">
									<dd>F.${dxq.optionF }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionG }">
									<dd>G.${dxq.optionG }</dd>
								</c:if>
								<c:if test="${not empty dxq.optionH }">
									<dd>F.${dxq.optionH }</dd>
								</c:if>
							</dl>
							<div class="xuxiang">
								<c:if test="${not empty dxq.optionA }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="A"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />A</label>
								</c:if>
								<c:if test="${not empty dxq.optionB }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="B"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />B</label>
								</c:if>
								<c:if test="${not empty dxq.optionC }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="C"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />C</label>
								</c:if>
								<c:if test="${not empty dxq.optionD }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="D"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />D</label>
								</c:if>
								<c:if test="${not empty dxq.optionE }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="E"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />E</label>
								</c:if>
								<c:if test="${not empty dxq.optionF }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="F"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />F</label>
								</c:if>
								<c:if test="${not empty dxq.optionG }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="G"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />G</label>
								</c:if>
								<c:if test="${not empty dxq.optionH }">
									<label><input type="checkbox"
										name="duoxuan${dxq.questionId}" value="H"
										onclick="duoxOnClick(this.name,'${dxq.questionId}')" />H</label>
								</c:if>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty pdQuestion}">
					<div class="shiti_l">
						<h2>三、判断题（共${pdQuestion.size() }道题，请判断下列各题是否正确）</h2>
						<c:forEach var="pdq" items="${pdQuestion }" varStatus="pds">
							<div class="shiti_l">
								<dl class="danxuan">
									<dt>
										<span>${pds.index+1}.</span>${pdq.questionName }
										（${pdq.questionValue }分）
									</dt>
								</dl>
								<div class="xuxiang">
									<label><input type="radio" value="1"
										name="panduan${pdq.questionId}"
										onclick="danxOnClick('${pdq.questionId }')" />A.对</label> <label><input
										type="radio" value="0" name="panduan${pdq.questionId}"
										onclick="danxOnClick('${pdq.questionId }')" />B.错</label>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty tkQuestion}">
					<div class="shiti_l shiti_3">
						<h2>四、填空题（共${tkQuestion.size() }道题，请在每题下面的输入框中作答，每个空格的答案用“|”隔开）</h2>
						<c:forEach var="tkq" items="${tkQuestion }" varStatus="tks">
							<div class="shiti_l shiti_3">
								<dl class="danxuan">
									<dt>
										<span>${tks.index+1}.</span>${tkq.questionName }
										（${tkq.questionValue }分）
									</dt>
								</dl>
								<div class="tiankong">
									<input style="vertical-align: top; margin-top: 0px" type="text"
										maxlength="150" name="tiankong${tkq.questionId }"
										onchange="tkOnCheck(this.value,'${tkq.questionId }')"
										onkeyup="tkOnCheck(this.value,'${tkq.questionId }')" />
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty wdQuestion}">
					<div class="shiti_l shiti_3">
						<h2>五、问答题（共${wdQuestion.size() }道题，请在每题下面的输入框中作答）</h2>
						<c:forEach var="wdq" items="${wdQuestion }" varStatus="wds">
							<div class="shiti_l shiti_3">
								<dl class="danxuan">
									<dt>
										<span>${wds.index+1}.</span>${wdq.questionName }
										（${wdq.questionValue }分）
									</dt>
								</dl>
								<div class="wenda">
									<textarea rows="5" cols="70" name="wenda${wdq.questionId }"
										id="wenda${wdq.questionId }"
										onchange="wdOnCheck(this.id,200,${wdq.questionId })"
										onkeyup="wdOnCheck(this.id,200,${wdq.questionId })"></textarea>
									<p style="font-size: 12px;">当前字数：0，字数限制：200</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
			</div>
			<div class="shiti_r">
				<div class="jishi">
					<span id="hh">00</span> <b>:</b> <span id="mm">00</span> <b>:</b> <span
						id="ss">00</span>
				</div>

				<!-- 单选题 -->
				<c:choose>
					<c:when test="${not empty dQuestion}">
						<dl>
							<dt>一、单选题</dt>
							<dd>
								<ul>
									<c:forEach items="${dQuestion}" var="dan" varStatus="danx">
										<li id="click_li_${dan.questionId}">${danx.index+1}</li>
									</c:forEach>
								</ul>
							</dd>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dt>一、单选题（暂无）</dt>
						</dl>
					</c:otherwise>
				</c:choose>

				<!-- 多选题 -->
				<c:choose>
					<c:when test="${not empty dxQuestion}">
						<dl>
							<dt>二、多选题</dt>
							<dd>
								<ul>
									<c:forEach items="${dxQuestion}" var="duo" varStatus="duox">
										<li id="click_li_${duo.questionId }">${duox.index+1 }</li>
									</c:forEach>

								</ul>
							</dd>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dt>二、多选题（暂无）</dt>
						</dl>
					</c:otherwise>
				</c:choose>

				<!-- 判断题 -->
				<c:choose>
					<c:when test="${not empty pdQuestion}">
						<dl>
							<dt>三、判断题</dt>
							<dd>
								<ul>
									<c:forEach items="${pdQuestion}" var="pan" varStatus="pand">
										<li id="click_li_${pan.questionId }">${pand.index+1 }</li>
									</c:forEach>
								</ul>
							</dd>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dt>三、判断题（暂无）</dt>
						</dl>
					</c:otherwise>
				</c:choose>

				<!-- 填空题 -->
				<c:choose>
					<c:when test="${not empty tkQuestion}">
						<dl>
							<dt>四、填空题</dt>
							<dd>
								<ul>
									<c:forEach items="${tkQuestion}" var="tian" varStatus="tiank">
										<li id="click_li_${tian.questionId }">${tiank.index+1 }</li>
									</c:forEach>
								</ul>
							</dd>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dt>四、填空题（暂无）</dt>
						</dl>
					</c:otherwise>
				</c:choose>

				<!-- 问答题 -->
				<c:choose>
					<c:when test="${not empty wdQuestion}">
						<dl>
							<dt>五、问答题</dt>
							<dd>
								<ul>
									<c:forEach items="${wdQuestion}" var="wen" varStatus="wend">
										<li id="click_li_${wen.questionId }">${wend.index+1 }</li>
									</c:forEach>
								</ul>
							</dd>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dt>五、问答题（暂无）</dt>
						</dl>
					</c:otherwise>
				</c:choose>

				<div class="tijiao_sj">
					<input id="examInfoBtn" type="button"
						onclick="gradeExamInfoSave('dj')" value="提交试卷" title="点击提交试卷" />
				</div>
			</div>
		</div>
	</form>
	<div class="bg exam_tx_div" id="exam_tx_div" style="display: none;">
		<dl class="bg_biao bg_biao_tx">
			<dt>
				考试时间提醒<span class="lv"></span><b onclick="examTxDivClose()"></b>
			</dt>
			<dd>
				<img src="<%=contextPath%>/center/grade/images/exam_tx.png"
					height="74" width="86" /> 您好！距离考试结束还有 <b class="cheng"
					id="tx_div_tNum">5</b> 分钟， 请您仔细检查试卷，时间结束后系统将自动提交试卷！（十五秒钟后该提醒将自动关闭）
			</dd>
		</dl>
	</div>
</body>
</html>

