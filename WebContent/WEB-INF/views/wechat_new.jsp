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
	style="width: 700px; text-align: center; margin-left: auto; margin-right: auto;">

	<div class="left fl">
		<div class="c7d">
			<ul class="t clearfix">
				<c:forEach items="${forecasts }" var="forecast" varStatus="s">

					<li class="sky  lv1">
						<p class = "wea">${forecast.station.stationName }</p> 
						<p title="晴" class="wea">${forecast.skyDay }</p>
						<p class="tem">
							<span>${forecast.minTemp}到${forecast.maxTemp}<i>℃</i>
						</p>
						<p class="win">
							<em> </em> <i>${forecast.windDirectionDay}</i>
						</p>
						<div class="slid"></div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>














