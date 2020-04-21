package tdiant.mc.protocolscrew.trapper;

import tdiant.mc.protocolscrew.ProtocolScrew;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrapperBoss {
    private ProtocolScrew protocolScrew;
    private Map<Class, ArrayList<TrapperUnit>> trapperUnitMap = new HashMap<>();

    public TrapperBoss(ProtocolScrew protocolScrew) {
        this.protocolScrew = protocolScrew;
    }

    public void registerTrapper(Trapper trapper){
        for(Method m : trapper.getClass().getMethods()){
            TrapperListener trapperListener = (TrapperListener)m.getAnnotation(TrapperListener.class);
            if(trapperListener==null || trapperListener.packetType()==null) continue;

            if(!trapperUnitMap.containsKey(trapperListener.packetType()))
                trapperUnitMap.put(trapperListener.packetType(),new ArrayList<>());

            ArrayList<TrapperUnit> trapperUnitList = trapperUnitMap.get(trapperListener.packetType());
            trapperUnitList.add(new TrapperUnit(trapper,m));
        }
    }

    public void callPacket(ServerPacket packet){
        if(packet==null) return;
        ArrayList<TrapperUnit> trapperUnitArrayList = this.trapperUnitMap.get(packet.getClass());
        if(trapperUnitArrayList==null) return;
        for(TrapperUnit trapperUnit:trapperUnitArrayList){
            try {
                trapperUnit.getMethod().setAccessible(true);
                trapperUnit.getMethod().invoke(trapperUnit.getListenerObj(),packet);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
