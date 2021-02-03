package com.springrod.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
 
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List; 
  

public class HttpRequest {
      class CustomizedHostnameVerifier implements HostnameVerifier{

        //重写验证方法
        @Override  
           public boolean verify(String arg0, SSLSession arg1)  
           {  
            //所有都正确
               return true;  
           }   
   }
      private String url; 
      private String method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    private Map<String,JSONObject> data=new HashMap<String,JSONObject>();
    public JSONObject doGet(String url){
           return doGet("default",url);
    }
    public JSONObject doGet(String groupName, String url){

        String result = "";
        BufferedReader in = null;
        try {
             
            URL realUrl = new URL(url);
            if(url.substring(0,5).equals("https")){
                HttpsURLConnection connection = (HttpsURLConnection)realUrl.openConnection(); 
                 connection.setHostnameVerifier(new CustomizedHostnameVerifier());
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                connection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
                connection.connect(); 
                Map<String, List<String>> map = connection.getHeaderFields(); 
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                } 
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }else{ 
            URLConnection connection = realUrl.openConnection(); 
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
            connection.connect(); 
            Map<String, List<String>> map = connection.getHeaderFields(); 
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            } 
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
       
        try{
              data.put(groupName, JSON.parseObject(result) ); 
        }catch(Exception e){
              return new JSONObject();
        } 
        System.out.println("json设置"+ groupName); 
        System.out.println("json设置"+JSON.toJSONString(data.get(groupName)));  
       return data.get(groupName);
    }
   
    public JSONObject doPost(String groupName, String url,String body ){
        JSONObject requestBody=JSON.parseObject(body); 
            return doPost(groupName,url,requestBody,"jsonBody");
    }
    public JSONObject doPost(String groupName, String url){
     
         int index = url.indexOf("?");
         String param = url.substring(index+1);
 
         String[] params = param.split("&"); 
         JSONObject data=new JSONObject();
         for (String item:params) {
             String[] kv = item.split("="); 
             data.put(kv[0],kv[1]);
         } 
        return doPost(groupName,url,data,"jsonBody");
}
     
    public JSONObject doPost(String groupName, String url,JSONObject body,String type){
        System.out.println(url);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            if(url.substring(0,5).equals("https")){
                HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection(); 
                conn.setRequestProperty("accept", "*/*");
                conn.setHostnameVerifier(new CustomizedHostnameVerifier());
                conn.setRequestProperty("connection", "Keep-Alive"); 
                conn.setRequestProperty("Content-Type","application/json; charset=UTF-8"); 
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
                conn.setDoOutput(true);
                conn.setDoInput(true); 
                out = new PrintWriter(conn.getOutputStream()); 
                out.print(JSON.toJSONString(body)); 
                out.flush(); 
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }

            }else{ 
                    URLConnection conn = realUrl.openConnection(); 
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive"); 
                    conn.setRequestProperty("Content-Type","application/json; charset=UTF-8"); 
                    conn.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
                    conn.setDoOutput(true);
                    conn.setDoInput(true); 
                    out = new PrintWriter(conn.getOutputStream()); 
                    out.print(JSON.toJSONString(body)); 
                    out.flush(); 
                    in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        result += line;
                    }
          }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
         
        try{ 
            data.put(groupName,JSON.parseObject(result)); 
        }catch(Exception e){
            e.printStackTrace(); 
        } 
         return data.get(groupName);
    }
     
    public JSONArray getJSONArray(String groupName,String name){ 
        return data.get(groupName).getJSONArray(name);
    }
    public JSONObject getJSONObject(String groupName, String name){ 
        return data.get(groupName).getJSONObject(name);
    }
    public String getString(String name){  
        return  getString("default",name);
    }
    public String getString(String groupName,String name){ 
        
        JSONObject obj=data.get(groupName); 
        System.out.println("json获取"+ groupName);  
        System.out.println("json获取"+JSON.toJSONString(obj));  
        return obj.getString(name);
    }

    public JSONArray getJSONArray(String name){ 
        return data.get("default").getJSONArray(name);
    }
    public JSONObject getJSONObject( String name){ 
        return data.get("default").getJSONObject(name);
    } 


} 


 