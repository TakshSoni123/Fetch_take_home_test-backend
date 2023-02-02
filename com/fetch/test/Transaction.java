package com.fetch.test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	private String payer;
	private long points;
	private Date timestamp;
	
	public Transaction(String payer, long points, String timestamp) throws ParseException {
		this.payer = payer;
		this.points = points;
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timestamp);

	}
	
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) throws ParseException {
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timestamp);
	}
	
	public static Transaction createTransaction(String payer, String points, String timestamp) throws ParseException {
		Transaction t = new Transaction();
		
		t.setPayer(payer);
		t.setPoints(Integer.valueOf(points));
		t.setTimestamp(timestamp);
		
		return t;
	}
	
}
