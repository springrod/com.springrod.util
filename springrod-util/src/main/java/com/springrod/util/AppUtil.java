package com.springrod.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties; 
import org.springframework.context.ConfigurableApplicationContext; 
 

/*应用管理
 * net.sf.ehcache.Cache 缓存
 * 
 * */
public class AppUtil {
	private static String _path = null;

	public static String getPath(String configName) {
	    
		 
		if (_path == null) {
			URL path=AppUtil.class.getClassLoader().getResource(configName);
			if(path==null)return "";
			String basePatha1 = path.getPath();
			String pre1 = basePatha1.split(".jar")[0];
			String[] pre2 = pre1.split("/");
			_path = pre1.replace(pre2[pre2.length - 1], "");

		}
		return _path;

	}

	public static Properties getAppProperties(String name) {
		InputStream in = null;
	
		if (name.split("ile:///").length < 2) { 
			 in = AppUtil.class.getClassLoader().getResourceAsStream(name);
			 
		} else { 
			File f = new File(name); 
			try {
		    in = new FileInputStream(f ); 
			}catch (Exception e) {  
				e.printStackTrace();
				return null; 
			}
		  }
		 System.out.println("配置文件位置："+getPath(name));  
		 Properties properties = new Properties();
		  try {
			BufferedReader  reader= new BufferedReader(new InputStreamReader(in,"UTF-8")); 
			properties.load(reader); 
            properties.setProperty("com.springrod.version", "1.0.0.0"); 

			  String tempPath=properties.getProperty("app.front.templates");
			  if(tempPath!=null){
			     tempPath=tempPath.replace("{target}",getPath(name));
			     System.out.println("前端路径："+ tempPath); 
			     properties.setProperty("spring.thymeleaf.prefix", tempPath ); 
			     properties.setProperty("spring.thymeleaf.cache","false");   

			  String staticPath=properties.getProperty("app.front.static");
			     staticPath=staticPath.replace("{target}",getPath(name)); 
		          System.out.println("前端[资源]路径："+ staticPath); 
			  properties.setProperty("spring.resources.static-locations", staticPath);
		    	} 
			 } catch (Exception e) {  
				 e.printStackTrace();
				 return null; 
			 }
		     return properties; 
	 }

	 public static Properties getAppProperties(Properties properties) {
		 
                /**#设置预置参数 */
			properties.setProperty("com.springrod.version", "1.0.0.0"); 
			String tempPath=properties.getProperty("app.front.templates");
			  if(tempPath!=null){ 
			   System.out.println("前端路径："+ tempPath); 
			   properties.setProperty("spring.thymeleaf.prefix", tempPath ); 
			   properties.setProperty("spring.thymeleaf.cache","false");    
			  } 
			  String staticPath=properties.getProperty("app.front.static"); 
			  if(staticPath!=null){ 
				   System.out.println("前端[资源]路径："+ staticPath); 
				   properties.setProperty("spring.resources.static-locations", staticPath);
				} 
		  return properties; 
   }
	 /**输出日志*/
	 public static void printInformation(ConfigurableApplicationContext context) { 
		  System.out.flush();
		  System.out.println("应用启动成功");
		  print(context,"启动端口","server.port"); 
	      }
	 /**输出配置日志*/
	 public static void print(ConfigurableApplicationContext context,String label,String name) {
		 StringBuffer log =new StringBuffer(label);
		 log.append(" ： ");
		 log.append(context.getEnvironment().getProperty(name));
		 System.out.println(log.toString());
		 
	 }
	 /**监测运行环境*/
     public static boolean checkEnvironmental(ConfigurableApplicationContext context) { 
    	  Map<String,String> appConfNames=new HashMap<String,String>();  
    	  
//    	  appConfNames.put("server.port", "服务器端口");     
//    	  appConfNames.put("server.session-timeout", "Session超时时间");  
    	  appConfNames.put("spring.thymeleaf.prefix", "模板路径 classpath:/{配置路径} = / src/main/resources/{配置路径}" );  
    	  appConfNames.put("wapp.cacheType", "配置缓存和session存储方式，默认ehcache,可选redis、memcached");   
    	//   appConfNames.put(D.CONFIG_CACHE_PATH, "缓存配置文件路径");   
    	//   appConfNames.put(D.CONFIG_CACHE_DEFAULTNAME, "默认缓存名");  
    	   
    	  boolean isErr=true;
    	  for (String name : appConfNames.keySet()) {  
          	  if(context.getEnvironment().getProperty(name)==null) {
   			      System.out.println("缺少配置: "+name+" #"+appConfNames.get(name));
   			      isErr=false;
   		      }
          } 
    	    
    	 return isErr;
     }
} 

 


