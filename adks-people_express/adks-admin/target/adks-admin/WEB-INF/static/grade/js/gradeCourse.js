//##############################################树start##############################################
var setting1 = {
	async : {
		enable : true,
		url : contextpath + "/course/getCourseSortListJson",
		autoParam : [ "id=parentId" ],
		type : "get"
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : changeValue,
		onAsyncSuccess : zTreeOnSuccess
	}
};

//默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	/*if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}*/
}

//添加机构
function changeValue(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#selCourseSortTree").val(treeNode.name);
		$("#selCourseSortCode").val(treeNode.courseSortCode);
		$("#treeDemo").css("display","none");
	}
}
function showCourseSortTree(){
	$("#treeDemo").css("display","");
}
$(function(){
	$.fn.zTree.init($("#treeDemo"), setting1);
});
//##############################################树end##############################################

//##############################################列表start##############################################
function formatCourseState(value,row,index){
	if(value == 1){
		return '激活';
	}else if(value == 0){
		return '冻结';
	}
}
function formatCourseType(value,row,index){
	if(value == 1){
		return '必修';
	}else if(value == 2){
		return '选修';
	}else{
		return '未设置';
	}
}
function formatCourse(value,row,index){
	if(value == 170){
		return '三分屏';
	}else if(value == 171){
		return '单视屏';
	}else if(value == 683){
		return '微课';
	}else if(value == 4){
		return '多媒体课程';
	}
}
function formatDateConversion(value,row,index){
	if(value != null){
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd');
	}
}
//##############################################列表end#################################################

//##############################################工具栏start##############################################
// 查询按钮点击
function doSearch() {
	var  courseName= encodeURI($('#courseName').val());
	var courseSortIds = $('#selCourseSortCode').val();
	$("#gradeCourseing").datagrid('load', {
		courseName : courseName,courseSortIds:courseSortIds.toString()
	});
}
function doSearchCourseName() {
	var  courseNameInGrade= encodeURI($('#courseNameInGrade').val());
	$("#gradeCoursed").datagrid('load', {
		courseName : courseNameInGrade
	});
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
//点击配置考试
function configureGradeExam(gradeId){
	var currTab = parent.$('#tabs').tabs('getSelected');
	var url = contextpath+"/grade/toConfigureGradeExam?gradeId="+gradeId;
	parent.$('#tabs').tabs('update',{
		tab:currTab,
		options:{
			content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>'
		}
	});
}
//返回培训班列表
function backToGradeList(){
	parent.$('#tabs').tabs('select','培训设置'); //这行必须在下一行的上面，否则效果不太对。亲测
	parent.$('#tabs').tabs('close', '配置培训班');
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
		ss[i] = row.courseId;
	}
	if(ss.length > 0){
		//添加
		$.ajax({
			url: contextpath+"/gradeCourse/addGradeCourses",
			async: false,
			type: "get",
			data: {"courseIds":ss.toString(),"gradeId":gradeId},
			success: function (data) {
				$.messager.alert('提示','您加入该班级的课程默认为选修，请及时修改课程类型，以免造成数据记录不准确！','warning');
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
		ss[i] = row.courseId;
	}
	if(ss.length > 0){
		//移除
		$.ajax({
			url: contextpath+"/gradeCourse/removeGradeCourses",
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
//设置课程（选修/必修）
function setCourseType(gradeId,type){
	var rows = $('#gradeCoursed').datagrid('getSelections');
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
		//移除
		$.ajax({
			url: contextpath+"/gradeCourse/setGradeCoursesType",
			async: false,
			type: "get",
			data: {"courseIds":ss.toString(),"gradeId":gradeId,"type":type},
			success: function (data) {
				$('#gradeCourseing').datagrid('reload'); //刷新数据
				$('#gradeCoursed').datagrid('reload'); //刷新数据
			},error:function(){
				alert('error');
			}
		});
	}
}
//##############################################工具栏end################################################
