
// 延长session  每隔 time 延时一次session
// 默认 20分钟延时一次session   add by  lxh
function continueSession(time){
	if(time==null){
		time=20*60*1000;
	}
	$.ajax({
		    url : contextPath+"/user/continueSession.do",
        	type: "POST",
        	data: '',
        	async: true,
        	success: function() {
        	}
    });
	setTimeout("continueSession("+time+")",time);
}

//去掉字符串头尾空格   
function trimStr(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}

// JavaScript Document
$(document).ready(function(){
	$('.listr dt h2').click(function(){
		$keyy=$(this).index('.listr dt h2');
		$('.listdd6 ul').eq($keyy).show();
		$('.listdd6 ul').eq($keyy).siblings().hide();
		$('.listr dt h2').eq($keyy).siblings().addClass('dh2');
		$('.listr dt h2').eq($keyy).removeClass('dh2');
	})
	$('.listdd7 ol li').click(function(){
		$keyyy=$(this).index('.listdd7 ol li');
		$('.listdd7 ul').eq($keyyy).show();
		$('.listdd7 ul').eq($keyyy).siblings().hide();
		$('.listdd7 ol').show()
		$('.listdd7 ol li').eq($keyyy).addClass('xxli');
		$('.listdd7 ol li').eq($keyyy).siblings().removeClass('xxli');
	})
	$('.bjj').toggle(function(){
		$(this).addClass('bian');
		$('.bjmai').show();
	},function(){
		$(this).removeClass('bian');
		$('.bjmai').hide();
		})
	$('.gbi').click(function(){
		$('.bjj').removeClass('bian');
		$('.bjmai').hide();						 
	})
	$('.xxda div').hover(function(){
		$('.xxda ul').show();						  
	},function(){
		$('.xxda ul').hide();
		})
	$('.listr dt .gduo').click(function(){
		$('.bg,.tqancu').fadeIn(500);									
	})
	$('.guan').click(function(){
		$('.bg,.tqancu').fadeOut(500);					  
	})
	
	$('.bfzri dd').click(function(){
		$('.bfzri dd').css({'background':'#222'});					  
		$(this).css({'background':'#2d3e4e'})							  
	})
})

/**
  Copyright (c) 2012-09-26, hqt 
  All rights reserved.
  @author hqt
  @see basic js grammer
  @version 1.0 
 */

// check required field 
function checkValue(fieldStr)
{
	var checkOk=true;
  if (fieldStr.length==0)
  return   checkOk;
  
        var arrayFieldName = fieldStr.split(',');
        var i;		
        for (i=0; i<arrayFieldName.length; i++)
        {
            var obj = document.getElementById(arrayFieldName[i]);
            if (obj.value == null || obj.value.trim() == "")
            {
            	var labelObj = document.getElementById(arrayFieldName[i] + "Label");
               	var msg = "\u8bf7\u8f93\u5165\u7ea2\u8272\u7684\u5fc5\u987b\u586b\u5199\u9879"
               	if(labelObj != null)
               		msg+="\n" + labelObj.innerText;
                alert('\u53cb\u60c5\u63d0\u793a', msg, 'info');
                obj.focus();
				checkOk=false;
				break;
                //return false;
            }
        }
  
	return checkOk;
}

