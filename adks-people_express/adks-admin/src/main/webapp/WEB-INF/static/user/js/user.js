var setting = {
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
		onClick : updateDataTabs,
		onAsyncSuccess : zTreeOnSuccess
	}
};
var setting1 = {
		async : {
			enable : true,
			url : contextpath + "/userIndex/getOrgsJson",
			autoParam : [ "id=parentId" ],
			type : "get"
		},
		view : {
			dblClickExpand : false
		},
		callback : {
			onClick : updateImportDataTabs,
			onAsyncSuccess : zTreeOnSuccess
		}
	};
// 默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "0", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
$(function() {
	$.fn.zTree.init($("#treeDemo"), setting);
	// 职级树
	$('#zhijiTree').combotree({
		url : contextpath + "/zhiji/getZhijisJsonAddSpace",
		method : 'get',
		onLoadSuccess : function(node, data) {
			$('#zhijiTree').combotree("setValue","");
		}
	});
});

// 左侧部门树 点击
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#userlistdatagrid").datagrid('load', {
		orgId : treeNode.id
	// 左侧部门id
	});
}
function showOrgTree(){
	$("#importTreeDemo").css("display","");
}
//导入用户，机构的点击事件
function updateImportDataTabs(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && treeNode.id!='' && treeNode.id!='0'){
		$("#userOrgId").val(treeNode.id);
		$("#userOrgName").val(treeNode.name);
		$("#userOrgCode").val(treeNode.orgCode);
		$("#orgNameShow").val(treeNode.name);
		$("#importTreeDemo").css("display","none");
	}
}
// 查询按钮点击
function doSearch() {
	var vl = $('#loginname').val();
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var deptv = "";
	if (nodes != null && nodes.length > 0) {
		deptv = nodes[0].id;
	}
	var  lname= encodeURI(vl);
	var rankId=$("#zhijiTree").combotree("getValue");
	//var positionId=$("#positionId").val();
	var userStatus=$("#userStatus").combotree("getValue");
	$("#userlistdatagrid").datagrid('load', {
		loginname : lname,
		dept : deptv,
		rankId:rankId,
		userStatus:userStatus
	});
}
//添加用户
function addUser(){
	var url = contextpath +'/userIndex/toAddUser';
	parent.addTab_update('添加用户', url, 'icon-save');
}

// 选择分校后，刷新左侧部门树
function refreshLeftDept() {
	var loginname=encodeURI($("#loginname").val());
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.reAsyncChildNodes(null, "refresh");
	$("#userlistdatagrid").datagrid('load', {
		loginname:loginname
	});
}
// 点击添加部门/用户
function addDialog(tid) {
	$('#dlg_user').dialog('open').dialog('setTitle', '添加用户');
	
	$('#hh').form('clear');
	// 设置截至日期的默认值
	$("#overdate").datebox('setValue', '2099-12-30');
	if (tid == 'deptadd') {
		$('#dlg').dialog('open');
		$('#ff').form('clear');
	} else if (tid == 'useradd') {
		$('#dlg_user').dialog('open').dialog('setTitle', '新增用户');
		/*$('#cc2').combotree({
			url : contextpath + "/userIndex/getOrgsJson",
			method : 'get',
			onLoadSuccess : function(node, data) {
				var node = $('#cc2').combotree("tree").tree('getRoot');
				$('#cc2').combotree("setValue", node.id);
			}
		});*/
		$.fn.zTree.init($("#treeDemo1"), setting1);
		// 民族下拉框
		$('#ss1').combobox({
			url : contextpath+ "/userIndex/getUserNationalityJson?name=nationality",
			valueField : 'id',
			textField : 'text',
			onLoadSuccess : function() {
				$('#ss1').combobox('select', '3');// 菜单项可以text或者id
			}
		});
		// 政治面貌下拉框
		$('#ss2').combobox({
			url : contextpath+ "/userIndex/getUserNationalityJson?name=politics",
			valueField : 'id',
			textField : 'text',
			onLoadSuccess : function() {
				$('#ss2').combobox('select', '14');// 菜单项可以text或者id
			}
		});
		// 职级树
		$('#zhijiTree').combotree({
			url : contextpath + "/zhiji/getZhijisJson",
			method : 'get',
			onLoadSuccess : function(node, data) {
				var node = $('#zhijiTree').combotree("tree").tree('getRoot');
				$('#zhijiTree').combotree("setValue", node.id);
			}
		});
		$("input[type=radio][name=userSex][value=1]").attr("checked",true);  
		
	}
}
// 点击取消
function cancleAdd(tid) {
	if (tid == "deptadd") {
		$('#dlg').dialog('close');
	} else if (tid == 'useradd') {
		$('#dlg_user').dialog('close');
	}
}

