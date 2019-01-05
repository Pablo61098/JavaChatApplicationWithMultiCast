import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

class MultiCastReceiver {


/*
    public static void main(String[] args){

        ArrayList<Mensaje> msgsDelivered = new ArrayList<Mensaje>();
        ArrayList<Mensaje> msgs_hold_back_queue = new ArrayList<Mensaje>();
        ArrayList<Integer> listaprueba = new ArrayList<Integer>();
        Request request = new Request(5);
        Integer RIndex = 0;

        try{
            InetAddress group = InetAddress.getByName("225.4.5.6");

            while(true){

                // The message is received
                MulticastSocket multicastSock = new MulticastSocket(3456);
                multicastSock.joinGroup(group);
                byte[] buffer = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSock.receive(packet);

                //String msg=new String(buffer);

                //this set of code turns a incoming set of bytes to its corresponding object, **Check wether you have to set o to Object instead
                //of Mensaje as by now I don't know  how the application will be tested
                Mensaje mensajeActual = null;
                ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
                ObjectInput in = null;
                try {
                    in = new ObjectInputStream(bis);
                    mensajeActual = (Mensaje) in.readObject();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        // ignore close exception
                    }
                }
                //

                int flag= 0;
                // Se ve si se puede entregar
                for(Mensaje e: msgsDelivered){
                    if(mensajeActual.getId()==(e.getId())){
                        flag=1;
                        System.out.println("Ya se ha recibido este mensaje");
                    }
                }

                // Si se puede recibir se entrega
                if(flag==0){
                    // Check if even if it is not delivered there should be a received form of storage
                        if(mensajeActual.getId()==RIndex+1){
                            msgsDelivered.add(mensajeActual); // se entrega
                            System.out.println("Se ha recibido y almacenado un mensaje");
                            System.out.println(mensajeActual.getMensaje());

                            System.out.println("Se reenviara a todos los agentes del grupo");

                            byte[] yourBytes = mensajeActual.getInSendingForm(); //turns object to bytes to send

                            DatagramPacket packet_out = new DatagramPacket(yourBytes,yourBytes.length, group, 3456);
                            multicastSock.send(packet_out);

                            RIndex++;

                        }else if(mensajeActual.getId()<=RIndex){
                            System.out.println("Se ignora el mensaje");
                        }else if(mensajeActual.getId()>RIndex+1){
                            System.out.println("Se debe ponerlo en la hold back queue");
                            msgs_hold_back_queue.add(mensajeActual);
                            request.start();
                            Thread.sleep(6000);
                            request.numero=15;
                            //ask if when meesages get requested, the ones that got them should send directly to the ones in need or to everyone. Solved: it should request just to the server not to others participants.
                        }

                    // Aqui se deberia de manejar el caso de que el que esta mandando sea el mismo proceso del que acaba de recibir
                    // pero dado a que el metodo send solo manda un DatagramPacket se me hace imposible poder mandar algun tipo de id
                    // del proceso que esta enviando y asi manejar este caso --------->Se logro manejar este problema


                }

                multicastSock.close();

            }
        }catch(Exception e ){
            e.printStackTrace();
        }

    }
*/
}
