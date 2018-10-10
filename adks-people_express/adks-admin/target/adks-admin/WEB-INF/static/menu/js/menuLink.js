
$(function() {
	$('#menuLinkForm').form({
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

// 点击查询按钮
function doSearch() {
	var s_menuLink_name=encodeURI($('#s_menuLink_name').val());
	$("#menuLinkList_table").datagrid('load', {
		s_menuLink_name : s_menuLink_name
	});
}

//编辑枚举值
function edit_menuLink(){
    var selectRows = $('#menuLinkList_table').datagrid("getSelections");  
    if (selectRows == null || selectRows.length < 1 || selectRows.length > 1) {  
        $.messager.alert("提示", "请您选中一条需要编辑的信息！","info");  
        return;  
    }
    $('#dlg').dialog({title: "编辑网校"});
    $('#dlg').dialog('open');
    $('#menuLinkForm').form('clear');
	//给表单赋值
	$('#menuLinkForm').form('load',selectRows[0]);
	var menuName=$("#menuNameOne").val();
	var menuId=$("#menuIdOne").val();
	$("#menuId").val(menuId);
	$("#menuName").val(menuName);
}


// 点击取消 添加或编辑信息
function close_div(){
	$('#dlg').dialog('close');
}

//点击添加
function addMenuLink(){
	$('#dlg').dialog({title: "添加菜单链接"});
	$('#dlg').dialog('open');
	$('#menuLinkForm').form('clear');
	var menuName=$("#menuNameOne").val();
	var menuId=$("#menuIdOne").val();
	$("#menuId").val(menuId);
	$("#menuName").val(menuName);
}

// 点击保存 添加或编辑信息
function submitForm(){
	//ajax form提交
	var mLinkDisplay=$("#mLinkDisplay").val();
	if(mLinkDisplay != null && mLinkDisplay != '' && trimStr(mLinkDisplay) != ''){
		if(mLinkDisplay.length>64){
			 $.messager.alert("提示", "菜单链接的描述不可以超过56个字符","info");
			return ;
		}
	}
	$('#menuLinkForm').submit();
}

// 点击删除（flag: -1表示删除，1表示开通，2表示禁用）
function del_menuLink(flag) {  
    //把你选中的 数据查询出来。  
    var selectRows = $('#menuLinkList_table').datagrid("getSelections");
    var t_info = "删除";
    var t_url= contextpath+"/menuLink/delMenuLink";
    if (selectRows.length < 1) {  
        $.messager.alert("提示", "请选中要"+t_info+"的数据信息！","info");  
        return;  
    }  
 
    $.messager.confirm("确认", "您确定要"+t_info+"选中的信息吗？", function (r) {  
        if (r) {  
            var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].menuLinkId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1);  
            $.post(t_url, { ids: strIds }, function (data) { 
                if (data == "succ") {  
                    //刷新表格，去掉选中状态的 那些行。  
                    $('#menuLinkList_table').datagrid("reload");  
                    $('#menuLinkList_table').datagrid("clearSelections");  
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


