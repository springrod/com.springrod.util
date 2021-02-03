// package com.springrod.util.util;
 
// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.FileInputStream;
 
// public class  ClassLoader extends java.lang.ClassLoader{
	
// 	private static final String SUFFIX = ".class";
// 	public String[] paths;
 
// 	public  ClassLoader(String[] paths) {
// 		this.paths = paths;
// 	}
	
// 	public  ClassLoader(ClassLoader parent,String[] paths){
// 		super(parent);
// 		this.paths = paths;
// 	}
 
// 	@SuppressWarnings("deprecation")
// 	@Override
// 	protected Class<?> findClass(String className) throws ClassNotFoundException { 
// 		String classPath = getClassPath(className);
// 		if(classPath != null){
// 			byte[] clazz = loadClazz(classPath);
// 			return defineClass(clazz, 0, clazz.length); 
// 		}else{
// 			System.out.println("class is not found !");
// 			return null;
// 		}
// 	}
// 	public void loadJar(String path) throws NoSuchMethodException, SecurityException, MalformedURLException {
// 		File libPath = new File(path);
		
// 	    // 获取所有的.jar和.zip文件
// 		File[] jarFiles = libPath.listFiles(new FilenameFilter() {
// 			public boolean accept(File dir, String name) {
// 				return name.endsWith(".jar") || name.endsWith(".zip");
// 			}
// 		});
	   
// 	  if (jarFiles != null) {
// 		  // 从URLClassLoader类中获取类所在文件夹的方法
// 		  // 对于jar文件，可以理解为一个存放class文件的文件夹
// 		  Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
// 		  boolean accessible = method.isAccessible();     // 获取方法的访问权限
// 		  try {
// 			  if (accessible == false) {
// 				  method.setAccessible(true);     // 设置方法的访问权限
// 			  }
// 			  // 获取系统类加载器
// 			  URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
// 			  for (File file : jarFiles) {
// 				  URL url = file.toURI().toURL();
// 				  try {
// 					  method.invoke(classLoader, url); 
// 					  System.out.println("读取jar文件[name={}]" +file.getName());
// 				  } catch (Exception e) {
// 					  System.out.println("读取jar文件[name={}]失败" +file.getName()); 
// 				  }
// 			  }
// 		  } finally {
// 			  method.setAccessible(accessible);
// 		  }
// 	  }
// 	}
// 	public byte[] loadClazz(String classPath) { 
// 		try { 
// 			FileInputStream in = new FileInputStream(new File(classPath));
// 			ByteArrayOutputStream baos = new ByteArrayOutputStream();
// 			int b;
// 			while((b = in.read()) != -1){
// 				baos.write(b);
// 			}
// 			in.close();
// 			return baos.toByteArray();
// 		} catch (Exception e) {
// 			System.out.println(e);
// 		}
// 		return null;
// 	}
	
// 	public String getClassPath(String className){
// 		for(String path : paths){
			
// 			 System.out.println(className);
// 			//if(className.contains(".")){
				
// 			 className = className.replaceAll("\\.","\\\\");
// 			//}
// 			String classPath = path + className + SUFFIX; 
// 			 System.out.println(classPath);
			
// 			File classFile = new File(classPath);
// 			if(classFile.exists()){
// 				return classPath;
// 			}
// 		}
// 		return null;
// 	}
// }
