
//##############################################工具栏start###############################################
//查询按钮点击
function doSearch() {
	var  courseName= encodeURI($('#courseName').val());
	$("#gradeUserCourseListTable").datagrid('load', {
		courseName : courseName
	});
}
//##############################################工具栏end#################################################

//##############################################列表start################################################
function courseUserStatus(value,row,index){
	if(value==0){
		return '未开始';
	}else if(value == 1){
		return '已完成';
	}else if(value == 2){
		return '未完成';
	}
}
function formatOper(val, row, index) {
	if(row.isOver==0||row.isOver==2){
		return '<a href="javascript:updateGradeUserCourse('+row.gradeId+','+ row.courseId + ','+row.userId+','+row.isOver+')">完成学习</a>';
	}else if(row.isOver==1){
		return '';
	}
}
//完成学习
function updateGradeUserCourse(gradeId,userId){
	var rows = $('#gradeUserCourseListTable').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示','请至少选择一个课程','warning');
		return ;
	}
	var ss = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss[i] = row.courseId;
	}
	if(ss.length > 0){
		$.messager.confirm("确认", "您确定要完成学习吗？", function(r) {
			if (r) {
				$.ajax({
					url : contextpath + "/gradeStudy/updateGradeUserCourse",
					async : false,// 改为同步方式
					type : "get",
					data : {"gradeId":gradeId,"userId":userId,"courseIds":ss.toString()},
					success : function(data) {
						// 刷新表格。
						var gradeUserId=$("#gradeUserId").val();
						parent.addTab_update("学习详情",contextpath +'/gradeStudy/toGradeUserCourseInfo?gradeUserId='+gradeUserId,"icon icon-nav");
					},
					error : function() {
						alert('error');
					}
				});
			}
		});
	}
}
//##############################################列表end##################################################

//##############################################子页面操作start##################################################

//##############################################子页面操作end##################################################

function setRowStyle(index,row){
	if(row.status == 0){
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}
