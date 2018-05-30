package com.chaboshi.api.sign.warranty;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.chaboshi.api.sign.warranty.util.CBS;

public class CBSTest {
	private static String userid="49";
	private static String keySecret = "70d6499403bb13acd294b8822cf41b4f";

	@Before
	public void init() {
	}


	@Test
	public void testGetAllCity() {
		String allInsuranceCity = CBS.getInstance(userid, keySecret, false).getAllInsuranceCity();
		System.out.println(allInsuranceCity);
	}


	@Test
	public void testCreatePreDetection() {
		String name = "前置延保检测测试";
		String phone = "13581565662";
		String cityId = "424";
		String address = "测试延保前置检测订单";
		String type = "201";
		String vin = "";
		String callbackUrl = null;
		String orderData = CBS.getInstance(userid, keySecret, false).buyInsuranceOrder(name, phone, cityId, address, type, callbackUrl, null);
		System.out.println(orderData);
	}

	@Test
	public void testGetBrandByVin() {
		String vin = "SCFHDDAJ0BAF02157";
		String brandByVin = CBS.getInstance(userid, keySecret, false).getBrandByVin(vin);
		System.out.println(brandByVin);
	}

	@Test
	public void testSeriesByBrandId() {
		String brandId = "166";
		String seriesByBrandId = CBS.getInstance(userid, keySecret, false).getSeriesByBrandId(brandId);
		System.out.println(seriesByBrandId);
	}


	@Test
	public void testGetModelListByBrandIdAndSeriesId() {
		String brandId = "166";
		String seriesId = "4";
		String modelByBrandIdAndSeriesId = CBS.getInstance(userid, keySecret, false).getModelByBrandIdAndSeriesId(brandId, seriesId);
		System.out.println(modelByBrandIdAndSeriesId);
	}


	/**
	 *   必检测     String vin = "LFV3A28K5D3040678";    String modelId = "118278";   String regTime = "2017-03-10";   String watchMiles = "1000";
	 *   免检         String vin = "LSVND2187C2123214";    String modelId = "108994";   String regTime = "2016-12-10";   String watchMiles = "10000";
	 *   不保          String vin = "SCFHDDAJ0BAF02157";    String modelId = "117482";   String regTime = "2016-03-10";   String watchMiles = "1000";
	 */
	@Test
	public void testCheckWarranty() {
		Long t1 = System.currentTimeMillis();
		//String vin = "LSVN02187EN035096";
		String vin = "LSVND2187C2123214";
		String modelId = "108994";
		String regTime = "2016-12-10";
		String watchMiles = "10000";
		String modelByBrandIdAndSeriesId = CBS.getInstance(userid, keySecret, false).checkWarranty(vin, modelId, regTime, watchMiles);
		Long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		System.out.println(modelByBrandIdAndSeriesId);
	}
	
	
	
	@Test
	public void testCreateWarrantyOrder() {
		Long t1 = System.currentTimeMillis();
		//String vin = "LSVN02187EN035096";
		String vin = "LSVND2187C2123214";
		String modelId = "108994";
		String regTime = "2016-12-10";
		String watchMiles = "10000";
		String phone = "13581565662";
		String name = "test";
		String carno = "京A4567C";
		String engineNo = "7011618";
		String packages = "341";
		String callbackurl = null;
		String orderResult = CBS.getInstance(userid, keySecret, false).createWarrantyOrder(vin, modelId, regTime, watchMiles, phone, name, callbackurl, carno, engineNo, packages);
		Long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		System.out.println(orderResult);
	}
	
	
	@Test
	public void testGetStatus() {
		String orderNo = "4abc980a2dd943e4b9f44424a9e2f3c8";
		String result = CBS.getInstance(userid, keySecret, false).getInsuranceStatus(orderNo);
		System.out.println(result);
	}
	

	@Test
	public void testSign() throws UnsupportedEncodingException {
		String orderNo = "4abc980a2dd943e4b9f44424a9e2f3c8";
		String signUrl = "http://api.cjabo.com/report?assaf=fdsa";
		String result = CBS.getInstance(userid, keySecret, false).sign(signUrl, orderNo);
		System.out.println(result);
	}
	
	
}
