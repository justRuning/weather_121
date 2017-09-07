package com.hebj.forecast.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hebj.forecast.entity.Station;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * 读取micaps数据
 * 
 * @author hebj
 *
 */
public class ReadMicapsData {

	/**
	 * 通过数值预报类型读取micaps全部数据
	 * 
	 * @param type
	 *            预报类型，如ecRH
	 * @param time
	 *            起报时间
	 * @param hour
	 *            预报时次
	 * @param age
	 *            预报时效
	 * @return
	 */
	public static String getMicapsData(String type, Date time, String high, int hour, int age) {

		String path = null;
		// String rootPath = ReadDataFromConfig.getValue("micaps路径");
		String ecRootPath = "smb://172.18.132.58/micaps/";
		String rootPath = "smb://172.18.132.5/mproduct/";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		if (type.contains("Physic") && high == "999") {
			high = "";
		}
		if (type.contains("T639") && high == "999") {
			high = "";
		}
		if (type.contains("Grapes") && high == "999") {
			high = "";
		}
		switch (type.toLowerCase()) {
		case "physic_cape":
			path = rootPath + "/physic" + "/CAPE/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_delta_t58":
			path = rootPath + "/physic" + "/DELTA_T58/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_delta_thse58":
			path = rootPath + "/physic" + "/DELTA_THSE58/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_dh500_24":
			path = rootPath + "/physic" + "/dh500_24/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_div":
			path = rootPath + "/physic" + "/div/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_h0c":
			path = rootPath + "/physic" + "/H0C/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_h20c":
			path = rootPath + "/physic" + "/H20C/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_ki":
			path = rootPath + "/physic" + "/ki/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_li":
			path = rootPath + "/physic" + "/LI/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_qadv":
			path = rootPath + "/physic" + "/qadv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_qfdiv":
			path = rootPath + "/physic" + "/qfdiv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_qflux":
			path = rootPath + "/physic" + "/qflux/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_qq":
			path = rootPath + "/physic" + "/qq/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_rh":
			path = rootPath + "/physic" + "/rh/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_si":
			path = rootPath + "/physic" + "/si/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_ssi":
			path = rootPath + "/physic" + "/SSI/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_t_adv":
			path = rootPath + "/physic" + "/t_adv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_tadv58":
			path = rootPath + "/physic" + "/tadv58/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_thetse":
			path = rootPath + "/physic" + "/thetse/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_tt":
			path = rootPath + "/physic" + "/tt/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_ttadv":
			path = rootPath + "/physic" + "/ttadv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_uv":
			path = rootPath + "/physic" + "/uv/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_vor":
			path = rootPath + "/physic" + "/vor/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "physic_vor_adv":
			path = rootPath + "/physic" + "/vor_adv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "physic_w":
			path = rootPath + "/physic" + "/w/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "ec_2d":
			path = ecRootPath + "/ecmwf_thin" + "/2D/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_2t":
			path = ecRootPath + "/ecmwf_thin" + "/2T/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_10fg3":
			path = ecRootPath + "/ecmwf_thin" + "/10FG3/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_10fg6":
			path = ecRootPath + "/ecmwf_thin" + "/10FG6/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_10uv":
			path = ecRootPath + "/ecmwf_thin" + "/10uv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_cape":
			path = ecRootPath + "/ecmwf_thin" + "/CAPE/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_cp":
			path = ecRootPath + "/ecmwf_thin" + "/CP/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_d":
			path = ecRootPath + "/ecmwf_thin" + "/D/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_dh":
			path = ecRootPath + "/ecmwf_thin" + "/dh/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_dp":
			path = ecRootPath + "/ecmwf_thin" + "/dp/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_dt":
			path = ecRootPath + "/ecmwf_thin" + "/dt/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_fal":
			path = ecRootPath + "/ecmwf_thin" + "/FAL/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_gh":
			path = ecRootPath + "/ecmwf_thin" + "/GH/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_lcc":
			path = ecRootPath + "/ecmwf_thin" + "/LCC/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_lsp":
			path = ecRootPath + "/ecmwf_thin" + "/LSP/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_msl":
			path = ecRootPath + "/ecmwf_thin" + "/MSL/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_mn2t6":
			path = ecRootPath + "/ecmwf_thin" + "/mn2t6/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_mx2t6":
			path = ecRootPath + "/ecmwf_thin" + "/mx2t6/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_q":
			path = ecRootPath + "/ecmwf_thin" + "/Q/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_r":
			path = ecRootPath + "/ecmwf_thin" + "/R/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_sf":
			path = ecRootPath + "/ecmwf_thin" + "/SF/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_skt":
			path = ecRootPath + "/ecmwf_thin" + "/SKT/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_t":
			path = ecRootPath + "/ecmwf_thin" + "/T/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_tcc":
			path = ecRootPath + "/ecmwf_thin" + "/TCC/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_tp":
			path = ecRootPath + "/ecmwf_thin" + "/TP/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_tcw":
			path = ecRootPath + "/ecmwf_thin" + "/tcw/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_tcwv":
			path = ecRootPath + "/ecmwf_thin" + "/tcwv/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "ec_w":
			path = ecRootPath + "/ecmwf_thin" + "/W/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_div":
			path = rootPath + "/T639" + "/DIV_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_h":
			path = rootPath + "/T639" + "/H_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_ki":
			path = rootPath + "/T639" + "/K_INDEX_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_omega":
			path = rootPath + "/T639" + "/OMEGA_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_psl":
			path = rootPath + "/T639" + "/PSL_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_q":
			path = rootPath + "/T639" + "/Q_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_q_div":
			path = rootPath + "/T639" + "/Q_DIV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_q_flux":
			path = rootPath + "/T639" + "/Q_FLUX_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rain03":
			path = rootPath + "/T639" + "/RAIN03_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rain06":
			path = rootPath + "/T639" + "/RAIN06_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rain12":
			path = rootPath + "/T639" + "/RAIN12_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rain24":
			path = rootPath + "/T639" + "/RAIN24_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rain":
			path = rootPath + "/T639" + "/RAIN_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rh2m":
			path = rootPath + "/T639" + "/RH2M_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_rh":
			path = rootPath + "/T639" + "/RH_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_t2m":
			path = rootPath + "/T639" + "/T2M_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_t":
			path = rootPath + "/T639" + "/T_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_t_adv":
			path = rootPath + "/T639" + "/T_ADV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_td":
			path = rootPath + "/T639" + "/TD_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_theta_se":
			path = rootPath + "/T639" + "/THETA_SE_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_vor":
			path = rootPath + "/T639" + "/VOR_4/" + high + "/" + sdf.format(time) + new DecimalFormat("00").format(hour)
					+ "." + new DecimalFormat("000").format(age);
			break;
		case "t639_vor_adv":
			path = rootPath + "/T639" + "/VOR_ADV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_wind10m":
			path = rootPath + "/T639" + "/WIND10M_2/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "t639_wind":
			path = rootPath + "/T639" + "/WIND_2/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_div":
			path = ecRootPath + "/grapesGfs" + "/DIV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_h":
			path = ecRootPath + "/grapesGfs" + "/H_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_k":
			path = ecRootPath + "/grapesGfs" + "/K_INDEX_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_psl":
			path = ecRootPath + "/grapesGfs" + "/PSL_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_c_dbz":
			path = ecRootPath + "/grapesGfs" + "/C_DBZ_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_q":
			path = ecRootPath + "/grapesGfs" + "/Q_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_q_div":
			path = ecRootPath + "/grapesGfs" + "/Q_DIV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_q_flux":
			path = ecRootPath + "/grapesGfs" + "/Q_FLUX_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rain03":
			path = ecRootPath + "/grapesGfs" + "/RAIN03_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rain06":
			path = ecRootPath + "/grapesGfs" + "/RAIN06_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rain12":
			path = ecRootPath + "/grapesGfs" + "/RAIN12_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rain24":
			path = ecRootPath + "/grapesGfs" + "/RAIN24_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rain":
			path = ecRootPath + "/grapesGfs" + "/RAIN_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rh2m":
			path = ecRootPath + "/grapesGfs" + "/RH2M_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_rh":
			path = ecRootPath + "/grapesGfs" + "/RH_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_t2m":
			path = ecRootPath + "/grapesGfs" + "/T2M_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_t":
			path = ecRootPath + "/grapesGfs" + "/T_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_t_adv":
			path = ecRootPath + "/grapesGfs" + "/T_ADV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_td":
			path = ecRootPath + "/grapesGfs" + "/TD_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_ts":
			path = ecRootPath + "/grapesGfs" + "/Ts_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_theta":
			path = ecRootPath + "/grapesGfs" + "/THETA_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_theta_se":
			path = ecRootPath + "/grapesGfs" + "/THETA_SE_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_vor":
			path = ecRootPath + "/grapesGfs" + "/VOR_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_vor_adv":
			path = ecRootPath + "/grapesGfs" + "/VOR_ADV_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_w":
			path = ecRootPath + "/grapesGfs" + "/W_4/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_wind10m":
			path = ecRootPath + "/grapesGfs" + "/WIND10M_2/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		case "grapes_wind":
			path = ecRootPath + "/grapesGfs" + "/WIND_2/" + high + "/" + sdf.format(time)
					+ new DecimalFormat("00").format(hour) + "." + new DecimalFormat("000").format(age);
			break;
		default:
			return null;
		}

		SmbFile smbFile = null;
		try {
			NtlmPasswordAuthentication auth;
			if (type.contains("EC") || type.contains("Grapes")) {
				auth = new NtlmPasswordAuthentication("", "administrator", "123");
			} else {
				auth = new NtlmPasswordAuthentication("", "m3", "qaz123!@#");
			}
			smbFile = new SmbFile(path, auth);
		} catch (MalformedURLException e1) {
			return null;
		}
		String micapsData = null;

		try {
			if (smbFile.exists()) {
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(new SmbFileInputStream(smbFile)));
				try {
					String line = br.readLine();
					StringBuffer micaps = new StringBuffer();
					while (line != null) {
						micaps.append(line);
						micaps.append("\n");
						line = br.readLine();
					}
					micapsData = micaps.toString();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SmbException | MalformedURLException | UnknownHostException e) {
			return null;
		}
		return micapsData;
	}

	/**
	 * Ec类格点数据插值
	 * 
	 * @param micapsData
	 * @param station
	 * @return
	 */
	public static String getValueLikeEc(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		if (micapsData == null || station == null) {
			return null;
		}
		String[] strList = micapsData.split("\n{2,}");
		double beginLatitude = Double.parseDouble(strList[2].split("\\s{1,}")[4]);
		double beginLongitude = Double.parseDouble(strList[2].split("\\s{1,}")[2]);
		double range = Double.parseDouble(strList[2].split("\\s{1,}")[0]);
		double f11, f12, f21, f22;

		// Math.Ceiling 如3.4，则在第四行
		int q1 = (int) Math.abs((beginLatitude - station.getLatitude()) / range) + 3;
		int q2 = (int) Math.abs((beginLongitude - station.getLongitude()) / range);

		String[] liStrings = strList[q1].split("\t{1}");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f12 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1].split("\t{1}");
		f21 = Double.parseDouble(liStrings[q2 + 1]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f22 = Double.parseDouble(liStrings[q2 + 1]);

		double result = getValueFromInterpolated(f12, f11, f22, f21, station.getLongitude(), station.getLatitude(),
				range);
		return Double.toString(result);
	}

	/**
	 * 插值Ec UV场
	 * 
	 * @param micapsData
	 * @param station
	 * @return 风向风速
	 */
	public static String[] getValueLikeEcuv(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		if (micapsData == null || station == null) {
			return null;
		}
		String[] strList = micapsData.split("\n{2,}");
		String[] strings = strList[2].split("\\s{1,}");
		double beginLatitude = Double.parseDouble(strings[4]);
		double beginLongitude = Double.parseDouble(strings[2]);
		double range = Double.parseDouble(strings[0]);
		int count = Integer.parseInt(strings[7]);
		double f11, f12, f21, f22;

		// Math.Ceiling 如3.4，则在第四行
		int q1 = (int) Math.abs((beginLatitude - station.getLatitude()) / range) + 3;
		int q2 = (int) Math.abs((beginLongitude - station.getLongitude()) / range);

		// u分量
		String[] liStrings = strList[q1].split("\t{1}");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f12 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1].split("\t{1}");
		f21 = Double.parseDouble(liStrings[q2 + 1]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f22 = Double.parseDouble(liStrings[q2 + 1]);

		double value = getValueFromInterpolated(f12, f11, f22, f21, station.getLongitude(), station.getLatitude(),
				range);
		// v分量
		q1 = q1 + count;
		liStrings = strList[q1].split("\t{1}");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f12 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1].split("\t{1}");
		f21 = Double.parseDouble(liStrings[q2 + 1]);

		liStrings = strList[q1 + 1].split("\t{1}");
		f22 = Double.parseDouble(liStrings[q2 + 1]);

		double value2 = getValueFromInterpolated(f12, f11, f22, f21, station.getLongitude(), station.getLatitude(),
				range);

		String[] results = new String[2];
		double result = Math.sqrt(value * value + value2 * value2);
		results[0] = Double.toString(((int) (result * 100)) / 100.0);
		result = ((270 - Math.atan2(value2, value) * 180 / Math.PI) % 360.0);
		results[1] = Double.toString(((int) (result * 100)) / 100.0);

		return results;
	}

