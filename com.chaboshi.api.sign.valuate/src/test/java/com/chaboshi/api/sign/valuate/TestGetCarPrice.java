package com.chaboshi.api.sign.valuate;

import org.junit.Test;

import com.chaboshi.api.sign.valuate.util.CBS;

public class TestGetCarPrice {
	
	@Test
	public void testGetCarPrice() {
		String userId = "49";
		String keySecret = "06602faa39a5438ab10016661f9607a7";
		//String vin = "LBV3M2100EMC60216";
		String vin = null;
		Long modelid = 200l;
		String regdate = "2016-04-18";
		String makedate = "2016-04-18";
		Long mile = 10000l;
		String cityid = "200";
		String color = "heise";
		String interior = "A";
		String surface = "A";
		String workstate = "A";
		int transferTimes = 2;
		String carPrice = CBS.getInstance(userId, keySecret, true).getCarPrice(vin, modelid, regdate, makedate, mile, cityid, color, interior, surface, workstate, transferTimes);
		System.out.println(carPrice);
	}
	
	
}
