package com.shoto.spring.fsm.service;

import com.shoto.spring.fsm.entity.Order;

import java.util.Map;

public interface OrderService {
    Order create();

    Order pay(long id);

    Order deliver(long id);

    Order receive(long id);

    Map<Long, Order> getOrders();
}
