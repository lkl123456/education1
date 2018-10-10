
// 查询按钮点击
function doSearch() {
	$("#gradeListTable").datagrid('load', {
		gradeName : $('#gradeName').val()
	});
}
//格式化列数据
function formatStatus(value,row,index){
	if(value == 1){
		return '发布';
	}else if(value == 2){
		return '结束';
	}else{
		return '<span style="color:red" >'+'草稿'+'</span>';
	}
}

function addGrade(){
	$('#dlg_grade').dialog('open').dialog('setTitle','新增培训班');
	$('#gradeForm').form('clear');
	$('#img_coverpath').attr('src','');
}
function editGrade(){
	var row = $('#gradeListTable').datagrid('getSelections');
	if(row.length != 1){
		$.messager.alert('提示','请选中一条记录编辑','warning');
		return ;
	}
	$('#dlg_grade').dialog('open').dialog('setTitle','编辑培训班');
	$('#gradeForm').form('clear');
	//给表单赋值
//	$('#gradeForm').form('load','/grade/loadEditGradeFormData?gradeId='+row[0].id);
	$('#gradeForm').form('load',row[0]);
	
	var paths = $('#cover_path').val();
	var imgServer = $('#imgServer').val();
	$('#img_coverpath').attr('src',imgServer+paths);
	
}
//上传班级封面图
function uploadGradeCover(){
	var imgServer = $('#imgServer').val();
	$('#gradeForm').form('submit',{
		url:contextpath+'/grade/updateGradeCover',
		onSubmit: function(){
			return true;
		},
		success:function(data){
			$('#cover_path').val(data);
			$('#img_coverpath').attr('src',imgServer+data);
		}
	});
}


//配置培训班
function configerGrade(){
	var row = $('#gradeListTable').datagrid('getSelections');
	if(row.length != 1){
		$.messager.alert('提示','请选中一个培训班配置','warning');
		return ;
	}
	var url = contextpath+"/grade/toConfigureGradeCourse?gradeId="+row[0].id;
	parent.addTab_update('配置培训班',url,'icon-save');
}
function deleteGrade(){
	alert('delete');
	
}
//提交表单
function submitAddGrade(tips){
	var succCallbacktype = tips; //标示success之后的操作，1,0,2,——》2需要走下一步配置课程
	if(tips == 2){
		tips = 1;
	}
	$('#status').val(tips);
	$('#gradeForm').form('submit',{
		url:contextpath+'/grade/saveGrade',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(data){
			var datajson = $.parseJSON(data);
			if(datajson['mesg'] == 'succ'){
				if(succCallbacktype == 2){
					//进入配置课程步骤
					//alert('下面开始配置课程');
					var url = contextpath+"/grade/toConfigureGradeCourse?gradeId="+datajson['gId'];
					parent.addTab_update('配置培训班',url,'icon-save');
					$('#dlg_grade').dialog('close');
					$('#gradeListTable').datagrid('reload');
				}else{
					$('#dlg_grade').dialog('close');
					$('#gradeListTable').datagrid('reload');
				}
			}
		},error:function(){
			alert('error');
		}
	});
}

function setRowStyle(index,row){
	if(row.status == 0){
		return 'background-color:#eee;color:blue;font-weight:bold;';
	}
}