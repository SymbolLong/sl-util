package com.zhang.util;

import com.yscredit.util.JsonUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class HttpsUtil {
	
  private static final int CONNECT_TIMEOUT = 1000;
  private static final int READ_TIMEOUT = 5000;
  private static SslContextUtils sslContextUtils = new SslContextUtils();
 
  public static String sendXMLDataByPost(String postUrl, String xmlData){
    StringBuilder sb = new StringBuilder();
    try {
      URL url = new URL(postUrl);
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      
      if(httpConn instanceof HttpsURLConnection){
        sslContextUtils.initHttpsConnect((HttpsURLConnection)httpConn);
      }
      
      httpConn.setRequestMethod("POST");
      httpConn.setDoOutput(true);
      httpConn.setDoInput(true);
      httpConn.setRequestProperty("Content-type", "text/xml");
      httpConn.setConnectTimeout(CONNECT_TIMEOUT);
      httpConn.setReadTimeout(READ_TIMEOUT);
      // 发送请求
      httpConn.getOutputStream().write(xmlData.getBytes("utf-8"));
      httpConn.getOutputStream().flush();
      httpConn.getOutputStream().close();
      // 获取输入流
      BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
      char[] buf = new char[1024];
      int length = 0;
      while ((length = reader.read(buf)) > 0) {
        sb.append(buf, 0, length);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
    } 
    return sb.toString();
  }
  
	public static String sendDataByPost(String postUrl, String params, Map<String, String> requestProps) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(postUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

			if (httpConn instanceof HttpsURLConnection) {
				sslContextUtils.initHttpsConnect((HttpsURLConnection) httpConn);
			}

			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setRequestProperty("Content-Type", "text/json; charset=utf-8");
			if (requestProps != null && !requestProps.isEmpty()) {
				for (Map.Entry<String, String> entry : requestProps.entrySet()) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
		      httpConn.setConnectTimeout(CONNECT_TIMEOUT);
		      httpConn.setReadTimeout(READ_TIMEOUT);
			// 发送请求
			httpConn.getOutputStream().write(params.getBytes("utf-8"));
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();
			// 获取输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
			char[] buf = new char[1024];
			int length = 0;
			while ((length = reader.read(buf)) > 0) {
				sb.append(buf, 0, length);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

		}
		return sb.toString();
	}
  
    public static String sendDataByPost(String postUrl, Map<String, String> params){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(postUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            if(httpConn instanceof HttpsURLConnection){
                sslContextUtils.initHttpsConnect((HttpsURLConnection)httpConn);
            }

            String param = HttpsUtil.getParams(params);

            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            httpConn.setConnectTimeout(CONNECT_TIMEOUT);
            httpConn.setReadTimeout(READ_TIMEOUT);
            // 发送请求
            httpConn.getOutputStream().write(param.getBytes("utf-8"));
            httpConn.getOutputStream().flush();
            httpConn.getOutputStream().close();
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
            char[] buf = new char[1024];
            int length = 0;
            while ((length = reader.read(buf)) > 0) {
                sb.append(buf, 0, length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
        return sb.toString();
    }

    public static String sendDataByPostForm(String postUrl, Map<String, Object> reqMap, int connectTimeout, int readTimeout){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(postUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            if(httpConn instanceof HttpsURLConnection){
                sslContextUtils.initHttpsConnect((HttpsURLConnection)httpConn);
            }

            String param = JsonUtil.toJsonString(reqMap);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-type", "application/json");
            httpConn.setConnectTimeout(connectTimeout);
            httpConn.setReadTimeout(readTimeout);
            // 发送请求
            httpConn.getOutputStream().write(param.getBytes("utf-8"));
            httpConn.getOutputStream().flush();
            httpConn.getOutputStream().close();
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
            char[] buf = new char[1024];
            int length = 0;
            while ((length = reader.read(buf)) > 0) {
                sb.append(buf, 0, length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
        return sb.toString();
    }
  public static String doGet(String getUrl){
	    StringBuilder sb = new StringBuilder();
	    try {
	      URL url = new URL(getUrl);
	      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	      
	      if(httpConn instanceof HttpsURLConnection){
	        sslContextUtils.initHttpsConnect((HttpsURLConnection)httpConn);
	      }
	      
	      httpConn.setRequestMethod("GET");
	      httpConn.setConnectTimeout(CONNECT_TIMEOUT);
	      httpConn.setReadTimeout(READ_TIMEOUT);
	      InputStream inStream = httpConn.getInputStream();    
	      // 获取输入流
	      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));
	      char[] buf = new char[1024];
	      int length = 0;
	      while ((length = reader.read(buf)) > 0) {
	        sb.append(buf, 0, length);
	      }
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
	    return sb.toString();
	  }

    public static String getParams(Map<String, String> paramValues)
    {
        String params="";
        Set<String> key = paramValues.keySet();
        String beginLetter="";


        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            if (params.equals(""))
            {
                params += beginLetter + s + "=" + paramValues.get(s);
            }
            else
            {
                params += "&" + s + "=" + paramValues.get(s);
            }
        }
        return params;
    }
}
