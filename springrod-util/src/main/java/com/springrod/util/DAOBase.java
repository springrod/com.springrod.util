package com.springrod.util;
import java.util.List;
import com.springrod.model.QueryParam;  
/**
 * 基本数据操作接口。
 * 
 * @author seerafe
 * @time 2018-05-16。
 */
public interface DAOBase<T,K> {

    void insert(T t);
    void delete(K k);
    void update(T t);
    T get(K k);
    List<T> list(QueryParam queryParam);
    int count(QueryParam queryParam); 
} 