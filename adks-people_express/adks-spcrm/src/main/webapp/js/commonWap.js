
var CourseId;
var Cname;
var ScormOrVideo;
var CourseCode;
var UserId;
var GreadId="CourseSuper";
var VideoRP;
var SessionId = "adks20160719";
var Systime;
var Location_Init;
var StudyKey; 

var UrlPost=window.location.href;//获取URL地址
var strs= new Array(); //定义一数组 
strs=UrlPost.split("/"); //字符分割  

CourseId = strs[5];
ScormOrVideo = strs[6];
CourseCode = strs[7];
UserId = strs[8];
VideoRP = strs[9];
Systime = strs[10];
Location_Init = strs[11] ;
StudyKey = strs[12]; 

$(document).ready(function(){
	if(ScormOrVideo != '170'){
		$belongedId_ = CourseId; //单视频页面评论使用
		$userId_ = UserId; // 单视频使用
		
		//每1秒检测一次用户学习时长 三分屏的继续播放在课件里边
		setInterval('checkUserLive();', 1000);
	}if(ScormOrVideo == '173'){
		 $('#courseframe').attr('src',$BasePathURL_ +'course/'+CourseCode + '/sco1/play.htm');
	}else{
		 $('#courseframe').attr('src',$BasePathURL_ +'course/'+CourseCode + '/index.html?videoServer=' + videoServer + CourseCode + '/');
	}
	
	//每5秒执行一次保存课程进度(单视频和三分屏一样)
	setInterval('doAjaxPost();', 5000);
	
	//地址栏对比
	var keystu='adks2016'+CourseId+UserId+Location_Init+ScormOrVideo+Systime;//加密参数
	if(hex_md5(keystu)!=StudyKey.replace(".shtml", "")){
		
		window.location.href = "/adks-spcrm/fail.html";
	}
	
});

		 
//时间秒数格式化
function arrTimer_format(s) {
 var t;
 if(s > -1){
     hour = Math.floor(s/3600);
     min = Math.floor(s/60) % 60;
     sec = s % 60;
     day = parseInt(hour / 24);
     if (day > 0) {
         hour = hour - 24 * day;
         t = day + "day " + hour + ":";
     }else{
     	 if(hour < 10){
     	 	t="0"+ hour + ":";
     	 }else{
     	 	t = hour + ":";
     	 }
     }

     if(min < 10){
     	t += "0";
     }
     t += min + ":";
     if(sec < 10){
     	t += "0";
     }
     t += sec;
 }
 	return t;
 }

/*****************************************************************************************************************************/
 