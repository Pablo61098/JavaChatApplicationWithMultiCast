import java.util.ArrayList;

public class Request extends Thread  {

    private boolean receivedStatus = false;
    public ArrayList<Integer> listaprueba;
    public Integer numero ;

    public Request(Integer numero){
        this.numero = numero;

    }

    public void run(){
        for(int i=0;i<numero;i++){
            System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
