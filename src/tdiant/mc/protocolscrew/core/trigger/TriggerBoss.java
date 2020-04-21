package tdiant.mc.protocolscrew.core.trigger;

import tdiant.mc.protocolscrew.ProtocolScrew;
import tdiant.mc.protocolscrew.core.trigger.type.T_0X00;
import tdiant.mc.protocolscrew.core.trigger.type.T_0X01;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TriggerBoss {
    private ProtocolScrew protocolScrew;
    private Map<Integer, TriggerUnit> triggerUnitMap = new HashMap<>();

    public TriggerBoss(ProtocolScrew protocolScrew) {
        this.protocolScrew = protocolScrew;
    }

    public void registerTrigger(Trigger trigger){
        for(Method m : trigger.getClass().getMethods()){
            TriggerTag triggerTag = m.getAnnotation(TriggerTag.class);
            if(triggerTag==null) continue;

            triggerUnitMap.put(triggerTag.packetId(), new TriggerUnit(trigger,m));
        }


    }

    public ServerPacket getServerPacket(int length, int packetId, DataInputStream in, DataOutput out, String host, int port){
        if(!triggerUnitMap.containsKey(packetId)) return null;
        TriggerUnit triggerUnit = triggerUnitMap.get(packetId);
        try {
            Object obj = triggerUnit.getMethod().invoke(triggerUnit.getListenerObj(),length, in, out, host, port);
            return (ServerPacket) obj;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void registerDefaultTriggers(){
        //Register Triggers
        registerTrigger(new T_0X00(this.protocolScrew));
        registerTrigger(new T_0X01(this.protocolScrew));
    }
}
