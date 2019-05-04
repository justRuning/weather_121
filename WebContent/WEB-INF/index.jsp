<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
 <head> 
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"> 
  <title>临河-天气预报</title> 
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"> 
  <link rel="shortcut icon" href="http://image.nmc.cn/static2/favicon.ico"> 
  <link rel="stylesheet" type="text/css" href="/IntegratedForecast/WebContent/style/css/basic.css"> 
  <script src="http://image.nmc.cn/static2/jquery/jquery-1.9.1.min.js?v=2.0" type="text/javascript"></script> 
  <script src="http://image.nmc.cn/static2/site/nmc/themes/basic/js/doT.min.js?v=2.0" type="text/javascript"></script> 
  <script type="text/javascript">
    var ctx = '/f';
    var ctxStatic = 'http://image.nmc.cn/static2';
  </script> 
    <link rel="stylesheet" type="text/css" href="http://image.nmc.cn/static2/site/nmc/themes/basic/css/product_list.css?v=2.0"> 
    <link rel="stylesheet" type="text/css" href="/IntegratedForecast/WebContent/style/css/forecast.css"> 
  <style type="text/css">
		body{background: url('http://img.wallpaperfolder.com/f/4A59C0381614/art-space-earth-atmosphere-moons.jpg') no-repeat fixed center top;}
	</style> 
 </head> 
 <body> 
   <div class="container"> 
    <div class="btitle">
     最佳预报结果
     <span>发布于：2016-09-21 20:00</span>
    </div> 
    <div id="forecast" class="forecast"> 
     <div class="detail"> 
      <div class="today" style="margin-left:10px;"> 
       <table> 
        <tbody>
         <tr> 
          <td> <p class="dname">明 天</p><br><p>星期四</p> </td> 
          <td> 09月22日 </td> 
         </tr> 
         <tr> 
          <td><img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png"></td> 
          <td><img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/night/0.png"></td> 
         </tr> 
         <tr> 
          <td class="wdesc">晴</td> 
          <td class="wdesc">晴</td> 
         </tr> 
         <tr> 
          <td class="temp"> 28℃ </td> 
          <td class="temp"> 11℃ </td> 
         </tr> 
         <tr> 
          <td class="direct">西风</td> 
          <td class="direct">西风</td> 
         </tr> 
         <tr> 
          <td class="power">微风</td> 
          <td class="power">微风</td> 
         </tr> 
        </tbody>
       </table> 
      </div> 
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
     </div> 
     
    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>

    <div class="detail">
      <div class="day"> 
       <div class="date">
        明 天
       </div> 
       <div class="week">
        星期四
       </div> 
       <div class="wicon">
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div class="wdesc">
        晴
       </div> 
       <div class="temp">
         28℃ 
       </div> 
       <div class="direct">
        西风
       </div> 
       <div class="wind">
        微风
       </div> 
      </div> 
    </div>



     <div class="clear"></div> 
    </div> 
    <div id="hour3"> 
     <!-- 逐3小时天气 --> 
     <div id="day0" class="hour3"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        22日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        17.7℃ 
       </div> 
       <div>
        13.5℃ 
       </div> 
       <div>
        11.2℃ 
       </div> 
       <div>
        13.5℃ 
       </div> 
       <div>
        23.7℃ 
       </div> 
       <div>
        27.8℃ 
       </div> 
       <div>
        27.6℃ 
       </div> 
       <div>
        19.1℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        2.4米/秒
       </div> 
       <div>
        3米/秒
       </div> 
       <div>
        3.1米/秒
       </div> 
       <div>
        2.9米/秒
       </div> 
       <div>
        2米/秒
       </div> 
       <div>
        3.7米/秒
       </div> 
       <div>
        3.6米/秒
       </div> 
       <div>
        1.7米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
      </div> 
      <div class="row qy"> 
       <div class="label h3_qy">
        气压
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        45.1%
       </div> 
       <div>
        53.4%
       </div> 
       <div>
        54.5%
       </div> 
       <div>
        53%
       </div> 
       <div>
        35.5%
       </div> 
       <div>
        32%
       </div> 
       <div>
        29.6%
       </div> 
       <div>
        26.1%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        3.3%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        9.2%
       </div> 
      </div> 
      <div class="row njd"> 
       <div class="label h3_njd">
        能见度
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
     </div> 
     <!-- 逐3小时天气 --> 
     <!-- 逐3小时天气 --> 
     <div id="day1" class="hour3" style="display:none;"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        23日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        13.5℃ 
       </div> 
       <div>
        23.7℃ 
       </div> 
       <div>
        27.8℃ 
       </div> 
       <div>
        27.6℃ 
       </div> 
       <div>
        19.1℃ 
       </div> 
       <div>
        16.7℃ 
       </div> 
       <div>
        11.7℃ 
       </div> 
       <div>
        11.2℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        2.9米/秒
       </div> 
       <div>
        2米/秒
       </div> 
       <div>
        3.7米/秒
       </div> 
       <div>
        3.6米/秒
       </div> 
       <div>
        1.7米/秒
       </div> 
       <div>
        0.7米/秒
       </div> 
       <div>
        1.3米/秒
       </div> 
       <div>
        1.8米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        东南风
       </div> 
       <div>
        西南风
       </div> 
      </div> 
      <div class="row qy"> 
       <div class="label h3_qy">
        气压
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        53%
       </div> 
       <div>
        35.5%
       </div> 
       <div>
        32%
       </div> 
       <div>
        29.6%
       </div> 
       <div>
        26.1%
       </div> 
       <div>
        50%
       </div> 
       <div>
        59.9%
       </div> 
       <div>
        57.4%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        10%
       </div> 
       <div>
        3.3%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        9.2%
       </div> 
       <div>
        9.2%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
      </div> 
      <div class="row njd"> 
       <div class="label h3_njd">
        能见度
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
     </div> 
     <!-- 逐3小时天气 --> 
     <!-- 逐3小时天气 --> 
     <div id="day2" class="hour3" style="display:none;"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        24日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        14.4℃ 
       </div> 
       <div>
        23.9℃ 
       </div> 
       <div>
        27.8℃ 
       </div> 
       <div>
        26.7℃ 
       </div> 
       <div>
        19.9℃ 
       </div> 
       <div>
        15.6℃ 
       </div> 
       <div>
        11.8℃ 
       </div> 
       <div>
        8.6℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        2.9米/秒
       </div> 
       <div>
        3.1米/秒
       </div> 
       <div>
        3.3米/秒
       </div> 
       <div>
        4.3米/秒
       </div> 
       <div>
        2.9米/秒
       </div> 
       <div>
        2.2米/秒
       </div> 
       <div>
        2.1米/秒
       </div> 
       <div>
        2.1米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        西南风
       </div> 
      </div> 
      <div class="row qy"> 
       <div class="label h3_qy">
        气压
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        50.4%
       </div> 
       <div>
        35.8%
       </div> 
       <div>
        30.9%
       </div> 
       <div>
        30.9%
       </div> 
       <div>
        27.1%
       </div> 
       <div>
        58.6%
       </div> 
       <div>
        68.1%
       </div> 
       <div>
        71.3%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        1.4%
       </div> 
       <div>
        0%
       </div> 
       <div>
        0%
       </div> 
      </div> 
      <div class="row njd"> 
       <div class="label h3_njd">
        能见度
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
     </div> 
     <!-- 逐3小时天气 --> 
     <!-- 逐3小时天气 --> 
     <div id="day3" class="hour3" style="display:none;"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        25日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        8.2℃ 
       </div> 
       <div>
        22.8℃ 
       </div> 
       <div>
        26.1℃ 
       </div> 
       <div>
        26.8℃ 
       </div> 
       <div>
        15℃ 
       </div> 
       <div>
        14.7℃ 
       </div> 
       <div>
        10.2℃ 
       </div> 
       <div>
        11℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        2.4米/秒
       </div> 
       <div>
        1.4米/秒
       </div> 
       <div>
        2.2米/秒
       </div> 
       <div>
        1.1米/秒
       </div> 
       <div>
        2.6米/秒
       </div> 
       <div>
        2.5米/秒
       </div> 
       <div>
        2.1米/秒
       </div> 
       <div>
        1.3米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西北风
       </div> 
       <div>
        东南风
       </div> 
       <div>
        东南风
       </div> 
       <div>
        东南风
       </div> 
       <div>
        东南风
       </div> 
      </div> 
      <div class="row qy"> 
       <div class="label h3_qy">
        气压
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        76%
       </div> 
       <div>
        36.3%
       </div> 
       <div>
        23.3%
       </div> 
       <div>
        16.9%
       </div> 
       <div>
        16.5%
       </div> 
       <div>
        42.9%
       </div> 
       <div>
        58.8%
       </div> 
       <div>
        60.7%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        0%
       </div> 
       <div>
        0%
       </div> 
       <div>
        0%
       </div> 
       <div>
        0%
       </div> 
       <div>
        6.3%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
      </div> 
      <div class="row njd"> 
       <div class="label h3_njd">
        能见度
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
     </div> 
     <!-- 逐3小时天气 --> 
     <!-- 逐3小时天气 --> 
     <div id="day4" class="hour3" style="display:none;"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        26日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/3.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/3.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/3.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        13.7℃ 
       </div> 
       <div>
        21℃ 
       </div> 
       <div>
        23.7℃ 
       </div> 
       <div>
        24.8℃ 
       </div> 
       <div>
        18.7℃ 
       </div> 
       <div>
        16.6℃ 
       </div> 
       <div>
        13.5℃ 
       </div> 
       <div>
        9.2℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         0.1毫米 
       </div> 
       <div>
         0.1毫米 
       </div> 
       <div>
         0.1毫米 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        1.8米/秒
       </div> 
       <div>
        2.6米/秒
       </div> 
       <div>
        3.5米/秒
       </div> 
       <div>
        3.6米/秒
       </div> 
       <div>
        1.1米/秒
       </div> 
       <div>
        1.1米/秒
       </div> 
       <div>
        4.3米/秒
       </div> 
       <div>
        5.2米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        东南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
      </div> 
      <div class="row qy"> 
       <div class="label h3_qy">
        气压
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        56.3%
       </div> 
       <div>
        45.6%
       </div> 
       <div>
        38.3%
       </div> 
       <div>
        32.9%
       </div> 
       <div>
        28.8%
       </div> 
       <div>
        64.4%
       </div> 
       <div>
        74.6%
       </div> 
       <div>
        84.9%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        10%
       </div> 
       <div>
        22.1%
       </div> 
       <div>
        44.7%
       </div> 
       <div>
        29.1%
       </div> 
       <div>
        35.2%
       </div> 
       <div>
        29.7%
       </div> 
       <div>
        4.4%
       </div> 
       <div>
        8.5%
       </div> 
      </div> 
      <div class="row njd"> 
       <div class="label h3_njd">
        能见度
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
     </div> 
     <!-- 逐3小时天气 --> 
     <!-- 逐3小时天气 --> 
     <div id="day5" class="hour3" style="display:none;"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        27日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/3.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        11.8℃ 
       </div> 
       <div>
        21.8℃ 
       </div> 
       <div>
        17.4℃ 
       </div> 
       <div>
        15℃ 
       </div> 
       <div>
        13.8℃ 
       </div> 
       <div>
        13.4℃ 
       </div> 
       <div>
        6.2℃ 
       </div> 
       <div>
        6.5℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         0.1毫米 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        4.7米/秒
       </div> 
       <div>
        4.2米/秒
       </div> 
       <div>
        3米/秒
       </div> 
       <div>
        8.8米/秒
       </div> 
       <div>
        4米/秒
       </div> 
       <div>
        5.5米/秒
       </div> 
       <div>
        1.8米/秒
       </div> 
       <div>
        5.5米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        东北风
       </div> 
      </div> 
      <div class="row qy"> 
       <div class="label h3_qy">
        气压
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        76.6%
       </div> 
       <div>
        48.2%
       </div> 
       <div>
        54.2%
       </div> 
       <div>
        56.8%
       </div> 
       <div>
        45.3%
       </div> 
       <div>
        73.1%
       </div> 
       <div>
        90.7%
       </div> 
       <div>
        73.1%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        100%
       </div> 
       <div>
        58.1%
       </div> 
       <div>
        35.5%
       </div> 
       <div>
        10.1%
       </div> 
       <div>
        10.1%
       </div> 
       <div>
        23.6%
       </div> 
       <div>
        10.1%
       </div> 
       <div>
        27.7%
       </div> 
      </div> 
      <div class="row njd"> 
       <div class="label h3_njd">
        能见度
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
       <div>
         - 
       </div> 
      </div> 
     </div> 
     <!-- 逐3小时天气 --> 
     <!-- 逐3小时天气 --> 
     <div id="day6" class="hour3" style="display:none;"> 
      <div class="row first"> 
       <div class="label">
        精细预报
       </div> 
       <div style="font-size: 12px;">
        08:00
       </div> 
       <div style="font-size: 12px;">
        11:00
       </div> 
       <div style="font-size: 12px;">
        14:00
       </div> 
       <div style="font-size: 12px;">
        17:00
       </div> 
       <div style="font-size: 12px;">
        20:00
       </div> 
       <div style="font-size: 12px;">
        23:00
       </div> 
       <div style="font-size: 12px;">
        28日02:00
       </div> 
       <div style="font-size: 12px;">
        05:00
       </div> 
      </div> 
      <div class="row second tqxx"> 
       <div class="label h3_tqxx">
        天气现象
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
       <div>
        <img src="http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png">
       </div> 
      </div> 
      <div class="row wd"> 
       <div class="label h3_wd">
        气温
       </div> 
       <div>
        6.9℃ 
       </div> 
       <div>
        21.8℃ 
       </div> 
       <div>
        20.4℃ 
       </div> 
       <div>
        19.1℃ 
       </div> 
       <div>
        16.2℃ 
       </div> 
       <div>
        16.2℃ 
       </div> 
       <div>
        9.2℃ 
       </div> 
       <div>
        11.4℃ 
       </div> 
      </div> 
      <div class="row js"> 
       <div class="label h3_js">
        降水
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
       <div>
         无降水 
       </div> 
      </div> 
      <div class="row winds"> 
       <div class="label h3_fl">
        风速
       </div> 
       <div>
        0.9米/秒
       </div> 
       <div>
        3.3米/秒
       </div> 
       <div>
        6米/秒
       </div> 
       <div>
        3.1米/秒
       </div> 
       <div>
        4.3米/秒
       </div> 
       <div>
        3.4米/秒
       </div> 
       <div>
        3米/秒
       </div> 
       <div>
        3.2米/秒
       </div> 
      </div> 
      <div class="row windd"> 
       <div class="label h3_fx">
        风向
       </div> 
       <div>
        东南风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        东北风
       </div> 
       <div>
        西南风
       </div> 
       <div>
        东北风
       </div> 
      </div> 
      <div class="row xdsd"> 
       <div class="label h3_xdsd">
        相对湿度
       </div> 
       <div>
        66%
       </div> 
       <div>
        58.1%
       </div> 
       <div>
        60.3%
       </div> 
       <div>
        55.5%
       </div> 
       <div>
        50.7%
       </div> 
       <div>
        51.1%
       </div> 
       <div>
        61.2%
       </div> 
       <div>
        50.5%
       </div> 
      </div> 
      <div class="row yl"> 
       <div class="label h3_yl">
        云量
       </div> 
       <div>
        39.7%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        1.6%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
       <div>
        10%
       </div> 
      </div> 

     </div> 
     <!-- 逐3小时天气 --> 
    </div> 
    
    
  </div> 
  <script type="text/javascript">
	var pcode = 'ANM';
	var scode = '53513';
	</script> 
  <script type="text/javascript" src="http://image.nmc.cn/static2/site/nmc/themes/basic/js/Highcharts-4.0.4/js/highcharts.js?v=2.0"></script> 
  <script type="text/javascript" src="/IntegratedForecast/WebContent/style/js/forecast.js"></script> 
  <script type="text/javascript" src="http://image.nmc.cn/static2/site/nmc/themes/basic/js/ac.js?v=2.0"></script> 
  <script type="text/javascript">
    $(function () {
    	$('#nav-05').trigger('mouseenter');
    	sunriseset(107.42, 40.75);
    	initReal('53513');
    	initAqi('53513');
    	drawTemperature('53513');
    	changeTab();
    	climate([-3.59,1.4,8.9,17.56,24.58,28.92,30.44,28.29,23.1,15.73,5.56,-1.97],[-15.73,-12,-4.92,2.52,9.43,14.74,17.68,15.83,9.55,2.33,-5.28,-12.81],[1.16,1.19,2.9,5.56,12.11,17.08,32.08,43.1,19.47,7.7,1.75,0.63],'临河');
    });

    </script> 
   <div id="footer" class="footer"> 
   <ul>  </ul> 
   <p>hebj 版权所有 Copyright@2016-2020 京ICP备05055842号</p> 
  </div> 
 </body>
</html>