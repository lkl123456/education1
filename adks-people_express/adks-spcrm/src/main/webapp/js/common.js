
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

$(document).ready(function(){
	//设置播放页课程名称
	document.title = Cname;//title
	//$("#cname").html(Cname);
	//document.getElementById("cname").value=Cname;
	
	if(ScormOrVideo != '170'){
		$belongedId_ = CourseId; //单视频页面评论使用
		$userId_ = UserId; // 单视频使用
		
		//每1秒检测一次用户学习时长 三分屏的继续播放在课件里边
		setInterval('checkUserLive();', 1000);
	}if(ScormOrVideo == '173'){
		 $('#courseframe').attr('src',$BasePathURL_ +'course/'+CourseCode + '/sco1/play.htm');
	}else{
		$('#courseframe').attr('src',$BasePathURL_ +'course/'+CourseCode + '/index.html?videoServer=' + videoServer+ "SCORM/" + CourseCode + '/');
	}
	
	//每5秒执行一次保存课程进度(单视频和三分屏一样)
	setInterval('doAjaxPost();', 5000);
	
	//判断当前课件打开是否有异常
	var Systime_Local = (new Date()).getTime()//系统时间毫秒数
	
	//打开页面超过5秒、
	if((Systime_Local-Systime)/1000 >5 ){
		//alert(Systime);
		//alert(Systime_Local);
		//alert(((Systime_Local-Systime)/1000));
		alert("请求超时,请重新打开！");
		window.location.href = "/adks-spcrm/fail.html";
	}
	//地址栏对比
	//var keystu='adks2016'+CourseId+UserId+GreadId+Location_Init+SessionId+ScormOrVideo+Systime;//加密参数
	//if(hex_md5(keystu)!=StudyKey.replace(".shtml", "")){
		//window.location.href = "/adks-spcrm/fail.html";
	//}
	
});

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
//JS判断浏览器类型的通用方法代码如下：
function UA() {
	var browsertype="";
	function c(browser) {
		return navigator.userAgent.toLowerCase().indexOf(browser) > -1;
	}
	
	return browsertype=c('opera')===true?'opera':(c('msie') && c('360se'))===true?'360se':((c('msie') && c('tencenttraveler')) && c('metasr'))===true?"sogobrowser":(c('msie') && c('qqbrowser'))===true?"QQbrowser":(c('msie') && c('tencenttraveler'))===true?"TTbrowser":c('msie')===true?'msie':(c('safari') && !c('chrome'))===true?'safari':c('maxthon')===true?"maxthon":((c('chrome') && c('safari')) && c('qihu 360ee'))===true?"360ee":(c('chrome') && c('taobrowser'))===true?"taobrowser":c('chrome')===true?"chrome":((c('gecko') && !c('webkit')) && c('seamonkey'))===true?"SeaMonkey":((c('gecko') && !c('webkit')) && !c('netscape'))===true?'firefox':((c('gecko') && !c('webkit')) && c('netscape'))===true?'netscape':"other";
}
//获取浏览器版本信息
function checkBrowser(msg){
	var info =msg+"\n\n";
	info +="当前浏览器信息如下：\n内核：";
	if(UA().toLowerCase()=="firefox" || UA().toLowerCase()=="seamonkey"){//火狐浏览器
		info += "火狐浏览器\n";
		info +="版本：("+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf("/"));
		if(info.indexOf("gecko")>=0){
			info = info.replace("like gecko", "");
		}
	}else if (UA().toLowerCase()=="opera"){//Opera浏览器
		info += "Opera浏览器\n";
		info +="版本：("+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf("/"));
	}else if (UA().toLowerCase()=="netscape"){//网景浏览器
		info += "网景浏览器\n";
		info += "版本："+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf("/"),navigator.userAgent.toLowerCase().lastIndexOf(" "));
	}else if (UA().toLowerCase()=="taobrowser"){//淘宝浏览器
		alert("你正在使用:"+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf("/")));
	}else if (UA().toLowerCase()=="qihu 360ee"){
		info += "360浏览器\n";
		info +="版本："+UA().toLowerCase();
	}else if (UA().toLowerCase()=="safari"){//safari浏览器
		info += "safari浏览器\n";
		info +="版本："+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf("/"));
	}else if (UA().toLowerCase()=="chrome"){//谷歌浏览器
		info += "谷歌浏览器\n";
		info += "版本："+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf(UA().toLowerCase()),navigator.userAgent.toLowerCase().lastIndexOf(" "));
	}else if (UA().toLowerCase()=="maxthon"){//遨游浏览器
		info += "遨游浏览器\n";
		info += "版本："+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf(UA().toLowerCase()),navigator.userAgent.toLowerCase().lastIndexOf('chrome'));
	}else if (UA().toLowerCase()=="360se"){//360浏览器
		info += "360浏览器\n";
		info +="版本："+UA().toLowerCase();
	}else if (UA().toLowerCase()=="qqbrowser"){//QQ浏览器
		info += "QQ浏览器\n";
		info +="版本："+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf("/"));
	}else if (UA().toLowerCase()=="ttbrowser"){
		info += "QQ浏览器\n";
		info +="版本："+UA().toLowerCase()+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf(" "),navigator.userAgent.toLowerCase().lastIndexOf(")"));
	}else if (UA().toLowerCase()=="msie"){//IE浏览器
		info += "IE浏览器\n";
		info +="版本："+navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf(UA().toLowerCase())).substring(0,navigator.userAgent.toLowerCase().substring(navigator.userAgent.toLowerCase().lastIndexOf(UA().toLowerCase())).indexOf(";"));
	}else{
		info += "未知\n";
		info +="版本："+UA().toLowerCase();
	}
	alert(info);
}
//显示提示信息，3秒后自动消失
function ShowEventMsg(msg,flag){
	document.getElementById("allts").innerHTML=msg;
	
	if(flag=='Y'){
		document.getElementById("allts").style.background="url(/adks-spcrm/img/allts.jpg) no-repeat";
	}else{
		document.getElementById("allts").style.background="url(/adks-spcrm/img/allts1.jpg) no-repeat";
	}
	document.getElementById("allts").style.display="inline";
	setTimeout("yc('allts')",1000);
}

//视频播放提示续播信息
function shootVideoMsg(){
	document.getElementById("shootId").style.display="inline";
	document.getElementById("shootNum").innerHTML=arrTimer_format(Location_Init);
	setTimeout("yc('shootId')",5000);
}

//隐藏div
function yc(divid){
	document.getElementById(divid).style.display="none";
}
