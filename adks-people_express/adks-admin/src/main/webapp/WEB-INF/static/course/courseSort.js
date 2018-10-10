var parentIdArr = new Array();// 保存父级分类id
var parentNameArr = new Array();// 保存父级分类name
var parentFirstIdArr = new Array();// 保存跟课程分类的id
var parentFirstSortCodeArr = new Array();// 保存跟课程分类的courseSortCode
var parentFirstSortNameArr = new Array();// 保存跟课程分类的courseName
parentIdArr[0] = 0;
parentNameArr[0] = '全部';
parentFirstSortCodeArr[0] = '0A';
parentFirstSortNameArr[0] = '全部';
parentFirstIdArr[0] = 0;

var setting = {
	async : {
		enable : true,
		url : contextpath + "/courseSort/getCourseSortTypeJson",
		//autoParam : [ "id=parentId" ],
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
	$('#categoryForm').form({
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				close_div();
				doSearch();
			} else if (data == 'snnameExists') {
				$.messager.alert("提示", "名称已存在，请重新输入!");
			}
		}
	});
	$.fn.zTree.init($("#treeDemo"), setting);
});

// 点击添加课程分类
function add_courseSort() {
	// alert(parentIdArr.length+"---"+parentIdArr+"---"+parentNameArr+"----"+parentFirstSortNameArr);
	var parentFirstId="";
	var parentFirstCode="";
	var parentFirstName="";
	var parentId = "0";
	var parentName = "";
	if (parentIdArr.length <= 1) {
		// 得到左边选中的节点
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();

		if (nodes != null && nodes.length > 0) {
			parentFirstId = nodes[0].id;
			parentFirstName = nodes[0].name;
			parentFirstCode = nodes[0].courseSortCode;
			parentId=nodes[0].id;
			parentName=nodes[0].name;//第一次添加，父分类就是左边的分类
		}
		parentFirstId = parentFirstIdArr[parentFirstIdArr.length - 1];
		parentFirstName = parentFirstSortNameArr[parentFirstSortNameArr.length - 1];
		parentFirstCode = parentFirstSortCodeArr[parentFirstSortCodeArr.length - 1];
		parentFirstId += "";
		if (parentFirstId == '') {
			alert("请选择一个根分类进行添加课程分类");
			return;
		}else if(parentFirstId == '0' || parentFirstId == 0){
			alert("根分类不能是全部");
			return;
		}
	} else {
		parentFirstId = parentFirstIdArr[parentFirstIdArr.length - 1];
		parentFirstName = parentFirstSortNameArr[parentFirstSortNameArr.length - 1];
		parentFirstCode = parentFirstSortCodeArr[parentFirstSortCodeArr.length - 1];
		parentId = parentIdArr[parentIdArr.length - 1];
		parentName = parentNameArr[parentNameArr.length - 1];
		// alert("b:"+parentName);
	}
	if(parentFirstId== null || parentFirstId==0 || parentFirstId == ''){
		$("#imgShow").css("display", "");
	}else{
		$("#imgShow").css("display", "none");
	}
	// alert("parentName:"+parentName);
	$('#dlg').dialog({
		title : "添加课程分类"
	});
	$('#dlg').dialog('open');
	$('#categoryForm').form('clear');

	/*$("#orgNameShow").val(orgName);
	$("#orgId").val(parentFirstId);
	$("#parentFirstCode").val(parentFirstCode);
	$("#orgName").val(orgName);*/

	$("#courseParentNameShow").val(parentName);
	$("#courseParentName").val(parentName);

	$("#courseParentId").val(parentId);
	if (parentFirstId == '0' || parentFirstId == 0) {
		var headPhoto="../static/admin/images/qsimage.jpg";
		$("#csip").attr("src", headPhoto);
	}
}

