package tdiant.mc.protocolscrew.core.trigger.type;

import tdiant.mc.protocolscrew.ProtocolScrew;
import tdiant.mc.protocolscrew.connection.ConnectionUnit;
import tdiant.mc.protocolscrew.core.packets.CorePacketUnit;
import tdiant.mc.protocolscrew.core.trigger.TriggerTag;
import tdiant.mc.protocolscrew.util.ByteBufferWrench;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;
import tdiant.mc.protocolscrew.v_1_15_2.packet.handshake.server.HandshakeServerPacket;
import tdiant.mc.protocolscrew.v_1_15_2.packet.login.server.LoginServerStartPacket;
import tdiant.mc.protocolscrew.v_1_15_2.packet.status.server.StatusServerRequestPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class T_0X00 extends CorePacketUnit {
    public T_0X00(ProtocolScrew protocolScrew) {
        super(protocolScrew);
    }

    @TriggerTag(packetId = 0x00)
    public ServerPacket triggerPacket(int length, DataInputStream in, DataOutputStream out, String host, int port) {
        try {
            ConnectionUnit.State s = this.getProtocolScrew().getConnectionState(host, port);

            if (s == ConnectionUnit.State.Handshaking) {
                int protocolVersion = ByteBufferWrench.readVarInt(in);
                final String ip = ByteBufferWrench.readUTF8(in);
                final int prt = in.readUnsignedShort();
                int state = ByteBufferWrench.readVarInt(in);
                if (state == 1) {
                    //Push to Status
                    this.getProtocolScrew().pushConnectionState(host, port, ConnectionUnit.State.Status);
                } else if (state == 2) {
                    //Push to Login
                    this.getProtocolScrew().pushConnectionState(host, port, ConnectionUnit.State.Login);
                }
                return new HandshakeServerPacket(out,protocolVersion, ip, prt, state);
            } else if(s== ConnectionUnit.State.Status){
                return new StatusServerRequestPacket(out);
            } else if(s== ConnectionUnit.State.Login){
                final String playerName = ByteBufferWrench.readUTF8(in);
                return new LoginServerStartPacket(out,playerName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
