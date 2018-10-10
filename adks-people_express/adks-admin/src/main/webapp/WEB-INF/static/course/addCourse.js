var setting1 = {
	async : {
		enable : true,
		url : contextpath + "/userIndex/getOrgsJson",
		autoParam : [ "id=parentId" ],
		// otherParam : ["orgunameValue",orgunameValue],
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
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo1");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//添加机构
function changeValue(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		$("#orgCode").val(treeNode.orgCode);
		$("#orgNameShow").val(treeNode.name);
		$("#treeDemo1").css("display","none");
	}
}
$(function() {
	$.fn.zTree.init($("#treeDemo1"), setting1);
	$("#treeDemo1").css("display","none");
	$('#courseAddForm').form({
		url : contextpath + '/course/saveCourse',
		onSubmit : function(param) {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				changeTab();
			}else if (data == 'snnameExists') {
				$.messager.alert("提示", "课程名称已存在，请重新输入!");
				$("#pload").html("0%");
				$("#pload").removeClass("loading2");
				$("#pload").addClass("loading1");
				$("#submitAdd").removeAttr("disabled");
				$("#cancelAdd").removeAttr("disabled");
			}else if(data == 'fileError'){
				$.messager.alert("提示", "课件类型有误，只能mp4或者zip文件");
				$("#pload").html("0%");
				$("#pload").removeClass("loading2");
				$("#pload").addClass("loading1");
				$("#submitAdd").removeAttr("disabled");
				$("#cancelAdd").removeAttr("disabled");
			}else if(data == 'errorOnFile'){
				$.messager.alert("提示", "课件上传失败");
				$("#pload").html("0%");
				$("#pload").removeClass("loading2");
				$("#pload").addClass("loading1");
				$("#submitAdd").removeAttr("disabled");
				$("#cancelAdd").removeAttr("disabled");
			}
		}
	});
	var ossPath = $("#ossPath").val();
	var cp = $("#coursePic").val();
	if (cp != null && cp != 'undefined' && cp != '') {
		cp = ossPath + cp;
	} else {
		// 默认图片
		cp="../static/admin/images/qsimage.jpg";
	}
	$("#ciPath").attr("src", cp);
})
function changeTab() {
	var url = contextpath+'/course/courseList';
	parent.addTab_update('课程管理', url, 'icon-save');
	parent.$('#tabs').tabs('close', '添加课程');
	parent.$('#tabs').tabs('close', '编辑课程');
}
// 课程添加保存
function addCourseOk() {
	var authorId = $("#authorId").val();
	if (authorId == null || authorId == '') {
		$.messager.alert('提示', '讲师不能为空', 'warning');
		return;
	}
	var courseTimeLong = $("#courseTimeLong").textbox("getValue");
	var re = /^([01][0-9]|2[0-3])\:[0-5][0-9]\:[0-5][0-9]$/;
	// alert("ok:"+courseTimeLong);
	if (courseTimeLong == null || trimStr(courseTimeLong) == '') {
		$.messager.alert('提示', '请输入课程时长', 'warning');
		return;
	} else if (re.test(courseTimeLong) == false) {
		$.messager.alert('提示', '课程时长格式有误', 'warning');
		return;
	}
	$("#pload").html("");
	$("#pload").removeClass("loading1");
	$("#pload").addClass("loading2");
	$("#submitAdd").attr({"disabled":"disabled"});
	$("#cancelAdd").attr({"disabled":"disabled"});
	$('#courseAddForm').submit();
	var courseSortId = $("#courseSortId").val();
}
// 课程添加取消
function addCourseCancle() {
	parent.$('#tabs').tabs('close', '添加课程');
	parent.$('#tabs').tabs('close', '编辑课程');
}
// 选择讲师
function checkAuthor() {
	var orgId=$("#orgId").val();
	var orgCode=$("#orgCode").val();
	if(orgId == null || orgId == '' || orgId == '0'){
		$.messager.alert('提示', '请选择课程所属机构', 'warning');
		return;
	}
	var courseSortId = $("#courseSortId").val();
	var courseId = $("#courseId").val();
	var url = contextpath+'/course/toAuthorCourse?orgCode=' + orgCode
			+ '&courseSortId=' + courseSortId + '&courseId=' + courseId+'&orgId='+orgId;
	parent.addTab_update('选择讲师', url, 'icon-save');
}
function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}
function showOrgTree(){
	$("#treeDemo1").css("display","");
}