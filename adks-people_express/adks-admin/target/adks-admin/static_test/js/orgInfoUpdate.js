
$(function() {
	/*$('#orgForm_update').form({
		url:contextpath+'/org/updateOrgInfo',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				$.messager.alert("提示","信息保存成功!", "info");  
			}
		}
	});*/
	
	// 网校ID，后续需要动态的获取当前用户所在网校的ID！
	$('#dlg_org_update').dialog('open');
	initOrgInfo(0);
})

// 加载显示网校信息
function initOrgInfo(orgId){
	//给表单赋值
    //$('#orgForm_update').form('load','/org/loadEditOrgFormData?orgId='+orgId);
	var logo_path = $('#logo_path').val();
	if(logo_path != ""){
		$('#img_src_logo').attr('src', $('#imgServer').val()+logo_path);
	}
    
}

// 点击保存网校基本信息
function submitForm(){
	//$('#orgForm_update').attr('action',contextpath+'/org/updateOrgInfo');
	//$('#orgForm_update').submit();
	$('#orgForm_update').form({
		url:contextpath+'/org/updateOrgInfo',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
			if(data == 'succ'){
				$.messager.alert("提示","信息保存成功!", "info");  
			}
		}
	});
	$('#orgForm_update').submit();
}

// 关闭窗口
function closePanel(){
	//$('#tabs').tabs('close',"网校基本信息");
	parent.$('#tabs').tabs('close', '网校基本信息');
}

// 点击上传图片
function click_upload_img(){
	$("#file_input").click();
}

// 上传图片
function upload_file(){
	//alert('开始上传图片！');
	$('#orgForm_update').form('submit',{
		url:contextpath+'/org/uploadOrgLogo',
		success:function(data){
			//alert(data);
			if(data != ''){
				$('#img_src_logo').attr('src', $('#imgServer').val()+data);
				$('#logo_path').val(data);
			}
		}
	});
}


