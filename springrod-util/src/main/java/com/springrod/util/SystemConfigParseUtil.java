package com.springrod.util; 
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Properties配置文件操作工具。
 * @author maxl
 * @time 2018-05-02
 */
public class SystemConfigParseUtil {
	 public static String getProperty(String name,String key){
			Properties properties = new Properties();
			System.out.println("配置文件F:"+name+".properties");  
	        InputStream inputStream = SystemConfigParseUtil.class.getClassLoader().getResourceAsStream(name+".properties");
	        try {
	            properties.load(inputStream);
	            properties.get(key);
	            return properties.get(key) == null ? "" : properties.get(key).toString();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "";
	        }
	    }
	 public static Map<String,String> getPropertys(String name){
	        Properties properties = new Properties();
	        InputStream inputStream = SystemConfigParseUtil.class.getClassLoader().getResourceAsStream(name+".properties");
	        Map<String,String> propertys=new HashMap<String,String>();
	        try {
	            properties.load(inputStream);
	            Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
	            
	    		while (it.hasNext()) {
	    			Entry<Object, Object> entry = it.next();
	    			Object key = entry.getKey();
	    			Object value = entry.getValue();
	    			propertys.put(String.valueOf(key),String.valueOf( value));
	    		}
 
	        } catch (IOException e) {
	            e.printStackTrace();
	        
	        }
	        return propertys;
	    }
}