function selectAll(flag) {
	if(formSearch.orderId==undefined){
		return;
	}else{
		if(flag=="true"){
			formSearch.chkall.checked=true;
		}
	  	formSearch.orderId.checked= formSearch.chkall.checked
		for (var i = 0; i < formSearch.orderId.length; i++) {
			formSearch.orderId[i].checked = formSearch.chkall.checked;
		}
	}
}
function selectCancel() {
	if(formSearch.orderId==undefined){
		return;
	}
 	formSearch.chkall.checked=false;
  	formSearch.orderId.checked=false;
	for (var i = 0; i < formSearch.orderId.length; i++) {
		formSearch.orderId[i].checked = false;
	}
}
function deleteChecked(tableName) {
	var idString = "";
	if (formSearch.orderId.length==undefined)
	{
	if (formSearch.orderId.checked == true)
	idString = formSearch.orderId.value
	}
	else{
		for (var i = 0; i < formSearch.orderId.length; i++) {
			if (formSearch.orderId[i].checked == true) {
				idString = idString + "," + formSearch.orderId[i].value;
			}
		}
	}
	if (idString.length == 0) {
		//alert("\u8bf7\u5148\u9009\u62e9\u8981\u5220\u9664\u7684\u8bb0\u5f55\uff01");
		alert('\u53cb\u60c5\u63d0\u793a', '\u8bf7\u5148\u9009\u62e9\u8981\u5220\u9664\u7684\u8bb0\u5f55\uff01', 'info');
		return;
	}
	//if (confirm("\u786e\u5b9e\u8981\u5220\u9664\u8fd9\u4e9b\u8bb0\u5f55\u5417\uff1f")) {
		//document.formSearch.action = tableName+"RemoveAll.action?orderIndexs=" + idString;
		//document.formSearch.submit();
	//}
	confirm('\u53cb\u60c5\u63d0\u793a', '\u786e\u5b9e\u8981\u5220\u9664\u8fd9\u4e9b\u8bb0\u5f55\u5417\uff1f', function (r){
	if (r) {
		document.formSearch.action = tableName+"RemoveAll.action?orderIndexs=" + idString;
		document.formSearch.submit();
	}
	})
}
function sortInit(){
	if(document.getElementById("pageInfo.sorterName") != null){
		sort = document.getElementById("pageInfo.sorterName").value;
		direction = document.getElementById("pageInfo.sorterDirection").value;
	}
}

function pageSubmit(currentPageName,action,formName,targetPage){
	document.getElementById("currentPage").value = targetPage;
	var ajaxSubmit = document.getElementById("ajaxSubmit");
	if(action == 'null'){
		var form = document.getElementById(formName);
		action = form.action;
	}
	// ajaxSubmit 不为空
	if(ajaxSubmit && ajaxSubmit.value != ''){
		
		var ajaxDialogSubmit = document.getElementById("ajaxDialogSubmit");
		if(ajaxDialogSubmit &&  ajaxDialogSubmit.value != '' ){
			ajaxSubmit.value=ajaxDialogSubmit.value;
			formName="ajaxDialogForm";
			var form = document.getElementById(formName);
			action = form.action;
			document.ajaxDialogForm.currentPage.value = targetPage;
		}
		//alert(action+":"+formName);
		$.ajax({
        	type: "POST",
        	url : action,
        	data: $("#"+formName).serialize(),// 你的formid
        	async: true,
        	success: function(data) {
        		$("#"+ajaxSubmit.value).html(data);
        	}
    	});
	}else{	
		document.getElementById(formName).submit();
	}
}

