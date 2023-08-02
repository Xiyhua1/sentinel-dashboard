package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @Author Luowenjian
 * @Description
 * @Date 2023/8/1 15:30
 **/
@Configuration
@EnableConfigurationProperties(NacosConfig.NacosProperties.class)
public class NacosConfig {

    @ConfigurationProperties(prefix = "nacos.config")
    public static class NacosProperties{
        private String group = "DEFAULT_GROUP";
        private String namespace;
        private Long timeout = 3000L;

        private String serverAddr;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public Long getTimeout() {
            return timeout;
        }

        public void setTimeout(Long timeout) {
            this.timeout = timeout;
        }

        public String getServerAddr() {
            return serverAddr;
        }

        public void setServerAddr(String serverAddr) {
            this.serverAddr = serverAddr;
        }
    }

    @Bean
    public ConfigService configService(NacosProperties nacosProperties) throws NacosException {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
        if (!StringUtils.isEmpty(nacosProperties.getNamespace())) {
            properties.setProperty(PropertyKeyConst.NAMESPACE, nacosProperties.getNamespace());
        }
        return NacosFactory.createConfigService(properties);
    }

}
