package com.shoto.spring.fsm.service.impl;

import com.shoto.spring.fsm.config.OrderStatusMachineConfig;
import com.shoto.spring.fsm.constants.OrderConstants;
import com.shoto.spring.fsm.entity.Order;
import com.shoto.spring.fsm.enums.OrderStatusEventEnum;
import com.shoto.spring.fsm.enums.OrderStatusEnum;
import com.shoto.spring.fsm.service.OrderFsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Service
public class OrderFsmServiceImpl implements OrderFsmService {

    @Autowired
    private StateMachine<OrderStatusEnum, OrderStatusEventEnum> orderStateMachine;

    @Autowired
    private OrderStatusMachineConfig machineConfig;

    @Override
    public boolean transition(Order order, OrderStatusEnum targetStatus) {
        OrderStatusEventEnum event = machineConfig.getEvent(order.getOrderStatus(), targetStatus);
        if (Objects.isNull(event)) {
            return false;
        }
        Message<OrderStatusEventEnum> message = MessageBuilder
                .withPayload(event).setHeader(OrderConstants.ORDER_FSM_ENTITY, order).build();
        StateMachineEventResult<OrderStatusEnum, OrderStatusEventEnum> result =
                orderStateMachine.sendEvent(Mono.just(message)).blockFirst();
        return result != null && result.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
    }

    @Override
    public void transition(Order order, OrderStatusEnum targetStatus, Consumer<Boolean> callback) {
        OrderStatusEventEnum event = machineConfig.getEvent(order.getOrderStatus(), targetStatus);
        if (Objects.isNull(event)) {
            callback.accept(false);
            return;
        }
        Message<OrderStatusEventEnum> message = MessageBuilder
                .withPayload(event).setHeader(OrderConstants.ORDER_FSM_ENTITY, order).build();
        Flux<Boolean> flux = orderStateMachine.sendEvent(Mono.just(message))
                .map(result -> result.getResultType() == StateMachineEventResult.ResultType.ACCEPTED)
                .defaultIfEmpty(false);
        flux.subscribe(callback);
    }
}
