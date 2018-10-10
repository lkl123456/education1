//##############################################班级列表树start##############################################

//##############################################班级列表树end##############################################

//##############################################工具栏start###############################################
// 点击取消添加、编辑公告
function cancleAdd_SysNotice() {
	// 公告添加取消
	parent.$('#tabs').tabs('close', '添加公告');
	parent.$('#tabs').tabs('close', '编辑公告');
}
// 提交保存公告信息
function submitAdd_SysNotice() {
	document.getElementById("newsContent").value = ue.getContent();
	$('#sysNoticeForm').form('submit', {
		url : contextpath + '/sysNotice/saveSysNotice',
		onSubmit : function(param) {
			return $(this).form('validate');
		},
		success : function(data) {
			var datajson = $.parseJSON(data);
			if (datajson['mesg'] == 'succ') {
				var url = contextpath + "/sysNotice/toSysNoticeList"
				parent.addTab_update('系统公告', url, 'icon-save');
				parent.$('#tabs').tabs('close', '添加公告');
			} else if (datajson['mesg'] == 'sameName') {
				$.messager.alert("提示", "系统公告名不能重复！", "info");
			}
		},
		error : function() {
			alert('error');
		}
	});
}
// 保存修改
function addNewsOk() {
	var newsTitle = $("#newsTitle").textbox("getValue");
	document.getElementById("newsContent").value = ue.getContent();
	getHtml();
	$('#sysNoticeForm').form('submit', {
		url : contextpath + '/sysNotice/saveSysNotice',
		onSubmit : function(param) {
			return $(this).form('validate');
		},
		success : function(data) {
			var datajson = $.parseJSON(data);
			if (datajson['mesg'] == 'succ') {
				var url = contextpath + "/sysNotice/toSysNoticeList"
				parent.addTab_update('系统公告', url, 'icon-save');
				parent.$('#tabs').tabs('close', '编辑公告');
			} else if (datajson['mesg'] == 'sameName') {
				$.messager.alert("提示", "系统公告名不能重复！", "info");
			}
		},
		error : function() {
			alert('error');
		}
	});
}

function getHtml() {
	var html = UE.getEditor('container').getContent();
	document.getElementById("newsContent").value = html;
}

// 公告添加取消
function addNewsCancle() {
	parent.$('#tabs').tabs('close', '添加公告');
	parent.$('#tabs').tabs('close', '编辑公告');
}
// ##############################################工具栏end#################################################

// ##############################################列表start################################################

// ##############################################列表end##################################################
