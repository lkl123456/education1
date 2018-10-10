/*
thePlayer.getState() 获取视频播放状态，
idle    为停止，
paused  为暂停，
触发播放事件thePlayer.play(true);playing 为播放中，触发暂停事件thePlayer.play(false);
thePlayer.seek(60);设置播放起始时间点
thePlayer.getPosition();获取播放时间点
thePlayer.getDuration();获取视频长度
*/


//流媒体地址
var videoServer = "http://59.46.46.83:88/";


var UrlPath=window.location.href;//获取URL地址
var strpath= new Array(); //定义一数组 
strpath=UrlPath.split("/"); //字符分割  

var videoPath = videoServer+"VIDEO/"+strpath[8],//流媒体 + 视频
	CourseCode = strpath[6],//文件名
	/// CourseName = strDec(strpath[6],"adks1","adks2","adks3"),//视频名称
	/// webposition = strpath[14],//播放位置
	//videoRP = strpath[11],//视频分辨率 _256K_512K_768K_1080K
	mylevel = [],//高清等级数组
	play_flag = true;
    mylevel.push( { bitrate: 300, label: "流 畅 ", file: videoServer+"VIDEO/"+CourseCode, width:320});
	
//********************************************************************
/*加载播放器*/
myJwPlayer = jwplayer('container').setup({
	file:videoPath,
    repeat:"always",//重复播放
	autostart:true,//自动播放
    html5player: '/adks-spcrm/js/jwplayer.html5.js',
    flashplayer: '/adks-spcrm/js/Player.swf',
    width: '100%',
    height: '90%',
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
 
