<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/exporting.js"></script>

<title>Insert title here</title>
</head>
<body>
	<div id="container" style="min-width: 700px; height: 400px"></div>

</body>
</html>


<script>
	
	
	$(function() {
		$('#container')
				.highcharts(
						{
							title : {
								text : 'Monthly Average Temperature',
								x : -20
							//center
							},
							subtitle : {
								text : 'Source: WorldClimate.com',
								x : -20
							},
							xAxis : {
								categories : ""
							},
							yAxis : {
								title : {
									text : 'Temperature (°C)'
								},
								plotLines : [ {
									value : 0,
									width : 1,
									color : '#808080'
								} ]
							},
							tooltip : {
								valueSuffix : '°C'
							},
							legend : {
								layout : 'vertical',
								align : 'right',
								verticalAlign : 'middle',
								borderWidth : 0
							},
							series : [
									{
										name : 'Tokyo',
										data : ${highTems}
									},
									{
										name : 'New York',
										data : [ -0.2, 0.8, 5.7, 11.3, 17.0,
												22.0, 24.8, 24.1, 20.1, 14.1,
												8.6, 2.5 ]
									},
									{
										name : 'Berlin',
										data : [ -0.9, 0.6, 3.5, 8.4, 13.5,
												17.0, 18.6, 17.9, 14.3, 9.0,
												3.9, 1.0 ]
									},
									{
										name : 'London',
										data : [ 3.9, 4.2, 5.7, 8.5, 11.9,
												15.2, 17.0, 16.6, 14.2, 10.3,
												6.6, 4.8 ]
									} ]
						});
	});
</script>