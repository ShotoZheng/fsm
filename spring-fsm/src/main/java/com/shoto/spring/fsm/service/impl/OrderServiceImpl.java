package com.shoto.spring.fsm.service.impl;

import com.shoto.spring.fsm.entity.Order;
import com.shoto.spring.fsm.enums.OrderStatusEnum;
import com.shoto.spring.fsm.service.OrderFsmService;
import com.shoto.spring.fsm.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderFsmService orderFsmService;

    private long id = 1L;

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order create() {
        // mock db insert
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.WAIT_PAYMENT);
        order.setOrderId(id++);
        orders.put(order.getOrderId(), order);
        System.out.println("订单创建成功:" + order);
        return order;
    }

    @Override
    public Order pay(long id) {
        Order order = orders.get(id);
        System.out.println("尝试支付，订单号：" + id);
        if (!orderFsmService.transition(order, OrderStatusEnum.WAIT_DELIVER)) {
            System.out.println("支付失败，状态异常，订单号：" + id);
        }
        order.setOrderStatus(OrderStatusEnum.WAIT_DELIVER);
        return orders.get(id);
    }

    @Override
    public Order deliver(long id) {
        Order order = orders.get(id);
        System.out.println(" 尝试发货，订单号：" + id);
        if (!orderFsmService.transition(order, OrderStatusEnum.WAIT_RECEIVE)) {
            System.out.println("发货失败，状态异常，订单号：" + id);
        }
        order.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE);
        return orders.get(id);
    }

    @Override
    public Order receive(long id) {
        Order order = orders.get(id);
        System.out.println(" 尝试收货，订单号：" + id);
        if (!orderFsmService.transition(order, OrderStatusEnum.FINISH)) {
            System.out.println("收货失败，状态异常，订单号：" + id);
        }
        order.setOrderStatus(OrderStatusEnum.FINISH);
        return orders.get(id);
    }

    @Override
    public Map<Long, Order> getOrders() {
        return orders;
    }
}
