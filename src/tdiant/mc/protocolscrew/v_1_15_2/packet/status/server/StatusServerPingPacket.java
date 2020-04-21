package tdiant.mc.protocolscrew.v_1_15_2.packet.status.server;

import tdiant.mc.protocolscrew.util.ByteBufferWrench;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.io.DataOutputStream;
import java.io.IOException;

public class StatusServerPingPacket extends ServerPacket {
    private long playload;

    public StatusServerPingPacket(DataOutputStream out, long playload) {
        super("Status Serverbound Ping", 0x01, out);
        this.playload = playload;
    }

    public long getPlayload() {
        return playload;
    }

    public void setPlayload(long playload) {
        this.playload = playload;
    }

    public void returnPlayload() throws IOException {
        //ByteBufferWrench.writeVarInt(this.getDataOutputStream(),0x09);
        //ByteBufferWrench.writeVarInt(this.getDataOutputStream(),0x01);
        this.getDataOutputStream().writeByte(0x09);
        this.getDataOutputStream().writeByte(0x01);
        this.getDataOutputStream().writeLong(playload);
        //ByteBufferWrench.writeVarLong(this.getDataOutputStream(),playload);
        this.getDataOutputStream().flush();
    }
}
