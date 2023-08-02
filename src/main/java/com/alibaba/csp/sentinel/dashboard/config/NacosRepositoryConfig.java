package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.repository.gateway.InMemApiDefinitionStore;
import com.alibaba.csp.sentinel.dashboard.repository.gateway.InMemGatewayFlowRuleStore;
import com.alibaba.csp.sentinel.dashboard.repository.rule.*;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Luowenjian
 * @Description
 * @Date 2023/8/1 18:05
 **/
@Configuration
public class NacosRepositoryConfig {
    @Autowired
    private ConfigService configService;
    @Autowired
    private NacosConfig.NacosProperties nacosProperties;

    @Bean
    public NacosRepository<ApiDefinitionEntity> apiDefinitionEntityNacosRepository(InMemApiDefinitionStore inMemApiDefinitionStore){
        return buildNacosRepository("gateway-api-definition",inMemApiDefinitionStore);
    };

    @Bean
    public NacosRepository<GatewayFlowRuleEntity> gatewayFlowRuleEntityNacosRepository(InMemGatewayFlowRuleStore inMemGatewayFlowRuleStore){
        return buildNacosRepository("gateway-flow-rule",inMemGatewayFlowRuleStore);
    };

    @Bean
    public NacosRepository<AuthorityRuleEntity> authorityRuleEntityNacosRepository(InMemAuthorityRuleStore inMemAuthorityRuleStore){
        return buildNacosRepository("authority-rule",inMemAuthorityRuleStore);
    };

    @Bean
    public NacosRepository<DegradeRuleEntity> degradeRuleEntityNacosRepository(InMemDegradeRuleStore inMemDegradeRuleStore){
        return buildNacosRepository("degrade-rule",inMemDegradeRuleStore);
    };

    @Bean
    public NacosRepository<FlowRuleEntity> flowRuleEntityNacosRepository(InMemFlowRuleStore inMemFlowRuleStore){
        return buildNacosRepository("flow-rule",inMemFlowRuleStore);
    };

    @Bean
    public NacosRepository<ParamFlowRuleEntity> paramFlowRuleEntityNacosRepository(InMemParamFlowRuleStore inMemParamFlowRuleStore){
        return buildNacosRepository("param-flow-rule",inMemParamFlowRuleStore);
    };

    @Bean
    public NacosRepository<SystemRuleEntity> systemRuleEntityNacosRepository(InMemSystemRuleStore inMemSystemRuleStore){
        return buildNacosRepository("system-rule",inMemSystemRuleStore);
    };

    private <T extends RuleEntity> NacosRepository<T> buildNacosRepository(String suffix, InMemoryRuleRepositoryAdapter<T> ruleRepositoryAdapter){
        NacosRepository<T> nacosRepository = new NacosRepository<>(suffix);
        nacosRepository.setNacosProperties(nacosProperties);
        nacosRepository.setRuleRepositoryAdapter(ruleRepositoryAdapter);
        nacosRepository.setConfigService(configService);
        return nacosRepository;
    }

}
