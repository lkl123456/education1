
function getCourseSortTreeDate (orgId){//页面加载时给有孩子结点的元素动态添加图标
	var tree = new dTree('tree');//创建一个对象.
	var _html = "";
	$.ajax({
		type: "POST",
		url:  contextPath+"/course/getCourseSortTreeDate.do?orgId="+orgId,
		data: {timeStampe:new Date()},
		async: false,
		success: function(data){
			_html = data;
		}
	});
	if(_html!=""){
		for(var i=0;i<_html.length;i++){
			tree.add(_html[i].courseSortId,
					_html[i].courseParentId,
					_html[i].courseSortName,
					""); 
		}
	}
	$("#menu_zzjs_net").html(tree);
}

