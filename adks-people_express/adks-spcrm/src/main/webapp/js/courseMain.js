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
		}
	});
}

//当前页面跳转
function changeHref(href){
	window.location.href=$webURL_+ href ;
}
//页面跳转到电子书刊
function toInterestIndex(){
	$.ajax({
		async:true,
		type:"post",
		url:$webURL_+"rest/user/interestIndex",
		data:{'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"InterestIndexReturn",
		type:'post',
		success:function(json){
			//alert(json)
			if("error" != json){
				window.location.href=json; 
			}
		}
	});
}
//初始化页面数据
function initObjList(){
	//我的笔记大家笔记tab切换效果
    $(".note_list dt ul li").mouseover(function () {
		$(".note_list dt ul li").removeClass("m_hover");
		$(this).addClass("m_hover");
		var $kwu = $(this).index(".note_list dt ul li");
		$(".note_list dd").hide();
		$(".note_list dd").eq($kwu).show();
	});
	
    //加载课程试题信息
    getCourseQuestion();
    //加载主题下其他所有课程信息列表
    getThemeOtherCourseList();
	//加载笔记列表
    getCourseNoteList();
    //加载评价信息
    getCourseEvaluateNum();
    //加载收藏信息
    getCourseCollectionInfo();
    //加载用户信息
    getUserInfo();
    //加载分类信息
    getCourseCatalogInfo();
    //加载相关课程
    getXgkcCourse();
    //初始化评论分页数据
    getCommentsPageInfo();
     
    $('.yonhu_xy_div img').click(function(){
		$('.yonhu_xy').hide();
	});
    
    
}

/*****************************笔记方法开始******************************************************************************/
//笔记提交button
function submitNote(){
    if($userId_ == null || $userId_ == '' || $userId_ == 'undefined' || $userId_ == undefined){
	    ShowEventMsg("对不起、登录后才能发表笔记！","N");
		return
	}
    if(!$isUserOnline){
		ShowEventMsg("对不起、登录后才能发表笔记！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
   	//校验笔记是否可以提交
	var noteContentRealValue = $.trim($('#noteContent').val());
	if(noteContentRealValue==''){
		ShowEventMsg("对不起、笔记内容不能为空","N");
		return;
	}else if (noteContentRealValue.length>200) {
	  	//alert('笔记长度不能超过200'); 
	  	ShowEventMsg("对不起、笔记长度不能超过200","N");
	  	return;
	}
	  
	var state = 0 ;
	if($('#isPrivate').is(':checked')){
		state = 1;
	}
	
	var studytimelong = myJwPlayer.getPosition(); 
	//提交笔记
	$.ajax({
		async:false,
		type:"post",
		url:$webURL_+"rest/course/saveCourseNote",
		data:{'courseId':$belongedId_,'content':noteContentRealValue,'state':state,'userId':$userId_,'studytimelong':studytimelong},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseEvaluateNumReturn",
		type:'post',
		success:function(json){
			if('ok' == json){
				$('#noteContent').val('');
				$("#isPrivate").attr("checked",false);
				//刷新笔记列表
				getCourseNoteList();
				// 笔记提交成功后，增加用户积分
				updateUserCredit_com(0, 'TAKE_NOTES');
				ShowEventMsg("恭喜您、笔记提交成功！","Y");
			}
		}
	});
		
}

//加载主题下其他所有课程信息列表
function getThemeOtherCourseList(){
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseListByThemeIdExceptTheCourse',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseListByThemeIdExceptTheCourseReturn",
		type:'post',
		success:function(json){
			var datalen = json.length;
			$('.themeCourse').empty();
			var actionId;  //将要赋值给每个li标签的id值，其实就是li标签的索引，第N个
			for(var i = 0; i < json.length; i++){
				$('.hidThemeCourses li a').text(json[i]['name']);
				var courseId = "'"+json[i]['id']+"'";
				var courseCode = "'"+json[i]['code']+"'";
				var cwType = "'"+json[i]['cwType']+"'";
				var courseName = "'"+json[i]['name']+"'";
				var lastpotion = "'"+json[i]['lastPosition']+"'";
				var videoRP = "'"+json[i]['videoRP']+"'";
				                       
				$('.hidThemeCourses li a').attr('name','ThemeCourses'+courseId);
				$('.hidThemeCourses li a').attr('onclick','BeginStudy('+courseId+','+courseCode+','+cwType+','+courseName+','+lastpotion+','+videoRP+')');
//				$('.hidThemeCourses li').attr('id','course_'+json[i]['id']);
				$('.hidThemeCourses li').attr('id',i+1);
				if($belongedId_ == json[i]['id']){
					$('.hidThemeCourses li').addClass('visited');
					actionId = i+1;
				}else{
					$('.hidThemeCourses li').removeClass('visited');
				}
				$('.themeCourse').append($('.hidThemeCourses li').clone(true));
			}
			var perc = actionId/datalen; //百分比
			var alltop = (datalen-10) * 46; // 计算 scrollTop最大值
			var last = (alltop+456)*perc - (46*4); //计算最终给该滚动条设置的scrollTop值
			
//			alert(last);
			// 38 -- 1296    46
			// 46 -- 1664    46
			// 16 -- 284	47			==>每个li标签高度横跨 为 46px
			// 40 -- 1388	46
//			var centerindex = alltop/2;
			
			$('.themeCourse').scrollTop(last);
//			alert($('.themeCourse').scrollTop());
			
		}
	});
	
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
							$('.modal-body').append('<p><input type="radio" name="ans" value="'+optionname+'" />'+optionname+'.'+name+'</p>');
						}
					}else if(json['questiontype'] == 73){ //多选题
						for(var i = 0; i < size; i++){
							var name = json['courseQanswerOptions'][i]['name'];//答案名
							var optionname = json['courseQanswerOptions'][i]['optionName']; //选项名 a,b,c
							$('.modal-body').append('<p><input type="checkbox" name="ans" value="'+optionname+'" />'+optionname+'.'+name+'</p>');
						}
					}
				}else{
					if(json['questiontype'] == 77){
						
						//填空题
						$('.modal-body').append("<input type='text' id='uans' name='ans' />");
					}else if(json['questiontype'] == 75){
						//判断题
						$('.modal-body').append('<p><input type="radio" name="ans" value="'+1+'" />'+"正确"+'</p>');
						$('.modal-body').append('<p><input type="radio" name="ans" value="'+0+'" />'+"错误"+'</p>');
					}
				}
			}
		}
	});
}


