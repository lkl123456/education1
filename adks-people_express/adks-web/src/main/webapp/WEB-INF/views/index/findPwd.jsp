<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<dl class="min_right" style="width:998px; float:none; margin:20px auto;">
	<dt>
		<ol>
			<li class="right_min_1"><a>重置密码</a></li>
        </ol>
    </dt>
    <dd class="mi_r1" style="height:240px;">
		<ul style="padding-left:270px;">
			<form id="userInfoSave" name="userInfoSaveFrom" method="post" action="<%=contextPath %>/cms/saveUserComplete.do">
				<li><label>真实姓名：</label>
	            <input class="input" id="userRealname" placeholder="请输入您的真实姓名" name="userRealName" type="text" onblur="checkCellPhone();" /> <span id="userRealnameSpan" class="xing"></span>
	            </li>
	            <li><label>移动电话：</label>
	            <input class="input" id="userCellphone" placeholder="请输入您绑定的手机号码" name="userPhone" type="text" onblur="checkCellPhone();" /> <span id="userCellphoneSpan" class="xing"></span>
	            </li>
	            <li><label>验证码：</label>
	            	<input class="input" id="seccode" type="text" style="width:100px;"/>
					<input class="hqyzm_btn" type="button" value="获取验证码" onclick="javascript:getSeccode(this);" />
	            	<span id="seccodeSpan" class="xing"></span>
	            	<input type="hidden" id="seccodeDate">
	            </li>
	            <li class="mi_li3"><input name="" type="button" value="确 定" onclick="javascript:pwdChange();" style="margin-left:0; border:none;" /> <span>${succ}</span></li>
	    	</form>
	    	<form action="<%=contextPath%>/cms/frontLogin.do" method="post" id="loginForm">
	    	</form>
		</ul>
	</dd>
	<input type="hidden" id="orgId" value="${orgId }">
</dl>

<script type="text/javascript">

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
 }

$(document).ready(function(){
	$('.bixu img').click(function(){
		$('.bixu').hide(100);							  
	});
	$('.min_right dt ol li a').click(function(){
		$keyy=$(this).index('.min_right dt ol li a');
		$('.mi_r1 ul').eq($keyy).show();
		$('.mi_r1 ul').eq($keyy).siblings().hide();
		$('.min_right dt ol li').eq($keyy).addClass('right_min_1');
		$('.min_right dt ol li').eq($keyy).siblings().removeClass('right_min_1');
	});

});
var yanzhengma=0;
var wait=60;
var ok=false;//判断验证码是否过时
//获取验证码
function getSeccode(o){
	//验证手机格式
     var  userCellphone = $("#userCellphone").val() ;
     if(checkNum(userCellphone)){
		var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|17[0-9]|18[0-9])[0-9]{8}$";
		var re = new RegExp(regu);
		if(!re.test(userCellphone)){
		    $("#userCellphoneSpan").text(' * 手机号码格式不正确 ！');	
			return ;
		}
		if(userCellphone.length > 11){
			 $("#userCellphoneSpan").text(' * 手机号码不能超过11位 ！');	
			return ;
		}
		var isOk=checkCellPhone();
		if(!isOk){
			return ;
		}
	}else{
	    $("#userCellphoneSpan").text(' * 手机号码必须为数字！');	
		return ;
	}
	//获取验证码
    var  userPhone = $("#userCellphone").val() ;
    $.ajax({
		async:false,
		url :contextPath+"/user/getSeccode.do",
		data:{'userPhone':userPhone},
		type:"post", 
		success:function(data){
			//alert(data);
			yanzhengma=data;
		}
	});
    $("#seccodeSpan").text('');
    time(o);
}
function time(o){
    if (wait == 0) {
        o.removeAttribute("disabled");            
        o.value="获取验证码";
        wait = 60;
        ok=false;
        yanzhengma=0;
    } else {
        o.setAttribute("disabled", true);
        o.value="重新发送(" + wait + ")";
        wait--;
        ok=true;
        setTimeout(function() {
            time(o)
        },1000)
    }
}
function checkCellPhone(){
	
	var okStr="";
	//真实姓名的用户是否存在
	var  userRealname = $("#userRealname").val() ;
	if(userRealname ==null || userRealname == '' || userRealname == 'undefined'){
		$("#userRealnameSpan").text(' * 真实姓名不能为空！');	
		return false;
	}else{
		$("#userRealnameSpan").text('');	
	}
	//判断手机号码是否唯一
	 var  userCellphone = $("#userCellphone").val() ;

	 if(checkNum(userCellphone)){
		var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|17[0-9]|18[0-9])[0-9]{8}$";
		var re = new RegExp(regu);
		if(!re.test(userCellphone)){
		    $("#userCellphoneSpan").text(' * 手机号码格式不正确 ！');	
			return false;
		}
		if(userCellphone.length > 11){
			 $("#userCellphoneSpan").text(' * 手机号码不能超过11位 ！');	
			return false;
		}
	}else{
		$("#userCellphoneSpan").text(' * 手机号码必须为数字！');	
		return false;
	}
	//alert(userId);
	$.ajax({
		async:false,
		url:contextPath+"/index/checkUserCellPhoneIndex.do",
        type:"post",
        data: {"cellphone":userCellphone,"realname":userRealname},
        success: function(data) {
		 	okStr=data;
        }
    });
    if(okStr == "succ"){
    	$("#userCellphoneSpan").text('');
    	return true;	
    }else if(okStr == "nouser"){
    	$("#userCellphoneSpan").text('* 该号码没有关联用户 ！');
    	return false;	
    }else if( okStr == "nocheckUser"){
    	$("#userCellphoneSpan").text('* 该号码没有与真实姓名相关联 ！');
    	return false;	
    }
}

