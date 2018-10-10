<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>

<div class="mainnq">
	<div class="xiagnag"></div>
	<ul class="min_left">
    	<div class="min_tou">
        	<img class="tu_tou" src="<%=contextPath%>/static/images/tgou.gif" width="144" height="18" />
        	<c:choose>
            	<c:when test="${ sessionScope.user.headPhoto == null || sessionScope.user.headPhoto == ''}">
            		<a  href="javascript:userInfo('person');" title="点击进入个人设置"> <img id="headPicpath"  src="<%=contextPath%>/static/images/userImg.jpg" width="62" height="62" /></a>
            	</c:when>
            	<c:otherwise>
            		<a  href="javascript:userInfo('person');" title="点击进入个人设置"> <img id="headPicpath" src="<%=imgServerPath%>${sessionScope.user.headPhoto}" width="62" height="62" /></a>
            	</c:otherwise>
            </c:choose>
            <p>${sessionScope.user.userRealName}，你好</p>
            <div><a href="javascript:userInfo('person');" title="点击进入个人设置"">个人信息</a> | <a href="javascript:userInfo('changepass');"  title="点击进入修改密码">修改密码</a><!-- | <a href="javascript:loginOut();"  title="点击退出登录">退出</a>--></div>
            <span title=" ${sessionScope.user.orgName}">单位： ${sessionScope.user.orgName}</span>
        </div>
    	<li class="min_le_1 gonggong"><a href="javascript:gradeCurrentList()">我的培训班</a></li>
        <li class="min_le_2"><a href="javascript:getUserCourseViewList();">观看记录</a></li>
        <li class="min_le_3"><a href="javascript:myCollection();">我的收藏</a></li>
        <li class="min_le_4"><a href="javascript:toRecord();">学习档案</a></li>
    </ul>
    <td><input type="hidden" id="flag_record" value="${flag}" /> </td>
    <div id="gradeListDiv">
    	<div style="width:750px; text-align:center; float:right;"><img src="<%=contextPath%>/static/images/loading.gif" /></div>
    </div>
</div>


<%@ include file="../../static/common/script.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/center/center_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/center/user.js"></script>

	<script type="text/javascript">
		$(document).ready(function(){ 
			var flag = $("#flag_record").val();
			if(null != flag && flag == "1"){
				$('.min_left li').removeClass('gonggong');
				$('.min_left .min_le_4').addClass('gonggong');
				toRecord();
			}else{
				gradeCurrentList();
				$('.min_left li').click(function(){
			        for(var $i=0;$i<4;$i++){
				        $('.min_left li').eq($i).removeClass('gonggong');
				    };
		        $keyn=$(this).index('.min_left li');
		        $('.min_left li').eq($keyn).addClass('gonggong');
				})
			}
　　		});

</script>