package com.amdigital.sales.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.amdigital.sales.dao.SalesRepository;
import com.amdigital.sales.dao.StatisticRepository;
import com.amdigital.sales.grpg.Item;
import com.amdigital.sales.grpg.Monthly;
import com.amdigital.sales.grpg.Sale;
import com.amdigital.sales.grpg.SaleResponse;
import com.amdigital.sales.grpg.SaleServiceGrpc.SaleServiceImplBase;
import com.amdigital.sales.grpg.Statistic;
import com.amdigital.sales.stream.SalesStream;
import com.amdigital.sales.utils.DateUtilities;
import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * Grpc Service to manage sales.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 */
@GrpcService
public class SalesService extends SaleServiceImplBase {

	@Autowired
	StatisticRepository statisticRepository;


	/** Retrieve a statistic report. */
	@Override
	public void getStatistics(Empty request, StreamObserver<Statistic> statisticObserver) {
		List<com.amdigital.sales.model.Statistic> statisticSalesByItem = this.statisticRepository
				.getAllStatisticSalesGroupByItem();
		
		Map<String, Item> items = new HashMap<String,Item>();
		for (com.amdigital.sales.model.Statistic i : statisticSalesByItem) {
			Map<String, Monthly> statisticSalesByMonth = this.statisticRepository
					.getStatisticSalesByItemGroupByMonth(i.getItem()).stream()
					.collect(Collectors.toMap(stat -> DateUtilities.dateToYearAndMonthString(stat.getMonth()),
							stat -> Monthly.newBuilder().setAveragePerSale(stat.getAverage_per_sale())
									.setTotalQuantity(stat.getTotal_quantity()).setTotalRevenue(stat.getTotal_revenue())
									.build()));
			items.put(i.getItem(), Item.newBuilder().setAveragePerSale(i.getAverage_per_sale()).setTotalQuantity(i.getTotal_quantity())
			.setTotalRevenue(i.getTotal_revenue()).putAllMonthly(statisticSalesByMonth).build());
		}
		
		Statistic statistic = Statistic.newBuilder().putAllItem(items).build();

		statisticObserver.onNext(statistic);
		statisticObserver.onCompleted();
	}

	/** Receive sale items as a stream. */
	@Override
	public StreamObserver<Sale> importSales(final StreamObserver<SaleResponse> saleResponse) {
		return new SalesStream(saleResponse);
	}

}
