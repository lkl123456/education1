<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/dtree.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dtree/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>

<dl class="min_right">
	<dt>
		<ol>
			<li class="right_min_1"><a>个人设置</a><span></span></li>
			<li><a>修改密码</a></li>
		</ol>
	</dt>
	<dd class="mi_r1">
		<ul>
			<input id="orgId" type="hidden" value="${orgId}" />
			<form id="userInfoSave" name="userInfoSaveFrom" method="post"
				action="<%=contextPath%>/user/saveUserInfo.do"
				enctype="multipart/form-data">
				<input name="userName" type="hidden" value="${user.userName}" /> <input
					name="userId" id="userId" type="hidden" value="${user.userId}" />
				<input name="headPhoto" type="hidden" value="${user.headPhoto}" />
				<input name="changeflag" id="changeflag" type="hidden"
					value="${flag}" />
				<li><label>用户名：</label>${user.userName}</li>
				<li class="mi_li1"><label>头像：</label> 
					<c:if test="${empty user.headPhoto }">
						<img id="headPicpathInfo" src="<%=contextPath%>/static/images/userImg.jpg" width="82" height="82" />
					</c:if> <c:if test="${!empty user.headPhoto  }">
						<img id="headPicpathInfo" src="<%=imgServerPath%>${user.headPhoto}" width="82" height="82" />
					</c:if> <input name="headPhotofile" id="headPhotofile" type="file"
					value="" /> <strong> 仅支持JPG，PNG,GIF图片文件，且文件小于5M</strong></li>
				<li><label>真实姓名：</label><input name="userRealName" type="text" value="${user.userRealName}" class="n_input1" /></li>
				<li><label>性别：</label> <c:if test="${ user.userSex == 1 }">
						<input name="userSex" type="radio" value="1" checked="checked" />男&nbsp;&nbsp;
	              	    <input name="userSex" type="radio" value="2" />女
	              	</c:if> <c:if test="${ user.userSex == 2 }">
						<input name="userSex" type="radio" value="1" />男&nbsp;&nbsp;
	              	    <input name="userSex" type="radio" value="2" checked="checked" />女
	              	</c:if></li>
				<li><label>职级：</label> 
					<input id="rankName" name=rankName type="text" value="${user.rankName}" onclick="getZhijiAll();" class="n_input1"/> 
					
				</li>
				<li>
					<label>职务：</label>
	             	<select id="positionId" name="positionId">
	             	<c:if test="${ !empty zhiWuList }">
	             		<option value="" ></option>
	            		<c:forEach items="${ zhiWuList }" var="zhiji">
	            			<c:if test="${zhiji.rankId eq user.positionId}">
	            				<option value="${zhiji.rankId}" selected="selected">${zhiji.rankName}</option>
	            			</c:if>
	            			<c:if test="${zhiji.rankId ne user.positionId}">
	            				<option value="${zhiji.rankId}" >${zhiji.rankName}</option>
	            			</c:if>
	            		</c:forEach>
	            	</c:if>
					</select>
					<input name="rankId" id="rankId" type="hidden" value="${user.rankId}" />
					<input name="positionName" id="positionName" type="hidden" value="${user.positionName}" />
				</li>
				<li><label>手动输入职务：</label> 
					<input type="text" placeholder="以上没有合适的职务，请手动输入" name="rankNameStr"  id="rankNameStr" class="n_input1"/>
				</li>
				<div id="menu_zzjs_net" style="position:absolute; width:auto; top:328px; left:150px; z-index:9999;"></div>
				<li><label>移动电话：</label> <input id="userPhone" name="userPhone"
					type="text" value="${user.userPhone}" onblur="vailUserPhone();" class="n_input1" />
					<span id="userPhoneSpan" class="xing"></span>
				</li>
				<li>
	            <label>身份证号：</label>
	            <input id="userCard" class="n_input1" name="cardNumer" type="text" value="${user.cardNumer}" onblur="vailUserCard();" /> <span id="userCardSpan" class="xing"></span>
	            </li>
				<!-- 
	            <li><span>民族：</span>
	            <select name="nationality" id="nationality" >
	            	<c:if test="${ !empty sessionScope.evList }">
	            		<c:forEach items="${ sessionScope.evList }" var="enumerationValue">
	            			<c:if test="${enumerationValue.id eq user.nationality}">
	            				<option value="${enumerationValue.id}" selected="selected" >${enumerationValue.displayRef}</option>
	            			</c:if>
	            			<c:if test="${enumerationValue.id ne user.nationality}">
	            				<option value="${enumerationValue.id}"  >${enumerationValue.displayRef}</option>
	            			</c:if>
	            		</c:forEach>
	            	</c:if>
				</select>
	            </li>
	            <li><span>岗位：</span><input name="rankName" type="text" value="${user.rankName}" readonly="readonly"/></li>
	             -->
				<li class="mi_li3"><input name="" type="button" value="确 定"
					onclick="javascript:saveUserInfo();" /> <span>${succ}</span></li>
			</form>
		</ul>

		<ul style="display: none;">
			<li><span>旧 密 码：</span> <input id="password" name="userPassword"
				type="text" onblur="getUserPassWord();" /><span id="passwordSpan"
				class="xing"></span></li>
			<li><span>新 密 码：</span> <input id="onePassWord" name="onePassWord"
				type="password" onblur="vailNewPassWord();" /> <span
				id="onePassWordSpan" class="xing"></span></li>
			<li><span>确认密码：</span> <input id="twoPassWord"
				name="twoPassWord" type="password" onblur="vailPassWord();" /> <span
				id="twoPassWordSpan" class="xing"></span></li>
			<li class="mi_li3"><input name="" id="subPass"
				disabled="disabled" type="button" value="确 定"
				onclick="saveNewPassWord();" /><span id="savePassWordSpan"
				class="xing"></span></li>
		</ul>
	</dd>
