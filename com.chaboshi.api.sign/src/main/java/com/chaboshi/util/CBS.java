package com.chaboshi.util;

import com.chaboshi.http.HttpRequest;
import com.chaboshi.http.OCRHttpHelper;
import com.chaboshi.signUtil.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 查博士API
 * 
 * @author maxiaodong
 *
 */
public class CBS {

  private static final Logger logger = LoggerFactory.getLogger(CBS.class);
  /**
   * 用户id
   */
  private final String userid;
  /**
   * 用户秘钥
   */
  private final String keySecret;

  private static CBS instance = null;

  private static String urlCommon = "https://api.chaboshi.cn";

  private CBS(String userid, String keySecret, boolean onLine) {
    this.userid = userid;
    this.keySecret = keySecret;
    if (!onLine) {
      urlCommon = "https://tapi.chaboshi.cn";
    }
  }

  public static synchronized CBS getInstance(String userid, String keySecret, boolean onLine) {
    if (userid == null || userid.isEmpty()) {
      return null;
    }
    if (keySecret == null || keySecret.isEmpty()) {
      return null;
    }
    if (instance == null) {
      instance = new CBS(userid, keySecret, onLine);
    }

    return instance;
  }

  /**
   * 获取CBS接口实例
   * 
   * @param userid 用户ID (必填)
   * @param keySecret 秘钥 (必填)
   * @return
   */
  public static synchronized CBS getInstance(String userid, String keySecret) {
    return getInstance(userid, keySecret, true);
  }

  /**
   * 购买报告
   * 
   * @param vin 车架号(必填)
   * @param enginno 发动机号(非必填)
   * @param licensePlate 车牌号(非必填)
   * @param callbackurl 回调地址(非必填)
   * @return 成功：{"Code":0,"orderId":"订单号,"Message":"描述结果信息"}</br>
   *         失败：{"Code":结果代码,"Message":"描述结果信息"}
   */
  public String getBuyReport(String vin, String enginno, String licensePlate, String callbackurl) {
    String param = param(vin, enginno, licensePlate, callbackurl);
    String url = urlCommon + "/report/buy_report";
    String sendPost = HttpRequest.sendPost(url, param);

    return sendPost;
  }

  /**
   * 获取报告状态
   * 
   * @param orderid 订单ID(必填)
   * @return {"Code":结果代码,"Message":"描述结果信息"}
   */
  public String getReportStatus(String orderid) {
    String param = this.param(orderid);
    String url = urlCommon + "/report/get_report_status";
    String sendGet = HttpRequest.sendGet(url, param);
    return sendGet;
  }

  /**
   * 判断该vin码是否支持查询
   * 
   * @param vin 车架号(必填)
   * @return {status : 状态码 ， message : 结果描述 }
   */
  public String getCheckBrand(String vin) {
    String param = this.checkVin(vin);
    String url = urlCommon + "/report/check_brand";
    String sendGet = HttpRequest.sendGet(url, param);
    return sendGet;
  }

  /**
   * 获取旧版报告URL
   * 
   * @param orderid 订单ID(必填)
   * @return key → pcUrl：电脑端url、mobileUrl：手机端url
   */
  public Map<String, String> getReportUrl(String orderid) {
    Map<String, String> result = new HashMap<String, String>();
    String param = this.param(orderid);
    String urlPC = urlCommon + "/report/show_report?" + param;
    String urlMobile = urlCommon + "/report/show_reportMobile?" + param;
    result.put("pcUrl", urlPC);
    result.put("mobileUrl", urlMobile);
    return result;
  }

  /**
   * 获取新版报告URL
   * 
   * @param orderid 订单ID(必填)
   * @return key → pcUrl：电脑端url、mobileUrl：手机端url
   */
  public Map<String, String> getNewReportUrl(String orderid) {
	    Map<String, String> result = new HashMap<String, String>();
	    String param = this.param(orderid);
	    String param1 = this.param(orderid,1);
	    String urlPC = urlCommon + "/new_report/show_report?" + param;
	    String urlMobile = urlCommon + "/new_report/show_reportMobile?" + param;
	    result.put("pcUrl", urlPC);
	    result.put("mobileUrl", urlMobile);
	    result.put("newPcUrl", urlCommon+"/new_report/show_report?"+param1);
	    return result;
	  }
  /**
   * 获取报告Json数据
   * 
   * @param orderid 订单ID(必填)
   * @return
   */
  public String getReportJson(String orderid) {
    String param = this.param(orderid);
    String url = urlCommon + "/report/get_report";
    String sendGet = HttpRequest.sendGet(url, param);
    return sendGet;
  }

  /**
   * 获取新版报告Json数据
   * 
   * @param orderid 订单ID(必填)
   * @return
   */
  public String getNewReportJson(String orderid) {
    String param = this.param(orderid);
    String url = urlCommon + "/new_report/get_report";
    System.out.println(url + "?" + param);
    String sendGet = HttpRequest.sendGet(url, param);
    return sendGet;
  }

