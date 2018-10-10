var setting = {
	async : {
		enable : true,
		url : contextpath+"/course/category",
	},
	data: {
		key: {
			name:"courseSortName",
		},
		simpleData: {
			idKey: "courseSortId",
			pIdKey: "courseParentId",
		}
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
	if(nodes){
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
$(function() {
	$.fn.zTree.init($("#treeDemo"), setting);
	//设置ajax form
	$('#categoryForm').form({
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				//alert('信息保存成功！');
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				//var node = treeObj.getNodeByParam("id", data['pid'], null);
				treeObj.reAsyncChildNodes(null, "refresh"); // 单独节点的局部刷新
				$("#cc").combotree('reload');
				$('#categoryForm').form('clear');
				$('#dlg').dialog('close');
			}
		}
	});
});

// 点击添加课程分类
function add_courseCategory(){
	$('#dlg').dialog('open');
	$("#t_category_status").attr("checked",true);
}

// 点击取消添加课程分类
function cancleAdd_category(){
	$('#dlg').dialog('close');
}

// 提交保存课程分类信息
function submitAdd_category(){
	$('#categoryForm').submit();
}
// 刷新树结构
function reloadTree(){
	alert('刷新，重新加载树！');
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.reAsyncChildNodes(null, "refresh");
	$("#cc").combotree('reload');
}

// 点击删除课程分类
function del_courseCategory() {  
	var id = $("#cc_ztree_node_id").val();
	if (id == null || id == '') {  
        $.messager.alert("提示", "请选中您要删除的课程分类信息！", "info");  
        return;  
    }else if(id == '0'){
    	$.messager.alert("提示", "不能删除课程顶级分类信息！", "info");  
        return;
    }  
	
    $.messager.confirm("确认", "您确定要删除选中的分类信息吗？", function (r) {  
        if (r) {  
            $.post(contextpath+"/courseCategory/delCourseCategory", { id: id }, function (data) {  
                if (data == "succ") {  
                    // 课程分类树
                	reloadTree(); 
                } else {  
                    $.messager.alert("删除失败~~", data);  
                }  
            });  
        }  
    }); 
}

// ============================ 课程分类、课程分割线 ===============================

$(function() {
	
	if(typeof($('#s_course_orgSnname').val()) != "undefined"){
		// 加载分校查询条件的 onchange()
		//$("#s_course_orgId").combobox({
			//onChange: function (n,o) {
				//doSearch();
			//}
		//});
	}else{
		// 隐藏分类操作按钮
		$('#btn_category_div').hide();
	}
});

//左侧课程分类树 点击事件
function updateDataTabs(event, treeId, treeNode, clickFlag) {
	$("#cc_ztree_node_id").val(treeNode.id);
	$("#cc_ztree_node_name").val(treeNode.name);
	$("#cc_ztree_node_code").val(treeNode.code);
	$("#courselistdatagrid").datagrid('load', {
		catId : treeNode.id // 左侧课程分类id
	});
}

// 查询按钮点击
function doSearch() {
	var categoryCode = $("#cc_ztree_node_code").val();
	if(typeof($('#s_course_orgSnname').val()) == "undefined"){
		$("#courselistdatagrid").datagrid('load', {
			s_course_name : $('#s_course_name').val(),
			categoryCode : categoryCode
		});
	}else{
		$("#courselistdatagrid").datagrid('load', {
			s_course_name : $('#s_course_name').val(),
			categoryCode : categoryCode,
			s_course_orgSnname : $('#s_course_orgSnname').combobox('getValue')
		});
	}
}

// 点击添加课程
function add_course(){
	$('#dlg_course').dialog('open').dialog('refresh', contextpath+'/course/addCourse');
}

// 点击编辑课程
function edit_course(){
	var rows = $('#courselistdatagrid').datagrid('getSelections');
	if(rows.length != 1){
		$.messager.alert('提示','请选中一条记录编辑','warning');
		return ;
	}
	$('#dlg_course').dialog('open').dialog('setTitle','编辑课程');
	$('#courseForm').form('clear');
	$('#p').progressbar('setValue', 0);
	//给表单赋值
	$('#courseForm').form('load', rows[0]);
	var paths = $('#cover_path').val();
	var imgServer = $('#imgServer').val();
	$('#img_coverpath').attr('src',imgServer+paths);
}

// 点击取消添加、编辑课程
function cancleAdd_course(){
	$('#dlg_course').dialog('close');
}

//上传课程封面图片
function uploadCourseCover(){
	var imgServer = $('#imgServer').val();
	// data-options="novalidate:true"
	$('#courseForm').form('submit',{
		url:contextpath+'/course/updateCourseCover',
		onSubmit: function(){
			return true;
		},
		success:function(data){
			//alert(data);
			$('#cover_path').val(data);
			$('#img_coverpath').attr('src',imgServer+data);
		}
	});
}


// 提交保存课程信息
function submitAdd_course(status){
	$('#t_course_status').val(status);
//	$('#courseForm').action = contextpath+'/course/saveCourse';
//	$('#courseForm').submit();
	$('#courseForm').form('submit',{
		url:contextpath+'/course/saveCourse',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			doSearch();
			$('#dlg_course').dialog('close');
		}
	});
}


// 点击删除课程（flag：-1表示删除，1表示发布，2表示下架）
function del_course(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#courselistdatagrid').datagrid("getSelections");
    var t_info = "删除";
    var t_url = contextpath+"/course/delCourse";
    if(flag == 1){
    	t_info = "发布";
    	var t_url = contextpath+"/course/updateCourse_fb";
    }else if(flag == 2){
    	t_info = "下架";
    	var t_url = contextpath+"/course/updateCourse_xj";
    }
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！", "info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].id + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            $.post(t_url, { ids: strIds,flag: flag }, function (data) {  
            	if (data == "succ") {  
                    //刷新表格，去掉选中状态的 那些行。  
                    $('#courselistdatagrid').datagrid("reload");  
                    $('#courselistdatagrid').datagrid("clearSelections");  
                } else {  
                    $.messager.alert("操作失败~~", data);  
                }  
            });  
        }  
    });  
}

//点击开始上传视频文件
function startUpload(){
	listeningUploadProcess();
	$('#courseForm').form('submit',{
		url:contextpath+'/course/uploadVideo',
		onSubmit: function(){
			return true;
		},
		success:function(data){
			$('#videopath').val(data);
		}
	});
	
}
//获取实时上传进度
function listeningUploadProcess(){
//	var value = $('#p').progressbar('getValue');
	var value = 0;
	$.ajax({
		url: contextpath+"/course/getUploadProcessNum",
		async: false,//改为同步方式
		type: "get",
		success: function (data) {
			value = data;
		},error:function(){
			alert('error');
		}
	});
	if (value < 100){
		$('#p').progressbar('setValue', value);
		setTimeout(arguments.callee, 500);
	}else if(value == 100){
		$('#p').progressbar('setValue', value);
	}
}
