package com.shoto.spring.fsm.entity;

import com.shoto.spring.fsm.enums.OrderStatusEnum;
import lombok.Data;

@Data
public class Order {
    private Long orderId;
    private OrderStatusEnum orderStatus;
}