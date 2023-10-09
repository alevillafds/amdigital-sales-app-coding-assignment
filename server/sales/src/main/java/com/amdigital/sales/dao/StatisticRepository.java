package com.amdigital.sales.dao;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.amdigital.sales.model.Statistic;

/**
 * Repository to manage statistics of {@link com.amdigital.sales.model.Sales}.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 *
 */
@Repository
public interface StatisticRepository extends CassandraRepository<Statistic, String>{
	
	@Query("SELECT item,month,SUM(quantity) as total_quantity, SUM(price) as total_revenue, AVG(quantity) as average_per_sale FROM sales GROUP BY item;")
	public List<Statistic> getAllStatisticSalesGroupByItem();
	
	@Query("SELECT item,month,SUM(quantity) as total_quantity, SUM(price) as total_revenue, AVG(quantity) as average_per_sale FROM sales WHERE item=?0 GROUP BY month;")
	public List<Statistic> getStatisticSalesByItemGroupByMonth(String item);
}
