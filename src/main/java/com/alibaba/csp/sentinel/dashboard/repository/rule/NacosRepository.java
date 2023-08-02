package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.config.NacosConfig;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.NacosEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Luowenjian
 * @Description
 * @Date 2023/8/1 15:29
 **/

public class NacosRepository<T extends RuleEntity> implements RuleRepository<T, Long> {

    private ConfigService configService;

    private NacosConfig.NacosProperties nacosProperties;

    private InMemoryRuleRepositoryAdapter<T> ruleRepositoryAdapter;

    private final String suffix;

    public NacosRepository(String suffix){
        this.suffix = suffix;
    }

    public ConfigService getConfigService() {
        return configService;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public NacosConfig.NacosProperties getNacosProperties() {
        return nacosProperties;
    }

    public void setNacosProperties(NacosConfig.NacosProperties nacosProperties) {
        this.nacosProperties = nacosProperties;
    }

    public InMemoryRuleRepositoryAdapter<T> getRuleRepositoryAdapter() {
        return ruleRepositoryAdapter;
    }

    public void setRuleRepositoryAdapter(InMemoryRuleRepositoryAdapter<T> ruleRepositoryAdapter) {
        this.ruleRepositoryAdapter = ruleRepositoryAdapter;
    }

    @Override
    public T save(T entity) {
        T save = ruleRepositoryAdapter.save(entity);
        publishConfig(entity.getApp());
        return save;
    }

    @Override
    public List<T> saveAll(List<T> rules) {
        List<T> ts = ruleRepositoryAdapter.saveAll(rules);
        if(ts!=null && !ts.isEmpty()){
            publishConfig(ts.get(0).getApp());
        }
        return ts;
    }

    @Override
    public T delete(Long id) {
        T delete = ruleRepositoryAdapter.delete(id);
        publishConfig(delete.getApp());
        return delete;
    }

    @Override
    public T findById(Long id) {
        return ruleRepositoryAdapter.findById(id);
    }

    @Override
    public List<T> findAllByMachine(MachineInfo machineInfo) {
        return ruleRepositoryAdapter.findAllByMachine(machineInfo);
    }

    @Override
    public List<T> findAllByApp(String appName) {
        return ruleRepositoryAdapter.findAllByApp(appName);
    }

    private void publishConfig(String app) {
        try {
            List<T> allByApp = ruleRepositoryAdapter.findAllByApp(app);
            List<Object> rules = allByApp.stream().map(NacosEntity::new).map(NacosEntity::getRule).collect(Collectors.toList());
            configService.publishConfig(app + "-" + suffix, nacosProperties.getGroup(), JSON.toJSONString(rules), ConfigType.JSON.getType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
