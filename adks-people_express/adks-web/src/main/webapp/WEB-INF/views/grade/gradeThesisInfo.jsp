<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<dl class="min_right min_rightq">
	<dl class="main_l main_l1">
		<dt>
		     <ol>
		         <li id="qb"><b class="c_title">论文</b></li>
		     </ol>
		</dt>
    </dl>
	<form id="gradeThesisFrom" name="gradeThesisFrom" method="post" action="<%=contextPath %>/grade/gradeThesisSave.do" enctype="multipart/form-data">
		<input type="hidden" id="leastSize" value="${schoolWork.leastSize }"/>
		<input type="hidden" id="maxSize" value="${schoolWork.maxSize }"/>
		<input type="hidden" name="gradeWorkReplyId" value="${schoolWork.gradeWorkReplyId }"/>
		<input type="hidden" name="gradeWorkId" value="${schoolWork.gradeWorkId }"/>
		<input type="hidden" name="workTitle" value="${schoolWork.workTitle }"/>
		<input type="hidden" name="submitFilePath" value="${schoolWork.submitFilePath }"/>
		<div class="main main_m1">
			<div class="top_con">
		        <div class="xiao_biao">${schoolWork.workTitle }</div>
		        <div class="faqi_tiame">
		           	 <p><span>发起时间：</span><fmt:formatDate value="${schoolWork.startDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;<span>结束时间：</span><fmt:formatDate value="${schoolWork.endDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		          	 <p><span>讨论内容：</span>${schoolWork.workContent }</p>
		        </div>
		        <div class="fa_bu">
	        		<c:if test="${schoolWork.allowFile !=null && schoolWork.allowFile == 1}">
		        		<p><span>提交附件：</span><input type="file" name="fileSchoolWork"/>&nbsp;&nbsp;
		        			<c:if test="${!empty schoolWork.submitFilePath  && schoolWork.submitFilePath != ''}">
		        			<a href="#" onclick="javascript:window.open('<%=imgServerPath %>${schoolWork.submitFilePath }')">学生论文已提交附件</a>
		        			</c:if>
	        			</p>
	        		</c:if>
	        		<c:if test="${showPath != null  }">
			         	<p><span>论文附件：</span><a href="#" onclick="javascript:window.open('<%=imgServerPath %>${schoolWork.filePath }')">${schoolWork.workTitle }-附件</a></p> 
		        	</c:if>
	        		<c:if test="${schoolWork.leastSize != null && schoolWork.maxSize != null}">
	        			<p><span>温馨提示：</span>论文字数限制在：${schoolWork.leastSize } 到 ${schoolWork.maxSize }
	        			</p>
	        		</c:if>
	        		<c:if test="${schoolWork.maxSize != null && (schoolWork.leastSize == null||schoolWork.leastSize == 0)}">
	        			<p><span>温馨提示：</span>论文字数不能超过 ${schoolWork.maxSize }
	        			</p>
	        		</c:if>
	        		<c:if test="${schoolWork.leastSize != null && (schoolWork.maxSize == null||schoolWork.maxSize == 0)}">
	        			<p><span>温馨提示：</span>论文字数不能少于 ${schoolWork.leastSize }
	        			</p>
	        		</c:if>
	                <dl>
	                <span style="float:left">论文概述：</span>
	                <div style="float:left; width:650px;">
	            	<c:choose>
	                	<c:when test="${fullVersion != null && (fullVersion == 6 || fullVersion == 7)}">
	                    	<textarea name="workAnswer" id="contentKd" style="width:640px;height:150px;" >${content }</textarea>
				    	</c:when>
	                	<c:otherwise>
	                		<textarea name="workAnswer" id="contentKd" style="width:640px;height:150px;" >${content }</textarea>
	                	</c:otherwise>
	                  </c:choose>
	                  </div>
	                  
	            </div>
	            
	            <div class="tian_b">
	            	<c:if test="${empty onlySee}">
	            		<input class="new_inpu_1" id="sbbutton" name="" value="提交" type="button" onclick="gradeThesisSave();" />
	            	</c:if>
	            	<input name="" class="new_inpu_2" value="返回" type="button" onclick="gradeThesisList_page();" />
	            </div>
	        </div>
	        </div>
</form>
		<input type="hidden" id="ajaxSubmit" value="gradeListDiv"/>	
		<input type="hidden" id="endDate" name="endDate" value="${endDate}"/>
		<input type="hidden" id="startDate" name="startDate" value="${startDate}"/>
		<input type="hidden" id="nowDate" name="nowDate" value="${nowDate}"/>
		<input type="hidden" id="currentPageFlag" name="currentPage" value="${currentPageFlag}"/> 
		<input type="hidden" id="fullVersion" name = "fullVersion" value="${fullVersion }" />
		<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}"/>
		<input type="hidden" id="gradeName" name="gradeName" value="${gradeName}"/> 
</dl>