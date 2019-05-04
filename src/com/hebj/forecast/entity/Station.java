package com.hebj.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;

/**
 * @author hebj
 *
 */
@Entity
public class Station {

	/**
	 * 观测站点资料
	 * 
	 * @author hebj
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String stationId;
	private String stationName;
	private double longitude;
	private double latitude;
	private String city;
//	private Boolean readData;

	public Station() {
	};

	public Station(String stationName) {
		this.stationName = stationName;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", stationId=" + stationId + ", stationName=" + stationName + ", longitude="
				+ longitude + ", latitude=" + latitude + ", city=" + city + ", readData=]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


}