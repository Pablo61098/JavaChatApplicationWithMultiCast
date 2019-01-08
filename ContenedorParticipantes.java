/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Pablo Solano Cordova
 */
public class ContenedorParticipantes {
    
    Map<String, Participante> contenedorParticipantes = new TreeMap();

    public void setContenedorServidores(Map<String, Participante> contenedorParticipantes) {
        this.contenedorParticipantes = contenedorParticipantes;
    }

    public Map<String, Participante> getContenedorParticipantes() {
        return contenedorParticipantes;
    }
    
    public boolean agregarParticipante(String name, String id) throws IOException{
        if (contenedorParticipantes.containsKey(name)) {
            return false;
        } else {
            Participante participanteNuevo = new Participante(name,id);
            contenedorParticipantes.put(name, participanteNuevo);
            return true;
        }
    }
    
}
