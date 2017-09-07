<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div style="width: 1400px; margin-left: auto; margin-right: auto;">
		<ul class="tab" style="margin-left: auto; margin-right: auto;">
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, '最佳')">最佳</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, '集成')">集成</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, 'American')">美国预报</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, 'T7Online')">天气在线</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, '中国天气网')">中国天气网</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, '中央指导')">中央指导</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, 'EC')">EC</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, 'T639')">T639</a></li>
			<li><a href="javascript:void(0)" class="tablinks"
				onclick="openCity(event, 'Grapes')">Grapes</a></li>

		</ul>

		<div id="最佳" class="tab1">
			<div id="最佳map" style="width: 100%; height: 700px;"
				style="display: none;"></div>
		</div>

		<script type="text/javascript">
			// 最佳预报
		    var map最佳 = new BMap.Map('最佳map') ;
		    var poi = new BMap.Point(107.9, 40.9);
		    map最佳.centerAndZoom(poi, 9);
		    map最佳.enableScrollWheelZoom();
		    map最佳.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		    map最佳.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
		    map最佳.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
		    map最佳.disableScrollWheelZoom()                        //禁止滚轮放大缩小
			var opt ={offset: new BMap.Size(45, 50)};                
			map最佳.addControl(new BMap.MapTypeControl(opt));          //添加地图类型控件
		    var menu = new BMap.ContextMenu();  //添加鼠标右键菜单
		    var txtMenuItem = [
				  {
				      text: '放大',
				      callback: function () { map.zoomIn() }
				  },
				  {
				      text: '缩小',
				      callback: function () { map.zoomOut() }
				  },
				  {
				      text: '清除所有已圈图形',
				      callback: function () { clearAll() }
				  }
			 ];


		    for (var i = 0; i < txtMenuItem.length; i++) {
		        menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
		    }

		    map最佳.addContextMenu(menu);
    		
    		</script>

		<c:forEach items="${bestForecasts}" var="weather" varStatus="s">
			<script>
	    		var point1${weather.forecastType}${weather.station.stationName} = new BMap.Point(${weather.station.longitude},${weather.station.latitude});
	    		var opts1${weather.station.stationName} = {
	    				  position : point1${weather.forecastType}${weather.station.stationName},    // 指定文本标注所在的地理位置
	    				  offset   : new BMap.Size(-15, -20)    //设置文本偏移量
	    				}
	    		
	    		var myIcon = new BMap.Icon("../style/img/map/1${weather.skyDay}.png", new BMap.Size(30,30));
	    		var marker1${weather.forecastType}${weather.station.stationName} = new BMap.Marker(point1${weather.forecastType}${weather.station.stationName},{icon:myIcon}); 
	    		map最佳.addOverlay(marker1${weather.forecastType}${weather.station.stationName});  
	    		
				var label = new BMap.Label("${weather.maxTemp}～${weather.minTemp}", opts1${weather.station.stationName}); 
					label.setStyle({
						 fontSize : "17px",
						 color : "#1010DE",
						 height : "20px",
						 lineHeight : "20px",
						 fontFamily:"方正准圆",
			          	 borderStyle:"none",
			             backgroundColor:"none"
					 });
				marker1${weather.forecastType}${weather.station.stationName}.setLabel(label);
				var eventOpts1${weather.forecastType}${weather.station.stationName} = {
						  width : 140,     // 信息窗口宽度
						  height: 140,     // 信息窗口高度
						}
				var infoWindow1${weather.station.stationName} =new BMap.InfoWindow("<div style='font-size:110%;text-align:left;color:#2B2B2B;padding-left:20px;'>${weather.station.stationName}<br>模式：${weather.forecastType}<br>天空状况：${weather.skyDay}<br>气温：${weather.minTemp}～${weather.maxTemp}℃<br>风向：${weather.windDirectionDay}<br>风力：${weather.windVelocityDay}级</div>", eventOpts1${weather.forecastType}${weather.station.stationName}); // 创建信息窗口对象 
				marker1${weather.forecastType}${weather.station.stationName}.addEventListener("click", function(){          
					map最佳.openInfoWindow(infoWindow1${weather.station.stationName}, point1${weather.forecastType}${weather.station.stationName}); //开启信息窗口
				});
					
			</script>
		</c:forEach>


		<c:forEach items="${everyTypeForecasts}" var="forecast" varStatus="s">


			<div id="${forecast.key}" class="tab1">
				<div id="${forecast.key}map" style="width: 100%; height: 700px;"></div>
			</div>



			<script type="text/javascript">
			// 百度地图API功能
		    var map${forecast.key} = new BMap.Map('${forecast.key}map') ;
		    var poi = new BMap.Point(107.9, 40.9);
		    map${forecast.key}.centerAndZoom(poi, 9);
		    map${forecast.key}.enableScrollWheelZoom();
		    map${forecast.key}.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		    map${forecast.key}.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
		    map${forecast.key}.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
		    map${forecast.key}.disableScrollWheelZoom()                        //禁止滚轮放大缩小
			var opt ={offset: new BMap.Size(45, 50)};                
			map${forecast.key}.addControl(new BMap.MapTypeControl(opt));          //添加地图类型控件
		    var menu = new BMap.ContextMenu();  //添加鼠标右键菜单
		    var txtMenuItem = [
				  {
				      text: '放大',
				      callback: function () { map.zoomIn() }
				  },
				  {
				      text: '缩小',
				      callback: function () { map.zoomOut() }
				  },
				  {
				      text: '清除所有已圈图形',
				      callback: function () { clearAll() }
				  }
			 ];


		    for (var i = 0; i < txtMenuItem.length; i++) {
		        menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
		    }

		    map${forecast.key}.addContextMenu(menu);
    		

    		</script>
			<c:forEach items="${forecast.value}" var="weather">
				<script>
	    		var point${forecast.key}${weather.station.stationName} = new BMap.Point(${weather.station.longitude},${weather.station.latitude});
	    		var opts = {
	    				  position : point${forecast.key}${weather.station.stationName},    // 指定文本标注所在的地理位置
	    				  offset   : new BMap.Size(-15, -20)    //设置文本偏移量
	    				}
	    		
	    		var myIcon = new BMap.Icon("../style/img/map/1${weather.skyDay}.png", new BMap.Size(30,30));
	    		var marker${forecast.key}${weather.station.stationName} = new BMap.Marker(point${forecast.key}${weather.station.stationName},{icon:myIcon}); 
	    		map${forecast.key}.addOverlay(marker${forecast.key}${weather.station.stationName});  
	    		
				var label = new BMap.Label("${weather.maxTemp}～${weather.minTemp}", opts); 
					label.setStyle({
						 fontSize : "17px",
						 color : "#1010DE",
						 height : "20px",
						 lineHeight : "20px",
						 fontFamily:"方正准圆",
			          	 borderStyle:"none",
			             backgroundColor:"none"
					 });
					marker${forecast.key}${weather.station.stationName}.setLabel(label);
					
					var opts${forecast.key}${weather.station.stationName} = {
							  width : 140,     // 信息窗口宽度
							  height: 140,     // 信息窗口高度
							}
							var infoWindow${forecast.key}${weather.station.stationName} = new BMap.InfoWindow("<div style='font-size:110%;text-align:left;color:#2B2B2B;padding-left:20px;'>${weather.station.stationName}<br>模式：${weather.forecastType}<br>天空状况：${weather.skyDay}<br>气温：${weather.minTemp}～${weather.maxTemp}℃<br>风向：${weather.windDirectionDay}<br>风力：${weather.windVelocityDay}级</div>", opts${forecast.key}${weather.station.stationName});  // 创建信息窗口对象 
							marker${forecast.key}${weather.station.stationName}.addEventListener("click", function(){          
								map${forecast.key}.openInfoWindow(infoWindow${forecast.key}${weather.station.stationName},point${forecast.key}${weather.station.stationName}); //开启信息窗口
							});
					</script>
			</c:forEach>
		</c:forEach>
	</div>

</body>
</html>