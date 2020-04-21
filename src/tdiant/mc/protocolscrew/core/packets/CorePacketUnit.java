package tdiant.mc.protocolscrew.core.packets;

import tdiant.mc.protocolscrew.ProtocolScrew;
import tdiant.mc.protocolscrew.core.trigger.Trigger;
import tdiant.mc.protocolscrew.core.trigger.TriggerTag;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class CorePacketUnit implements Trigger {
    private ProtocolScrew protocolScrew;

    public CorePacketUnit(ProtocolScrew protocolScrew) {
        this.protocolScrew = protocolScrew;
    }

    @TriggerTag public abstract ServerPacket triggerPacket(int length, DataInputStream in, DataOutputStream out, String host, int port);

    public ProtocolScrew getProtocolScrew() {
        return protocolScrew;
    }
}
