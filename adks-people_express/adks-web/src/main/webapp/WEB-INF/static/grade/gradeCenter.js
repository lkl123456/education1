//班级信息
function gradeMessage(gradeId) {
	$.ajax({
		async : false,
		url : contextPath + "/grade/gradeMessage.do?gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			$("#gradeMessageDiv").html(data);
		}
	});
}

// 班级课程
function getGradeCourseList(flag) {
	var gradeId = $("#gradeId").val();
	var xsFlag = $("#xsFlag").val();
	if (xsFlag == '') {
		xsFlag = 'tw';
	}
	if (flag == 2) {
		$("#bx").addClass("right_min_1");
	} else if (flag == 3) {
		$("#xx").addClass("right_min_1");
	} else if (flag == 4) {
		$("#wks").addClass("right_min_1");
	} else if (flag == 5) {
		$("#xxz").addClass("right_min_1");
	} else if (flag == 6) {
		$("#yxw").addClass("right_min_1");
	} else {
		$("#qb").addClass("right_min_1");
	}
	// alert(flag);
	$.ajax({
		async : true,
		url : contextPath + "/gradeCourse/gradeCourseList.do?gradeId=" + gradeId
				+ "&flag=" + flag + "&xsFlag=" + xsFlag,
		type : "post",
		success : function(data) {
			// alert(data)
			$("#gradeDiv").html(data);
		}
	});
}

function UpdateGradeUser(courseId, userId, gradeId) {
	// alert(courseId+","+userId+","+gradeId);
	$.ajax({
		async : true,
		url : contextPath + "/gradeCourse/updateGradeUser.do?courseId=" + courseId
				+ "&userId=" + userId + "&gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			window.location.reload();
		}
	});
}

/**
 * 班级论文 start
 */
// 班级论文
function gradeThesisList() {
	var gradeId = $("#gradeId").val();
	$.ajax({
		async : true,
		url : contextPath + "/grade/gradeThesisList.do?gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			$("#gradeDiv").html(data);
		}

	});
}

// 班级论文列表,带分页页码
function gradeThesisList_page() {
	var currentPageFlag = $("#currentPageFlag").val();
	var gradeId = $("#gradeId").val();
	$.ajax({
		async : true,
		url : contextPath + "/grade/gradeThesisList.do?gradeId=" + gradeId
				+ "&currentPageFlag=" + currentPageFlag,
		type : "post",
		success : function(data) {
			$("#gradeDiv").html(data);
		}

	});
}
// 班级论文内容
function gradeThesisInfo(gradeWorkId, gradeId, onlySee) { // onlySee标示只能查看论文内容，老师已批改，不能再提交了，屏蔽提交按钮
	var currentPageFlag = $("#currentPageFlag").val();
	$.ajax({
		async : true,
		url : contextPath + "/grade/gradeThesisInfo.do?gradeWorkId="
				+ gradeWorkId + "&gradeId=" + gradeId + "&onlySee=" + onlySee
				+ "&currentPageFlag=" + currentPageFlag,
		type : "post",
		success : function(data) {
			$("#gradeDiv").html(data);
		}
	});
}

// 保存、修改学员论文信息
function gradeThesisSave() {
	// alert(minSize+"*******"+maxSize);
	var gradeId = $("#gradeId").val();
	var leastSize = $("#leastSize").val();
	var maxSize = $("#maxSize").val();
	var fv = $("#fullVersion").val();
	if (fv != null && (fv == 6 || fv == 7)) {
		var contentKd = $("#contentKd").val();
		if (contentKd == '' || $.trim(contentKd) == '') {
			alert("论文内容不能为空！");
			return;
		}
	} else {
		// var oEditor = FCKeditorAPI.GetInstance("st_content");
		// var getcontent = oEditor.EditorDocument.body.innerText;
		var contentKd = $("#contentKd").val();
		if (contentKd == '' || $.trim(contentKd) == '') {
			alert("论文内容不能为空！");
			return;
		}
		// 字数控制
		// var oDOM = oEditor.EditorDocument;
		var iLength = contentKd.length;
		/*
		 * if(document.all){ iLength = oDOM.body.innerText.length; }else{ var r =
		 * oDOM.createRange(); r.selectNodeContents(oDOM.body); iLength =
		 * r.toString().length; }
		 */
		if (maxSize != null && (parseInt(iLength) > parseInt(maxSize))) {
			alert("论文字数不能超过" + maxSize);
			return;
		}
		if (leastSize != null && (parseInt(iLength) < parseInt(leastSize))) {
			alert("论文字数至少为" + leastSize);
			return;
		}
	}

	var nowDate = $("#nowDate").val();
	var endDate = $("#endDate").val();
	var startDate = $("#startDate").val();
	if (endDate < nowDate) {
		alert("论文已过期！");
		return false;
	}
	if (nowDate < startDate) {
		alert("未到开始时间，无法提交！");
		return false;
	}

	$("#sbbutton").attr("disabled", true);
	$("#gradeThesisFrom").ajaxSubmit({
		async : true,
		url : contextPath + "/grade/gradeThesisSave.do",
		iframe : true,
		type : "post",
		data : $("#gradeThesisFrom").serialize(),
		success : function(data) {
			if (data == 'succ') {
				alert("您的论文已经成功提交！");
				$("#goGradeThesisList").click();
				gradeThesisList_page();// 返回论文列表页面
			} else {
				$("#sbbutton").attr("disabled", false);
				alert("您的论文提交失败！");
				gradeThesisList_page();// 返回论文列表页面
			}
		}
	});
}
/**
 * 班级论文 end
 */

