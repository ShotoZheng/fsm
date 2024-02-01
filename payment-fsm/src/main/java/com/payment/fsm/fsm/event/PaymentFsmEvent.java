package com.payment.fsm.fsm.event;


import com.shoto.fsm.event.IFSMEvent;

/**
 * 支付事件
 * @author shoto
 */
public enum PaymentFsmEvent implements IFSMEvent {
    // 支付创建
    PAY_CREATE("PAY_CREATE", "支付创建"),
    // 支付中
    PAY_PROCESS("PAY_PROCESS", "支付中"),
    // 支付成功
    PAY_SUCCESS("PAY_SUCCESS", "支付成功"),
    // 支付失败
    PAY_FAIL("PAY_FAIL", "支付失败");

    /**
     * 事件
     */
    private String event;
    /**
     * 事件描述
     */
    private String description;

    PaymentFsmEvent(String event, String description) {
        this.event = event;
        this.description = description;
    }

    @Override
    public String val() {
        return event;
    }

    @Override
    public String desc() {
        return description;
    }
}

