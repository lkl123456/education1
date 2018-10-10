<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<form id="formSearch" name="formSearch" method="post" action="<%=contextPath%>/record/recordIndex.do">
<input type="hidden" id="ajaxSubmit" value="gradeListDiv" />
<dl class="min_right">
    <dt>
        <h2>学习档案</h2>
    </dt>
    <dd class="main_r_dd5">
    	<div class="main_top">
       	    <b>	<strong>
       	    		<c:if test="${empty selYear}">
	       	    		<c:if test="${!empty yearsList}">
	               	        <c:forEach items="${yearsList}" var="year" begin="0" end="0">
	               	        	<li><a href="javascript:" onclick="selRecord(${year});">${year}</a></li>
	               	        </c:forEach>
	               	    </c:if>
               	    </c:if>
       	    		<c:if test="${!empty selYear}">
       	    			<li><a href="javascript:" onclick="selRecord(${selYear});">${selYear}</a></li>
               	    </c:if>
				</strong>
  	    		<c:if test="${fn:length(yearsList)>1}" >
          	    	<ul>
          	        <c:forEach items="${yearsList}" var="year"  begin="0">
          	        	<li><a href="javascript:" onclick="selRecord(${year});" >${year}</a></li>
          	        </c:forEach>
          	        </ul>
          	    </c:if>
       	    </b>
       	    <span>年学习档案</span>
            <input name="" type="button"  onClick="javascript:window.print()" value="打印" style="display: none" />
    	</div>
       <div class="huan">
       		<div><b class="lan">个人信息</b></div>
            <table width="100%" border="1" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tian">姓名</td>
                <td>${user.userRealName}</td>
                <td class="tian">性别</td>
                <td><c:if test="${ user.userSex == 1 }">男</c:if><c:if test="${ user.userSex == 2 }">女</c:if></td>
                <c:if test="${empty user.headPhoto }">
                <td width="88" rowspan="3" style="padding:0"><img src="<%=contextPath%>/static/images/userImg.jpg" width="88" height="107" /></td>
                </c:if>
                <c:if test="${!empty user.headPhoto }">
                <td width="88" rowspan="3" style="padding:0"><img src="<%=imgServerPath %>${user.headPhoto }" width="88" height="107" /></td>
                </c:if>
              </tr>
              <tr>
                <td class="tian">电话</td>
                <td>${user.userPhone}</td>
                <!-- 
                <td class="tian">名族</td>
                <td>${ationalityName }</td>
                 -->
                <td class="tian">职级</td>
                <td>${user.rankName}</td>
              </tr>
<!-- 
              <tr>
              </tr>
 -->
            </table>

      </div>
      <div class="huan">
   		<div><b class="lan">学习履历</b></div>
   			<input id="selYear" name="selYear" value="${selYear}" type="hidden" >
            <table width="100%" border="1" cellspacing="0" cellpadding="0">
            	<tr>
                	<td width="534" class="tian">课程名称</td>
                	<!-- 
                    <td width="87" class="tian">学分</td>
                	 -->
                	 <td width="87" class="tian">学时</td>
                    <td class="tian" width="88">选课时间</td>
            	</tr>
            	<c:if test="${ page == null || page.rows== null || page.totalPage == 0 }">
					<tr>
		       			<td colspan="4" class="tdNull">暂无信息</td>
					</tr>
				</c:if>
				<c:if test="${ page != null && page.rows != null && page.totalPage > 0 }">
					<c:forEach items="${page.rows}" var ="map" >
			        	<tr>
		                    <td>${map.courseName}</td>
		                    <td align="center">${map.isOver}</td>
		                    <td><fmt:formatDate value="${map.xkDate}" type="date" pattern="yyyy-MM-dd" /></td>
		                  </tr>  
					</c:forEach>
				</c:if>
           </table>
		</div>
			<div style=" overflow: hidden; padding: 0 12px 0 12px; position: relative; top:-5px">	<com:pageCom /></div>
		<div class="huan">
   			<div><b class="lan">培训班</b></div>
	        <table width="100%" border="1" cellspacing="0" cellpadding="0">
	        	<tr>
		            <td width="375" class="tian">培训名称</td>
		            <td width="160" class="tian">培训时间</td>
		            <td class="tian" width="88">毕业状态</td>
	        	</tr>
		        <c:if test="${recordsList==null|| empty recordsList }">
					<tr>
		       			<td colspan="4" class="tdNull">暂无信息</td>
					</tr>
				</c:if>
				<c:if test="${recordsList != null && !empty recordsList }">
					<c:forEach items="${recordsList}" var ="map" >
					<tr>
			            <td>${map.gradeName}</td>
			            <td align="center" class="liqi"><fmt:formatDate value="${map.startDate}" type="date" pattern="yyyy-MM-dd" /> - <fmt:formatDate value="${map.endDate}" type="date" pattern="yyyy-MM-dd" /></td>
			            <td align="center" class="liqi"><c:if test="${map.isGraduate==1}">已毕业</c:if><c:if test="${map.isGraduate==2}">未毕业</c:if></td>
			          </tr>
					</c:forEach>
				</c:if>
	        </table>

		</div>  
    </dd>
</dl>
</form>

<script type="text/javascript">
$(document).ready(function(){ 
	$('.xxda div').hover(function(){
		$('.xxda ul').show();						  
		},function(){
			$('.xxda ul').hide();
		})
});
</script>

<script type="text/javascript" src="<%=contextPath%>/static/record/record.js"></script>