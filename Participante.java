package Modelo;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class Participante {


    // Variables for IP MultiCast
    Mensaje mensajeActual;
    InetAddress group;
    ArrayList<Mensaje> msgsDelivered = new ArrayList<Mensaje>();
    ArrayList<Mensaje> msgs_hold_back_queue = new ArrayList<Mensaje>();
    //
    //Request request = new Request(5);
    //
    Integer RIndex = 0;
    //
    //Variables for socket tranference
    boolean request_needed = false;
    InetAddress host;
    Integer UniCastPort;
    Integer MultiCastPort;
    //
    private final String id;
    private final String name;
    
    public Participante(String id, String name) throws IOException {// the password would be the id
        this.id = id;
        this.name = name;
        this.host = InetAddress.getLocalHost();
        /*
        Random rand= new Random();
        UniCastPort = rand.nextInt(( 40000- 1030) + 1) + 1030; 
        */
        this.UniCastPort = 8000; 
        System.out.println("Port: " + UniCastPort );
    }

    public void receiverFromServers(){
        try{
            for(int i=0;i<100000;i++){

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
                    synchronized(this){
                        this.deliver(mensajeActual,multicastSock);
                    }
                }else if(mensajeActual.getId()<=RIndex){
                    System.out.println("Se ignora el mensaje");
                }else if(mensajeActual.getId()>RIndex+1){

                        this.request_needed = true;
                        System.out.println("Se debe ponerlo en la hold back queue");
                        msgs_hold_back_queue.add(mensajeActual);
                        Integer indexEntregar = mensajeActual.getId();

                        Thread threadNuevo1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    standForDelivery(indexEntregar,mensajeActual,multicastSock);
                                } catch (IOException ex) {
                                    Logger.getLogger(Participante.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        Integer indexRequest=RIndex;
                        Thread threadNuevo2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sendRequest(mensajeActual,indexRequest);
                            }
                        });

                        threadNuevo1.start();
                        threadNuevo2.start();

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

    public synchronized void standForDelivery(Integer indexEntregar, Mensaje mensajeActual,MulticastSocket multicastSock) throws IOException {

        while (indexEntregar != this.RIndex + 1) {
            try {
                System.out.println("El Rindex es: " + this.RIndex);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        //After the while it needs to send to Server the message that is about to be delivered.
        System.out.println("-------------------------");
        System.out.println("Se entregara el mesnaje con id: " + mensajeActual.getId());
        this.request_needed = false;
        this.RIndex++;
        System.out.println("RIndex aumento a: " + RIndex);
        notify();
        System.out.println("-------------------------");

    }

    public void sendRequest(Mensaje  mensajeActual, Integer indexRequest){
        //System.out.println("holaaaaa");
        ArrayList<Integer> arrayRequests = new ArrayList<Integer>();
        for (int i = indexRequest + 1; i < mensajeActual.getId(); i++) {
               arrayRequests.add(i);
            //System.out.println("hhh: " + i);
        }
        try {
            Socket socket = new Socket(host.getHostName(), this.UniCastPort);
            System.out.println("Poooooort used: " + socket.getPort());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(arrayRequests);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("holaaaaa 22222222222222");

    }

    public void deliver(Mensaje mensajeActual,MulticastSocket multicastSock) throws IOException {
        System.out.println("-------------------------");
        this.msgsDelivered.add(mensajeActual); // se entrega
        System.out.println("Se ha recibido y almacenado un mensaje");
        System.out.println("Mensaje :" + mensajeActual.getMensaje());

        System.out.println("Se reenviara a todos los agentes del grupo");

        this.sendToServer(mensajeActual, multicastSock);

        RIndex++;
        System.out.println("RIndex aumento a: " + RIndex);
        notify();
        System.out.println("-------------------------");

    }

    public void sendToServer(Mensaje mensajeActual, MulticastSocket multicastSock) throws IOException { //this whole method needs to change, the PARTICICPANT always sends just a String, the server is the one that turns it into a MESSAGE given the actual RIndex of the given GROUP
        byte[] yourBytes = mensajeActual.getInSendingForm(); //turns object to bytes to send
        DatagramPacket packet_out = new DatagramPacket(yourBytes, yourBytes.length, group, 3456);
        multicastSock.send(packet_out);
    }
    


//////////////Setters and Getters
    public void setGroup(InetAddress group) {
        this.group = group;
    }

    public void setHost(InetAddress host) {
        this.host = host;
    }

    public InetAddress getGroup() {
        return group;
    }

    public InetAddress getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return this.id;
    }
    
    public Integer getUniCastPort() {
        return UniCastPort;
    }

    public Integer getMultiCastPort() {
        return MultiCastPort;
    }

    public void setMultiCastPort(Integer MultiCastPort) {
        this.MultiCastPort = MultiCastPort;
    }
    
    public void generateGroup() throws UnknownHostException{ // this method should not return that put that ip to group, the generation needs to be randomic
        this.group=InetAddress.getByName("225.4.5.6");
    }
    
}
