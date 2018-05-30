package com.chaboshi.api.sign.valuate.util;

import java.util.UUID;

import com.chaboshi.api.sign.valuate.http.HttpRequest;
import com.chaboshi.api.sign.valuate.signUtil.SignUtil;

/**
 * 查博士API
 * @author renjie
 *
 */
public class CBS {
	//用户id
	private final String userid;
	//用户秘钥
	private final String keySecret;

	private static CBS cbs = null;

	private static String url_commen = null;

	private CBS(String userid,String keySecret, boolean isOnline){
		this.userid = userid;
		this.keySecret = keySecret;
		if(isOnline) {
			url_commen = "http://api.chaboshi.cn";
		} else {
			url_commen = "http://tapi.chaboshi.cn";
		}
	}


	/**
	 * 获取CBS接口实例
	 * @param userid
	 * @param keySecret
	 * @param isOnline
	 *          true-正式    false-测试
	 * @return
	 */
	public static synchronized CBS getInstance(String userid,String keySecret, boolean isOnline){
		if(userid == null || userid.isEmpty()){
			return null;
		}
		if(keySecret == null || keySecret.isEmpty()){
			return null;
		}
		if(cbs == null){
			cbs = new CBS(userid, keySecret, isOnline);
		}
		return cbs;
	}


	/**
	 * 获取估价结果
	 * @param vin
	 * @param modelid
	 * @param regdate
	 * @param makedate
	 * @param mile
	 * @param cityid
	 * @param color
	 * @param interior
	 * @param surface
	 * @param workstate
	 * @param transferTimes
	 * @return
	 */
	public String getCarPrice(String vin,Long modelid,String regdate, String makedate, Long mile,String cityid,String color, String interior, String surface, String workstate, int transferTimes){
		String param = paramEvaluate(userid, null, modelid + "", regdate, cityid, mile + "", makedate, vin, color, interior, surface, workstate, transferTimes + "");
		String url = url_commen + "/evaluate/v1/carprice";
		String sendPost = HttpRequest.sendPost(url, param);

		return sendPost;
	}


	/**
	 * 用于构造精准估价的参数
	 * @param userId
	 * @param orderId
	 * @param modelId
	 * @param regDate
	 * @param cityId
	 * @param mile
	 * @param makeDate
	 * @param vin
	 * @param color
	 * @param interior
	 * @param surface
	 * @param workState
	 * @param transferTimes
	 * @return
	 */
	private String paramEvaluate(String userId, String orderId, String modelId, String regDate, String cityId, String mile, String makeDate, String vin, String color, String interior, String surface, String workState, String transferTimes) {
		long timestamp = System.currentTimeMillis();
		String Nonce = UUID.randomUUID().toString();
		StringBuilder content = new StringBuilder();
		//用户id
		content.append("userid").append('=').append(userid);
		//订单id
		if(orderId != null && !orderId.isEmpty()){
			content.append("&orderid").append('=').append(orderId);
		}
		//随机数
		content.append("&nonce").append('=').append(Nonce);
		//时间戳
		content.append("&timestamp").append('=').append(timestamp);
		//车型ID
		if(modelId != null && !modelId.isEmpty()){
			content.append("&modelid").append('=').append(modelId);
		}

		//生产日期
		if(makeDate != null && !makeDate.isEmpty()){
			content.append("&makedate").append('=').append(makeDate);
		}

		//vin码
		if(vin != null && !vin.isEmpty()){
			content.append("&vin").append('=').append(vin);
		}

		//颜色
		if(color != null && !color.isEmpty()){
			content.append("&color").append('=').append(color);
		}

		//interior
		if(interior != null && !interior.isEmpty()){
			content.append("&interior").append('=').append(interior);
		}

		//surface
		if(surface != null && !surface.isEmpty()){
			content.append("&surface").append('=').append(surface);
		}

		//workState
		if(workState != null && !workState.isEmpty()){
			content.append("&workstate").append('=').append(workState);
		}

		//transferTimes
		if(transferTimes != null && !transferTimes.isEmpty()){
			content.append("&transfertimes").append('=').append(transferTimes);
		}

		//上牌日期
		if(regDate != null && !regDate.isEmpty()) {
			content.append("&regdate").append('=').append(regDate);
		}

		//城市ID
		if(cityId != null && !cityId.isEmpty()){
			content.append("&cityid").append('=').append(cityId);
		}

		//公里数
		if(mile != null && !mile.isEmpty()){
			content.append("&mile").append('=').append(mile);
		}

		//签名
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
