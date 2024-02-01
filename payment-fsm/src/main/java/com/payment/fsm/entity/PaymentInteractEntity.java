package com.payment.fsm.entity;

import lombok.Data;

/**
 * 支付记录实体
 */
@Data
public class PaymentInteractEntity {

    private String paymentNo;

    private Integer paymentStatus;
}
