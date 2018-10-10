setTimeout("get_examPaperId()", 5);
function get_examPaperId() {
	var selectRows = $('#examPaperList_table').datagrid("getRows");
	var strIds = "";
	if (selectRows.length > 0) {
		for (var i = 0; i < selectRows.length; i++) {
			strIds += selectRows[i].paperId + ",";
		}
	}
	$("#epid").val(strIds);
}