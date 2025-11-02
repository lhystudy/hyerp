package com.hyerp.dao;

import com.hyerp.model.ConfigAttribute;

import com.hyerp.dao.ConfigAttributeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FollowListMapperTest {
    @Autowired
    ConfigAttributeMapper configAttributeMapper;

    @Test
    public void testInert() {
        ConfigAttribute configAttribute = new ConfigAttribute();
        configAttribute.setConfigName("test2");
        configAttribute.setConfigValue("1112");
        configAttribute.setInstId("test1");
        configAttributeMapper.insert(configAttribute);
        System.out.println(configAttribute);
    }
}
