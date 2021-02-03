package  com.springrod.model;
  
 
 
public class WebModel  {

      private String root="";

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
    public String getRoot(String action){
        return root+action;
    }
    public String resource(String action){
        return root+action;
    }
} 


 