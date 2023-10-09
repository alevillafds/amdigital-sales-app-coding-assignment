package com.amdigital.sales.model;

import java.text.ParseException;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.amdigital.sales.utils.DateUtilities;

/**
 * DTO class to store Sales.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 *
 */
@Table(value = "sales")
public class Sale {

	/**
	 * Generates a sales from grpg sale.
	 *
	 * @param saleGrpg Sale in proto format
	 * @return a Sale
	 * @throws ParseException 
	 */
	public static Sale fromGrpgSale(com.amdigital.sales.grpg.Sale saleGrpg) throws ParseException {
//		Date dateMilliseconds = DateUtilities.stringDateToDate(saleGrpg.getDate());
		return new Sale(saleGrpg.getItem(), DateUtilities.dateToStartOfMonth(new Date(saleGrpg.getDate())), new Date(saleGrpg.getDate()), saleGrpg.getQuantity(), saleGrpg.getPrice());
	}

	public static Sale of(String item, Date month, Date date, int quantity, double price) {
		return new Sale(item, month, date, quantity, price);
	}

	@PrimaryKey
	private String item;

	@Column("month")
	private Date month;

	@Column("date")
	private Date date;

	@Column("quantity")
	private int quantity;

	@Column("price")
	private double price;
	

	public String getItem() {
		return item;
	}

	public Date getMonth() {
		return month;
	}

	public Date getDate() {
		return date;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public Sale() {
		super();
	}

	private Sale(String item, Date month, Date date, int quantity, double price) {
		super();
		this.item = item;
		this.month = month;
		this.date = date;
		this.quantity = quantity;
		this.price = price;
	}

}
