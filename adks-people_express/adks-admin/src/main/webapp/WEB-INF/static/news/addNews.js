$(function() {
	$('#newsForm').form({
		url : contextpath + '/news/saveNews',
		onSubmit : function(param) {
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				changeTab();
			}
		}
	});
	var newsId = $("#newsId").val();
	if (newsId != null && newsId != 'undefined' && newsId != '') {
		var ossPath = $("#imgServer").val();
		var headPhoto = $("#newsFocusPic").val();
		if (headPhoto != null && headPhoto != 'undefined' && headPhoto != '') {
			headPhoto = ossPath + headPhoto;
		} else {
			// 默认图片
		}
		$("#hpath").attr("src", headPhoto);
		var newsType = $("input[name='newsType']").val();
		if (newsType == '2') {
			changeNewsType(2);
		} else {
			changeNewsType(1);
		}
	} else {
		var cp="../static/admin/images/qsimage.jpg";
		$("#hpath").attr("src", cp);
		$("input[name='newsType']").val(1);
		changeNewsType(1);
	}
	// src="${news.newsFocusPic}";
})
function changeTab() {
	parent.$('#tabs').tabs('close', '添加新闻');
	parent.$('#tabs').tabs('close', '编辑新闻');
}
// 课程添加保存
function addNewsOk() {
	var newsTitle = $("#newsTitle").textbox("getValue");
	if (newsTitle == null || newsTitle == '') {
		$.messager.alert('提示', '新闻标题不能为空', 'warning');
		return;
	} else if (newsTitle.length >= 64) {
		$.messager.alert('提示', '新闻标题的长度不能超过64个字符', 'warning');
		return;
	}
	getHtml();
	$('#newsForm').submit();
}
function getHtml() {
	var html = UE.getEditor('container').getContent();
	document.getElementById("newsContent").value = html;
}
// 课程添加取消
function addNewsCancle() {
	parent.$('#tabs').tabs('close', '添加新闻');
	parent.$('#tabs').tabs('close', '编辑新闻');
}

function changeNewsType(newsType) {
	if (newsType == 1) {
		$("#lianjie").hide();
		$("#neirong").show();
	} else {
		$("#lianjie").show();
		$("#neirong").hide();
	}
}

// 上传新闻焦点图图片
function showImg() {
	var imgPath = $('#newsFocusPicFile').val();
	$('#newsFocusPic').attr('src', imgPath);
}
function headpicPathChange() {
	var docObj = document.getElementById("myFile");
	var imgObjPreview = document.getElementById("hpath");
	if (docObj.files && docObj.files[0]) {
		// 火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '201px';
		imgObjPreview.style.height = '121px';
		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("localImag");
		// 必须设置初始大小
		localImagId.style.width = "201px";
		localImagId.style.height = "121px";
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			alert("您上传的图片格式不正确，请重新选择!");
			if (document.getElementById("myFile").outerHTML) {
				document.getElementById("myFile").outerHTML = document
						.getElementById("myFile").outerHTML;
			} else {
				document.getElementById("myFile").txt = "";
			}
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}

// 提交保存新闻信息
function submitAdd_news() {
	$("#newsContent").val(ue.getContent());
	$("#content").val(ue.getContent());
	$('#newsForm').form('submit', {
		url : contextpath + '/news/saveNews',
		contentType : "application/json;charset=UTF-8",
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			var url = contextpath+"/news/toNewsList"
			parent.addTab_update('新闻管理', url, 'icon-save');
			cancleAdd_news();
			if (data == "{mesg=sameName}") {
				$.messager.alert("提示", "新闻标题不能重复！", "info");
			} else {
				var url = contextpath + "/news/toNewsList"
				parent.addTab_update('新闻管理', url, 'icon-save');
				cancleAdd_news();
			}
		}
	});
}

// 点击取消添加、编辑新闻
function cancleAdd_news() {
	parent.$('#tabs').tabs('close', '编辑新闻');
	parent.$('#tabs').tabs('close', '添加新闻');
}