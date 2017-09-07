var hourIsLoad = false;
function changeTab(){
	$('#tabchar > li').hover(function(){
		var $this = $(this);
		$this.find('a').addClass('actived').parent().siblings().find('a').removeClass('actived');
		var $content = $('#tabcontent > div').eq($this.index());
		$content.show().siblings().hide();
		$content.next().css({'width':1216});
		if(!hourIsLoad){
			$content.next().css({'width':1216});
			drawHour24();
			hourIsLoad = true;
		}
	},function(){
	});
}
$(function(){
	$('#forecast .detail').click(function(){
		var $this = $(this);
		$this.siblings().find('.today').hide();
		$this.find('.day').hide();
		$this.siblings().find('.day').show();
		//$this.find('.today').show();

		$('#day' + $this.index()).show().siblings().hide();

		if($this.index() == 0){
			$('#hour6').show();
		} else {
			$('#hour6').hide();
		}
	});
	
	$.getJSON(ctx + '/rest/province',function(data){
		var pSel = '';
		for(var i = 0; i < data.length; i++){
			pSel += '<option value="' + data[i].code + '">' + data[i].name + '</option>';
		}
		$('#provinceSel').html(pSel);
		$("#provinceSel").val(pcode);
		$('#provinceSel').change(function(){
			var pcode = $(this).val();
			$.getJSON(ctx + '/rest/province/' + pcode, function(data) {
				var cSel = '<option value="">璇烽€夋嫨甯傚尯</option>';
		    	for(var i = 0; i < data.length; i++) {
		    		cSel += '<option value="' + data[i].code + '" url="' + data[i].url + '">' + data[i].city + '</option>';
		    	}
		    	$('#citySel').html(cSel);
				$("#citySel").val(scode);
			});
			
		});
		
		$('#provinceSel option[value="' + pcode + '"]').trigger('change');
		$(document).on('change','#citySel',function(){
			window.location.href = $(this).find('>option:selected').attr('url');
		});
	});
	
});
var precipitationunit = "mm";
var pressureunit = "hPa";
var tempunitunit = "鈩�";
var humidityunit = "%";
var defaultOptions = {
	temperaturename: "娓╁害(" + tempunitunit + ")",
	precipitationname: "闄嶆按(" + precipitationunit + ")",
	humidityname: "鐩稿婀垮害(" + humidityunit + ")",
	pressurename: "姘斿帇(" + pressureunit + ")",
	temperature_high_name: "楂樻俯(" + tempunitunit + ")",
	temperature_low_name: "浣庢俯(" + tempunitunit + ")",
	yaxistitlefont: "11px Tahoma",
	gridcolor: "#C0D0E0",
	altnernatebgcolor: "#f1f1f1",
	bgcolor: null,
	creditstext: "涓ぎ姘旇薄鍙�",
	creditshref: "http://www.nmc.cn",
	temperaturecolor: "#f78723",//"#f3715c",
	precipitationcolor: "#10f81a",//"#2468a2",
	humiditycolor: "#09b3f1",//"#AA4643",
	pressurecolor: "#585eaa",
	temperature_high_color: "#f3715c",
	temperature_low_color: "#4572A7",
	showtemperature: true,
	showprecipitation: true,
	showhumidity: true,
	showpressure: true,
	show_temperature_high: true,
	show_temperature_low: true
};
Highcharts.setOptions({
	lang: {
		resetZoom: '<font color="blue">杩樺師</font>',
		resetZoomTitle: "閲嶈瑙嗗浘鍖哄煙1:1",
		loading: "姝ｅ湪杞藉叆......",
		downloadPNG: "涓嬭浇PNG鍥惧儚",
		downloadJPEG: "涓嬭浇JPEG鍥惧儚",
		downloadPDF: "涓嬭浇PDF鏂囨。",
		downloadSVG: "涓嬭浇SVG鐨勭煝閲忓浘鍍�",
		exportButtonTitle: "瀵煎嚭鍒版爡鏍兼垨鐭㈤噺鍥惧儚",
		printButtonTitle: "鎵撳嵃鍥捐〃"
	},
	exporting: {
		filename: "鍥藉姘旇薄涓績妫€楠屽钩鍙�",
		url: "/highcharts/downchar",
		width: 1200,
		height: 600,
		buttons: {
			printButton: {
				enabled: false
			}
		}
	},
	credits: {
		enabled: true,
		href: defaultOptions.creditshref,
		target: "_blank",
		text: defaultOptions.creditstext
	},
	loading: {
		labelStyle: {
			top: "45%"
		},
		style: {
			position: "absolute",
			backgroundColor: "red",
			opacity: 0.5,
			textAlign: "center"
		}
	}
});
function getDayOfWeek(e) {
	var a = e.substr(0, 4) + "/" + e.substr(4, 2) + "/" + e.substr(6, 2);
	var g = new Date(Date.parse(a));
	var f = new Array("鍛ㄦ棩", "鍛ㄤ竴", "鍛ㄤ簩", "鍛ㄤ笁", "鍛ㄥ洓", "鍛ㄤ簲", "鍛ㄥ叚");
	var c = new Date();
	var b = c.getFullYear();
	var i = (c.getMonth() + 1) > 9 ? (c.getMonth() + 1) : "0" + (c.getMonth() + 1);
	var d = (c.getDate() > 9) ? c.getDate() : "0" + c.getDate();
	var h = "" + b + i + d;
	if ((parseInt(e) - parseInt(h)) == 0) {
		return '<span style="color:#240481;font-weight:bold;">浠婂ぉ</span>'
	}
	if ((parseInt(e) - parseInt(h)) == 1) {
		return '<span style="color:#240481;font-weight:bold;">鏄庡ぉ</span>'
	}
	if ((parseInt(e) - parseInt(h)) == 2) {
		return '<span style="color:#240481;font-weight:bold;">鍚庡ぉ</span>'
	}
	return f[g.getDay()]
}
var chartoptions = {
	temperature: {
		options: {
			chart: {
				renderTo: "tempchart",
				backgroundColor: 'rgba(131,132,139,0.5)',
                plotBackgroundColor: 'rgba(0,0,0,0)',
				defaultSeriesType: "spline",
				margin: [50, 80, 50, 80],
				zoomType: "x"
			},
			title: {
				text: ""
			},
			subtitle: {
				text: ""
			},
			xAxis: {
				categories: [],
				labels: {
                    style: {
                        color: '#FFF',
                        fontFamily: "Microsoft YaHei"
                    },
					formatter: function() {
						return this.value.substr(4, 4) + "<br/>" + getDayOfWeek(this.value);
					}
				}
			},
			yAxis: [{
				title: {
					text: defaultOptions.temperaturename,
					margin: 3,
					style: {
						color: '#FFF',//defaultOptions.temperature_high_color,
						font: "13px Microsoft YaHei",//defaultOptions.yaxistitlefont,
						fontWeight: "bold"
					}
				},
				labels: {
					style: {
						color: '#f78723'//defaultOptions.temperature_high_color
					}
				},
				lineWidth: 1,
				tickColor: defaultOptions.gridcolor,
				lineColor: defaultOptions.gridcolor,
				gridLineColor: defaultOptions.gridcolor,
				tickWidth: 1
			}],
			legend: {
				verticalAlign: "top",
				itemStyle: {
                    color: '#fff',
                    fontWeight: 'bold',
                    fontFamily: "Microsoft YaHei"
                }
			},
			tooltip: {
				shared: true,
				crosshairs: true
			},
			series: [{
				name: defaultOptions.temperature_high_name,
				color: '#f78723',//defaultOptions.temperature_high_color,
				visible: defaultOptions.show_temperature_high,
				lineWidth: 3
			},
			{
				name: defaultOptions.temperature_low_name,
				color: '#27a5f9',//defaultOptions.temperature_low_color,
				visible: defaultOptions.show_temperature_low,
				lineWidth: 3
			}]
		}
	},
	hours: {
		options: {
			chart: {
				renderTo: "hours",
				backgroundColor: 'rgba(131,132,139,0.5)',
                plotBackgroundColor: 'rgba(0,0,0,0)',
				defaultSeriesType: "spline",
				margin: [80, 150, 50, 150],
				zoomType: "x"
			},
			title: {
				text: ""
			},
			subtitle: {
				text: ""
			},
			xAxis: {
				gridLineWidth: 0,
				lineColor: defaultOptions.gridcolor,
				gridLineColor: defaultOptions.gridcolor,
				labels: {
            style: {
                color: '#FFF'
            },
            formatter:function(){
								return this.value.substr(11,2) + '鏃�';
            }
        }
			},
			yAxis: [{
				title: {
					text: defaultOptions.temperaturename,
					margin: 3,
					style: {
						color: defaultOptions.temperaturecolor,
						font: "12px Microsoft YaHei"
					}
				},
				labels: {
					style: {
						color: defaultOptions.temperaturecolor
					}
				},
				offset: 0,
				tickWidth: 1
			},
			{
				title: {
					text: defaultOptions.precipitationname,
					margin: 3,
					style: {
						color: defaultOptions.precipitationcolor,
						font: "12px Microsoft YaHei"
					}
				},
				labels: {
					style: {
						color: defaultOptions.precipitationcolor
					}
				},
				opposite: true,
				min: 0,
				tickWidth: 1
			},
			{
				title: {
					text: defaultOptions.humidityname,
					margin: 3,
					style: {
						color: defaultOptions.humiditycolor,
						font: "12px Microsoft YaHei"
					}
				},
				labels: {
					style: {
						color: defaultOptions.humiditycolor
					}
				},
				opposite: true,
				offset: 55,
				min: 0,
				tickWidth: 1
			},
			{
				title: {
					text: defaultOptions.pressurename,
					margin: 3,
					style: {
						color: defaultOptions.pressurecolor,
						font: "12px Microsoft YaHei"
					}
				},
				labels: {
					style: {
						color: defaultOptions.pressurecolor
					}
				},
				offset: 45,
				tickWidth: 1
			}],
			legend: {
				verticalAlign: "top",
				itemStyle: {
                    color: '#fff',
                    fontWeight: 'bold'
                }
			},
			tooltip: {
				shared: true,
				crosshairs: true
			},
			plotOptions: {
				series: {
					events: {
						hide: function() {
							this.yAxis.axisTitle.hide()
						},
						show: function() {
							this.yAxis.axisTitle.show()
						}
					}
				}
			},
			series: [{
				name: defaultOptions.temperaturename,
				yAxis: 0,
				color: defaultOptions.temperaturecolor,
				visible: defaultOptions.showtemperature
			},
			{
				name: defaultOptions.precipitationname,
				yAxis: 1,
				pointPadding: -0.2,
				groupPadding: 0.2,
				type: "column",
				color: defaultOptions.precipitationcolor,
				visible: defaultOptions.showprecipitation
			},
			{
				name: defaultOptions.humidityname,
				yAxis: 2,
				color: defaultOptions.humiditycolor,
				visible: defaultOptions.showhumidity
			},
			{
				name: defaultOptions.pressurename,
				yAxis: 3,
				color: defaultOptions.pressurecolor,
				visible: defaultOptions.showpressure
			}]
		}
	}
};
/**
 * 鏈€鏂�24灏忔椂鍐呮暣鐐瑰疄鍐垫暟鎹浘琛�
 * @param g
 */
