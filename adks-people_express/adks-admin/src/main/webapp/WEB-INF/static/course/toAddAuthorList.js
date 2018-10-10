var orgCode=$("#orgCode").val();
var orgId=$("#orgId").val();
//alert(orgCode);
var setting = {
	async : {
		enable : true,
		url : contextpath+"/userIndex/getOrgsJsontoAuthor?parentId="+orgId+"&first=1",
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
//默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	if(nodes){
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
$(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
});

// 左侧部门树 点击
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#s_author_name").textbox('setValue',"");
	$("#authorlistdatagrid").datagrid('load', {
		orgCode : treeNode.orgCode // 左侧部门id
	});
}

// 查询按钮点击
function doSearch() {
	var s_author_name = encodeURI($('#s_author_name').val());
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var orgCode = "";
	if(nodes != null && nodes.length > 0){
		orgCode = nodes[0].orgCode;
	}
	$("#authorlistdatagrid").datagrid('load', {
		s_author_name : s_author_name,
		orgCode : orgCode
	});
}
function formatColumnData(value,row,index){
	if(value == 1){
		return '男';
	}else{
		return '女';
	}
}
function formatColumnData1(value,row,index){
	return "<a href='#' onclick=\""+"javascript:checkAuthor('"+row.authorId+"','"+row.authorName+"')\""+" >选择</a>";
	//return "";
}

//选择讲师
function checkAuthor(authorId,authorName){
	var courseId=$("#courseId").val();
	//var courseSortId=$("#courseSortId").val();
	//var aName=encodeURI(authorName);
	//var url=contextpath +'/course/toAddCourse?courseSortId='+courseSortId+'&courseId='+courseId+'&authorName='+aName+'&authorId='+authorId;
	//alert(url);
	//parent.addTab_update('添加课程',url,'icon-save');
	if(courseId == null || courseId == ''){
		if (parent.$('#tabs').tabs('exists','添加课程')) {  
			var smsTab=parent.$('#tabs').tabs('getTab',"添加课程");
			var ifram = smsTab.find('iframe')[0];
			//var iframHtml=$(ifram.contentWindow.document.body).html();  
			//alert(iframHtml);
			//alert($(ifram.contentWindow.document).find("#courseSortNameShow").val());
			$(ifram.contentWindow.document).find("#authorId").val(authorId);   
		    $(ifram.contentWindow.document).find("#authorName").val(authorName);   
		    $(ifram.contentWindow.document).find("#showAuthorName").val(authorName);   
		}
	}else{
		if (parent.$('#tabs').tabs('exists','编辑课程')) {  
			var smsTab=parent.$('#tabs').tabs('getTab',"编辑课程");
			var ifram = smsTab.find('iframe')[0];
			//var iframHtml=$(ifram.contentWindow.document.body).html();  
			//alert(iframHtml);
			//alert($(ifram.contentWindow.document).find("#courseSortNameShow").val());
			$(ifram.contentWindow.document).find("#authorId").val(authorId);   
		    $(ifram.contentWindow.document).find("#authorName").val(authorName);   
		    $(ifram.contentWindow.document).find("#showAuthorName").val(authorName);   
		}
	}
	
	parent.$('#tabs').tabs('close', '选择讲师');
}

function setRowStyle(index,row){
	if(row.status == 2){
		return 'background-color:#cccccc;font-weight:bold;';
	}
}