// 点击编辑用户按钮
function editUser() {
	$("#userPassword").attr("disabled","disabled");
	$("#uiPath").attr("src", "");
	var row = $('#userlistdatagrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	var url = contextpath +'/userIndex/toAddUser?userId='+row[0].userId;
	parent.addTab_update('编辑用户', url, 'icon-save');
}
// 点击删除用户
function deleteUser() {
	var selectRows = $('#userlistdatagrid').datagrid('getSelections');
	var t_info = "删除";
	var t_url = contextpath + "/userIndex/delUser";
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请至少选中一条记录删除', 'warning');
		return;
	}
	$.messager.confirm('确认', '确认要删除这批数据吗?', function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].userId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				ids : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					$('#userlistdatagrid').datagrid("reload");
					$('#userlistdatagrid').datagrid("clearSelections");
				}else if(data == 'error') {
					$.messager.alert("Info", "删除失败，用户已学习或是班级学员!");
				}else{
					$.messager.alert("Info", "操作失败~~");
				}
			});
		}
	});
}
// 提交保存
function submitAdd(tid) {
	// 设置ajax form
	var t1 = $('#zhijiTree').combotree('tree'); // 获取树对象
	var n1 = t1.tree('getSelected'); // 获取选择的节点
	$("#rankName").val(n1.text);
	//手机号码验证
	var userPhone=$("#userPhone").val();
	var phontSpan=vailUserCellphone(userPhone);
	if(phontSpan != ''){
		alert(phontSpan);
		return ;
	}
	//邮箱验证
	var userMail=$("#userMail").val();
	var userMailSpan=vailUserMail(userMail);
	if(userMailSpan != ''){
		alert(userMailSpan);
		return ;
	}
	//验证身份证号
	var cardNumer=$("#cardNumer").val();
	var cardNumerSpan=test(cardNumer);
	if(cardNumerSpan!= '验证通过'){
		alert(cardNumerSpan);
		return ;
	}
	$('#hh').form({
		url : contextpath + '/userIndex/saveUser',
		onSubmit : function(param) {
			param.userBir = $('#userBirthday').datebox('getValue');
			param.odate = $('#overdate').datebox('getValue');
			// param.orgName=$('#orgName').html();
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				$('#dlg_user').dialog('close');
				doSearch();
			} else if (data == 'snnameExists') {
				$.messager.alert("提示", "用户名已经存在，请重新输入!");
			}
		}
	});
	$('#hh').submit();
}
// 刷新树结构
function reloadTree() {
	// alert('刷新，重新加载树');
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.reAsyncChildNodes(null, "refresh");
}

function addUserclick() {
	$('#dlg_user').dialog('open');
}

function formatColumnData(value, row, index) {
	if (value == 1) {
		return '开通';
	} else {
		return '停用';
	}
}

