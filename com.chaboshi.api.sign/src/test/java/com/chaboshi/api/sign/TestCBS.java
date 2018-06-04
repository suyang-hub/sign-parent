package com.chaboshi.api.sign;

import org.junit.Test;

import com.chaboshi.util.CBS;

import java.util.Map;

public class TestCBS {

	private static final String userId = "109263";
	private static final String keySecret = "8d748ea2e9ac51b80eddcd062097ff9d";
	private static final String orderNo = "cd5c747036764b6c9dd3bf441a3d0c13";
	
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

	@Test
	public void testGetReportStatus() {
		String newReportJson = CBS.getInstance(userId, keySecret).getReportJson(orderNo);
		System.out.println(newReportJson);
	}


	@Test
	public void testGetReport() {
		Map<String, String> newReportJson = CBS.getInstance(userId, keySecret).getNewReportUrl(orderNo);
		System.out.println(newReportJson);
	}

	
	
	
	
	
}

