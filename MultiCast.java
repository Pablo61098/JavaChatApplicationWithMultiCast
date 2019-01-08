/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast;

import Controlador.ControladorLobby;
import Controlador.ControladorLogin;
import Vista.Lobby;
import Vista.Login;

/**
 *
 * @author Pablo Solano Cordova
 */
public class MultiCast {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Login view = new Login();
        ControladorLogin ctrlLogin = new ControladorLogin(view);
        
    }
    
}
