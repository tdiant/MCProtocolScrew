package tdiant.mc.protocolscrew.connection;

import tdiant.mc.protocolscrew.ProtocolScrew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectionBoss {
    private ProtocolScrew protocolScrew;
    private Map<String, ArrayList<ConnectionUnit>> connectionUnitList = new HashMap<>();

    public ConnectionBoss(ProtocolScrew protocolScrew) {
        this.protocolScrew = protocolScrew;
    }

    public ConnectionUnit getConnectionUnit(String host, int port){
        if(!connectionUnitList.containsKey(host)) return null;
        for(ConnectionUnit connectionUnit : connectionUnitList.get(host)){
            if(connectionUnit.getHost().equals(host) && connectionUnit.getPort()==port){
                return connectionUnit;
            }
        }
        return null;
    }

    public ConnectionUnit.State getConnectionState(String host, int port){
        radomRemover(); //Remove Dead Connection
        ConnectionUnit cu = getConnectionUnit(host,port);
        return cu==null? ConnectionUnit.State.Handshaking:cu.getState();
    }

    public void pushConnectionState(String host, int port, ConnectionUnit.State state){
        ConnectionUnit cu = getConnectionUnit(host, port);
        if(cu==null){
            if(!connectionUnitList.containsKey(host))
                connectionUnitList.put(host,new ArrayList<>());
            connectionUnitList.get(host).add(new ConnectionUnit(host, port, state));
        }else{
            cu.setState(state);
        }
    }

    private void radomRemover(){
        if((Math.random() * (100-0) + 0)>30) return; // 30% to remove
        for(String host:connectionUnitList.keySet()){
            Iterator<ConnectionUnit> i = connectionUnitList.get(host).iterator();
            while(i.hasNext()){
                ConnectionUnit cu = i.next();
                if((System.currentTimeMillis() - cu.getLastRefreshTimeMillis())>this.protocolScrew.getTimeout()){
                    connectionUnitList.get(host).remove(cu);
                }
            }
        }
    }

    public ProtocolScrew getProtocolScrew() {
        return protocolScrew;
    }
}
