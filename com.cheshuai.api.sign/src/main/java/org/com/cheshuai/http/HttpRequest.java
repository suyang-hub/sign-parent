package org.com.cheshuai.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
  private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
  /**
   * 向指定URL发送GET方法的请求
   * @param url 发送请求的URL
   * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return URL 所代表远程资源的响应结果
   */
  public static String sendGet(String url, String param) {
    String result = "";
    BufferedReader in = null;
    try {
      String urlNameString = url + "?" + param;
      URL realUrl = new URL(urlNameString);
      URLConnection connection = realUrl.openConnection();
      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      connection.setRequestProperty("Accept-Charset", "utf-8");
      connection.setRequestProperty("contentType", "utf-8");
      connection.connect();
      in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
     	logger.error("发送GET请求异常:",e);
    }
    finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (Exception e2) {
      }
    }
    return result;
  }

  /**
   * 向指定 URL 发送POST方法的请求
   * @param url 发送请求的 URL
   * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return 所代表远程资源的响应结果
   */
  public static String sendPost(String url, String param) {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url);
      URLConnection conn = realUrl.openConnection();
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setRequestProperty("Accept-Charset", "utf-8");
      conn.setRequestProperty("contentType", "utf-8");
      conn.setDoOutput(true);
      conn.setDoInput(true);
      out = new PrintWriter(conn.getOutputStream());
      out.print(param);
      out.flush();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      	logger.error("发送POST请求异常:",e);
    }
    finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
      }
    }
    return result;
  }

}