/**
 * 班级考试 start
 */
// 点击考试
function gradeExamList() {
	var gradeId = $("#gradeId").val();
	$.ajax({
		async : true,
		url : contextPath + "/gradeExam/gradeExamList.do?gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			$("#gradeDiv").html(data);
			/*if (examType == 92) {
				$("#mnks").addClass("right_min_1");
			}*/
			$("#jyks").addClass("right_min_1");
		}
	});
}
// 进入考试前弹出考试信息
function examInfoDivShow(id, name, score, passScore, testTime) {
	$("#div_id").val(id);
	$("#div_name").html(name);
	$("#div_score").html(score);
	$("#div_passScore").html(passScore);
	$("#div_testTime").html(testTime);
	$("#exam_info").show();
}
// 关闭弹出的考试信息
function examInfoDivHide() {
	$("#exam_info").hide();
}
// 进入班级考试
function enterGradeExam() {
	var examId = $("#div_id").val();
	var gradeId = $("#gradeId").val();
	$
			.ajax({
				async : true,
				url : contextPath + "/gradeExam/gradeExamInfo.do?examId=" + examId
						+ "&gradeId=" + gradeId,
				type : "post",
				success : function(data) {
					// {"paperId":2,"gradeId":15,"userId":13561,"paperHtmlUrl":"/NEWSHTML/306CED25EA6C4E6E9A419AFA8DBA1FE4.html","examId":1,"msg":"succ","examDate":60}
					var datajson = $.parseJSON(data);
					if (datajson['msg'] == 'succ') {
						window
								.open(
										datajson['paperHtmlUrl'] + "?userId="
												+ datajson['userId']
												+ "&examId="
												+ datajson['examId']
												+ "&paperId="
												+ datajson['paperId']
												+ "&gradeId="
												+ datajson['gradeId']
												+ "&examDate="
												+ datajson['examDate'],
										'upkjimg',
										'height=660, width=1100, top=80, left=180, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
						examInfoDivHide();
						// var url = contextpath
						// + "/paper/toConfigurePaper?paperId="
						// + datajson['paperId'];
					}
				}
			});
}
// 进入班级模拟练习
function enterGradeExamTest(examId) {
	var gradeId = $("#gradeId").val();
	window
			.open(
					contextPath + "/gradeExam/gradeExamInfo.do?examId=" + examId
							+ "&gradeId=" + gradeId,
					'upkjimg',
					'height=660, width=1100, top=80, left=180, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
}
// 显示班级考试成绩层信息
function gradeExamScoreShow(examId) {
	var gradeId = $("#gradeId").val();
	$.ajax({
		async : true,
		url : contextPath + "/gradeExam/gradeExamScoreList.do?examId=" + examId
				+ "&gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			$("#exam_score_div").html(data);
			$("#exam_score_div").show();
		}
	});
}
// 再班级考试成绩层信息中点击再考一次
function againGradeExam(examId) {
	var flag = $("#enter_exam_flag_" + examId).val();
	// flag 为 ‘true’ 则能再次参加考试
	if (flag == "false") {
		alert('您已用完允许参加考试次数，现已不能再参加考试！');
		return;
	} else if (flag == "timeOut") {
		alert('考试开放时间已过，现已不能再参加考试！');
		return;
	}
	var gradeId = $("#gradeId").val();
	/*
	 * window .open( contextPath + "/gradeExam/gradeExamInfo.do?examId=" + examId +
	 * "&gradeId=" + gradeId, 'upkjimg', 'height=660, width=1100, top=80,
	 * left=180, toolbar=no, menubar=no, scrollbars=yes,
	 * resizable=yes,location=no, status=no');
	 */
	$
			.ajax({
				async : true,
				url : contextPath + "/gradeExam/gradeExamInfo.do?examId=" + examId
						+ "&gradeId=" + gradeId,
				type : "post",
				success : function(data) {
					var datajson = $.parseJSON(data);
					if (datajson['msg'] == 'succ') {
						window
								.open(
										datajson['paperHtmlUrl'] + "?userId="
												+ datajson['userId']
												+ "&examId="
												+ datajson['examId']
												+ "&paperId="
												+ datajson['paperId']
												+ "&gradeId="
												+ datajson['gradeId']
												+ "&examDate="
												+ datajson['examDate'],
										'upkjimg',
										'height=660, width=1100, top=80, left=180, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
						gradeExamScoreHide();
					}
				}
			});

}
// 关闭班级考试成绩层信息
function gradeExamScoreHide() {
	$("#exam_score_div").hide();
}
// 查看模拟练习成绩
function enterGradeExamTestScore(examId) {
	var gradeId = $("#gradeId").val();
	window
			.open(
					contextPath + "/grade/gradeExamScoreInfo.do?examId="
							+ examId + "&gradeId=" + gradeId,
					'upkjimg',
					'height=660, width=1100, top=80, left=180, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
}
/**
 * 班级考试 end
 */

/**
 * 点击我要结业
 */
function graduate() {
	var gradeId = $("#gradeId").val();
	$.ajax({
		async : true,
		url : contextPath + "/grade/graduate.do?gradeId=" + gradeId,
		type : "post",
		success : function(data) {
			$("#gradeDiv").html(data);
		}
	});
}
// 点击 结业情况查询
function showInfo() {
	$("#jieye_11").hide();
	$("#jieye_22").show();
}