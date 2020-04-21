package tdiant.mc.protocolscrew.core.trigger;

import java.lang.reflect.Method;

public class TriggerUnit {
    private Object listenerObj;
    private Method method;

    public TriggerUnit(Object listenerObj, Method method) {
        this.listenerObj = listenerObj;
        this.method = method;
    }

    public Object getListenerObj() {
        return listenerObj;
    }

    public Method getMethod() {
        return method;
    }
}
