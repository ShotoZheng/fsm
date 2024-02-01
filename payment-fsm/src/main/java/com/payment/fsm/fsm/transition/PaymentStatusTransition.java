package com.payment.fsm.fsm.transition;

import com.payment.fsm.entity.PaymentInteractEntity;
import com.payment.fsm.fsm.constant.FSMConstants;
import com.payment.fsm.fsm.context.PaymentStatusContext;
import com.payment.fsm.fsm.state.PaymentFsmState;
import com.shoto.fsm.transition.IFSMTransition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 支付状态持久化
 * @author admin
 */
@Slf4j
@Component
public class PaymentStatusTransition implements IFSMTransition<PaymentStatusContext, PaymentFsmState> {

    @Override
    public void onTransition(PaymentStatusContext context, PaymentFsmState targetState) {
        PaymentInteractEntity entity = context.getParam(FSMConstants.PAYMENT_FSM_PARAM);
        // 分布式锁 start...
        // 判断支付记录是否存在，存在一定是更新，否则是插入
        /*
        if () {
            log.info("insert into t_payment_interact values({}, {})",
                     entity.getPaymentNo(), entity.getPaymentStatus());
        } else {*/
            // 更新
            log.info("update t_payment_interact set payment_status = {} where payment_status = {} and payment_no = {}",
                    targetState.getBizStatusEnum().getStatus(), entity.getPaymentStatus(), entity.getPaymentNo());
        // 分布式锁 end...
        }

}
