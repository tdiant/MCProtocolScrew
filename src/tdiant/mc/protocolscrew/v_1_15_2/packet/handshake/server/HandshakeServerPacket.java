package tdiant.mc.protocolscrew.v_1_15_2.packet.handshake.server;

import tdiant.mc.protocolscrew.util.ByteBufferWrench;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakeServerPacket extends ServerPacket {
    private int protocolVersion = 578;
    @SuppressWarnings("unused") private String address = "127.0.0.1";
    @SuppressWarnings("unused") private int port;
    private int nextState;

    public HandshakeServerPacket(DataOutputStream out, int protocolVersion, String address, int port, int nextState) {
        super("Handshake Serverbound", 0x00, out);
        this.protocolVersion = protocolVersion;
        this.address = address;
        this.port = port;
        this.nextState = nextState;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public NextState getNextState() {
        return nextState==1?NextState.Status:
                nextState==2?NextState.Login:
                null;
    }

    public static enum NextState{
        Status, Login
    }

    public void respondString(String message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        ByteBufferWrench.writeVarInt(dataOutputStream, 0x00); //Packet ID: 0x00
        ByteBufferWrench.writeUTF8(dataOutputStream, message);
        ByteBufferWrench.writeVarInt(this.getDataOutputStream(), byteArrayOutputStream.size());
        this.getDataOutputStream().write(byteArrayOutputStream.toByteArray());
        this.getDataOutputStream().flush();
    }
}
