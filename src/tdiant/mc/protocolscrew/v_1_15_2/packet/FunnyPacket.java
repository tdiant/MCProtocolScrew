package tdiant.mc.protocolscrew.v_1_15_2.packet;

import java.io.DataOutputStream;

public class FunnyPacket extends ServerPacket {

    public FunnyPacket(DataOutputStream out) {
        super("Just For Fun", -1, out);
    }

    public void relax(){
        System.out.println("Happy~~");
    }
}
