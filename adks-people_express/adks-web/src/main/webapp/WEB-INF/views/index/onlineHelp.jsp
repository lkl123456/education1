<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../static/common/common.jsp"%>
<%-- <%@ include file="/common/script.jsp"%> --%>
<!-- 
<div class="tux">
	<img src="../images/bang_07.gif" width="1000" height="130" />
</div>
 -->
<script type="text/javascript">
$(document).ready(function(){
	$('.news_l dd').click(function(){
	$('.news_l dd a').css({'color':'#333','font-weight':'normal'});
	$(this).find('a').css({'color':'#b00007','font-weight':'bold'});
		$neli=$(this).index();
		$('.news_r').hide();
		$('.news_r').eq($neli).show();
		return false;
	})
	$("#"+$("#mainFlag").val()).click();
})
</script>
<div class="tux">
</div>
<div class="news">
	<ul class="news_l">
		<%-- <li>
			<a href="<%=contextPath%>/user/feedbackIndex/${orgId }.shtml">在线反馈</a>
		</li> --%>
		<li class="ne_li">
			<a href="<%=contextPath%>/index/onlineHelp/xxlc/${orgId }.shtml">在线帮助</a>
		</li>
		<dl>
			<dd id="xxlc">
				<a style="font-weight: bold; color: #004896" href="<%=contextPath%>/index/onlineHelp/xxlc/${orgId }.shtml">学习流程</a>
			</dd>
			<dd id="czzn">
				<a href="<%=contextPath%>/index/onlineHelp/czzn/${orgId }.shtml">操作指南</a>
			</dd>
			<dd id="cjwt">
				<a href="<%=contextPath%>/index/onlineHelp/cjwt/${orgId }.shtml">常见问题</a>
			</dd>
			<dd id="kqbd">
				<a href="<%=contextPath%>/index/onlineHelp/kqbd/${orgId }.shtml">考前必读</a>
			</dd>
		</dl>
		<li>
			<a href="<%=contextPath%>/index/contactUs/${orgId }.shtml">学习支持</a>
		</li>
	</ul>
	<dl class="news_r">
		<dt>
			<b>学习流程</b>
			<span>当前位置：<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a> > 学习流程</span>
		</dt>
		<dd class="lchen">
			<p>
			
				目前平台支持浏览器IE7以上版本（含7）以及360浏览器，在使用平台前，请先确认您的电脑上是否安装浏览器, 如果未安装，请到百度查询IE或360浏览器下载安装。安装成功之后，启动浏览器，输入网址“http://59.46.46.81”开始您的学习之旅。
				 
			</p>
			<h3>
				<b>学习流程图</b>
			</h3>
			<H3>
				1.1干训学习流程图
			</H3>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/lc_1.jpg" />
			</h4>
			<H3>
				1.2撰写论文流程图
			</H3>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/lc_2.jpg" />
			</h4>
			<H3>
				1.3考试流程图
			</H3>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/lc_3.jpg" />
			</h4>
		</dd>
		<!-- 
		<div><a href="<%=contextPath%>/DownLoad/学习流程.doc"><img src="<%=contextPath%>/images/kchen110.gif" /></a></div>
		 -->
	</dl>
	
	<dl style="display: none" class="news_r">
		<dt>
			<b>操作指南</b>
			<span>当前位置：<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a> > 学习流程</span>
		</dt>
		<dd class="lchen">
			<p>
				为了保证平台的课程学习过程中计时的准确性，请尽量使用主流浏览器进行课程播放学习，推荐谷歌chrome浏览器，火狐浏览器，360浏览器极速模式，搜狗浏览器极速模式。
			</p>
			<h3>
				1登录系统
			</h3>
			<H3>
				1.1登录
			</H3>
			<p>
				在浏览器地址栏中输入网址“http://www.caacmooc.org”，打开中国民航教育培训在线首页，点击“两学一做”专题学习。（图1-1）
			</p>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_1.png" />
			</h4>
			<span>（图1-1）</span>
			<p>
				进入“中国民航局党校-民航党员在线课堂”首页，在首页右侧“学员登录”区（图1-2）进行登录，或者点击右上角登录链接打开登录页（图1-3）进行登录。
			</p>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_2.png" />
			</h4>
			<span>（图1-2）</span>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_3.png" />
			</h4>
			<span>（图1-3）</span>
			<H3>
				1.2修改密码及完善个人资料
			</H3>
			<p>
				登录成功之后，点击菜单“学习中心”项（图1-4）进入学习中心页，点击“修改密码”（图1-5），修改密码；点击“设置”，完善个人资料。
			</p>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_4.png" />
			</h4>
			<span>（图1-4）</span>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_5.png" />
			</h4>
			<span>（图1-5）</span>
			
			<h3>
				2 课程学习
			</h3>
			<p>
				在学习中心页点击上“开始培训”（图1-6），进入到培训页面首页（图1-7）,这时能看到自己正在学习的课程，点击“继续观看”；或者点击菜单“课程”项，打开课程页（图1-8），点击对应课程的“继续观看”，弹出课程播放页窗口（图1-9）。
