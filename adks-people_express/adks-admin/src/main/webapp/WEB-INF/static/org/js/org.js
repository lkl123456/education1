$(function() {
	$('#orgForm').form({
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				close_div();
				doSearch();
			} else if (data == 'snnameExists') {
				$.messager.alert("提示", "机构名称已存在，请重新输入!");
			}
		}
	});
	$("#orgConfigForm").form({
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				close_div();
			} else if (data == 'snnameExists') {
				$.messager.alert("提示", "唯一标识已存在，请重新输入!");
			}
		}
	});
	// 隐藏列（机构ID）
	// $('#orgList_table').datagrid('hideColumn','org_id');
})

// 点击查询按钮
function doSearch() {
	var orgName = encodeURI($('#s_org_name').val());
	// alert(orgName);
	$("#orgList_table").datagrid('load', {
		s_org_name : orgName
	});
}
// 查看子级机构管理
function editOrgVal() {
	var selectRows = $('#orgList_table').datagrid("getSelections");
	if (selectRows == null || selectRows.length <= 0) {
		$.messager.alert("提示", "请先选中一个父级枚举", "info");
		return;
	}
	var row = selectRows[0];
	var orgId = row.orgId;// 现在请求的父级id
	$("#orgParentId").val(orgId);// 当前页的父级id

	var url = contextpath +'/org/orgList?parentId=' + orgId;
	parent.addTab_update('机构管理', url, 'icon-save');

}
// 返回上一级的机构
function editOrgValBefore() {
	var overParentId = $("#overParentId").val();
	var url = contextpath +'/org/orgList?parentId=' + overParentId;
	parent.addTab_update('机构管理', url, 'icon-save');
}

// 点击添加机构信息
function add_org() {
	$('#dlg').dialog({
		title : "添加机构"
	});
	$('#dlg').dialog('open');
	$('#orgForm').form('clear');

	var orgParentName = $("#orgParentName").val();
	var orgParentId = $("#orgParentId").val();
	$('#parentName').val(orgParentName);
	$('#parentId').val(orgParentId);
	$('#orgNameOrgShow').val(orgParentName);
}

// 点击编辑网校信息(加载显示相关信息)
function edit_org() {
	var selectRows = $('#orgList_table').datagrid("getSelections");
	if (selectRows.length < 1 || selectRows.length > 1) {
		$.messager.alert("提示", "请您选中一条需要编辑的信息！", "info");
		return;
	}
	$('#dlg').dialog({
		title : "编辑机构"
	});
	$('#dlg').dialog('open');
	$('#orgForm').form('clear');
	var orgParentName = $("#orgParentName").val();
	$('#orgNameOrgShow').val(orgParentName);
	// 给表单赋值
	$('#orgForm').form('load', selectRows[0]);
}

// 点击取消 添加或编辑信息
function close_div() {
	$('#dlg').dialog('close');
	$('#dlgConfig').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm() {
	// ajax form提交
	var orgName = $("#orgName").val();
	if (orgName == null || orgName == '' || trimStr(orgName) == '') {
		$.messager.alert("提示", "请输入机构名称", "info");
		return;
	}else if(orgName.length>64){
		$.messager.alert("提示", "机构名称不能超过64个字符", "info");
		return;
	}
	$('#orgForm').submit();
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）
function del_org(flag) {
	// 把你选中的 数据查询出来。
	var selectRows = $('#orgList_table').datagrid("getSelections");
	var t_info = "删除";
	var t_url = contextpath + "/org/delOrg";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}

	$.messager.confirm("确认", "您确定要" + t_info + "选中的机构以及其子机构吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].orgId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			$.post(t_url, {
				ids : strIds
			}, function(data) {
				if (data == "succ") {
					// 刷新表格，去掉选中状态的 那些行。
					$('#orgList_table').datagrid("reload");
					$('#orgList_table').datagrid("clearSelections");
				} else if (data == 'hasUser') {
					$.messager.alert("提示", "删除失败,该机构下已跟用户、班级、新闻、课程或讲师相关联", "info");
					$('#orgList_table').datagrid("clearSelections");
				} else {
					$.messager.alert("操作失败~~", data);
				}
			});
		}
	});
}

