//MultiCast is to send a message to a group of people

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiCastSender {

    public static void main(String[] args) {

        try {
            InetAddress group = InetAddress.getByName("225.4.5.6");

            MulticastSocket multicastSock = new MulticastSocket();

            Mensaje msg =  new Mensaje("Hola desde el Sender, como estas ?", 9);

            //byte[] yourBytes = (byte[]) msg.getInSendingForm();

            System.out.println(msg);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            byte[] yourBytes =null;

            try {
                out = new ObjectOutputStream(bos);
                System.out.println("holaa 1");
                out.writeObject(msg);
                System.out.println("holaa 2");
                out.flush();
                System.out.println("holaa 3");
                yourBytes = bos.toByteArray();
                System.out.println("holaa 4");
            }finally {
                try {
                    bos.close();
                } catch (IOException ex) {
                    // ignore close exception
                }
            }


            DatagramPacket packet = new DatagramPacket(yourBytes, yourBytes.length, group, 3456);

            multicastSock.send(packet);

            multicastSock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
