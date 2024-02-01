package com.payment.fsm.fsm.config;

import com.payment.fsm.fsm.event.PaymentFsmEvent;
import com.payment.fsm.fsm.machine.PaymentStatusMachine;
import com.payment.fsm.fsm.state.PaymentFsmState;
import com.payment.fsm.fsm.transition.PaymentStatusTransition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 支付状态状态机流转规则配置
 * @author shoto
 */
@Component
public class PaymentStatusFsmConfig {

    @Resource
    private PaymentStatusMachine paymentStatusMachine;

    @Resource
    private PaymentStatusTransition paymentStatusTransition;

    /**
     * 初始化添加状态机规则
     */
    @PostConstruct
    public void configure() {
        // 初始状态
        paymentStatusMachine.accept(PaymentFsmState.INIT, PaymentFsmEvent.PAY_CREATE, PaymentFsmState.INIT)
                .setFsmTransition(paymentStatusTransition);
        // 初始化》支付中
        paymentStatusMachine.accept(PaymentFsmState.INIT, PaymentFsmEvent.PAY_PROCESS, PaymentFsmState.PAYING)
                .setFsmTransition(paymentStatusTransition);
        // 支付中》支付成功
        paymentStatusMachine.accept(PaymentFsmState.PAYING, PaymentFsmEvent.PAY_SUCCESS, PaymentFsmState.PAID)
                .setFsmTransition(paymentStatusTransition);
        // 初始化》支付成功
        paymentStatusMachine.accept(PaymentFsmState.INIT, PaymentFsmEvent.PAY_SUCCESS, PaymentFsmState.PAID)
                .setFsmTransition(paymentStatusTransition);
        // 支付失败》支付成功
        paymentStatusMachine.accept(PaymentFsmState.FAILED, PaymentFsmEvent.PAY_SUCCESS, PaymentFsmState.PAID)
                .setFsmTransition(paymentStatusTransition);
        // 初始化》支付失败
        paymentStatusMachine.accept(PaymentFsmState.INIT, PaymentFsmEvent.PAY_PROCESS, PaymentFsmState.FAILED)
                .setFsmTransition(paymentStatusTransition);
        // 支付中》支付失败
        paymentStatusMachine.accept(PaymentFsmState.PAYING, PaymentFsmEvent.PAY_FAIL, PaymentFsmState.FAILED)
                .setFsmTransition(paymentStatusTransition);
    }



}
