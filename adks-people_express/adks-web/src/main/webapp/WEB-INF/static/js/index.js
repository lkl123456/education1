$(document).ready(function() {
	$('.bjcl dt ol li').mouseover(function() {
		var $km = $(this).index();
		$('.bjcl dt ol li').removeClass('bjcl_li');
		$(this).addClass('bjcl_li');
		$('.bjcl dd').hide();
		$('.bjcl dd').eq($km).show();
		$('.bjcl dt a').hide();
		$('.bjcl dt a').eq($km).show();
	})
	$('.gaos_l_r dt ol li').mouseover(function() {
		var $kn = $(this).index();
		$('.gaos_l_r dt ol li').removeClass('bjcl_li');
		$(this).addClass('bjcl_li');
		$('.gaos_l_r dd').hide();
		$('.gaos_l_r dd').eq($kn).show();
		$('.gaos_l_r dt a').hide();
		$('.gaos_l_r dt a').eq($kn).show();
	})
	$('.main_left_1 ol li').mouseover(function() {
		var $kcn = $(this).index();
		$('.main_left_1 ol li').removeClass('shnax');
		$(this).addClass('shnax');
		$('.main_left_1 dd').hide();
		$('.main_left_1 dd').eq($kcn).show();
	})
	$('.ce_bian li').hover(function() {
		$(this).stop().animate({
			'left' : '-80px'
		}, 450);
	}, function() {
		$(this).stop().animate({
			'left' : '0px'
		}, 450);
	})
	for (var $v = 0; $v < 11; $v++) {
		var $hei = $('.tixi_l dd ul li').eq($v).height();
		$('.tixi_l dd ul li h2').eq($v).css({
			'height' : $hei,
			'line-height' : $hei + 'px'
		})
	}
	$('.tixi_l dd ul li div p:nth-child(1)').mouseover(function() {
		$('.tixi_l dd ul li div .wke_n .ne').css({
			'left' : '1px'
		});
	})
	$('.tixi_l dd ul li div p:nth-child(2)').mouseover(function() {
		$('.tixi_l dd ul li div .wke_n .ne').css({
			'left' : '82px'
		});
	})
	$('.tixi_l dd ul li div p:nth-child(3)').mouseover(function() {
		$('.tixi_l dd ul li div .wke_n .ne').css({
			'left' : '162px'
		});
	})
	$('.tixi_l dd ul li div p:nth-child(4)').mouseover(function() {
		$('.tixi_l dd ul li div .wke_n .ne').css({
			'left' : '242px'
		});
	})
	$('.tixi_l dd ul li div p:nth-child(5)').mouseover(function() {
		$('.tixi_l dd ul li div .wke_n .ne').css({
			'left' : '322px'
		});
	})
})

function loginOut() {
	var time = new Date().getTime();
	window.location.href = contextPath + "/user/loginOut/" + time + ".shtml";
}

// 新闻公告列表
function getNewsList(newsSortId, orgId) {
	$("#mainIndex").html(
			'<p style="text-align:center; width:1000px; margin:0 auto;"><img src="'
					+ contextPath + '/static/images/loading.gif" /></p>');
	$("#newsSortId").val(newsSortId);
	$("#orgId").val(orgId);
	// $("#newsSortType").val(newsSortType);
	// alert(newsSortId+","+orgId+","+newsSortType);
	$.ajax({
		async : true,
		url : contextPath + "/cms/newsList.do?newsSortId=" + newsSortId
				+ "&orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}

	});
}

// 新闻公告详细
// function getNewsDetail(newsId,newsSortId,orgId,newsSortType){
function getNewsDetail(newsHtmlAdress, newsId, newsSortId, orgId) {
	// url:contextPath+"/cms/newsDetail.do?newsId="+newsId+"&newsSortId="+newsSortId+"&orgId="+orgId+"&newsSortType="+newsSortType,
	// alert(contextPath+"/cms/newsDetail.do?newsHtmlAdress="+newsHtmlAdress+"&newsId="+newsId+"&newsSortId="+newsSortId+"&orgId="+orgId+"&newsSortType="+newsSortType);
	$.ajax({
		async : true,
		url : contextPath + "/cms/newsDetail.do?newsHtmlAdress="
				+ newsHtmlAdress + "&newsId=" + newsId + "&newsSortId="
				+ newsSortId + "&orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}

	});
}

