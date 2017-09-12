package com.garten.util.ditu;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.UnsupportedEncodingException;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLConnection;  
import java.util.HashMap;  
import java.util.Map;  
  
import net.sf.json.JSONObject;  
  
/**  
* 获取经纬度 
*  
* @author jueyue 返回格式：Map<String,Object> map map.put("status",  
* reader.nextString());//状态 map.put("result", list);//查询结果  
* list<map<String,String>>  
* 密钥:C0a9eecc5b366bde412021d4d7e5d8c4  
*/   
public class baidu {  
  
  
    public static void main(String[] args) throws IOException {  
        baidu getLatAndLngByBaidu = new baidu();  
          
        Map<String,Double> map=getLatAndLngByBaidu.getLngAndLat("温州市");  
        System.out.println("经度："+map.get("lng")+"---纬度："+map.get("lat"));  
    }  
      
    public static Map<String,Double> getLngAndLat(String address){  
        Map<String,Double> map=new HashMap<String, Double>();  
         String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=C0a9eecc5b366bde412021d4d7e5d8c4";  
            String json = loadJSON(url);  
            JSONObject obj = JSONObject.fromObject(json);  
            if(obj.get("status").toString().equals("0")){  
                double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");  
                double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");  
                map.put("lng", lng);  
                map.put("lat", lat);  
                //System.out.println("经度："+lng+"---纬度："+lat);  
            }else{  
                //System.out.println("未找到相匹配的经纬度！");  
            }  
        return map;  
    }  
      
     public static String loadJSON (String url) {  
            StringBuilder json = new StringBuilder();  
            try {  
                URL oracle = new URL(url);  
                URLConnection yc = oracle.openConnection();  
                BufferedReader in = new BufferedReader(new InputStreamReader(  
                                            yc.getInputStream()));  
                String inputLine = null;  
                while ( (inputLine = in.readLine()) != null) {  
                    json.append(inputLine);  
                }  
                in.close();  
            } catch (MalformedURLException e) {  
            } catch (IOException e) {  
            }  
            return json.toString();  
        }  
       
       
}  