package com.shoto.fsm.machine;

import com.shoto.fsm.context.IStateMachineContext;

/**
 * 状态机
 * @author shoto
 */
public interface IStateMachine {

    void fire(IStateMachineContext context);
}
