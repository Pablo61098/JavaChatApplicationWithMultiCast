/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ChatApplication;
import Modelo.ContenedorGrupos;
import Modelo.Participante;
import Vista.AnadirGrupo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo Solano Cordova
 */
public class ControladorAnadirGrupo implements ActionListener{
    
    AnadirGrupo view;
    ChatApplication chatApplication;
    Participante participante;
    String serverIPMultiCast;
    private DefaultTableModel TablaModel;
    ContenedorGrupos ctnGrupos;
    
    public ControladorAnadirGrupo(AnadirGrupo view, ChatApplication chatApplication, Participante participante, String serverIPMultiCast){
        this.view = view;
        this.view.jButtonAñadir.addActionListener(this);
        this.view.jTextFieldNombre.addActionListener(this);
        this.chatApplication=chatApplication;
        this.participante=participante;
        this.serverIPMultiCast=serverIPMultiCast;
        this.view.jButtonRefresh.addActionListener(this);
        this.ctnGrupos = this.chatApplication.getCtnServidores().getContenedorServidores().get(this.serverIPMultiCast).getCtnGrupos();
        this.Iniciar();
        
        MostrarTabla();
        CargarDatosTabla(); 
    }
    
    public void Iniciar(){
        this.view.setVisible(true);
        this.view.setTitle("Añadir Grupo");
        this.view.setLocationRelativeTo(null);
        this.view.jLabelServerIP.setText(this.serverIPMultiCast);
    }
    
    public void MostrarTabla(){
        String data[][]={};
        String col[]={"Grupo","Puerto de Comunicacion","# DE PARTICPANTE"};
        this.TablaModel=new DefaultTableModel(data ,col);
        this.view.jTableGruposEnServidor.setModel(TablaModel);
    }
    
    public void CargarDatosTabla(){
        int cuantasFilas=this.TablaModel.getRowCount();
        
        for(int i=cuantasFilas-1;i>=0;i--){
           this.TablaModel.removeRow(i);
        }

        Iterator it = this.ctnGrupos.getContenedorGrupos().keySet().iterator();
        while(it.hasNext()){
            Integer key =(Integer) it.next();
            this.TablaModel.insertRow(this.TablaModel.getRowCount(), new Object[]{});
            this.TablaModel.setValueAt(this.ctnGrupos.getContenedorGrupos().get(key).getGroupName(), this.TablaModel.getRowCount()-1,0);
            this.TablaModel.setValueAt(String.valueOf(this.ctnGrupos.getContenedorGrupos().get(key).getPort()), this.TablaModel.getRowCount()-1,1);
            this.TablaModel.setValueAt(this.ctnGrupos.getContenedorGrupos().get(key).getCtnParticipantes().getContenedorParticipantes().size(), this.TablaModel.getRowCount()-1,2);
        }   
        System.out.println("Hollaaaaaa 2");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.view.jButtonAñadir){
            if(this.view.jTextFieldNombre.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Ingrese un nombre valido para el Grupo");
            }else{
                try {
                    Integer wasItAccepted = this.chatApplication.getCtnServidores().getContenedorServidores().get(serverIPMultiCast).getCtnGrupos().agregarGrupos(this.view.jTextFieldNombre.getText(), this.participante);
                    if(wasItAccepted==-1){
                        JOptionPane.showMessageDialog(null,"No se pudo crear el grupo ya existe uno en ese mismo puerto");
                    }else{
                        JOptionPane.showMessageDialog(null,"Se ha creado un grupo con el puerto: " + wasItAccepted + " en el servidor: " + this.serverIPMultiCast);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ControladorAnadirGrupo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(e.getSource()==this.view.jButtonRefresh){
            this.CargarDatosTabla();
        }
    }
    
}
