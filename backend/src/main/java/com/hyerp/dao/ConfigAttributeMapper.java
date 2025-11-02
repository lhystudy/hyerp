package com.hyerp.dao;

import com.hyerp.model.ConfigAttribute;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfigAttributeMapper {
    int insert(ConfigAttribute record);
    int updateByPrimaryKey(ConfigAttribute record);

    ConfigAttribute getAttrByInstIdAndName(String instId, String configName);

    List<ConfigAttribute> getAllAttr();

    ConfigAttribute getAttrById(int id);

    int delete(int id);
}
