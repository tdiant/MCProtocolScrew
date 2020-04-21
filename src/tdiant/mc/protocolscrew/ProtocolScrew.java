package tdiant.mc.protocolscrew;

import tdiant.mc.protocolscrew.connection.ConnectionBoss;
import tdiant.mc.protocolscrew.connection.ConnectionUnit;
import tdiant.mc.protocolscrew.core.trigger.Trigger;
import tdiant.mc.protocolscrew.core.trigger.TriggerBoss;
import tdiant.mc.protocolscrew.core.trigger.type.T_0X00;
import tdiant.mc.protocolscrew.trapper.Trapper;
import tdiant.mc.protocolscrew.trapper.TrapperBoss;

import java.io.IOException;
import java.net.ServerSocket;

public class ProtocolScrew {
    private int timeout;
    private ProtocolSocketListener socketListener;
    private String host;
    private int port;

    private TriggerBoss triggerBoss; //Trigger
    private TrapperBoss trapperBoss; //Trapper

    private ConnectionBoss connectionBoss; //Connections

    public ProtocolScrew(String host, int port) throws IOException {
        this(host,port,5000);
    }

    public ProtocolScrew(String host, int port, int timeout) throws IOException {
        this.timeout = timeout<0||timeout>10*1000?5000:timeout;

        this.triggerBoss = new TriggerBoss(this);
        this.trapperBoss = new TrapperBoss(this);
        this.connectionBoss = new ConnectionBoss(this);
        this.triggerBoss.registerDefaultTriggers();

        this.socketListener = new ProtocolSocketListener(this);
        this.host = host;
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public ProtocolSocketListener getSocketListener() {
        return socketListener;
    }

    public boolean isAvailable(){
        return socketListener.isAvailable();
    }

    public TriggerBoss getTriggerBoss() {
        return triggerBoss;
    }

    public TrapperBoss getTrapperBoss() {
        return trapperBoss;
    }

    public void registerTrapper(Trapper trapper){
        this.getTrapperBoss().registerTrapper(trapper);
    }

    public ConnectionUnit.State getConnectionState(String host, int port){
        return this.connectionBoss.getConnectionState(host, port);
    }

    public void pushConnectionState(String host, int port, ConnectionUnit.State state){
        this.connectionBoss.pushConnectionState(host, port, state);
    }

    public void listenStart() throws IOException {
        this.socketListener.listenStart(host,port);
    }
}
