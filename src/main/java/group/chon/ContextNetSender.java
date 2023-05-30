package group.chon;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;

public class ContextNetSender implements NodeConnectionListener {

    private static String       gatewayIP;
    private static int          gatewayPort;
    private MrUdpNodeConnection connection;
    private UUID                myUUID;

    public UUID getDestinationUUID() {
        return destinationUUID;
    }

    public void setDestinationUUID(String strDestinationUUID) {
        this.destinationUUID = UUID.fromString(strDestinationUUID);
    }

    private UUID                destinationUUID;

    public static String getGatewayIP() {
        return gatewayIP;
    }

    public static void setGatewayIP(String gatewayIP) {
        ContextNetSender.gatewayIP = gatewayIP;
    }

    public static int getGatewayPort() {
        return gatewayPort;
    }

    public static void setGatewayPort(int gatewayPort) {
        ContextNetSender.gatewayPort = gatewayPort;
    }

    public UUID getMyUUID() {
        return myUUID;
    }

    public void setMyUUID(String strMyUUID) {
        this.myUUID = UUID.fromString(strMyUUID);;
    }

    public ContextNetSender() {

    }

    public void sendMessage(String msg) {

        InetSocketAddress address = new InetSocketAddress(getGatewayIP(), getGatewayPort());
        try {
            connection = new MrUdpNodeConnection(getMyUUID());
            connection.addNodeConnectionListener(this);
            connection.connect(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationMessage message = new ApplicationMessage();
        message.setContentObject(msg);
        message.setRecipientID(getDestinationUUID());

        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connected(NodeConnection remoteCon) {
        ApplicationMessage message = new ApplicationMessage();
        message.setContentObject("Registering");

        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newMessageReceived(NodeConnection remoteCon, Message message) {
        System.out.println("Confirmação: " + message.getContentObject());
    }

    public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}

    public void disconnected(NodeConnection remoteCon) {}

    public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}

    public void internalException(NodeConnection remoteCon, Exception e) {}
}
