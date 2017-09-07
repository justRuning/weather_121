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
		class="bg-success"></p>

</div>


<div style="margin: 50px 30%">

	<p style='text-align: center'>
		<strong style='font-size: 36.0pt; font-family: 华文行楷; color: #FF0000'>生活指数预报发布单</strong><strong
			style='font-size: 36.0pt; font-family: 华文行楷; color: #FF0000'></strong>
	</p>
	<p style='text-indent: 23.6pt'>
		<strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>巴彦淖尔市气象灾害防御中心</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'><SPAN
			style='mso-spacerun: yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</SPAN></strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>签发：</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>夏成</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'></strong>
	</p>
	<p style='text-indent: 23.6pt'>
		<strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>${date }</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>制作</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'><SPAN
			style='mso-spacerun: yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</SPAN></strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'> </strong>

		<strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'></strong><SPAN
			style='mso-spacerun: yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</SPAN></strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>
		</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>
		</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'><SPAN
			style='mso-spacerun: yes'>&nbsp; </SPAN></strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>
		</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'><SPAN
			style='mso-spacerun: yes'>&nbsp; </SPAN></strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'>
		</strong><strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #000000'></strong>
	</p>
	<p style='text-align: center'>
		<strong
			style='font-size: 12.0pt; font-family: 仿宋_GB2312; color: #FF0000'>---------------------------------------------------------------------------------------</strong>
	</p>
	<p style='text-align: center'>
		<strong
			style='font-size: 20.0pt; font-family: 仿宋_GB2312; color: #000000'>巴市主要城市生活指数预报</strong><strong
			style='font-size: 20.0pt; font-family: 仿宋_GB2312; color: #000000'></strong>
	</p>
	<br>
	<c:forEach items="${indexForecast}" var="forecast">
		<h3 style="margin: 10px 30px" class="bs-docs-featurette-title">${forecast.key}</h3>
		<p style="margin: 10px 30px" class="lead">${forecast.value }</p>


	</c:forEach>

</div>


<div
	style="margin-top: 20px; width: 100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
	<a style="width: 150px; font-size: 20px; margin: 10px" id="success"
		href="#" class="btn btn-info btn-lg"> <span
		class="glyphicon glyphicon-ok"></span> 邮件发送
	</a>
</div>











