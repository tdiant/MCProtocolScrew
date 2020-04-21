package tdiant.mc.protocolscrew.core.trigger.type;

import tdiant.mc.protocolscrew.ProtocolScrew;
import tdiant.mc.protocolscrew.core.packets.CorePacketUnit;
import tdiant.mc.protocolscrew.core.trigger.TriggerTag;
import tdiant.mc.protocolscrew.util.ByteBufferWrench;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;
import tdiant.mc.protocolscrew.v_1_15_2.packet.status.server.StatusServerPingPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class T_0X01 extends CorePacketUnit {
    public T_0X01(ProtocolScrew protocolScrew) {
        super(protocolScrew);
    }

    @TriggerTag(packetId = 0x01)
    public ServerPacket triggerPacket(int length, DataInputStream in, DataOutputStream out, String host, int port) {
        try {
            //long playload = ByteBufferWrench.readVarLong(in);
            long playload = in.readLong();
            StatusServerPingPacket packet = new StatusServerPingPacket(out,playload);
            //packet.returnPlayload();
            return packet;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
