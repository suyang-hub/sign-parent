//package com.chaboshi.api.sign.detection.util;
//
//import org.junit.Test;
//
//public class TestCBSInsurance {
//	private static final String userid = "";
//	private static final String keySecret = "";
//	private static final String orderNo = "";
//
//	@Test
//	public void testgetAllInsuranceCity() {
//		CBSInsurance cbsInsurance = CBSInsurance.getInstance(userid, keySecret);
//		String cityResult = cbsInsurance.getAllInsuranceCity();
//		System.out.println(cityResult);
//	}
//
//	@Test
//	public void testBuyInsuranceOrder() {
//		CBSInsurance cbsInsurance = CBSInsurance.getInstance(userid, keySecret);
//		String name = "测试创建订单mmm";
//		String phone = "13581565662";
//		String cityId = "424";
//		String address = "凯旋城检测接口测试";
//		String type = "101";
//		String vin = "LGWEF4A55GH006367";
//		String callbackurl = null;
//		String buyInsuranceOrder = cbsInsurance.buyInsuranceOrder(name, phone, cityId, address, type, callbackurl, vin);
//		System.out.println(buyInsuranceOrder);
//	}
//	
//	@Test
//	public void testGetInsuranceData() {
//		String result = CBSInsurance.getInstance(userid, keySecret).getInsuranceResult(orderNo);
//		System.out.println(result);
//	}
//
//	
//	@Test
//	public void testGetInsuranceReportUrl() {
//		String result = CBSInsurance.getInstance(userid, keySecret).getInsuranceReportUrl(orderNo);
//		System.out.println(result);
//	}
//	
//	
//}
