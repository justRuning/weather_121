<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script>
	$('.btn1').click(function() {

		if ($(this).text().indexOf("修改") >= 0) {
			$(this).parent().siblings().attr("contenteditable", "true");
			$(this).text("完成");
		} else {
			$(this).text("修改");
			$(this).parent().siblings().attr("contenteditable", "false");
		}
	});

	$("#success").click(function() {

		$(".tijiao").each(function() {
			$.post("./scssForecastMsg", {
				name : $(this).children("h2:first").text(),
				text : $(this).children("p:first").html()
			});
		});

		$("#infoContent").fadeToggle(3000);
		$("#infoContent").delay(3000).slideUp(300);
	});

	$("#btnClose").click(function() {
		$("#infoContent").hide();
	});
</script>


<div>
	<p
		style="margin: 0; padding: 20px; font-size: 30px; text-align: center"
		class="bg-success">下午短信制作</p>

</div>

<div id="infoContent" class="alert alert-info"
	style="width: 80%; margin: 10px; display: none; margin-left: auto; margin-right: auto; text-align: center; font-size: 20px">
	内容提交成功
	<button id="btnClose" type="button" class="close">
		<span>&times;</span>
	</button>
</div>


<c:forEach items="${forecasts}" var="forecast">

	<div class="col-md-2 column ui-sortable"
		style="margin: 15px; height: 280px; width: 300px; overflow: auto;">
		<div class="box box-element">
			<div class="view tijiao">
				<h2 contenteditable="false" style="text-align: center">${forecast.key}</h2>
				<p style="font-size: 17px; margin: 15px" contenteditable="false">${forecast.value}
				</p>
				<p>
					<a class="btn btn1" contenteditable="false">修改</a>
				</p>
			</div>
		</div>
	</div>

</c:forEach>

<div
	style="margin-top: 600px; width: 100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
	<a style="width: 100px; font-size: 20px; margin: 10px" id="success"
		href="#" class="btn btn-info btn-lg"> <span
		class="glyphicon glyphicon-ok"></span> 提交
	</a>
</div>










