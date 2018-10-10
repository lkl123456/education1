<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<dl class="min_right" style="width:998px; float:none; margin:20px auto;">
	<dt>
		<ol>
			<li class="right_min_1"><a>信息完善</a></li>
        </ol>
    </dt>
    <dd class="mi_r1" style="height:480px;">
		<ul style="padding-left:270px;">
			<form id="userInfoSave" name="userInfoSaveFrom" method="post" action="<%=contextPath %>/user/saveUserComplete.do">
	            <input name="userId" id="userId" type="hidden"  value="${user.userId}" />
	        	<li><label>用户名：</label>${user.userName}</li>
				<li><label>真实姓名：</label>${user.userRealName}</li>
				<li><label>身份证：</label>
				<input class="input" id="userCard" name="cardNumer" type="text"  value="${user.cardNumer}" onblur="vailCardMail();"  /> <span id="userCardSpan" class="xing"></span>
	            <li><label>移动电话：</label>
	            <input class="input" id="userPhone" name="userPhone" type="text" value="${user.userPhone}" onblur="checkCellPhone();" /> <span id="userPhoneSpan" class="xing"></span>
	            </li><!--
	            <li><label>职级：</label>
	             	<select  id="zhiJi" name="zhiJi">
	            	<c:if test="${ !empty zhijiList }">
	            		<c:forEach items="${ zhijiList }" var="zhiji">
	            			<c:if test="${zhiji.id eq user.zhiJi}">
	            				<option value="${zhiji.id}" selected="selected">${zhiji.name}</option>
	            			</c:if>
	            			<c:if test="${zhiji.id ne user.zhiJi}">
	            				<option value="${zhiji.id}" >${zhiji.name}</option>
	            			</c:if>
	            		</c:forEach>
	            	</c:if>
					</select>
					<input name="rankName" id="rankName" type="hidden"  value="${user.rankName}" />
	             </li>
	             --><li><label>验证码：</label>
	            	<input class="input" id="seccode" type="text" style="width:100px;"/>
					<input class="hqyzm_btn" type="button" value="获取验证码" onclick="javascript:getSeccode(this);" />
	            	<span id="seccodeSpan" class="xing"></span>
	            	<input type="hidden" id="seccodeDate">
	            </li>
	            <li class="mi_li3"><input name="" type="button" value="确 定" onclick="javascript:saveUserInfo('${gradeId }');" style="margin-left:180px;" /> <span>${succ}</span></li>
	            <input id="gradeId" type="hidden"  value="${gradeId}" />
	    	</form>
	    	<form action="<%=contextPath%>/cms/frontLogin.do" method="post" id="loginForm">
	    	</form>
		</ul>
	</dd>
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
     var  userPhone = $("#userPhone").val() ;
     if(checkNum(userPhone)){
		var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|17[0-9]|18[0-9])[0-9]{8}$";
		var re = new RegExp(regu);
		if(!re.test(userPhone)){
		    $("#userPhoneSpan").text(' * 手机号码格式不正确 ！');	
			return ;
		}
		if(userPhone.length > 11){
			 $("#userPhoneSpan").text(' * 手机号码不能超过11位 ！');	
			return ;
		}
	}else{
	    $("#userPhoneSpan").text(' * 手机号码必须为数字！');	
		return ;
	}
	//获取验证码
    var  userPhone = $("#userPhone").val() ;
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