//加载笔记列表方法
function getCourseNoteList(){
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseNoteList',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseNoteListReturn",
		type:'post',
		success:function(json){
			if(json == null || json == ''){
				return ;
			}
			// 清除原来的数据
			
			$('.myNote').empty();
			$('.otherNote').empty();
			//我的笔记
			if(json['selfNoteList'] != null && json['selfNoteList'].length>0){
				for(var i=0,l=json['selfNoteList'].length;i<l;i++){  
					// 我的笔记
				    if(null == json['selfNoteList'][i]['headPicpath'] || '' == json['selfNoteList'][i]['headPicpath'] || 'undeifined' == json['selfNoteList'][i]['headPicpath']) {
						$('.hidNoteLi li div:first img:first').attr('src',$BasePathURL_+'/img/userImg.jpg');//头像赋值
					}else{
						$('.hidNoteLi li div:first img:first').attr('src',$userHeadPicURL_ + json['selfNoteList'][i]['headPicpath']);//头像赋值
					}
					
				    $('.hidNoteLi li div:first .f_default1').text(json['selfNoteList'][i]['userrealname']);//用户名赋值
					$('.hidNoteLi li div:first span:nth-child(3)').text(json['selfNoteList'][i]['lastupdateString']);//时间
					$('.hidNoteLi li div:last').text( json['selfNoteList'][i]['content']); //内容
					
					$('.hidNoteLi li a').attr('onclick','seekVideo('+json['selfNoteList'][i]['studyPositionLong']+')');//播放
					
				    //克隆需要显示的节点,完全克隆（节点数据+事件）
				    
				     $('.myNote').append($('.hidNoteLi li').clone(true));
				    // $('.hidNoteLi li').clone(true).appendTo($('.myNote')); 
				   
				}
			}
			
			//大家笔记
			if(json['otherNoteList'] != null && json['otherNoteList'].length>0){
				for(var i=0,l=json['otherNoteList'].length;i<l;i++){  
					// 大家的笔记
			   		if(null == json['otherNoteList'][i]['headPicpath'] || '' == json['otherNoteList'][i]['headPicpath'] || 'undeifined' == json['otherNoteList'][i]['headPicpath']) {
						$('.hidNoteLi li div:first img:first').attr('src',$BasePathURL_+'/img/userImg.jpg');//头像赋值
					}else{
				   		$('.hidNoteLi li div:first img:first').attr('src',$userHeadPicURL_ + json['otherNoteList'][i]['headPicpath']);//头像赋值
					}
			   		
				    $('.hidNoteLi li div:first span:first').text(json['otherNoteList'][i]['userrealname']);//用户名赋值
					$('.hidNoteLi li div:first span:nth-child(3)').text(json['otherNoteList'][i]['lastupdateString']);//时间
					$('.hidNoteLi li div:div:last').text(json['otherNoteList'][i]['content']); //内容
					
					$('.hidNoteLi li a').attr('onclick','seekVideo('+json['otherNoteList'][i]['studyPositionLong']+')');//播放
					
				   //克隆需要显示的节点,完全克隆（节点数据+事件）
				   $('.otherNote').append($('.hidNoteLi li').clone(true)); 
				   
				}
			}
			
		}
	});
}

