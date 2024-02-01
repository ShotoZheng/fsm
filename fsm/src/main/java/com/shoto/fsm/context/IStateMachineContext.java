package com.shoto.fsm.context;

import com.shoto.fsm.event.IFSMEvent;
import com.shoto.fsm.state.IFSMState;

import java.util.Map;

/**
 * 状态机上下文
 * @author shoto
 */
public interface IStateMachineContext {

    IFSMState sourceState();

    IFSMEvent event();
}
