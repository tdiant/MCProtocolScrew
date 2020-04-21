package tdiant.mc.protocolscrew.v_1_15_2.packet;

import com.sun.istack.internal.NotNull;

import java.io.DataOutputStream;

public abstract class ServerPacket {
    private String name;
    private int packetId;

    private DataOutputStream out;

    public ServerPacket(String name, int packetId, DataOutputStream out) {
        this.name = name;
        this.packetId = packetId;
        this.out = out;
    }

    @NotNull
    public String getName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }

    public int getPacketId() {
        return packetId;
    }

    public DataOutputStream getDataOutputStream(){
        return out;
    }
}
