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

var videoPath = videoServer+"VIDEO/"+CourseCode,//流媒体 + 视频
	CourseCode = strpath[8],//文件名
	CourseName = strDec(strpath[6],"adks1","adks2","adks3"),//视频名称
	webposition = strpath[14],//播放位置
	videoRP = strpath[11],//视频分辨率 _256K_512K_768K_1080K
	
	mylevel = [],//高清等级数组
	play_flag = true;

	/* 流畅：480*270，  码流256 */
	if(videoRP.indexOf("256K") > 0 ){
	    mylevel.push( { bitrate: 300, label: "流 畅 ", file: videoServer +"VIDEO/"+ CourseCode, width:320});
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
    repeat:false,//"always",//重复播放
	autostart:true,//自动播放
    html5player: '/adks-spcrm/js/jwplayer.html5.js',
    flashplayer: '/adks-spcrm/js/Player.swf',
    width: '930',
    height: '526',
    smoothing:true,
	largecontrols:true,
	//bufferlength:'5',//缓冲时间
	//stretching : 'fill',//拉伸播放
	controlbar : 'bottom', 
	/* 视频分辨率 _256P_512P_768P_1080P */
    levels: mylevel,
	provider: "http","http.startparam":"starttime",
	/*系列视频
	playlist: [
		 { duration: 1, title:  "意大利语初级第四课对话", description: "大麦抗病－WRKY1、2和MYB6的相互作用",   file: videoPath, image:"img/20141127162410953.jpg"},
		 { duration: 20, title:  "意大利语初级第四课对话", description: "大麦抗病－WRKY1、2和MYB6的相互作用",   file: videoPath, image:"img/20141127162410953.jpg"},
		 { duration: 30, title:  "大麦抗病－WRKY1、2和MYB6的相互作用", description: "大麦抗病－WRKY1、2和MYB6的相互作用",   file: videoPath, image:"img/20141127162410953.jpg"},
		 { duration: 542, title: "意大利语初级第四课对话", description: "意大利语初级第四课对话",  file: videoPath, image:"img/20141127162656359.jpg"}
	],
	listbar: {
		 position: "right",
		 size: 240
	},
	
	dock:true,
	*/
	/* modes配置项被用来指定渲染播放器不同浏览器技术的顺序 */
	modes: [
		 { type: 'html5' },
		 { type: 'flash', 'src': '/js/Player.swf' },
		 { type: 'download' }
	],
	
	allowFullScreen: true
	
}).onComplete(function (){
	//播放完成事件监听，展示课程问答，连播下一集
	if($hasQuestion){
	   $('#showCourseQuestion').click();
	   
   }else{
//	   alert('没有课程问答，直接连播下一集...');
	   //接着播放下一个选集
	   var currId = $('.themeCourse li.visited').attr('id');
	   var nextId = parseInt(currId) + 1;
	   $('#'+nextId+' a').click();
   }
	
   
});
//视频时间点定位    
if(webposition>0){
	shootVideoMsg();
	jwplayer('container').seek(Location_Init);
	//playinit("您已学习到　"+arrTimer_format(webposition),"重新开始","继续学习",webposition);
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
        callback();
    });
    
    //继续学习
    $('#jxstudy').click(
    function () {
    	myJwPlayer.play(true);
        mask.hide();
        jwplayer('container').seek(Location_Init);
        callback();
    });
    mask.show();
}
 //**************************************************************************************
  /***************************单视频播放数据传输方法******************************/
// var playCount = 0;
 var playTime = 5000;
 var nowTime = null;
 
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
   
   //当视频播放状态时检测播放参数
   if (myJwPlayer.getState() == 'PLAYING') {
	   //10秒钟后开始检测，避免页面加载未完成	   
	   if (bTime >= 10) {
		   checkPostData(Location,addSessiontime,Duration);
	   }
   }
   
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
         	if (bTime % checkTimes == 0 && bTime != 0) {
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
        callback();
    });
    
    //继续学习
    $('#jxstudy').click(
    function () {
        mask.hide();
        myJwPlayer.play(true); 
        callback();
    });
    mask.show();
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

//提交用户的 课程问答的答案到后台。比对答案，返回信息
function submitUserAns(){
	var optionType = $("#optionType").val();
	var userAns = "";
	if(optionType == 71){
		userAns = $('.modal-body input:radio:checked').val();
	}else if(optionType == 73){
		
		$('.modal-body input:checkbox').each(function(){
			if(this.checked){
				userAns = userAns + $(this).val()+";";
			}
		});
	}else if(optionType == 77){
		userAns = $('#uans').val();
	}else if(optionType == 75){
		userAns = $('.modal-body input:radio:checked').val();
	}
//	alert(userAns);
	if(userAns == '' || userAns==null || userAns == undefined ){
		ShowEventMsg("对不起、请选择答案!","N");
		return false;
	}
//	alert('您的答案是：'+userAns);
	var quesId = $('#quesId').val();
	$.ajax({
		async:true,
		url:$commonServiceURL_ + 'commonservice/rest/checkUserAnswerForCourse',
		data:{'userAns':userAns,'quesId':quesId},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"checkUserAnswerForCourseReturn",
		type:'post',
		success:function(json){
//			alert(json['res']);
			if(json['res'] == 'right'){
			   setTimeout("studyNext()",1000);
			   ShowEventMsg("恭喜、回答正确，即将播放下集！","Y");
			}else{
			    ShowEventMsg("对不起、回答错误，请重新回答！","N");
				//$('#closeCourseQuestion').click();
			}
		}
	});
}
//播放下一门课件
function studyNext(){
	var currId = $('.themeCourse li.visited').attr('id');
	var nextId = parseInt(currId) + 1;
	$('#'+nextId+' a').click();
}