  public String getAllCity() {
    String param = param(null, null, null, null);
    String url = urlCommon + "/evaluate/getallcity";
    String sendGet = HttpRequest.sendPost(url, param);

    return sendGet;
  }

  public String getAllBrand() {
    String param = param(null, null, null, null);
    String url = urlCommon + "/evaluate/getallbrand";
    String sendGet = HttpRequest.sendPost(url, param);
    return sendGet;
  }

  public String getSeriesByBrandId(String brandId) {
    String param = paramEvaluate(this.userid, null, null, brandId, null, null, null, null);
    String url = urlCommon + "/evaluate/getseries";
    String sendGet = HttpRequest.sendPost(url, param);
    return sendGet;
  }

  public String getModelBySeriesId(String seriesId) {
    String param = paramEvaluate(this.userid, null, null, null, seriesId, null, null, null);
    String url = urlCommon + "/evaluate/getmodel";
    String sendGet = HttpRequest.sendPost(url, param);
    return sendGet;
  }

  public String getCarPrice(String orderId, String cityId, String modelId, String regDate, String mile) {
    String param = paramEvaluate(this.userid, orderId, modelId, null, null, regDate, cityId, mile);
    String url = urlCommon + "/evaluate/getcarprice";
    String sendGet = HttpRequest.sendPost(url, param);

    return sendGet;
  }

  public String getDocumentByOrderNo(String orderNo) {
    String param = param(orderNo);
    String url = urlCommon + "/report/get_archives";
    String sendPost = HttpRequest.sendPost(url, param);

    return sendPost;
  }

  /**
   * 上传OCR图片识别
   * 
   * @param filePath 图片的路径
   * @return
   */
  public String upLoadPic(String filePath) {
    if (filePath == null || filePath.isEmpty()) {
      return null;
    }

    String urlOCR = urlCommon + "/ocr/uploadPic";
    long timestamp = System.currentTimeMillis();
    String nonce = UUID.randomUUID().toString();
    String signature = getSignature(nonce, timestamp + "");
    Map<String, String> textMap = new HashMap<String, String>();
    textMap.put("userid", userid);
    try {
      textMap.put("signature", URLDecoder.decode(signature, "UTF-8"));
    } catch (Exception e) {
      logger.error("", e);
    }
    textMap.put("nonce", nonce);
    textMap.put("timestamp", timestamp + "");
    Map<String, String> fileMap = new HashMap<String, String>();
    fileMap.put("file", filePath);

    return OCRHttpHelper.formUpload(urlOCR, textMap, fileMap);
  }

  /**
   * 获取订单最后的更新时间
   * 
   * @param orderid 订单ID(必填) 回调地址(可以为空)
   * @return
   */
  public String updateReport(String orderid, String callbackUrl) {
    String param = this.param(orderid, null, null, null, callbackUrl);
    String url = urlCommon + "/report/update_report";
    String sendGet = HttpRequest.sendPost(url, param);
    return sendGet;
  }

  /**
   * 获取订单最后的更新时间
   * 
   * @param orderid 订单ID(必填)
   * @return
   */
  public String getUpdateTime(String orderid) {
    String param = this.param(orderid);
    String url = urlCommon + "/report/get_report_updatetime";
    String sendGet = HttpRequest.sendGet(url, param);
    return sendGet;
  }

  /**
   * 根据随机数和时间戳获取签名
   * 
   * @param nonce
   * @param timestamp
   * @return
   */
  public String getSignature(String nonce, String timestamp) {
    if (nonce == null || nonce.isEmpty() || timestamp == null || timestamp.isEmpty()) {
      return null;
    }
    StringBuilder content = new StringBuilder();
    // 随机数
    content.append("nonce").append('=').append(nonce);
    // 时间戳
    content.append("&timestamp").append('=').append(timestamp);

    // 用户id
    content.append("&userid").append("=").append(userid);
    // 签名
    String signAture = getSignature(content);

    return signAture;
  }

  /**
   * 拼装参数
   * 
   * @param vin 车架号(必填)
   * @param enginno 发动机号(非必填)
   * @param licensePlate 车牌号(非必填)
   * @return
   */
  private String param(String vin, String enginno, String licensePlate, String callbackurl) {
    return param(null, vin, enginno, licensePlate, callbackurl);
  }

  /**
   * 拼装参数
   * 
   * @param orderid 订单ID(必填)
   * @return
   */
  private String param(String orderid) {
    return param(orderid, null, null, null, null);
  }

  private String param(String orderid,int type) {
	    return param(orderid, null, null, null, null,type);
	  }
  /**
   * 拼装参数
   * 
   * @param vin 车架号(必填)
   * @return
   */
  private String checkVin(String vin) {
    return param(null, vin, null, null, null);
  }

