package com.amdigital.sales.service;

import com.amdigital.sales.grpg.Item;
import com.amdigital.sales.grpg.Monthly;
import com.amdigital.sales.grpg.Sale;
import com.amdigital.sales.grpg.SaleResponse;
import com.amdigital.sales.grpg.SaleServiceGrpc.SaleServiceImplBase;
import com.amdigital.sales.stream.SalesStream;
import com.amdigital.sales.grpg.Statistic;
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

	/** Retrieve a statistic report.*/
	@Override
	public void getStatistics(Empty request, StreamObserver<Statistic> statisticObserver) {
		Monthly monthly = Monthly.newBuilder().setAveragePerSale(2.0).setTotalQuantity(2).setTotalRevenue(3).build();
		Item item = Item.newBuilder().setAveragePerSale(3.4).putMonthly("12-2023", monthly).setTotalQuantity(30)
				.setTotalRevenue(45).build();
		Statistic statistic = Statistic.newBuilder().putItem("apple", item).build();
		
		statisticObserver.onNext(statistic);
		statisticObserver.onCompleted();
	}

	/** Receive sale items as a stream.*/
	@Override
	public StreamObserver<Sale> importSales(final StreamObserver<SaleResponse> saleResponse) {
		return new SalesStream(saleResponse);
	}

}
