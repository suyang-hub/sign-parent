package com.chaboshi.api.sign;

import org.junit.Test;

import com.chaboshi.util.CBS;

public class TestCBS {

	private static final String userId = "49";
	private static final String keySecret = "70d6499403bb13acd294b8822cf41b4f";
	private static final String orderNo = "06a1bd85f20340a0ae17810f38083ed4";
	
	@Test
	public void testBuyReport() {
		String vin = "WBAKB41029C321863";
		String enginNo = "vcdasd";
		String carNo = "京HJDDD";
		String buyReport = CBS.getInstance(userId, keySecret).getBuyReport(vin, enginNo, carNo, null);
		System.out.println(buyReport);
	}
	
	
	@Test
	public void testGetReportJson() {
		String newReportJson = CBS.getInstance(userId, keySecret).getNewReportJson(orderNo);
		System.out.println(newReportJson);
	}
	
	
	
	
	
	
}

