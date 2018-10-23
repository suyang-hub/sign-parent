package com.chaboshi.builder;

import com.chaboshi.constants.CBSField;
import com.chaboshi.http.HttpRequest;
import com.chaboshi.signUtil.SignUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.UUID;

/*
 * @desc	新版签名SDK
 * @author 	Zack
 * @date 	2018/6/4
 ****************************************
 */
public class CBSBuilder {
	private final String CBS_TEST = "http://jxapi.chaboshi.cn";
	private final String CBS_ONLINE = "https://api.chaboshi.cn";
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户秘钥
	 */
	private String keySecret;
	/**
	 * 请求域名
	 */
	private String URL = CBS_ONLINE;

	private static CBSBuilder cbsBuilder = null;

	private CBSBuilder(String userid, String keySecret, boolean onLine) {
		this.userId = userid;
		this.keySecret = keySecret;
		if (!onLine) {
			URL = CBS_TEST;
		}
	}

	/**
	 * 构建
	 * @param userid
	 * @param keySecret
	 * @param onLine
	 * @return CBSBuilder
	 */
	public static synchronized CBSBuilder newCBSBuilder(String userId, String keySecret, boolean onLine) {
		if (userId == null || keySecret == null || userId.isEmpty() || keySecret.isEmpty()) {
			throw new RuntimeException("构建参数错误！");
		}
		if (cbsBuilder == null || !userId.equals(cbsBuilder.userId)) {
			cbsBuilder = new CBSBuilder(userId, keySecret, onLine);
		}
		return cbsBuilder;
	}

	/**
	 * 发送POST请求
	 * @param suffix 请求地址：/demo/test
	 * @return json{data}
	 */
	public String sendPost(String suffix) {
		return sendPost(suffix, null);
	}

	/**
	 * 发送GET请求
	 * @param suffix 请求地址：/demo/test
	 * @return json{data}
	 */
	public String sendGet(String suffix) {
		return sendGet(suffix, null);
	}

	/**
	 * 发送POST请求
	 * @param suffix 请求地址：/demo/test
	 * @param params 除构建参数以外的所有参数 (构建参数为：userId, keySecret)
	 * @return json{data}
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
	 * @param suffix 请求地址：/demo/test
	 * @param params 除构建参数以外的所有参数 (构建参数为：userId, keySecret)
	 * @return json{data}
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


	/**
	 * 获取报告URL地址
	 * @param suffix 请求地址：/demo/test
	 * @param params 除构建参数以外的所有参数 (构建参数为：userId, keySecret)
	 * @return URL
	 */
	public String getReportUrl(String suffix, HashMap<String, Object> params) {
		try {
			String paramsStr = sign(params);
			return URL + suffix + "?" + paramsStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	String sign(HashMap<String, Object> params) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(CBSField.USER_ID).append("=").append(userId);
		if(params != null) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value == null || StringUtils.isBlank(String.valueOf(value))) {
					continue;
				}
				sb.append("&").append(key).append("=").append(value);
			}
		}
		long timestamp = System.currentTimeMillis();
		String nonce = UUID.randomUUID().toString();
		sb.append("&").append(CBSField.TIMESTAMP).append("=").append(timestamp);
		sb.append("&").append(CBSField.NONCE).append("=").append(nonce);
		String signature = SignUtil.getSignature(keySecret, sb.toString());
		sb.append("&").append(CBSField.SIGNATURE).append("=").append(signature);
		return sb.toString();
	}
}
