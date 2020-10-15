package com.chaboshi.builder;

import org.junit.Test;

import java.util.HashMap;

public class CBSBuilderTest {

	@Test
	public void newCBSBuilder() {
		String userId = "146574";
		String key = "a81cd13aa735140fd72c7ab402a2d98f";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, key, false);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orderno", "1002461834566983680");
		String s = cbsBuilder.sendPost("/api/report/getInfo", params);
		System.out.println(s);
	}


	@Test
	public void newCBSBuilder1() {
		String userId = "146574";
		String key = "a81cd13aa735140fd72c7ab402a2d98f";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, key, true);
		String s = cbsBuilder.sendPost("/evaluate/getallcity");
		System.out.println(s);
	}

	@Test
	public void getAllBrand() {
		String userId = "146574";
		String key = "a81cd13aa735140fd72c7ab402a2d98f";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, key, true);
		String s = cbsBuilder.sendPost("/evaluate/getallbrand");
		System.out.println(s);
	}



	@Test
	public void testBuyReport() {
		final String userId = "123";
		final String keySecret = "f917a1c66fc2b91f85f4f07b12000136";
		String vin = "LE4WG4CB0FL045502";
//		String enginNo = "vcdasd";
//		String carNo = "京HJDDD";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, keySecret, false);
		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("vin", vin);
		params.put("ordeid", "f81abb312313b1238808");
//		params.put("enginno", "");
//		params.put("carno", "");
		String s = cbsBuilder.sendPost("/report/buy_report", params);
		System.out.println(s);
	}

	@Test
	public void test1() {
		final String userId = "104";
		final String keySecret = "cdb7cee9495f159710cc6280528c3868";
		String vin = "WBAKB41029C321863";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, keySecret, true);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("vin", vin);
		String s = cbsBuilder.sendGet("/report/check_brand", params);
		System.out.println(s);
	}

	@Test
	public void order() {
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder("106977", "5347e002ac91e308560786141caf359f", false);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("vin", "LBEYFAKD0BY021276");
		params.put("modelid", 90999);
		params.put("regtime", "2015-9-23");
		params.put("watchmiles", "12345");
		params.put("phone", "17600232953");
		params.put("name", "张三丰");
		params.put("carno", "京A1234A");
		params.put("engineno", "sdfdsfdsdf");
		params.put("packages", "12");
		params.put("year", "1");
		String result = cbsBuilder.sendPost("/warranty/v1/order", params);
		System.out.println(result);
	}


	@Test
	public void getReportUrl() {
		final String userId = "42340";
		final String keySecret = "9040091791bc4aad278ded92bd4b897c";
		String orderid = "50331ece5b6349fe95b8d9cdf334261c";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, keySecret, true);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderid);
		String s = cbsBuilder.getReportUrl("/claim/report/show", params);
		System.out.println(s);
	}

	@Test
	public void getBrand1() {
		CBSBuilder c = CBSBuilder.newCBSBuilder("1854879", "6e69ebb3c2179058f9cb66e04b7f3d0c", false);
		HashMap param = new HashMap();
		param.put("vin", "LFMAP22C3F0728422");
		String s = c.sendPost("/warranty/v1/brand", param);
		System.out.println(s);
	}

	@Test
	public void reportBuy() {
		CBSBuilder c = CBSBuilder.newCBSBuilder("2277465", "ffd8711171f709622ec00a94afa849d4", true);
		HashMap param = new HashMap();
		param.put("vin", "LFV**************");
		String s = c.sendPost("/new_report/buy", param);
		System.out.println(s);
	}
}