function gopageSubmit(obj){
	var goToNumber =document.getElementById("pagenumtext").value;
	//alert(goToNumber);
	if(goToNumber && ""!=goToNumber){
		if(parseFloat(goToNumber)>0){
			var pagenum = "1";
			if(parseFloat(goToNumber) > parseFloat(document.getElementById("totalpage").value)){
				pagenum = document.getElementById("totalpage").value;
			}else{
				pagenum = goToNumber;
			}
			pageSubmit('page','null','formSearch',pagenum);
		}
	}
}
function checkThenSubmit(currentPageName,action,formName,targetPage,totalPage){
	var pageNum = targetPage;
	if(isNaN(parseInt(targetPage))){
		alert('\u53cb\u60c5\u63d0\u793a', '\u8bf7\u8f93\u5165\u6570\u5b57', 'info');
		return false;
	}
	if(parseInt(targetPage)>parseInt(totalPage)){
		pageNum = totalPage;
	}
	if(parseInt(targetPage)<=0){
		pageNum = "1";
	}
	pageSubmit(currentPageName,action,formName,pageNum);
}
window.onload = sortInit;
/*
$j(function(){
	$j('.chancleButton').live('click',function(){
		parent.chancleButton();
	})
})
*/
function taCheckOnChange(taId,maxSize) {  
    var defaultMaxSize = getDefaultMaxSize();  
    var ta = document.getElementById(taId);  
    if(!ta) {  
        return;  
    }  
    if(!maxSize) {  
        maxSize = defaultMaxSize;  
    } else {  
        maxSize = parseInt(maxSize);  
        if(!maxSize || maxSize < 1) {  
            maxSize = defaultMaxSize;  
        }  
    }  
   // var taTipDiv =  $j("#"+taId).next("font");  
    var taTipDiv = document.getElementById("f");
    taTipDiv.innerHTML="当前字数：" + ta.value.length + "，字数限制：" + maxSize;  
    if (ta.value.length > maxSize) {  
		//parent.$.messager.alert('友情提示', "当前字数 "+ta.value.length+" ，超过最大字符限制数 "+maxSize+" ，点击 \"确定\" 将自动截断。", 'info');
        ta.value=ta.value.substring(0,maxSize);
        taTipDiv.innerHTML="字数：" + ta.value.length + "，字数限制：" + maxSize;  
    }   
}  

function taCheckOnKeyUp(taId,maxSize) {  
    var defaultMaxSize = getDefaultMaxSize();  
    var ta = document.getElementById(taId);  
    if(!ta) {  
        return;  
    }  
    if(!maxSize) {  
        maxSize = defaultMaxSize;  
    } else {  
        maxSize = parseInt(maxSize);  
        if(!maxSize || maxSize < 1) {  
            maxSize = defaultMaxSize;  
        }  
    }     
    if (ta.value.length > maxSize) {  
        ta.value=ta.value.substring(0,maxSize);  
    }   
    var taTipDiv = document.getElementById("f");
   // var taTipDiv = $j("#"+taId).next("font");           
    taTipDiv.innerHTML="字数：" + ta.value.length + "，字数限制：" + maxSize;  
}  

function getDefaultMaxSize() {  
    return 200;  
}

//李立江 把数字(2400)转换成时间格式 00:40:00
function longToString(lon){
	
	var str = "";
	
	var hh = lon/3600;
	if((hh+"").indexOf(".")>=0){
		hh =parseFloat((hh+"").substring(0,(hh+"").indexOf(".")));
	}
	
	var mm = parseFloat((lon - hh * 3600)/60);
	
	if((mm+"").indexOf(".")>=0){
		mm =(mm+"").substring(0,(mm+"").indexOf("."));
	}
	var ss = parseFloat(lon - hh * 3600 - mm * 60);
	if((ss+"").indexOf(".")>=0){
		ss =(ss+"").substring(0,(ss+"").indexOf("."));
	}
	if(hh < 10){
		str = "0" + hh + ":";
	}else{
		str = str + hh + ":";
	}
	if(mm < 10){
		str = str + "0" + mm + ":";
	}else{
		str = str + mm + ":";
	}
	if(ss < 10){
		str = str + "0" + ss;
	}else{
		str = str + ss;
	}
	return str;
}

/** 时间转秒数 (00:00:00)
 * @param str
 * @return
 * @throws ParseException
 */
 function  stringToLong(str){
	var lon = 0;
	var ss = new Array();
	ss = str.split(":");
	var s1=ss[0] * 3600;
	var s2=ss[1] * 60;
	if(s1>0){
		lon = parseInt(lon) + ss[0] * 3600;
	}
	if(s2>0){
		lon = parseInt(lon) + ss[1] * 60;
	}
	if(ss[2]>0){
		lon = parseInt(lon) + parseInt(ss[2]);
	}
	return lon;
 }