function drawHour24() {
	var chart = new Highcharts.Chart(chartoptions.hours.options);
	chart.showLoading();
	$.getJSON(ctx + '/rest/passed/' + scode, function(data) {
		var line = '',category = [],temperature = [],humidity = [],pressure = [],wind = [],rain1h = [];
		var flag = false;
		for(var i = data.length - 1; i >= 0; i--){
			if(data[i].time.substr(11,2) == '00') {
				line = data.length - i - 1;
				flag = true;
			}
			category.push(data[i].time);
			temperature.push((data[i].temperature >= 9999) ? null : data[i].temperature);
			humidity.push((data[i].humidity >= 9999) ? null : data[i].humidity);
			pressure.push((data[i].pressure >= 9999) ? null : data[i].pressure);
			wind.push({direction:(data[i].windDirection >= 9999) ? null : data[i].windDirection,speed:(data[i].windSpeed >= 9999) ? null : data[i].windSpeed});
			rain1h.push((data[i].rain1h >= 9999) ? null : data[i].rain1h);
		}

		var d = temperature[23] == null ? '鏃犳暟鎹�' : temperature[23] + "鈩�";
		var f = rain1h[23] == null ? '鏃犳暟鎹�' : rain1h[23] + "mm";
		var e = humidity[23] == null ? '鏃犳暟鎹�' : humidity[23] + "%";
		var b = pressure[23] == null ? '鏃犳暟鎹�' : pressure[23] + "hPa";
		$("#hours_title").html("鏈€鏂版暣鐐瑰疄鍐碉紙" + category[23] + "鏃讹級锛�<font color='#f3715c'>姘旀俯锛�" + d + "</font> <font color='#2468a2'>闄嶆按锛�" + f + "</font> <font color='#AA4643'>鐩稿婀垮害锛�" + e + "</font> <font color='#585eaa'>姘斿帇锛�" + b + "</font>");

		chart.xAxis[0].setCategories(category, false);
		chart.xAxis[0].addPlotLine({
			color: "green",
			width: 2,
			value: line
		});
		chart.series[0].setData(temperature, false);
		chart.series[1].setData(rain1h, false);
		chart.series[2].setData(humidity, false);
		chart.series[3].setData(pressure, false);
		chart.hideLoading();
		chart.redraw();

	});

}
/**
 * 娓╁害鏇茬嚎鍥捐〃
 * @param b
 */
