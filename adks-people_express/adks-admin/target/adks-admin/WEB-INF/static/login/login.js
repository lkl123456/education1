//		$(function() {		    });
//取消
function cancelChange() {
	parent.$('#tabs').tabs('close', '修改密码');
}
// 保存
function saveChange() {
	var oldpassword = $("#password").val();
	var passwordNew = $("#passwordNew").val();
	var passwordNew2 = $("#passwordNew2").val();
	if (oldpassword == null || "" == oldpassword || trimStr(oldpassword) == "") {
		$.messager.alert('友情提示', '请输入原密码！', 'info');
		return false;
	}
	if (passwordNew == null || "" == passwordNew || trimStr(passwordNew) == "") {
		$.messager.alert('友情提示', '请输入新密码！', 'info');
		return false;
	} else {
		if (passwordNew.length < 6) {
			$.messager.alert('友情提示', '密码不得少于六位！', 'info');
			return false;
		}
	}
	if (passwordNew2 == null || "" == passwordNew2
			|| trimStr(passwordNew2) == "") {
		$.messager.alert('友情提示', '请输确认新密码！', 'info');
		return false;
	} else {
		if (passwordNew2.length < 6) {
			$.messager.alert('友情提示', '密码不得少于六位！', 'info');
			return false;
		}
	}
	if (passwordNew != passwordNew2) {
		$.messager.alert('友情提示', '两次输入的密码必须相同！', 'info');
		return false;
	}
	var userId = $("#userId").val();
	$.ajax({
		url : contextpath + "/userIndex/updatePassword",
		async : false,// 改为同步方式
		type : "get",
		data : {
			"password" : oldpassword,
			"passwordNew" : passwordNew,
			"userId" : userId
		},
		success : function(data) {
			if (data == 'succ') {
				parent.$.messager.alert('友情提示', "密码修改成功！", 'info');
				cancelChange();
			} else if (data == 'nosucc') {
				parent.$.messager.alert('友情提示', "原密码输入错误！", 'info');
			} else {
				parent.$.messager.alert('友情提示', "密码修改失败！", 'info');
			}
		},
		error : function() {
			parent.$.messager.alert('友情提示', "密码修改失败！", 'info');
		}
	});
}
// 修改密码
function updatePw() {
	var url = contextpath + '/userIndex/personalSetting';
	parent.addTab_update('修改密码', url, 'icon-save');
}
// 登录方法
function dologin() {
	var username = $('#username').val();
	var userPassword = $('#userPassword').val();

	if (null == username || trimStr(username) == "" || null == userPassword
			|| trimStr(userPassword) == "") {
		$("#message").html("用户名或密码不能为空");
		return false;
	}

	if (null == username || username == "") {
		parent.$.messager.alert('友情提示', '请输入您的用户名！', 'info');
		return false;
	}

	$.ajax({
		type : "post",
		url : contextpath + "/userLogin/userlogin",
		data : {
			'username' : username,
			'userPassword' : userPassword
		},
		// jsonpCallback : "success_jsonpCallback",
		dataType : "json",
		success : function(data) {
			// alert(data);
			// var t = JSON.stringify(data);
			if (null != data) {
			}
			var code = data["code"];
			if (null != code && code == "200") {
				var message = data["message"];
				$("#message").show();
				$("#message").html(message);
				window.location.href = contextpath + "/userIndex/home";
			} else {
				var message = data["message"];
				$("#message").show();
				$("#message").html(message);
			}
			// $("#bodyId").load("/weather/index.html");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("请求对象XMLHttpRequest: " + XMLHttpRequest);
			console.log("错误类型textStatus: " + textStatus);
			console.log("异常对象errorThrown: " + errorThrown);
		}
	/*
	 * , success_jsonpCallback:function(data){ //alert("jsonpcallback:"+data); }
	 */
	});
}

// 管理员退出系统操作
function logout() {

	$('#logout').submit();
}


function show_cur_times(){
 //获取当前日期
 var date_time = new Date();
 //定义星期
 var week;
 //switch判断
 switch (date_time.getDay()){
	case 1: week="星期一"; break;
	case 2: week="星期二"; break;
	case 3: week="星期三"; break;
	case 4: week="星期四"; break;
	case 5: week="星期五"; break;
	case 6: week="星期六"; break;
	default:week="星期天"; break;
 }
 
 //年
 var year = date_time.getFullYear();
 	//判断小于10，前面补0
   if(year<10){
 	year="0"+year;
 }
 //月
 var month = date_time.getMonth()+1;
 	//判断小于10，前面补0
  if(month<10){
	  month="0"+month;
  }
  //日
  var day = date_time.getDate();
   //判断小于10，前面补0
   if(day<10){
 	day="0"+day;
   }
 
	 //时
	 var hours =date_time.getHours();
	 	//判断小于10，前面补0
	    if(hours<10){
	 	hours="0"+hours;
	 }
 
	 //分
	 var minutes =date_time.getMinutes();
	 	//判断小于10，前面补0
	    if(minutes<10){
	 	minutes="0"+minutes;
	 }
 
	//秒
	var seconds=date_time.getSeconds();
	//判断小于10，前面补0
	if(seconds<10){
	seconds="0"+seconds;
	}
 
	//拼接年月日时分秒
	var date_str = year+"年"+month+"月"+day+"日 "+hours+":"+minutes+":"+seconds+" "+week;
 
	//显示在id为showtimes的容器里
	document.getElementById("showtimes").innerHTML= date_str;
}
 
//设置1秒调用一次show_cur_times函数
setInterval("show_cur_times()",100);

function trimStr(str) { // 删除左右两端的空格
	return str.replace(/(^s*)|(s*$)/g, "");
}