	/**
	 * 插值T639數據
	 * 
	 * @param micapsData
	 * @param station
	 * @return
	 */
	public static String getValueLikeT639(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		if (micapsData == null || station == null) {
			return null;
		}
		String[] strList = micapsData.split("\n{1,}");
		String[] strings = strList[1].trim().split("\\s{1,}");
		double beginLatitude = Double.parseDouble(strings[10]);
		double beginLongitude = Double.parseDouble(strings[8]);
		double range = Double.parseDouble(strings[6]);
		int cells = (int) Math.ceil((Double.parseDouble(strings[12]) / 10.0));
		double f11, f12, f21, f22;

		// Math.Ceiling 20160614 q1比实际值小6
		int q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude()) / range) * cells)
				+ (int) Math.abs((beginLongitude - station.getLongitude()) / range / 10) + 2;
		int q2 = (int) Math.abs((beginLongitude - station.getLongitude()) / range % 10);

		String[] liStrings = strList[q1].trim().split("\\s+");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f12 = Double.parseDouble(liStrings[q2]);

		q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude()) / range) * cells)
				+ (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) / 10) + 2;
		q2 = (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) % 10);

		liStrings = strList[q1].trim().split("\\s+");
		f21 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f22 = Double.parseDouble(liStrings[q2]);

		double result = getValueFromInterpolated(f11, f12, f21, f22, station.getLongitude(), station.getLatitude(),
				range);
		return Double.toString(result);
	}

	/**
	 * 插值T639 风场
	 * 
	 * @param micapsData
	 * @param station
	 * @return
	 */
	public static String[] getValueLikeT639Wind(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		if (micapsData == null || station == null) {
			return null;
		}
		String[] strList = micapsData.split("\n{1,}");
		double f11, f12, f21, f22, q11, q12, q21, q22;

		double beginLat = Double.parseDouble(strList[2].split("\\s+")[3]);
		double beginLon = Double.parseDouble(strList[2].split("\\s+")[2]);
		double latCount = 0;
		String lat = strList[2].split("\\s+")[3];
		
		for (int i = 2; i < strList.length; i++) {
			if (!lat .equals(strList[i].split("\\s+")[3])) {
				latCount = i - 2;
				break;
			}
		}

		
		int q1 = (int) (((int) Math.abs(beginLat - station.getLatitude())) * latCount)
				+ (int) Math.abs((beginLon - station.getLongitude())) + 2;

		// u分量
		String[] liStrings = strList[q1].trim().split("\\s+");
		f11 = Double.parseDouble(liStrings[8]);
		q11 = Double.parseDouble(liStrings[9]);

		liStrings = strList[q1 + 1].trim().split("\\s+");
		f12 = Double.parseDouble(liStrings[8]);
		q12 = Double.parseDouble(liStrings[9]);

		q1 = (int) (((int) Math.abs(beginLat - station.getLatitude()) + 1) * latCount)
				+ (int) Math.abs((beginLon - station.getLongitude())) + 2;

		liStrings = strList[q1].trim().split("\\s+");
		f21 = Double.parseDouble(liStrings[8]);
		q21 = Double.parseDouble(liStrings[9]);

		liStrings = strList[q1 + 1].trim().split("\\s+");
		f22 = Double.parseDouble(liStrings[8]);
		q22 = Double.parseDouble(liStrings[9]);

		double value = getValueFromInterpolated(f11, f21, f12, f22, station.getLongitude(), station.getLatitude(), 1);
		double value2 = getValueFromInterpolated(q11, q21, q12, q22, station.getLongitude(), station.getLatitude(), 1);

		String[] results = new String[2];
		results[0] = Double.toString(((int) (value * 100)) / 100.0);
		results[1] = Double.toString(((int) (value2 * 100)) / 100.0);

		return results;
	}

	/**
	 * 插值T639數據
	 * 
	 * @param micapsData
	 * @param station
	 * @return
	 */
	public static String getValueLikePhysic(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		if (micapsData == null || station == null) {
			return null;
		}
		String[] strList = micapsData.split("\n{1,}");
		String[] strings = strList[2].trim().split("\\s{1,}");
		double beginLatitude = Double.parseDouble(strings[4]);
		double beginLongitude = Double.parseDouble(strings[2]);
		double range = Double.parseDouble(strings[0]);
		strings = strList[3].trim().split("\\s{1,}");
		int cells = (int) Math.ceil((Double.parseDouble(strings[0]) / 10.0));
		double f11, f12, f21, f22;

		//
		int q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude())) / range) * cells
				+ (int) Math.abs((beginLongitude - station.getLongitude()) / range / 10) + 4;
		int q2 = (int) Math.abs((beginLongitude - station.getLongitude()) / range % 10);

		String[] liStrings = strList[q1].trim().split("\\s+");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f12 = Double.parseDouble(liStrings[q2]);

		q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude())) / range) * cells
				+ (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) / 10) + 4;
		q2 = (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) % 10);

		liStrings = strList[q1].trim().split("\\s+");
		f21 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f22 = Double.parseDouble(liStrings[q2]);

		double result = getValueFromInterpolated(f12, f11, f22, f21, station.getLongitude(), station.getLatitude(),
				range);
		return Double.toString(result);
	}

	/**
	 * 物理量场uv
	 * 
	 * @param micapsData
	 * @param station
	 * @return
	 */
	public static String[] getValueLikePhysicuv(String micapsData, Station station) {

		/**
		 * 双线性插值求站点值
		 */
		if (micapsData == null || station == null) {
			return null;
		}
		String[] strList = micapsData.split("\n{1,}");
		String[] strings = strList[2].trim().split("\\s{1,}");
		double beginLatitude = Double.parseDouble(strings[4]);
		double beginLongitude = Double.parseDouble(strings[2]);
		double range = Double.parseDouble(strings[0]);
		int count = Integer.parseInt(strings[7]);
		int cells = (int) Math.ceil((Double.parseDouble(strings[6]) / 10.0));
		double f11, f12, f21, f22;

		//
		int q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude())) / range) * cells
				+ (int) Math.abs((beginLongitude - station.getLongitude()) / range / 10) + 3;
		int q2 = (int) Math.abs((beginLongitude - station.getLongitude()) / range % 10);

		// u分量
		String[] liStrings = strList[q1].trim().split("\\s+");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f12 = Double.parseDouble(liStrings[q2]);

		q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude())) / range) * cells
				+ (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) / 10) + 3;
		q2 = (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) % 10);

		liStrings = strList[q1].trim().split("\\s+");
		f21 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f22 = Double.parseDouble(liStrings[q2]);

		double value = getValueFromInterpolated(f12, f11, f22, f21, station.getLongitude(), station.getLatitude(),
				range);
		// v分量
		q1 = q1 + count * cells;
		liStrings = strList[q1].trim().split("\\s+");
		f11 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f12 = Double.parseDouble(liStrings[q2]);

		q1 = (int) (((int) Math.abs(beginLatitude - station.getLatitude())) / range) * cells
				+ (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) / 10) + 3;
		q2 = (int) ((Math.abs((beginLongitude - station.getLongitude()) / range) + 1) % 10);

		liStrings = strList[q1].trim().split("\\s+");
		f21 = Double.parseDouble(liStrings[q2]);

		liStrings = strList[q1 + cells].trim().split("\\s+");
		f22 = Double.parseDouble(liStrings[q2]);

		double value2 = getValueFromInterpolated(f12, f11, f22, f21, station.getLongitude(), station.getLatitude(),
				range);

		String[] results = new String[2];
		double result = Math.sqrt(value * value + value2 * value2);
		results[0] = Double.toString(((int) (result * 100)) / 100.0);
		result = ((270 - Math.atan2(value2, value) * 180 / Math.PI) % 360.0);
		results[1] = Double.toString(((int) (result * 100)) / 100.0);

		return results;
	}

	public static double getValueFromInterpolated(double f11, double f12, double f21, double f22, double longitude,
			double latitude, double range) {

		/**
		 * 双线性插值求站点值 四个格点数求站点数
		 */
		if (f11==9999) {
			f11=0;
		}
		if (f12==9999) {
			f12=0;
		}
		if (f21==9999) {
			f21=0;
		}
		if (f22==9999) {
			f22=0;
		}
		
		Double result;
		Double N1, E1, N2, E2;
		N1 = ((int) (latitude / range)) * range;
		E1 = ((int) (longitude / range)) * range;
		N2 = ((int) (latitude / range)) * range + range;
		E2 = ((int) (longitude / range)) * range + range;
		result = f11 * (E2 - longitude) * (N2 - latitude) / ((E2 - E1) * (N2 - N1))
				+ f21 * (longitude - E1) * (N2 - latitude) / ((E2 - E1) * (N2 - N1))
				+ f12 * (E2 - longitude) * (latitude - N1) / ((E2 - E1) * (N2 - N1))
				+ f22 * (longitude - E1) * (latitude - N1) / ((E2 - E1) * (N2 - N1));
		return ((int) (result * 100)) / 100.0;

	}
}
