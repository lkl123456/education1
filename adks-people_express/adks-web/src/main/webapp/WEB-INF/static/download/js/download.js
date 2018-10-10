window.onload = function() {
	var u = navigator.userAgent;
	if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) { // 安卓手机
		// alert("安卓手机");
		window.location.href = "http://120.27.10.217/static/download/mhdyzxkt_v1.0.apk";
	} else if (u.indexOf('iPhone') > -1) { // 苹果手机
		//alert("苹果手机");
		window.location.href = "https://itunes.apple.com/cn/app/id1239331323";
	}
}

$(window).on("load", function() {
	var winHeight = $(window).height();
	function is_weixin() {
		var ua = navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == "micromessenger") {
			return true;
		} else {
			return false;
		}
	}
	var isWeixin = is_weixin();
	if (isWeixin) {
		$(".weixin-tip").css("height", winHeight);
		$(".weixin-tip").show();
	}
})