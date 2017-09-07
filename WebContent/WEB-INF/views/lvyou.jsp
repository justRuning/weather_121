<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script>
	$('.btn1').click(function() {

		if ($(this).text() == "修改") {
			$(this).parent().siblings().attr("contenteditable", "true");
			$(this).text("完成");
		} else {
			$(this).text("修改");
			$(this).parent().siblings().attr("contenteditable", "false");
		}
	});

	$("#success").click(function() {

		$(".tijiao").each(function() {
			$.post("./scssYYWeather", {
				name : $(this).children("h2:first").text(),
				text : $(this).children("p:first").html()
			}, function(data, status) {

			});
		});
	});
</script>



<div>
	<p
		style="margin: 0; padding: 20px; font-size: 30px; text-align: center"
		class="bg-success">巴市主要旅游景点预报</p>

</div>
<div id="infoContent" class="alert alert-info"
	style="width: 60%; margin: 10px; display: none; margin-left: auto; margin-right: auto; text-align: center; font-size: 20px">
	内容保存成功
	<button type="button" class="close" id="btnClose">
		<span>&times;</span>
	</button>
</div>
<br>
<br>
<br>


<div
	style="width: 70%; text-align: center; margin-left: auto; margin-right: auto;">

	<table class="table table-bordered table-hover">
		<thead>
			<tr class="warning">
				<th>景区</th>
				<th>所属城市</th>
				<th>天空状况</th>
				<th>风向风力</th>
				<th>气温（℃）</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${forecasts }" var="forecast">
				<tr class="success">
					<th>${forecast.name }</th>
					<th>${forecast.city}</th>
					<th>${forecast.sky}</th>
					<th>${forecast.wind}</th>
					<th>${forecast.temp}</th>
				</tr>
			</c:forEach>
		</tbody>
	</table>



</div>


<div
	style="margin-top: 20px; width: 100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
	<a style="width: 150px; font-size: 20px; margin: 10px" id="success"
		href="#" class="btn btn-info btn-lg"> <span
		class="glyphicon glyphicon-ok"></span> 邮件发送
	</a>
</div>











