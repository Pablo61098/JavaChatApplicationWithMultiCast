/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;



import Modelo.ChatApplication;
import Modelo.Participante;
import Modelo.Servidor;
import Vista.CrearServidor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo Solano Cordova
 */
public class ControladorCrearServidor implements ActionListener{
    
    CrearServidor view;
    Participante participante;
    ChatApplication chatApplication;
    
    
    
    public ControladorCrearServidor(CrearServidor view, Participante participante,ChatApplication chatApplication){
        this.view = view;
        this.view.jButtonGenerarIPMultiCast.addActionListener(this);
        this.view.jButtonCrearServidor.addActionListener(this);
        this.view.jTextFieldIPMultiCast.addActionListener(this);
        this.participante=participante;
        this.chatApplication = chatApplication;
                
        this.Iniciar();
    }
    
    public void Iniciar(){
        this.view.setVisible(true);
        this.view.setTitle("Crear Servidor");
        this.view.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==this.view.jButtonGenerarIPMultiCast){
            try {
                participante.generateGroup();
            } catch (UnknownHostException ex) {
                Logger.getLogger(ControladorCrearServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.view.jTextFieldIPMultiCast.setText(participante.getGroup().getHostAddress());
        }else if(e.getSource()==this.view.jButtonCrearServidor){
            try {
                if(this.chatApplication.getCtnServidores().agregarServidor(this.view.jTextFieldIPMultiCast.getText())){
                    JOptionPane.showMessageDialog(null,"Se ha creado un servidor con la IPMiltiCast: " + this.view.jTextFieldIPMultiCast.getText());
                }else{
                    JOptionPane.showMessageDialog(null,"Ya existe el servidor con la IPMultiCast: " + this.view.jTextFieldIPMultiCast.getText());
                } 
                //must add the servidor to the chatApplication's contenedor de servidores // Solved: the method is already implemented in the if just above this
            } catch (IOException ex) {
                Logger.getLogger(ControladorCrearServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
}
