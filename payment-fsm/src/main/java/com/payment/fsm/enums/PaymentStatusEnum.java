package com.payment.fsm.enums;

/**
 * @Description
 * @Author zhengst
 * @Date 2024/1/31 17:34
 * @Version 1.0
 */
public enum PaymentStatusEnum {

    /**
     * 支付状态枚举
     */
    INIT(0, "初始化"),
    PAYING(1, "支付中"),
    PAID(2, "支付成功"),
    FAILED(9, "支付失败");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String description;

    PaymentStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentStatusEnum getByStatus(Integer status) {
        for (PaymentStatusEnum statusEnum : PaymentStatusEnum.values()) {
            if (statusEnum.status.equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }
}
