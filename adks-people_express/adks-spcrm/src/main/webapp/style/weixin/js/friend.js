function myFriendList(fgId){
	document.location.href = contextPath+'/api/adks/myFriendList.do?fgId='+fgId;
}

function toAddFriendGroup(){
	document.location.href = contextPath+'/center/adks/jsp/friend/addFriendGroup.jsp';
}

function goBack(){
	document.location.href = contextPath+'/api/adks/myFriendGroupList.do';
}

function goBackMyFriend(fgId){
	document.location.href = contextPath+'/api/adks/myFriendList.do?fgId='+fgId;
}

function toUpdateFriendGroup(fgId,fgName){
	document.location.href = contextPath+'/center/adks/jsp/friend/updateFriendGroup.jsp?fgId='+fgId+"&fgName="+fgName;
}

function addFriendGroup(){
	var addFgName = $('#addFgName').val();
	if(addFgName==null||addFgName==""){
		alert("分组名称不能为空!");
		return false;
	}
	
	$.ajax({
		async:false,
		url : contextPath+"/index/checkFriendGroup.do",
		data:{'fgName':addFgName},
		type:"post",
		success:function(data){
				if('more'== data){
					alert("分组名称已存在，请重新填写！");
					return false;
				}else{
					$.ajax({
						async:false,
						url : contextPath+"/api/adks/saveFriendGroup.do",
						data:{'fgName':addFgName},
						type:"post",
						success:function(data){
								if('ok'== data){
									alert("好友分组添加成功！");
									document.location.href = contextPath+'/api/adks/myFriendGroupList.do';
								}else{
									alert("好友分组添加失败！");
								}
						}
				    });
				}
		}
    });
}


function updateFriendGroup(){
	var updatefgname = $('#updatefgname').val();
	var oldFgName = $('#oldFgName').val();
	var oldFgId = $('#oldFgId').val();
	if(updatefgname==null||updatefgname==""){
		alert("分组名称不能为空！");
		return false;
	}else if(updatefgname==oldFgName){
		alert("分组名称没有修改！");
		return false;
	}else{
		$.ajax({
		async:false,
		url : contextPath+"/index/checkFriendGroup.do",
		data:{'fgName':updatefgname},
		type:"post",
		success:function(data){
				if('more'== data){
					alert("分组名称已存在，请重新填写！");
					return false;
				}else{
					$.ajax({
						async:false,
						url : contextPath+"/api/adks/updateFriendGroup.do",
						data:{'fgName':updatefgname,'fgId':oldFgId},
						type:"post",
						success:function(data){
								if('updateok'== data){
									alert("好友分组修改成功！");
									document.location.href = contextPath+'/api/adks/myFriendList.do?fgId='+oldFgId;
								}else{
									alert("好友分组修改失败！");
								}
						}
				    });
				}
		}
    });
	}
}


function deleteFriendGroup(id){
	if(window.confirm('删除好友分组，该分组下所以好友将被移到"我的好友"分组下！')){
		$.ajax({
			async:true,
			url:contextPath+"/api/adks/deleteFriendGroup.do",
			data:{'fgId':id},
			type:"post", 
			success:function(data){
				if('ok'== data){
					alert("好友分组删除成功！");
					document.location.href = contextPath+'/api/adks/myFriendGroupList.do';
				}
			}
		});
	}
}

function deleteFriend(fId){
	if(window.confirm('确定要删除该好友？')){
		$.ajax({
			async:true,
			url:contextPath+"/api/adks/deleteFriend.do",
			data:{'fId':fId},
			type:"post", 
			success:function(data){
				if('ok'== data){
					alert("好友删除成功！");
					document.location.href = contextPath+'/api/adks/myFriendList.do?fgId='+$('#fgId').val();
				}
			}
		});
	}
}


function toUpdateFriend(id,name){
	$('#friendName').html("移动\“"+name+"\“至:");
	$('#oldFriendId').val(id);
}

function updateFriend(){
	var oldFriendId = $('#oldFriendId').val();
    var fgId = $('input[name="fgName"]:checked').val();  
	if(fgId=="undefined"||fgId==null||fgId==""||fgId=="null"){
		alert('您还没有选择任何分组！'); 
	}else{
		if(window.confirm('确定要把好友移动到该分组？')){
			$.ajax({
				async:true,
				url:contextPath+"/api/adks/updateFriend.do",
				data:{'oldFriendId':oldFriendId,'fgId':fgId},
				type:"post", 
				success:function(data){
					if('updateok'== data){
						alert("好友移动分组成功！");
						document.location.href = contextPath+'/api/adks/myFriendList.do?fgId='+$('#fgId').val();
					}
				}
			});
		}
	}
}

function toSearchFriend(){
	document.location.href = contextPath+'/center/adks/jsp/friend/searchFriend.jsp';
}

function searchFriend(){
	document.formSearch.submit();
}


function showFriendGroup(id){
	$('#fId').val(id);
	$('#message').val("");
	$("#word").text(100);
}

function addFriend(){
	var fId = $('#fId').val();
	var message = $('#message').val();
    var fgId = $('input[name="fgName"]:checked').val();  
	if(fgId=="undefined"||fgId==null||fgId==""||fgId=="null"){
		alert('您还没有选择任何分组！'); 
	}else{
			$.ajax({
				async:true,
				url:contextPath+"/api/adks/addFriend.do",
				data:{'fId':fId,'fgId':fgId,'message':message},
				type:"post", 
				success:function(data){
					if('ok'== data){
						alert("好友申请已经发送，请耐心等待！");
						document.formSearch.submit();
					}else if('error'== data){
						alert("请耐心等待，不要重复发送好友申请！");
						document.formSearch.submit();
					}
				}
			});
	}
}


$(function(){
	$("#message").keyup(function(){
		var len = $(this).val().length;
   		if(len > 99){
    		$(this).val($(this).val().substring(0,100));
   		}
   		var num = 100 - len;
   		if(num>-1){
	   		$("#word").text(num);
   		}
  	});
});