//密码重置
function pwdChange(){
	var okStr="";
	//真实姓名的用户是否存在
	var  userRealname = $("#userRealname").val() ;
	if(userRealname ==null || userRealname == '' || userRealname == 'undefined'){
		$("#userRealnameSpan").text(' * 真实姓名不能为空！');	
		return false;
	}else{
		$("#userRealnameSpan").text('');	
	}
	//判断手机号码是否唯一
	 var  userCellphone = $("#userCellphone").val() ;

	 if(checkNum(userCellphone)){
		var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|17[0-9]|18[0-9])[0-9]{8}$";
		var re = new RegExp(regu);
		if(!re.test(userCellphone)){
		    $("#userCellphoneSpan").text(' * 手机号码格式不正确 ！');	
			return false;
		}
		if(userCellphone.length > 11){
			 $("#userCellphoneSpan").text(' * 手机号码不能超过11位 ！');	
			return false;
		}
	}else{
		$("#userCellphoneSpan").text(' * 手机号码必须为数字！');	
		return false;
	}
	//alert(userId);
	$.ajax({
		async:false,
		url:contextPath+"/index/checkUserCellPhoneIndex.do",
        type:"post",
        data: {"cellphone":userCellphone,"realname":userRealname},
        success: function(data) {
		 	okStr=data;
        }
    });
    if(okStr == "succ"){
    	$("#userCellphoneSpan").text('');
    }else if(okStr == "nouser"){
    	$("#userCellphoneSpan").text('* 该号码没有关联用户 ！');
    	return false;	
    }else if( okStr == "nocheckUser"){
    	$("#userCellphoneSpan").text('* 该号码没有与真实姓名相关联 ！');
    	return false;	
    }
    
   //获取验证码
  if(yanzhengma ==0){
   		$("#seccodeSpan").text(' * 请获取验证码！');
   		return false;
   }
   var seccode=$("#seccode").val();
   if(seccode ==null || seccode == 'undefined'|| seccode ==''){
   		$("#seccodeSpan").text(' * 请输入验证码！');
   		return false;
   }else if(seccode != yanzhengma){
   		$("#seccodeSpan").text(' * 验证码有误！');
   		return false;
   }
   var orgId=$("#orgId").val();
   // 修改密码
   $.ajax({
		 async:false,
		 url:contextPath+"/index/changPwd.do",
         type:"post",
         data: {"userCellphone":userCellphone},
         success: function(data) {
         	//alert(data);
		  if(data=='succ'){
		  	alert("密码重置成功，为111111");
		  	window.location.href=contextPath+'/user/toLogin/'+orgId+'.shtml';
		  }else{
		  	alert("重置失败");
		  }
         }
     });
     
	yanzhengma=0;
	ok=false; 
}

//进入培训班信息
function toGradeCenterIndex(gradeId){
	var url = contextPath+"/grade/toGradeCenterIndex.do?gradeId="+gradeId;
	window.location.href=url;
}

$(document).ready(function(){ 
	if($("#changeflag").val()=="changepass"){
		$('.min_right dt ol li a').eq(1).trigger('click'); 
	}
});
// 手机号码验证
function vailUserCellphone(){
        var  userCellphone = $("#userCellphone").val() ;
		if(checkNum(userCellphone)){
			var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|18[0-9])[0-9]{8}$";
			var re = new RegExp(regu);
			if(!re.test(userCellphone)){
			    $("#userCellphoneSpan").text(' * 手机号码格式不正确 ！');	
				return false;
			}else{
			   $("#userCellphoneSpan").text('');	
			}
			if(userCellphone.length > 11){
				 $("#userCellphoneSpan").text(' * 手机号码不能超过11位 ！');	
				return false;
			}else{
			   $("#userCellphoneSpan").text('');	
			}
		}else{
		    $("#userCellphoneSpan").text(' * 手机号码必须为数字！');	
			return false;
		}
}

/*是否为整数*/
function checkNum(num){
	//var re = /^[1-9]\d*$/;
	var re =  /^[-]{0,1}[0-9]{1,}$/;
	if (!re.test(num)){
        return false;
    }else{
    	return true;
    }
}

</script>
