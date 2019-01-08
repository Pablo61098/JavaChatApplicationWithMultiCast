/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.AnadirGrupo;
import Vista.Lobby;
import Vista.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo Solano Cordova
 */
public class ControladorLogin implements ActionListener,MouseListener{
    
    Login view;
    
    public ControladorLogin(Login view){
        this.view = view;
        this.view.jButtonIngresar.addActionListener(this);
        this.view.jTextFieldNombre.addActionListener(this);
        this.view.jTextFieldContrasena.addActionListener(this);
        this.view.jMenuSalir.addMouseListener(this);
        this.Iniciar();
    }
    
    public void Iniciar(){
        this.view.setVisible(true);
        this.view.setTitle("Añadir Grupo");
        this.view.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.view.jButtonIngresar){
            String nombre = this.view.jTextFieldNombre.getText();
            String id = this.view.jTextFieldContrasena.getText();
            Lobby view = new Lobby();
            try {
                ControladorLobby ctrlInicio = new ControladorLobby(view, nombre,id);
            } catch (IOException ex) {
                Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()==this.view.jMenuSalir) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
