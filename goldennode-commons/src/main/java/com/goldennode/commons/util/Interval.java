package com.goldennode.commons.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Interval implements Comparable<Interval> {

	private Date endDate;
	private List<Object> data = new ArrayList<Object>();
	private String intervalString;
	private Date startDate;
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Interval() {
		//
	}

	public int getDataSize() {
		return data.size();
	}

	public Interval(Date startDate, Date endDate, String intervalString) {
		if ((startDate == null) || (endDate == null)) {
			throw new NullPointerException("Start date or end date is null");
		}
		if (startDate.after(endDate)) {
			throw new RuntimeException("Start date can not be greater than enddate");
		}
		this.startDate = startDate;
		this.endDate = endDate;
		this.intervalString = intervalString;
	}

	public void addData(Object data) {
		this.data.add(data);
	}

	@Override
	public int compareTo(Interval other) {
		return startDate.compareTo(other.startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Interval)) {
			return false;
		}
		Interval other = (Interval) obj;
		return startDate.compareTo(other.startDate) == 0;
	}

	public Date getEndDate() {
		return endDate;
	}

	//@JsonIgnore
	public List<Object> getData() {
		return data;
	}

	public String getIntervalString() {
		return intervalString;
	}

	public Date getStartDate() {
		return startDate;
	}

	@Override
	public int hashCode() {
		return (startDate.toString()).hashCode();
	}

}
