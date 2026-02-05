package Network;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class GameDiscoveryBroadcaster extends Thread {

    private String id;
    private String name;
    private final String host;
    private final int port;

    private volatile boolean running = true;

    public GameDiscoveryBroadcaster(String id, String name, String host, int port) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public void setId(String id){
        this.id = id;
    }

    public void name(String name){
        this.name = name;
    }
    
    @Override
    public void run() {
        
        try (MulticastSocket socket = new MulticastSocket();){
            socket.setTimeToLive(1);
            InetAddress group = InetAddress.getByName("239.255.42.99");

            while (running) {
                String msg = String.format(
                    "Id:%s,Name:%s,Host:%s,Port:%d",
                    id, name, host, port
                );
                byte[] buffer = msg.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 5000);
                socket.send(packet);

                Thread.sleep(2000); // broadcast every 2 seconds
            }
        } catch(IOException | InterruptedException e){
            if (running){
                System.out.println("Error broadcasting game: " + e.getMessage());
            }
            Thread.currentThread().interrupt();
        } 
    }

    public void stopBroadcasting(){
        running = false;
        this.interrupt();
    }
}