/**
课程评价 星星效果
**/
function loadStarInfo (){
    var stepW = 24;
    var description = new Array("非常差，回去再练练","真的是差，都不忍心说你了","一般，还过得去吧","很好，是我想要的东西","太完美了，此物只得天上有，人间哪得几回闻!");
    var stars = $("#star > li");
    var descriptionTemp;
    $("#showb").css("width",$evaluateScore*24); // 24 per/star
    stars.each(function(i){
        $(stars[i]).click(function(e){
            $evaluateScore= i+1;
            $("#showb").css({"width":stepW*$evaluateScore});
            descriptionTemp = description[i];
            $(this).find('a').blur();
            // update database
            updateCourseEvaluate();
            return stopDefault(e);
            return descriptionTemp;
        });
    });
    stars.each(function(i){
        $(stars[i]).hover(
            function(){
                $(".description").text(description[i]);
            },
            function(){
                if(descriptionTemp != null)
                    $(".description").text("当前您的评价为："+descriptionTemp);
                else 
                    $(".description").text(" ");
            }
        );
    });
}

function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};

//加载评价信息
function  getCourseEvaluateNum(){
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseEvaluateNum',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseEvaluateNumReturn",
		type:'post',
		success:function(json){
			//初始化 星星显示
			$evaluateScore = json;
			loadStarInfo ();
		}
	});
}	
//更新,评价
function updateCourseEvaluate(){
	if(!$isUserOnline){
		ShowEventMsg("对不起、登录后才能评价课程","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	$.ajax({
		async:false,
		url:$webURL_+'rest/course/updateCourseEvaluate',
		data:{'courseId':$belongedId_,'userId':$userId_,'evaluateScore':$evaluateScore},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"updateCourseEvaluateReturn",
		type:'post',
		success:function(json){
			if('yes' == json){
				updateUserCredit_com(0, 'SOUPER_COURSE_PJ');
				ShowEventMsg("恭喜您、评价成功！","Y");
			}else {
				ShowEventMsg("对不起、评价未成功,请稍后再试！","N");
			}
			
		}
	});
}
//加载收藏信息
function  getCourseCollectionInfo(){
	$.ajax({
		type : "post",
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseCollectionInfo',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseCollectionInfoReturn",
		success:function(json){
			//初始化 收藏
			if('yes' == json){
				$('#isCollect').addClass('f_visited');	
			}else{
				$('#isCollect').removeClass('binggo');	
			}
		}
	});
}

