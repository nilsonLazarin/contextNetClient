package group.chon;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

public class ContextNetReceiver implements NodeConnectionListener {

    private static String gatewayIP;
    private static int gatewayPort;
    private MrUdpNodeConnection connection;
    private UUID myUUID;

    public ContextNetReceiver() {


    }

    public void listen(){
        InetSocketAddress address = new InetSocketAddress(getGatewayIP(), getGatewayPort());
        try {
            connection = new MrUdpNodeConnection(getMyUUID());
            connection.addNodeConnectionListener(this);
            connection.connect(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String getGatewayIP() {
        return gatewayIP;
    }

    public static void setGatewayIP(String gatewayIP) {
        ContextNetReceiver.gatewayIP = gatewayIP;
    }

    public static int getGatewayPort() {
        return gatewayPort;
    }

    public static void setGatewayPort(int gatewayPort) {
        ContextNetReceiver.gatewayPort = gatewayPort;
    }

    public UUID getMyUUID() {
        return myUUID;
    }

    public void setMyUUID(String strMyUUID) {
        this.myUUID = UUID.fromString(strMyUUID);
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
        System.out.println("Mensagem Recebida: " + message.getContentObject());

        ApplicationMessage appMessage = new ApplicationMessage();
        appMessage.setContentObject("Mensagem de confirmação de recepção no destino!");
        appMessage.setRecipientID(message.getSenderID());

        try {
            connection.sendMessage(appMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {
    }

    public void disconnected(NodeConnection remoteCon) {
    }

    public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {
    }

    public void internalException(NodeConnection remoteCon, Exception e) {
    }

}