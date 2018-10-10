var contextPath = "http://localhost:8080/adks-web";
// 考试提醒时间
var txTime = '05';
// 是否已提醒
var txFlag = true;

// 延长SESSION 标识
var csTime = 0;

// 页面加载时执行
$(function() {

	$("#userId").val(getUrlParam('userId'));
	$("#paperId").val(getUrlParam('paperId'));
	$("#examId").val(getUrlParam('examId'));
	$("#gradeId").val(getUrlParam('gradeId'));
	$("#examDate").html(getUrlParam('examDate'));
	$("#testTime").val(getUrlParam('examDate'));

	// 初始化时间
	initTime();
	// 开始计时
	startJs();
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

// 初始化考试时间
function initTime() {
	var testTime = $("#testTime").val();
	var hh = '00';
	var mm = '45';
	if (testTime <= 120 && testTime >= 60) {
		var th = parseInt(testTime / 60);
		hh = '0' + th;
		if (th == 2) {
			mm = '00';
		} else {
			var tm = testTime - 60;
			if (tm < 10) {
				mm = '0' + tm;
			} else {
				mm = '' + tm;
			}
		}
	} else {
		if (testTime < 10) {
			mm = '0' + testTime;
		} else {
			mm = '' + testTime;
		}
	}
	$("#hh").html(hh);
	$("#mm").html(mm);
}

// 考试开始计时，时间结束后将自动提交试卷
function startJs() {
	var hh = $("#hh").html();
	var mm = $("#mm").html();
	var ss = $("#ss").html();

	// 提醒
	if (txFlag && hh == '00' && mm == txTime && ss == '01') {
		showExamTxDiv(txTime);
	}

	if (hh.substring(0, 1) == "0") {
		hh = hh.substring(1, 2);
	}
	if (mm.substring(0, 1) == "0") {
		mm = mm.substring(1, 2);
	}
	if (ss.substring(0, 1) == "0") {
		ss = ss.substring(1, 2);
	}
	if ((parseInt(ss) - 1) <= 0) {
		if ((parseInt(mm) - 1) < 0 && (parseInt(hh) - 1) < 0) {
			setTimeout("gradeExamInfoSave('zd')", 3000);
		} else if ((parseInt(mm) - 1) < 0 && (parseInt(hh) - 1) >= 0) {
			$("#hh").html("0" + (parseInt(hh) - 1));
			$("#mm").html(59);
			$("#ss").html(59);
			setTimeout("startJs()", 1500);
		} else {
			var tm;
			if ((parseInt(mm) - 1) < 10) {
				tm = "0" + (parseInt(mm) - 1);
			} else {
				tm = parseInt(mm) - 1;
			}
			$("#mm").html(tm);
			$("#ss").html(59);
			setTimeout("startJs()", 1500);
		}
	} else {
		var ts = "";
		if ((parseInt(ss) - 1) < 10) {
			ts = "0" + (parseInt(ss) - 1);
		} else {
			ts = parseInt(ss) - 1;
		}
		$("#ss").html(ts);
		setTimeout("startJs()", 1000);
	}

}
// 单选、判断选择操作
function danxOnClick(qnum) {
	$("#click_li_" + qnum).addClass("li_m");
}

// 多选选择操作
function duoxOnClick(name, qnum) {
	var dxs = document.getElementsByName(name);
	var flag = 'false';
	for (var i = 0; i < dxs.length; i++) {
		if (dxs[i].checked) {
			flag = 'true';
			break;
		}
	}
	if (flag == 'true') {
		$("#click_li_" + qnum).addClass("li_m");
	} else {
		$("#click_li_" + qnum).removeClass("li_m");
	}
}
// 填空答题操作
function tkOnCheck(value, qnum) {
	if ($.trim(value) != '') {
		$("#click_li_" + qnum).addClass("li_m");
	} else {
		$("#click_li_" + qnum).removeClass("li_m");
	}
}

// 关闭考试时间提醒信息层
function examTxDivClose() {
	$('.exam_tx_div').hide();
}

// 显示考试时间提醒信息层
function showExamTxDiv(txTime) {
	txFlag = false;
	$("#tx_div_tNum").val(txTime);
	$('.exam_tx_div').show();
	setTimeout("examTxDivClose()", 15000);
}

// 学员班级考试提交（tj: zd-自动时间结束提交，dj-点击按钮提交）
function gradeExamInfoSave(tj) {
	var hh = $("#hh").html();
	var mm = $("#mm").html();
	var ss = $("#ss").html();
	$("#jsTime").val(hh + ":" + mm + ":" + ss);
	$("#examInfoBtn").attr("disabled", true);
	var dUrl = "/gradeExam/gradeExamSave.do";
	var ndUrl = "/index/index/1.shtml";
	$
			.ajax({
				type : "get",
				url : contextPath + dUrl,
				data : $("#examInfoFrom").serialize(),// fromid
				async : false,
				dataType : 'JSONP',// here
				jsonp : 'callback',
				jsonpCallback : "myCallbackFunction",
				success : function(data) {
					if (data == 'succ') {
						alert("您本次考试已成功提交！");
						window.close();
					} else {
						alert("您本次考试提交失败！");
					}
				}
			});
}

// 问答题答题操作
function wdOnCheck(taId, maxSize, qnum) {
	var defaultMaxSize = 200;
	var ta = document.getElementById(taId);
	if (!ta) {
		return;
	}
	if (!maxSize) {
		maxSize = defaultMaxSize;
	} else {
		maxSize = parseInt(maxSize);
		if (!maxSize || maxSize < 1) {
			maxSize = defaultMaxSize;
		}
	}
	if ($.trim(ta.value.length) > 0) {
		$("#click_li_" + qnum).addClass("li_m");
	} else {
		$("#click_li_" + qnum).removeClass("li_m");
	}
	var taTipDiv = $("#" + taId).next("p");
	taTipDiv.html("当前字数：" + ta.value.length + "，字数限制：" + maxSize);
	if (ta.value.length > maxSize) {
		ta.value = ta.value.substring(0, maxSize);
		taTipDiv.html("当前字数：" + ta.value.length + "，字数限制：" + maxSize);
	}
}