function drawTemperature(scode) {
	var a = new Highcharts.Chart(chartoptions.temperature.options);
	a.showLoading();
	var category = [],maxTemp = [],minTemp = [];
	$.getJSON(ctx + '/rest/tempchart/' + scode, function(data) {
		for (var i = 0; i < data.length; i++) {
			category.push(data[i].realTime);
			if(data[i].dayImg == '9999'){
				maxTemp.push(data[i].maxTemp >= 9999 ? null : data[i].maxTemp);
			} else {
				if(data[i].maxTemp >= 9999){
					maxTemp.push(null);
				} else {
					maxTemp.push({
						y: data[i].maxTemp,
						marker: {
							symbol: "url(http://image.nmc.cn/static/site/nmc/themes/basic/weather/color/day/small/" + data[i].dayImg + ".png)"
						}
					});
				}
			}
			if(data[i].nightImg == '9999'){
				minTemp.push(data[i].minTemp >= 9999 ? null : data[i].minTemp);
			} else {
				if(data[i].minTemp >= 9999){
						minTemp.push(data[i].minTemp);
				} else {
					minTemp.push({
						y: data[i].minTemp,
						marker: {
							symbol: "url(http://image.nmc.cn/static/site/nmc/themes/basic/weather/color/night/small/" + data[i].nightImg + ".png)"
						}
					});
				}
		}
	}
		a.xAxis[0].setCategories(category, false);
		a.xAxis[0].addPlotBand({
			from: -1,
			to: 7,
			color: "rgba(18, 48, 84, 0.5)",
			id: "plot-band-1"
		});
		a.xAxis[0].addPlotBand({
			from: 7,
			to: 14,
			color: "rgba(119, 165, 183, 0.5)",
			id: "plot-band-2"
		});
		a.series[0].setData(maxTemp, false);
		a.series[1].setData(minTemp, false);
		a.hideLoading();
		a.redraw();

	});

};

