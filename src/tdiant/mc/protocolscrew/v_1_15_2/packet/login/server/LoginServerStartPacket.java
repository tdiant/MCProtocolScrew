package tdiant.mc.protocolscrew.v_1_15_2.packet.login.server;

import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;

import java.io.DataOutputStream;

public class LoginServerStartPacket extends ServerPacket {
    private String playerName;

    public LoginServerStartPacket(DataOutputStream out, String playerName) {
        super("Login Serverbound Start", 0x00, out);
        this.playerName = playerName;
    }
}