您也可以通过左侧“课程列表”或“搜索功能”精确的找到您需要的课程（图1-8），来进行课程学习。

			</p>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_6.png" />
			</h4>
			<span>（图1-6）</span>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_7.png" />
			</h4>
			<span>（图1-7）</span>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_8.png" />
			</h4>
			<span>（图1-8）</span>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_9.png" />
			</h4>
			<span>（图1-9）</span>
			<p>
				<span>课程进度是每隔30秒记录一次，不需要其他操作。<span style="color: red;font-weight: bold">特别注意：禁止向后拖拽进度条上的游标（图1-9），否则会造成无法记录课时的后果。</span></span>

				
			</p>
			<h3>
				3 学习支持
			</h3>
			<p>
				如果您遇到课程视频长时间无法播放、课程看不了、课时无法记录等使用问题，可直接询问维护人员（图1-10）。
			</p>
			<h4>
				<img width="600" src="<%=contextPath%>/static/images/lc_10.png" />
			</h4>
			<span>（图1-10）</span>

		</dd>
		<!-- 
		<div><a href="<%=contextPath%>/DownLoad/操作指南.doc"><img src="<%=contextPath%>/images/kchen110.gif" /></a></div>
		 -->
	</dl>
	<dl class="news_r" style="display: none">
		<dt>
			<b>常见问题</b>
			<span>当前位置：<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a> > 常见问题</span>
		</dt>
		<dd class="lchen">
			<h3>
				1无法观看视频，画面显示白屏、黑屏
			</h3>
			<p>
				第一步,安装或升级Flash最新播放器:
				<a target="_blank" style="text-decoration: underline; color: #004986" href="https://get.adobe.com/cn/flashplayer/?no_redirect">官方下载</a>
				</br>
				注: 下载安装后, 须关闭全部浏览器窗口, 重新开启浏览器方可正常观看。
			</p>
			<p>
				第二步,清空浏览器缓存: 打开IE浏览器菜单, ”工具” —> ”Internet选项” —> ”常规” —> ”浏览历史记录” —> 清除浏览器Internet临时文件、缓存、垃圾、Cookie等, 重启浏览器。
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie1.png" />
			</h4>
			<p>
				第三步,为浏览器开启Java及Active功能。
				<br />
				打开IE浏览器顶部菜单的"工具" —> "Internet选项", 切换到"安全"选项卡, 点击"默认级别"按钮, 确定。
			</p>
			<p>
				如出现警告提示框, 请选择"是"。(见下图)
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie2.png" />
			</h4>
			<p style="margin-bottom: 0">
				或者在第四步基础上, 选择左边按钮"自定义级别", 拖动滚动条, 找到关于脚本的描述, 点选启用。
			</p>
			<p style="margin-bottom: 0">
				如果您是FireFox用户, 在"工具" —> "选项" —> "内容"位置, 勾选启用Java和JavaScript即可。
			</p>
			<p>
				开启Active功能：与开启Java相同。选择"默认级别"按钮, 系统将默认开启ActiveX。
			</p>
			<p>
				第四步,取消使用代理服务器, 打开IE浏览器菜单, “工具” —> “Internet选项” —>“连接”—>“局域网设置”，取消代理服务器设置。
			</p>
			<p>
				第五步,使用安全软件清理系统插件（清理掉流氓插件），修复浏览器到默认配置。
			</p>
			<p>
				第六步,进行过上述操作后仍未见效，请联系您所在地区的宽带运营商，帮您查看连接问题，并设置适合您的DNS地址。
			</p>
			<p style="text-align: 0; margin-bottom: 0">
				设置DNS的步骤如下：
			</p>
			<p>
				1) 打开网络连接，右键查看网络连接属性；
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie3.png" />
			</h4>
			<h3>
				2) 选择Internet协议版本4，查看属性；
			</h3>
			<h4>
				<img src="<%=contextPath%>/static/images/ie4.png" />
			</h4>
			<p>
				3) 请联系您的网络运营商获取正确的DNS服务器地址；
			</p>
			<p>
				如果您使用的是360浏览器, 打开菜单”工具” —> ”浏览器医生”（或按下键盘上的F1）,按”一键修复”, 重新启动浏览器。
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie5.png" />
			</h4>
			<p>
				视频播放器加载需要时间，且视频播放速度取决于当地网络环境，建议在此期间减少其他网络操作以加快缓冲速度。
			</p>
			<p>
				如果您通过上述操作仍然没有解决问题，可能是您所使用的网络运营商劫持了视频的网址解析，将视频解析到了错误的IP上，所以出现播放错误或长时间的缓冲，我们建议您联系您的运营商或宽带安装公司的服务人员来帮您修复解决。
			</p>
			<p>
				2无法观看视频，提示”Error loading media:File could not be played!”错误
			</p>
			<p>
				此问题是由于视频文件损坏或者链接出错导致，建议您与平台管理人员联系或点击屏幕右侧在线反馈，将问题反馈给管理人员。
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie6.png" />
			</h4>
			<h3>
				3画面一直加载中，拖动进度条无效
			</h3>
			<p>
				建议您检查网络稳定性，暂停其它的下载活动, 如: BT下载、其它P2P软件的数据交流。可显著提高视频的流畅度。
			</p>
			<p>
				清空浏览器缓存: 打开IE浏览器菜单, ”工具” —> ”Internet选项” —> ”常规” —> ”浏览历史记录” —> 清除浏览器Internet临时文件、缓存、垃圾、Cookie等, 重启浏览器。
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie7.png" />
			</h4>
			<p>
				视频若播放一段后停止缓冲，尝试将视频进度条向后拖动一小段。
			</p>
			<p>
				视频长时间不加载，进行过上述操作后仍未见效，请联系您所在地区的宽带运营商，帮您查看连接问题，并设置适合您的DNS地址。
			</p>
			<p>
				设置DNS的步骤如下：
			</p>
			<p>
				1)打开网络连接，右键查看网络连接属性；
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie8.png" />
			</h4>
			<p>
				2) 选择Internet协议版本4，查看属性；
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie9.png" />
			</h4>
			<p>
				3) 设定使用下面的dns地址；
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie10.png" />
			</h4>
			<h3>
				4学习进度自动保存
			</h3>
			<p>
				学习课程自动保存并全程跟踪学习记录，学习完课程后，刷新课程页面，可查看学习实时进度。
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie11.png" />
			</h4>
			<h3>
				5断点续播
			</h3>
			<p>
				将保持本次学习进度和最后学习时间点，以便支持续播功能，即下次继续学习，系统自动判断接着上次学习点继续课程学习。
			</p>
			<h3>
				6学习监控
			</h3>
			<p>
				为了防止学习作弊，设置了自动学习监控，每10分钟自动监控用户学习，不点击“继续学习”按钮，系统自动暂停记录学习进度，不会影响暂停前的学习进度保存；点击”继续学习“按钮，继续记录跟踪用户学习。
			</p>
			<h4>
				<img src="<%=contextPath%>/static/images/ie12.png" />
			</h4>

		</dd>
		<!-- 
		<div><a href="<%=contextPath%>/DownLoad/常见问题.doc"><img src="<%=contextPath%>/images/kchen110.gif" /></a></div>
		 -->
	</dl>
	<dl class="news_r" style="display: none">
		<dt>
			<b>考前必读</b>
			<span>当前位置：<a href="<%=contextPath%>/index/index/${orgId }.shtml">首页</a> > 考前必读</span>
		</dt>
		<dd class="lchen">
			<H3>
				1.浏览器支持，建议使用IE7以上版本（含7）以及360浏览器
			</H3>
			<p>
				目前平台支持浏览器IE7以上版本（含7）以及360浏览器，在考试前请先确认您的电脑上是否安装浏览器, 如果未安装，请到百度查询IE或360浏览器下载安装。安装成功之后，启动浏览器，输入网址“http://59.46.46.81”开始您的学习之旅。
			</p>
			<H3>
				2.点击交卷，长时间等待交卷，无法成功提交
			</H3>
			<p>
				如出现点击交卷，长时间处于等待状态，无法成功提交。由于浏览器cookies引起，解决办法如下：
			</p>
			<ul>
				<li>
					a. 打开浏览器的 工具 → Internet 选项 → 常规，点击删除（如图2-2）
				</li>
				<li>
					b. 如图2-3全部选中，点击删除
				</li>
				<li>
					c.工具 → Internet 选项 → 隐私 → 高级
				</li>
				<li>
					d. 确定“替代自动 cookie 处理”是没有选上，如下图2-4
				</li>
			</ul>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/kqbd_4.png" />
			</h4>
			<span>（图2-3）</span>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/kqbd_5.png" />
			</h4>
			<span>（图2-4）</span>
			<H3>
				3.点击交卷无反应
			</H3>
			<p>
				如出现点击交卷，页面无任何反应。请按如下操作设置：
			</p>
			<ul style="padding-left: 12px;">
				<li>
					第一步，启用JavaScript脚本 如果被禁用了，请根据以下步骤启用：
				</li>
				<li style="padding-left: 12px;">
					a. 打开浏览器的 工具→Internet 选项→安全→Internet →自定义级别
				</li>
				<li style="padding-left: 12px;">
					b. 找到“脚本”部分，如下图3-1
				</li>
				<li style="padding-left: 12px;">
					c. 确定“活动脚本”是“启用”状态
				</li>
			</ul>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/kqbd_6.png" />
			</h4>
			<span>（图3-1）</span>
			<p>
				第二步，取消阻止弹出窗口 如果您的浏览器禁止任何弹出窗口，请把它取消。或者您安装了任何可以拦截弹出窗口的工具，请把该功能也取消。
			</p>
			<ul>
				<li style="padding-left: 12px;">
					a. 打开浏览器的 工具 → Internet 选项 → 安全 → 可信站点 → 自定义级别
				</li>
				<li style="padding-left: 12px;">
					b. 设置禁用“弹出窗口阻止程序”。
				</li>
			</ul>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/kqbd_7.png" />
			</h4>
			<span>（图3-2）</span>
			<ul>
				<li>
					第三步，ActiveX控件和插件需要启用
				</li>
				<li style="padding-left: 12px;">
					a. 打开浏览器的 工具 → Internet 选项 → 安全 → 可信站点 → 自定义级别
				</li>
				<li style="padding-left: 12px;">
					b. 找到“ActiveX控件和插件”部分，如下图3-3
				</li>
				<li style="padding-left: 12px;">
					c. 确定“ActiveX控件和插件”是“启用”状态
				</li>
			</ul>
			<h4>
				<img class="min" src="<%=contextPath%>/static/images/kqbd_8.png" />
			</h4>
			<span>（图3-3）</span>
		</dd>
		<!-- 
		<div><a href="<%=contextPath%>/DownLoad/考前必读.doc"><img src="<%=contextPath%>/images/kchen110.gif" /></a></div>
		 -->
	</dl>
	
	
	 
	 
</div>