var icomfort = {
		'i9999':{label:'',color:'#e74936'},
		'i4':{label:'寰堢儹锛屾瀬涓嶉€傚簲',color:'#e74936'},
		'i3':{label:'鐑紝寰堜笉鑸掗€�',color:'#f57f1f'},
		'i2':{label:'鏆栵紝涓嶈垝閫�',color:'#FF9900'},
		'i1':{label:'娓╂殩锛岃緝鑸掗€�',color:'#00a44f'},
		'i0':{label:'鑸掗€傦紝鏈€鍙帴鍙�',color:'#53aaae'},
		'i-1':{label:'鍑夌埥锛岃緝鑸掗€�',color:'#0079c3'},
		'i-2':{label:'鍑夛紝涓嶈垝閫�',color:'#2c459c'},
		'i-3':{label:'鍐凤紝寰堜笉鑸掗€�',color:'#754783'},
		'i-4':{label:'寰堝喎锛屾瀬涓嶉€傚簲',color:'#9b479b'}
};
function initReal(citycode){
	$.ajax({
		url:ctx + '/rest/real/' + citycode,
		dataType:'json',
		cache:false,
		success:function(data){
			$('#realPublishTime').html('鏇存柊浜庯細' + data.publish_time + '鍙戝竷');
			$('#realTemperature').html(data.weather.temperature == 9999 ? '-' : (data.weather.temperature + '鈩�'));
			$('#realRain,#todayRain').html(data.weather.rain == 9999 ? '-' : (data.weather.rain + 'mm'));
			$('#realWindDirect,#todayDirect').html(data.wind.direct == 9999 ? '-' : (data.wind.direct));
			$('#realWindPower,#todayPower').html((data.wind.direct == 9999 || data.wind.power == 9999) ? '-' : (data.wind.power));
			$('#realHumidity').html(data.weather.humidity == 9999 ? '-' : (data.weather.humidity + '%'));
			$('#realIcomfort').html(data.weather.icomfort == 9999 ? '-' : '<span style="color:' + icomfort['i' + data.weather.icomfort].color + '">' + (icomfort['i' + data.weather.icomfort].label)) + '</span>';
			$('#realFeelst').html(data.weather.feelst == 9999 ? '-' : (data.weather.feelst + '鈩�'));
			if(data.warn.alert == 9999){
				$('#realWarn').html('鏈競棰勮-鏆傛棤');
			} else {
				$('#realWarn').html(data.warn.alert).attr('href',data.warn.url);
			}
		}
	});
}
var AQIDICT = [];
AQIDICT[1] = {level:'浼�',		ccolor:'#d9fed7',color:'#32f43e',tcolor:'#000',	health:'绌烘皵璐ㄩ噺浠や汉婊℃剰,鍩烘湰鏃犵┖姘旀薄鏌撱€�',											suggestion:'鍚勭被浜虹兢鍙甯告椿鍔ㄣ€�',background:'background-position:0 -22px',border:'#6ec129'};
AQIDICT[2] = {level:'鑹� ',		ccolor:'#f7f9cd',color:'#e4f33e',tcolor:'#000', health:'绌烘皵璐ㄩ噺鍙帴鍙�,浣嗘煇浜涙薄鏌撶墿鍙兘<br/>瀵规瀬灏戞暟寮傚父,鏁忔劅浜虹兢鍋ュ悍鏈夎緝寮卞奖鍝嶃€�',	suggestion:'鏋佸皯鏁板紓甯告晱鎰熶汉缇ゅ簲鍑忓皯鎴峰娲诲姩銆�',background:'background-position:-41px -22px',border:'#e0cf22'};
AQIDICT[3] = {level:'杞诲害姹℃煋 ',	ccolor:'#fcebd7',color:'#e19535',tcolor:'#000', health:'鏄撴劅浜虹兢鐥囩姸鏈夎交搴﹀姞鍓�,鍋ュ悍浜虹兢<br/>鍑虹幇鍒烘縺鐥囩姸銆�',							suggestion:'鍎跨銆佽€佸勾浜哄強蹇冭剰鐥呫€佸懠鍚哥郴缁熺柧<br/>鐥呮偅鑰呭簲鍑忓皯闀挎椂闂淬€侀珮寮哄害鐨勬埛<br/>澶栭敾鐐笺€�',background:'background-position:-82px -22px',border:'#fd5b30'};
AQIDICT[4] = {level:'涓害姹℃煋',	ccolor:'#f8d7d9',color:'#ec0800',tcolor:'#fff', health:'杩涗竴姝ュ姞鍓ф槗鎰熶汉缇ょ棁鐘�,鍙兘瀵瑰仴<br/>搴蜂汉缇ゅ績鑴忋€佸懠鍚哥郴缁熸湁褰卞搷銆�',			suggestion:'鍎跨銆佽€佸勾浜哄強蹇冭剰鐥呫€佸懠鍚哥郴缁熺柧<br/>鐥呮偅鑰呴伩鍏嶉暱鏃堕棿銆侀珮寮哄害鐨勬埛澶�<br/>閿荤偧,涓€鑸汉缇ら€傞噺鍑忓皯鎴峰杩愬姩銆�',background:'background-position:0 -48px',border:'#e10724'};
AQIDICT[5] = {level:'閲嶅害姹℃煋',	ccolor:'#ebd7e3',color:'#950449',tcolor:'#fff', health:'蹇冭剰鐥呭拰鑲虹梾鎮ｈ€呯棁鐘舵樉钁楀姞鍓�,杩�<br/>鍔ㄨ€愬彈鍔涘噺浣�,鍋ュ悍浜虹兢鏅亶鍑虹幇鐥囩姸銆�',		suggestion:'鑰佸勾浜哄拰蹇冭剰鐥呫€佽偤鐥呮偅鑰呭簲鍋滅暀鍦�<br/>瀹ゅ唴锛屽仠姝㈡埛澶栨椿鍔紝涓€鑸汉缇ゅ噺<br/>灏戞埛澶栨椿鍔ㄣ€�',background:'background-position:-41px -48px',border:'#8f0c50'};
AQIDICT[6] = {level:'涓ラ噸姹℃煋',	ccolor:'#e7d7dd',color:'#7b001f',tcolor:'#fff', health:'鍋ュ悍浜鸿繍鍔ㄨ€愬姏鍑忎綆,鏈夋樉钁楀己鐑堢棁<br/>鐘�,鎻愬墠鍑虹幇鏌愪簺鐤剧梾銆�',					suggestion:'鑰佸勾浜哄拰鐥呬汉搴斿綋鐣欏湪瀹ゅ唴锛岄伩鍏嶄綋<br/>鍔涙秷鑰楋紝涓€鑸汉缇ゅ簲閬垮厤鎴峰娲诲姩銆�',background:'background-position:-82px -48px',border:'#410468'};


