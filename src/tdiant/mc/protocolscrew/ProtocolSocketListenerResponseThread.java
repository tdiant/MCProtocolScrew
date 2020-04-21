package tdiant.mc.protocolscrew;

import tdiant.mc.protocolscrew.util.ByteBufferWrench;
import tdiant.mc.protocolscrew.v_1_15_2.packet.ServerPacket;
import tdiant.mc.protocolscrew.v_1_15_2.packet.status.server.StatusServerPingPacket;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ProtocolSocketListenerResponseThread extends Thread {
    private boolean available = false;
    private ProtocolSocketListener listener;

    private Socket socket;
    private String host; //remote client host

    private final DataInputStream in;
    private final DataOutputStream out;

    public ProtocolSocketListenerResponseThread(ProtocolSocketListener listener,Socket socket) throws IOException {
        this.listener = listener;
        if(socket == null) throw new NullPointerException();
        this.socket = socket;
        this.host = socket.getRemoteSocketAddress().toString().substring(1);
        socket.setSoTimeout(listener.getProtocolScrew().getTimeout());

        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.available = true;
    }

    @Override
    public void run() {
        boolean showMotd = false;
        int protocol = 5;

        try {
            while (available && this.socket.isConnected()) {
                final int length   = ByteBufferWrench.readVarInt(this.in);
                final int packetId = ByteBufferWrench.readVarInt(this.in);

                if(length <= 0) return;

                ServerPacket serverPacket = listener.getProtocolScrew().getTriggerBoss().getServerPacket(length,packetId,in, out, socket.getInetAddress().getHostAddress(),socket.getPort());
                this.listener.getProtocolScrew().getTrapperBoss().callPacket(serverPacket);
            }
        }
        //these can be ignored
        catch (EOFException ex1) {} catch (SocketTimeoutException ex2) {}catch (SocketException ex3) {}
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    private final void close() {
        this.available = false;
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
            this.interrupt();
        }catch (Exception ex){}
    }
}
