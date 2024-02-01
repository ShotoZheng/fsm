package com.payment.fsm.controller;

import com.payment.fsm.entity.PaymentNotifyMessage;
import com.payment.fsm.service.PaymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shoto
 */
@RestController
@RequestMapping("/payment/notify")
public class PaymentNotifyController {

    @Resource
    private PaymentService paymentService;

    @RequestMapping("/payNotify")
    public String payNotify() {
        PaymentNotifyMessage message = new PaymentNotifyMessage();
        message.setPaymentNo("152653565665989860");
        return paymentService.payNotify(message);
    }
}
