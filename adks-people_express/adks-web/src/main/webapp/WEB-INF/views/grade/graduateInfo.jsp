<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/static/grade/js.js"></script>
<dl class="min_right min_rightq">
	<dl class="main_l main_l1">
		<dt>
		     <ol>
		         <li id="qb"><b class="c_title">进度分析</b></li>
		     </ol>
		</dt>
    </dl>
	<form id="formSearch" name="formSearch" method="post" action="<%=contextPath%>/grade/graduateInfo.do" >
			
			<div class="jieye" id="jieye_11">
    	<div class="jie_ye1">
     		<c:if test="${map.userGraduate < map.graduate}">
	        	<img src="<%=contextPath%>/static/images/jieye_n.png" width="150" height="103" />
	            <div>
	     			 尚未完成
	     		</div>
     		</c:if>       
     		<c:if test="${map.userGraduate >= map.graduate}">
	        	<img src="<%=contextPath%>/static/images/jieye_m.png" width="150" height="103" />
	            <div>
	     			 已完成
	           </div>
     		</c:if>       
        </div>
        <div class="jychaxun"><a href="#" onclick="showInfo()">结业情况查询</a></div>
        <div class="jytj"><span>结业条件：</span></div>
    	</div>
			 <div class="jieye" style="display: none" id="jieye_22">
    	<div class="gang_1"></div>
        <div class="jie_ye1">
        	<c:if test="${map.userGraduate < map.graduate}">
	        	<img src="<%=contextPath%>/static/images/jieye_n.png" width="150" height="103" />
	            <div>尚未完成</div>
        	</c:if>
        	<c:if test="${map.userGraduate >= map.graduate}">
        		<img src="<%=contextPath%>/static/images/jieye_m.png" width="150" height="103" />
	            <div>已完成</div>
        	</c:if>
        </div>
        <ol>
        	<li>
        	<c:if test="${map.userCourseStudyState == 2}">
                <img src="<%=contextPath%>/static/images/jieye_b.png"  width="27" height="27" />
        	</c:if>
                <div>
                	<c:if test="${grade.requiredPeriod > 0}">
	                	<p>必修考核<b class="cheng">${grade.requiredPeriod}</b>个学时</p>
	                	
	                    <span <c:if test="${map.bixiuPerioSucc == 0}">class="ol_p"</c:if>>已完成${map.userbixiuPerio}个学时</span>
                	</c:if>
                	<c:if test="${grade.optionalPeriod > 0}">
	                    <p>选修考核<b class="cheng">${grade.optionalPeriod }</b>个学时</p>
	                    <span <c:if test="${map.xuanxiuPerioSucc == 0}">class="ol_p"</c:if>>已完成${map.userxuanxiuPerio }个学时</span>
                	</c:if>
                </div>
            </li>
            <li class="ol_li_1">
            <c:if test="${map.examCount - map.examUserCount == 0}">
                <img src="<%=contextPath%>/static/images/jieye_b.png"  width="27" height="27" />
        	</c:if>
                <div>
                	<p>共<b class="cheng">${map.examCount }</b>个考试</p>
                	<c:choose>
                		<c:when test="${map.examCount - map.examUserCount == 0}">
	                    <span>已完成</span>
                		</c:when>
                		<c:otherwise>
	                    <span class="ol_p">还剩${map.examCount - map.examUserCount}个考试</span>
                		</c:otherwise>
                	</c:choose>
                </div>
            </li>
        </ol>
        <ol class="li_ol_1">
        	<li class="ol_li_2">
        		<c:if test="${map.workCount - map.workUserCount == 0}">
                <img src="<%=contextPath%>/center/grade/images/jieye_b.png"  width="27" height="27" />
               	</c:if>
                <div>
                	<p>共<b class="cheng">${map.workCount }</b>个论文</p>
                	<c:choose>
                		<c:when test="${map.workCount - map.workUserCount == 0 }">
                			<span>已完成</span>
                		</c:when>
                		<c:otherwise>
		                    <span class="ol_p">还剩${map.workCount- map.workUserCount }个论文</span>
                		</c:otherwise>
                	</c:choose>
                </div>
            </li>
        </ol>
        <c:if test="${map.userGraduate < map.graduate}">
        <div class="jychaxun">您目前尚未完成，请再接再厉哟！</div>
        </c:if>
        <c:if test="${map.userGraduate >= map.graduate}">
        <div class="jychaxun">恭喜!您已经完成培训任务！</div>
        </c:if>
        <%-- <div class="jytj"><span>学习进度：</span>${grade.trainingRequired }</div> --%>
        <div class="jytj"><span>学习进度：</span>
        	<c:if test="${grade.requiredPeriod > 0}">必修考核：${map.userbixiuPerio}/${grade.requiredPeriod};</c:if>
       		<c:if test="${grade.optionalPeriod > 0}">选修考核：${map.userxuanxiuPerio}/${grade.optionalPeriod };</c:if>
       		<c:if test="${map.examCount == 1}">考试考核：${map.examUserCount}/${map.examCount};</c:if>
       		<c:if test="${map.workCount == 1}">论文考核：${map.workUserCount }/${map.workCount };</c:if>
        </div>
    </div>
		
	</form>
	</dl>