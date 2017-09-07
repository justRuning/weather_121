//
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
//
//
//

package com.hebj.forecast.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hebj.forecast.dao.StationDao;
import com.hebj.forecast.dao.YuyinDao;
import com.hebj.forecast.entity.AllMsg;
import com.hebj.forecast.entity.Fire;
import com.hebj.forecast.entity.Forecast;
import com.hebj.forecast.entity.WeatherActual;
import com.hebj.forecast.entity.ZaoJian;
import com.hebj.forecast.entity.lvyou;
import com.hebj.forecast.service.AllMsgService;
import com.hebj.forecast.service.FireService;
import com.hebj.forecast.service.ForecastService;
import com.hebj.forecast.service.WeatherActualService;
import com.hebj.forecast.service.ZaoJianService;
import com.hebj.forecast.util.ForecastHelper;
import com.hebj.forecast.util.GetIndexForecast;
import com.hebj.forecast.util.Helper;
import com.hebj.forecast.util.WeatherUtil;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

	@Autowired
	ForecastService forecastService;
	@Autowired
	WeatherActualService weatherActualservice;
	@Autowired
	StationDao stationDao;
	@Autowired
	YuyinDao yuyinDao;
	@Autowired
	ZaoJianService zaoJianService;
	@Autowired
	FireService fireService;
	@Autowired
	AllMsgService allMsgService;

	/**
	 * 保存前端传送的语音天气实况
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	@RequestMapping(value = "/scssYYWeather", method = RequestMethod.POST)
	public String scssWeather(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "text", required = true) String text) {

		return WeatherUtil.savaWeather(name, text);

	}

	/**
	 * 保存前端传送的天气预报短信
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	@RequestMapping(value = "/scssForecastMsg", method = RequestMethod.POST)
	public String scssForecastMsg(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "text", required = true) String text) {

		return ForecastHelper.savaMsg(name, text);

	}

	/**
	 * 保存前端传送的早间气象
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scssZaojian", method = RequestMethod.POST)
	public String scssZaojian(@RequestParam(value = "content", required = true) String content) {

		ZaoJian zaoJian = new ZaoJian();

		zaoJian.content = content;
		zaoJianService.sava(zaoJian);

		return "OK!";

	}

	@ResponseBody
	@RequestMapping(value = "/scssAllMsg", method = RequestMethod.POST)
	public String scssAllMsg(@RequestParam(value = "content", required = true) String content) {

		AllMsg allMsg = new AllMsg();
		allMsg.content = content;
		allMsgService.sava(allMsg);

		return "OK!";

	}

	/**
	 * 保存前端传送的森林火险短信
	 * 
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scssFire", method = RequestMethod.POST)
	public String scssFire(@RequestParam(value = "content", required = true) String content) {

		Fire fire = new Fire();
		Calendar calendar = Calendar.getInstance();
		fire.date = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		fire.content = content;
		fireService.sava(fire);

		return "OK!";

	}

	/**
	 * 保存前端传送的语音天气预报
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	@RequestMapping(value = "/scssYYForecast", method = RequestMethod.POST)
	public void scssForecast(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "text", required = true) String text) {

		ForecastHelper.savaYYMsg(name, text);

		// if (name == "临河") {
		// return "语音预报保存成功！";
		// }
		// return null;

	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index.jsp");

		List<Forecast> forecasts = forecastService.getLastForecast();
		String time;
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月d日");
		time = dateFormat.format(forecasts.get(0).getBeginTime());
		time = time + forecasts.get(0).getHour() + "时";

		model.addObject("time", time);

		return model;
	}

	/**
	 * 旅游天气预报
	 * 
	 * @return
	 */
	@RequestMapping("/lvyou")
	public ModelAndView lvyou() {
		ModelAndView model = new ModelAndView("lvyou.jsp");
		List<lvyou> lvyous = ForecastHelper.getLvyou();
		try {
			ForecastHelper.writeLvyouForecast(lvyous);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addObject("forecasts", lvyous);
		return model;
	}

	/**
	 * 早间气象
	 * 
	 * @return
	 */
	@RequestMapping("/zaojian")
	public ModelAndView zaojian() {
		ModelAndView model = new ModelAndView("zaojian.jsp");

		Calendar calendar = Calendar.getInstance();
		java.sql.Date date = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH));

		ZaoJian zaoJian = new ZaoJian();
		zaoJian = zaoJianService.getZaoJian(date);
		if (calendar.get(Calendar.DAY_OF_MONTH) == 7) {
			zaoJian = zaoJianService.makeZaoJian();
		}

		String strZaoJian = zaoJian.getContent();

		model.addObject("strZaojian", strZaoJian);
		model.addObject("day", zaoJian.getDate().getDate());
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/getZaojian", method = RequestMethod.POST)
	public ModelMap getZaojian(@RequestParam(value = "day", required = true) int day,
			@RequestParam(value = "type", required = true) String type) {

		Calendar calendar = Calendar.getInstance();

		if (type.equals("last")) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			day = day - 1;
		} else if (type.equals("next")) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			day = day + 1;
		} else {
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}
		java.sql.Date date = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH));
		ModelMap modelMap = new ModelMap();
		ZaoJian zaoJian = zaoJianService.getZaoJian(date);

		modelMap.addAttribute("day", day);
		modelMap.addAttribute("zaojian", zaoJian);
		return modelMap;

	}

	/**
	 * 全市预报
	 * 
	 * @return
	 */
	@RequestMapping("/allMsg")
	public ModelAndView allMsg() {
		ModelAndView model = new ModelAndView("allMsg.jsp");

		AllMsg allMsg = allMsgService.makeAllMsg();

		model.addObject("allMsg", allMsg);
		model.addObject("day", allMsg.getDate().getDate());
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/getAllMsg", method = RequestMethod.POST)
	public ModelMap getAllMsg(@RequestParam(value = "day", required = true) int day,
			@RequestParam(value = "type", required = true) String type) {

		Calendar calendar = Calendar.getInstance();

		if (type.equals("last")) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			day = day - 1;
		} else if (type.equals("next")) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			day = day + 1;
		} else {
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}
		java.sql.Date date = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH));
		ModelMap modelMap = new ModelMap();
		AllMsg allMsg = allMsgService.getAllMsg(date);

		modelMap.addAttribute("day", day);
		modelMap.addAttribute("allMsg", allMsg);
		return modelMap;

	}

	/**
	 * 森林火险
	 * 
	 * @return
	 */
	@RequestMapping("/fire")
	public ModelAndView Fire() {
		ModelAndView model = new ModelAndView("fire.jsp");

		Calendar calendar = Calendar.getInstance();
		java.sql.Date date = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH));
		Fire fire = fireService.getFire(date);
		if (fire.getDate() == null) {
			fire = fireService.makeFire();
		}

		int day = fire.getDate().getDate();
		model.addObject("day", day);
		model.addObject("fire", fire);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/getFire", method = RequestMethod.POST)
	public ModelMap getFire(@RequestParam(value = "day", required = true) int day,
			@RequestParam(value = "type", required = true) String type) {

		Calendar calendar = Calendar.getInstance();

		if (type.equals("last")) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			day = day - 1;
		} else if (type.equals("next")) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			day = day + 1;
		} else {
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}
		java.sql.Date date = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONDAY),
				calendar.get(Calendar.DAY_OF_MONTH));
		ModelMap modelMap = new ModelMap();
		Fire fire = fireService.getFire(date);

		modelMap.addAttribute("day", day);
		modelMap.addAttribute("fire", fire);
		return modelMap;

	}

	/**
	 * 实况、预报首页
	 * 
	 * @return
	 */
	@RequestMapping("/weather")
	public ModelAndView Weather() {

		ModelAndView model = new ModelAndView("weather.jsp");

		List<WeatherActual> weathers = weatherActualservice.getLastWeather();
		List<Forecast> forecasts = forecastService.getLastForecast();
		Map<String, ArrayList<WeatherActual>> weather24 = weatherActualservice.getLast24Weather();
		String lines = ForecastHelper.getForecastTxt();
		String time;
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月d日");
		time = dateFormat.format(forecasts.get(0).getBeginTime());
		time = time + forecasts.get(0).getHour() + "时更新";

		// Map<String, String> map = GetIndexForecast.getWeatherWeb();

		model.addObject("weathers", weathers);
		model.addObject("forecasts", forecasts);
		model.addObject("lines", lines);
		model.addObject("weather24", weather24);
		model.addObject("time", time);
		// model.addObject("map", map);

		return model;
	}

	@RequestMapping("/wechat")
	public ModelAndView wechat() {
		ModelAndView model = new ModelAndView("wechat.jsp");

		List<Forecast> forecasts = forecastService.getWeChatForecast();
		DateFormat dateFormat = new SimpleDateFormat("M月d日");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(forecasts.get(0).getBeginTime());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		List<String> times = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			String string = dateFormat.format(calendar.getTime()) + "(" + Helper.getDayOfWeek2(calendar.getTime())
					+ ")";
			times.add(string);
			calendar.add(Calendar.DAY_OF_MONTH, 1);

		}
		model.addObject("times", times);
		model.addObject("forecasts", forecasts);
		return model;
	}

	/**
	 * 语音检索
	 * 
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getYuyin", method = RequestMethod.POST)
	public List<String> getYuyin(@RequestParam(value = "pinyin", required = true) String pinyin) {

		return yuyinDao.getYuyinByPinyin(pinyin);

	}

	/**
	 * 语音实况
	 * 
	 * @return
	 */
	@RequestMapping("/yy_shikuang")
	public ModelAndView yy_shikuang() {
		ModelAndView model = new ModelAndView("yy_shikuang.jsp");

		Map<String, String> weathers = weatherActualservice.getYYWeather();
		model.addObject("weathers", weathers);
		return model;
	}

	/**
	 * 语音预报
	 * 
	 * @return
	 */
	@RequestMapping("/yy_yubao")
	public ModelAndView yy_yubao() {
		ModelAndView model = new ModelAndView("yy_yubao.jsp");

		List<Forecast> forecasts = forecastService.getLastForecast();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(forecasts.get(0).getBeginTime());
		int h = calendar.get(Calendar.HOUR_OF_DAY);

		String lastTime = calendar.get(Calendar.DAY_OF_MONTH) + "日" + forecasts.get(0).getHour() + "时更新";
		Map<String, String> forecast = new TreeMap<>();
		forecast = forecastService.getYYForecast();
		model.addObject("forecasts", forecast);
		model.addObject("lastTime", lastTime);
		return model;
	}

	/**
	 * 短信预报
	 * 
	 * @return
	 */
	@RequestMapping("/yubao")
	public ModelAndView yubao() {

		ModelAndView model = new ModelAndView("yubao.jsp");
		Map<String, String> weathers = forecastService.getForecastMsg();
		model.addObject("forecasts", weathers);
		return model;
	}

	@RequestMapping("/shikuang")
	public ModelAndView shikuang() {
		ModelAndView model = new ModelAndView("shikuang.jsp");

		return model;
	}

	@RequestMapping("/indexForecast")
	public ModelAndView indexForecast() {
		ModelAndView model = new ModelAndView("indexForecast.jsp");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String date = dateFormat.format(calendar.getTime());
		Map<String, String> indexForecast = GetIndexForecast.getIndex();
		GetIndexForecast indexForecast2 = new GetIndexForecast();
		try {
			indexForecast2.writeIndexForecast(indexForecast);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("date", date);
		model.addObject("indexForecast", indexForecast);
		return model;
	}

}
