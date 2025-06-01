package com.shoto.spring.fsm.config;

import cn.hutool.core.lang.Pair;
import com.shoto.spring.fsm.enums.OrderStatusEventEnum;
import com.shoto.spring.fsm.enums.OrderStatusEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
@EnableStateMachine(name = "orderStatusMachine")
public class OrderStatusMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderStatusEventEnum> {

    private Map<Pair<OrderStatusEnum, OrderStatusEnum>, OrderStatusEventEnum> transitionRulesMap;

    public OrderStatusEventEnum getEvent(OrderStatusEnum sourceStatus, OrderStatusEnum targetStatus) {
        return transitionRulesMap.getOrDefault(Pair.of(sourceStatus, targetStatus), null);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderStatusEventEnum> config)
            throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderStatusEventEnum> states) throws Exception {
        states.withStates()
                .initial(OrderStatusEnum.WAIT_PAYMENT)
                .end(OrderStatusEnum.FINISH)
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderStatusEventEnum> transitions) throws Exception {
        initTransitionsRules();
        for (Map.Entry<Pair<OrderStatusEnum, OrderStatusEnum>, OrderStatusEventEnum> entry : transitionRulesMap.entrySet()) {
            Pair<OrderStatusEnum, OrderStatusEnum> pair = entry.getKey();
            transitions = transitions.withExternal().source(pair.getKey()).target(pair.getValue()).event(entry.getValue())
                    .and();
        }
    }

    private void initTransitionsRules() {
        transitionRulesMap = new LinkedHashMap<>();
        transitionRulesMap.put(Pair.of(OrderStatusEnum.WAIT_PAYMENT, OrderStatusEnum.WAIT_DELIVER), OrderStatusEventEnum.PAYED);
        transitionRulesMap.put(Pair.of(OrderStatusEnum.WAIT_DELIVER, OrderStatusEnum.WAIT_RECEIVE), OrderStatusEventEnum.DELIVERY);
        transitionRulesMap.put(Pair.of(OrderStatusEnum.WAIT_RECEIVE, OrderStatusEnum.FINISH), OrderStatusEventEnum.RECEIVED);
    }

}
