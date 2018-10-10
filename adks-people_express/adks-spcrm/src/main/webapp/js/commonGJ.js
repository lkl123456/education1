//当前项目地址
var $BasePathURL_ = "http://192.168.1.74/adks-spcrm/"; 

//web地址
var $webURL_ = "http://192.168.1.74/"; 

//图片地址
var $userHeadPicURL_ = "http://www.lllnet.net:1889/";

//通用服务地址
var $commonServiceURL_ = "http://192.168.1.74/";

//评论所属类型（eg： 课程，活动...）
var $commentType_ = 794;

//流媒体地址
var videoServer = "http://59.46.46.83:88/";

//主体ID 如（当前课程，活动等...的ID）
var $belongedId_ ;  
//用户id
var $userId_ ;
   
//课程评价
var $evaluateScore = 0;

var playCount = 0;
var playTime = 5000;
var nowTime = null;



var CourseId;
var Cname;
var ScormOrVideo;
var CourseCode;
var UserId;
var GreadId;
var VideoRP;
var SessionId;
var Systime;
var Location_Init;
var StudyKey; 

$(document).ready(function(){
	var UrlPost=window.location.href;//获取URL地址
	var strs= new Array(); //定义一数组 
	strs=UrlPost.split("/"); //字符分割  
	
    CourseId = strs[5];
	Cname = strDec(strs[6],"adks1","adks2","adks3");
	ScormOrVideo = strs[7];
	CourseCode = strs[8];
	UserId = strs[9];
	GreadId = strs[10];
	VideoRP = strs[11];
	SessionId = strs[12];
	Systime = strs[13];
	Location_Init = strs[14] ;
	StudyKey = strs[15]; 
	
	//设置播放页课程名称
	document.title = Cname;//title
	$("#cname").html(Cname);
	$belongedId_ = CourseId;
	$userId_ = UserId;
	
	//每5秒执行一次保存课程进度
	setInterval('doAjaxPost();', 5000);
	
	//每1秒检测一次用户学习时长
	setInterval('checkUserLive();', 1000);
	
	//判断当前课件打开是否有异常
	var Systime_Local = (new Date()).getTime()//系统时间毫秒数
	
	/**
	
	//打开页面超过5秒、
	if((Systime_Local-Systime)/1000 >5 ){
		
		window.location.href = "/adks-spcrm/fail.html";
	}
	//地址栏对比
	var keystu='adks2016'+CourseId+UserId+GreadId+Location_Init+SessionId+ScormOrVideo+Systime;//加密参数
	if(hex_md5(keystu)!=StudyKey.replace(".shtml", "")){
		
		window.location.href = "/adks-spcrm/fail.html";
	}
	
	*/
	
});


/*
*通过URL方式传递参数给控制器并返回字符串类型结果
*/
var LastSessiontime = 0 ;//最后一次观看课程时长
function doAjaxPost(){
   
   var Location = parseInt(jwplayer().getPosition());
   var Sessiontime = getPlayTime();
   var Duration = arrTimer_format(parseInt(jwplayer().getDuration()));
   
   var addSessiontime;//观看时长增量
   if(LastSessiontime == 0){
   	   addSessiontime = arrTimer_format(Sessiontime);
   }else if(Sessiontime > LastSessiontime){
   	   addSessiontime = arrTimer_format(Sessiontime - LastSessiontime);
   }else{
   	   addSessiontime = "00:00:00";
   }
   
   LastSessiontime = Sessiontime;//将当前的播放时长秒数 存放在LastSessiontime
   //alert("视频时长:"+Duration);
   //alert("Location:"+Location);
   //alert("addSessiontime:"+addSessiontime);
   //return false;
   var addSessiontimes = addSessiontime.split(":")[0]*60*60+addSessiontime.split(":")[1]*60+addSessiontime.split(":")[2]*1;
   
   if(addSessiontimes >0 && Location >0 && Duration!='00:00:00'){
	   $.ajax({
	    type: "POST",
	    url: "/adks-spcrm/rest/AddCourse",
	    data: "CourseId=" + CourseId + "&UserId=" + UserId+ "&SessionId=" + SessionId + "&GreadId=" + GreadId + "&Location=" + Location + "&Sessiontime=" + addSessiontime + "&ScormOrVideo=" + ScormOrVideo + "&Systime=" + Systime + "&CourseCode=" + CourseCode + "&Duration=" + Duration + "",
	    success: function(response){
	    	$('#info').html(response);
	 	},
	 	error: function(e){
	  		alert('Error: ' + "对不起，课程服务已暂停，暂时无法记录进度，请联系管理员核对后再继续学习！");
	    }
	   });
   }
}

//检测视频是否播放			 

function  checkUserLive(){
	 if (myJwPlayer.getState() == 'PLAYING') {
			bTime += 1;
         	if (bTime % 600 == 0 && bTime != 0) {
				bTime = 0;
                myJwPlayer.play(false);
         		//显示监控信息      
         		showMessage("课程监控","重新开始","继续学习");
             }
     }else{
     	bTime = 0;
     }
}

//课程监控
function showMessage(msg,buttonTex1,buttonTex2) {
    var mask = $('.toshare');
    var tip = $('.fzdm');
    $("#fxz").html(msg);
    tip.html( '<input id="restudy" type="button" class="restudy" value=' + buttonTex1 + ' />' + '<input id="jxstudy" type="button" class="jxstudy" value='+ buttonTex2 + ' />');
   	//重新开始
   	$('#restudy').click(
    function () {
        mask.hide();
        jwplayer('container').seek(0);
        callback()
    });
    
    //继续学习
    $('#jxstudy').click(
    function () {
        mask.hide();
        myJwPlayer.play(true); 
        callback()
    });
    mask.show();
}

//初始化播放信息
function playinit(msg,buttonTex1,buttonTex2,Location_Init) {
    var mask = $('.toshare');
    var tip = $('.fzdm');
    $("#fxz").html(msg);
    tip.html( '<input id="restudy" type="button" class="restudy" value=' + buttonTex1 + ' />' + '<input id="jxstudy" type="button" class="jxstudy" value='+ buttonTex2 + ' />');
   	//重新开始
   	$('#restudy').click(
    function () {
        mask.hide();
        jwplayer('container').seek(0);
        callback()
    });
    
    //继续学习
    $('#jxstudy').click(
    function () {
    	myJwPlayer.play(true);
        mask.hide();
        jwplayer('container').seek(Location_Init);
        callback()
    });
    mask.show();
}		
 //判断加密是否正确
 var StudySecret = '${StudySecret}';
 function addressInit(){
 	
 	if(StudySecret == 'false' || StudySecret == 'timeout'){
 		alert('友情提示：对不起，您有以下非法操作：\n\n 1、刷新播放窗口\n 2、修改播放窗口参数\n 3、复制地址到其它地方\n\n 影响与处理如下：\n\n 影响：无法记录学习进度\n 建议：请重新打开该课件\n 处理：系统将自动关闭窗口或选项卡');
 		window.close();
 	}
 	
 	if(StudySecret == 'sessionout'){
 		alert('友情提示：对不起，登录时间已超时\n\n 影响：系统无法记录学习时间\n 建议：重新登录学习课程');
 		window.close();
 	}
 }
		 
//记录时间 播放时间
function getPlayTime() {
	if (nowTime == null) {
        nowTime = new Date().getTime();
    }
    
    if(myJwPlayer.getState()=='PLAYING'){
	   	playTime = playTime + (new Date().getTime() - nowTime);
    }
   	nowTime = new Date().getTime();

    return Math.round(playTime / 1000);
}

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
