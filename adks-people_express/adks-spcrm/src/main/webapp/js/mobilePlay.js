/*
thePlayer.getState() 获取视频播放状态，
idle    为停止，
paused  为暂停，
触发播放事件thePlayer.play(true);playing 为播放中，触发暂停事件thePlayer.play(false);
thePlayer.seek(60);设置播放起始时间点
thePlayer.getPosition();获取播放时间点
thePlayer.getDuration();获取视频长度
*/
var UrlPath=window.location.href;//获取URL地址
var strpath= new Array(); //定义一数组 
strpath=UrlPath.split("/"); //字符分割  

var videoPath = videoServer+"VIDEO/"+strpath[8],//流媒体 + 视频
	CourseCode = strpath[8],//文件名
	CourseName = strDec(strpath[6],"adks1","adks2","adks3"),//视频名称
	webposition = strpath[14],//播放位置
	videoRP = strpath[11],//视频分辨率 _256K_512K_768K_1080K
	
	mylevel = [],//高清等级数组
	play_flag = true;

	/* 流畅：480*270，  码流256 */
	if(videoRP.indexOf("256K") > 0 ){
	    mylevel.push( { bitrate: 300, label: "流 畅 ", file: videoServer+"VIDEO/"+CourseCode, width:320});
	} 
	/* 普清：720*480，  码流512 */
	if (videoRP.indexOf("512K") > 0) {
	    mylevel.push({ bitrate: 600, label: "普  清 ",  file: videoServer +"VIDEO/"+ CourseCode.substring(0,CourseCode.length-4)+"_512K.mp4", width: 480});
	}
	/* 高清：1280*720， 码流768 */
	if (videoRP.indexOf("768K") > 0) {
	    mylevel.push({ bitrate: 900, label: "高  清 ",   file: videoServer +"VIDEO/"+ CourseCode.substring(0,CourseCode.length-4)+"_768K.mp4", width: 720});
	}
	/* 超清：1920*1080，码流1024 */
	if (videoRP.indexOf("1080K") > 0) {
	    mylevel.push({ bitrate: 1080, label: "超  清 ",  file: videoServer +"VIDEO/"+ CourseCode.substring(0,CourseCode.length-4)+"_1080K.mp4", width: 1080})
	}
//********************************************************************
/*加载播放器*/
myJwPlayer = jwplayer('container').setup({
	file:videoPath,
    repeat:"always",//重复播放
	autostart:true,//自动播放
    html5player: '/adks-spcrm/js/jwplayer.html5.js',
    flashplayer: '/adks-spcrm/js/Player.swf',
    width: '100%',
    height: '100%',
	/* 视频分辨率 _256P_512P_768P_1080P */
    //levels: mylevel,
	
	/* modes配置项被用来指定渲染播放器不同浏览器技术的顺序 */
	modes: [
		 { type: 'html5' },
		 { type: 'flash', 'src': '/js/Player.swf' },
		 { type: 'download' }
	],
	
	allowFullScreen: true
	
});

//视频时间点定位    
if(webposition>0){
	jwplayer('container').seek(webposition);
}
//********************************************************************
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

     if(min < 10){t += "0";}
         t += min + ":";
     if(sec < 10){t += "0";}
         t += sec;
 }
 return t;
 } 
 
//**************************************************************************************
/*
*通过URL方式传递参数给控制器并返回字符串类型结果
*/
//var playCount = 0;
var playTime = 5000;
var nowTime = null;
var LastSessiontime = 0 ;//最后一次观看课程时长
function doAjaxPost(){
   var Location = parseInt(jwplayer().getPosition());
   var Sessiontime = getPlayTime();
   var Duration = parseInt(jwplayer().getDuration());
   
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

/***************************单视频播放参数配置******************************/
//检测获取数据是否存在异常
function checkPostData(Location,addSessiontime,Duration){
	
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
	
	if(Location == "" || Location == undefined || Location == null){
		alert("异常数据Location："+Location);
		checkData=false;
	}
	
	if(addSessiontime == "" ||addSessiontime == "00:00:00" ||addSessiontime == "00:00:01" || addSessiontime == undefined || addSessiontime == null){
		alert("异常数据addSessiontime："+addSessiontime);
		checkData=false;
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
	
	if(Duration == ""  ||Duration == "00:00:00" ||Duration == "00:00:01" || Duration == undefined || Duration == null){
		alert("异常数据Duration："+Duration);
		checkData=false;
	}
	
	//当数据异常时，弹出友好提示
	if(!checkData){
		var msg = "对不起、获取数据存在异常，可能无法正确记录进度信息，请选用谷歌、IE、火狐等主流浏览器进行学习。";
		checkBrowser(msg);
		return  false;
	}
}
