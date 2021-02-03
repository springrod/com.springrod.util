package com.springrod.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.springrod.annotation.FieldProperty;
 
/**接口返回*/
public class ResponseBase<T> implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public void error(){
		 code=500; 
		 state=false;
	}
	 
	
	public ResponseBase() {}
	public ResponseBase(int code,String msg) {

		 setCode(code);
		 setMessage(msg);
	}
     /**
	 * 返回结果代码
	 */
	@FieldProperty(title = "结果代码", position = 0)
	private int code;
	/**
	 * 返回结果类型
	 */
	@FieldProperty(title = "状态", position = 1)
	private boolean state=true;
	/**
	 * 具体的错误信息
	 */
	@FieldProperty(title = "返回消息", position = 2)
	private String message;
	 
	@FieldProperty(title = "结果数据", position =3)
	private T data;
   
	@FieldProperty(title = "结果数据集", position =3)
	private List<T> rows=new ArrayList<T>();

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

 

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
  
	 
}


 
  