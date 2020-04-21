package tdiant.mc.protocolscrew;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProtocolSocketListener {
    public static final int BACKLOG = 5;
    private boolean available = false;

    private ProtocolScrew protocolScrew;
    private ServerSocket serverSocket;


    private ProtocolSocketListener(){}

    public ProtocolSocketListener(ProtocolScrew protocolScrew) {
        this.protocolScrew = protocolScrew;
    }

    public void listenStart(String host, int port) throws IOException {
        host = host==null || host.isEmpty() ? "*":host;
        port = port<0 ? 25566 : port;
        serverSocket = new ServerSocket(port, BACKLOG, host.equals("*")?null:InetAddress.getByName(host));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
        available = true;

        while(!serverSocket.isClosed()) {
            new ProtocolSocketListenerResponseThread(this,serverSocket.accept()).start();
        }
    }

    public ProtocolScrew getProtocolScrew() {
        return protocolScrew;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isAvailable() {
        return available;
    }

    public void stop(){
        try {
            serverSocket.close();
            available = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
