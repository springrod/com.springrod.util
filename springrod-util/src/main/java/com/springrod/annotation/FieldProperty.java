package com.springrod.annotation;  
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;  



// @Target(ElementType.METHOD)
// @Retention(RetentionPolicy.RUNTIME)
public @interface FieldProperty{
 
     public String title() default "";   
     public String description() default ""; 
     public int position() default 0; 
     /**#对外不可见 */
     public boolean hidden() default false; 

     
} 