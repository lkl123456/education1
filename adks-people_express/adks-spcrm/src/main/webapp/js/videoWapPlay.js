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
var CourseCode = strpath[7],//文件名
	videoPath = videoServer+"VIDEO/"+CourseCode,//流媒体 + 视频
	webposition = strpath[11],//播放位置
	videoRP = strpath[9],//视频分辨率 _256K_512K_768K_1080K
	
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
	
/*加载播放器*/
myJwPlayer = jwplayer('container').setup({
	file:videoPath,
    repeat:false,//"always",//重复播放
	autostart:true,//自动播放
    html5player: '/adks-spcrm/js/jwplayer.html5.js',
    flashplayer: '/adks-spcrm/js/Player.swf',
    width: '100%',
    height: '75%',
    smoothing:true,
	largecontrols:true,
	//stretching : 'fill',//拉伸播放
	controlbar : 'bottom', 
	/* 视频分辨率 _256P_512P_768P_1080P */
    //levels: mylevel,
	provider: "http","http.startparam":"starttime",
	/* modes配置项被用来指定渲染播放器不同浏览器技术的顺序 */
	modes: [
		 { type: 'html5' },
		 { type: 'flash', 'src': '/js/Player.swf' },
		 { type: 'download' }
	],
	
	allowFullScreen: true
	
}).onComplete(function (){
	//播放完成事件监听，展示课程问答，连播下一集
	$('#container').remove();//删除当前播放器div，手机端弹出层方便展示
	
	if($hasQuestion){
	   $('#showCourseQuestion').click();
	   
   }else{
	   //接着播放下一个选集
	   //$('#courseList_'+$belongedId_).next().click();
	   // 修改为：课程评价
	   $('#showCourseEvaluate').click();
   }
	
   
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
	
	if(addSessiontime == "" ||addSessiontime == "00:00:00" || addSessiontime == undefined || addSessiontime == null){
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
		var msg = "对不起、获取数据存在异常，可能无法正确记录进度信息。";
		alert(msg);
		return  false;
	}
}

/***************************************************************************************************************/
/***************************************** 分割线（页面相关操作交互功能）*********************************************/
/***************************************************************************************************************/

// 修改用户学分（积分）
function updateUserCredit_com(credit, ruleEname){
	$.ajax({
		async:true,
		type:"post",
		url:$webURL_+"rest/user/userUpdateCreditCom",
		data:{'credit':credit,'ruleEname':ruleEname,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"userUpdateCreditComReturn",
		type:'post',
		success:function(json){
			if('succ' == json){
				
			}
			if($('#courseList_'+$belongedId_).next().attr('id') != undefined){
				$('#courseList_'+$belongedId_).next().click();
			}else{
				$('#closeCourseEvaluate').click();
			}
		}
	});
}


function initWapObj(){
	$('.cgen_duode').click(function(){
		$('.list-group .list-group-item').show();
		$('.cgen_duode').hide();
		$('.cshow_qi').show();
	})
	$('.cshow_qi').click(function(){
		$('.list-group .list-group-item').eq(5).nextAll().hide();
		$('.cgen_duode').show();
		$('.cshow_qi').hide();
	})
	
	// 获取课程信息
	getCourseInfo();
	// 获取主题课程下的课程列表信息
	getCourseListByThemeId();
	// 获取课程下的试题信息
    getCourseQuestion();
    // 获取课程评价信息
    getCourseEvaluate();
}

// 获取课程信息
function getCourseInfo(){
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseInfoById',
		data:{'courseId':$belongedId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseInfoByIdReturn",
		type:'post',
		success:function(json){
			if(json != null && json != ''){
				$('#cname_show').html(json["name"]);
				$('#clickNum_show').html(json["clickNum"]);
				$('#chooseNum_show').html(json["chooseNum"]);
				$('#ccName_show').html(json["courseCatalogName"]);
				var _pathImg = json["pathImg"];
				if(_pathImg != null && _pathImg != ''){
					$('#fxImg').attr('src', $userHeadPicURL_+_pathImg); 
				}else{
					$('#fxImg').attr('src', $userHeadPicURL_+'/PTIMG/wxImg/1.jpg');
				}
			}
		}
	});
}

// 获取主题课程下的课程列表信息
function getCourseListByThemeId(){
	if($userId_ != null && $userId_ != '' && $userId_ != undefined && $userId_ != 'undefined'){
		$.ajax({
			async:true,
			url:$commonServiceURL_+'commonservice/rest/getCourseListByThemeIdExceptTheCourse',
			data:{'courseId':$belongedId_,'userId':$userId_},
			dataType : "jsonp",
			jsonp: "jsoncallback",
			jsonpCallback:"getCourseListByThemeIdExceptTheCourseReturn",
			type:"post", 
			success:function(data){
				if(data == null || data == ''){
					$("#courseOfTheme").empty();
					return 
				}
				$("#courseOfTheme").empty();
				var countNum = 0;
				for(var i = 0; i < data.length; i++){
					var id = "'"+data[i]["id"]+"'";
					var code = "'"+data[i]["code"]+"'";
					var cwtype = "'"+ data[i]["cwType"]+"'";
					var cname = "'"+data[i]["name"]+"'";
					var lastpotion = "'"+data[i]["lastPosition"]+"'";
					var videoRP = "'"+data[i]["videoRP"]+"'";
					
					var hreff = "javascript: BeginStudy("+id+","+code+","+cwtype+","+cname+","+lastpotion+","+videoRP+");";
					var lii;
					var itemId = "courseList_"+data[i]["id"];
					if(i < 5){							
						var t_style = "";
						if($belongedId_ == data[i]["id"]){
							t_style = 'style="background-color:#EEE; color:#167bca;"';
						}
						lii = '<a href="#" onclick="'+hreff+'" id="'+itemId+'" class="list-group-item" '+t_style+'><span class="glyphicon glyphicon-play-circle" style="margin-right:5px;"></span>'+data[i]['name']+'<span class="badge"><span class="glyphicon glyphicon-chevron-right"></span></span></a>';
					}else{
						var t_style = 'style="display:none;"';
						if($belongedId_ == data[i]["id"]){
							t_style = 'style="display:none; background-color:#EEE; color:#167bca;"';
						}
						lii = '<a href="#" onclick="'+hreff+'" id="'+itemId+'" class="list-group-item" '+t_style+'><span class="glyphicon glyphicon-play-circle" style="margin-right:5px;"></span>'+data[i]['name']+'<span class="badge"><span class="glyphicon glyphicon-chevron-right"></span></span></a>';
					}
					$("#courseOfTheme").append(lii);
					countNum = countNum + 1;
				}
				if(parseInt(countNum) > 5){
					$('.cgen_duode').show();
					$('.cshow_qi').hide();
				}
			}
		});
	}
}


//加载课程知识问答试题
function getCourseQuestion(){
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseQuestion',
		data:{'courseId':$belongedId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseQuestionReturn",
		type:'post',
		success:function(json){
			if(json['res'] == 'noQues'){
				//没有课程问答
				$hasQuestion = false;
			}else{
				$hasQuestion = true; //标示为 该课程 有问答
				$('.modal-body h4').text(json['name']);
				$('.modal-body').append('<input type="hidden" id="quesId" name="quesId" value="'+json['id']+'" />');
				$('.modal-body').append('<input type="hidden" id="optionType" name="optionType" value="'+json['questiontype']+'" />');
				var size = json['courseQanswerOptions'].length;
				$('.modal-body p').remove();
				
				if(size > 0){
					
					if(json['questiontype'] == 71){ //单选题
						
						for(var i = 0; i < size; i++){
							var name = json['courseQanswerOptions'][i]['name'];
							var optionname = json['courseQanswerOptions'][i]['optionName'];
							$('.modal-body').append('<label for="ans'+i+'" class="list-group-item"><input type="radio" name="ans" value="'+optionname+'" id="ans'+i+'" />'+optionname+'.'+name+'</label>');
							//$('.modal-body').append('<p><input type="radio" name="ans" value="'+optionname+'" />'+optionname+'.'+name+'</p>');
						}
					}else if(json['questiontype'] == 73){ //多选题
						for(var i = 0; i < size; i++){
							var name = json['courseQanswerOptions'][i]['name'];//答案名
							var optionname = json['courseQanswerOptions'][i]['optionName']; //选项名 a,b,c
							//$('.modal-body').append('<p><input type="checkbox" name="ans" value="'+optionname+'" />'+optionname+'.'+name+'</p>');
							$('.modal-body').append('<label for="ans'+i+'" class="list-group-item"><input type="checkbox" name="ans" value="'+optionname+'" id="ans'+i+'"/>'+optionname+'.'+name+'</label>');
						}
					}
				}else{
					if(json['questiontype'] == 77){
						
						//填空题
						$('.modal-body').append("<input type='text' id='uans' name='ans' />");
					}else if(json['questiontype'] == 75){
						//判断题
						$('.modal-body').append('<label for="ans'+i+'" class="list-group-item"><input type="radio" name="ans" value="'+1+'" id="ans'+i+'"/>'+"正确"+'</label>');
						$('.modal-body').append('<label for="ans'+i+'" class="list-group-item"><input type="radio" name="ans" value="'+0+'" id="ans'+i+'"/>'+"错误"+'</label>');
					}
				}
			}
		}
	});
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
	if(userAns == '' || userAns==null || userAns == undefined ){
		alert('请选择答案!');
		return false;
	}
	var quesId = $('#quesId').val();
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/checkUserAnswerForCourse',
		data:{'userAns':userAns,'quesId':quesId},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"checkUserAnswerForCourseReturn",
		type:'post',
		success:function(json){
			if(json['res'] == 'right'){
				alert('恭喜！您答对了！');
//				alert($('#courseList_'+$belongedId_).next().attr("id"));
				//$('#courseList_'+$belongedId_).next().click();
				$('#closeCourseQuestion').click();
				$('#showCourseEvaluate').click();
			}else{
				alert('很遗憾，您答错了...');
				//$('#closeCourseQuestion').click();
			}
		}
	});
}

