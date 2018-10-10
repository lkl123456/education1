<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/static/grade/js.js"></script>
<dl class="min_right min_rightq">
	<dt>
        <ol>
            <li id="jyks" class="right_min_1"><a href="javascript:gradeExamList()">结业考试</a></li>
            <!-- <li id="mnks"><a href="javascript:gradeExamList(92)">模拟练习</a></li> -->
        </ol>
	</dt>
        <dl class="main_r_dd2a">
        	<input type="hidden" id="gradeId" value="${gradeId }" />
        	<c:set var="e_num" value="0"></c:set>
        	<c:if test="${ page != null && page.rows != null && page.totalPage > 0 }">
            <c:forEach  items="${page.rows}" var ="map" >
           	<input type="hidden" id="examId_${e_num}" value="${map.examId }" />
           	<input type="hidden" id="examTimes_${e_num}" value="${map.examTimes }" />
            <dd class="main_r_dd1">
            	<p class="lan">${map.examName}</p>
                <div  class="hui">剩余/允许次数：<font id="reTimes_${e_num}">0</font>/${map.examTimes}　　　开放时间：<fmt:formatDate value="${map.startDate}" pattern="yyyy.MM.dd HH:mm"/>--<fmt:formatDate value="${map.endDate}" pattern="yyyy.MM.dd HH:mm"/> </div>
                <c:choose>
                	<c:when test="${ map.endDate > nowDate }">
                		<a class="kaoshi_1 ks2" id="btn_enter_${e_num}" href="javascript: examInfoDivShow(${map.examId}, '${map.examName}','${map.scoreSum }','${map.passScore}','${map.examDate }')">参加考试</a>
                		<input type="hidden" id="enter_exam_flag_${map.examId}" value="true"/>
                	</c:when>
                	<c:otherwise>
                		<a class="kaoshi_1 ks2" id="btn_enter_${e_num}" href="javascript: alert('考试开放时间已过，现已不能再参加考试！')">参加考试</a>
                		<input type="hidden" id="enter_exam_flag_${map.examId}" value="timeOut"/>	
                	</c:otherwise>
                </c:choose>
                <a class="kaoshi_1" href="#" onclick="javascript: gradeExamScoreShow(${map.examId})">查看成绩</a>	
          	</dd>
            <c:set var="e_num" value="${e_num+1}"></c:set>
          	</c:forEach>
            </c:if>
            <input type="hidden" id="ex_num" value="${e_num }" />
            <dd>
            	<br/>
            	<com:pageCom />
            </dd>
       	</dl>
       	<script type="text/javascript">
	      	$(document).ready(function(){
	      		//alert($("#ex_num").val());
	      		var ex_num = $("#ex_num").val();
	      		if(ex_num > 0){
	      			for(var i = 0; i < ex_num; i++){
	      				// 班级ID
	      				var gradeId = $("#gradeId").val();
	      				// 考试ID
	      				var examId = $("#examId_"+i).val();
	      				// 当前考试允许次数
	      				var examTimes = $("#examTimes_"+i).val();
	      				$.ajax({
						async:false,
						url:contextPath+"/gradeExam/getResidueExamTimes.do?gradeId="+gradeId+"&examId="+examId+"&examTimes="+examTimes,
						type:"post", 
						success:function(data){
							$("#reTimes_"+i).html(data);
							//alert("---"+data);
							if(data == 0){								
								$("#btn_enter_"+i).attr("href","javascript: alert('您已用完允许参加考试次数，现已不能再参加考试！')");
								$("#enter_exam_flag_"+examId).val("false");
							}
							
						}
					});
	      			}
	      		}
	      	});
		</script>
</dl>
