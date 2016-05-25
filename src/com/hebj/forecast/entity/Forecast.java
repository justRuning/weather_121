package com.hebj.forecast.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Forecast {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn
	private Station station;

	private String forecastType;
	private Date beginTime;
	private int hour;
	private int age;
	private String highTem;
	private String lowTem;
	private String highTemRevised;
	private String lowTemRevised;
	private String windDirectionDay;
	private String windVelocityDay;
	private String windDirectionNight;
	private String windVelocityNight;
	private String RHDay;
	private String RHNight;
	private String rainDay;
	private String rainNight;
	private String skyDay;
	private String skyNight;

	public Forecast() {
	}

	@Override
	public String toString() {
		return "Forecast [id=" + id + ", station=" + station + ", forecastType=" + forecastType + ", beginTime="
				+ beginTime + ", hour=" + hour + ", age=" + age + ", highTem=" + highTem + ", lowTem=" + lowTem
				+ ", highTemRevised=" + highTemRevised + ", lowTemRevised=" + lowTemRevised + ", windDirectionDay="
				+ windDirectionDay + ", windVelocityDay=" + windVelocityDay + ", windDirectionNight="
				+ windDirectionNight + ", windVelocityNight=" + windVelocityNight + ", RHDay=" + RHDay + ", RHNight="
				+ RHNight + ", rainDay=" + rainDay + ", rainNight=" + rainNight + ", skyDay=" + skyDay + ", skyNight="
				+ skyNight + "]";
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

	public String getForecastType() {
		return forecastType;
	}

	public void setForecastType(String forecastType) {
		this.forecastType = forecastType;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHighTem() {
		return highTem;
	}

	public void setHighTem(String highTem) {
		this.highTem = highTem;
	}

	public String getLowTem() {
		return lowTem;
	}

	public void setLowTem(String lowTem) {
		this.lowTem = lowTem;
	}

	public String getHighTemRevised() {
		return highTemRevised;
	}

	public void setHighTemRevised(String highTemRevised) {
		this.highTemRevised = highTemRevised;
	}

	public String getLowTemRevised() {
		return lowTemRevised;
	}

	public void setLowTemRevised(String lowTemRevised) {
		this.lowTemRevised = lowTemRevised;
	}

	public String getWindDirectionDay() {
		return windDirectionDay;
	}

	public void setWindDirectionDay(String windDirectionDay) {
		this.windDirectionDay = windDirectionDay;
	}

	public String getWindVelocityDay() {
		return windVelocityDay;
	}

	public void setWindVelocityDay(String windVelocityDay) {
		this.windVelocityDay = windVelocityDay;
	}

	public String getWindDirectionNight() {
		return windDirectionNight;
	}

	public void setWindDirectionNight(String windDirectionNight) {
		this.windDirectionNight = windDirectionNight;
	}

	public String getWindVelocityNight() {
		return windVelocityNight;
	}

	public void setWindVelocityNight(String windVelocityNight) {
		this.windVelocityNight = windVelocityNight;
	}

	public String getRHDay() {
		return RHDay;
	}

	public void setRHDay(String rHDay) {
		RHDay = rHDay;
	}

	public String getRHNight() {
		return RHNight;
	}

	public void setRHNight(String rHNight) {
		RHNight = rHNight;
	}

	public String getRainDay() {
		return rainDay;
	}

	public void setRainDay(String rainDay) {
		this.rainDay = rainDay;
	}

	public String getRainNight() {
		return rainNight;
	}

	public void setRainNight(String rainNight) {
		this.rainNight = rainNight;
	}

	public String getSkyDay() {
		return skyDay;
	}

	public void setSkyDay(String skyDay) {
		this.skyDay = skyDay;
	}

	public String getSkyNight() {
		return skyNight;
	}

	public void setSkyNight(String skyNight) {
		this.skyNight = skyNight;
	}

}
