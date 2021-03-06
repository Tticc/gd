package gd.web.entity.viewModel;

import java.util.HashSet;

//for build excel file
public class DataTable {

	private String dateTime;
	
	private int staId;
	
	private String staBrief;
	
	private String direction;
	
	private int total;
	
	private double average;
	
	private String datas;
	
	private HashSet<String> briefSet;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getStaId() {
		return staId;
	}

	public void setStaId(int staId) {
		this.staId = staId;
	}

	public String getStaBrief() {
		return staBrief;
	}

	public void setStaBrief(String staBrief) {
		this.staBrief = staBrief;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public HashSet<String> getBriefSet() {
		return briefSet;
	}

	public void setBriefSet(HashSet<String> briefSet) {
		this.briefSet = briefSet;
	}
	
}