// 查看子级分类
function checkChild() {
	$("#s_courseSort_name").textbox('setValue', "");
	var row = $('#cslistdatagrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	var courseSortCode = row[0].courseSortCode;
	var parentId = row[0].courseSortId;
	if (parentIdArr.length == 1) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();

		if (nodes != null && nodes.length > 0) {
			parentFirstSortCodeArr[0] = nodes[0].courseSortCode;
			parentFirstIdArr[0] = nodes[0].id;
			parentFirstSortNameArr[0] = nodes[0].name;
			parentIdArr[0] = nodes[0].id;
			parentNameArr[0] = nodes[0].name;
		}
	}

	parentNameArr[parentNameArr.length] = row[0].courseSortName;
	parentIdArr[parentIdArr.length] = row[0].courseSortId;
	parentFirstSortCodeArr[parentFirstSortCodeArr.length] = row[0].courseSortCode;
	parentFirstIdArr[parentFirstIdArr.length] = row[0].courseSortId;
	parentFirstSortNameArr[parentFirstSortNameArr.length] = row[0].courseSortName;
	// alert(parentIdArr+"---"+parentFirstSortCodeArr);
	// var s_courseSort_name=encodeURI($("#s_courseSort_name").val());
	var s_courseSort_name = null;
	$("#cslistdatagrid").datagrid('load', {
		parentId : parentId,
		s_courseSort_name : s_courseSort_name
	});
}
// 查看父级
function checkParent() {
	$("#s_courseSort_name").textbox('setValue', "");
	if (parentIdArr.length == 1) {
		alert("没有父级可查询啦~");
		return;
	}
	var flag = true;
	if (parentIdArr.length <= 2) {
		flag = false;
	}
	// alert(flag);

	var parentFirstCode = parentFirstSortCodeArr[parentFirstSortCodeArr.length - 2];
	var parentFirstId = parentFirstIdArr[parentFirstIdArr.length - 2];
	var parentFirstName = parentFirstSortNameArr[parentFirstSortNameArr.length - 2];
	var courseSortId = parentIdArr[parentIdArr.length - 2];
	var courseNameArr = parentNameArr[parentNameArr.length - 2];

	parentFirstSortCodeArr.splice(parentFirstSortCodeArr.length - 1, 1);
	parentFirstIdArr.splice(parentFirstIdArr.length - 1, 1);
	parentFirstSortNameArr.splice(parentFirstSortNameArr.length - 1, 1);
	parentIdArr.splice(parentIdArr.length - 1, 1);
	parentNameArr.splice(parentNameArr.length - 1, 1);

	// alert(parentIdArr+"---"+parentFirstSortCodeArr);
	var s_courseSort_name = null;
	if (!flag) {
		$("#cslistdatagrid").datagrid('load', {
			parentFirstCode : parentFirstCode,
			parentFirstId:parentFirstId,
			s_courseSort_name : s_courseSort_name
		});
	} else {
		$("#cslistdatagrid").datagrid('load', {
			parentId : courseSortId,
			s_courseSort_name : s_courseSort_name
		});
	}
}
// 编辑课程分类
function edit_courseSort() {
	$('#categoryForm').form('clear');
	var row = $('#cslistdatagrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	$('#dlg').dialog({
		title : "编辑课程分类"
	});
	$('#dlg').dialog('open');
	//var orgName = row[0].orgName;
	//$("#orgNameShow").val(orgName);
	var courseParentName = row[0].courseParentName;
	$("#courseParentNameShow").val(courseParentName);
	$('#categoryForm').form('load', row[0]);
	var courseParentId=row[0].courseParentId;
	if(courseParentId== null || courseParentId==0 || courseParentId == ''){
		$("#imgShow").css("display", "");
		var ossPath = $("#imgServer").val();
		var headPhoto =$("#csImgpath").val();
		$("#csip").attr("src", "");
		//alert(headPhoto);
		if (headPhoto != null && headPhoto != 'undefined' && headPhoto != '') {
			headPhoto = ossPath + headPhoto;
		} else {
			// 默认图片
			headPhoto="../static/admin/images/qsimage.jpg";
		}
		$("#csip").attr("src", headPhoto);
	}else{
		$("#imgShow").css("display", "none");
	}
	
}

