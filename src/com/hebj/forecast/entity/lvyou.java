package com.hebj.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class lvyou {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;

	public String name;
	public String wind;
	public String temp;
	public String city;
	public String sky;

	@Override
	public String toString() {
		return "lvyou [id=" + id + ", name=" + name + ", wind=" + wind + ", temp=" + temp + ", city=" + city + ", sky="
				+ sky + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

}
