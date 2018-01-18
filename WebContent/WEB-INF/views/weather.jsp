<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

<style>

/* Style the list */
ul.tab {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	border: 1px solid #eee;
	background-color: #2B91D5;
}

.layer {
	position: fixed;
	right: 70px;
	top: 50%;
	margin-top: -97px;
	z-index: 10000;
}

.layer ul li a {
	display: inline-block;
	width: 80px;
	height: 45px;
	text-align: center;
	line-height: 48px;
	background-color: #fff;
	color: #00ace9;
	position: relative;
	-webkit-transition: all .2s;
	transition: all .2s;
	font-size: 17px;
}

/* Float the list items side by side */
ul.tab li {
	float: left;
}

/* Style the links inside the list items */
ul.tab li a {
	display: inline-block;
	color: black;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	transition: 0.6s;
	font-size: 17px;
	font-family: 方正准圆;
}

/* Change background color of links on hover */
ul.tab li a:hover {
	background-color: #FFCF88;
}

/* Create an active/current tablink class */
ul.tab li a:focus, .active {
	background-color: #FFCF88;
}

/* Style the tab content */
.tabcontent {
	display: none;
	padding: 10px 12px;
	border-top: none;
}
</style>

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=sA0bSnULTPEEtWYDd8AgSHWT"></script>

