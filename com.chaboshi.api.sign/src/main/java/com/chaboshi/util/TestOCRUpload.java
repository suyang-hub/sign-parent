package com.chaboshi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.chaboshi.http.OCRHttpHelper;

public class TestOCRUpload {

  public static void main(String[] args) throws UnsupportedEncodingException {
    long begin = System.currentTimeMillis();
    String filepath = "d:\\vin.jpg";
    // File file = new File(filepath);
    String urlStr = "http://api.chaboshi.cn/ocr/uploadPic";
    Map<String, String> textMap = new HashMap<String, String>();
    String userid = "89";
    String keySecret = "sdfghhjkhjkl;";
    String nonce = UUID.randomUUID().toString();
    long timeStamp = System.currentTimeMillis();
    String signature = CBS.getInstance(userid, keySecret).getSignature(nonce, timeStamp + "");
    System.out.println("签名是:" + signature);
    textMap.put("userid", userid);
    textMap.put("signature", URLDecoder.decode(signature, "UTF-8"));
    textMap.put("nonce", nonce);
    textMap.put("timestamp", timeStamp + "");
    Map<String, String> fileMap = new HashMap<String, String>();
    fileMap.put("file", filepath);
    String ret = OCRHttpHelper.formUpload(urlStr, textMap, fileMap);
    long end = System.currentTimeMillis();
    System.out.println(ret);
    System.out.println((end - begin) / 1000);
    System.out.println(CBS.getInstance("", "").upLoadPic(filepath));
  }

}
