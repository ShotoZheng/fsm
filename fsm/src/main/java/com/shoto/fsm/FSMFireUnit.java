package com.shoto.fsm;


import com.google.common.base.Objects;
import com.shoto.fsm.event.IFSMEvent;
import com.shoto.fsm.state.IFSMState;
import com.shoto.fsm.transition.IFSMTransition;

/**
 * 状态事件对，指定的状态只能接受指定的事件
 * @author shoto
 */
public class FSMFireUnit<S extends IFSMState, E extends IFSMEvent> {
    /**
     * 指定的状态
     */
    private final IFSMState sourceState;
    /**
     * 可接受的事件
     */
    private final IFSMEvent event;

    /**
     * 目标状态
     */
    private final IFSMState targetState;

    /**
     * 持久化
     */
    private IFSMTransition fsmTransition;

    public FSMFireUnit(S sourceState, E event, S targetState) {
        this.sourceState = sourceState;
        this.event = event;
        this.targetState = targetState;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FSMFireUnit) {
            FSMFireUnit<S, E> other = (FSMFireUnit<S, E>)obj;
            return this.sourceState.equals(other.sourceState) && this.event.equals(other.event)
                    && this.targetState.equals(other.targetState);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // 这里使用的是google的guava包。com.google.common.base.Objects
        return Objects.hashCode(sourceState, event, targetState);
    }

    public IFSMState getTargetState() {
        return targetState;
    }

    public IFSMState getSourceState() {
        return sourceState;
    }

    public IFSMEvent getEvent() {
        return event;
    }

    public void setFsmTransition(IFSMTransition fsmTransition) {
        this.fsmTransition = fsmTransition;
    }

    public IFSMTransition getFsmTransition() {
        return fsmTransition;
    }
}

