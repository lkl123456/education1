<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
	<form id="formSearch" name="formSearch" method="post"  action="<%=contextPath %>/grade/gradeCurrentList.do"  >
		<input type="hidden" id="ajaxSubmit" value="gradeListDiv"/>
		<dl class="min_right min_rightq">
            <dt>
                <ol>
                    <li class="right_min_1"><a href="javascript:">当前培训班<b>(${page.total})</b></a><span></span></li>
                    <li><a href="javascript:" onclick="gradeOverList()">历史培训班<b></b></a></li>
            	</ol>
            </dt>
        	<dd class="mi_r3">
            	<ul>
            		<c:if var="current" test="${ page == null || page.rows== null || page.totalPage == 0 }">
                	<li>
                        暂无当前培训班信息
                    </li>
                    </c:if>
                    <c:if test="${ page != null && page.rows != null && page.totalPage > 0 }"> 
                    <c:forEach  items="${page.rows}" var ="map" >
                    <li>
                    	<c:choose>
                      		<c:when test="${map.gradeImg == null || map.gradeImg == ''}">                        		
                      			<img src="<%=contextPath %>/static/images/gradeLogoImg.jpg" width="71" height="71"  />
                      		</c:when>
                      		<c:otherwise>
                      			<img src="<%=imgServerPath %>/${map.gradeImg}" width="71" height="71"  />
                      		</c:otherwise>
                      	</c:choose>
                        <p><a href="#" onclick="completeUserData('${user.userId }','${map.gradeId }')" class="lan"><c:out value="${map.gradeName}" /></a></p>
                        <div>
                        	组织单位：${map.orgName }<br />
							班级成员：<c:out value="${map.userNum}" />人　　　　　培训时间：<fmt:formatDate value="${map.startDate}" pattern="yyyy.MM.dd"/> - <fmt:formatDate value="${map.endDate}" pattern="yyyy.MM.dd"/>
                        </div>
                        <!-- 
                        <a href="#" onclick="gradeIndex(${map.id})" class="xuxi_1">开始培训</a>
                         -->
                         <a href="#" onclick="completeUserData('${user.userId }','${map.gradeId }')" class="xuxi_1">开始培训</a>
                    </li>
                    </c:forEach>
                    </c:if> 
              </ul>
               <com:pageCom />
          	</dd>
        </dl>
	</form>
    
<script type="text/javascript">
	function completeUserData(userId,gradeId){
		if(userId ==null || userId ==''){
			alert("请先登录...");
		}else{
			$.ajax({
				async:true,
				url:contextPath+"/user/checkData.do",
				type:"post",
				success:function(data){
					//alert(data);
					if(data == 'succ'){
						toGradeCenterIndex(gradeId);
					}else if(data == 'nouser'){
						alert("请先登录...");
					}else if(data == 'error'){
						//完善信息
						completeData(gradeId);
					}
				}
			});
		}
	}
	//进入培训班信息
	function toGradeCenterIndex(gradeId){
		var url = contextPath+"/grade/toGradeCenterIndex.do?gradeId="+gradeId;
		window.location.href=url;
	}
	function completeData(gradeId){
		$.ajax({
			async:true,
			url:contextPath+"/user/completeData.do",
			data:{"gradeId":gradeId},
			type:"post",
			success:function(data){
				$("#mainIndex").html(data);
			}
		});
	}
</script>