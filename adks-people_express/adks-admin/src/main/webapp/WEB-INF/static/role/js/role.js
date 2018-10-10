
$(function() {
	$('#roleForm').form({
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				close_div();
				doSearch();
			}else if(data == 'snnameExists'){
				$.messager.alert("提示","唯一标识已存在，请重新输入!");
			}
		}
	});
	
	// 隐藏列（机构ID）
	//$('#zhijiList_table').datagrid('hideColumn','org_id');
})

function formatColumnData(value,row,index){
	if(value == 1){
		return '是';
	}else{
		return '否';
	}
}

// 点击查询按钮
function doSearch() {
	var roleName=encodeURI($('#s_role_name').val());
	//alert(roleName);
	$("#roleList_table").datagrid('load', {
		s_role_name : roleName
	});
}
// 点击添加机构信息
function add_role(){
	$('#dlg').dialog({title: "添加角色"});
	$('#dlg').dialog('open');
	$('#roleForm').form('clear');
}

// 点击编辑网校信息(加载显示相关信息)
function edit_role(){
    var selectRows = $('#roleList_table').datagrid("getSelections");  
    if (selectRows.length < 1 || selectRows.length > 1) {  
        $.messager.alert("提示", "请您选中一条需要编辑的信息！","info");  
        return;  
    }
    $('#dlg').dialog({title: "编辑角色"});
    $('#dlg').dialog('open');
    $('#roleForm').form('clear');
	//给表单赋值
	$('#roleForm').form('load',selectRows[0]);
}


// 点击取消 添加或编辑信息
function close_div(){
	$('#dlg').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm(){
	//ajax form提交
	var displayRef=$("#displayRef").val();
	if(displayRef!=null && displayRef!=''&& trimStr(displayRef) !='' && displayRef.length>=25){
		$.messager.alert("提示", "角色显示的名称长度过长","info");
		return ;
	}else if(trimStr(displayRef) ==''){
		$("#displayRef").textbox("setValue",'');
	}
	var roleDes=$("#roleDes").val();
	if(roleDes!=null && roleDes!=''&& trimStr(roleDes) !=''  && roleDes.length>=25){
		$.messager.alert("提示", "描述长度过长","info");
		return ;
	}else if(trimStr(roleDes) ==''){
		$("#roleDes").textbox("setValue",'');
	}
	$('#roleForm').submit();
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）
function del_role(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#roleList_table').datagrid("getSelections");
    var t_info = "删除";
    var t_url= contextpath+"/role/delRole";
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！","info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].roleId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            $.post(t_url, { ids: strIds }, function (data) { 
                if (data == "succ") {  
                    //刷新表格，去掉选中状态的 那些行。  
                    $('#roleList_table').datagrid("reload");  
                    $('#roleList_table').datagrid("clearSelections");  
                } else {  
                    $.messager.alert("操作失败~~", data);  
                }  
            });  
        }  
    });  
}

//自定义验证
/*$.extend($.fn.validatebox.defaults.rules, {    
    textLength: {    
        validator: function(value,param){
        	if(value == ''){
        		return true;
        	}
        	if(value!=null && value.trim()!=''){
        		 alert(value+"-"+value.length);
        		 return value.length <= param[0];  
        	}else{
        		return true;
        	}
        },    
        message: ''   
    }    
}); */
function trimStr(str){ //删除左右两端的空格　　
   return str.replace(/(^s*)|(s*$)/g, "");
}