package Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDiscoveryListener extends Thread {
    
    private volatile boolean running = true;
    private MulticastSocket socket;
    private InetSocketAddress socketAddress;
    private NetworkInterface nif;

    private final List<GameInfo> gameInfos = Collections.synchronizedList(new ArrayList<>());

    public List<GameInfo> getGameInfos() {
        return this.gameInfos;
    }

    @Override
    public void run() {
        try {
            socket = new MulticastSocket(5000);
            InetAddress group = InetAddress.getByName("239.255.42.99");
            nif = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            socketAddress = new InetSocketAddress(group, 5000);

            socket.joinGroup(socketAddress, nif);
            socket.setTimeToLive(1);

            byte[] buffer = new byte[256];

            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String receivedMsg = new String(packet.getData(), 0, packet.getLength());

                if (receivedMsg.startsWith("Id")) {
                    String[] parts = receivedMsg.split(",");
                    if (parts.length == 4) {
                        String id = parts[0].split(":")[1];
                        String name = parts[1].split(":")[1];
                        String host = parts[2].split(":")[1];
                        int port = Integer.parseInt(parts[3].split(":")[1]);
                        gameInfos.add(new GameInfo(id, name, host, port));
                    }
                }
            }

        } catch (IOException e) {
            if (running) { // avoid noisy logs when stopped intentionally
                System.out.println("Error listening to Multicast: " + e.getMessage());
            }
        } finally {
            cleanup();
        }
    }

    public void stopListening() {
        running = false;
        cleanup();
    }

    private void cleanup() {
        if (socket != null) {
            try {
                if (socketAddress != null && nif != null) {
                    socket.leaveGroup(socketAddress, nif);
                }
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}