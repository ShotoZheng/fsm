package com.shoto.fsm.transition;

import com.shoto.fsm.context.IStateMachineContext;
import com.shoto.fsm.state.IFSMState;

/**
 * 状态机持久化机制
 * @author shoto
 */
public interface IFSMTransition<T extends IStateMachineContext, S extends IFSMState> {
    /**
     * 做持久化
     * @param context 上下文
     * @param targetState 目标状态
     */
    void onTransition(T context, S targetState);
}
