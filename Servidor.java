import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Servidor {


    // Variables for IP MultiCast
    Mensaje mensajeActual = null;
    InetAddress group = InetAddress.getByName("225.4.5.6");
    ArrayList<Mensaje> msgsDelivered = new ArrayList<Mensaje>();
    ArrayList<Mensaje> msgs_hold_back_queue = new ArrayList<Mensaje>();
    ArrayList<Integer> listaprueba = new ArrayList<Integer>();
    ArrayList<Mensaje> msgInDataBase = new ArrayList<Mensaje>();
    //
    //Request request = new Request(5);
    //
    Integer RIndex = 0;
    //
    //Variables for socket tranference
    boolean request_needed = false;
    ServerSocket serverSocketCommunication; //For Messages
    ServerSocket serverSocketRequest; //For Requests
    //

    public Servidor() throws IOException {
        Mensaje msg = new Mensaje("helloooo mensaje", 1);
        Mensaje msg2 = new Mensaje("helloooo mensaje 2 ", 2);
        Mensaje msg3 = new Mensaje("helloooo mensaje", 3);
        Mensaje msg4 = new Mensaje("helloooo mensaje 2 ", 4);
        Mensaje msg5= new Mensaje("helloooo mensaje", 5);
        Mensaje msg6 = new Mensaje("helloooo mensaje 2 ", 6);
        Mensaje msg7= new Mensaje("helloooo mensaje", 7);
        Mensaje msg8 = new Mensaje("helloooo mensaje 2 ", 8);

        msgInDataBase.add(msg);
        msgInDataBase.add(msg2);
        msgInDataBase.add(msg3);
        msgInDataBase.add(msg4);
        msgInDataBase.add(msg5);
        msgInDataBase.add(msg6);
        msgInDataBase.add(msg7);
        msgInDataBase.add(msg8);


        Thread nuevaThread = new Thread(new Runnable(){
            public void run(){
                try {
                    receiveFromGroupRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        nuevaThread.start();
    }


    public void sendToServer(){

    }

    public void sendToGroup(byte[] yourBytes){
        try {
            InetAddress group = InetAddress.getByName("225.4.5.6");
            MulticastSocket multicastSock = new MulticastSocket();

            //byte[] yourBytes = (byte[]) msg.getInSendingForm();

            DatagramPacket packet = new DatagramPacket(yourBytes, yourBytes.length, group, 3456);

            multicastSock.send(packet);
            multicastSock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveFromGroup() throws IOException, ClassNotFoundException, InterruptedException {
        for(int i=0;i<100;i++){
            this.serverSocketCommunication= new ServerSocket(8001);
            System.out.println("El Servidor se esta ejecutando -- Esperando respuesta...\n");
            Socket socketCommunication = serverSocketCommunication.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socketCommunication.getInputStream());
            Mensaje mensaje = (Mensaje) objectInputStream.readObject();
            socketCommunication.close();
            mensaje.setId(mensaje.getId()+1);
            byte[] yourbytes= mensaje.getInSendingForm();
            this.sendToGroup(yourbytes);
            Thread.sleep(1000);
        }
    }

    public void receiveFromGroupRequest() throws IOException, ClassNotFoundException, InterruptedException {
        this.serverSocketRequest= new ServerSocket(8001);
        System.out.println("Poooooort used wawa: " + serverSocketRequest.getLocalPort());
        for(int p=0;p<2;p++){
            System.out.println("El Servidor se esta ejecutando -- Esperando respuesta...\n");
            Socket socketRequest = serverSocketRequest.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socketRequest.getInputStream());
            ArrayList acks = (ArrayList) objectInputStream.readObject();
            socketRequest.close();
            System.out.println("acks size: " + acks.size());
            //System.out.println("holaaaaa 3333333");
            for(int i = 0; i<acks.size();i++){
                //System.out.println("holaaaaa 5");
                for(int j = 0;j<this.msgInDataBase.size();j++){
                    //System.out.println("holaaaaa 6");
                    if((msgInDataBase.get(j).getId()).equals(acks.get(i))){
                        //System.out.println("holaaaaa 7");
                        System.out.println(msgInDataBase.get(j));
                        Thread.sleep(2000); //If you take this sleep out the, just one message is send out
                        this.sendToGroup(msgInDataBase.get(j).getInSendingForm());
                    }
                }
            }
            //System.out.println("holaaaaa 4444444444444");
            //Thread.sleep(1000);
       }
    }

    public void receiverFromServer(){
        /*

        try{


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

                // Si se puede entregar se califica segun su id y segun RIndex
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
                        this.request_needed = true;
                        System.out.println("Se debe ponerlo en la hold back queue");
                        msgs_hold_back_queue.add(mensajeActual);

                        Thread threadNuevo1 = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                synchronized(this){
                                    while(RIndex!=RIndex+1){
                                        try {
                                            System.out.println("El Rindex es: "  +  RIndex);
                                            wait();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.println("Se entregara el mesnaje con id: " + mensajeActual.getId());
                                    request_needed=false;
                                }
                            }
                        });

                        Thread threadNuevo2 = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                synchronized(this) {
                                    for (int i = RIndex + 1; i <= mensajeActual.getId(); i++) {
                                        try {
                                            Socket socket = new Socket(host.getHostName(), 8000);
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(i);
                                            socket.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        notify();
                                    }
                                }
                            }
                        });


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

        */
    }




}
