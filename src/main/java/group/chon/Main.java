package group.chon;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.ALL);

        if (args.length == 5) {
            ContextNetSender sender = new ContextNetSender();
            sender.setGatewayIP(args[0]);
            sender.setGatewayPort(Integer.parseInt(args[1]));
            sender.setMyUUID(args[2]);
            sender.setDestinationUUID(args[3]);
            sender.sendMessage(args[4]);
        } else if(args.length == 3) {
            ContextNetReceiver receiver = new ContextNetReceiver();
            receiver.setGatewayIP(args[0]);
            receiver.setGatewayPort(Integer.parseInt(args[1]));
            receiver.setMyUUID(args[2]);
            receiver.listen();
        }else{
            System.err.println("Usage:" +
                    "\n Receiver [IP] [PORTA] [UUID-ORIGEM]" +
                    "\n Sender [IP] [PORTA] [UUID-ORIGEM] [UUID-DESTINO] [MSG]");
        }

    }
}