//验证身份证号方法 
	var test=function(idcard){ 
	if(idcard ==null || idcard == '' || idcard == 'undefined'){
		return ' 请输入正确的身份证格式！';
	}
	var Errors=new Array("验证通过","身份证号码位数不对!","身份证号码出生日期超出范围或含有非法字符!","身份证号码校验错误!","身份证地区非法!"); 
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"xinjiang",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
	var idcard,Y,JYM; 
	var S,M; 
	var idcard_array = new Array(); 
	idcard_array = idcard.split(""); 
	if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4]; 
	switch(idcard.length){ 
		case 15: 
			if ((parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
			}else{ 
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
			} 
			if(ereg.test(idcard)) 
				return Errors[0]; 
			else 
				return Errors[2]; 
				break; 
		case 18: 
			if( parseInt(idcard.substr(6,4)) % 4 == 0 || ( parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
			} else{ 
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
			} 
			if(ereg.test(idcard)){ 
				S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
				+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 +
				 (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) 
				+ parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3 ; 
				Y = S % 11; 
				M = "F"; 
				JYM = "10X98765432"; 
				M = JYM.substr(Y,1); 
				if(M == idcard_array[17]) 
					return Errors[0]; 
				else 
					return Errors[3]; 
			}else 
				return Errors[2]; 
				break; 
				default: 
				return Errors[1]; 
				break; 
			} 
		} 
function vailCardMail(){
	var  userCard = $("#userCard").val() ;
     //var re = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
    if(userCard != null && userCard!= '' && userCard != 'undefined'){
    	var te=test(userCard);
	    if(te != '验证通过'){
	       $("#userCardSpan").text(' * '+te);	
		   return false;
	    }else{
	    	var userId=$("#userId").val();
	    	var falg=false; 
	    	$.ajax({
				async:false,
				url :contextPath+"/user/checkCardName.do",
				data:{'userCard':userCard,"userId":userId},
				type:"post", 
				success:function(data){
					if(data=='succ')
						falg=true;
				}
			});
			if(!falg){
				$("#userCardSpan").text(' 该身份证已经注册过了');
	        	return false;
			}else{
				$("#userCardSpan").text('');
	        	return true;
			}
	    }
    }else{
    	$("#userCardSpan").text('');
    	return true;
    }
}

function checkCellPhone(){
	var ok=true;
	//判断手机号码是否唯一
	var userId=$("#userId").val();
	var userPhone = $("#userPhone").val() ;
	//alert(userId);
	if(!vailUserPhone()){
		return false;
	}
	$.ajax({
		async:false,
		url:contextPath+"/user/checkUserPhone.do",
        type:"post",
        data: {"userPhone":userPhone,"userId":userId},
        success: function(data) {
		 if(data=='succ'){
		 	
		 }else{
		 	ok=false;
		 }
        }
    });
    if(!ok){
    	$("#userPhoneSpan").text(' * 该号码已注册 ！');	
    }else{
    	$("#userPhoneSpan").text('');	
    }
}

// 保存个人信息
function saveUserInfo(gradeId){
	//var gradeId=$("#gradeId").val();
	// 邮箱验证
	var  userCard = $("#userCard").val() ;
	if(userCard!=null && userCard !='' && 'undefined'!=userCard){
		if(!vailCardMail()){//验证身份证号是否已经注册过
			return false;
		}
	}else{
		$("#userCardSpan").text('');
	}
	/*var rankName=$("#zhiJi").find("option:selected").text();
	var zhiJi=$("#zhiJi").val();
	$("#rankName").val(rankName);*/
	// 手机号验证
    var  userPhone = $("#userPhone").val() ;
	if(checkNum(userPhone)){
		var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|17[0-9]|18[0-9])[0-9]{8}$";
		var re = new RegExp(regu);
		if(!re.test(userPhone)){
		    $("#userPhoneSpan").text(' * 手机号码格式不正确 ！');	
			return false;
		}else{
		   $("#userPhoneSpan").text('');	
		}
		if(userPhone.length > 11){
			 $("#userPhoneSpan").text(' * 手机号码不能超过11位 ！');	
			return false;
		}
	}else{
	    $("#userPhoneSpan").text(' * 手机号码必须为数字！');	
		return false;
	}
	var ok=true;
	//判断手机号码是否唯一
	var userId=$("#userId").val();
	//alert(userId);
	$.ajax({
		async:false,
		url:contextPath+"/user/checkUserPhone.do",
        type:"post",
        data: {"cellphone":userPhone,"userId":userId},
        success: function(data) {
		 if(data=='succ'){
		 	
		 }else{
		 	ok=false;
		 }
        }
    });
    if(!ok){
    	$("#userPhoneSpan").text(' * 该号码已注册 ！');	
    	return false;
    }
   $("#userPhoneSpan").text('');
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
   // 修改个人信息， 上传头像
   $.ajax({
		 async:false,
		 url:contextPath+"/user/saveUserComplete.do",
         type:"post",
         data: $("#userInfoSave").serialize(),
         success: function(data) {
         	//alert(data);
		  if(data=='succ'){
		  	//alert("请重新登录");
		  	toGradeCenterIndex(gradeId);
		  	//window.location.href="<%=basePath%>index/index.shtml";
		  }else{
		  	alert("修改失败");
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
function vailUserPhone(){
        var  userPhone = $("#userPhone").val() ;
		if(checkNum(userPhone)){
			var regu = "^0{0,1}(13[0-9]|15[0-9]|153|17[0-9]|156|18[0-9])[0-9]{8}$";
			var re = new RegExp(regu);
			if(!re.test(userPhone)){
			    $("#userPhoneSpan").text(' * 手机号码格式不正确 ！');	
				return false;
			}else{
			   $("#userPhoneSpan").text('');	
			}
			if(userPhone.length > 11){
				 $("#userPhoneSpan").text(' * 手机号码不能超过11位 ！');	
				return false;
			}else{
			   $("#userPhoneSpan").text('');	
			}
		}else{
		    $("#userPhoneSpan").text(' * 手机号码必须为数字！');	
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
