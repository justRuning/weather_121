package com.hebj.forecast.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WeatherActual {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn
	private Station station;

	private String name;
	private String stationID;
	private Date time;
	private int hour;
	private String tem;
	private String surfaceTemp;
	private String maxTemp;
	private String minTemp;
	private String maxTemp24;
	private String minTemp24;
	private String relativeHumidity;
	private String water;
	private String water3;
	private String water6;
	private String water12;
	private String water24;
	private String windDirection2min;
	private String windVelocity2min;
	private String windDirection10min;
	private String windVelocity10min;
	private String barometricPressure;
	private String visibility;

	public WeatherActual() {
	}

	@Override
	public String toString() {
		return "WeatherActual [id=" + id + ", name=" + name + ", stationID=" + stationID + ", time=" + time + ", hour="
				+ hour + ", tem=" + tem + ", surfaceTemp=" + surfaceTemp + ", maxTemp=" + maxTemp + ", minTemp="
				+ minTemp + ", maxTemp24=" + maxTemp24 + ", minTemp24=" + minTemp24 + ", relativeHumidity="
				+ relativeHumidity + ", water=" + water + ", water3=" + water3 + ", water6=" + water6 + ", water12="
				+ water12 + ", water24=" + water24 + ", windDirection2min=" + windDirection2min + ", windVelocity2min="
				+ windVelocity2min + ", windDirection10min=" + windDirection10min + ", windVelocity10min="
				+ windVelocity10min + ", barometricPressure=" + barometricPressure + ", visibility=" + visibility + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getTem() {
		return tem;
	}

	public void setTem(String tem) {
		this.tem = tem;
	}

	public String getSurfaceTemp() {
		return surfaceTemp;
	}

	public void setSurfaceTemp(String surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}

	public String getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}

	public String getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}

	public String getMaxTemp24() {
		return maxTemp24;
	}

	public void setMaxTemp24(String maxTemp24) {
		this.maxTemp24 = maxTemp24;
	}

	public String getMinTemp24() {
		return minTemp24;
	}

	public void setMinTemp24(String minTemp24) {
		this.minTemp24 = minTemp24;
	}

	public String getRelativeHumidity() {
		return relativeHumidity;
	}

	public void setRelativeHumidity(String relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public String getWater3() {
		return water3;
	}

	public void setWater3(String water3) {
		this.water3 = water3;
	}

	public String getWater6() {
		return water6;
	}

	public void setWater6(String water6) {
		this.water6 = water6;
	}

	public String getWater12() {
		return water12;
	}

	public void setWater12(String water12) {
		this.water12 = water12;
	}

	public String getWater24() {
		return water24;
	}

	public void setWater24(String water24) {
		this.water24 = water24;
	}

	public String getWindDirection2min() {
		return windDirection2min;
	}

	public void setWindDirection2min(String windDirection2min) {
		this.windDirection2min = windDirection2min;
	}

	public String getWindVelocity2min() {
		return windVelocity2min;
	}

	public void setWindVelocity2min(String windVelocity2min) {
		this.windVelocity2min = windVelocity2min;
	}

	public String getWindDirection10min() {
		return windDirection10min;
	}

	public void setWindDirection10min(String windDirection10min) {
		this.windDirection10min = windDirection10min;
	}

	public String getWindVelocity10min() {
		return windVelocity10min;
	}

	public void setWindVelocity10min(String windVelocity10min) {
		this.windVelocity10min = windVelocity10min;
	}

	public String getBarometricPressure() {
		return barometricPressure;
	}

	public void setBarometricPressure(String barometricPressure) {
		this.barometricPressure = barometricPressure;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

}