// 鑾峰彇绌烘皵璐ㄩ噺淇℃伅
function initAqi(stationcode){
	$.ajax({
		url:ctx + '/rest/aqi/' + stationcode,
		dataType:'json',
		cache:false,
		success:function(data){
			if(data.aq){
				$('#aqi').html('<span style="color:' + AQIDICT[data.aq].border + '">' + AQIDICT[data.aq].level + '</span>');
			} else {
				$('#aqi').html('-');
			}
		}
	});
}

function climate(maxtemp, mintemp,rain, stationname){
	$('#container').highcharts({
        chart: {
            type: 'column',
            backgroundColor: 'rgba(131,132,139,0.5)',
            plotBackgroundColor: 'rgba(0,0,0,0)',
            style: {
                fontFamily: 'Microsoft YaHei'
            }
        },
        title: {
            text: stationname + '鏈堝钩鍧囨皵娓╁拰闄嶆按',
            style: {
                color: '#fff',//'#89A54E'
                fontFamily: 'Microsoft YaHei'
            }
        },
        subtitle: {
        //    text: '鏉ユ簮锛歸ww.nmc.cn'
        },
        legend: {
        	layout: 'horizontal',
        	align: 'center',
        	verticalAlign: 'top',
        	borderWidth: 0,
            x: 0,
            y: 30,
            itemStyle: {
                color: '#8abfdf',
                fontWeight: 'bold'
            }
        },
        xAxis: {
            categories: [
                '涓€鏈�',
                '浜屾湀',
                '涓夋湀',
                '鍥涙湀',
                '浜旀湀',
                '鍏湀',
                '涓冩湀',
                '鍏湀',
                '涔濇湀',
                '鍗佹湀',
                '鍗佷竴鏈�',
                '鍗佷簩鏈�'
            ],
            labels: {
                style: {
                    color: '#FFF'
                }
            }
        },
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}掳C',
                style: {
                    color: '#f78723'//'#89A54E'
                }
            },
            title: {
                text: '娓╁害',
                style: {
                    color: '#fff'//'#89A54E'
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '闄嶆按',
                style: {
                    color: '#fff'//'#4572A7'
                }
            },
            labels: {
                format: '{value} mm',
                style: {
                    color: '#fff'//'#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        series: [{
            name: '鏈€楂樻俯搴�',
            color: '#f78723',//'#AA4643',
            type: 'area',
            data: maxtemp,
            tooltip: {
                valueSuffix: '掳C'
            }
        },{
            name: '鏈€浣庢俯搴�',
            color: '#09b3f1',//'#89A54E',
            type: 'area',
            data: mintemp,
            tooltip: {
                valueSuffix: '掳C'
            }
        },{
            name: '闄嶆按閲�',
            color: '#91c91a',//'#4572A7',
            type: 'column',
            yAxis: 1,
            data: rain,
            tooltip: {
                valueSuffix: ' mm'
            }

        }]
    });
}


