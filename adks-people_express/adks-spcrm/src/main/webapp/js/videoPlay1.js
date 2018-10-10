/*
thePlayer.getState() 获取视频播放状态，
idle    为停止，
paused  为暂停，
触发播放事件thePlayer.play(true);playing 为播放中，触发暂停事件thePlayer.play(false);
thePlayer.seek(60);设置播放起始时间点
thePlayer.getPosition();获取播放时间点
thePlayer.getDuration();获取视频长度
*/
var videoPath = $("#videoServer").val(),//流媒体 + 视频
	webposition = $("#webposition").val();//播放位置

/*加载播放器*/
myJwPlayer = jwplayer('container').setup({
    playlist: [{ 
		sources: [{  
				    file: 'http://vod.cchvc.com:2335/files/poweruser/067c7bba-f566-11e4-b411-782bcb53e2a3/all.m3u8两会热点解读-小康社会' // 这里可以写m3u8的url' 
				},{ 
	   				file: 'http://vod.cchvc.com:2335/files/poweruser/067c7bba-f566-11e4-b411-782bcb53e2a3/all.m3u8两会热点解读-小康社会' // 这里可以写m3u8的url。 
			 	}] 
	}], 
    provider: 'http',
    autostart: true,
    html5player: 'js/jwplayer.html5.js',
    flashplayer: 'js/Player.swf',
    height: '600',
    width: '800',
    startparam: 'start'
});
    
function dd(){
	jwplayer('container').seek(webposition);
}
setTimeout(dd, 3000);


//***************************************************************

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