  /**
   * 拼装参数
   * 
   * @param orderid 订单号
   * @param vin vin码
   * @param enginno 发动机号
   * @param licensePlate 车牌号
   * @param callbackurl 回调地址
   * @return
   */
  private String param(String orderid, String vin, String enginno, String licensePlate, String callbackurl) {
    long timestamp = System.currentTimeMillis();
    String Nonce = UUID.randomUUID().toString();
    StringBuilder content = new StringBuilder();
    // 用户id
    content.append("userid").append('=').append(userid);
    // 订单id
    if (orderid != null && !orderid.isEmpty()) {
      content.append("&orderid").append('=').append(orderid);
    }
    // 随机数
    content.append("&nonce").append('=').append(Nonce);
    // 时间戳
    content.append("&timestamp").append('=').append(timestamp);
    // 车架号
    if (vin != null && !vin.isEmpty()) {
      content.append("&vin").append('=').append(vin);
    }
    // 发动机号
    if (enginno != null && !enginno.isEmpty()) {
      content.append("&enginno").append('=').append(enginno);
    }
    // 车牌号
    if (licensePlate != null && !licensePlate.isEmpty()) {
      content.append("&licenseplate").append('=').append(licensePlate);
    }

    // 回调地址
    if (callbackurl != null && !callbackurl.isEmpty()) {
      content.append("&callbackurl").append('=').append(callbackurl);
    }

    // 签名
    String signAture = getSignature(content);
    content.append("&signature").append('=').append(signAture);

    return content.toString();
  }
  
  private String param(String orderid, String vin, String enginno, String licensePlate, String callbackurl,int type) {
	    long timestamp = System.currentTimeMillis();
	    String Nonce = UUID.randomUUID().toString();
	    StringBuilder content = new StringBuilder();
	    // 用户id
	    content.append("userid").append('=').append(userid);
	    // 订单id
	    if (orderid != null && !orderid.isEmpty()) {
	      content.append("&orderid").append('=').append(orderid);
	    }
	    // 随机数
	    content.append("&nonce").append('=').append(Nonce);
	    // 时间戳
	    content.append("&timestamp").append('=').append(timestamp);
	    // 车架号
	    if (vin != null && !vin.isEmpty()) {
	      content.append("&vin").append('=').append(vin);
	    }
	    // 发动机号
	    if (enginno != null && !enginno.isEmpty()) {
	      content.append("&enginno").append('=').append(enginno);
	    }
	    // 车牌号
	    if (licensePlate != null && !licensePlate.isEmpty()) {
	      content.append("&licenseplate").append('=').append(licensePlate);
	    }

	    if (licensePlate != null && !licensePlate.isEmpty()) {
		      content.append("&licenseplate").append('=').append(licensePlate);
		    }
	    // 回调地址
	    if (callbackurl != null && !callbackurl.isEmpty()) {
	        content.append("&callbackurl").append('=').append(callbackurl);
	      }
	    if (type == 1) {
	      content.append("&type").append('=').append(type);
	    }

	    // 签名
	    String signAture = getSignature(content);
	    content.append("&signature").append('=').append(signAture);

	    return content.toString();
	  }
  /**
   * 估价拼接参数
   * 
   * @param userId
   * @param orderId
   * @param modelId
   * @param brandId
   * @param seriesId
   * @param regDate
   * @param cityId
   * @param mile
   * @return
   */
  private String paramEvaluate(String userId, String orderId, String modelId, String brandId, String seriesId, String regDate,
      String cityId, String mile) {
    long timestamp = System.currentTimeMillis();
    String Nonce = UUID.randomUUID().toString();
    StringBuilder content = new StringBuilder();
    content.append("userid").append('=').append(this.userid);
    if ((orderId != null) && (!orderId.isEmpty())) {
      content.append("&orderid").append('=').append(orderId);
    }
    content.append("&nonce").append('=').append(Nonce);
    content.append("&timestamp").append('=').append(timestamp);
    if ((modelId != null) && (!modelId.isEmpty())) {
      content.append("&modelid").append('=').append(modelId);
    }
    if ((brandId != null) && (!brandId.isEmpty())) {
      content.append("&brandid").append('=').append(brandId);
    }
    if ((seriesId != null) && (!seriesId.isEmpty())) {
      content.append("&seriesid").append('=').append(seriesId);
    }
    if ((regDate != null) && (!regDate.isEmpty())) {
      content.append("&regdate").append('=').append(regDate);
    }
    if ((cityId != null) && (!cityId.isEmpty())) {
      content.append("&cityid").append('=').append(cityId);
    }
    if ((mile != null) && (!mile.isEmpty())) {
      content.append("&mile").append('=').append(mile);
    }

    String signAture = getSignature(content);
    content.append("&signature").append('=').append(signAture);

    return content.toString();
  }

  private String getSignature(StringBuilder content) {
    try {
      return SignUtil.getSignature(keySecret, content.toString());
    } catch (Exception e) {
      logger.error("", e);
    }
    return null;
  }
}
