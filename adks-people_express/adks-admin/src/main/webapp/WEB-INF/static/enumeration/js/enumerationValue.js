
$(function() {
	$('#enumerationForm').form({
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				//$.messager.alert("提示","信息保存成功!");  
				close_div();
				doSearch();
			}else if(data == 'snnameExists'){
				$.messager.alert("提示","唯一标识已存在，请重新输入!");
			}
		}
	});
	
})

function formatOper(val,row,index){ 
	var enName=$("#enName").val();
    return enName;
}

//添加枚举值
function addEnVal(){
	var enName=$("#enName").val();
	var enId=$("#enIdOne").val();
	$('#dlg').dialog({title: "添加枚举值"});
	$('#dlg').dialog('open');
	$('#enumerationForm').form('clear');
	$("#ss1").val(enName);
	$("#enId").val(enId);
}

// 点击查询按钮
function doSearch() {
	var enumerationName=encodeURI($('#s_en_name').val());
	var enumerationValName=encodeURI($('#s_enVal_name').val());
	$("#enumerationList_table").datagrid('load', {
		s_en_name : enumerationName,
		s_enVal_name:enumerationValName
	});
}

//编辑枚举值
function edit_en(){
    var selectRows = $('#enumerationList_table').datagrid("getSelections");  
    if (selectRows == null || selectRows.length < 1 || selectRows.length > 1) {  
        $.messager.alert("提示", "请您选中一条需要编辑的信息！","info");  
        return;  
    }
    $('#dlg').dialog({title: "编辑网校"});
    $('#dlg').dialog('open');
    $('#enumerationForm').form('clear');
	//给表单赋值
	$('#enumerationForm').form('load',selectRows[0]);
	var enName=$("#enName").val();
	$("#ss1").val(enName);
	var enId=$("#enIdOne").val();
	$("#enId").val(enId);
}


// 点击取消 添加或编辑信息
function close_div(){
	$('#dlg').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm(){
	//ajax form提交
	var valDesc=$("#valDesc").val();
	if(valDesc!=null && valDesc!='' && trimStr(valDesc)!=''){
		if(valDesc.length>256){
			$.messager.alert("提示", "枚举值描述的长度不可以超过256个字符","info");  
	        return;  
		}
	}
	$('#enumerationForm').submit();
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）
function del_en(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#enumerationList_table').datagrid("getSelections");
    var t_info = "删除";
    var t_url= contextpath+"/enumerationValue/delEnumerationValue";
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！","info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].enValueId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            $.post(t_url, { ids: strIds }, function (data) { 
                if (data == "succ") {  
                    //刷新表格，去掉选中状态的 那些行。  
                    $('#enumerationList_table').datagrid("reload");  
                    $('#enumerationList_table').datagrid("clearSelections");  
                } else {  
                    $.messager.alert("操作失败~~", data);  
                }  
            });  
        }  
    });  
}

function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}


