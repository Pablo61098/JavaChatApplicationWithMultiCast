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
public class ContenedorServidores {
    
    Map<String, Servidor> contenedorServidores = new TreeMap();

    public void setContenedorServidores(Map<String, Servidor> contenedorServidores) {
        this.contenedorServidores = contenedorServidores;
    }

    public Map<String, Servidor> getContenedorServidores() {
        return contenedorServidores;
    }
    
    public boolean agregarServidor(String IPMultiCast) throws IOException{
        if (contenedorServidores.containsKey(IPMultiCast)) {
            return false;
        } else {
            Servidor servidorNuevo = new Servidor(IPMultiCast);
            contenedorServidores.put(IPMultiCast, servidorNuevo);
            return true;
        }
    }
    
}
