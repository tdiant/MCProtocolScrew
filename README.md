# MCProtocolScrew

Make Fake the Protocol of Minecraft Java Edition Server More Suck and Stupid!

MCProtocolScrew is an open-source toolkit for developing faking Minecraft Java Edition Server with a stupid way. A suck and worst bug-filling framework is provided for you to build projects insanely.

UNDER DEVELOPMENT NOW!

# Get Start

First, include MCProtocolScrew to your project with Maven or Gradle.

```maven
<error>
   <msg>Not support now.</msg>
</error>
```

Second, create a listen-thread for `ServerSocket` on your host-ip and port, let the service on.

```java
ProtocolScrew protocolScrew= new ProtocolScrew("127.0.0.1",25565,5000); //5000 is the timeout threshold.

new Thread(() -> {
    try {
        protocolScrew.listenStart();
    } catch (IOException e) {
        e.printStackTrace();
    }
}).start();
```

Third, make your Trapper to "TRAP THE PACKET". 

```java
class DemoTrapper implements Trapper {
    @TrapperListener(packetType = HandshakeServerPacket.class)
    public void onHandshake(ServerPacket p){
        System.out.println("Handshake!!");
        HandshakeServerPacket packet = (HandshakeServerPacket) p;
        protocol = packet.getProtocolVersion();

        //If players' clients is trying to login the server, a message "HelloWorld!" will be sent to them.
        if(packet.getNextState()==HandshakeServerPacket.NextState.Login) {
            try {
                System.out.println("Handshake Login Attempt!!");
                packet.respondString("{text:\"HelloWorld!\", color: white}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

then, register the trapper.

```java
protocolScrew.registerTrapper(new DemoTrapper());
```

Done!  
(so SUCK, am i right huh?

# Let MCProtocolScrew Support More PacketTypes

First, add a Class extends `ServerPacket`.

```java
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
}
```

Second, create a Trigger for this `PacketID`. Every `PacketID` needs a Trigger to pair.

```java
public class T_0X01 extends CorePacketUnit {
    public T_0X01(ProtocolScrew protocolScrew) {
        super(protocolScrew);
    }

    @TriggerTag(packetId = 0x01)
    public ServerPacket triggerPacket(int length, DataInputStream in, DataOutputStream out, String host, int port) {
        try {
            long playload = in.readLong();
            StatusServerPingPacket packet = new StatusServerPingPacket(out,playload);
            return packet;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
```

*CorePacketUnit is a default implements for Trigger, which aims to create trigger more quicker.*

The most important is, update the Connection State after receiving the packet from clients, if it is needed.

```java
this.getProtocolScrew().pushConnectionState(host, port, NEW_STATE);
```

A Connection is the combination of the remote host-ip and its port. The state's default value is `Handshaking`.

Third, register your trigger.

```java
//BE CAREFUL: DO NOT REGISTER THE SAME TRIGGER TWICE!
protocolScrew.getTriggerBoss().registerTrigger(YOUR_TRIGGER_OBJECT);
```

Done!

# Demos

## A fake MOTD showing server

Create a server which is only to show MOTD message to players' clients.

Source: `MOTDFakingServerDemo.java`

