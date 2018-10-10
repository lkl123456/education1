//##############################################工具栏start###############################################
// 查询按钮点击
function doSearch() {
	var name = $('#userName').val();
	$("#sysNoticeListTable").datagrid('load', {
		userName : name,
		deptIds : depts.toString()
	});
}
// 点击添加公告
function addSysNotice() {
	parent.addTab("添加公告", contextpath + '/sysNotice/toAddSysNotice',
			"icon icon-nav");
}

// 点击编辑公告
function editSysNotice() {
	var rows = $('#sysNoticeListTable').datagrid('getSelections');
	if (rows == null || rows.length <= 0) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	var newsId = rows[0].newsId;
	// alert(newsId);
	var url = contextpath + '/sysNotice/toAddSysNotice?newsId=' + newsId;
	parent.addTab_update('编辑公告', url, 'icon-save');
}
// 删除班级公告
function deleteSysNotice() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#sysNoticeListTable').datagrid("getSelections");
	var t_info = "删除";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}
	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].newsId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);

			if (strIds.length > 0) {
				$.ajax({
					url : contextpath + "/sysNotice/deleteSysNotice",
					async : false,// 改为同步方式
					type : "get",
					data : {
						"newsIds" : strIds
					},
					success : function(data) {
						// 刷新表格，去掉选中状态的 那些行。
						$('#sysNoticeListTable').datagrid("reload");
						$('#sysNoticeListTable').datagrid("clearSelections");
					},
					error : function() {
						alert('error');
					}
				});
			} else {
				$.messager.alert("操作失败~~", data);
			}

		}
	});
}
// ##############################################工具栏end#################################################

// ##############################################列表start################################################
function formatDateConversion(value, row, index) {
	if (value != null) {
		var unixTimestamp = new Date(value);
		return unixTimestamp.Format('yyyy-MM-dd');
	}
}
// ##############################################列表end##################################################
function setRowStyle(index, row) {
	if (row.status == 0) {
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}
