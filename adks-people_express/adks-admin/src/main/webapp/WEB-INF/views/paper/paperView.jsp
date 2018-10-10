<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>预览试卷</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/paper/css/css.css" />
</head>
<body>
	<form>
		<dl class="zidao">
			<dt>《${paper.paperName }》</dt>
			<dd>
				全卷题数：${paper.qsNum }道题<br /> 卷面总分：${paper.score }分
			</dd>
		</dl>
		<div class="shiti">
			<div style="width: 1000px; float: left;">
				<c:if test="${not empty dQuestion}">
					<div class="shiti_l">
						<h2>一、单选题</h2>
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
									<label><input type="radio" value="" name="d${ds.index+1}" />A</label>
								</c:if>
								<c:if test="${not empty dq.optionB }">
									<label><input type="radio" name="d${ds.index+1}" />B</label>
								</c:if>
								<c:if test="${not empty dq.optionC }">
									<label><input type="radio" name="d${ds.index+1}" />C</label>
								</c:if>
								<c:if test="${not empty dq.optionD }">
									<label><input type="radio" name="d${ds.index+1}" />D</label>
								</c:if>
								<c:if test="${not empty dq.optionE }">
									<label><input type="radio" name="d${ds.index+1}" />E</label>
								</c:if>
								<c:if test="${not empty dq.optionF }">
									<label><input type="radio" name="d${ds.index+1}" />F</label>
								</c:if>
								<c:if test="${not empty dq.optionG }">
									<label><input type="radio" name="d${ds.index+1}" />G</label>
								</c:if>
								<c:if test="${not empty dq.optionH }">
									<label><input type="radio" name="d${ds.index+1}" />H</label>
								</c:if>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty dxQuestion}">
					<div class="shiti_l shiti_2">
						<h2>二、多选题</h2>
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
									<label><input type="checkbox" name="dx${dxs.index+1}" />A</label>
								</c:if>
								<c:if test="${not empty dxq.optionB }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />B</label>
								</c:if>
								<c:if test="${not empty dxq.optionC }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />C</label>
								</c:if>
								<c:if test="${not empty dxq.optionD }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />D</label>
								</c:if>
								<c:if test="${not empty dxq.optionE }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />E</label>
								</c:if>
								<c:if test="${not empty dxq.optionF }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />F</label>
								</c:if>
								<c:if test="${not empty dxq.optionG }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />G</label>
								</c:if>
								<c:if test="${not empty dxq.optionH }">
									<label><input type="checkbox" name="dx${dxs.index+1}" />H</label>
								</c:if>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty pdQuestion}">
					<div class="shiti_l">
						<h2>三、判断题</h2>
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
										name="pd${pds.index+1}" />A.对</label> <label><input
										type="radio" value="0" name="pd${pds.index+1}" />B.错</label>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty tkQuestion}">
					<div class="shiti_l shiti_3">
						<h2>四、填空题</h2>
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
										maxlength="150" />
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty wdQuestion}">
					<div class="shiti_l shiti_3">
						<h2>五、问答题</h2>
						<c:forEach var="wdq" items="${wdQuestion }" varStatus="wds">
							<div class="shiti_l shiti_3">
								<dl class="danxuan">
									<dt>
										<span>${wds.index+1}.</span>${wdq.questionName }
										（${wdq.questionValue }分）
									</dt>
								</dl>
								<div class="wenda">
									<textarea rows="5" cols="70"></textarea>
									<p style="font-size: 12px;">当前字数：0，字数限制：200</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>

				<%-- <c:forEach var="question" items="${questions }" varStatus="status">
					<c:if test="${question.questionType==1 }">
						<div class="shiti_l">
							<dl class="danxuan">
								<dt>
									<span>${status.index+1}.</span>${question.questionName }
									（${question.questionValue }分）
								</dt>
								<c:if test="${not empty question.optionA }">
									<dd>A.${question.optionA }</dd>
								</c:if>
								<c:if test="${not empty question.optionB }">
									<dd>B.${question.optionB }</dd>
								</c:if>
								<c:if test="${not empty question.optionC }">
									<dd>C.${question.optionC }</dd>
								</c:if>
								<c:if test="${not empty question.optionD }">
									<dd>D.${question.optionD }</dd>
								</c:if>
								<c:if test="${not empty question.optionE }">
									<dd>E.${question.optionE }</dd>
								</c:if>
								<c:if test="${not empty question.optionF }">
									<dd>F.${question.optionF }</dd>
								</c:if>
								<c:if test="${not empty question.optionG }">
									<dd>G.${question.optionG }</dd>
								</c:if>
								<c:if test="${not empty question.optionH }">
									<dd>F.${question.optionH }</dd>
								</c:if>
							</dl>
							<div class="xuxiang">
								<c:if test="${not empty question.optionA }">
									<label><input type="radio" />A</label>
								</c:if>
								<c:if test="${not empty question.optionB }">
									<label><input type="radio" />B</label>
								</c:if>
								<c:if test="${not empty question.optionC }">
									<label><input type="radio" />C</label>
								</c:if>
								<c:if test="${not empty question.optionD }">
									<label><input type="radio" />D</label>
								</c:if>
								<c:if test="${not empty question.optionE }">
									<label><input type="radio" />E</label>
								</c:if>
								<c:if test="${not empty question.optionF }">
									<label><input type="radio" />F</label>
								</c:if>
								<c:if test="${not empty question.optionG }">
									<label><input type="radio" />G</label>
								</c:if>
								<c:if test="${not empty question.optionH }">
									<label><input type="radio" />H</label>
								</c:if>
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==2 }">
						<div class="shiti_l shiti_2">
							<dl class="danxuan">
								<dt>
									<span>${status.index+1}.</span>${question.questionName }
									（${question.questionValue }分）
								</dt>
								<c:if test="${not empty question.optionA }">
									<dd>A.${question.optionA }</dd>
								</c:if>
								<c:if test="${not empty question.optionB }">
									<dd>B.${question.optionB }</dd>
								</c:if>
								<c:if test="${not empty question.optionC }">
									<dd>C.${question.optionC }</dd>
								</c:if>
								<c:if test="${not empty question.optionD }">
									<dd>D.${question.optionD }</dd>
								</c:if>
								<c:if test="${not empty question.optionE }">
									<dd>E.${question.optionE }</dd>
								</c:if>
								<c:if test="${not empty question.optionF }">
									<dd>F.${question.optionF }</dd>
								</c:if>
								<c:if test="${not empty question.optionG }">
									<dd>G.${question.optionG }</dd>
								</c:if>
								<c:if test="${not empty question.optionH }">
									<dd>F.${question.optionH }</dd>
								</c:if>
							</dl>
							<div class="xuxiang">
								<c:if test="${not empty question.optionA }">
									<label><input type="checkbox" />A</label>
								</c:if>
								<c:if test="${not empty question.optionB }">
									<label><input type="checkbox" />B</label>
								</c:if>
								<c:if test="${not empty question.optionC }">
									<label><input type="checkbox" />C</label>
								</c:if>
								<c:if test="${not empty question.optionD }">
									<label><input type="checkbox" />D</label>
								</c:if>
								<c:if test="${not empty question.optionE }">
									<label><input type="checkbox" />E</label>
								</c:if>
								<c:if test="${not empty question.optionF }">
									<label><input type="checkbox" />F</label>
								</c:if>
								<c:if test="${not empty question.optionG }">
									<label><input type="checkbox" />G</label>
								</c:if>
								<c:if test="${not empty question.optionH }">
									<label><input type="checkbox" />H</label>
								</c:if>
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==3 }">
						<div class="shiti_l">
							<dl class="danxuan">
								<dt>
									<span>${status.index+1}.</span>${question.questionName }
									（${question.questionValue }分）
								</dt>
							</dl>
							<div class="xuxiang">
								<label><input type="radio" value="1" />A.对</label> <label><input
									type="radio" value="0" />B.错</label>
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==4 }">
						<div class="shiti_l shiti_3">
							<dl class="danxuan">
								<dt>
									<span>${status.index+1}.</span>${question.questionName }
									（${question.questionValue }分）
								</dt>
							</dl>
							<div class="tiankong">
								<input style="vertical-align: top; margin-top: 0px" type="text"
									maxlength="150" />
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==5 }">
						<div class="shiti_l shiti_3">
							<dl class="danxuan">
								<dt>
									<span>${status.index+1}.</span>${question.questionName }
									（${question.questionValue }分）
								</dt>
							</dl>
							<div class="wenda">
								<textarea rows="5" cols="70"></textarea>
								<p style="font-size: 12px;">当前字数：0，字数限制：200</p>
							</div>
						</div>
					</c:if> 
					</c:forEach>--%>
				<%-- <div class="qtitlediv">
								第${status.index+1}题：
								<c:choose>
									<c:when test="${question.questionType==1 }">（单选题）</c:when>
									<c:when test="${question.questionType==2 }">（多选题）</c:when>
									<c:when test="${question.questionType==3 }">（判断题）</c:when>
									<c:when test="${question.questionType==4 }">（填空题）</c:when>
									<c:when test="${question.questionType==5 }">（问答题）</c:when>
								</c:choose>
								${question.questionName }（分值：${question.questionValue }）
							</div>
							<div class="qanswerdiv">
								<c:choose>
									<c:when test="${question.questionType==1 }">
										<c:if test="${not empty question.optionA }">
											<input type="radio" />${question.optionA }</c:if>
										<c:if test="${not empty question.optionB }">
											<input type="radio" />${question.optionB }</c:if>
										<c:if test="${not empty question.optionC }">
											<input type="radio" />${question.optionC }</c:if>
										<c:if test="${not empty question.optionD }">
											<input type="radio" />${question.optionD }</c:if>
										<c:if test="${not empty question.optionE }">
											<input type="radio" />${question.optionE }</c:if>
										<c:if test="${not empty question.optionF }">
											<input type="radio" />${question.optionF }</c:if>
										<c:if test="${not empty question.optionG }">
											<input type="radio" />${question.optionG }</c:if>
										<c:if test="${not empty question.optionH }">
											<input type="radio" />${question.optionH }</c:if>
									</c:when>
									<c:when test="${question.questionType==2 }">
										<c:if test="${not empty question.optionA }">
											<input type="checkbox" />${question.optionA }</c:if>
										<c:if test="${not empty question.optionB }">
											<input type="checkbox" />${question.optionB }</c:if>
										<c:if test="${not empty question.optionC }">
											<input type="checkbox" />${question.optionC }</c:if>
										<c:if test="${not empty question.optionD }">
											<input type="checkbox" />${question.optionD }</c:if>
										<c:if test="${not empty question.optionE }">
											<input type="checkbox" />${question.optionE }</c:if>
										<c:if test="${not empty question.optionF }">
											<input type="checkbox" />${question.optionF }</c:if>
										<c:if test="${not empty question.optionG }">
											<input type="checkbox" />${question.optionG }</c:if>
										<c:if test="${not empty question.optionH }">
											<input type="checkbox" />${question.optionH }</c:if>
									</c:when>
								</c:choose>
							</div>
							<div class="qanswer1div">答案：${question.anwsers }</div> --%>

			</div>
		</div>
	</form>
</body>
</html>