function videoScoreClickFunc(num){
	var videoscoreInput = document.getElementById("videoscore");
	if(videoscoreInput){
		videoscoreInput.value = num;
	}
	for(var i = 1; i <= 5; i++){
		var videoscoreH = document.getElementById("videoscore" + i);
		if(!videoscoreH){
			continue;
		}
		if(i <= num){
			videoscoreH.className = "glyphicon glyphicon-star";
		}else{
			videoscoreH.className = "glyphicon glyphicon-star-empty";
		}
	}
}
// 获取课程评价信息
function getCourseEvaluate(){
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseEvaluateNum',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseEvaluateNumReturn",
		type:'post',
		success:function(json){
			// json为0时表示未评价
			//alert(json);
			if(json != 0){
				$('#c_e_btn').html('您已评价！重新评价');
				$('#c_e_btn_next').show();
			}
		}
	});
}
//更新,评价
function updateCourseEvaluate(){
	var evaluateScore = $('#videoscore').val();
	$.ajax({
		async:false,
		url:$webURL_+'rest/course/updateCourseEvaluate',
		data:{'courseId':$belongedId_,'userId':$userId_,'evaluateScore':evaluateScore},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"updateCourseEvaluateReturn",
		type:'post',
		success:function(json){
			if('yes' == json){
				alert('评价成功！');
				updateUserCredit_com(0, 'SOUPER_COURSE_PJ');
				//if($('#courseList_'+$belongedId_).next().attr('id') != undefined){
					//$('#courseList_'+$belongedId_).next().click();
				//}else{
					//$('#closeCourseEvaluate').click();
				//}
			}else {
				alert('评价未成功，请稍后再试！');
			}
		}
	});
}
// 跳过评价（针对已评价过的用户）
function nextCourse(){
	if($('#courseList_'+$belongedId_).next().attr('id') != undefined){
		$('#courseList_'+$belongedId_).next().click();
	}else{
		$('#closeCourseEvaluate').click();
	}
}

