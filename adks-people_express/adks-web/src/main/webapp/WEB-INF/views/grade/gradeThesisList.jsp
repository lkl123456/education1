<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<dl class="min_right min_rightq">
 <form id="formSearch" name="formSearch" method="post" action="<%=contextPath%>/grade/gradeThesisList.do" >
	<input type="hidden" id="ajaxSubmit" value="gradeDiv"/>
	<input type="hidden" id="gradeId" name="gradeId" value="${gradeId }"/>
	<input type="hidden" id="currentPageFlag" name="currentPage" value="${page.currentPage }"/>
    <div class="main">
  	<dl class="main_l main_l1">
		<dt>
		     <ol>
		         <li id="qb"><b class="c_title">论文</b></li>
		     </ol>
		</dt>
    </dl>
     <dl class="main_r">
        	<c:if test="${ page != null && page.rows != null && page.totalPage > 0 }">
				<c:forEach var="map" items="${page.rows}">        	
		            <dd class="main_r_dd2a">
		            <c:choose>
		            	<c:when test="${map.isEnd == 3}">
		            		<p class="lan"><a class="lan" href="#" onclick="gradeThesisInfo(${map.gradeWorkId},${gradeId},'onlySee')">${map.workTitle }</a></p>
						</c:when>
		            	<c:otherwise>
		            		<p class="lan"><a class="lan" href="#" onclick="gradeThesisInfo(${map.gradeWorkId},${gradeId})">${map.workTitle }</a></p>
		            	</c:otherwise>
		            </c:choose>
		                <div  class="hui">
		                	开放时间：<fmt:formatDate value="${map.startDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>--<fmt:formatDate value="${map.endDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>　　　　　<span class="hui">状态：</span>
		                		<c:choose>
		                			<c:when test="${map.isOver == 1}">
		                				未开始
		                			</c:when>
									<c:when test="${map.isOver == 2}">
										已结束
									</c:when>
									<c:when test="${map.isOver == 3}">
										进行中	
									</c:when>
		                		</c:choose>
		                		</div>
					<c:choose>
						<c:when test="${map.isEnd == 3}">
			                <a class="kaoshi_1 kaoshi_2" href="#" onclick="gradeThesisInfo(${map.gradeWorkId},${gradeId},'onlySee')">查看论文</a>
						</c:when>
						<c:otherwise>
			                <a class="kaoshi_1 kaoshi_2" href="#" onclick="gradeThesisInfo(${map.gradeWorkId},${gradeId})">撰写论文</a>
						</c:otherwise>
					</c:choose>
					</dd>
        		</c:forEach>
        	</c:if>
        	<div><com:pageCom /></div>
        </dl>
    </div>
	</form>
	</dl>