//点击添加收藏
function addUserCollection() {
	if(null == $userId_ || '' == $userId_ || undefined == $userId_ || 'undeifined' == $userId_) {
		ShowEventMsg("对不起、登录后才能收藏课程！","N");
	}
	if(!$isUserOnline){
		ShowEventMsg("对不起、登录后才能收藏课程！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	$.ajax({
		async:true,
		url:$webURL_+'rest/course/addUserCollection',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"addUserCollectionReturn",
		type:'post',
		success:function(json){
			if('yesyes' == json){
				ShowEventMsg("对不起、您已经收藏过该课程！","N");
			}else{
				$('#isCollect').addClass('f_visited');
				ShowEventMsg("恭喜您、收藏成功！","Y");
				// 收藏课程成功后，增加用户积分
				updateUserCredit_com(0, 'COURSE_SC');
			}
		}
	});
}

//加载分类信息
function getCourseCatalogInfo() {
	
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getCourseCatalogInfo',
		data:{'courseId':$belongedId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getCourseCatalogInfoReturn",
		type:'post',
		success:function(json){
			// <a href="#" class="f_default1">少儿教育</a>
			//alert("1:"+json.length);
			//alert(json['courseCatalogName']);
			//var node1 = '<a href="'+$webURL_+'course/courseIndex/'+json[i]['id']+'_0.shtml" class="f_default1">'+ json[i]['name']  +'</a> >' ;
			var node1 = '<a href="'+$webURL_+'course/courseIndex/'+json['courseCatalog']+'_0.shtml" class="f_default1">'+ json['courseCatalogName']  +'</a> >' ;
			$('#rootCatalog').append(node1); //分类信息
			var node2 = '<a href="'+$webURL_+'course/courseInfo/'+json['id']+'.shtml" class="f_default1">'+ json['name']  +'</a> >' ;
			$('#rootCourseTheme').append(node2); //合集信息
			
		}
	});
}
//加载用户信息
function getUserInfo() {
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getUserInfo',
		data:{'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getUserInfoReturn",
		type:'post',
		success:function(json){
			
			$('#userRealName').text(json['realname']);
			if(null == json['headPicpath'] || '' == json['headPicpath'] || 'undeifined' == json['headPicpath']) {
				$('#userHeadPic').attr('src',$BasePathURL_+'/img/userImg.jpg');
			}else{
				$('#userHeadPic').attr('src',$userHeadPicURL_ + json['headPicpath']);
			}
			
		}
	});
}
//加载相关课程
function getXgkcCourse() {
	$.ajax({
		async:true,
		url:$commonServiceURL_+'/commonservice/rest/getXgkcCourse',
		data:{'courseId':$belongedId_,'userId':$userId_},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"getXgkcCourseReturn",
		type:'post',
		success:function(json){
			for(var i=0,l=json.length;i<l;i++){
				//带入到播放链接！！！！！
				
				
				$('.hidXgkcLi li').attr('id',json[i]['id']); // li id
		   		$('.hidXgkcLi li h4 a').text(json[i]['name']);//课程名称
//		   		$('.hidXgkcLi li h4 a').attr('href', $webURL_ +'index/mainIndex.jsp?courseFlag=courseInfo&courseId='+ json[i]['id']);//课程链接
//		   		$('.hidXgkcLi li div a').attr('href', $webURL_ +'index/mainIndex.jsp?courseFlag=courseInfo&courseId='+ json[i]['id']);//课程链接
				
		   		if(null == json[i]['pathImg'] || '' == json[i]['pathImg'] || 'undeifined' == json[i]['pathImg']) {
					$('.hidXgkcLi li div img').attr('src', $BasePathURL_ + '/img/img_null.jpg');
				}else{
				    $('.hidXgkcLi li div img').attr('src', $userHeadPicURL_ + json[i]['pathImg']);//课程头像
				}
//				$('.hidXgkcLi li div span:nth-child(3)').text(json[i]['createDateString']);//评论时间
//				$('.hidXgkcLi li p:nth-child(3) a').text(json[i]['courseCatalogName']); //课程分类名称
//				$('.hidXgkcLi li p:nth-child(3) a').attr('href', $webURL_ +'course/courseIndex/'+ json[i]['courseCatalog']+'.shtml');//课程链接
//				$('.hidXgkcLi li p:nth-child(4)').text(json[i]['createDateString']); //时间
//		   		$('.hidXgkcLi li p:nth-child(4)').text(''); //时间
		   		
		   		//播放链接
//		   		var lastpotion = json[i]['lastPosition'];
		   		var courseId = "'"+json[i]['id']+"'";
				var courseCode = "'"+json[i]['code']+"'";
				var cwType = "'"+json[i]['cwType']+"'";
				var courseName = "'"+json[i]['name']+"'";
				var lastpotion = "'"+json[i]['lastPosition']+"'";
				var videoRP = "'"+json[i]['videoRP']+"'";
		   		$('.hidXgkcLi li h4 a').attr('onclick','BeginStudy('+courseId+','+courseCode+','+cwType+','+courseName+','+lastpotion+','+videoRP+')');
		   		$('.hidXgkcLi li div a').attr('onclick','BeginStudy('+courseId+','+courseCode+','+cwType+','+courseName+','+lastpotion+','+videoRP+')');
		   		
				$('#xgkc').append($('.hidXgkcLi li').clone(true)); 
				
				 // 清除数据id
			    /*
				$('.hidXgkcLi li').attr('id',''); // li id
		   		$('.hidXgkcLi li h4 a').text('');//课程名称
			    $('.hidXgkcLi li div img').attr('src', '');//课程头像
				$('.hidXgkcLi li div span:nth-child(3)').text('');//评论时间
				$('.hidXgkcLi li p:nth-child(3) a').text(''); //课程分类名称
				$('.hidXgkcLi li p:nth-child(4)').text(''); //时间
				*/
			}  
		}
	});
}
/*****************************笔记方法开始******************************************************************************/
/*****************************评论方法开始******************************************************************************/

