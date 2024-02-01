package com.shoto.fsm.context;

import com.shoto.fsm.event.IFSMEvent;
import com.shoto.fsm.state.IFSMState;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author shoto
 */
@ToString
public abstract class BaseStateMachineCommonContext implements IStateMachineContext {

    private IFSMState sourceState;
    private IFSMEvent event;
    private Map<String, Object> paramsMap;

    @Override
    public IFSMState sourceState() {
        return this.sourceState;
    }

    @Override
    public IFSMEvent event() {
        return this.event;
    }

    public void addExtendParam(String key, Object value) {
        if (Objects.isNull(paramsMap) || paramsMap.isEmpty()) {
            this.paramsMap = new HashMap<>();
        }
        this.paramsMap.put(key, value);
    }

    public <T> T getParam(String key) {
        return (T) paramsMap.get(key);
    }

    public void setSourceState(IFSMState sourceState) {
        this.sourceState = sourceState;
    }

    public void setEvent(IFSMEvent event) {
        this.event = event;
    }
}