</dl>

<script type="text/javascript">
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}

	$(document).ready(
			function() {
				$('.bixu img').click(function() {
					$('.bixu').hide(100);
				});
				$('.min_right dt ol li a').click(
						function() {
							$keyy = $(this).index('.min_right dt ol li a');
							$('.mi_r1 ul').eq($keyy).show();
							$('.mi_r1 ul').eq($keyy).siblings().hide();
							$('.min_right dt ol li').eq($keyy).addClass(
									'right_min_1');
							$('.min_right dt ol li').eq($keyy).siblings()
									.removeClass('right_min_1');

						});
			});
	var adkstree = new dTree('adkstree');//创建一个对象.
	//得到所有的职级
	function getZhijiAll() {
		var _html = "";
		//adkstree.add(0, -1, "全部职级", "javascript:setRankValue('" + 0 + "','sa')");
		$.ajax({
			type : "POST",
			url : contextPath + "/user/getZhijiListAll.do",
			data : {
				timeStampe : new Date()
			},
			async : false,
			success : function(data) {
				_html = data;
			}
		});
		//alert(_html);
		if (_html != "") {
			_html = JSON.parse(_html);
			for (var i = 0; i < _html.length; i++) {
				adkstree.add(_html[i].rankId,//当前分了ID
				_html[i].parentId,//父级分类ID
				_html[i].rankName,//当前分类名称
				"javascript:setRankValue('" + _html[i].rankId + "','"+ _html[i].rankName + "')"//当前分类链接
				);
			}
		}
		document.getElementById("menu_zzjs_net").innerHTML = adkstree;
	}
	function setRankValue(rankId, rankName) {
		if (rankId == '0' || rankId == 0) {
			$("#rankId").val('');
			$("#rankName").val('');
		} else {
			$("#rankId").val(rankId);
			$("#rankName").val(rankName);
		}
		document.getElementById("menu_zzjs_net").innerHTML = '';
		changZHiwu(rankId);
	}
	function changZHiwu(rankId){
		if (rankId == '0' || rankId == 0) {
			$('#positionId').empty();
		}else{
			$.ajax({
				async:false,
				url:contextPath+"/user/getZhiwu.do?zhijiId="+rankId,
				type:'post',
				success:function(data){
					var dataJSON=$.parseJSON(data);
					$('#positionId').empty();
					$('#positionId').append("<option value='0' selected = 'true'></option>");
					for (i = 0; i < dataJSON.length; i++){
						$('#positionId').append("<option value='"+dataJSON[i]['rankId']+"'>"+dataJSON[i]['rankName']+"</option>");
					}
				}
			});
		}
	}
	$(document).ready(function() {
		if ($("#changeflag").val() == "changepass") {
			$('.min_right dt ol li a').eq(1).trigger('click');
		}
	});
	// 邮箱格式验证
	function vailUserMail() {
		var userMail = $("#userMail").val();
		var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"
		var re = new RegExp(regu);
		if (!re.test(userMail)) {
			$("#userMailSpan").text(' * 请输入正确的邮箱格式！');
			return false;
		} else {
			$("#userMailSpan").text('');
		}
	}
	// 手机号码验证
	function vailUserPhone() {
		var userPhone = $("#userPhone").val();
		if (checkNum(userPhone)) {
			var regu = "^0{0,1}(13[0-9]|15[0-9]|153|156|18[0-9])[0-9]{8}$";
			var re = new RegExp(regu);
			if (!re.test(userPhone)) {
				$("#userPhoneSpan").text(' * 手机号码格式不正确 ！');
				return false;
			} else {
				$("#userPhoneSpan").text('');
			}
			if (userPhone.length > 11) {
				$("#userPhoneSpan").text(' * 手机号码不能超过11位 ！');
				return false;
			} else {
				$("#userPhoneSpan").text('');
			}
		} else {
			$("#userPhoneSpan").text(' * 手机号码必须为数字！');
			return false;
		}
	}

	//密码一致验证
	function getUserPassWord() {
		var userId = $("#userId").val();
		var password = $("#password").val();
		$.ajax({
			async : true,
			url : contextPath + "/user/getUserPassWord.do?userId=" + userId
					+ "&password=" + password,
			type : "post",
			success : function(succ) {
				if (succ == "error") {
					$("#passwordSpan").text(' * 您输入的密码有误！');
				} else if (succ == "passerror") {
					$("#passwordSpan").text(' * 原密码不能为空！');
				} else {
					$("#passwordSpan").text('');
				}
			}
		});
	}
	function vailNewPassWord() {
		var password = $("#password").val();
		var onePassWord = $("#onePassWord").val();
		if (password == onePassWord) {
			$("#onePassWordSpan").text('新密码和原密码不能相同！');
			return false;
		} else {
			if (onePassWord.trim().length < 6) {
				$("#onePassWordSpan").text('密码不得少于六位！');
				return false;
			} else {
				$("#onePassWordSpan").text('');
			}
		}
	}
	function vailPassWord() {
		var password = $("#password").val();
		var onePassWord = $("#onePassWord").val();
		var twoPassWord = $("#twoPassWord").val();
		if (password == onePassWord) {
			$("#onePassWordSpan").text('新密码和原密码不能相同！');
			return false;
		} else {
			if (onePassWord != twoPassWord) {
				$("#twoPassWordSpan").text('两次输入的新密码必须相同！');
				return false;
			} else {
				if (onePassWord.trim().length < 6
						&& twoPassWord.trim().length < 6) {
					$("#twoPassWordSpan").text('密码不得少于六位！');
					return false;
				} else {
					$("#subPass").removeAttr("disabled");
					$("#twoPassWordSpan").text('');
				}
			}
		}
	}

	/*是否为整数*/
	function checkNum(num) {
		//var re = /^[1-9]\d*$/;
		var re = /^[-]{0,1}[0-9]{1,}$/;
		if (!re.test(num)) {
			return false;
		} else {
			return true;
		}
	}
</script>
