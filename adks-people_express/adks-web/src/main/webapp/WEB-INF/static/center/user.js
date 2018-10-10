//我的收藏 课程 播放
function coursesPlay(courseId,cwType){
	if(cwType!=null &&courseId!=null){
		if(cwType==170){
			window.open(contextPath+"/course/courseListPlayVideo.do?courseId="+courseId,"courseplay",'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-90)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
		}else if(cwType==171){ // 单视频 
			window.open(contextPath+"/course/courseListPlayVideo.do?courseId="+courseId,'upkjimg','height=750, width=1200, top=10, left=50, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
		}
	}
}


//移除我的收藏课程
function removeMyCollection(myCollectionid,courseId){
	$.ajax({
		async:false,
		url:contextPath+"/user/removeMyCollection.do?courseId="+courseId+"&userConId="+myCollectionid,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}