// 点击取消添加课程分类
function close_div() {
	$('#dlg').dialog('close');
}

// 提交保存课程分类信息
function submitAdd() {
	var courseSortName = $("#courseSortName").val();
	if (courseSortName == null || courseSortName == '' || trimStr(courseSortName) == '') {
		alert("课程分类名称不能为空");
		return;
	} else if (courseSortName.length > 32) {
		alert("课程分类名称要小于32个字符");
		return;
	}

	$('#categoryForm').submit();
}
// 点击删除课程分类
function del_courseSort() {
	var selectRows = $('#cslistdatagrid').datagrid('getSelections');
	var t_url = contextpath + "/courseSort/delCourseSort";
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请至少选中一条记录删除', 'warning');
		return;
	}
	for (var i = 0; i < selectRows.length; i++) {
		var courseParentId= selectRows[i].courseParentId ;
		if(courseParentId == 0 || courseParentId == '0'){
			$.messager.alert('提示', '删除失败，不能删除根目录的课程分类', 'warning');
			return;
		}
	}
	$.messager.confirm('确认', '确认要删除这批数据吗?', function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].courseSortId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				ids : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					$('#cslistdatagrid').datagrid("reload");
					$('#cslistdatagrid').datagrid("clearSelections");
				} else {
					alert("分类下面关联了课程不能直接删除");
					// $.messager.alert("操作失败~~", data);
				}
			});
		}
	});
}
// 左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var parentFirstId = "";
	var parentFirstCode = "";
	var parentFirstName = "";
	if (nodes != null && nodes.length > 0) {
		parentFirstId = nodes[0].id;
		parentFirstName = nodes[0].name;
		parentFirstCode = nodes[0].courseSortCode;
	}
	parentIdArr.splice(0, parentIdArr.length);
	parentNameArr.splice(0, parentNameArr.length);
	parentFirstSortCodeArr.splice(0, parentFirstSortCodeArr.length);
	parentFirstSortNameArr.splice(0, parentFirstSortNameArr.length);
	parentFirstIdArr.splice(0, parentFirstIdArr.length);
	parentIdArr[0] = 0;
	parentNameArr[0] = '';
	parentFirstSortCodeArr[0] = parentFirstCode;
	parentFirstSortNameArr[0] = parentFirstName;
	parentFirstIdArr[0] = parentFirstId;

	// alert(parentIdArr+"---"+parentFirstSortCodeArr);
	var s_courseSort_name = encodeURI($("#s_courseSort_name").val());
	$("#cslistdatagrid").datagrid('load', {
		parentFirstCode : parentFirstCode,
		parentFirstId:parentFirstId,
		s_courseSort_name : s_courseSort_name
	});
}

// 查询按钮点击
function doSearch() {
	// alert(parentIdArr+"---"+parentFirstSortCodeArr);
	if (parentIdArr.length <= 1) {
		// 得到左边选中的节点
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		var parentFirstId = "";
		var parentFirstCode = "";
		var parentFirstName = "";
		if (nodes != null && nodes.length > 0) {
			parentFirstId = nodes[0].id;
			parentFirstName = nodes[0].name;
			parentFirstCode = nodes[0].courseSortCode;
		} else {
			if (parentIdArr.length == 1) {
				parentFirstCode = parentFirstSortCodeArr[parentFirstSortCodeArr.length - 1];
			}
		}
		var s_courseSort_name = encodeURI($("#s_courseSort_name").val());
		$("#cslistdatagrid").datagrid('load', {
			parentFirstCode : parentFirstCode,
			parentFirstId:parentFirstId,
			s_courseSort_name : s_courseSort_name
		});
	} else {
		var courseSortId = parentIdArr[parentIdArr.length - 1];
		var s_courseSort_name = encodeURI($("#s_courseSort_name").val());
		$("#cslistdatagrid").datagrid('load', {
			parentId : courseSortId,
			s_courseSort_name : s_courseSort_name
		});
	}
	// alert("doSearch after:"+parentIdArr+"---"+parentFirstSortCodeArr);
}
function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}