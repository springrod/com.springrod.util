package com.springrod.util;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired; 
import com.springrod.model.HttpRequest;
import com.springrod.model.WebModel;
import org.springframework.ui.Model;
import org.springframework.core.env.Environment;
/**基础控制层*/
@Controller
public class ControllerBase {
     @Autowired
     private Environment environment;
 
     private WebModel web;
     protected Model parseModel(Model model){
       /***添加HTTP请求工具 */
       model.addAttribute("http", new HttpRequest()); 
       model.addAttribute("config", environment);
       model.addAttribute("web",web);
       model.addAttribute("staticRoot","../");
        
       return model; 
 
     }

     public WebModel getWeb() {
          if(web==null)web=new WebModel(); 
          return web;
     }

     public void setWeb(WebModel web) {
          this.web = web;
     }

      
  
}
