package com.chaboshi.builder;

import com.chaboshi.util.CBS;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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



}