function toFeedback(orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/user/toFeedback.do?orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

function toOnlineHelp(flag) {
	$.ajax({
		async : true,
		url : contextPath + "/index/toOnlineHelp.do?flag=" + flag,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

function toCenter(orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/index/toCenter/" + orgId + ".shtml",
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 进入课程超市
function toCourseList(orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/course/toCourseList.do?orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}
// 进入课程详情页
function toCourseInfo(courseId, orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/course/toCourseInfo.do?courseId=" + courseId
				+ "&orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}
// 进入讲师栏目列表页
function toAllAuthor(letter, org) {
	$.ajax({
		async : true,
		url : contextPath + "/author/toAllAuthor.do?letter=" + letter
				+ "&orgnaization=" + org,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}
// 进入讲师详情页
function toAuthorInfo(authorId, orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/author/toAuthorInfo.do?authorId=" + authorId
				+ "&orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 点击添加收藏
function addUserCollection(courseId) {
	$
			.ajax({
				async : true,
				url : contextPath + "/course/addUserCollection.do?courseId="
						+ courseId,
				type : "post",
				success : function(data) {
					alert(data);
				}
			});
}

// 进入专题
function toSubjectList() {
	$.ajax({
		async : true,
		url : contextPath + "/cms/subjectList.do",
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}
// 进入前台报名培训班list
function toRegisterGradeList(orgId) {
	$("#mainIndex").html(
			'<p style="text-align:center; width:1000px; margin:0 auto;"><img src="'
					+ contextPath + '/static/images/loading.gif" /></p>');
	$.ajax({
		async : true,
		url : contextPath + "/registGrade/registerGradeList/" + orgId
				+ ".shtml",
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}
// 进入前台报名培训班
function toRegisterGrade(gradeId) {
	window.open(contextPath + "/registGrade/registerGrade/" + gradeId
			+ ".shtml");
}
// 添加收藏
function addBookmark1111() {
	var title = "沈阳干部在线学习网";
	var url = window.location.href;
	if (window.sidebar) {
		window.sidebar.addPanel(title, url, "");
	} else {
		if (document.all) {
			window.external.AddFavorite(url, title);
		} else {
			if (window.opera && window.print) {
				return true;
			}
		}
	}
}
// 加入收藏 修改： @author:yzh @date:2015-05-18
function addBookmark() {
	sURL = window.location;
	var sTitle = document.title;
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch (e) {
			alert("加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}

// 设置首页
function setHome() {
	var url = window.location.href;
	if (document.all) {
		document.body.style.behavior = "url(#default#homepage)";
		document.body.setHomePage(url);
	} else {
		if (window.sidebar) {
			if (window.netscape) {
				try {
					netscape.security.PrivilegeManager
							.enablePrivilege("UniversalXPConnect");
				} catch (e) {
					alert("\u8be5\u64cd\u4f5c\u88ab\u6d4f\u89c8\u5668\u62d2\u7edd\uff0c\u5982\u679c\u60f3\u542f\u7528\u8be5\u529f\u80fd\uff0c\u8bf7\u5728\u5730\u5740\u680f\u5185\u8f93\u5165 about:config,\u7136\u540e\u5c06\u9879 signed.applets.codebase_principal_support \u503c\u8be5\u4e3atrue");
				}
			}
			if (window.confirm("\u4f60\u786e\u5b9a\u8981\u8bbe\u7f6e" + url
					+ "\u4e3a\u9996\u9875\u5417\uff1f") == 1) {
				var prefs = Components.classes["@mozilla.org/preferences-service;1"]
						.getService(Components.interfaces.nsIPrefBranch);
				prefs.setCharPref("browser.startup.homepage", url);
			}
		}
	}
}

function indexSearch(qt) {
	var searchKey = $("#searchKey").val();
	var searchType = "";
	var qyName = "";
	if (qt != "undefined") {
		searchType = qt;
		$("div[class='select_showbox']").val(qt);
		if (qt == 0) {
			qyName = "全部";
		} else if (qt == 1) {
			qyName = "课程";
		} else if (qt == 2) {
			qyName = "新闻";
		} else if (qt == 3) {
			qyName = "专题";
		} else if (qt == 4) {
			qyName = "讲师";
		}
		$("div[class='select_showbox']").text(qyName);
	} else
		searchType = $("div[class='select_showbox']").val();
	if (searchKey == '') {
		alert("请输入要搜索的关键字!");
		return false;
	}
	var path = $("#path").val();
	$.ajax({
		async : true,
		url : contextPath + "/search/query?q=" + encodeURI(searchKey) + "&qt="
				+ searchType,
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}

	});
	return false;
}

function toLinkUrl(linkUrl) {
	if (escape(linkUrl).indexOf("%u") != -1) {
		linkUrl = encodeURI(linkUrl);
		window.open(linkUrl);
	} else {
		window.open(linkUrl);
	}
}

// 进入下载
function toDownloadtList() {
	$.ajax({
		async : true,
		url : contextPath + "/index/downloadList.do",
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 进入排行
function toPhIndex(orgId) {
	$("#mainIndex").html(
			'<p style="text-align:center; width:1000px; margin:0 auto;"><img src="'
					+ contextPath + '/static/images/loading.gif" /></p>');
	$.ajax({
		async : true,
		url : contextPath + "/index/toPhIndex.do?orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 联系我们
function toContactUs(orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/index/toContactUs.do?orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 应急首页
function toEmergencyIndex() {
	$.ajax({
		async : true,
		url : contextPath + "/index/toEmergencyIndex.do",
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 进入班级详情
function toGradeCenterIndex(gradeId) {
	$.ajax({
		async : true,
		url : contextPath + "/grade/gradeMessage.do?gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 找回密码页面
function findPwd(orgId) {
	$.ajax({
		async : true,
		url : contextPath + "/index/toFindPwd.do?orgId=" + orgId,
		type : "post",
		success : function(data) {
			$("#mainIndex").html(data);
		}
	});
}

// 点击添加收藏
function isGetInfo(gradeId) {
	$.ajax({
		async : true,
		url : contextPath + "/registGrade/toGradeInfo.do?gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			if (data == "isGetInfo1") {
				checkGradeUserDetail(gradeId);
			} else if (data == "isGetInfo2") {
				completeUserData('1', gradeId);
			} else if (data == "isGetInfo3") {
				alert("未加入该班级!");
			}

		}
	});
}

function completeUserData(userId, gradeId) {
	if (userId == null || userId == '') {
		alert("请先登录...");
	} else {
		$.ajax({
			async : true,
			url : contextPath + "/user/checkData.do",
			type : "post",
			success : function(data) {
				// alert(data);
				if (data == 'succ') {
					toGradeCenterIndex(gradeId);
				} else if (data == 'nouser') {
					alert("请先登录...");
				} else if (data == 'error') {
					// 完善信息
					completeData(gradeId);
				}
			}
		});
	}
}
// 点击历史培训班个人学习情况
function checkGradeUserDetail(gradeId) {
	var url = contextPath + "/registGrade/checkGradeUserDetail.do?gradeId="
			+ gradeId;
	window.open(url);
}