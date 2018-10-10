function onlogin(){
	// 用户名
	var key = "skda";
	var username = document.getElementById("userNameTemp").value;
	if(username == ''|| username == '请输入您的用户名'){
		alert("请输入您的用户名！");
		return false;
	}else {
		//var  enResult = strEnc(str,key1,key2,key3);
		username = strEnc(username,key,key,key);
//		alert(username);
		//$.cookie("49BAC005-7D5B-4231-8CEA-userName",username);
		 $("#userName").val(username);
	}
	// 密码
	var password = document.getElementById("passWordTemp").value;
	if(password == '' || password == '请输入您的登录密码'){
		alert("请输入您的登录密码！");
		return false;
	}else {
		password = strEnc(password,key,key,key);
		//alert(password);
		$("#passWord").val(password);
		//$.cookie("49BAC005-7D5B-4231-8CEA-passWord",password);
	}
	// 验证码
	var randNum = document.getElementById("randNum").value;
	if(randNum == ''){
		alert("请您输入验证码！");
		document.getElementById("randNum").focus();
		return false;
	}

}

function getYanZhengMa(url){
 		var sRandNum="";
	   for(var i=0;i<4;i++){
	      var randNum=Math.floor(Math.random()*10);
	      sRandNum=sRandNum+randNum;
	   }
	   $("#checkcode").attr("src",url+"/common/image.jsp?sRand="+sRandNum);
	   $("#sRandNum").val(sRandNum);
}

function GetRandomNum(Min,Max){   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
}   
  

function logininit(){
	/*
	var userNameStr=$.cookie("49BAC005-7D5B-4231-8CEA-userName");
	
	if(document.getElementById('userName') != undefined){
		if(userNameStr != null) { 
			document.getElementById('userName').value = userNameStr; 
		}
	} 
	*/
}