// 改变用户状态，开通/停用
function changeUserStatus(tips) {
	var selectRows = $('#userlistdatagrid').datagrid('getSelections');
	var t_url = contextpath + "/userIndex/changeUser";
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请勾选需要操作的用户!', 'warning');
		return;
	}
	var hType = "";// 操作类型 1：重置密码，2：停用，3：恢复
	if (tips == 'stop') {
		hType = 2;// 停用
	} else if (tips == 'continue') {
		hType = 3;// 恢复
	} else if (tips == 'resetpass') {
		hType = 1;// 重置密码
	}
	var strIds = "";
	for (var i = 0; i < selectRows.length; i++) {
		strIds += selectRows[i].userId + ",";
	}
	strIds = strIds.substr(0, strIds.length - 1);
	$.post(t_url, {
		userIds : strIds,
		handelType : hType
	}, function(data) {
		if (data == "succ") {
			if (tips == 'stop') {
				alert("该用户已停用");// 停用
			} else if (tips == 'continue') {
				alert("恢复成功");// 恢复
			} else if (tips == 'resetpass') {
				alert("密码重置成功，新密码为111111");// 重置密码
			}
			// 刷新表格，去掉选中状态的 那些行。
			$('#userlistdatagrid').datagrid("reload");
			$('#userlistdatagrid').datagrid("clearSelections");
		} else {
			$.messager.alert("操作失败~~", data);
		}
	});
}

// Date 格式化“yyyy-MM-dd”
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function setRowStyle(index, row) {
	if (row.status == 2) {
		return 'background-color:#cccccc;font-weight:bold;';
	}
}

/**
 * 批量导入用户
 */
function importUser() {
	/*$('#import_org').combotree({
		url : contextpath + "/userIndex/getOrgsJson",
		method : 'get',
		onLoadSuccess : function(node, data) {
			var node = $('#import_org').combotree("tree").tree('getRoot');
			$('#import_org').combotree("setValue", node.id);
		}
	});*/
	$.fn.zTree.init($("#importTreeDemo"), setting1);
	$("#importTreeDemo").css("display","none");
	$('#importUser').dialog('open');
	$('#imUser').form('clear');
}
function cancleImport() {
	$('#importUser').dialog('close');
}

// 执行导入请求
function submitImport() {
	var oName=$("#userOrgName").val();
	var orgCode = $("#userOrgCode").val();
	if(orgCode == null || oName == '' || oName == '机构'){
		$.messager.alert('提示信息', "请选择机构");
		return ;
	}
	var orgName = encodeURI(oName);
	var orgId=$("#userOrgId").val();
	$('#imUser').form({
		url : contextpath + '/userIndex/importUsers',
		onSubmit : function(param) {
			param.orgName = orgName;
			param.orgCode = orgCode;
			param.orgId=orgId;
			return $(this).form('validate');
		},
		success : function(data) {
			var json = $.parseJSON(data);
			if (json['code'] == 200) {
				$('#importUser').dialog('close');
				doSearch();
				$.messager.alert('Info', json['message']);
			} else {
				// 失败
				$.messager.alert('Error', json['message']);
			}
		}
	});
	$('#imUser').submit();
}

// 修改用户的角色
function updateUserRole() {
	var selectRows = $('#userlistdatagrid').datagrid('getSelections');
	var t_url = contextpath + "/userIndex/changeUser";
	if (selectRows.length < 1) {
		$.messager.alert('提示', '请勾选需要操作的用户!', 'warning');
		return;
	}
	var userId = selectRows[0].userId;
	var userName = encodeURI(selectRows[0].userName);
	var url = contextpath +'/userIndex/userRoleList?userId=' + userId
			+ '&userName=' + userName;
	parent.addTab_update('用户角色管理', url, 'icon-save');
}

function changeZhiWu(){
	var t1 = $('#zhijiTree').combotree('tree'); // 获取树对象
	var n1 = t1.tree('getSelected'); // 获取选择的节点
	var rankId=n1.id;
	$.ajax({
		async:false,
		url:contextpath+"/userIndex/getZhiwu.do?zhijiId="+rankId,
		type:'post',
		success:function(data){
			var dataJSON=$.parseJSON(data);
			$('#positionId').empty();
			$('#positionId').append("<option value='' selected = 'true'></option>");
			for (i = 0; i < dataJSON.length; i++){
				$('#positionId').append("<option value='"+dataJSON[i]['rankId']+"'>"+dataJSON[i]['rankName']+"</option>");
			}
		}
	});
}