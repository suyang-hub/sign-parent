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
		params.put("orderno", "1d28f41c648d4b19a86460515116f65c");
		String s = cbsBuilder.sendPost("/warranty/v1/inform", params);
		System.out.println(s);
	}

}