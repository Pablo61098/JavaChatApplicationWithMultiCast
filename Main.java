import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Number of active threads from the given thread: " + Thread.activeCount());



        Participante participante = new Participante("Pablo", "010550");
        //Participante participante2 = new Participante("Jose", "010550");

        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                participante.receiverFromGroup();
                //participante2.receiverFromGroup();
            }
        });

        /*
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                servidor.sendToGroup(yourBytes);

            }
        });
        */
        t1.start();

        Servidor servidor = new Servidor();
        Mensaje msg = new Mensaje("hellllasdasd 213", 3);
        byte[] yourBytes = msg.getInSendingForm();
        servidor.sendToGroup(yourBytes);
        Thread.sleep(10000);

        //t2.start();




    }
}