/********************* 播放方法，从前台拷贝修改 ********************/

function BeginStudy(CourseId , CourseCode , ScormOrVideo , Cname,lastPosition,videoRP){
	
	var keys = [];
    var values = [];
    
    keys[0] = "CourseId";//课程ID
    values[0] = CourseId;

    keys[1] = "ScormOrVideo";//三分屏或单视频  170/171
	values[1] = ScormOrVideo;
	
    keys[2] = "CourseCode";//课程编码或文件名
    values[2] = CourseCode;
	
    keys[3] = "UserId";
    values[3] = $userId_;
	
	keys[4] = "VideoRP";//分辨率 _256K_512K_768K_1080K
	values[4] = videoRP;
	
	var Systime = (new Date()).getTime()//系统时间毫秒数
	keys[5] = "Systime";
	values[5] = Systime;
	
	var Location = 0;//时间点
	keys[6] = "Location";
	values[6] = lastPosition;
 	
	var keystu='adks2016'+CourseId+UserId+lastPosition+ScormOrVideo+Systime;//加密参数
	keys[7] = "StudyKey";
    values[7] = hex_md5(keystu);
   
    openUrl($VideoWapServer_, keys, values);
}



/* 
 * PC 端Post方式打开课程学习 
 */
function openUrl(url, keys , values){  
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
	window.location.href = url;
}


