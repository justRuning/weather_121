<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="http://wx.nmc.cn/newwx//Public/template//css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/WeChat/WebContent/style/css/am.css" media="all" />
<link type="text/css" rel="stylesheet"
	href="https://weather.com/sites/acquia-prod/files/css/css_fuampu2Gui7i8EV42Wp96BvmGE6woAVkWTZHK7gJf8Y.css"
	media="all" />
<link type="text/css" rel="stylesheet"
	href="https://weather.com/sites/acquia-prod/files/css/css_9wvYYAYlmZe3p-IkrIqCRhAo6S6S_4mbH94xoCzPE-E.css"
	media="all" />
<script type="text/javascript"
	src="http://wx.nmc.cn/newwx//Public/template//js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="http://wx.nmc.cn/newwx//Public/template//js/iScroll v4.2.5.js"></script>
<script type="text/javascript"
	src="http://wx.nmc.cn/newwx//Public/template//js/jquery.flot.min.js"></script>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="http://code.highcharts.com/highcharts.js"></script>


<title>天气预报</title>
</head>
<body id="back" onload="pageLoad()"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes">
	<section class="qui-page"> <header class="header">

	<div class="logo">
		<img src="http://chuantu.biz/t4/15/1463290515x1035372876.png" />
	</div>
	<div>
		<span style="color: black"> <select id="select_id"
			onChange="window.location.href=this.value"
			style="background: transparent; border: 0; text-align: center; height: 50px; line-height: 50px; font-family: '黑体'">
				<option value="/WeChat/forecast?name=临河">临河</option>
				<option value="/WeChat/forecast?name=磴口">磴口</option>
				<option value="/WeChat/forecast?name=杭锦后旗">杭锦后旗</option>
				<option value="/WeChat/forecast?name=五原">五原</option>
				<option value="/WeChat/forecast?name=乌前旗">乌前旗</option>
				<option value="/WeChat/forecast?name=乌中旗">乌中旗</option>
				<option value="/WeChat/forecast?name=乌后旗">乌后旗</option>
				<option value="/WeChat/forecast?name=海力素">海力素</option>
				<option value="/WeChat/forecast?name=大佘太">大佘太</option>
		</select> <img alt="" src="./style/img/arrow.png">
		</span>
	</div>
	<script>
		document.getElementById("select_id").value="/WeChat/forecast?name=${forecasts[0].name}"
	</script>
	<div class="nav">
		<img src=>
	</div>
	<div class="line_header">
		<img src=>
	</div>
	</header>
	<div class="tqyb">
		<div class="back"></div>

		<div class="dq_tq" align="center">
			<div id="tq" class="num">&nbsp&nbsp${Integer.parseInt(weathers[11].tem)/10}℃</div>

		</div>
		<div class="dushu">
			<div class="tm_dushu"></div>
			<div class="line_h"></div>
			<div class="line_s1"></div>
			<div class="line_s2"></div>
			<ul>
				<li><img
					src="http://wx.nmc.cn/newwx//Public/template//images/tg.png">
					<p>地表温度</p>
					<p class="p1">${Integer.parseInt(weathers[11].surfaceTem)/10}℃</p></li>
				<li><img
					src="http://wx.nmc.cn/newwx//Public/template//images/qy.png">
					<p>气压</p>
					<p class="p1">${Integer.parseInt(weathers[11].barometricPressure)/10}hPa</p></li>
				<li><img
					src="http://wx.nmc.cn/newwx//Public/template//images/sd.png">
					<p>相对湿度</p>
					<p class="p1">${Integer.parseInt(weathers[11].relativeHumidity)}%</p></li>
				<li><img
					src="http://wx.nmc.cn/newwx//Public/template//images/js.png">
					<p>降水</p>
					<p class="p1">${Integer.parseInt(weathers[11].water)/10}mm</p></li>
				<li><img
					src="http://wx.nmc.cn/newwx//Public/template//images/fs.png">
					<p>风向风速</p>
					<p class="p1">${weathers[11].windDirection10min }${Integer.parseInt(weathers[11].windVelocity10min)/10}m/s</p></li>
				<li><img
					src="http://wx.nmc.cn/newwx//Public/template//images/ssd.png">
					<p>能见度</p>
					<p class="p1">${Integer.parseInt(weathers[11].visibility)/10}m</p></li>
			</ul>
		</div>
		<div class="title_3" align="center">
			<div class="tm_title" align="center"></div>
			<h1>温度实况</h1>
		</div>
		<div id="container" style=""min-width:700px;height:450px"></div>


		<div style="top:10px;height: 40px; position: relative; font-size: 18px" align="center">
			&nbsp未来一周天气预报 
		</div>


		<div class="weather-table -table day-table">
			<div class="legend summary-view">
				<span class="heading weather-cell"
					style="font-size: 22px; font-family: 黑体;">日期</span> <span
					class="temp-12 weather-cell"
					style="font-size: 22px; font-family: 黑体;"> <span>最高温度</span>/<span>最低温度</span>
				</span> <span class="weather-phrase weather-cell">天空状况</span> <span
					class="wind-conditions weather-cell"
					style="font-size: 22px; font-family: 黑体;">风力</span>
			</div>
		</div>


		<div class="weather-table day-table list height"
			style="margin: 0px; width: 100%;">
			<c:forEach items="${forecasts}" var="forecast" varStatus="s">
				<section class="wxcard wxcard-summary summary-view is24Hour">
				<header class="heading weather-cell">
				<h2 data-ng-bind="::(daypart.title || daypart.day.dayPartName)">${forecast.week }</h2>
				<span class="sub-heading"> <span class="wx-dsxdate">
						${forecast.toTime }日</span> <!-- end ngIf: ::!daypart.holidayName -->
				</span> </header>
				<p class="temp temp-12 weather-cell">
					<span class="hi-temp temp-1" class="dir-ltr"> <c:if
							test="${forecasts[0].hour == 16}">
   							${forecast.highTem }℃/${forecast.lowTem }℃
						</c:if> <c:if test="${forecasts[0].hour == 6}">
							${forecast.highTem }℃/${forecast.lowTem }℃
						</c:if> <c:if test="${forecasts[0].hour == 10}">
							${forecast.highTem }℃/${forecast.lowTem }℃
						</c:if>
					</span>
				</p>
				<h3 class="weather-phrase weather-cell">
					<span lass="phrase">${forecast.weather }</span>
				</h3>

				<p class="wind-conditions weather-cell">${forecast.wind }</p>
				</section>
			</c:forEach>
		</div>
	</div>
	</section>
	<div class="footer_h">
		<h1>© 气象灾害防御中心</h1>
	</div>

