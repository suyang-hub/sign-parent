package com.chaboshi.api.sign;

import org.junit.Test;

import com.chaboshi.util.CBS;

import java.util.Map;

public class TestCBS {

	private static final String userId = "81";
	private static final String keySecret = "b22148ef09978c7c6f584b7154ed4d0e";
	private static final String orderNo = "889e9cd3c6564ba885863dd9294726fe";
	
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
	public void testGetReport() {
		Map<String, String> newReportJson = CBS.getInstance(userId, keySecret).getNewReportUrl(orderNo);
		System.out.println(newReportJson);
	}

	
	
	
	
	
}

