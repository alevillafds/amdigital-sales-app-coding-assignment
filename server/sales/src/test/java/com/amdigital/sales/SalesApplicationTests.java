package com.amdigital.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import com.amdigital.sales.grpg.Item;
import com.amdigital.sales.grpg.Monthly;
import com.amdigital.sales.grpg.Statistic;
import com.amdigital.sales.model.Sale;
import com.amdigital.sales.service.SalesService;
import com.amdigital.sales.utils.DateUtilities;
import com.google.protobuf.Empty;

import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@ComponentScan({"com.amdigital.sales.dao"})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestEnvironment.class)
class SalesApplicationTests {
	
	@Autowired SalesService salesService;

	@Test
	void contextLoads() {
	}
	
	@Test
	@Order(1)
    void t01_getStatistics() throws Exception {
        Empty request = Empty.newBuilder().build();
        
        StreamRecorder<Statistic> responseObserver = StreamRecorder.create();
        salesService.getStatistics(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        assertNull(responseObserver.getError());
        List<Statistic> results = responseObserver.getValues();
        assertEquals(1, results.size());
        Statistic response = results.get(0);
        assertTrue(response.containsItem("avocado"));
        assertTrue(response.containsItem("apple"));
        assertTrue(response.containsItem("orange"));
        assertTrue(response.containsItem("banana"));
        
        Item banana = response.getItemOrThrow("banana");
        assertEquals(2, banana.getTotalQuantity());
        assertEquals(3, banana.getTotalRevenue());
        assertEquals(2, banana.getAveragePerSale());
        
        Map<String, Monthly> bananaMonthly = banana.getMonthlyMap();
        assertTrue(bananaMonthly.containsKey("2020-10"));
        assertEquals(2, bananaMonthly.get("2020-10").getTotalQuantity());
        assertEquals(3, bananaMonthly.get("2020-10").getTotalRevenue());
        assertEquals(2, bananaMonthly.get("2020-10").getAveragePerSale());
    }

}
