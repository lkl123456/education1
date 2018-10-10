//用于左侧切换
$(document).ready(function(){
	$('.list_ul li a').click(function(){
		for(var $i=0;$i<6;$i++){
			$('.list_ul li a').eq($i).removeClass('lua'+($i+1)+'0');
			$('.list_ul li a').eq($i).addClass('lua'+($i+1));
			$('.list_ul li a img').eq($i).hide();
			};
	$keyn=$(this).index('.list_ul li a');
		$('.list_ul li a').eq($keyn).addClass('lua'+($keyn+1)+'0');
		$('.list_ul li a img').eq($keyn).show();	
	})					   
})

/**
 * 我的收藏
 */
function myCollection(){
	$.ajax({
		async:true,
		url:contextPath+"/user/myCollectionList.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}




/** 
 *	班级信息 start 
 */
// 点击当前培训班
function gradeCurrentList(){
	$.ajax({
		async:true,
		url:contextPath+"/grade/gradeCurrentList.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}
		
// 点击历史培训班
function gradeOverList(){
	$.ajax({
		async:true,
		url:contextPath+"/grade/gradeOverList.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}  
// 点击历史培训班个人学习情况
function checkGradeUserDetail(gradeId){
	var url = contextPath+"/registGrade/checkGradeUserDetail.do?gradeId="+gradeId;
	window.open(url);
}  

/** 班级信息 end */


/** 
 *	通知公告 start 
 */
// 系统公告
function  systemNotice(){
	$.ajax({
		async:true,
		url:contextPath+"/notice/getSystemNoticeList.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
} 
//系统公告详情
function  getSystemNoticeInfo(noticeId,flag){
	$.ajax({
		async:true,
		url:contextPath+"/notice/getSystemNoticeInfo.do?noticeId="+noticeId+"&flag="+flag,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}
//系统公告更多页
function  getSystemNoticeMore(){
	$.ajax({
		async:true,
		url:contextPath+"/notice/getSystemNoticeMore.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}

/** 通知公告 end */


/** 
 *	个人设置 start 
 */
// 获得个人信息
function loginOut(){
	var time = new Date().getTime();
	window.location.href = contextPath+"/user/loginOut/"+time+".shtml";
}
function userInfo(flag){
	//var userId=$("#userId").val();
	$.ajax({
		async:true,
		url:contextPath+"/user/userInfo.do?flag="+flag,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}
/*验证身份证号*/
function vailUserCard(){
	var userCard=$("#userCard").val();
	if(userCard != null && userCard != "" && userCard.trim() != "" && userCard != 'undefined'){
		var span=test(userCard);
		if(span != '验证通过'){
			$("#userCardSpan").text(' * '+span+' ！');
			return false;	
		}else{
			var okStr="";
			/*判断是否唯一*/
			var userId=$("#userId").val();
			$.ajax({
				async:false,
				url:contextPath+"/index/checkUserCardIndex.do",
		        type:"post",
		        data: {"userId":userId,"cardNumer":userCard},
		        success: function(data) {
				 	okStr=data;
		        }
		    });	
		    if(okStr == 'succ'){
		 		$("#userCardSpan").text('');
		 		return true;	
		 	}else{
		 		$("#userCardSpan").text(' * 该身份证号已被使用 ！');
		 		return false;	
		 	}
		}
	}
	return true;
}
// 保存个人信息
function saveUserInfo(){
	// 邮箱验证
	 /*var  userMail = $("#userMail").val() ;
	    var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"
	    var re = new RegExp(regu);
	    if(!re.test(userMail)){
	       $("#userMailSpan").text(' * 请输入正确的邮箱格式！');	
		   return false;
	    }else{
	         $("#userMailSpan").text('');	
	    }*/
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
		}else{
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
		   //var zhiJiName=$("#zhiJi").find("option:selected").text();
		  // var zhiJi=$("#zhiJi").val();
		   //$("#zhiJiName").val(zhiJiName);
		   var cardOk=vailUserCard();
		   if(!cardOk){
		   		return false;
		   }
		   var positionName=$("#positionId").find("option:selected").text();
		  $("#positionName").val(positionName);
		   var orgId=$("#orgId").val();
		   // 修改个人信息， 上传头像
		   $("#userInfoSave").ajaxSubmit({
				 async:true,
				 url:contextPath+"/user/saveUserInfo.do",
		         iframe: true,
		         type:"post",
		         data: $("#userInfoSave").serialize(),
		         success: function(data) {
				   //$("#gradeListDiv").html(data);
				   //var  headPicpathInfo = $("#headPicpathInfo")[0].src ;
				   //if(headPicpathInfo != null){
					   //$("#headPicpath").attr("src",headPicpathInfo);
				  // }
				   alert("修改信息成功!");
				   //gradeCurrentList();
				   window.location.href=contextPath+"/index/centerIndex/"+orgId+".shtml";
		         }
		     });
		}
	}else{
	    $("#userPhoneSpan").text(' * 手机号码必须为数字！');	
		return false;
	}
	 
}
// 修改密码
function  saveNewPassWord(){
	var userId=$("#userId").val();
	var twoPassWord=$("#twoPassWord").val();
    $.ajax({
 		async:true,
 		url:contextPath+"/user/saveUserPassWord.do?userId="+userId+"&password="+twoPassWord,
 		type:"post", 
 		success:function(succ){
    	   if(succ == "succ"){
		      $("#savePassWordSpan").text(' 修改密码成功！');	
		   }else{
		      $("#savePassWordSpan").text('修改密码失败！');	
		   }
 		}
 	});
}

/** 个人设置 end */





/** 
 *	班级课程 start 
 */

//ajax 获取 班级基本信息  班主任信息  以及 班级公告
function getGradeInfo(){
	var gradeId=$("#gradeId").val();
	$.ajax({
		async:false,
		url:contextPath+"/grade/getGradeInfo.do?gradeId="+gradeId,
		type:"post", 
		success:function(data){
			$("#gradeInfoDiv").html(data);
		}
	});
}

// 点击进入培训班，进入班级课程列表页面
/*function gradeCourseList(gradeId,gradeName){
	$.ajax({
		async:false,
		
		url:contextPath+"/gradeCourse/gradeCourseList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName)+"&userStudyType=2&courseType=500",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}*/

//点击进入培训班首页 ckl
function gradeIndex(gradeId){
	var url = contextPath+"/grade/gradeIndex.do?gradeId="+gradeId;
	window.open(url);
	
}

function selGradeCourseList(userStudyType){
	$("#userStudyType").val(userStudyType);
	var gradeId=$("#gradeId").val();
	var gradeName=$("#gradeName").val();
	var courseType=$("#courseType").val();
	$.ajax({
		async:false,
		url:contextPath+"/gradeCourse/gradeCourseList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName)+"&userStudyType="+userStudyType+"&courseType="+courseType,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}

//modify ckl 2015年3月13日18:06:07
function selGradeCourseListByCourseType(courseType){
	//alert(courseType);
	$("#courseType").val(courseType);
	var gradeId=$("#gradeId").val();
	var gradeName=$("#gradeName").val();
	var userStudyType=$("#userStudyType").val();
	/*$.ajax({
		async:false,
		url:contextPath+"/gradeCourse/gradeCourseList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName)+"&userStudyType="+userStudyType+"&courseType="+courseType,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});*/
}
function play(gradeId,courseId,courseType,courseName){
	
	//优化时该项方法要进行优化
		if(courseType!=null && gradeId!=null && courseId!=null ){
			if(courseType==170){
				window.open(contextPath+"/course/playScorm.do?gradeId="+gradeId+"&courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');

			}else if(courseType==171){
				window.open(contextPath+"/course/playVideo.do?gradeId="+gradeId+"&courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
			}
		}
	/* 暂时停用判断 是否正打开视频观看
	var openedCourseName=isOpenedCourseName(courseName);
	if(openedCourseName!=null && openedCourseName!=''&& openedCourseName==courseName){
		 // 提示 此课程正在播放 
		showMoreOpenedCourseNameMsgDiv(openedCourseName);
	}else if(openedCourseName!=null && openedCourseName!='' && openedCourseName!=courseName) {
        // 提示 已经 其他课程正在播放 
       	 showOpenedCourseNameMsgDiv(openedCourseName);
	}else {
		//优化时该项方法要进行优化
		if(courseType!=null && gradeId!=null && courseId!=null ){
			if(courseType==170){
				window.open(contextPath+"/course/playScorm.do?gradeId="+gradeId+"&courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');

			}else if(courseType==171){
				window.open(contextPath+"/course/playVideo.do?gradeId="+gradeId+"&courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
			}
		}
	}
	*/

}

function GetIEVersion()
{
	var fullVersion = -1;
	var nAgt = navigator.userAgent;
	if ((verOffset=nAgt.indexOf("MSIE"))!=-1)				
 		fullVersion = parseInt(nAgt.substring(verOffset+5));
 				 				
	return fullVersion;
}

// 课程观看记录

function  getUserCourseViewList(){
	$.ajax({
		async:false,
		url:contextPath+"/courseview/getUserCourseViewList.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
	
}
function  deleteUserCourseView(courseId){
	$.ajax({
		async:false,
		url:contextPath+"/courseview/deleteUserCourseView.do?courseId="+courseId,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}
	
/** 班级课程 end */


/** 
 *	班级论文 start 
 */
// 点击进入班级论文列表信息 (参数：gradeId-班级ID)
function gradeThesisList(gradeId, gradeName){
	$.ajax({
		async:true,
		url:contextPath+"/grade/gradeThesisList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}

// 点击进入班级论文详情信息 (参数：thesisId-论文ID, gradeId-班级ID)
/*function gradeThesisInfo(thesisId, gradeId, gradeName){
	$.ajax({
		async:true,
		url:contextPath+"/grade/gradeThesisInfo.do?thesisId="+thesisId+"&gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}*/

/*// 保存、修改学员论文信息
function gradeThesisSave(){
	var gradeId = $("#gradeId").val();
	var fv = $("#fullVersion").val();
	if(fv != null && (fv==6||fv==7)){
		var contentKd = $("#contentKd").val();
		if(contentKd == '' || $.trim(contentKd) == ''){
			alert("论文内容不能为空！");
			return;
		}
    }else{
    	var oEditor = FCKeditorAPI.GetInstance("st_content");
    	var getcontent = oEditor.EditorDocument.body.innerText;
    	if(getcontent == '' || $.trim(getcontent) == ''){
			alert("论文内容不能为空！");
			return;
		}
    }
    
	var nowDate=$("#nowDate").val();
	var endDate=$("#endDate").val();
	var startDate=$("#startDate").val();
	if(endDate<nowDate){
		alert("论文已过期！");
		return false;
	}
	if(nowDate<startDate){
		alert("未到开始时间，无法提交！");
		return false;
	}

	$("#sbbutton").attr("disabled", true);
	$("#gradeThesisFrom").ajaxSubmit({
		async:true,
		url:contextPath+"/grade/gradeThesisSave.do",
		iframe: true,
		type:"post",
		data: $("#gradeThesisFrom").serialize(),
		success: function(data) {
			if(data == 'succ'){
       			alert("您的论文已经成功提交！");
       			$("#goGradeThesisList").click();
       		}else{
       			$("#sbbutton").attr("disabled", false);
       			alert("您的论文提交失败！");
       		}
		}
	});
}*/
function gradeThesisSave111(){
	var gradeId = $("#gradeId").val();
	$.ajax({
        type: "POST",
       	url : contextPath+"/grade/gradeThesisSave.do",
       	data: $("#gradeThesisFrom").serialize(),// formid
       	async: true,
       	success: function(data) {
       		if(data == 'succ'){
       			alert("您的论文已经成功提交！");
       		}else{
       			alert("您的论文提交失败！");
       		}
       	}
   	});
}
// 点击进入上传论文附件页面
function uploadThesisFile(){
	//window.open(contextPath+"/center/grade/uploadThesisFile.jsp");
	window.open(contextPath+"/center/grade/uploadThesisFile.jsp",'upkjimg','height=130, width=360, top=380, left=560, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}

/** 班级论文 end */



/** 
 *	班级考试 start 
 */
// 点击进入班级考试列表页面
/*function gradeExamList(gradeId, gradeName){
	$.ajax({
		async:true,
		url:contextPath+"/gradeExam/gradeExamList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}*/
// 点击进入班级考试页面
function gradeExamInfo(examId, rdEtimes, etimes){
	if(rdEtimes != '' && parseInt(rdEtimes) >= etimes){
		alert("您已经没有可用的考试次数！");
	}else if(($("#startdate"+examId).val())>(new Date()).getTime()){
		alert("考试未开始！");
	}else{
		window.open(contextPath+"/gradeExam/gradeExamInfo.do?examId="+examId,'upkjimg','height=660, width=1100, top=80, left=180, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
}
// 点击进入班级考试成绩页面（多次考试成绩则取其最好成绩）
function gradeExamScoreInfo(examId, rdEtimes){
	if(rdEtimes == '' || rdEtimes <= 0){
		alert("您没有本次考试的成绩记录！");
	}else{
		window.open(contextPath+"/grade/gradeExamScoreInfo.do?examId="+examId,'upkjimg','height=660, width=1100, top=80, left=180, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
}
 /* 班级考试 end */
 
 
/* 个人档案  start */
function  toRecord(){
	$.ajax({
		async:true,
		url:contextPath+"/record/recordIndex.do",
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}

/* 个人档案  end */
function gradeGraduation(gradeId,gradeName){
	$.ajax({
		async:true,
		url:contextPath+"/grade/gradeGraduation.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}

//进入培训班信息
function toGradeCenterIndex(gradeId){
	var url = contextPath+"/grade/toGradeCenterIndex.do?gradeId="+gradeId;
	window.location.href=url;
}

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
 