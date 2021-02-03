package com.springrod.util; 
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * IOC容器工具。
 * @author maxl
 * @time 2018-04-27。
 */
@Component
public class ContextUtil implements ApplicationContextAware {

     
    private static ApplicationContext APPLICATION_CONTEXT;
 

    /**
     * 获取容器
     * @return
     */
    public static ApplicationContext getInstance() {
         return APPLICATION_CONTEXT;
    }
    /**
       * 获取配置数据
     * */
    public static String getConfig(String name) {
    	  return APPLICATION_CONTEXT.getEnvironment().getProperty(name);
    }

    public static int getConfigForInt(String name ) {
    	 return getConfigForInt(name,0); 
    }
    public static int getConfigForInt(String name,int defaultVal) {
  	     String conf= getConfig(name); 
  	     return conf==null?defaultVal: Integer.parseInt(conf);
     }
    /**
     * 获取容器对象
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return APPLICATION_CONTEXT.getBean(type);
    }


    /**
     * 设置spring上下文
     * @param applicationContext spring上下文
     * @throws BeansException
     * */ 
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       
        APPLICATION_CONTEXT = applicationContext;
    }
}