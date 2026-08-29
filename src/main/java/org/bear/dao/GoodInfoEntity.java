package org.bear.dao;

import java.util.Date;

import javax.persistence.Id;

public class GoodInfoEntity {
	@Id
	Date exchangeDate;
	@Id
	String stockId;
	double price;
	String day_k;
	String day_d;
	String week_k;
	String week_d;
	String day_kd_20;
	String week_kd_20;
	public Date getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDay_k() {
		return day_k;
	}
	public void setDay_k(String day_k) {
		this.day_k = day_k;
	}
	public String getDay_d() {
		return day_d;
	}
	public void setDay_d(String day_d) {
		this.day_d = day_d;
	}
	public String getWeek_k() {
		return week_k;
	}
	public void setWeek_k(String week_k) {
		this.week_k = week_k;
	}
	public String getWeek_d() {
		return week_d;
	}
	public void setWeek_d(String week_d) {
		this.week_d = week_d;
	}
	public String getDay_kd_20() {
		return day_kd_20;
	}
	public void setDay_kd_20(String day_kd_20) {
		this.day_kd_20 = day_kd_20;
	}
	public String getWeek_kd_20() {
		return week_kd_20;
	}
	public void setWeek_kd_20(String week_kd_20) {
		this.week_kd_20 = week_kd_20;
	}
	
}
