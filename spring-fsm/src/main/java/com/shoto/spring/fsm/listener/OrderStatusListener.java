package com.shoto.spring.fsm.listener;

import com.shoto.spring.fsm.constants.OrderConstants;
import com.shoto.spring.fsm.entity.Order;
import com.shoto.spring.fsm.enums.OrderStatusEventEnum;
import com.shoto.spring.fsm.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@WithStateMachine(name = "orderStatusMachine")
@Transactional
public class OrderStatusListener {

    @OnTransition
    public boolean anyTransition(StateContext<OrderStatusEnum, OrderStatusEventEnum> stateContext) {
        OrderStatusEventEnum event = stateContext.getEvent();
        State<OrderStatusEnum, OrderStatusEventEnum> source = stateContext.getSource();
        State<OrderStatusEnum, OrderStatusEventEnum> target = stateContext.getTarget();
        Order order = (Order) stateContext.getMessageHeader(OrderConstants.ORDER_FSM_ENTITY);
        log.info("order id: {} from {} to {},event:{}", order.getOrderId(), source.getId(), target.getId(), event);
        return true;
    }
}
