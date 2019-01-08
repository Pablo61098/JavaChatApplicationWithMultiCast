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
public class ContenedorGrupos {
    
    Map<Integer, Grupo> contenedorGrupos = new TreeMap();

    public void setContenedorGrupos(Map<Integer, Grupo> contenedorGrupos) {
        this.contenedorGrupos = contenedorGrupos;
    }

    public Map<Integer, Grupo> getContenedorGrupos() {
        return contenedorGrupos;
    }
    
    public Integer agregarGrupos(String name, Participante participante) throws IOException{
        Grupo grupoNuevo = new Grupo(name,participante);
        if (contenedorGrupos.containsKey(grupoNuevo.getPort())) {
            return -1;            
        } else {
            contenedorGrupos.put(grupoNuevo.getPort(), grupoNuevo);
            System.out.println("si se anadio el grupo");
            return grupoNuevo.getPort();
        }
    }
    
    
    
}
