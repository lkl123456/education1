var lesson_location,position,currentPosition,Course_Duration;

/*
 *通过URL方式传递参数给控制器并返回字符串类型结果
 */
 var LastSessiontime = 0 ;//最后一次观看课程时长
 function doAjaxPost(){
   var addSessiontime_adks;
   //将观看时长转为秒数
   var Sessiontimes = currentPosition.split(":")[0]*60*60+currentPosition.split(":")[1]*60+currentPosition.split(":")[2]*1;
  
   if(LastSessiontime == 0){
	   addSessiontime_adks = currentPosition;
   }else if(Sessiontimes>LastSessiontime){
	   addSessiontime_adks = arrTimer_format(Sessiontimes - LastSessiontime);
   }else{
	   addSessiontime_adks = "00:00:00";
   }
   
   //将当前的播放时长秒数 存放在LastSessiontime
   LastSessiontime = Sessiontimes;
   //alert("Location: "+position);
   //alert("Sessiontime: "+currentPosition);
   //alert("Duration:  "+Course_Duration);
   //return false; 
   
   var addSessiontimes = addSessiontime_adks.split(":")[0]*60*60+addSessiontime_adks.split(":")[1]*60+addSessiontime_adks.split(":")[2]*1;
   if(addSessiontimes >0){
	   //当视频播放时的时候检测
	   if(Sessiontimes >= 10){
		   checkPostData(addSessiontime_adks);
	   }
	   
	   $.ajax({
	    type: "POST",
	    url: "/adks-spcrm/rest/AddCourse",
	    data: "CourseId=" + CourseId + "&UserId=" + UserId+ "&SessionId=" + SessionId + "&GreadId=" + GreadId + "&Location=" + position + "&Sessiontime=" + addSessiontime_adks + "&ScormOrVideo=" + ScormOrVideo + "&Systime=" + Systime + "&CourseCode=" + CourseCode + "&Duration=" + Course_Duration + "",
	    success: function(response){
	    	$('#info').html(response);
	 	},
	 	error: function(e){
	  		alert('Error: ' + "对不起，课程服务已暂停，暂时无法记录进度，请联系管理员核对后再继续学习！");
	    }
	   });
   }
  }

/****************************************************************************************************************/


function API() { }
  API.LMSInitialize = function(e) {
  	return "true";
  }
  var lesson_location = Location_Init;
  if(lesson_location==null || lesson_location==''){
	  lesson_location=0;
  }
  API.LMSSetValue = function(e,f) {
  	if(e == "cmi.core.lesson_location"){
  		if(f!=null && f!='' && f!=0 && f!= undefined){
  	  		position=f;
  	  	}
  	}
  	if(e == "cmi.core.session_time"){
  		if(f!=null && f!='' && f!=0 && f!= undefined){
  			currentPosition=f;
  	  	}
  	}
  	if(e == "adks.course.mp4.duration"){
  		if(f!=null && f!='' && f!=0 && f!= undefined){
  			Course_Duration=f;
  	  	}
  	}
  	
  	return true;
  }
  API.LMSGetLastError = function(e) {
    return 0;
  }

  API.LMSFinish = function(e) {
    return "true";
  }

  API.LMSGetValue = function(e) {
    if (e == "cmi.core.lesson_location") {
  	  return parseInt(lesson_location);
    }
    return "true";
  }

  API.LMSCommit = function(e) {
    return "true";
  }
  
/********************************************************************************************************************************/

//检测获取数据是否存在异常
function checkPostData(addSessiontime_adks){
	
	var checkData=true;//检测数据状态
	
	if(CourseId == "" || CourseId == undefined || CourseId == null){
		alert("异常数据CourseId："+CourseId);
		checkData=false;
	}
	
	if(UserId == "" || UserId == undefined || UserId == null){
		alert("异常数据UserId："+UserId);
		checkData=false;
	}
	
	if(SessionId == "" || SessionId == undefined || SessionId == null){
		alert("异常数据SessionId："+SessionId);
		checkData=false;
	}
	
	if(GreadId == "" || GreadId == undefined || GreadId == null){
		alert("异常数据GreadId："+GreadId);
		checkData=false;
	}
	
	if(position == "" || position == undefined || position == null){
		alert("异常数据position："+position);
		checkData=false;
	}
	
	if(addSessiontime_adks == "" ||addSessiontime_adks == "00:00:00" ||addSessiontime_adks == "00:00:01" || addSessiontime_adks == undefined || addSessiontime_adks == null){
//		alert("异常数据addSessiontime_adks："+addSessiontime_adks);
//		checkData=false;
	}
	
	if(ScormOrVideo == "" || ScormOrVideo == undefined || ScormOrVideo == null){
		alert("异常数据ScormOrVideo："+ScormOrVideo);
		checkData=false;
	}
	
	if(Systime == "" || Systime == undefined || Systime == null){
		alert("异常数据Systime："+Systime);
		checkData=false;
	}
	
	if(CourseCode == "" || CourseCode == undefined || CourseCode == null){
		alert("异常数据CourseCode："+CourseCode);
		checkData=false;
	}
	
	if(Course_Duration == "" || Course_Duration == undefined || Course_Duration == null){
		alert("异常数据Course_Duration："+Course_Duration);
		checkData=false;
	}
	
	//当数据异常时，弹出友好提示
	if(!checkData){
		var msg = "对不起、获取数据存在异常，可能无法正确记录进度信息，请选用谷歌、IE、火狐等主流浏览器进行学习。";
		checkBrowser(msg);
		return  false;
	}
}
  