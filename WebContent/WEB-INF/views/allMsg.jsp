<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="./style/js/jquery-1.9.1.min.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script>
	$(document).ready(function() {
		$("#input").keyup();
	});

	$("#input").on('keyup', function(event) {

		var val = $(this).val();
		$(this).text(val);
		var len = val.length;
		var value = "";

		if (len <= 68) {
			value = "当前字数：" + len + " （一条短信）";
		}
		if (len > 68) {
			value = "当前字数：" + len + " （两条短信）";
		}
		$("#strCount").text(value)
	});

	$("#success").click(function() {

		$.post("./scssAllMsg", {
			content : $("#input").text()

		});

		$("#infoContent").fadeToggle(3000);
		$("#infoContent").delay(3000).slideUp(300);
	});

	$("#last").click(function() {
		$.post("./getAllMsg", {
			day : $("#day").text(),
			type : "last"
		}, function(data, status) {
			$("#day").text(data["day"]);
			$("#input").text(data["allMsg"]["content"])
		});

	});

	$("#next").click(function() {
		$.post("./getAllMsg", {
			day : $("#day").text(),
			type : "next"
		}, function(data, status) {
			$("#day").text(data["day"]);
			$("#input").text(data["allMsg"]["content"])
		});

	});

	$("#toDay").click(function() {
		$.post("./getAllMsg", {
			day : $("#day").text(),
			type : "toDay"
		}, function(data, status) {
			$("#day").text(data["day"]);
			$("#input").text(data["allMsg"]["content"])
		});

	});

	$("#btnClose").click(function() {
		$("#infoContent").hide();
	});
</script>
<div>
	<p
		style="margin: 0; padding: 20px; font-size: 30px; text-align: center"
		class="bg-success">下午全体职工短信</p>

</div>
<div id="infoContent" class="alert alert-info"
	style="width: 80%; margin: 10px; display: none; margin-left: auto; margin-right: auto; text-align: center; font-size: 20px">
	内容保存成功
	<button type="button" class="close" id="btnClose">
		<span>&times;</span>
	</button>
</div>
<br>
<br>
<br>
<div style="text-align: center;">
	<button style="font-size: 25px; margin: 30px;" id="last" type="button"
		class="btn btn-primary">前一天</button>
	<button style="font-size: 25px; margin: 30px;" id="toDay" type="button"
		class="btn btn-primary">今天</button>
	<button style="font-size: 25px; margin: 30px;" id="next" type="button"
		class="btn btn-primary">后一天</button>
</div>
<div
	style="text-align: center; margin-left: auto; margin-right: auto; width: 55%;">
	<div id="strCount" class="alert alert-info"
		style="text-align: center; font-size: 20px;"></div>
	<textarea id="input" class="form-control" rows="7"
		style="font-size: 20px; padding: 15px;">${allMsg.content }</textarea>
	<div id="day" style="display: none;">${day }</div>

</div>


<div
	style="margin-top: auto; width: 100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
	<a style="width: 100px; font-size: 20px; margin: 10px" id="success"
		href="#" class="btn btn-info btn-lg"> <span
		class="glyphicon glyphicon-ok"></span> 保存
	</a>
</div>









