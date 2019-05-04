<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	<p
		style="margin: 0; padding: 20px; font-size: 30px; text-align: center"
		class="bg-success">微信专题制作预报图片</p>

</div>
<br>
<br>
<br>
<div
	style="width: 450px; text-align: center; margin-left: auto; margin-right: auto;">

	<table
		class="table table-bordered table-hover table-condensed table-striped text-center">
		<c:forEach items="${forecasts }" var="forecast" varStatus="s">
			<c:if test="${s.index%7==0}">
				<thead
					style="background-color: rgb(91, 154, 215); text-align: center">
					<tr>
						<th>${times[s.index/7] }</th>
						<th>天气</th>
						<th>气温（℃）</th>
						<th>风向风力</th>
					</tr>
				</thead>
			</c:if>

			<tbody>
				<c:if test="${s.index%2==1}">
					<tr style="background-color: rgb(221, 235, 246)">
				</c:if>
				<c:if test="${s.index%2==0}">
					<tr style="background-color: rgb(255, 254, 255)">
				</c:if>
				<th>${forecast.station.stationName }</th>
				<th>${forecast.skyDay}</th>
				<th>${forecast.minTemp}到${forecast.maxTemp}</th>
				<th>${forecast.windDirectionDay}</th>
				</tr>
			</tbody>

		</c:forEach>
	</table>
</div>