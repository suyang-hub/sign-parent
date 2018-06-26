package com.chaboshi.api.sign.detection.util;

import java.util.UUID;

import com.chaboshi.api.sign.detection.http.HttpRequest;
import com.chaboshi.api.sign.detection.signutil.SignUtil;


/**
 * 用于提供查询事故保的接口
 * @author renjie
 *
 */
public class CBSInsurance {

	//用户id
	private final String userid;
	//用户秘钥
	private final String keySecret;

	private static CBSInsurance cbsInsurance = null;

	private static String url_commen = "http://api.chaboshi.cn";

	private CBSInsurance(String userid,String keySecret){
		this.userid = userid;
		this.keySecret = keySecret;
	}

	/**
	 * 获取查博士实例
	 * @param userid  用户ID
	 * @param keySecret   用户密钥
	 * @return
	 */
	public static synchronized CBSInsurance getInstance(String userid,String keySecret){
		if(userid == null || userid.isEmpty()){
			return null;
		}
		if(keySecret == null || keySecret.isEmpty()){
			return null;
		}
		if(cbsInsurance == null){
			cbsInsurance = new CBSInsurance(userid,keySecret);
		}

		return cbsInsurance;
	}


	/**
	 * 生成检测的订单
	 * @param name
	 * @param phone
	 * @param cityId
	 * @param address
	 * @param type
	 *        type ：  101-事故保本地检测    102-事故保异地代检  201-车况保
	 * @param orderNO
	 *        orderNo : 第三方订单号
	 * @param callbackUrl
	 *        回调地址
	 * @return
	 */
	public String buyInsuranceOrder(String name, String phone, String cityId, String address, String type, String callbackUrl, String vin) {
		String param = param(name, phone, cityId, address, type, null, callbackUrl, vin);
		String url = url_commen + "/insurance/order";
		String sendPost = HttpRequest.sendPost(url, param);
		return sendPost;
	}



	/**
	 * 获取事故保开放的城市
	 * @return
	 */
	public String getAllInsuranceCity() {
		String param = param(null, null, null, null, null, null, null, null);
		String url = url_commen + "/insurance/opencity";
		String sendPost = HttpRequest.sendGet(url, param);

		return sendPost;
	}


	/**
	 * 通过订单号获取检测报告信息
	 * @param orderNo
	 * @return
	 */
	public String getInsuranceResult(String orderNo) {
		String param = param(null, null, null, null, null, orderNo, null, null);
		String url = url_commen + "/insurance/orderinfo";
		System.out.println(url + "?" + param);
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}


	/**
	 * 通过订单号获取查看检测报告的url
	 * @param orderNo
	 * @return
	 */
	public String getInsuranceReportUrl(String orderNo) {
		String param = param(null, null, null, null, null, orderNo, null, null);
		String url = url_commen + "/insurance/report";

		return url + "?" + param;
	}


	// 只提供给58放心车
//	public String getInsurancestatus(String orderNo) {
//		String param = param(null, null, null, null, null, orderNo, null, null);
//		String url = new StringBuilder().append(url_commen).append("/insurance/orderstatus").toString();
//		String sendPost = HttpRequest.sendPost(url, param);
//
//		return sendPost;
//	}

//	public String cancelDetection(String orderNo) {
//		String param = param(null, null, null, null, null, orderNo, null, null);
//		String url = new StringBuilder().append(url_commen).append("/insurance/cancel").toString();
//		String sendPost = HttpRequest.sendPost(url, param);
//
//		return sendPost;
//	}


	/**
	 * 构造签名
	 * @param name
	 * @param phone
	 * @param cityId
	 * @param address
	 * @param type
	 * @param orderNO
	 * @param callbackurl
	 *        回调地址
	 * @return
	 */
	private String param(String name, String phone, String cityId,String address, String type, String orderNO, String callbackurl, String vin){
		long timestamp = System.currentTimeMillis();
		String Nonce = UUID.randomUUID().toString();
		StringBuilder content = new StringBuilder();
		content.append("userid").append('=').append(userid);
		content.append("&nonce").append('=').append(Nonce);
		content.append("&timestamp").append('=').append(timestamp);
		if(name != null && !name.isEmpty()){
			content.append("&name").append('=').append(name);
		}
		if(orderNO != null && !orderNO.isEmpty()) {
			content.append("&orderno").append('=').append(orderNO);
		}
		if(phone != null && !phone.isEmpty()){
			content.append("&phone").append('=').append(phone);
		}
		if(cityId != null && !cityId.isEmpty()){
			content.append("&cityid").append('=').append(cityId);
		}
		if(address != null && !address.isEmpty()) {
			content.append("&address").append('=').append(address);
		}
		if(type != null && !type.isEmpty()) {
			content.append("&type").append('=').append(type);
		}
		if(callbackurl != null && !callbackurl.equals("")) {
			content.append("&callbackurl").append("=").append(callbackurl);
		}
		if(vin != null && !vin.equals("")) {
			content.append("&vin").append("=").append(vin);
		}

		String signAture = null;
		try {
			signAture = SignUtil.getSignature(keySecret, content.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		content.append("&signature").append('=').append(signAture);

		return content.toString();
	}


}
