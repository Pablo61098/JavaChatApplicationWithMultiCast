package Modelo;

import java.io.IOException;

public class Grupo {
    
    ContenedorParticipantes ctnParticipantes = new ContenedorParticipantes();
    String groupName;
    Integer port;
    Participante fundador;

    public Grupo(String groupName, Participante participante) throws IOException{
        this.groupName = groupName;
        /*
        Random rand= new Random();
        port = rand.nextInt(( 40000- 1030) + 1) + 1030; 
        */
        this.port=3456;
        this.fundador=participante;
        System.out.println("Port number of Group: "+ this.port);
      
        anadirAGrupo(participante);
        
    }
    
    public void anadirAGrupo(Participante participante) throws IOException{
        this.ctnParticipantes.agregarParticipante(participante.getId(), participante.getName());
    }
    
    public ContenedorParticipantes getCtnParticipantes() {
        return ctnParticipantes;
    }

    public String getGroupName() {
        return groupName;
    }

    public Integer getPort() {
        return port;
    }

    public Participante getFundador() {
        return fundador;
    }

    public void setCtnParticipantes(ContenedorParticipantes ctnParticipantes) {
        this.ctnParticipantes = ctnParticipantes;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setFundador(Participante fundador) {
        this.fundador = fundador;
    }

    
}
