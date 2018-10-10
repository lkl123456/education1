
// 查询按钮点击
function doSearch() {
	var catalogs = $('#courseCataTree').combotree('getValues');
	$("#gradeCourseing").datagrid('load', {
		courseName_platform : $('#courseName').val(),
		catalogTypes:catalogs.toString()
	});
}
function doSearchCourseName() {
	$("#gradeCoursed").datagrid('load', {
		courseName : $('#courseNameInGrade').val()
	});
}

//返回培训班列表
function backToGradeList(){
	parent.$('#tabs').tabs('select','培训设置'); //这行必须在下一行的上面，否则效果不太对。亲测
	parent.$('#tabs').tabs('close', '配置培训班');
	/*var tab = parent.$('#tabs').tabs('getSelected');
	if (tab){
		var index = parent.$('#tabs').tabs('getTabIndex', tab);
		parent.$('#tabs').tabs('close', index);
		parent.$('#tabs').tabs('select','培训设置');
	}*/
}

//添加班级课程
function addGradeCourse(gradeId){
	var rows = $('#gradeCourseing').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一个课程','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.id;
	}
	if(ss.length > 0){
		//添加
		$.ajax({
			url: contextpath+"/gradeCourse/addGradeCourses",
			async: false,
			type: "get",
			data: {"courseIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeCourseing').datagrid('reload'); //刷新数据
				$('#gradeCoursed').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//移除班级课程
function removeGradeCourse(gradeId){
	var rows = $('#gradeCoursed').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一个课程','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.id;
	}
	if(ss.length > 0){
		//移除
		$.ajax({
			url: contextpath+"/gradeCourse/removeGradeCourses",
			async: false,
			type: "get",
			data: {"gcIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$('#gradeCourseing').datagrid('reload'); //刷新数据
				$('#gradeCoursed').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
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
function formatCourseState(value,row,index){
	if(value == 1){
		return '发布';
	}else if(value == 0){
		return '草稿';
	}else{
		return '下架';
	}
}
