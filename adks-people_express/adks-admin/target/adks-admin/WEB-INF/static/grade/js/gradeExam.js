//##############################################列表start###############################################
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd');
	}
}
//##############################################列表end#################################################

//##############################################工具栏start##############################################
//查询按钮点击
function doSearch() {
	var  examName= encodeURI($('#examName').val());
	$("#gradeExaming").datagrid('load', {
		examName : examName,
	});
}
function doSearchCourseName() {
	var  examNameInGrade= encodeURI($('#examNameInGrade').val());
	$("#gradeExamed").datagrid('load', {
		examName : examNameInGrade
	});
}
//返回培训班列表
function backToGradeList(){
	parent.$('#tabs').tabs('select','培训设置'); //这行必须在下一行的上面，否则效果不太对。亲测
	parent.$('#tabs').tabs('close', '配置培训班');
}
//点击配置学员
function configureGradeUser(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeUser?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//点击配置课程
function configureGradeCourse(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeCourse?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//点击配置班主任
function configureGradeHeadTeacher(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeHeadTeacher?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//添加班级考试
function addGradeExam(gradeId,gradeName){
	var rows = $('#gradeExaming').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一个考试','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.examId;
	}
	gradeName=encodeURI(gradeName);
	if(ss.length > 0){
		//添加
		$.ajax({
			url: contextpath+"/gradeExam/addGradeExams",
			async: false,
			type: "get",
			data: {"examIds":ss.toString(),"gradeId":gradeId,"gradeName":gradeName},
			success: function (data) {
				$('#gradeExaming').datagrid('reload'); //刷新数据
				$('#gradeExamed').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//移除班级课程
function removeGradeExam(gradeId){
	var rows = $('#gradeExamed').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一个考试','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.examId;
	}
	if(ss.length > 0){
		//移除
		$.ajax({
			url: contextpath+"/gradeExam/removeGradeExams",
			async: false,
			type: "get",
			data: {"examIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeExaming').datagrid('reload'); //刷新数据
				$('#gradeExamed').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//##############################################工具栏end################################################