
//去掉字符串头尾空格
function trimStr(str) {
return str.replace(/(^\s*)|(\s*$)/g, "");
} 

//点击 获取验证码
var codeLength = 6; //验证码长度
var count = 60;//间隔秒数  60秒
var curCount; //当前剩余秒数
function getMobileCheckCode(selector){
	var code = ""; //验证码内容
	var cellPhone = $("#cellPhone").val();
	if(selector == 'regist'){
		
		obj = vailCellPhone();
		if(!obj.result){
			return false;
		}
	}else{
		obj = vailCellPhone_findPsd();
		if(!obj.result){
			return false;
		}
	}
	curCount = count;
	//产生验证码
	for(var i = 0; i < codeLength; i++){
		code = code + parseInt((Math.random() * 9).toString());
	}
	$("#hiddenMobileCheckCode").val(code); //把随机验证码放到  隐藏域中存在页面
	//设置button效果，开始计时
	$("#mobileBtn").attr("disabled","true");
	$("#mobileBtn").val("重新发送(" + curCount + ")");
	InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次

	//ajax请求服务端，发送短信验证码，同时返回该验证码
	$.ajax({
		async:false,
		dataType:"text",
		url:contextPath+"/user/sendMobileCode.do",
		data:"code="+code+"&cellPhone="+cellPhone,
		type:"post", 
		success:function(data){
			if(data == 'yes'){
			}
		}
	});
	//timer处理函数
	function SetRemainTime() {
        if (curCount == 0) { 
            window.clearInterval(InterValObj);//停止计时器
            $("#mobileBtn").removeAttr("disabled");//启用按钮
            $("#mobileBtn").val("重新发送验证码");
            code = "N"; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
            $("#hiddenMobileCheckCode").val(code);
        }
        else {
            curCount--;
            $("#mobileBtn").val("重新发送(" + curCount + ")");
        }
	 }
}


/**
 * 找回密码页面，提交手机号码和验证码
 * @return
 */
function submitFindPsd(){
	var cellPhone = $("#cellPhone").val();
//	alert(cellPhone);
	//var mobileCheckCode = $("#mobileCheckCode").val();
	//校验手机号码
	obj = vailCellPhone_findPsd();
	if(!obj.result){
		return false;
	}
	
	//短信验证码
	var mobileCheckCode = $("#mobileCheckCode").val();
	if(mobileCheckCode == ''){
		$("#msg").text('请输入短信验证码！');
		document.getElementById("mobileCheckCode").focus();
		return false; //至于短信验证码输入是否正确， ,操作在服务端去，逻辑写在这里
	}else{
		var theTrueCode = $("#hiddenMobileCheckCode").val(); //发送验证码的时候，存放的验证码值
//		alert(theTrueCode);
		if(theTrueCode == 'N'){
			//验证码被清，说明已过期
			$("#msg").text('验证码已过期!请重新获取！');
			document.getElementById("mobileCheckCode").value = '';
			document.getElementById("mobileCheckCode").focus();
			return false;
		}else if(mobileCheckCode != theTrueCode || theTrueCode == ''){
			$("#msg").text('短信验证码错误！');
			document.getElementById("mobileCheckCode").value = '';
			document.getElementById("mobileCheckCode").focus();
			return false;
		}
		
	}
	$("#msg").text = '';
	window.location.href=contextPath + "/api/adks/toResetPwdYz.do?cellPhone="+cellPhone;
	
}


/**
 * 校验手机号码 =-=== 找回密码使用
 * @return
 */
function vailCellPhone_findPsd(){
	var cellPhone = trimStr($("#cellPhone").val());
	if(cellPhone == ''){
		$("#msg").text('请输入手机号码！');
		$("#cellPhone").focus();
		return false;
	}else{
		var pattern = /^1[34578]\d{9}$/;  
	    if (pattern.test(cellPhone)) {
	    	
	    	//校验手机号码是否是注册用户，不是，则不可提交。是，查询该注册用户，返回用户信息再下个页面显示给用户
	    	var isRegisted = false;
	    	$.ajax({
	    		async:false,
	    		url : contextPath+"/user/vailCellPhone.do",
	    		data:{'cellPhone':cellPhone},
	    		type:"post", 
	    		success:function(data){
	    			if(data == 'no'){
	    				isRegisted = true; //该手机号码，已注册过，正常
	    			}
	    		}
	    	});
	    	if(isRegisted){
	    		$("#msg").text('');
	    		return {'result':true};
	    		
	    	}else{
	    		$("#msg").text('该手机号码尚未注册使用！');
	    		return false;
	    	}
	    	
	        $("#msg").text('');
	        return {'result':true};
	    }else{
	    	$("#msg").text('手机号码格式不正确！');
	    	return false;
	    } 
	    
	    
	}
}

//重置密码  提交操作
function resetPass(){
	var cellPhone = $("#cellPhone").val();
	var newPass = $("#newPass").val(); //新密码，用户输入的
	var reNewPass = $("#reNewPass").val(); //再次确认，用户输入的

	if(newPass == ''){
		$("#msg").text('请输入新密码！');
		$("#newPass").focus();
		return false;
	}else if(newPass.length < 6){
		$("#msg").text('密码长度不能小于6位！');
		$("#newPass").focus();
		return false;
	}
	
	if(reNewPass == ''){
		$("#msg").text('请确认新密码！');
		$("#reNewPass").focus();
		return false;
	}
	
	if(reNewPass != newPass){
		$("#msg").text('两次输入的密码不一致！');
		return false;
	}
	$("#msg").text('');
	//window.location.href=contextPath + "/user/resetPassWord.do?newPass="+newPass+"&cellPhone="+cellPhone;
	$("#resetPassForm").submit();
}


//校验手机号码（非空、唯一性）
function vailCellPhone(){
	var cellPhone = trimStr($("#cellPhone").val());
	if(cellPhone == ''){
		$('#needCellPhoneImg').attr('src',contextPath + '/images/shequ38.gif');
		$("#needCellPhone").text('请填写手机号码!');
		$("#cellPhone").focus();
		return false;
	}else{
		var pattern = /^1[34578]\d{9}$/;  
	    if (pattern.test(cellPhone)) {  
	    	$('#needCellPhoneImg').attr('src',contextPath + '/images/shequ39.gif');
	        $("#needCellPhone").text('');
	    }else{
	    	$('#needCellPhoneImg').attr('src',contextPath + '/images/shequ38.gif');
	    	$("#needCellPhone").text('手机号码格式不正确!');
	    	return false;
	    }  
	}
	var obj = false;
	$.ajax({
		async:false,
		url:contextPath+"/user/vailCellPhone.do",
		data:{'cellPhone':cellPhone},
		type:"post", 
		success:function(data){
			if(data == 'yes'){
				obj = true;
			}
		}
	});
    if(obj){
    	$('#needCellPhoneImg').attr('src',contextPath + '/images/shequ39.gif');
    	$("#needCellPhone").text('');
    	return {'result':true};
    }else{
    	$('#needCellPhoneImg').attr('src',contextPath + '/images/shequ38.gif');
    	$("#needCellPhone").text(' 该手机号码已经被注册过');
    	return false;
    }
}


