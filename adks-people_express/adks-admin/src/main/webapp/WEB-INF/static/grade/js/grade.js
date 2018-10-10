//##############################################列表start##############################################
//格式化列数据
function formatStatus(value, row, index) {
	if (value == 1) {
		return '发布';
	} else if (value == 2) {
		return '未发布';
	}
}
// ##############################################列表操作end##############################################

// ##############################################工具栏操作start##############################################
// 查询按钮点击
function doSearch() {
	var  gradeName= encodeURI($('#gradeName').val());
	$("#gradeListTable").datagrid('load', {
		gradeName : gradeName
	});
}
// 新增班级
function addGrade() {
	var cp="../static/admin/images/qsimage.jpg";
	$('#dlg_grade').dialog('open').dialog('setTitle', '新增培训班');
	$('#gipath').attr('src', cp);
	$('#ciPath').attr('src', cp);
	$('#ePath').attr('src', cp);
	$('#gradeForm').form('clear');
	//班级状态下拉框
//	$('#ss1').combobox({
//		url : contextpath+"/userIndex/getUserNationalityJson?name=isPublish",
//		valueField : 'id',
//		textField : 'text',
//		onLoadSuccess : function() {
//			$('#ss1').combobox('select', '20');// 菜单项可以text或者id
//		}
//	});
	//作业下拉框
//	$('#ss2').combobox({
//		url : contextpath+"/userIndex/getUserNationalityJson?name=yesNo",
//		valueField : 'id',
//		textField : 'text',
//		onLoadSuccess : function() {
//			$('#ss2').combobox('select', '2');// 菜单项可以text或者id
//		}
//	});
	//考试下拉框
//	$('#ss3').combobox({
//		url : contextpath+"/userIndex/getUserNationalityJson?name=yesNo",
//		valueField : 'id',
//		textField : 'text',
//		onLoadSuccess : function() {
//			$('#ss3').combobox('select', '2');// 菜单项可以text或者id
//		}
//	});
}
// 编辑班级
function editGrade() {
	var row = $('#gradeListTable').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条记录编辑', 'warning');
		return;
	}
	$('#dlg_grade').dialog('open').dialog('setTitle', '编辑培训班');
	$('#gradeForm').form('clear');
	// 给表单赋值
	// $('#gradeForm').form('load','/grade/loadEditGradeFormData?gradeId='+row[0].id);
	$('#gradeForm').form('load', row[0]);

	var ossPath = $("#imgServer").val();
	var gipath = $("#gradeImg").val();
	var ciPath = $("#certificateImg").val();
	var ePath = $("#eleSeal").val();
	var cp="../static/admin/images/qsimage.jpg";
	if (gipath != null && gipath != 'undefined' && gipath != '') {
		gipath = ossPath + gipath;
	} else {
		// 默认图片
		gipath=cp;
	}
	if (ciPath != null && ciPath != 'undefined' && ciPath != '') {
		ciPath = ossPath + ciPath;
	} else {
		// 默认图片
		ciPath=cp;
	}
	if (ePath != null && ePath != 'undefined' && ePath != '') {
		ePath = ossPath + ePath;
	} else {
		// 默认图片
		ePath=cp;
	}

	$("#gipath").attr("src", gipath);
	$("#ciPath").attr("src", ciPath);
	$("#ePath").attr("src", ePath);
}
// 配置培训班
function configerGrade() {
	var row = $('#gradeListTable').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一个培训班配置', 'warning');
		return;
	}
	var url = contextpath + "/grade/toConfigureGradeCourse?gradeId="
			+ row[0].gradeId;
	parent.addTab_update('配置培训班', url, 'icon-save');
}
// 删除班级
function deleteGrade() {
	// 把你选中的 数据查询出来。
	var selectRows = $('#gradeListTable').datagrid("getSelections");
	var t_info = "删除";
	if (selectRows.length < 1) {
		$.messager.alert("提示", "请选中要" + t_info + "的数据信息！", "info");
		return;
	}
	$.messager.confirm("确认", "您确定要" + t_info + "选中的信息吗？", function(r) {
		if (r) {
			var strIds = "";
			for (var i = 0; i < selectRows.length; i++) {
				strIds += selectRows[i].gradeId + ",";
			}
			strIds = strIds.substr(0, strIds.length - 1);
			if (strIds.length > 0) {
				$.ajax({
					url : contextpath + "/grade/delGrade",
					async : false,// 改为同步方式
					type : "get",
					data : {
						"gradeIds" : strIds
					},
					success : function(data) {
						// 刷新表格，去掉选中状态的 那些行。
						$('#gradeListTable').datagrid("reload");
						$('#gradeListTable').datagrid("clearSelections");
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
// ##############################################工具栏操作end##############################################

// ##############################################子页面操作start##############################################
// 提交表单
function submitAddGrade(tips) {
	var succCallbacktype = tips; // 标示success之后的操作，1,2,——》2需要走下一步配置课程
	if (tips == 2) {
		tips = 1;
	}
	var startDate = $('#s_startDate_str').datebox('getValue');
	var sDate=new Date((startDate.substring(0,10)+" 00:00:00").replace(/-/g,"/"));
	var endDate = $('#s_endDate_str').datebox('getValue');
	var eDate=new Date((endDate.substring(0,10)+" 00:00:00").replace(/-/g,"/"));
	if(sDate>=eDate){
		$.messager.alert('友情提示', '开始时间不能大于或等于结束时间!', 'info');
		return;
	}
	$('#status').val(tips);
	//提交表单前，设置按钮不可点击，防止重复提交
	$('#gradeSave').linkbutton('disable');
	$('#gradeConfig').linkbutton('disable');
	$('#gradeCancle').linkbutton('disable');
	$('#gradeForm').form(
			'submit',
			{
				url : contextpath + '/grade/saveGrade',
				onSubmit : function(param) {
					param.s_startDate_str = $('#s_startDate_str').datetimebox('getValue');
					param.s_endDate_str = $('#s_endDate_str').datetimebox('getValue');
					return $(this).form('validate');
				},
				success : function(data) {
					var datajson = $.parseJSON(data);
					if (datajson['mesg'] == 'succ') {
						//解除按钮不可点击状态
						$('#gradeSave').linkbutton('enable');
						$('#gradeConfig').linkbutton('enable');
						$('#gradeCancle').linkbutton('enable');
						if (succCallbacktype == 2) {
							$('#gradeListTable').datagrid('reload');
							// 进入配置课程步骤
							var url = contextpath+ "/grade/toConfigureGradeCourse?gradeId="+ datajson['gId'];
							parent.addTab_update('配置培训班', url, 'icon-save');
							$('#dlg_grade').dialog('close');
						} else {
							$('#dlg_grade').dialog('close');
							$('#gradeListTable').datagrid('reload');
						}
					}else if(datajson['mesg']=='sameGradeName'){
						//解除按钮不可点击状态
						$('#gradeSave').linkbutton('enable');
						$('#gradeConfig').linkbutton('enable');
						$('#gradeCancle').linkbutton('enable');
						$.messager.alert('友情提示', '班级名不能重复!', 'info');
					}
				},
				error : function() {
					alert('error');
				}
			});
}

//验证方法
$.extend($.fn.validatebox.defaults.rules, {
	//验证班级名是否重复
    checkGradeName:{
    	validator : function(value, param) {
    		var gradeId=$("#selGradeId").val();
    		value= encodeURI(value);
    		var flag = false;
    		$.ajax({
    			async: false,
    			url: contextpath+"/grade/checkGradeName",
    			type: "post", 
    			data: {"gradeName":value,"gradeId":gradeId},
    			success: function(data){
    				if(data == "succ"){
    					flag = false;
    				}else{
    					flag = true;
    				}
    			},error:function(){
    				alert('error');
    			}
    			
    		});
    		return flag;
        },  
        message : "班级名称不能重复！" 
    }
});

//图片设置
function pathChange() {
	var file = $(this).filebox('textbox').next();
	alert(file[0]);
	var docObj = document.getElementById(this.id);
	var str;
	if(this.id="gradeImgfile"){
		str="gipath";
	}else if(this.id="certificateImgfile"){
		str="ciPath";
	}else if(this.id="eleSealfile"){
		str="ePath";
	}
	var imgObjPreview = document.getElementById(str);
	if (docObj.files && docObj.files[0]) {
		// 火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '71px';
		imgObjPreview.style.height = '72px';
		// imgObjPreview.src = docObj.files[0].getAsDataURL();
		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		alert(imgSrc);
		var localImagId = document.getElementById(str);
		alert(localImagId);
		// 必须设置初始大小
		localImagId.style.width = "71px";
		localImagId.style.height = "72px";
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			alert("您上传的图片格式不正确，请重新选择！");
			var fileObj = document.getElementById("file");
			fileObj.outerHTML = fileObj.outerHTML;
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}
// ##############################################子页面操作end##############################################
// 添加面板——样式
$('#state').combo({
	panelHeight : 50,
});
function setRowStyle(index, row) {
	if (row.status == 0) {
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}