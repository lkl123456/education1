<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%@ include file="../../static/common/script.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/grade/gradeCenter.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/center/user.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var gradeId=$("#gradeId").val();
	var flag="";
	//gradeMessage(gradeId);
	getGradeCourseList(flag);
	$('.min_left li').click(function(){
        for(var $i=0;$i<4;$i++){
	        $('.min_left li').eq($i).removeClass('gonggong');
	    };
    $keyn=$(this).index('.min_left li');
       $('.min_left li').eq($keyn).addClass('gonggong');
    })
	});
</script>

<div class="wrap_min_tou">
	<div class="min_tou_px">
   		<c:choose>
           	<c:when test="${ grade.gradeImg == null || grade.gradeImg == ''}">
           		<a  href=""> <img id="logoPath"  src="<%=contextPath%>/static/images/gradeLogoImg.jpg" width="62" height="62" /></a>
           	</c:when>
           	<c:otherwise>
           		<a  href=""> <img id="logoPath" src="<%=imgServerPath%>${grade.gradeImg}" width="62" height="62" /></a>
           	</c:otherwise>
           </c:choose>
    </div>
    <div class="r_box">
		<h2>${grade.gradeName }</h2>
		<p>
			<span><b>培训时间：</b><fmt:formatDate value="${grade.startDate }" type="date" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${grade.endDate }" type="date" pattern="yyyy.MM.dd"/></span><span><b>班级人数：</b>${grade.userNum }</span>
		</p>
		<p><span><b>我的排名：</b>${gradeUser.userRanking }/${grade.userNum }</span><span><b>我的学时：</b>${myCredit }</span></p>
		<%-- <p><b>培训要求：</b>${grade.trainingRequired }</p> --%>
		<p>
		<b>培训目标：</b>
		${grade.gradeTarget }
		<%-- <c:if test="${fn:length(grade.target) > 60}">
	       		${fn:substring(grade.target,0,60) }...
	       	</c:if>
	       	<c:if test="${fn:length(grade.target) <= 50}">
	         	${grade.target }
	       	</c:if> --%>
		</p>
		<p><b>班级简介：</b>${grade.gradeDesc }</p>	
	</div>
</div>
<div class="mainnq">
<!-- 左侧 -->

	<div class="xiagnag"></div>
	<ul class="min_left">
    	<li class="min_le_grade4 gonggong"><a href="javascript:getGradeCourseList('')">课程</a></li>
        <li class="min_le_grade1"><a href="javascript:gradeThesisList();">论文</a></li>
        <li class="min_le_grade2"><a href="javascript:gradeExamList();">考试</a></li>
        <li class="min_le_grade3"><a href="javascript:graduate();">进度分析</a></li>
    </ul>

<!-- 右侧 -->
<div id="gradeDiv"> <img src="<%=contextPath%>/static/images/loading.gif" style="display:block; margin:0 auto;" /></div>
</div>

<!-- 考试过渡层 start -->  
<div class="main_kao" id="exam_info">
<div class="kaoT">
	<input type="hidden" id="div_id" value="">
 	<dl class="kaoT_top">
      <dt id="div_name">试卷名称</dt>
      <dd>
          <ul>
              <li>卷面总分：<b class="cheng" id="div_score">100</b><span>分</span></li>
              <li>及格分数：<b class="cheng" id="div_passScore">60</b><span>分</span></li>
              <li>考试时间：<b class="cheng" id="div_testTime">120</b><span>分钟</span></li>
          </ul>
     		<div class="dikao">
             	<a class="tidiao" href="#" onclick="enterGradeExam()"></a>
            <a class="xcizl" href="#" onclick="javascript: examInfoDivHide()"></a>
        </div>
    </dd>
</dl>
<dl class="kqbd">
	<dt><b>考前必读</b></dt>
    <dd>
    	<ul>
        	<li>如果你已经准备好了，就点击“点击进入考试”按钮进入考场，开始计时。</li>
            <li>考试过程中会在试卷的右侧显示倒计时，您可以随时查看考试剩余时间。</li>
            <li>选项前的单选框（<img src="<%=contextPath%>/static/images/jiao1.gif" width="12" height="12" />）会表示该题只能选择一个答案。</li>
            <li>选项前的复选框（<img src="<%=contextPath%>/static/images/jiao2.gif" width="13" height="13" />）表示该题可以选择一个或多个答案。</li>
            <li>当完成试卷后，可以点击“提交试卷”按钮提交试卷。</li>
            <!-- <li>主观题可以根据参考答案自己评分。</li> -->
          </ul>
      </dd>
  </dl>
</div>
</div>
<!-- 考试过渡层 end -->
<!-- 班级考试成绩显示层 start -->
<div class="main_kao" id="exam_score_div">
<div class="kaoT">
</div>
</div>
<!-- 班级考试成绩显示层 end -->
