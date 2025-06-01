package com.shoto.spring.fsm.service;

import com.shoto.spring.fsm.entity.Order;
import com.shoto.spring.fsm.enums.OrderStatusEnum;

import java.util.function.Consumer;

public interface OrderFsmService {

    boolean transition(Order order, OrderStatusEnum targetStatus);

    void transition(Order order, OrderStatusEnum targetStatus, Consumer<Boolean> callback);
}
