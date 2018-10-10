<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <script type="text/javascript" src="${ip}/static/js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ip}/static/css/papercss.css" />
	<script type="text/javascript" src="${ip}/static/js/gradeExamInfo.js"></script>
    <title>${paperName}</title>
</head>
<body>
    <form id="examInfoFrom" name="examInfoFrom" method="post">
		<input type="hidden" name="paperId" id="paperId" />
		<input type="hidden" name="examId" id="examId"/>
		<input type="hidden" name="userId" id="userId"/> 
		<input type="hidden" name="testTime" id="testTime"/>
		<input type="hidden" name="gradeId" id="gradeId" />
		<input type="hidden" name="jsTime" id="jsTime" value="00:00:00" />
		<dl class="zidao">
			<dt>《${paperName }》</dt>
			<dd>
				考试时间：<font id="examDate"></font>分钟<br />全卷题数：${qsNum }道题<br />
				卷面总分：${score }分
			</dd>
		</dl>
		<div class="shiti">
			<div style="width: 720px; float: left;">${dContent}${dxContent}${pdContent}${tkContent}${wdContent}</div>
			<div class="shiti_r">
				<div class="jishi">
					<span id="hh">00</span> <b>:</b> <span id="mm">00</span> <b>:</b> <span
						id="ss">00</span>
					${dContentDA}${dxContentDA}${pdContentDA}${tkContentDA}${wdContentDA}
					<div class="tijiao_sj">
						<input id="examInfoBtn" type="button"
							onclick="gradeExamInfoSave('dj')" value="提交试卷" title="点击提交试卷" />
					</div>
				</div>
			</div>
		</div>
	</form>
	<div class="bg exam_tx_div" id="exam_tx_div" style="display: none;">
		<dl class="bg_biao bg_biao_tx">
			<dt>
				考试时间提醒<span class="lv"></span><b onclick="examTxDivClose()"></b>
			</dt>
			<dd>
				<img src="<%=contextPath%>/center/grade/images/exam_tx.png"
					height="74" width="86" /> 您好！距离考试结束还有 <b class="cheng"
					id="tx_div_tNum">5</b> 分钟， 请您仔细检查试卷，时间结束后系统将自动提交试卷！（十五秒钟后该提醒将自动关闭）
			</dd>
		</dl>
	</div>
</body>
</html>