</body>


<script language="JavaScript">
$(function() {
	$('#container')
			.highcharts(
					{
						chart : {
							
						},
						title : {
							text : '过去12小时气温变化',
							x : -20
						//center
						},
						subtitle : {
							text : '',
							x : -20
						},
						xAxis : {
							categories : ${hours}
						},
						yAxis : {
							title : {
								text : 'Temperature (°C)'
							},
							
							plotLines : [ {
								value : 0,
								width : 0.5,
								color : '#808080'
							} ]
						},
						tooltip : {
							valueSuffix : '°C'
						},
						legend : {
							layout : 'vertical',
							align : 'center',
							verticalAlign : 'bottom',
							borderWidth : 0
						},
						plotOptions: {
				            line: {
				                dataLabels: {
				                    enabled: true
				                },
				                enableMouseTracking: false
				            }
				        },
						series : [
								{
									name : '气温',
									data : ${tems}
								}
								]
					});
});
</script>






<script>
	var devicewidth = document.body.clientWidth;
	var signwidth = (devicewidth) / 5;
	var ulwidth = signwidth * 7;
	$("#wrapper #scroller,.zhexian").width(ulwidth);
	$("#wrapper ul").width(ulwidth);
	$("#wrapper ul li").width(signwidth);
	$(".weather ul li div").css("margin-left", (signwidth - 8) / 2);
	$(".zx").css("left", signwidth / 2);
	var myscroll = new iScroll("wrapper", {
		hScrollbar : false,
		vScrollbar : false,
		vScroll : false,
		onBeforeScrollStart : function(e) {
			e.preventDefault();
		}
	});
	$(".zxt").each(function() {
		var id = $(this).attr("data-id");
		$(this).css("left", signwidth * id)
	});
	function $$(id) {
		return document.getElementById(id);
	};
	function pageLoad() {
		var can = $$('can');
		var cans = can.getContext('2d');
		cans.moveTo(0, 60);
		cans.lineTo(signwidth, 52);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can_1');
		var cans = can.getContext('2d');
		cans.moveTo(0, 52);
		cans.lineTo(signwidth, 44);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can_2');
		var cans = can.getContext('2d');
		cans.moveTo(0, 44);
		cans.lineTo(signwidth, 49.6);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can_3');
		var cans = can.getContext('2d');
		cans.moveTo(0, 49.6);
		cans.lineTo(signwidth, 22.4);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can_4');
		var cans = can.getContext('2d');
		cans.moveTo(0, 22.4);
		cans.lineTo(signwidth, 12);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can_5');
		var cans = can.getContext('2d');
		cans.moveTo(0, 12);
		cans.lineTo(signwidth, 0);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can1');
		var cans = can.getContext('2d');
		cans.moveTo(0, 19);
		cans.lineTo(signwidth, 29);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can1_1');
		var cans = can.getContext('2d');
		cans.moveTo(0, 29);
		cans.lineTo(signwidth, 6);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can1_2');
		var cans = can.getContext('2d');
		cans.moveTo(0, 6);
		cans.lineTo(signwidth, 29);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can1_3');
		var cans = can.getContext('2d');
		cans.moveTo(0, 29);
		cans.lineTo(signwidth, 34);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can1_4');
		var cans = can.getContext('2d');
		cans.moveTo(0, 34);
		cans.lineTo(signwidth, 18);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();

		var can = $$('can1_5');
		var cans = can.getContext('2d');
		cans.moveTo(0, 18);
		cans.lineTo(signwidth, 0);
		cans.lineWidth = 2;
		cans.strokeStyle = '#fff';
		cans.stroke();
	}
</script>
<script type="text/javascript">
	/*全局js*/
	//清除IPHONE导航条
	setTimeout(function() {
		window.scrollTo(0, 0);
	}, 0);
	$(window).load(
			function() {
				$(".photo img").height($(".photo img").width());
				$(".tc").css("display", "none");
				$(".nav").click(function() {
					$("body").find(".mask").remove();
					$("body").append("<div class='mask'></div>");
					var dis = $("body").find(".tc");
					if (dis.css("display") == "none") {
						$(".mask").fadeIn("slow");
						dis.css("display", "block").fadeIn("slow");
					} else {
						dis.css("display", "none").fadeOut("slow");
					}
				});
				$(".dushu ul li").height($(".dushu").width() / 3);
				$(".tm_dushu").height($(".dushu").width() / 3 * 2);
				$(".sun").height($(".sun").width() * 0.3215);
				$(".sun ul li img").height($(".sun ul li img").width());
				$(".dushu ul li img").height($(".dushu ul li").height() * 0.3);
				$(".dushu ul li p").css("line-height",
						$(".dushu ul li p").height() * 1.2 + 'px');
				$(".dushu ul li .p1").css("line-height",
						$(".dushu ul li .p1").height() * 0.8 + 'px');

			});
</script>
</html>