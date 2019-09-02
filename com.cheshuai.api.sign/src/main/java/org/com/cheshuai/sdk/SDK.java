package org.com.cheshuai.sdk;



import org.apache.commons.lang3.StringUtils;
import org.com.cheshuai.constants.Field;
import org.com.cheshuai.http.HttpRequest;
import org.com.cheshuai.signUtil.SignUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

/*
 * SDK签名
 */
public class SDK {
	private final String URL = "http://123.56.156.238";//请求地址
	
	private String userid;//用户id
	
	private String token;//用户token
	
	private static SDK sdk = null;

	private SDK(String userid, String token) {
		this.userid = userid;
		this.token = token;
	}

	/**
	 * 构建
	 */
	public static synchronized SDK newSDK(String userId, String token) {
		if (userId == null || token == null || userId.isEmpty() || token.isEmpty()) {
			throw new RuntimeException("参数为空");
		}
		if (sdk == null || !userId.equals(sdk.userid)) {
			sdk = new SDK(userId, token);
		}
		return sdk;
	}

	/**
	 * 发送POST请求
	 */
	public String sendPost(String suffix) {
		return sendPost(suffix, null);
	}

	/**
	 * 发送GET请求
	 */
	public String sendGet(String suffix) {
		return sendGet(suffix, null);
	}

	/**
	 * 发送POST请求
	 * @param params 除构建参数以外的所有参数 (构建参数为:USERID,TOKEN)
	 */
	public String sendPost(String suffix, HashMap<String, Object> params) {
		try{
			String paramsStr = sign(params);
			return HttpRequest.sendPost(URL + suffix, paramsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送GET请求
	 * @param params 除构建参数以外的所有参数 (构建参数为:USERID,TOKEN)
	 */
	public String sendGet(String suffix, HashMap<String, Object> params) {
		try {
			String paramsStr = sign(params);
			return HttpRequest.sendGet(URL + suffix, paramsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String sign(HashMap<String, Object> params) throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilder encodeStr = new StringBuilder();

		sb.append(Field.USER_ID).append("=").append(userid);
		encodeStr.append(Field.USER_ID).append("=").append(userid);
		if(params != null) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value == null || StringUtils.isBlank(String.valueOf(value))) {
					continue;
				}
				sb.append("&").append(key).append("=").append(value);
				String encode = URLEncoder.encode(String.valueOf(value), "UTF-8");
				encodeStr.append("&").append(key).append("=").append(encode);
			}
		}
		long timestamp = System.currentTimeMillis();
		String nonce = UUID.randomUUID().toString();
		sb.append("&").append(Field.TIMESTAMP).append("=").append(timestamp);
		sb.append("&").append(Field.NONCE).append("=").append(nonce);
		String signature = SignUtil.getSignature(token, sb.toString());
		encodeStr.append("&").append(Field.TIMESTAMP).append("=").append(timestamp);
		encodeStr.append("&").append(Field.NONCE).append("=").append(nonce);
		encodeStr.append("&").append(Field.SIGNATURE).append("=").append(signature);
		return encodeStr.toString();
	}
}
