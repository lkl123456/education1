//		$(function() {		    });
//登录方法
function dologin(){
	var username =$('#username').val();
	var password =$('#password').val();
	// alert(username + '-' + password);
	$.ajax({		
		type:"post",
		url:context+"/sso/login",
		data:{'username':username,'password':password},
		// jsonpCallback : "success_jsonpCallback",
		dataType: "json",
		success:function(data){
			// alert(data);
			// var t = JSON.stringify(data);
			if(null != data){}
				var code =  data["code"];
				if(null != code && code =="200" ){
					var message = data["message"];
					 $("#message").show();
					 $("#message").html(message);
					window.location.href = context+"/home";
				 }else{
					 var message = data["message"];
					 $("#message").show();
					 $("#message").html(message);
				 }
			// $("#bodyId").load("/weather/index.html");
		},error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("请求对象XMLHttpRequest: "+XMLHttpRequest);  
			alert("错误类型textStatus: "+textStatus);  
			alert("异常对象errorThrown: "+errorThrown);  
		}
		/*
		 * , success_jsonpCallback:function(data){
		 * //alert("jsonpcallback:"+data); }
		 */
	});
}

//管理员退出系统操作
function logout(){
	
	$('#logout').submit();
}