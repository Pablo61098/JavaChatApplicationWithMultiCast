/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Pablo Solano Cordova
 */
public class ChatApplication {
    
    ContenedorServidores ctnServidores = new ContenedorServidores();

    public ContenedorServidores getCtnServidores() {
        return ctnServidores;
    }

    public void setCtnServidores(ContenedorServidores ctnServidores) {
        this.ctnServidores = ctnServidores;
    }
    
    public ChatApplication(){
        
    }
    
}
