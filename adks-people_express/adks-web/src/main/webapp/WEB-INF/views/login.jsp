<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>登录 - 中共民航局党校</title>
	 <script type="text/javascript" src="./js/des.js"></script>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery1.4.2.js"></script>
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
	 <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/login.js"></script>
	 <style>
    input:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px #fff inset !important;
        -webkit-text-fill-color: #333!important;
    }

</style>
</head>
	<c:choose>
	<c:when test="${empty sessionScope.user}">
<body class="body1" onload="logininit();">
	<input type="hidden" id="orgId" value="${orgId }"/>
    <div class="top">
    	<a href="<%=contextPath %>/index/index/${orgId }.shtml">
    	<c:if test="${orgConfig!=null && orgConfig.orgLogoPath!=null}">
				<img src="<%=imgServerPath %>${orgConfig.orgLogoPath }" width="316" height="52"  /></a>
		</c:if>
		<c:if test="${orgConfig==null || orgConfig.orgLogoPath==null}">
				<img src="<%=contextPath%>/static/images/index_6.gif" width="316" height="52"  /></a>
		</c:if>
	    <div>登录</div>
	</div>
    <div class="mainn">
    	<img class="zuo" src="<%=contextPath%>/static/images/dl1.gif" width="587" height="421" />
        <form id="loginForm" action="<%=contextPath%>/user/toUserLogin.do" method="post" onsubmit="return onlogin();">
    		<input type="hidden" name="loginFlag" value="${loginFlag }"/>
    		<input type="hidden" name="loginFrom" value="login"/>
    		<dl style="background:#fff;">
				<dt>用户登录</dt>
		        <dd>
		        	<ul>
		        		<%
							//生成随机类
							Random random = new Random();
							String sRand = "";
							for (int i=0;i<4;i++){
								String rand=String.valueOf(random.nextInt(10));
								sRand += rand;
							}
							//sRand="2017";
						%>
		            	<li><i></i><input class="inp_3" type="text" placeholder="用户名或绑定的手机号码" name="userNameTemp"  id="userNameTemp" value="${userName }" maxlength="20"/></li>
		                <li><input class="inp_4" type="password" placeholder="密码" name="passWordTemp" id="passWordTemp"  maxlength="20"/></li>
		                <li class="dli">
			                <input class="inp_5" type="text" placeholder="验证码" name="randNum" id="randNum" maxlength="4"/>
			                <img id="checkcode" width="113" height="40" src="${pageContext.request.contextPath}/common/image.jsp?sRand=<%=sRand%>" onclick="javascript: getYanZhengMa('${pageContext.request.contextPath}');"  /> 
			                <input type="hidden" id="sRandNum" name="sRandNum" value="<%=sRand%>"/>
		                </li>
		                <li class="dli1"><input name="" type="submit" value="登 录" /></li>
		                <li class="dli1"><a href="<%=contextPath%>/index/findPwd/${orgId }.shtml" style="display:inline-block; margin-bottom:10px; width:100%; height:38px; border:1px solid #ddd; line-height:38px; text-align:center; border-radius:4px; ">重置密码</a></li>
		                <input type="hidden" name="userName" id="userName" />
		                <input type="hidden" name="passWord" id="passWord" />
		                
		                <span class="mesg" style="color:red;">${message}</span>
		                
		            </ul>
		        </dd>
		    </dl>
        </form>
    </div>
    <div class="footer" id="footer">
		<script type="text/javascript">
			var orgId=$("#orgId").val();
		     $(document).ready(function(){
			   	 $.ajax({
					async:false,
					url:'<%=contextPath%>/index/footer.do?orgId='+orgId,
						type:"post", 
						success:function(data){
							$("#footer").html(data);
						}
					});
			   }); 
	   </script>
	</div>
</body>
 <%@ include file="../static/common/script.jsp"%>
<script type="text/javascript" >
	function findPwd(){
		
	}
</script>
    </c:when>
    <c:otherwise>
		<jsp:forward page="/index/index/1.shtml"/>
    </c:otherwise>
	</c:choose>
</html>
