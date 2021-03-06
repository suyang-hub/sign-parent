package com.chaboshi.signUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 签名工具类
 * 
 * @author maxiaodong
 *
 */
public class SignUtil {

  public static final String ENCODING = "UTF-8";

  /**
   * 获取签名
   *
   * @param keySecret 秘钥（必填）
   * @param params 参数（必填）
   * @return 签名字符串
   * @throws Exception
   */
  public static String getSignature(String keySecret, String params) throws Exception {
    if (null == keySecret || keySecret.isEmpty() || null == params || params.isEmpty()) {
      throw new Exception("keySecret and param must NOT (null or empty)");
    }
    Map<String, String[]> paramMap = new HashMap<String, String[]>();
    String[] para = params.split("&");
    for (String str : para) {
      String[] paramm = str.split("=");
      if (paramm.length == 2) {
        paramMap.put(paramm[0], paramm[1].split(","));
      }
    }

    return URLEncoder.encode(paramsMakeSignature(paramMap, keySecret), ENCODING);
  }

  private static String paramsMakeSignature(Map<String, String[]> paramMap, String keySecert) throws Exception {
    // 如果该hashMap是空的，则返回空
    if (null == paramMap || null == paramMap.get("userid") || paramMap.size() == 0) {
      return null;
    }

    // 构造签名的字符串
    String signature = validateRequestToSignStr(paramMap);
    SecretKeySpec signingKey = new SecretKeySpec(keySecert.getBytes(), "HmacSHA1");
    Mac mac = Mac.getInstance("HmacSHA1");
    mac.init(signingKey);
    byte[] rawHmac = mac.doFinal(signature.getBytes());

    return Base64.encode(rawHmac);
  }

  /**
   * 构造生成签名的字符串
   * 
   * @param request
   * @return
   * @throws Exception
   */
  private static String validateRequestToSignStr(Map<String, String[]> paramMap) throws Exception {
    if (null == paramMap) {
      return "";
    }

    Collection<String> keyset = paramMap.keySet();
    List<String> list = new ArrayList<String>(keyset);

    // 对key键值按字典升序排序
    Collections.sort(list);
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      String split = i == 0 ? "" : "&";

      if ("userid".equalsIgnoreCase(list.get(i))) {
        content.append(split).append("userid").append("=").append(paramMap.get(list.get(i))[0]);
      } else if ("nonce".equalsIgnoreCase(list.get(i))) {
        content.append(split).append("nonce").append("=").append(paramMap.get(list.get(i))[0]);
      } else if ("timestamp".equalsIgnoreCase(list.get(i))) {
        content.append(split).append("timestamp").append("=").append(paramMap.get(list.get(i))[0]);
      } else if (!"signature".equalsIgnoreCase(list.get(i))) {
        String[] values = paramMap.get(list.get(i));
        content.append(split).append(list.get(i)).append("=");
        for (int j = 0; j < values.length; j++) {
          String split1 = j == 0 ? "" : ",";
          content.append(split1 + values[j]);
        }
      }
    }

    return URLEncoder.encode(content.toString(), ENCODING);
  }
}