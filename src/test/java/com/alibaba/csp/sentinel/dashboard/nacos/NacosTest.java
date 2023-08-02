package com.alibaba.csp.sentinel.dashboard.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @Author Luowenjian
 * @Description
 * @Date 2023/8/1 15:15
 **/

public class NacosTest {

    ConfigService configService  = null;

    public static final String GROUP = "DEFAULT_GROUP";
    @Before
    public void initNacosConifg() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "nacos-dev-test.azj-middleware.svc.cluster.azj:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "8b4670d7-ae4f-44ad-a954-3607c06a8ff0");
        configService = NacosFactory.createConfigService(properties);
    }
    @Test
    public void testReadFromNacos() throws NacosException {
        String defaultGroup = configService.getConfig("sentinel-gw-flow", GROUP, 3000);
        System.out.println(defaultGroup);
    }

    @Test
    public void testPublishNacos() throws NacosException {
        configService.publishConfig("test", GROUP, "1234", ConfigType.TEXT.getType());
    }

}
