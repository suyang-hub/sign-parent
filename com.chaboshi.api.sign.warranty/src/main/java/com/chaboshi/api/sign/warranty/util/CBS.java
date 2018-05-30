package com.chaboshi.api.sign.warranty.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.chaboshi.api.sign.warranty.http.HttpRequest;
import com.chaboshi.api.sign.warranty.signutil.Base64;
import com.chaboshi.api.sign.warranty.signutil.SignUtil;


/**
 * 用于提供查询延保的接口
 * @author renjie
 */
public class CBS {

	//用户id
	private final String userid;
	//用户秘钥
	private final String keySecret;

	private static String url_commen = null;

	private CBS(String userid,String keySecret, boolean isOnLine){
		this.userid = userid;
		this.keySecret = keySecret;
		if(isOnLine) {
			url_commen = "http://api.chaboshi.cn";
		} else {
			url_commen = "http://tapi.chaboshi.cn";
		}
	}


	/**
	 * @param userid
	 * @param keySecret
	 * @param isOnLine
	 *        true-线上    false-测试
	 * @return
	 */
	public static synchronized CBS getInstance(String userid,String keySecret, boolean isOnLine){
		if(userid == null || userid.isEmpty()){
			return null;
		}
		if(keySecret == null || keySecret.isEmpty()){
			return null;
		}

		return new CBS(userid,keySecret, isOnLine);
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
		String param = param(name, phone, cityId, address, type, null, callbackUrl, vin, null, null, null, null, null, null, null, null,null);
		String url = url_commen + "/insurance/order";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}



	/**
	 * 获取事故保开放的城市
	 * @return
	 */
	public String getAllInsuranceCity() {
		String param = param(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		String url = url_commen + "/insurance/opencity";
		String sendPost = HttpRequest.sendGet(url, param);

		return sendPost;
	}


	/**
	 * 查看检测订单状态
	 * @param orderNo
	 * @return
	 */
	public String getInsuranceStatus(String orderNo) {
		String params = param(null, null, null, null, null, orderNo, null, null, null, null, null, null, null, null, null, null, null);
		System.out.println(params);
		String url = url_commen + "/insurance/status";
		String sendGet = HttpRequest.sendGet(url, params);

		return sendGet;
	}


	/**
	 * 通过vin码查询品牌ID
	 * @return
	 */
	public String getBrandByVin(String vin) {
		String param = param(null, null, null, null, null, null, null, vin, null, null, null, null, null, null, null, null, null);
		String url = url_commen + "/warranty/v1/brand";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}


	/**
	 * 通过品牌ID查询支持的车系
	 * @return
	 */
	public String getSeriesByBrandId(String brandId) {
		String param = param(null, null, null, null, null, null, null, null, brandId, null, null, null, null, null, null, null, null);
		String url = url_commen + "/warranty/v1/series";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}


	/**
	 * 通过品牌ID和车系ID查询车型
	 * @return
	 */
	public String getModelByBrandIdAndSeriesId(String brandId, String seriesId) {
		String param = param(null, null, null, null, null, null, null, null, brandId, seriesId, null, null, null, null, null, null, null);
		String url = url_commen + "/warranty/v1/model";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}



	/**
	 * 查询是否支持延保
	 * @param vin
	 * @param modelId
	 * @param regtime
	 * @param watchmiles
	 * @return
	 */
	public String checkWarranty(String vin, String modelId, String regtime, String watchmiles) {
		String param = param(null, null, null, null, null, null, null, vin, null, null, modelId, regtime, watchmiles, null, null, null, null);
		String url = url_commen + "/warranty/v1/check";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}


	/**
	 * 创建延保订单
	 * @param vin
	 * @param modelId
	 * @param regtime
	 * @param watchmiles
	 * @return
	 */
	public String createWarrantyOrder(String vin, String modelId, String regtime, String watchmiles, String phone, String name, String callbackurl, String carno, String engineNo, String packages) {
		String param = param(name, phone, null, null, null, null, callbackurl, vin, null, null, modelId, regtime, watchmiles, packages, carno, engineNo, null);
		String url = url_commen + "/warranty/v1/order";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}



	/**
	 * 签字，将签字的图片链接传过来
	 * @param signUrl
	 * @param orderNo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String sign(String signUrl, String orderNo) throws UnsupportedEncodingException {
		String encode = Base64.encode(signUrl.getBytes());
		System.out.println(encode);
		String param = param(null, null, null, null, null, orderNo, null, null, null, null, null, null, null, null, null, null, encode);
		String url = url_commen + "/warranty/v1/contract";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}


	/**
	 * 构造签名
	 * @param name
	 * @param phone
	 * @param cityId
	 * @param address
	 * @param type
	 * @param orderNO
	 * @param callbackurl
	 * @param vin
	 * @param brandId
	 * @param serisId
	 * @param modelId
	 * @param regTime
	 * @param watchMiles
	 * @return
	 */
	private String param(String name, String phone, String cityId,String address, String type, String orderNO, String callbackurl, String vin, String brandId, String serisId, String modelId, String regTime, String watchMiles, String packages, String carno, String engineNo, String signurl){
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
			content.append("&vin=").append(vin);
		}

		if(brandId != null && !brandId.equals("")) {
			content.append("&brandid=").append(brandId);
		}

		if(serisId != null && !serisId.equals("")) {
			content.append("&seriesid=").append(serisId);
		}

		if(modelId != null && !modelId.equals("")) {
			content.append("&modelid=").append(modelId);
		}
		if(regTime != null && !regTime.equals("")) {
			content.append("&regtime=").append(regTime);
		}

		if(watchMiles != null && !watchMiles.equals("")) {
			content.append("&watchmiles=").append(watchMiles);
		}

		if(packages != null && !packages.equals("")) {
			content.append("&packages=").append(packages);
		}

		if(carno != null && !carno.equals("")) {
			content.append("&carno=").append(carno);
		}

		if(engineNo != null && !engineNo.equals("")) {
			content.append("&engineno=").append(engineNo);
		}

		if(signurl != null && !signurl.equals("")) {
			content.append("&signurl=").append(signurl);
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
