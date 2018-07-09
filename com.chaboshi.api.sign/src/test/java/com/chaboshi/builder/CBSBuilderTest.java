package com.chaboshi.builder;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

public class CBSBuilderTest {

	@Test
	public void newCBSBuilder() {
		String userId = "146574";
		String key = "a81cd13aa735140fd72c7ab402a2d98f";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, key, false);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("tradeno", "1002461834566983680");
		String s = cbsBuilder.sendPost("/insurance/orderInfo", params);
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
		final String userId = "104";
		final String keySecret = "cdb7cee9495f159710cc6280528c3868";
		String vin = "WBAKB41029C321863";
		String enginNo = "vcdasd";
		String carNo = "京HJDDD";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, keySecret, true);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("vin", vin);
		params.put("enginno", enginNo);
		params.put("carno", carNo);
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


}