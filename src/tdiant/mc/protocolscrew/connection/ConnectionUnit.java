package tdiant.mc.protocolscrew.connection;

public class ConnectionUnit {
    private String host;
    private int port;
    private State state;
    private long lastRefreshTimeMillis = -1;

    public ConnectionUnit(String host, int port, State state) {
        this.host = host;
        this.port = port;
        this.state = state;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        lastRefreshTimeMillis = System.currentTimeMillis();
    }

    public long getLastRefreshTimeMillis() {
        return lastRefreshTimeMillis;
    }

    public static enum State{
        Handshaking, Login, Status, Play
    }
}