var totalPages_ = 0; // 总页数
var currentPage_ = 1;// 当前pageNum
var pageSize_ = 5; // 一页显示条数
var visiblePages_ = 10; // 默认显示页码

//加载评论list内容信息
function  getCommentsInfo(){
	$.ajax({
			type : "post",
			async:false,
			url : $commonServiceURL_ + "commonservice/rest/getComments",
			data:{'belongedId':$belongedId_,'commentType':$commentType_,'userId':$userId_,'pageSize':pageSize_,'currentPage':currentPage_},
			dataType : "jsonp",
			jsonp: "jsoncallback",
			jsonpCallback:"getCommentsInfoReturn",
			success : function(json){
				//移除之前的子elements
				$("#comment").empty();
				
				for(var i=0,l=json.length;i<l;i++){  
					   
				   		// 单条评论数据赋值
					    if(null == json[i]['userPhotoPath'] || '' == json[i]['userPhotoPath'] || 'undeifined' == json[i]['userPhotoPath']) {
							$('.hid li div img:first').attr('src',$webURL_+'/images/userImg.jpg');//头像赋值
						}else{
							$('.hid li div img:first').attr('src',$userHeadPicURL_ + json[i]['userPhotoPath']);//头像赋值
						}
				   		
					    $('.hid li div:first span:first').text(json[i]['userRealName']);//用户名赋值
						$('.hid li div:first span:nth-child(3)').text(json[i]['createDateString']);//评论时间
						$('.hid .clonechild .n_con').text(json[i]['content']); //评论内容
						
						$('.hid li div span a:first span').text(json[i]['likeCount']); //赞总数
						$('.hid li div span a:nth-child(2) span').text(json[i]['noLikeCount']); //踩总数
						
						
						///绑定事件 error 
						///$('.btns m_t_10 span a:first').bind('click',likeClick(json[i]['id'])); //增加 赞事件
					   ///$('.btns m_t_10 span a:first').click(likeClick(json[i]['id']))
					   
					   // 方便调用 赋动态ID
					   $('.hid li').attr('id',json[i]['id']); // li id
					   $('.hid li div span a:first span').attr('id','like' + json[i]['id']); //赞
					   $('.hid li div:nth-child(3) span a:nth-child(2) span').attr('id','noLike' + json[i]['id']);//踩
					   $('.hid li div span a:nth-child(4)').attr('id','rep' + json[i]['id']);//举报
					   
					   //设置评论的状态,如果用户对相应的评论做过操作，则记录相关的状态
					   if(json[i]['isLike'] == 1){
					   		$('.hid li div span a:first ').attr('class','visited'); //赞
					   }
					   if(json[i]['isNoLike'] == 1){
					   		$('.hid li div span a:nth-child(2)').attr('class','visited');//踩
					   }
					   
					   if(json[i]['isRep'] == 1){
					   		 $('.hid li div span a:nth-child(4)').attr('class','visited');//举报
					   }
					  
					   //克隆需要显示的节点,完全克隆（节点数据+事件）
					   $('#comment').append($('.hid li').clone(true)); 
					   
					   // 清除数据id
					   $('.hid li').attr('id',''); // li id
					   $('.hid li div span a:first span').attr('id','');//赞
					   $('.hid li div span a:nth-child(2) span').attr('id','');//踩
					   $('.hid li div span a:nth-child(4)').attr('id','');//举报
					   // 清除样式
					   $('.hid li div span a:first').attr('class',''); //赞
					   $('.hid li div span a:nth-child(2)').attr('class','');//踩
					   $('.hid li div span a:nth-child(4)').attr('class','');//举报
				 }  
			}
		}); 
}


