package com.payment.fsm.fsm.state;

import com.payment.fsm.enums.PaymentStatusEnum;
import com.shoto.fsm.state.IFSMState;

/**
 * 支付状态机
 * @author shoto
 */
public enum PaymentFsmState implements IFSMState {

    /**
     * 支付状态枚举
     */
    INIT("INIT", "初始化", PaymentStatusEnum.INIT),
    PAYING("PAYING", "支付中", PaymentStatusEnum.PAYING),
    PAID("PAID", "支付成功", PaymentStatusEnum.PAID),
    FAILED("FAILED", "支付失败", PaymentStatusEnum.FAILED),
    ;

    /**
     * 状态
     */
    private final String status;

    /**
     * 描述
     */
    private final String description;

    /**
     * 业务支付状态枚举
     */
    private final PaymentStatusEnum bizStatusEnum;

    PaymentFsmState(String status, String description, PaymentStatusEnum bizStatusEnum) {
        this.status = status;
        this.description = description;
        this.bizStatusEnum = bizStatusEnum;
    }

    @Override
    public String val() {
        return this.status;
    }

    @Override
    public String desc() {
        return this.description;
    }

    public PaymentStatusEnum getBizStatusEnum() {
        return bizStatusEnum;
    }

    public static PaymentFsmState getByBizStatus(Integer bizStatus) {
        PaymentStatusEnum bizStatusEnum = PaymentStatusEnum.getByStatus(bizStatus);
        for (PaymentFsmState fsmState : PaymentFsmState.values()) {
            if (fsmState.getBizStatusEnum().equals(bizStatusEnum)) {
                return fsmState;
            }
        }
        return null;
    }

}