/*禁用backspace键的后退功能，但是可以删除文本内容*/  
document.onkeydown = check;   
function check(e) {   
    var code;   
    if (!e) var e = window.event;   
    if (e.keyCode) code = e.keyCode;   
    else if (e.which) code = e.which;   
    var obj = e.srcElement?e.srcElement:e.target;   
    var keycode = e.keyCode?e.keyCode:e.which;   
    if (((keycode == 8) &&   //BackSpace    
         ((obj.type != "text" &&    
         obj.type != "textarea" &&    
         obj.type != "password") ||    
         obj.readOnly == true)) ||    
        ((e.ctrlKey) && ((keycode == 78) || (keycode == 82)) ) ||    //CtrlN,CtrlR   
        (keycode == 116) ) {                                                   //F5    
        if(window.event){   
            event.keyCode = 0;    
            event.returnValue = false;    
        }else{   
            e.preventDefault();   
        }   
    }   
    return true;   
}

// 多行文本框字符数控制
function taCheckOnChange(taId,maxSize) {  
	//alert("------");
    var defaultMaxSize = getDefaultMaxSize();  
    var ta = document.getElementById(taId);  
    if(!ta) {  
        return;  
    }  
    if(!maxSize) {  
        maxSize = defaultMaxSize;  
    } else {  
        maxSize = parseInt(maxSize);  
        if(!maxSize || maxSize < 1) {  
            maxSize = defaultMaxSize;  
        }  
    }  
    var taTipDiv =  $("#"+taId).next("font");   
    taTipDiv.html("当前字数：" + ta.value.length + "，字数限制：" + maxSize);  
    if (ta.value.length > maxSize) {  
		alert("当前字数 "+ta.value.length+" ，超过最大字符限制数 "+maxSize+" ，点击 \"确定\" 将自动截断。");
        ta.value=ta.value.substring(0,maxSize);
        taTipDiv.html("当前字数：" + ta.value.length + "，字数限制：" + maxSize);  
    }   
}
function taCheckOnKeyUp(taId,maxSize) {  
    var defaultMaxSize = getDefaultMaxSize();  
    var ta = document.getElementById(taId);  
    if(!ta) {  
        return;  
    }  
    if(!maxSize) {  
        maxSize = defaultMaxSize;  
    } else {  
        maxSize = parseInt(maxSize);  
        if(!maxSize || maxSize < 1) {  
            maxSize = defaultMaxSize;  
        }  
    }
    if (ta.value.length > maxSize) {  
        ta.value=ta.value.substring(0,maxSize);  
    }   
    var taTipDiv = $("#"+taId).next("font");
    if(maxSize == ta.value.length){
    	taTipDiv.html("<font color='red'>当前字数：" + ta.value.length + "，字数限制：" + maxSize+"</font>");
    } else{
	    taTipDiv.html("当前字数：" + ta.value.length + "，字数限制：" + maxSize);
    } 
}

//session中查询  已打开课程的 课程名    session中取到的课程名 == newCourseName
function isOpenedCourseName(newCourseName){
	var openedCourseName="";
	// 发送请求 从session 中 获取  上一次打开课程的 课程名称 
	$.ajax({
	    url : contextPath+"/course/getOpenedCourseName.do",
    	type: "POST",
    	data: '',
    	async: false,
    	success: function(result) {
			if(result!=null &&result!=''){
				openedCourseName=result;
			}
    	}
	});
	//alert("openedCourseName:"+openedCourseName);
	if(openedCourseName==null || openedCourseName==''){
		// 添加当前 打开的 courseName
		$.ajax({
		    url : contextPath+"/course/saveOpenedCourseName.do?courseName="+encodeURI(newCourseName),
	    	type: "POST",
	    	data: '',
	    	async: false,
	    	success: function(result) {
	    	}
		});
	}
	return openedCourseName;
}

// 删除 保存到session中的  已打开课程的 课程名   
//modif by hqt 由于目前写的方法不能适用所有的浏览器，帮先注掉该功能的调用
function delOpenedCourseName(){
	var courseName="";
	$.ajax({
	    url : contextPath+"/course/saveOpenedCourseName.do?courseName="+encodeURI(courseName),
    	type: "POST",
    	data: '',
    	async: false,
    	success: function(result) {
    	}
	});
}

