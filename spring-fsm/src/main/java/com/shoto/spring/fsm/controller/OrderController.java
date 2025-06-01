package com.shoto.spring.fsm.controller;

import com.shoto.spring.fsm.entity.Order;
import com.shoto.spring.fsm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sprig-fsm")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public void testFsm() {
        Order order = orderService.create();
        orderService.pay(order.getOrderId());
        orderService.deliver(order.getOrderId());
        orderService.receive(order.getOrderId());
        System.out.println("全部订单状态：" + orderService.getOrders());
    }
}