function sunriseset(lat,lng){
	// 鏃ュ嚭鏃ヨ惤
	var now=new Date();
	var d=now.getDate(),
    m=now.getMonth() + 1,
    y=now.getFullYear(),
    z=8;

    var obj = Cal(mjd(d,m,y,0.0), z, lat, lng);

    var minutes = parseInt(now.getHours()) * 60 + parseInt(now.getMinutes());
    var setMinutes = parseInt(obj.set.split(':')[0]) * 60 + parseInt(obj.set.split(':')[1]);
    var riseMinutes = parseInt(obj.rise.split(':')[0]) * 60 + parseInt(obj.rise.split(':')[1]);
    var width = (setMinutes - riseMinutes)/9;

    var hours = now.getHours() > 9 ? now.getHours() : '0' + now.getHours();
    var minute = now.getMinutes() > 9 ? now.getMinutes() : '0' + now.getMinutes();
    var currentTime = hours + ":" + minute;
	if(currentTime > obj.set || currentTime < obj.rise) {
		$('#sun').removeClass();
	} else {
		$('#sun').addClass('sun' + (9 - Math.floor((setMinutes - minutes)/width)));
	}

    $('#sunrise').text('鏃ュ嚭' + obj.rise);
    $('#sunset').text('鏃ヨ惤' + obj.set);
}