/* 
 * 课程播放--方法 
 */
var objWin;//判断窗口是否已打开
function BeginStudy(CourseId , CourseCode , ScormOrVideo , Cname){
	
	var keys = [];
    var values = [];
    
    keys[0] = "CourseId";//课程ID
    values[0] = CourseId;

    keys[1] = "Cname";//课程名称
    values[1] = strEnc(Cname,"adks1","adks2","adks3");//中文乱码 加密;
    
    keys[2] = "ScormOrVideo";//三分屏或单视频  三分屏是1
	values[2] = ScormOrVideo;
	
    keys[3] = "CourseCode";//课程编码或文件名
    values[3] = CourseCode;
	
	var UserId = $("#userId").val();//用户Id;
    keys[4] = "UserId";
    values[4] = UserId;
    //如果是课件超市打开课程 GreadId 一定要最后设为 CourseSuper 否则课件无法打开
    var GreadId =$("#gradeId").val();//班级ID
    if(typeof(GreadId) == 'undefined'|| GreadId == ''|| GreadId == 'null'){
		GreadId = 0;
	}
    
    keys[5] = "GreadId";
    values[5] = GreadId;
    
	keys[6] = "VideoRP";//分辨率 _256K_512K_768K_1080K
	values[6] = "_256K";
	
	var SessionId = strEnc(Cname,"adks1","adks2","adks3");//当前会话ID

	keys[7] = "SessionId";
	values[7] = SessionId;
	
	var Systime = (new Date()).getTime()//系统时间毫秒数
	keys[8] = "Systime";
	values[8] = Systime;
	var Location = 0;//时间点
	keys[9] = "Location";
	if(UserId!=null&&UserId!=""&&UserId!="null"&&UserId!="undefined"){
		$.ajax({
			async:false,
			type:"post",
			url:contextPath+"/course/CourseUserLocation.do?1=1&userId="+UserId+"&courseId="+CourseId+"&time="+(new Date()).getTime()+"&gradeId="+GreadId,
			data: "",
			dataType: "text",
			success:function(rests){
				Location = rests;
			}
		});
	}else{
		alert("请您先登录平台,再点击播放课程！");
		return;
	}
	values[9] = Location;
 	
	var keystu='adks2016'+CourseId+UserId+GreadId+Location+SessionId+ScormOrVideo+Systime;//加密参数
	keys[10] = "StudyKey";
    values[10] = hex_md5(keystu);
	var scormServer;//课件地址 videoFlag = Mobile or Pc
	$.ajax({
		async:false,
		type:"post",
		url:contextPath+"/course/getVideoServer.do?courseId="+CourseId+"&courseType="+ScormOrVideo,
		//url:contextPath+"/rest/getScormServer?courseId="+CourseId,
		data: "",
		dataType: "text",
		success:function(Servers){
			scormServer=Servers;
		}
	});
	/*
	keys[11] = "CourseName";//课程名称
    values[11] = Cname;
    
	keys[12] = "scormServer";//课件地址
    values[12] = scormServer;
    
    var OpenPath = contextPath+"/course/courseMain.jsp";
    */
	openUrl(scormServer, "Web", keys, values);
}

/* 
 * PC 端Post方式打开课程学习 
 */
function openUrl(url, CourseName , keys , values){  
	var parms;
    if (keys && values && (keys.length == values.length)){
        for ( var i = 0; i < keys.length; i++){
		      
		    if(parms==null||parms=="null"){
			    parms =  values[i];
		    }else{
			    parms += '/'+values[i];
		    }
        }
        url = url + '/'+ parms + '.shtml';
    }
	var tempForm = document.createElement("form");  
    tempForm.id = "tempForm1";  
    tempForm.method = "post";  
    tempForm.action = url;  
    tempForm.target = CourseName;  
    document.body.appendChild(tempForm);  
    tempForm.submit();
    document.body.removeChild(tempForm);
}
