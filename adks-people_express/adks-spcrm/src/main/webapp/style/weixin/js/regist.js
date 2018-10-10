//去掉字符串头尾空格
function trimStr(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
} 

String.prototype.endWith=function(str){
if(str==null||str==""||this.length==0||str.length>this.length)
  return false;
if(this.substring(this.length-str.length)==str)
  return true;
else
  return false;
return true;
}

function registValidator(){
	//验证用户名
	var obj = {};
	obj = vailUserName();
	if(!obj.result){
		return false;
	}
	//校验手机号码
	obj = vailCellPhone();
	if(!obj.result){
		return false;
	}
	//验证密码
	obj = vailPass();
	if(!obj.result){
		return false;
	}
	//验证真实姓名
	obj = vailRealName();
	if(!obj.result){
		return false;
	}
	
	//验证身份证
	obj = vailCard();
	if(!obj.result){
		return false;
	}
	
	if($('#isAgree').is(':checked')==true){
		$("#msg").text('');$("#msg").hide();
		return true;
	}else{
		$("#msg").text("请仔细阅读用户注册协议，同意请勾选！");$("#msg").show();
		return false;
	}
	
}

function vailUserName(){
	var name = trimStr($("#userName").val());
	if("" == name){
		$("#msg").text('用户名不能为空！');$("#msg").show();
		return false;
	}else{
		$("#msg").text('');$("#msg").hide();
	}
	//不能全为数字
    var rule = /(^\d*$)|(^\S+\s+\S+$)/; // /^\d+$/
    if(name.match(rule)){
    	$("#msg").text('用户名不能为纯数字或空格！');$("#msg").show();
    	return false;
    }
    //如果用户名中包含汉字 ,不可以
    var rule1 = /[\u4e00-\u9fa5]/;
    if(name.match(rule1)){
    	$("#msg").text('用户名不能包含汉字!');$("#msg").show();
    	return false;
    }
	var checkOK = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";	
	for(i = 0; i <name.length; i++){
		ch = name.charAt(i);
		for(j = 0; j < checkOK.length; j++){
			if(ch == checkOK.charAt(j)){
				break;
			}
			if(j == checkOK.length-1){
				$("#msg").text('用户名只能为字母、数字和下划线！');	$("#msg").show();
				return false;
			}else{
				$("#msg").text('');$("#msg").hide();
		}
	}
	var obj = false;
	$.ajax({
		async:false,
		url : contextPath+"/user/vailUserName.do",
		data:{'userName':name},
		type:"post", 
		success:function(data){
			if(data == 'yes'){
				obj = true;
			}
		}
	});

    if(obj){
    	$("#msg").text('');$("#msg").hide();
    	return {'result':true};
    }else{
    	$("#msg").text('该用户名已经被占用！');$("#msg").show();
    	return false;
    }
   
}
	
}
function vailRealName(){
	var realname = trimStr($("#realName").val());
	if("" == realname){
		$("#msg").text('真实姓名不能为空！');$("#msg").show();
		return false;
	}else{
		var rule = /^[\u4e00-\u9fa5]+$/; //中文正则
		if(!realname.match(rule)){
			$("#msg").text('真实姓名必须为中文汉字！');$("#msg").show();
			return false;
		}
		$("#msg").text('');$("#msg").hide();
		return {'result':true};
	}
}

function vailPass(){
	
	var password = trimStr($("#userPwd").val());
	var repassword = trimStr($("#userPwdConfirm").val());
	if("" == password){
		$("#msg").text('登录密码不能为空！');$("#msg").show();
		return false;
	}else {
	    if(password.length<6){
	     $("#msg").text('登录密码位数不能少于6位！');$("#msg").show();
	     return false;
	    }
	}
	$("#msg").text('');$("#msg").hide();
	
	if(""!= repassword){
		if(repassword == password){
			$("#msg").text('');$("#msg").hide();
			return {'result':true};
		}else{
			 $("#msg").text('确认密码与登录密码不一致！');$("#msg").show();
			 return false;
		}
	}else{
		$("#msg").text('确认密码不能为空！');$("#msg").show();
	    return false;
	}
	$("#msg").text('');$("#msg").hide();
}

