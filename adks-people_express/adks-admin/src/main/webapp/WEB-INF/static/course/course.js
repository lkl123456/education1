var setting = {
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
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess
	}
};
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
$(function() {
	$.fn.zTree.init($("#treeDemo"), setting);
});

// 点击删除课程分类
function delCourse() {
	var t_url = contextpath + "/course/delCourse";
	var selectRows = $("#cslistdatagrid").datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请选中一条记录删除', 'warning');
		return;
	}
	$.messager.confirm('确认', '确认要删除这批数据吗?', function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].courseId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				courseIds : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					$('#cslistdatagrid').datagrid("reload");
					$('#cslistdatagrid').datagrid("clearSelections");
				} else if(data == "error") {
					$.messager.alert("Info", "删除失败，课程已被观看或者是班级课程!");
				}else{
					$.messager.alert("Info", "操作失败~~");
				}
			});
		}
	});

}
// 左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var courseSortCode = "";
	if (nodes != null && nodes.length > 0) {
		courseSortCode = nodes[0].courseSortCode;
	}
	var s_course_name = encodeURI($("#s_course_name").val());
	$("#cslistdatagrid").datagrid('load', {
		courseSortCode : courseSortCode,
		s_course_name : s_course_name
	});
}

function addCourse() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	if (nodes == null || nodes.length < 1) {
		$.messager.alert('提示', '请选中一个分类进行添加', 'warning');
		return;
	}
	var courseSortId = nodes[0].id;
	if(courseSortId== 0|| courseSortId == '0'){
		$.messager.alert('提示', '分类不能为全部', 'warning');
		return;
	}
	// alert(courseSortId);
	var url = contextpath +'/course/toAddCourse?courseSortId=' + courseSortId;
	parent.addTab_update('添加课程', url, 'icon-save');
}

function editCourse() {
	var selectRows = $("#cslistdatagrid").datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	var courseId = selectRows[0].courseId;
	var courseSortId = selectRows[0].courseSortId;
	var url = contextpath +'/course/toAddCourse?courseSortId=' + courseSortId
			+ '&courseId=' + courseId;
	parent.addTab_update('编辑课程', url, 'icon-save');
}

// 查询按钮点击
function doSearch() {
	// 得到左边选中的节点
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var orgId = "";
	var orgCode = "";
	var orgName = "";
	if (nodes != null && nodes.length > 0) {
		orgId = nodes[0].id;
		orgName = nodes[0].name;
		orgCode = nodes[0].orgCode;
	}
	var s_course_name = encodeURI($("#s_course_name").val());
	$("#cslistdatagrid").datagrid('load', {
		orgCode : orgCode,
		s_course_name : s_course_name
	});
}
// courseId课程id
// flag 1审核状态，2是否推荐，3课程状态
// result标记修改到数据库的数据
function courseUpdateData(flag, courseId, result) {

	var t_url = contextpath + "/course/courseUpdateData";
	var strIds = "";
	if(courseId ==null || courseId == ''){
		var selectRows = $('#cslistdatagrid').datagrid('getSelections');
		if (selectRows.length < 1) {
			$.messager.alert('提示', '请勾选需要操作的课程!', 'warning');
			return;
		}
		
		for (var i = 0; i < selectRows.length; i++) {
			strIds += selectRows[i].courseId + ",";
		}
		strIds = strIds.substr(0, strIds.length - 1);
		result="1";
	}else{
		strIds=courseId;
		result="2";
	}
	$.post(t_url, {
		courseIds : strIds,
		flag : flag,
		result:result
	}, function(data) {
		if (data == "succ") {
			// 刷新表格，去掉选中状态的 那些行。
			$('#cslistdatagrid').datagrid("reload");
			$('#cslistdatagrid').datagrid("clearSelections");
		} else {
			$.messager.alert("操作失败~~", data);
		}
	});
}
// 审核状态
function formatColumnData1(value, row, index) {
	var courseId = row.courseId;
	var flag = '1';
	if (value == 1) {
		 return "通过&nbsp;&nbsp;<a href='#' onclick='javascript:courseUpdateData("+flag+","+courseId+","+2+")' >取消</a>";
		//return "通过";
	} else if (value == 2) {
		// return "未通过&nbsp;&nbsp;<a href='#'
		// onclick='javascript:courseUpdateData("+flag+","+courseId+","+1+")'
		// >通过</a>";
		return "未通过";
	} else if (value == 3) {
		// return "待审核&nbsp;&nbsp;<a href='#'
		// onclick='javascript:courseUpdateData("+flag+","+courseId+","+1+")'
		// >通过</a>";
		return "待审核";
	}
}
// 是否推荐
function formatColumnData2(value, row, index) {
	var courseId = row.courseId;
	var flag = '2';
	if (value == 1) {
		 return "推荐&nbsp;&nbsp;<a href='#' onclick='javascript:courseUpdateData("+flag+","+courseId+","+2+")'>取消</a>";
		//return "推荐";
	} else if (value == 2) {
		// return "不推荐&nbsp;&nbsp;<a href='#'
		// onclick='javascript:courseUpdateData("+flag+","+courseId+","+1+")'
		// >推荐</a>";
		return "不推荐";
	}
}
// 课程状态
function formatColumnData3(value, row, index) {
	var courseId = row.courseId;
	var flag = '3';
	if (value == 1) {
		 return "激活&nbsp;&nbsp;<a href='#' onclick='javascript:courseUpdateData("+flag+","+courseId+","+2+")'>冻结</a>";
		//return "激活";
	} else if (value == 2) {
		// return "冻结&nbsp;&nbsp;<a href='#'
		// onclick='javascript:courseUpdateData("+flag+","+courseId+","+1+")'
		// >激活</a>";
		return "冻结";
	}
}
// 课程类型
function formatColumnData4(value, row, index) {
	if (value == 170) {
		return '三分屏';
	} else if (value == 171) {
		return '单视屏';
	} else if (value == 683) {
		return '微课';
	} else if (value == 4) {
		return '多媒体课';
	}
}
// 课程添加保存
function addCourseOk() {
	var courseName = $("#courseName").textbox("getValue");
	if (courseName == null || courseName == '') {
		$.messager.alert('提示', '课程名称不能为空', 'warning');
		return;
	} else if (courseName.length >= 64) {
		$.messager.alert('提示', '课程名称的长度不能超过64个字符', 'warning');
		return;
	}
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
	// alert("courseAdd");
	$('#courseAddForm').form({
		url : contextpath + '/course/saveCourse',
		onSubmit : function(param) {
			return $(this).form('validate');
		},
		success : function(data) {
			alert(data);
		}
	});
	$('#courseAddForm').submit();
	var courseSortId = $("#courseSortId").val();
	// alert(courseSortId);
	var url = contextpath +'/course/courseList';
	parent.addTab_update('课程管理', url, 'icon-save');
	parent.$('#tabs').tabs('close', '添加课程');
}
// 课程添加取消
function addCourseCancle() {
	parent.$('#tabs').tabs('close', '添加课程');
}

function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}