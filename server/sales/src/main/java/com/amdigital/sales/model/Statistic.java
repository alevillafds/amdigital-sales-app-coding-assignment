package com.amdigital.sales.model;

import java.util.Date;

/**
 * DTO class to store Statistic Result.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 *
 */
public class Statistic {

	private String item;

	private Date month;

	private int total_quantity;

	private int total_revenue;

	private double average_per_sale;
	

	public String getItem() {
		return item;
	}

	public Date getMonth() {
		return month;
	}

	public int getTotal_quantity() {
		return total_quantity;
	}

	public int getTotal_revenue() {
		return total_revenue;
	}

	public double getAverage_per_sale() {
		return average_per_sale;
	}

	public Statistic() {
		super();
	}

	private Statistic(String item, Date month, int total_quantity, int total_revenue, double average_per_sale) {
		super();
		this.item = item;
		this.month = month;
		this.total_quantity = total_quantity;
		this.total_revenue = total_revenue;
		this.average_per_sale = average_per_sale;
	}

}
