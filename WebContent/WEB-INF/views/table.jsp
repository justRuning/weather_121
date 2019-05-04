<%@page import="com.hebj.forecast.entity.Forecast"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



<title>集合预报—表格显示</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="../style/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="../style/css/wdate.css">
<link rel="stylesheet" href="../style/css/main.css">
<script type="text/javascript" src="../style/js/WdatePicker.js"></script>
<script type="text/javascript" src="../style/js/jquery-1.9.1.min.js"></script>



</head>
<body>

	<div class="container" style="width: 80%">

		<div class="row text-center">
			<h1>集合预报——表格显示</h1>
			<br>
		</div>
		<br>
		<form class="form-inline" align="center">
			<div class="form-group">
				<label for="time">选择日期：</label> <input class="form-control"
					type="date" value="2017-04-10" id="time" name="time">

			</div>
			<div class="form-group">
				<label for="station">选择站点：</label> <select class="form-control"
					id="station" name="station">
					<option value="临河">临河</option>
					<option value="磴口">磴口</option>
					<option value="杭锦后旗">杭锦后旗</option>
					<option value="五原">五原</option>
					<option value="乌前旗">乌前旗</option>
					<option value="乌中旗">乌中旗</option>
					<option value="乌后旗">乌后旗</option>
				</select>
			</div>
			<div class="form-group">
				<label for="hour">选择时次：</label> <select class="form-control"
					id="hour" name="hour">
					<option value="08">08</option>
					<option value="20">20</option>
				</select>
			</div>
			<div class="form-group" margin="10px">
				<button type="submit" class="btn btn-primary" margin="10px"
					font-size="20px">查询</button>
			</div>

		</form>
		<br>
		<script>
			$(document).ready(function() {
				$("#time").attr("value", "${time}");
				$("option[value=${hour}]").attr("selected", "selected");
				$("option[value=${station}]").attr("selected", "selected");
			});
		</script>

		<table class="table table-bordered table-striped table-condensed">
			<thead>
				<tr>
					<th class="text-center" colspan="2" rowspan="2"></th>
					<th class="text-center" colspan="2">-168</th>
					<th class="text-center" colspan="2">-144</th>
					<th class="text-center" colspan="2">-120</th>
					<th class="text-center" colspan="2">-96</th>
					<th class="text-center" colspan="2">-72</th>
					<th class="text-center" colspan="2">-48</th>
					<th class="text-center" colspan="2">-24</th>
					<th class="text-center" colspan="2">3天平均</th>
					<th class="text-center" colspan="2">7天平均</th>
					<th class="text-center" colspan="2">24</th>
					<th class="text-center" colspan="2">48</th>
					<th class="text-center" colspan="2">72</th>
					<th class="text-center" colspan="2">96</th>
					<th class="text-center" colspan="2">120</th>
					<th class="text-center" colspan="2">144</th>
					<th class="text-center" colspan="2">168</th>
				</tr>
				<tr>
					<%!int i;%>
					<%
						for (i = 1; i <= 16; i++) {
					%>
					<th class="text-center">MAX</th>
					<th class="text-center">MIN</th>
					<%
						}
					%>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="text-center" colspan="2">实况温度</td>
					<c:forEach items="${weathers}" var="weather">
						<td class="text-center">${weather.maxTemp24/10}</td>
						<td class="text-center">${weather.minTemp24/10}</td>
					</c:forEach>
				</tr>
				<c:forEach items="${everyTypeForecasts}" var="forecasts">
					<tr>
						<td class="text-center text-middle" colspan="2" rowspan="2">${forecasts.key}</td>
						<c:forEach items="${forecasts.value}" var="forecast">
							<td class="text-center"><c:choose>
									<c:when test="${forecast.maxTemp==null}">
									--
								</c:when>
									<c:otherwise>
									${forecast.maxTemp }
								</c:otherwise>
								</c:choose></td>
							<td class="text-center"><c:choose>
									<c:when test="${forecast.minTemp==null}">
									--
								</c:when>

									<c:otherwise>
									${forecast.minTemp }
								</c:otherwise>
								</c:choose></td>
						</c:forEach>
					</tr>
					<tr>
						<c:forEach items="${max[forecasts.key]}" var="forecast"
							varStatus="s">
							<td class="text-center"><c:choose>
									<c:when test="${forecast==null}">
									--
								</c:when>
									<c:otherwise>
									${forecast}
								</c:otherwise>
								</c:choose></td>
							<td class="text-center"><c:choose>
									<c:when test="${min[forecasts.key][s.index]==null}">
									--
								</c:when>
									<c:otherwise>
									${min[forecasts.key][s.index]}
								</c:otherwise>
								</c:choose></td>
							</td>
						</c:forEach>
						<c:forEach items="${forecasts.value}" var="forecast" varStatus="s">

							<c:choose>
								<c:when test="${s.index>8 }">
									<td class="text-center"><c:choose>
											<c:when test="${forecast.maxTempRevised==null}">
									--
								</c:when>
											<c:otherwise>
									${forecast.maxTempRevised }
								</c:otherwise>
										</c:choose></td>
									<td class="text-center"><c:choose>
											<c:when test="${forecast.minTempRevised==null}">
									--
								</c:when>

											<c:otherwise>
									${forecast.minTempRevised }
								</c:otherwise>
										</c:choose></td>

								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</c:forEach>



			</tbody>
		</table>

		
	</div>
</body>
</html>