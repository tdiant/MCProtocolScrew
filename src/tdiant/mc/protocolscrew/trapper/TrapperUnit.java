package tdiant.mc.protocolscrew.trapper;

import java.lang.reflect.Method;

public class TrapperUnit {
    private Object trapperObj;
    private Method method;

    public TrapperUnit(Object listenerObj, Method method) {
        this.trapperObj = listenerObj;
        this.method = method;
    }

    public Object getListenerObj() {
        return trapperObj;
    }

    public Method getMethod() {
        return method;
    }
}
