package com.amdigital.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.amdigital.sales.configuration.SalesServiceConfiguration;
import com.amdigital.sales.grpg.Item;
import com.amdigital.sales.grpg.Monthly;
import com.amdigital.sales.grpg.Statistic;
import com.amdigital.sales.service.SalesService;
import com.google.protobuf.Empty;

import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@SpringJUnitConfig(classes = {SalesServiceConfiguration.class})
class SalesApplicationTests {
	
	@Autowired SalesService salesService;

	@Test
	void contextLoads() {
	}
	
	@Test
    void t01_getStatistics() throws Exception {
        Empty request = Empty.newBuilder().build();
        
		Monthly monthly = Monthly.newBuilder().setAveragePerSale(2.0).setTotalQuantity(2).setTotalRevenue(3).build();
		Item item = Item.newBuilder().setAveragePerSale(3.4).putMonthly("12-2023", monthly).setTotalQuantity(30)
				.setTotalRevenue(45).build();
		Statistic statisticExpected = Statistic.newBuilder().putItem("apple", item).build();
        
        StreamRecorder<Statistic> responseObserver = StreamRecorder.create();
        salesService.getStatistics(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        assertNull(responseObserver.getError());
        List<Statistic> results = responseObserver.getValues();
        assertEquals(1, results.size());
        Statistic response = results.get(0);
        assertEquals(statisticExpected, response);
    }

}
