package com.shoto.fsm.machine;

import com.shoto.fsm.FSMFireUnit;
import com.shoto.fsm.context.IStateMachineContext;
import com.shoto.fsm.event.IFSMEvent;
import com.shoto.fsm.state.IFSMState;
import com.shoto.fsm.transition.IFSMTransition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 状态机
 *
 * @author shoto
 */
@Slf4j
@Component
public class SimpleStateMachine implements IStateMachine {

    private final Map<String, FSMFireUnit<IFSMState, IFSMEvent>> stateEventMap = new HashMap<>();

    /**
     * 只接受指定的当前状态下，指定的事件触发，可以到达的指定目标状态
     */
    public FSMFireUnit<IFSMState, IFSMEvent> accept(IFSMState sourceState, IFSMEvent event, IFSMState targetState) {
        String key = getKey(sourceState, event);
        FSMFireUnit<IFSMState, IFSMEvent> unit = new FSMFireUnit<>(sourceState, event, targetState);
        stateEventMap.put(key, unit);
        return unit;
    }

    private String getKey(IFSMState source, IFSMEvent fsmEvent) {
        return source.val() + ":" + fsmEvent.val();
    }

    /**
     * 通过源状态和事件，获取目标状态单元
     */
    public FSMFireUnit<IFSMState, IFSMEvent> getFireUnit(IFSMState sourceState, IFSMEvent event) {
        String key = getKey(sourceState, event);
        return stateEventMap.get(key);
    }

    @Override
    public void fire(IStateMachineContext context) {
        // 根据当前状态和事件，去获取目标状态单元
        FSMFireUnit<IFSMState, IFSMEvent> unit = getFireUnit(context.sourceState(), context.event());
        // 如果目标状态单元不为空，说明是可以推进的
        if (Objects.isNull(unit)) {
            // 目标状态单元为空，说明是非法推进，进入异常处理，这里只是抛出去，由调用者去具体处理
            throw new RuntimeException("状态机流转失败");
        }
        log.info("fsm fire from status:{} to status:{} by event:{}", context.sourceState(),
                unit.getTargetState(), context.event());
        IFSMTransition fsmTransition = unit.getFsmTransition();
        if (fsmTransition != null) {
            fsmTransition.onTransition(context, unit.getTargetState());
        }
    }
}

