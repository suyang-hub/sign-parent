package org.com.cheshuai;

import java.util.HashMap;

import org.com.cheshuai.sdk.SDK;
import org.junit.Test;

public class SDKTest {
	
	@Test
	public void newSDK(){
		String userId = "145";
		String key = "456cc716dbcf41e1aa1d1e13b9a6f7cf";
		SDK sdk = SDK.newSDK(userId, key, false);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("vin", "LFV3A23C1J3141163");
		params.put("enginno", "D26445");
		String s = sdk.sendPost("/api/report/getInfo", params);
		System.out.println(s);
	}
}
