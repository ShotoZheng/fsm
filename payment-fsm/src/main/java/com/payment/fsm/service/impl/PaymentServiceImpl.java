package com.payment.fsm.service.impl;

import com.payment.fsm.fsm.constant.FSMConstants;
import com.payment.fsm.fsm.context.PaymentStatusContext;
import com.payment.fsm.entity.PaymentInteractEntity;
import com.payment.fsm.entity.PaymentNotifyMessage;
import com.payment.fsm.enums.PaymentStatusEnum;
import com.payment.fsm.fsm.event.PaymentFsmEvent;
import com.payment.fsm.fsm.machine.PaymentStatusMachine;
import com.payment.fsm.service.PaymentService;
import com.payment.fsm.fsm.state.PaymentFsmState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付领域域服务
 * @author shoto
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentStatusMachine paymentStatusMachine;

    /**
     * 支付结果通知
     */
    @Override
    public String payNotify(PaymentNotifyMessage message) {
        PaymentStatusContext statusContext = new PaymentStatusContext();
        PaymentInteractEntity paymentInteract = buildPayEditParams(message.getPaymentNo());
        statusContext.addExtendParam(FSMConstants.PAYMENT_FSM_PARAM, paymentInteract);
        statusContext.setSourceState(PaymentFsmState.getByBizStatus(paymentInteract.getPaymentStatus()));
        statusContext.setEvent(PaymentFsmEvent.PAY_SUCCESS);
        paymentStatusMachine.fire(statusContext);
        return "SUCCESS";
    }

    /**
     * 此处检索DB,如果支付记录不存在则构建插入参数，否则构建更新参数
     * 当然，在支付回调时一般都是更新参数
     */
    private PaymentInteractEntity buildPayEditParams(String paymentNo) {
        PaymentInteractEntity paymentInteractEntity = new PaymentInteractEntity();
        paymentInteractEntity.setPaymentNo(paymentNo);
        paymentInteractEntity.setPaymentStatus(PaymentStatusEnum.FAILED.getStatus());
        // 也可以添加其他参数...
        return paymentInteractEntity;
    }
}

