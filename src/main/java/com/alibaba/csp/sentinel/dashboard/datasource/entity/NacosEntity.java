package com.alibaba.csp.sentinel.dashboard.datasource.entity;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;

/**
 * @Author Luowenjian
 * @Description
 * @Date 2023/8/2 10:03
 **/

public class NacosEntity {
    private final RuleEntity ruleEntity;

    public NacosEntity(RuleEntity ruleEntity) {
        this.ruleEntity = ruleEntity;
    }

    public Object getRule() {
        if (ruleEntity instanceof ApiDefinitionEntity) {
            return ((ApiDefinitionEntity) ruleEntity).toApiDefinition();
        } if (ruleEntity instanceof GatewayFlowRuleEntity){
            return ((GatewayFlowRuleEntity) ruleEntity).toGatewayFlowRule();
        }else {
            return ruleEntity.toRule();
        }
    }
}