//加载分页信息
function  getCommentsPageInfo(){

	$.ajax({
			type : "post",
			async:false,
			url : $commonServiceURL_ + "commonservice/rest/getCommentsPageInfo",
			data:{'belongedId':$belongedId_,'commentType':$commentType_,'pageSize':pageSize_},
			dataType : "jsonp",
			jsonp: "jsoncallback",
			jsonpCallback:"getCommentsPageInfoReturn",
			success : function(json){
				
				//设置评论总数
				$('.cc_page_num').text(json['totalNum']);
				
				//初始化 totalPages_ ,currentPage_
				totalPages_ =  json['totalPages'];
				
				currentPage_ = 1;
				
				// 分页模块样式
			    $.jqPaginator('#pagination2', {
			        totalPages: totalPages_,
			        visiblePages: visiblePages_,
			        currentPage: currentPage_,
			        prev: '<li class="prev"><a href="javascript:void(0)">上一个</a></li>',
			        next: '<li class="next"><a href="javascript:void(0)">下一个</a></li>',
			        page: '<li class="page"><a href="javascript:void(0)">{{page}}</a></li>',
			        onPageChange: function (num, type) { /// 页码点击触发的事件
		        	currentPage_ = num; //改变当前页码
		            getCommentsInfo();
			            
			        }
			    });
			}
		}); 
}



