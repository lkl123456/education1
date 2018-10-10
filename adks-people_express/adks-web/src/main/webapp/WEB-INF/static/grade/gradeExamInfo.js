var contextPath = "http://localhost:8080/adks-web";
// ��������ʱ��
var txTime = '05';
// �Ƿ�������
var txFlag = true;

// �ӳ�SESSION ��ʶ
var csTime = 0;

// ҳ�����ʱִ��
$(function() {
	
	$("#userId").val(getUrlParam('userId'));
	$("#paperId").val(getUrlParam('paperId'));
	$("#examId").val(getUrlParam('examId'));
	$("#gradeId").val(getUrlParam('gradeId'));
	$("#examDate").html(getUrlParam('examDate'));
	$("#testTime").val(getUrlParam('examDate'));
	
	// ��ʼ��ʱ��
	initTime();
	// ��ʼ��ʱ
	startJs();
});

function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //����һ������Ŀ������������ʽ����
	var r = window.location.search.substr(1).match(reg);  //ƥ��Ŀ�����
	if (r!=null)
		return unescape(r[2]); 
	return null; //���ز���ֵ
} 

// ��ʼ������ʱ��
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

// ���Կ�ʼ��ʱ��ʱ�������Զ��ύ�Ծ�
function startJs() {
	var hh = $("#hh").html();
	var mm = $("#mm").html();
	var ss = $("#ss").html();

	// ����
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
// ��ѡ���ж�ѡ�����
function danxOnClick(qnum) {
	$("#click_li_" + qnum).addClass("li_m");
}

// ��ѡѡ�����
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
// ��մ������
function tkOnCheck(value, qnum) {
	if ($.trim(value) != '') {
		$("#click_li_" + qnum).addClass("li_m");
	} else {
		$("#click_li_" + qnum).removeClass("li_m");
	}
}

// �رտ���ʱ��������Ϣ��
function examTxDivClose() {
	$('.exam_tx_div').hide();
}

// ��ʾ����ʱ��������Ϣ��
function showExamTxDiv(txTime) {
	txFlag = false;
	$("#tx_div_tNum").val(txTime);
	$('.exam_tx_div').show();
	setTimeout("examTxDivClose()", 15000);
}

// ѧԱ�༶�����ύ��tj: zd-�Զ�ʱ������ύ��dj-�����ť�ύ��
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
				async : true,
				cache : false,
				dataType: 'JSONP',//here
				error : function() {
					alert("��ο����ύʧ�ܣ�");
					$("#examInfoBtn").attr("disabled", false);
				},
				success : function(data) {
					if (data == 'succ') {
						alert("��ο����ѳɹ��ύ��");
						// window.opener.document.getElementById("clickGradeExam").click();
						window.opener.document.getElementById("course_list_id")
								.click();
						$("#examInfoBtn").attr("disabled", true);
						window.close();
					} else {
						alert("��ο����ύʧ�ܣ�");
						$("#examInfoBtn").attr("disabled", false);
					}
				}
			});
}

// �ʴ���������
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
	taTipDiv.html("��ǰ����" + ta.value.length + "���������ƣ�" + maxSize);
	if (ta.value.length > maxSize) {
		ta.value = ta.value.substring(0, maxSize);
		taTipDiv.html("��ǰ����" + ta.value.length + "���������ƣ�" + maxSize);
	}
}