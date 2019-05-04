<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="./style/js/jquery-1.9.1.min.js"></script>
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
			$.post("./scssYYWeather", {
				name : $(this).children("h2:first").text(),
				text : $(this).children("p:first").html()
			})
		});

		$("#infoContent").fadeToggle(3000);
		$("#infoContent").delay(3000).slideUp(300);
	});

	$("#btnClose").click(function() {
		$("#infoContent").hide();
	});

	$("input[name='name']").keyup(
			function() {

				var val = $("input[name='name']").val();
				if (val.length > 0) { //当文本内容不为空时进行异步检索
					$.post("./getYuyin", {
						pinyin : val
					}, function(data, status) {
						if (status == "success") { //当接收服务器端数据成功时

							var tipHtml = ""; //拼接html标签
							var tipText = data; //进行解析json数据
							if (tipText.length <= 0) {
								$('#tip').hide();
								return;
							} else {
								$('#tip').show();
							}
							for (var i = 0; i < tipText.length; i++) {
								tipHtml += "<li>" + tipText[i] + "</li>";
							}
							//获得输入姓名文本框的宽度
							var width = parseInt($('input[name="name"]')
									.width());
							//设置ul宽度和文本框的宽度相等
							$("#tip").html(tipHtml).width(width);
							$("#tip").css("position", "relative").css("left",
									110).css("list-style-type", "none");
							$("#tip li").click(function() {
								$("#tip").hide();
								$("input[name='name']").val($(this).text());
							});
						}
					});
				}
			});
</script>


<div>
	<p
		style="margin: 0; padding: 20px; font-size: 30px; text-align: center"
		class="bg-success">12121实况更新</p>

</div>
<div style="padding: 10px 100px 10px; width: 50%">
	<form class="bs-example bs-example-form" role="form">
		<div class="input-group input-group-lg">
			<span class="input-group-addon">检索词条</span> <input id="inputName"
				name="name" type="text" class="form-control" placeholder="请输入拼音首字母">
		</div>

		<ul id="tip"
			style="margin: 0; padding: 0; color: rgba(222, 131, 17, 0.92); font-size: 17px;"></ul>
	</form>
</div>

<div id="infoContent" class="alert alert-info"
	style="width: 80%; margin: 10px; display: none; margin-left: auto; margin-right: auto; text-align: center; font-size: 20px">
	内容提交成功
	<button type="button" class="close" id="btnClose">
		<span>&times;</span>
	</button>
</div>



<c:forEach items="${weathers}" var="weather">

	<div class="col-md-2 column ui-sortable"
		style="margin: 1px 20px 20px; height: 690px; width: 360px; overflow: auto;">
		<div class="box box-element">
			<div class="view tijiao">
				<h2 contenteditable="false" style="text-align: center">${weather.key}</h2>
				<p style="font-size: 17px; margin: 15px" contenteditable="false">${weather.value}
				</p>
				<p>
					<a class="btn btn1" style="font-size: 20px" contenteditable="false">修改
					</a>
				</p>
			</div>
		</div>
	</div>

</c:forEach>


<div
	style="margin-top: 900px; width: 100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto;">
	<a style="width: 100px; font-size: 20px; margin: 10px" id="success"
		href="#" class="btn btn-info btn-lg"> <span
		class="glyphicon glyphicon-ok"></span> 提交
	</a>
</div>









