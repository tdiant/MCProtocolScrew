package tdiant.mc.protocolscrew.v_1_15_2.packet.handshake.server;

import tdiant.mc.protocolscrew.util.ByteBufferWrench;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakeServerPingPacket extends ServerPacket {
    public HandshakeServerPingPacket(DataOutputStream out) {
        super("Handshake Serverbound Ping", 0xFE, out);
    }

    public void sendFE() throws IOException {
        ByteBufferWrench.writeVarInt(this.getDataOutputStream(),3);
        ByteBufferWrench.writeVarInt(this.getDataOutputStream(),0xFE);
        this.getDataOutputStream().writeByte(1);
        this.getDataOutputStream().flush();
    }
}
