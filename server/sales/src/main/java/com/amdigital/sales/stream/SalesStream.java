package com.amdigital.sales.stream;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amdigital.sales.dao.SalesRepository;
import com.amdigital.sales.grpg.Sale;
import com.amdigital.sales.grpg.SaleResponse;

import io.grpc.stub.StreamObserver;

/**
 * Grpc Stream implementation to manage sales stream messages.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 */
public class SalesStream implements StreamObserver<Sale>{
	private static final Logger logger = LoggerFactory.getLogger(SalesStream.class.getName());
	private StreamObserver<SaleResponse> responseObserver;
	private int count;
	private List<com.amdigital.sales.model.Sale> sales = new ArrayList<>();
	
	@Autowired
	SalesRepository salesRepository;
	
	public SalesStream(StreamObserver<SaleResponse> responseObserver) {
		this.responseObserver = responseObserver;
		this.count = 0;
	}

	@Override
	public void onNext(Sale saleGrpg) {
		logger.info(String.format("Item name %s", saleGrpg.getItem()));
		logger.info(String.format("Date %s", saleGrpg.getDate()));
		logger.info(String.format("Price name %s", saleGrpg.getPrice()));
		logger.info(String.format("Quantity %s", saleGrpg.getQuantity()));
		
		com.amdigital.sales.model.Sale sale;

		try {
			sale = com.amdigital.sales.model.Sale.fromGrpgSale(saleGrpg);
			if (sale != null) this.sales.add(sale);
			else logger.info("Sale is null");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		count++;
	}

	@Override
	public void onError(Throwable t) {
		logger.warn("error:{}", t.getMessage());
	}

	@Override
	public void onCompleted() {
		SaleResponse response = SaleResponse.newBuilder().setSalesRecived(count).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		this.salesRepository.saveAll(sales);
	}

}
