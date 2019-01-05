import java.io.*;

public class Mensaje implements Serializable {

    public String mensaje;
    public int id;


    public Mensaje( String mensaje, int id){
        this.mensaje=mensaje;
        this.id=id;
    }

    public String getMensaje(){
        return this.mensaje;
    }

    public String toString(){
        return this.mensaje;
    }

    public int getId(){
        return this.id;
    }

    public byte[] getInSendingForm(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] yourBytes =null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            yourBytes = bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return yourBytes;
    }
}