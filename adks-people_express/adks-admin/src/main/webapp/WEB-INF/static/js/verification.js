//验证方法
$.extend($.fn.validatebox.defaults.rules, {
	//验证非空
	isNullOrEmpty: {  
        validator: function (value,param) {
        	return checkValueIsNull(value);
        },
        message:'该项不能为空或输入空格！'
    },
    //验证设置时间是否小于于当前时间
    checkDate:{
    	validator: function (value,param) {
    		var nowDate=new Date();//取今天的日期
    		var setDate=new Date(value.replace(/-/g,"/"));
    		return nowDate<setDate;
        },
        message:'结束时间不能小于当前时间！'
    },
    //验证是否输入特殊字符
    specialCharacter: {  
        validator: function(value, param){  
            var reg = new RegExp("[`~!@#$^&*()=|{}':;'\\[\\]<>~！@#￥……&*（）——|{}【】‘；：”“'、？]");  
            return !reg.test(value);  
        },  
        message: '不允许输入特殊字符！'  
    },
    //验证是否输入数字
    isNumber: {  
        validator : function(value, param) {  
            return /^[0-9]*$/.test(value);  
        },  
        message : "请输入数字！"  
    },
    //验证是否输入中文
    isChinese: {  
        validator : function(value, param) {  
            var reg = /^[\u4e00-\u9fa5]+$/i;  
            return reg.test(value);  
        },  
        message : "请输入中文！"  
    },
    checkGradeName:{
    	validator : function(value, param) {
    		
            return reg.test(value);  
        },  
        message : "请输入中文！" 
    }
});

function trim(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}

//邮箱格式验证
function vailUserMail(userMail){
	if(userMail == null || userMail == '' || trim(userMail) == ''){
		return '';
	}
    var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"
    var re = new RegExp(regu);
    if(!re.test(userMail)){
	   return ' * 请输入正确的邮箱格式！';
    }else{
         return '';	
    }
}
// 手机号码验证
function vailUserCellphone(userCellphone){
	if(userCellphone == null || userCellphone == '' || trim(userCellphone) == ''){
		return '';
	}
	if(checkNum(userCellphone)){
		var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|17[0-9]|18[0-9])[0-9]{8}$";
		var re = new RegExp(regu);
		if(!re.test(userCellphone)){
			return ' * 手机号码格式不正确 ！';
		}
		if(userCellphone.length > 11){
			return ' * 手机号码不能超过11位 ！';
		}
		return '';
	}else{
		return ' * 手机号码必须为数字！';
	}
}
//验证身份证号方法 
var test=function(idcard){ 
if(idcard ==null || idcard == '' || trim(idcard) == '' || idcard == 'undefined'){
	return '验证通过';
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

/*登录名*/
function checkValueIsNull(value)
{
	if(value.replace(/\s*/g, "")==""){
		return false;
	}else{
		return true;
	}
}