function vailRePass(){
	var password = trimStr($("#userPwd").val());
	var repassword = trimStr($("#userPwdConfirm").val());
	if("" == repassword){
		$("#msg").text('确认密码不能为空！');$("#msg").show();
	    return false;
	}
	if(""!= password){
		if(repassword == password){
			$("#msg").text('');$("#msg").hide();
			return {'result':true};
		}else{
			$("#msg").text('确认密码与密码不一致！');$("#msg").show();
			return false;
		}
	}else{
		$("#msg").text('');$("#msg").hide();
		return {'result':true};
	}
}
//校验手机号码（非空、唯一性）
function vailCellPhone(){
	var cellPhone = trimStr($("#cellPhone").val());
	if(cellPhone == ''){
		$("#msg").text('请填写手机号码！');$("#msg").show();
		$("#cellPhone").focus();
		return false;
	}else{
		var pattern = /^1[34578]\d{9}$/;  
	    if (pattern.test(cellPhone)) {  
	        $("#msg").text('');$("#msg").hide();
	    }else{
	    	$("#msg").text('手机号码格式不正确！');$("#msg").show();
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
    	$("#msg").text('');$("#msg").hide();
    	return {'result':true};
    }else{
    	$("#msg").text('该手机号码已经被注册过！');$("#msg").show();
    	return false;
    }
}

function vailCard(){
 var cardValue=$("#card").val();
 if(cardValue.endWith("x")){
 	$("#msg").text("身份证X必须大写！");$("#msg").show();
  	return false;
 }
 //是否为空
 if(cardValue==""){
  $("#msg").text("身份证不能为空");$("#msg").show();$("#msg").show();
  return false;
 }else if(isCardNo(cardValue) == false){ //校验长度，类型
  $("#msg").text("您输入的身份证号码不正确，请重新输入！");$("#msg").show();
  return false;
 }else if(checkProvince(cardValue) == false){//检查省份
  $("#msg").text("您输入的身份证号码不正确,请重新输入！");$("#msg").show();
  return false;
 }else if(checkBirthday(cardValue) == false){//校验生日
  $("#msg").text("您输入的身份证号码生日不正确,请重新输入！");$("#msg").show();
  return false;
 }else if(checkParity(cardValue) == false){//检验位的检测
  $("#msg").text("您的身份证校验位不正确,请重新输入！");$("#msg").show();
  return false;
 }else{
  $("#msg").text("");$("#msg").hide();
  return {'result':true};
 }

}

//身份证省的编码
var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
        21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
        33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
        42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
        51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
        63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
       };

//检查号码是否符合规范，包括长度，类型
function isCardNo(card){
 //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
 var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
 if(reg.test(card) === false){
  //alert("demo");
  return false;
 }
 return true;
}

//取身份证前两位,校验省份
function checkProvince(card){
 var province = card.substr(0,2);
 if(vcity[province] == undefined){
  return false;
 }
 return true;
}

//检查生日是否正确
function checkBirthday(card){
 var len = card.length;
 //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
 if(len == '15'){ 
     var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
     var arr_data = card.match(re_fifteen);
     var year = arr_data[2];
     var month = arr_data[3];
     var day = arr_data[4];
     var birthday = new Date('19'+year+'/'+month+'/'+day);
     return verifyBirthday('19'+year,month,day,birthday);
 }
 //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
 if(len == '18'){
     var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
     var arr_data = card.match(re_eighteen);
     var year = arr_data[2];
     var month = arr_data[3];
     var day = arr_data[4];
     var birthday = new Date(year+'/'+month+'/'+day);
     return verifyBirthday(year,month,day,birthday);
 }
 return false;
}

//校验日期
function verifyBirthday(year,month,day,birthday){
 var now = new Date();
 var now_year = now.getFullYear();
 //年月日是否合理
 if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day)
 {
     //判断年份的范围（3岁到100岁之间)
     var time = now_year - year;
     if(time >= 3 && time <= 100)
     {
         return true;
     }
     return false;
 }
 return false;
}

//校验位的检测
function checkParity(card){
 //15位转18位
 card = changeFivteenToEighteen(card);
 var len = card.length;
 if(len == '18'){
     var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
     var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
     var cardTemp = 0, i, valnum;
     for(i = 0; i < 17; i ++)
     {
         cardTemp += card.substr(i, 1) * arrInt[i];
     }
     valnum = arrCh[cardTemp % 11];
     if (valnum == card.substr(17, 1))
     {
         return true;
     }
     return false;
 }
 return false;
}

//15位转18位身份证号
function changeFivteenToEighteen(card){
 if(card.length == '15')
 {
     var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
     var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
     var cardTemp = 0, i;  
     card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
     for(i = 0; i < 17; i ++)
     {
         cardTemp += card.substr(i, 1) * arrInt[i];
     }
     card += arrCh[cardTemp % 11];
     return card;
 }
 return card;
}


