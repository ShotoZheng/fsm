package com.payment.fsm.service;


import com.payment.fsm.entity.PaymentNotifyMessage;

/**
 * @author shoto
 */
public interface PaymentService {
    /**
     * 支付回调通知
     * @param message 回调通知信息
     */
    String payNotify(PaymentNotifyMessage message);
}
