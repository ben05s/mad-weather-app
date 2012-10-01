package com.example.madex01.Util;

public class Weather {

	private String city;
	private String country;
	private String temp;
	private String tempUnit;
	private String condition;
	
	/** default constructor */
	public Weather() {
		
	}
	
	/** full constructor */
	public Weather(String city, String country, String temp, String tempUnit, String condition) {
		this.city = city;
		this.country = country;
		this.temp = temp;
		this.tempUnit = tempUnit;
		this.condition = condition;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getTempUnit() {
		return tempUnit;
	}
	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}
	
	@Override
	public String toString() {
		return ("Temp=" + temp + " " + tempUnit + " Condition=" + condition +
				" City=" + city);
	}
}
