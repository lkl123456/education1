function removePlan(planId){
	if(confirm("确认删除学习计划吗？删除学习计划后不可恢复！")){
		$.ajax({
			async:false,
			url:contextPath+'/api/adks/removePlan.do',
			data:{'planId':planId},
			type:'post',
			success:function(data){
				if('yes' == data){
					//删除后刷新计划列表
					document.location.href = contextPath+'/api/adks/myPlanList.do';
				}
			}
		});
	}
}

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
 }
function submitPlan(){
	var title = $("#title").val().trim();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var contentLen = $("#content").val();
	if(title==null||title==""||title=="null"||title=="undefined"){
		alert("计划名称不能为空");
		return false;
	}
	if(startDate == ""){
		alert("计划开始时间不能为空");
		return false;
	}
	if(endDate == ""){
		alert("计划结束时间不能为空");
		return false;
	}
	if(startDate >= endDate){
		alert("计划开始时间不能大于等于结束时间");
		return false;
	}
	endDate = endDate.replace(/-/g,"/");//替换字符，变成标准格式
	var d2=new Date();//取今天的日期
	var d1 = new Date(Date.parse(endDate)); //结束日期
	if(d1<d2){
		alert("计划结束时间不能早于当前时间");
		return false;
	}
	
	if(contentLen.length>200){
		alert("计划内容最多200个字符");
		return false;
	}
	$.ajax({
			async:false,
			url : contextPath+"/api/adks/savePlan.do",
			data:$('#formSearch').serialize(),
			type:"post",
			success:function(data){
					if('ok'== data){
						alert("学习计划添加成功！");
						document.location.href = contextPath+'/api/adks/myPlanList.do';
					}else if('updateok'== data){
						alert("学习计划修改成功！");
						document.location.href = contextPath+'/api/adks/myPlanList.do';
					}else{
						alert("学习计划添加失败！");
					}
			}
	    });
}

function removePlanCourse(planCourseId,planId){
	if(confirm("确认删除计划课程吗？")){
		$.ajax({
			async:false,
			url:contextPath+'/api/adks/removePlanCourse.do',
			data:{'planCourseId':planCourseId},
			type:'post',
			success:function(data){
				if('yes' == data){
						//删除后刷新计划课程列表
						document.location.href = contextPath+'/api/adks/planCourseList.do?planId='+planId;
					}
			}
		});
	}
}

function selectCourse(planId){
	document.location.href = '/api/adks/getCoursesForPlanListYz.do?planId='+planId;
}

function courseList(ccCode){
	$("#ccCode").val(ccCode);
	$("#pageNum").val(0);
	document.formSearch.submit();
	
}

function getCoursesList(){
	document.formSearch.submit();
}

// 全选、全不选
function checkboxClick(flag){
	$('#ucMsg').text('');
	var boxs = document.getElementsByName("cid");
	for(var i = 0; i < boxs.length; i++){
		boxs[i].checked=flag;
	}
}

// 保存选中的课程
function userCourseSelSave(){
	$('#ucMsg').text('');
	var planId = $('#planId').val();
	var cids = '';
	var boxs = document.getElementsByName("cid");
	for(var i = 0; i < boxs.length; i++){
		if(boxs[i].checked){
			if(cids == ''){
				cids = boxs[i].value;
			}else{
				cids = cids+','+boxs[i].value;
			}
		}
	}
	if(cids == ''){
		$('#ucMsg').text('您还未选择课程！');
		return;
	}
	$.ajax({
		async:true,
		url:contextPath+'/api/adks/savePlanCourse.do',
		//data:{'orderId':cids,'planId':planId}
		data:$('#formSearch').serialize(),
		type:"post", 
		success:function(data){
			if(data == 'yes'){
				alert('课程添加成功！');
				document.formSearch.submit();
			}else{
				alert('课程添加失败！');
			}
		}
	});			
}

