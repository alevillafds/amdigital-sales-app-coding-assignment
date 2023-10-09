package com.amdigital.sales.stream;

import java.text.ParseException;

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
	
	@Autowired
	SalesRepository salesRepository;
	
	public SalesStream(StreamObserver<SaleResponse> responseObserver) {
		this.responseObserver = responseObserver;
		this.count = 0;
	}

	@Override
	public void onNext(Sale value) {
		logger.info(String.format("Item name %s", value.getItem()));
		logger.info(String.format("Date %s", value.getDate()));
		try {
			this.salesRepository.save(com.amdigital.sales.model.Sale.fromGrpgSale(value));
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
	}

}
