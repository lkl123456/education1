$(document).ready(function(){
	var saveMsg = $('#saveMsg').val();
	if(saveMsg.length > 0 && saveMsg == 'error'){
		$('#msg').text('错误提示：您课程评论提交失败！');$("#msg").show();	
	}
});

/**
 * teacher star click
 * 
 * @param num
 */
function teacherscoreClickFunc(num){
	var teacherscoreInput = document.getElementById("teacherscore");
	if(teacherscoreInput){
		teacherscoreInput.value = num;
	}
	for(var i = 1; i <= 5; i++){
		var teacherscoreH = document.getElementById("teacherscore" + i);
		if(!teacherscoreH){
			continue;
		}
		if(i <= num){
			teacherscoreH.className = "glyphicon glyphicon-star";
		}else{
			teacherscoreH.className = "glyphicon glyphicon-star-empty";
		}
	}
}

/**
 * teach star click
 * 
 * @param num
 */
function teachscoreClickFunc(num){
	var teachscoreInput = document.getElementById("teachscore");
	if(teachscoreInput){
		teachscoreInput.value = num;
	}
	for(var i = 1; i <= 5; i++){
		var teachscoreH = document.getElementById("teachscore" + i);
		if(!teachscoreH){
			continue;
		}
		if(i <= num){
			teachscoreH.className = "glyphicon glyphicon-star";
		}else{
			teachscoreH.className = "glyphicon glyphicon-star-empty";
		}
	}
}

/**
 * video score click
 * 
 * @param num
 */
function videoscoreClickFunc(num){
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

// 保存课程评价信息
function courseEvaluateSave(cpath){
	var courseId = $("#courseId").val();
	var teacherscore = $("#teacherscore").val();
	var teachscore = $("#teachscore").val();
	var videoscore = $("#videoscore").val();
	$.ajax({
		async:false,
		url:cpath+"/api/adks/courseEvaluateSaveYz.do?courseId="+courseId+"&teacherscore="+teacherscore+"&teachscore="+teachscore+"&videoscore="+videoscore,
		type:"post", 
		success:function(succ){
			if(succ == "succ"){
			    document.location.href = cpath+'/api/adks/toCourseEvaluateListYz.do?courseId='+courseId+'&isEvaluate=1';
			}else if(succ == "error"){
			    $('#msg').text('错误提示：您课程评论提交失败！');$("#msg").show();	
			}
		}
	});
}
