<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css"
	href="./style/bootstrap/css/bootstrap.min.css">
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=sA0bSnULTPEEtWYDd8AgSHWT"></script>
<script src="./style/js/jquery-1.9.1.min.js"></script>
<script src="./style/bootstrap//js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<!-- Theme style -->
<link rel="stylesheet" href="./style/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="./style/dist/css/skins/_all-skins.min.css">


<title>公共气象服务平台</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">



		<header class="main-header"> <a href="./index" class="logo">
			<!-- LOGO --> 公共气象服务平台
		</a> <nav class="navbar navbar-static-top content" role="navigation"
			style="text-align:right;font-size:20px;color:yellow;">预报更新时间：${time }
		</nav> </header>

		<aside class="main-sidebar" style="top:30px">
		<div class="slimScrollDiv"
			style="position: relative; overflow: hidden; width: auto; height: 600px;">
			<div class="sidebar" id="scrollspy"
				style="height: 600px; overflow: hidden; width: auto;">

				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="nav sidebar-menu" style="font-size: 19px">
					<li class="header"></li>
					<li id="weather" class="active"><a href="./index"><i
							class="fa fa-circle-o hbj"></i> 首页</a></li>
					<li id="zaojian" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 早间气象</a></li>
					<li id="yubao" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 下午短信</a></li>
					<li id="allMsg" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 下午全市预报</a></li>
					<li id="fire" class="hbj"><a><i class="fa fa-circle-o hbj"></i>
							森林防火</a></li>
					<li id="yy_shikuang" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 实况语音</a></li>
					<li id="yy_yubao" class="hbj"><a><i class="fa fa-circle-o"></i>
							预报语音</a></li>
					<li id="indexForecast" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 指数预报</a></li>
					<li id="lvyou" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 景区预报</a></li>
					<li id="wechat" class="hbj"><a><i
							class="fa fa-circle-o hbj"></i> 微信预报</a></li>



				</ul>
			</div>
		</div>
		</aside>

		<div class="content-wrapper" style="min-height: 1000px;">
			<div id="content" style="min-height: 1000px;"></div>


		</div>


		<footer class="main-footer">
		<div class="pull-right hidden-xs">
			<b>Version</b> 2.1.0
		</div>
		<strong>Copyright © 2016-future <a href="">HeBujiang</a>.
		</strong> All rights reserved. </footer>

	</div>

	<script>
		$('.hbj').click(function() {
			$(this).addClass("active").siblings().removeClass("active");
			$("#content").load($(this).attr("id"));
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#content").load("weather");
		});
	</script>

	<script type="text/javascript">
		function openCity(evt, cityName) {
			// Declare all variables
			var i, tabcontent, tablinks;

			// Get all elements with class="tabcontent" and hide them
			tabcontent = document.getElementsByClassName("tab1");
			for (i = 0; i < tabcontent.length; i++) {
				tabcontent[i].style.display = "none";
			}

			// Get all elements with class="tablinks" and remove the class "active"
			tablinks = document.getElementsByClassName("tablinks");
			for (i = 0; i < tablinks.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" active", "");
			}

			// Show the current tab, and add an "active" class to the link that opened the tab

			var cls = '.' + cityName;
			$(cls).show();
			evt.currentTarget.className += " active";
		}
	</script>

	<script type="text/javascript">
		function changeStation(evt, cityName) {
			// Declare all variables
			var i, tabcontent, tablinks;

			// Get all elements with class="tabcontent" and hide them
			tabcontent = document.getElementsByClassName("index");
			for (i = 0; i < tabcontent.length; i++) {
				tabcontent[i].style.display = "none";
			}

			// Get all elements with class="tablinks" and remove the class "active"
			tablinks = document.getElementsByClassName("shikuang");
			for (i = 0; i < tablinks.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" active", "");
			}

			// Show the current tab, and add an "active" class to the link that opened the tab

			evt.currentTarget.className += " active";

			var name = '#' + cityName + 'weather24';
			$('.24hour').hide();
			$(name).show();
		}
	</script>

	<script>
		$().ready(function() {
			$("#isRead").addClass("active");
			$(".forecastDiv").hide();
			$(".24hour").hide();
			$("#临河weather24").show();
		});
	</script>

</body>
</html>