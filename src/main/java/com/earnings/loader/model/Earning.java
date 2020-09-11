package com.earnings.loader.model;

import javax.persistence.*;

@Entity
@Table(name = "earnings")
public class Earning {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name = "AnnouncementTime")
	private String announcementTime;

	@Column(name = "Company")
	private String company;

	@Column(name = "Exchange")
	private String exchange;

	@Column(name = "Symbol")
	private String symbol;

	@Column(name = "Currency")
	private String currency;

	@Column(name = "Term")
	private String term;

	@Column(name = "PreviousClose")
	private float previousClose;

	public Earning() {

	}

	public Earning(String announcementTime, String company, String exchange, String symbol, String currency, String term, float previousClose) {
		this.announcementTime = announcementTime;
		this.company = company;
		this.exchange = exchange;
		this.symbol = symbol;
		this.currency = currency;
		this.term = term;
		this.previousClose = previousClose;
	}

	public Earning(Long id, String announcementTime, String company, String exchange, String symbol, String currency, String term, float previousClose) {
		this.id = id;
		this.announcementTime = announcementTime;
		this.company = company;
		this.exchange = exchange;
		this.symbol = symbol;
		this.currency = currency;
		this.term = term;
		this.previousClose = previousClose;
	}

	public long getId() { return id; }

	public void setId(long id) { this.id = id; }

	public String getAnnouncementTime() {
		return announcementTime;
	}

	public void setAnnouncementTime(String announcementTime) {
		this.announcementTime = announcementTime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public float getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(float previousClose) {
		this.previousClose = previousClose;
	}

	@Override
	public String toString() {
		return "Earning{" +
				"id=" + id +
				", announcementTime='" + announcementTime + '\'' +
				", company='" + company + '\'' +
				", exchange='" + exchange + '\'' +
				", symbol='" + symbol + '\'' +
				", currency='" + currency + '\'' +
				", term='" + term + '\'' +
				", previousClose=" + previousClose +
				'}';
	}
}
