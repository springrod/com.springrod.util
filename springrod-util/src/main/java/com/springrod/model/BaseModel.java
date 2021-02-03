package com.springrod.model;

import java.util.UUID;

import javax.validation.groups.Default;
 
import com.springrod.annotation.FieldProperty;

public class BaseModel {
	/**新增校验 */
	public interface Add extends Default { }

	/**更新校验 */
    public interface Update extends Default { }
	
	/**删除校验 */
	public interface Delete extends Default { }
	
	/**查询校验 */
    public interface Select extends Default { }
 
	public BaseModel(){ 
		_model_sn= "srm_"+UUID.randomUUID().toString().replaceAll("-", "");
	}
	@FieldProperty(title = "返回消息", position = 2,hidden=true)
	private String _model_sn;/**实例化流水号 */

	/**
	 * @return the _model_sn
	 */
	public String get_model_sn() {
		return _model_sn;
	}

	/**
	 * @param _model_sn the _model_sn to set
	 */
	public void set_model_sn(String _model_sn) {
		this._model_sn = _model_sn;
	}
 
}
