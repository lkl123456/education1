
$(function() {
	$('#adminForm').form({
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				//$.messager.alert("提示","信息保存成功!");  
				close_div();
				doSearch();
			}else if(data == 'nameExists'){
				$.messager.alert("提示","用户名已存在，请重新输入!");
			}
		}
	});
	
	// 隐藏列（机构ID）
	$('#adminList_table').datagrid('hideColumn','org_id');
	
})

// 初始化分校下拉列表
function init_orgList_sel(){
	$('#orgListSel').combobox({
	    url: contextpath+'/org/orgListSelJson',
	    valueField: 'id',
	    textField: 'name'            
	});
	$("#orgListSel").combobox('setValue', 0);
}

// 点击查询按钮
function doSearch() {
	if(typeof($('#s_user_orgSnname').val()) == "undefined"){
		$("#adminList_table").datagrid('load', {
			s_user_name : $('#s_user_name').val()
		});
	}else{
		$("#adminList_table").datagrid('load', {
			s_user_name : $('#s_user_name').val(),
			s_user_orgSnname : $('#s_user_orgSnname').combobox('getValue')
		});
	}
}

// 点击添加管理员信息
function add_admin(){
	$('#dlg').dialog({title: "添加管理员"});
	$('#dlg').dialog('open');
	$('#adminForm').form('clear');
	// 初始化分校下拉列表（如果分校管理员添加时，无此选择项）
	var isPlatform = $("#isPlatform").val();
	if(isPlatform == 'gclc'){
		$("#tr_org_list").show();
		init_orgList_sel();
		$('#admin_role_id').combobox('setValue', 2);
		$('#admin_role_id').combobox('setText', '网校管理员');
	}else{
		$("#tr_org_list").hide();
		$('#admin_role_id').combobox('setValue', 3);
		$('#admin_role_id').combobox('setText', '培训管理员');
	}
	// 初始化状态下拉列表
	$("#status_sel").combobox('setValue', 0);
}

// 点击添加管理员信息(加载显示相关信息)
function edit_admin(){
    var selectRows = $('#adminList_table').datagrid("getSelections");  
    if (selectRows.length < 1 || selectRows.length > 1) {  
        $.messager.alert("提示", "请您选中一条需要编辑的信息！","info");  
        return;  
    }
    $('#dlg').dialog({title: "编辑管理员"});
    $('#dlg').dialog('open');
    $("#t_id").val(selectRows[0].id);
    // 初始化分校下拉列表（如果分校管理员添加时，无此选择项）
	var isPlatform = $("#isPlatform").val();
	if(isPlatform == 'gclc'){
		$("#tr_org_list").show();
		init_orgList_sel();
	    var org_id = selectRows[0].org_id;
	    if(org_id != null && org_id != ''){
	    	$("#orgListSel").combobox('setValue', org_id);
	    }
	}else{
		$("#tr_org_list").hide();
	}
    
    //$("#t_user_name").val(selectRows[0].user_name);
    $("#t_user_name").textbox('setValue', selectRows[0].user_name);
    $("#t_mobile").textbox('setValue', selectRows[0].mobile);
    $("#t_email").textbox('setValue', selectRows[0].email);
    var status = selectRows[0].status;
    if(status != null && status != ''){
    	$("#status_sel").combobox('setValue', status);
    }
    // 隐藏角色选择项
    $('#tr_admin_role').hide();
}


// 点击取消 添加或编辑信息
function close_div(){
	$('#dlg').dialog('close');
}

// 点击保存 添加或编辑信息
function submitForm(){
	//alert('保存管理员信息');
	//ajax form提交
	$('#adminForm').submit();
}

// 点击删除（flag: -1表示删除，1表示激活，2表示冻结）
function del_admin(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#adminList_table').datagrid("getSelections");  
    var t_info = "删除";
    var t_url= contextpath+"/admin/delAdmin";
    if(flag == 1){
    	t_info = "激活";
    	t_url = contextpath+"/admin/updateAdmin_jh";
    }else if(flag == 2){
    	t_info = "冻结";
    	t_url = contextpath+"/admin/updateAdmin_dj";
    }
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！","info");  
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
                    $('#adminList_table').datagrid("reload");  
                    $('#adminList_table').datagrid("clearSelections");  
                } else {  
                    $.messager.alert("操作失败~~", data);  
                }  
            });  
        }  
    });  
}




