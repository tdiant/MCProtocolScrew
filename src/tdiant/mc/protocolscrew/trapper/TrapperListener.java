package tdiant.mc.protocolscrew.trapper;

import tdiant.mc.protocolscrew.v_1_15_2.packet.FunnyPacket;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface TrapperListener {
    Class packetType() default FunnyPacket.class;
}