// 以下是机构配置信息的
// 最后一列，机构配置信息列
function formatOper(val, row, index) {
	return '<a href="javascript:editOrgConfig(' + row.orgId + ','+index+')">编辑</a>';

}
// 编辑机构配置信息
function editOrgConfig(orgId,index) {

	//取消原先的选中
	$('#orgList_table').datagrid('clearSelections');
	$("#orgList_table").datagrid("selectRow",index);
	var obj;
	$.ajax({
		url : contextpath + '/org/getOrgConfig',
		async : false,
		type : "post",
		data : {
			"orgId" : orgId
		},
		success : function(data) {
			// alert(data);
			if (data != null && '' != data) {
				obj = JSON.parse(data);
			}
		},
		error : function() {
			alert('error');
		}
	});
	// 给表单赋值
	$('#dlgConfig').dialog({
		title : "编辑机构配置信息"
	});
	$('#dlgConfig').dialog('open');
	$('#orgConfigForm').form('clear');
	var orgName = $("#orgParentName").val();

	var oname = obj.orgName;
	$("#orgNameShow").val(oname);
	$("#orgConName").val(oname);

	if (obj != null && '' != obj) {
		var orgId = obj.orgId;
		$("#orgConId").val(orgId);

		var orgConfigId = obj.orgConfigId;
		$("#orgConfigId").val(orgConfigId);

		var orgUrl = obj.orgUrl;
		if (orgUrl != null && orgUrl != 'undefined' && orgUrl != '') {
			$("#orgUrl").textbox("setValue", orgUrl);
		}
		var ossPath = $("#ossPath").val();
		$("#orgLogoPath").attr("src", "");
		var orgLogoPath = obj.orgLogoPath;
		if (orgLogoPath != null && orgLogoPath != 'undefined'
				&& orgLogoPath != '') {
			orgLogoPath = ossPath + orgLogoPath;
		} else {
			// 默认图片
			orgLogoPath="../static/admin/images/qsimage.jpg";
		}
		$("#orgLogoPath").attr("src", orgLogoPath);
		$("#orgLogoPathTrue").val(obj.orgLogoPath);
		$("#orgBanner").attr("src", "");
		var orgBanner = obj.orgBanner;
		if (orgBanner != null && orgBanner != 'undefined' && orgBanner != '') {
			orgBanner = ossPath + orgBanner;
		} else {
			// 默认图片
			orgBanner="../static/admin/images/qsimage.jpg";
		}
		$("#orgBanner").attr("src", orgBanner);
		$("#orgBannerTrue").val(obj.orgBanner);
		var orgDesc = obj.orgDesc;
		if (orgDesc != null && orgDesc != 'undefined' && orgDesc != '') {
			$("#orgDesc").val(orgDesc);
		}

		var contactUser = obj.contactUser;
		if (contactUser != null && contactUser != 'undefined'
				&& contactUser != '') {
			$("#contactUser").textbox("setValue", contactUser);
		}

		var contactPhone = obj.contactPhone;
		if (contactPhone != null && contactPhone != 'undefined'
				&& contactPhone != '') {
			$("#contactPhone").textbox("setValue", contactPhone);
		}

		var contactQQ = obj.contactQQ;
		if (contactQQ != null && contactQQ != 'undefined' && contactQQ != '') {
			$("#contactQQ").textbox("setValue", contactQQ);
		}

		var contactMail = obj.contactMail;
		if (contactMail != null && contactMail != 'undefined'
				&& contactMail != '') {
			$("#contactMail").textbox("setValue", contactMail);
		}

		var copyRight = obj.copyRight;
		if (copyRight != null && copyRight != 'undefined' && copyRight != '') {
			$("#copyRight").val(copyRight);
		}
	}
}
$.extend(
	$.fn.validatebox.defaults.rules,
	{
		emailRex : {
			validator : function(value) {
				var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
				return reg.test(value);
			},
			message : '输入邮箱格式不准确.'
		},
		phoneRex : {
			validator : function(value) {
				var reg = /^1[3|4|5|8|9]\d{9}$/;
				return reg.test(value);
			},
			message : '输入手机号码格式不准确.'
		},
		urlRex : {
			validator : function(value) {
				var strReg = '(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]';
				var reg = new RegExp(strReg);
				return reg.test(value);
			},
			message : '输入URL地址不准确.'
		},
		QQRex : {// 验证整数或小数
			validator : function(value) {
				return /^\d+(\.\d+)?$/i.test(value);
			},
			message : '请输入数字，并确保格式正确'
		}
	})
// 保存机构配置信息
function submitFormConfig() {

	// ajax form提交
	// 机构域名
	var orgUrl = $("#orgUrl").val();
	if (orgUrl != null && orgUrl != '' && trimStr(orgUrl) != '') {
		var strReg = '(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]';
		var reg = new RegExp(strReg);
		if (!reg.test(orgUrl)) {
			alert('机构域名格式有误');
			return;
		}
	}
	//联系人
	var contactUser=$("#contactUser").val();
	if(contactUser !=null && contactUser!='' && trimStr(contactUser)!=''){
		if(contactUser.length>500){
			alert('联系人名称不能超过32个字符');
			return;
		}
	}
	// 联系人电话
	var contactPhone = $("#contactPhone").val();
	var phontSpan=vailUserCellphone(contactPhone);
	if(phontSpan != ''){
		alert(phontSpan);
		return ;
	}
	// 联系人QQ
	var contactQQ = $("#contactQQ").val();
	if (contactQQ != null && contactQQ != '' && trimStr(contactQQ)!='') {
		var reg = /^[0-9]*$/;
		if (!reg.test(contactQQ)) {
			alert('联系人QQ格式有误');
			return;
		}
	}
	// 联系人邮箱
	var contactMail = $("#contactMail").val();
	var userMailSpan=vailUserMail(contactMail);
	if(userMailSpan != ''){
		alert(userMailSpan);
		return ;
	}
	//机构描述
	var orgDesc=$("#orgDesc").val();
	if(orgDesc !=null && orgDesc!='' && trimStr(orgDesc)!=''){
		if(orgDesc.length>500){
			alert('机构描述的长度不能超过500');
			return;
		}
	}
	//机构copy信息
	var copyRight=$("#copyRight").val();
	if(copyRight !=null && copyRight!='' && trimStr(copyRight)!=''){
		if(copyRight.length>500){
			alert('版权信息的长度不能超过500');
			return;
		}
	}
	$('#orgConfigForm').submit();
}

function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}