// 赞click
function likeClick(self){
	
	if($userId_ == null || $userId_ == '' || $userId_ == 'undefined' || $userId_ == undefined){
	    ShowEventMsg("对不起、登录后才能顶！","N");
		return
	}
	if(!$isUserOnline){
	    ShowEventMsg("对不起、登录后才能顶！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	var commentId = self.parentNode.parentNode.parentNode.id; // 评论ID
	  	$.ajax({
			type : "post",
			async:false,
			url : $webURL_ + "rest/commonComment/commentBehaviorLike",
			data:{'commentId':commentId,'userId':$userId_},
			dataType : "jsonp",
			jsonp: "jsoncallback",
			jsonpCallback:"likeClickReturn",
			success : function(json){
				var isSelect = json['isSelect'];
				var likeCount = json['likeCount'];
				
				$('#like'+commentId).text(likeCount);
				if(isSelect == 'yes'){
					ShowEventMsg("恭喜您、顶成功！","Y");
					$(self).addClass('visited');
				}else{
					ShowEventMsg("恭喜您、取消顶成功！","Y");
					$(self).removeClass('visited');
				}
			}
		}); 
}
// 踩click
function noLikeClick(self){

	if($userId_ == null || $userId_ == '' || $userId_ == 'undefined' || $userId_ == undefined){
	    ShowEventMsg("对不起、登录后才能踩！","N");
		return
	}
	if(!$isUserOnline){
	    ShowEventMsg("对不起、登录后才能踩！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	var commentId = self.parentNode.parentNode.parentNode.id; // 评论ID
	  	$.ajax({
			type : "post",
			async:false,
			url : $webURL_ + "rest/commonComment/commentBehaviorNoLike",
			data:{'commentId':commentId,'userId':$userId_},
			dataType : "jsonp",
			jsonp: "jsoncallback",
			jsonpCallback:"noLikeClickReturn",
			success : function(json){
				var isSelect = json['isSelect'];
				var noLikeCount = json['noLikeCount'];
				$('#noLike'+commentId).text(noLikeCount);
				if(isSelect == 'yes'){
					ShowEventMsg("恭喜您、踩成功！","Y");
					$(self).addClass('visited');
				}else{
					ShowEventMsg("恭喜您、取消踩成功！","Y");
					$(self).removeClass('visited');
				}
			}
		}); 
}


var repCommentId;
var commentElement;

//检测是否已经举报过该条评论,只允许举报一次
function  repClick(self){

	if($userId_ == null || $userId_ == '' || $userId_ == 'undefined' || $userId_ == undefined){
	    ShowEventMsg("对不起、登录后才举报！","N");
		return
	}
	if(!$isUserOnline){
	    ShowEventMsg("对不起、登录后才举报！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	commentElement = self;
	var commentId = self.parentNode.parentNode.parentNode.id; // 评论ID
	// 判断是否已经举报过了
	if($(self).attr('class') != 'visited'){
		$('.yonhu_xy').show();
		repCommentId = commentId;
	}else{
	    ShowEventMsg("对不起、您已经举报过该评论！","N");
	}
}

//举报提交
function repCommit(){

	if($userId_ == null || $userId_ == '' || $userId_ == 'undefined' || $userId_ == undefined){
	    ShowEventMsg("对不起、登录后才能举报！","N");
		return
	}
	if(!$isUserOnline){
	    ShowEventMsg("对不起、登录后才能举报！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	var repType = $('input:radio:checked').val();
	$.ajax({
		type : "post",
		async:false,
		url :$webURL_ + "rest/commonComment/commentBehaviorRep",
		data:{'commentId':repCommentId,'userId':$userId_,'repType':repType},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"noLikeClickReturn",
		success : function(json){
			if(json == 'yes'){
				$('#rep'+repCommentId).addClass('visited');
				ShowEventMsg("您已成功举报该条评论！","Y");
			}
			$('.yonhu_xy').hide();
		}
	}); 
	
}
// 评论提交
function postComment(){

	if($userId_ == null || $userId_ == '' || $userId_ == 'undefined' || $userId_ == undefined){
	    ShowEventMsg("对不起、登录后才能发表评论！","N");
		return
	}
	if(!$isUserOnline){
	    ShowEventMsg("对不起、登录后才能发表评论！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	
	if($('#pComment').val().replace(/\ +/g,'').length == 0){
	    ShowEventMsg("对不起、评论内容不能为空！","N");
		return
	}
	if($('#pComment').val().replace(/\ +/g,'').length > 200){
	    ShowEventMsg("对不起、评论内容不能超过200字！","N");
		return
	}
	
	// submit
	$.ajax({
		type : "post",
		async:false,
		url :$webURL_ + "rest/commonComment/addComment",
		data:{'userId':$userId_,'belongedId':$belongedId_,'commentType':$commentType_,'content':$('#pComment').val().replace(/\ +/g,'')},
		dataType : "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback:"postCommentReturn",
		success : function(json){
			if(json == 'yes'){
				$('#pComment').val('');
				ShowEventMsg("恭喜您、评论成功！","Y");
				//刷新评论列表和分页
				currentPage_ = 1;
				getCommentsPageInfo();
				// 评论完后，用户增加积分
				updateUserCredit_com(0, 'SOUPER_COURSE_PL');
			}else{
				ShowEventMsg("对不起、评论失败请稍后再试！","N");
			}
		}
	}); 
}
/*****************************评论方法结束******************************************************************************/

//播放方法，从前台拷贝修改
function BeginStudy(CourseId , CourseCode , ScormOrVideo , Cname,lastPosition,videoRP){
	if(!$isUserOnline){
		ShowEventMsg("对不起、登录超时请重新登录！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	
	var keys = [];
    var values = [];
    
    keys[0] = "CourseId";//课程ID
    values[0] = CourseId;

    keys[1] = "Cname";//课程名称
    values[1] = strEnc(Cname,"adks1","adks2","adks3");//中文乱码 加密;
    
    keys[2] = "ScormOrVideo";//三分屏或单视频  170/171
	values[2] = ScormOrVideo;
	
    keys[3] = "CourseCode";//课程编码或文件名
    values[3] = CourseCode;
	
//	var UserId = $("#userId").val();//用户Id;
    keys[4] = "UserId";
    values[4] = $userId_;
	
    //如果是课件超市打开课程 GreadId 一定要最后设为 CourseSuper 否则课件无法打开
    keys[5] = "GreadId";
	values[5] = 'CourseSuper';
	
	keys[6] = "VideoRP";//分辨率 _256K_512K_768K_1080K
	values[6] = videoRP;
	
	var SessionId = "adks123456";//当前会话ID

	keys[7] = "SessionId";
	values[7] = SessionId;
	
	var Systime = (new Date()).getTime()//系统时间毫秒数
	keys[8] = "Systime";
	values[8] = Systime;
	
	var Location = 0;//时间点
	keys[9] = "Location";
	values[9] = lastPosition;
 	
	if(typeof(GreadId) == 'undefined'|| GreadId == ''|| GreadId == 'null'){
		GreadId = 'CourseSuper';
	}
	var keystu='adks2016'+CourseId+UserId+GreadId+lastPosition+SessionId+ScormOrVideo+Systime;//加密参数
	keys[10] = "StudyKey";
    values[10] = hex_md5(keystu);
   
    openUrl($VideoServer_, "Web", keys, values);
}

/* 
 * PC 端Post方式打开课程学习 
 */
function openUrl(url, CourseName , keys , values){  
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
    
    window.location.href=url;
}

//指定视频到位置播放
function seekVideo(videoPosition){
	if(!$isUserOnline){
		ShowEventMsg("对不起、登录超时请重新登录！","N");
		window.location.href=$webURL_+"login.shtml";
		return false;
	}
	if(videoPosition>0){
		var Duration = parseInt(jwplayer().getDuration());
		if(videoPosition<Duration){
			jwplayer('container').seek(videoPosition);
		} 
	}
}

//获得焦点暂停播放
function videoStop(){
	jwplayer('container').play(false);
}

//失去焦点继续播放
function videoPlay(){
	jwplayer('container').play(true);
}

/*******************二维码开始**************************************************************************/
function QrCodeStudy(){
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
	
	var Location = parseInt(jwplayer('container').getPosition());//获得当前播放位置
	keys[6] = "Location";
	values[6] = Location;
 	
	var keystu='adks2016'+CourseId+UserId+Location+ScormOrVideo+Systime;//加密参数
	keys[7] = "StudyKey";
    values[7] = hex_md5(keystu);
    
    CreatetQrCode(keys, values);
}

/** 
 * Get方式生成课程二维码，供移动端扫描学习
 **/
var phonehidden = false;//隐藏
function CreatetQrCode(keys,values){  
    var parms;
    if (keys && values && (keys.length == values.length)){
        for ( var i = 0; i < keys.length; i++){
		      
		    if(parms==null||parms=="null"){
			    parms =  values[i];
		    }else{
			    parms += '/'+values[i];
		    }
        }
    }
    /* 微信扫描 */
	var url = $VideoWapServer_ + '/'+  parms + '.shtml';;//二维码全路径
    
    //openWindowIframe("wapVideo",url);return;
    
    var qrcode_make = document.getElementById("qrcode_make");//外层背景
    var qrcode_span = document.getElementById("qrcode_span");//生成位置
    //判断是否生成，未生成则进行创建
    if(!phonehidden){
	    jwplayer('container').play(false);//暂停播放
	    qrcode_make.style.display="block";
	    qrcode_span.style.display="block";
	   	phonehidden = true;
	    var qrcode_create = new QRCode(qrcode_span, {
		        text : url,
		        width : 160,//设置宽高
		        height : 160,
		        colorDark : '#000000',
  				colorLight : '#ffffff'
		});
    }else{
	    jwplayer('container').play(true);//继续播放
	    qrcode_span.innerHTML="";
    	qrcode_span.style.display="none";
    	qrcode_make.style.display="none";
	   	phonehidden = false;
    }
}


/* 
 * iframe 嵌入播放地址  
 */  
function openWindowIframe(CourseName,url){  
    window.open(url,CourseName,'height=400, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
}

/*******************二维码结束**************************************************************************/