<div style="width: 100%; margin-left: auto; margin-right: auto;">
	<ul class="tab" style="margin-left: auto; margin-right: auto;">
		<li><a id="isRead" href="javascript:void(0)" class="tablinks"
			onclick="openCity(event, 'forecastDiv')">天气预报</a></li>
		<li><a href="javascript:void(0)" class="tablinks"
			onclick="openCity(event, 'weatherDiv')">天气实况</a></li>

		<a
			style="margin-right: 10px; width: 200px; height: 54px; float: right; overflow: hidden; font-size: 22px; "
			href="./reload" class="btn btn-large btn-block btn-warning">更新预报</a>
	</ul>



	<div id="forecastDiv" class="tab1 forecastDiv">
		<div id="forecastMap" style="width: 100%; height: 800px;"></div>
	</div>

	<script type="text/javascript">
			// 预报
		    var mapf = new BMap.Map('forecastMap') ;
		    var poi = new BMap.Point(107.9, 40.9);
		    mapf.centerAndZoom(poi,9);
		    mapf.enableScrollWheelZoom();
		    mapf.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		    mapf.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
		    mapf.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
		    mapf.disableScrollWheelZoom()                        //禁止滚轮放大缩小
			var opt ={offset: new BMap.Size(45, 50)};                
			mapf.addControl(new BMap.MapTypeControl(opt));          //添加地图类型控件
		   
    		
    		</script>
	<c:forEach items="${forecasts}" var="forecast" varStatus="s">
		<script>
	    		var point1${forecast.station.stationName} = new BMap.Point(${forecast.station.longitude},${forecast.station.latitude});
	    		var opts1${forecast.station.stationName} = {
	    				  position : point1${forecast.station.stationName},    // 指定文本标注所在的地理位置
	    				  offset   : new BMap.Size(-15, -20)    //设置文本偏移量
	    				}
	    		
	    		var myIcon = new BMap.Icon("./style/img/map/1${forecast.rainDay}.png", new BMap.Size(30,30));
	    		var marker1${forecast.station.stationName} = new BMap.Marker(point1${forecast.station.stationName},{icon:myIcon}); 
	    		mapf.addOverlay(marker1${forecast.station.stationName});  
	    		
				var label = new BMap.Label("${forecast.minTemp}～${forecast.maxTemp}℃", opts1${forecast.station.stationName}); 
					label.setStyle({
						 fontSize : "17px",
						 color : "#1010DE",
						 height : "20px",
						 lineHeight : "20px",
						 fontFamily:"方正准圆",
			          	 borderStyle:"none",
			             backgroundColor:"none"
					 });
				marker1${forecast.station.stationName}.setLabel(label);
				var eventOpts1${forecast.station.stationName} = {
						  width : 120,     // 信息窗口宽度
						  height: 120,     // 信息窗口高度
						}
				var infoWindow1${forecast.station.stationName} =new BMap.InfoWindow("<div style='font-size:110%;text-align:left;color:#2B2B2B;padding-left:20px;'><span style='color:#00E5EE;font-size:18px;‘=''>${forecast.station.stationName}</span><br>更新时间：${forecast.hour}时<br>最高气温：${forecast.maxTemp}℃<br>最低气温：${forecast.minTemp}℃<br>天空状况：${forecast.skyDay}", eventOpts1${forecast.station.stationName}); // 创建信息窗口对象 
				marker1${forecast.station.stationName}.addEventListener("click", function(){          
					mapf.openInfoWindow(infoWindow1${forecast.station.stationName}, point1${forecast.station.stationName}); //开启信息窗口
				});
					
			</script>
	</c:forEach>
	<br>
	<div id="weatherDiv" class="tab1 weatherDiv">
		<div id="weatherMap" style="width: 100%; height: 800px;"></div>
	</div>



	<script type="text/javascript">
			// 实况
		    var map121 = new BMap.Map('weatherMap') ;
		    var poi = new BMap.Point(107.9, 40.9);
		    map121.centerAndZoom(poi, 9);
		    map121.enableScrollWheelZoom();
		    map121.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		    map121.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
		    map121.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
		    map121.disableScrollWheelZoom()                        //禁止滚轮放大缩小
			var opt ={offset: new BMap.Size(45, 50)};                
			map121.addControl(new BMap.MapTypeControl(opt));          //添加地图类型控件
		    
    		
    		</script>
	<c:forEach items="${weathers}" var="weather" varStatus="s">
		<script>
	    		var point1${weather.station.stationName} = new BMap.Point(${weather.station.longitude},${weather.station.latitude});
	    		var opts1${weather.station.stationName} = {
	    				  position : point1${weather.station.stationName},    // 指定文本标注所在的地理位置
	    				  offset   : new BMap.Size(-15, -20)    //设置文本偏移量
	    				}
	    		
	    		var myIcon = new BMap.Icon("./style/img/map/1晴间少云.png", new BMap.Size(30,30));
	    		var marker${weather.station.stationName} = new BMap.Marker(point1${weather.station.stationName},{icon:myIcon}); 
	    		map121.addOverlay(marker${weather.station.stationName});
	    		
				var label = new BMap.Label("${weather.tem/10}℃", opts1${weather.station.stationName}); 
					label.setStyle({
						 fontSize : "17px",
						 color : "#1010DE",
						 height : "20px",
						 lineHeight : "20px",
						 fontFamily:"方正准圆",
			          	 borderStyle:"none",
			             backgroundColor:"none"
					 });
				marker${weather.station.stationName}.setLabel(label);
				var eventOpts1${weather.station.stationName} = {
						  width : 140,     // 信息窗口宽度
						  height: 160,     // 信息窗口高度
						}
				var infoWindow2${weather.station.stationName} =new BMap.InfoWindow("<div style='font-size:110%;text-align:left;color:#2B2B2B;padding-left:20px;'><span style='color:#00E5EE;font-size:18px;‘=''>${weather.station.stationName}</span><br>时间：${weather.hour}点<br>气温：${weather.tem/10}℃<br>最高/最低气温：${weather.maxTemp/10}～${weather.minTemp/10}℃<br>地表温度：${weather.surfaceTemp/10}℃<br>相对湿度：${weather.relativeHumidity/1}%<br>风速：${weather.windVelocity2min/10}m/s</div>", eventOpts1${weather.station.stationName}); // 创建信息窗口对象 
				marker${weather.station.stationName}.addEventListener("click", function(){          
					map121.openInfoWindow(infoWindow2${weather.station.stationName}, point1${weather.station.stationName}); //开启信息窗口
				});
					
			</script>
	</c:forEach>



	<div id="weatherDiv" class="tab1 weatherDiv"
		style="width: 100%; align: center;">
		<ul class="tab"
			style="margin-left: auto; margin-right: auto; align: center">
			<li><a id="lh" href="javascript:void(0)" class="shikuang active"
				onclick="changeStation(event, '临河')">临河</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '磴口')">磴口</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '杭锦后旗')">杭锦后旗</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '五原')">五原</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '前旗')">乌拉特前旗</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '中旗')">乌拉特中旗</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '后旗')">乌拉特后旗</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '大佘太')">大佘太</a></li>
			<li><a href="javascript:void(0)" class="shikuang"
				onclick="changeStation(event, '海力素')">海力素</a></li>
		</ul>
	</div>

	<div id="forecastDiv" class="tab1 forecastDiv"
		style="width: 100%; text-align: center;">
		<br> <br>
		<p>
		<h4>${lines }</h4>
		</p>
	</div>


	<br> <br>

	<div class="tab1 weatherDiv">
		<c:forEach items="${weather24}" var="entry">
			<div class="clear"></div>
			<div id="${entry.key}weather24" class='24hour'
				style="width: 1200px; height: 400px; margin: 0 auto;"></div>
			<script language="JavaScript">
					$(function() { 
						
						var title = {
					      text: '${entry.key}过去24小时温度变化'   
					   };
					   var subtitle = {
					      text: ''
					   };
					   var xAxis = {
					      categories: [ <c:forEach items="${entry.value}" var="en">
					         ${en.hour}, </c:forEach>]
					   };
					   var yAxis = {
					      title: {
					         text: 'Temperature (\xB0C)'
					      },
					      labels: {
					          formatter: function () {
					             return this.value + '\xB0';
					          }
					       },
					       lineWidth: 2
					   };
					   var plotOptions = {
					      line: {
					         dataLabels: {
					            enabled: true
					         },   
					         enableMouseTracking: true
					      },
					      spline: {
					          marker: {
					             radius: 4,
					             lineWidth: 1
					          }
					       }
					   };
					   var tooltip = {
							      crosshairs: true,
							      shared: true,
							      valueSuffix: '\xB0C'
							   };
					   var series= [
						         
						      {
							     name: '最高气温',
							     data:[ <c:forEach items="${entry.value}" var="en">
							     ${en.maxTemp/10}, </c:forEach>],
							     
							  },
							  {
							         name: '整点气温',
							         data:[ <c:forEach items="${entry.value}" var="en">
							         ${en.tem/10}, </c:forEach>],
							         
							      },
							  {
								 name: '最低气温',
								 data:[ <c:forEach items="${entry.value}" var="en">
								 ${en.minTemp/10}, </c:forEach>],
								 
							  },
					   ];
					   
					   var json = {};
					   json.title = title;
					   json.subtitle = subtitle;
					   json.tooltip = tooltip;
					   json.xAxis = xAxis;
					   json.yAxis = yAxis;  
					   json.series = series;
					   json.plotOptions = plotOptions;
					   $('#${entry.key}weather24').highcharts(json);
					});
					</script>
		</c:forEach>
	</div>

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
			        tablinks[i].className = tablinks[i].className.replace(" active", "");
			    }
			
			    // Show the current tab, and add an "active" class to the link that opened the tab
			    
			    var cls = '.' + cityName;
			    $(cls).show();
			    changeStation(event, "临河");
			    
			    $("#lh").addClass("active");
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
			        tablinks[i].className = tablinks[i].className.replace(" active", "");
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
			$(".weatherDiv").hide();
			$(".24hour").hide();
		});
		</script>