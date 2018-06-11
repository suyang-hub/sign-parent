package com.chaboshi.builder;

import org.junit.Test;

import java.util.HashMap;

public class CBSBuilderTest {

	@Test
	public void newCBSBuilder() {
		String userId = "146574";
		String key = "a81cd13aa735140fd72c7ab402a2d98f";
		CBSBuilder cbsBuilder = CBSBuilder.newCBSBuilder(userId, key, true);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("tradeno", "1002461834566983680");
		String s = cbsBuilder.sendPost("/insurance/orderInfo", params);
		System.out.println(s);
	}

}