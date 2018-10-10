<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%@ include file="../../static/common/script.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/static/css/authorInfo.css" />
<style stype="text/css">
	form{padding:0; margin:0;}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/author/author.js"></script>
<form  id="formSearch" name="formSearch" method="post"
	action="<%=contextPath%>/author/toAuthorInfo.do">
	<input type="hidden" id="ajaxSubmit" value="mainIndex" />
	<input type="hidden" name="authorId" id="authorId" value="${author.authorId }" />
	<div class="jiansi">
    	<div class="jian_atk">
        	<img src="<%=imgServerPath %>/${author.authorPhoto}" width="137" height="137" />
            <p>${author.authorName }</p>
            <div><span>${author.authorDes }</span><a>展开>></a><b>收起<<</b></div>
        </div>
        <div class="miniin">
        	<dl class="minini_l">
            	<dt><h2>他的课程</h2></dt>	
                <dd>
                	<ul>
                	<c:if  test="${empty authorCourseList }">
                        	<br/>该讲师暂无课程
         			 </c:if>
         			<c:if test="${! empty authorCourseList }">
         				<c:forEach items="${authorCourseList}" var="map">
	                    	<li>
	                        	<a  class="jias" href="#" onclick="BeginStudy('${map.courseId}','${map.courseCode}','${map.courseType}','${map.courseName}');"><img src="<%=imgServerPath %>/${map.coursePic }" width="150" height="100" /></a>
	                            <div>
	                            	<h2><a href="#" onclick="BeginStudy('${map.courseId}','${map.courseCode}','${map.courseType}','${map.courseName}');">${map.courseName }</a></h2>
	                                <p>讲　　师：${map.authorName }　　课件类型：<c:if test="${map.courseType==1}">scorm课件</c:if><c:if test="${map.courseType==2}">普通视频</c:if>　　课程分类：${map.courseSortName}　　发布时间：<fmt:formatDate value="${map.createTime}"  type="date" pattern="yyyy-MM-dd" /></p>
	                                <p title="${map.courseDes }">简介：<c:if test="${fn:length(map.courseDes)>100}">
	                    					${fn:substring(map.courseDes,0,100)}... </c:if></p>
	                          </div>
	                        </li>
         				</c:forEach>
         			</c:if>
                    </ul>
                </dd>
                
            </dl>
            <dl class="minini_r">
            	<dt>热门讲师</dt>
                <dd>
                	<ol>
                	<c:forEach items="${topHotAuthorList}" var="author" varStatus="authorStatus">
                    	<li><a href="<%=contextPath%>/author/authorInfo/${author.authorId}/${orgId }.shtml" class="nobody"><img src="<%=imgServerPath %>/${author.authorPhoto}" width="54" height="54" />
                        		<span <c:if test="${authorStatus.index < 3 }">class="bianel"</c:if>>${authorStatus.index+1 }</span>
                        	</a>
                            <div>
                           	  	<h2><a href="#">${author.authorName }</a></h2>
                                <p></p>
                          </div>
                   	  	</li>
                	</c:forEach>
                    </ol>
                </dd>
            </dl>
        </div>
        <%-- <input type="hidden" id="userId" value="${user.id}" /> --%>
    	<input type="hidden" id="sessionId" value="<%=session.getId()%>" />
    </div>
</form>