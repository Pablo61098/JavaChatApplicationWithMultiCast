/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ChatApplication;
import Modelo.ContenedorServidores;
import Modelo.Participante;
import Vista.AnadirGrupo;
import Vista.CrearServidor;
import Vista.Lobby;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo Solano Cordova
 */
public class ControladorLobby implements ActionListener, MouseListener{
    
    Lobby view;
    Participante participante;
    ChatApplication chatApplication; 
    private DefaultTableModel TablaModel;
    ContenedorServidores ctnServidores;
    Integer numeroDeGruposEnServidor;
    String IPMultiCastServidor;
    
    public ControladorLobby(Lobby view, String nombre, String id) throws IOException{
        
        this.view = view;
        this.view.jButtonIngresarAGrupo.addActionListener(this);
        this.view.jButtonAñadirGrupo.addActionListener(this);
        this.view.jButtonCrearServidor.addActionListener(this);
        this.participante = new Participante(nombre, id);
        this.view.jButtonRefresh.addActionListener(this);
        this.chatApplication = new ChatApplication(); //this line must change to load all the information into this variable
        
        this.view.jTableServidores.addMouseListener(this);
        
        ctnServidores=this.chatApplication.getCtnServidores();
        
        this.iniciar();
        MostrarTabla();
        estadoIncialBotones();
        /* Thread to always keep on checking for active servers, it was turn off because this.view.isAlive() didn't work
        Thread nuevoThread = new Thread( new Runnable(){
            public void run(){
                try { 
                    CargarDatosTabla();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorLobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        nuevoThread.start();
        */
    }
    
    public void MostrarTabla(){
        String data[][]={};
        
        String col[]={"SERVIDORES","# DE GRUPOS"};
        this.TablaModel=new DefaultTableModel(data ,col);
        this.view.jTableServidores.setModel(TablaModel);
        
    }
    
    public void CargarDatosTabla() throws InterruptedException{
         ///////////// Tabla solo se actualiza 10 veces cada tres segundos debe ser un while infinito 
            int cuantasFilas=this.TablaModel.getRowCount();

            for(int i=cuantasFilas-1;i>=0;i--){
               this.TablaModel.removeRow(i);
            }

            Iterator it = this.ctnServidores.getContenedorServidores().keySet().iterator();
            while(it.hasNext()){
                String key =(String) it.next();
                System.out.println("Key: " + key);
                this.TablaModel.insertRow(this.TablaModel.getRowCount(), new Object[]{});
                this.TablaModel.setValueAt(key, this.TablaModel.getRowCount()-1,0);
                this.TablaModel.setValueAt(this.chatApplication.getCtnServidores().getContenedorServidores().get(key).getCtnGrupos().getContenedorGrupos().size() , this.TablaModel.getRowCount()-1,1);
            }
            System.out.println("Holaaaa");
    }
    
    public void iniciar(){
        this.view.setVisible(true);
        this.view.setTitle("Servidor");
        this.view.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.view.jButtonAñadirGrupo){
            AnadirGrupo anadirGrupo = new AnadirGrupo();
            ControladorAnadirGrupo ctrlAnadirGrupo = new ControladorAnadirGrupo(anadirGrupo,chatApplication,participante, IPMultiCastServidor);
            
        }else if(e.getSource()==this.view.jButtonCrearServidor){
            CrearServidor crearServidor = new CrearServidor();
            ControladorCrearServidor ctrlCrearServidor = new ControladorCrearServidor(crearServidor,this.participante, this.chatApplication);
        }else if(e.getSource()==this.view.jButtonIngresarAGrupo){
            
        }else if(e.getSource()==this.view.jButtonRefresh){
            try {
                this.CargarDatosTabla();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorLobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public void CargarContenedorServidores(){
        //En este metodo se inicializa la aplicacion con el contenedor de servidores (en el caso de que haya) o 
        //se inicia con uno nuevo si no ha habido ninguno
        
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getSource()==this.view.jTableServidores){
            String nombreCancha= String.valueOf(this.view.jTableServidores.getValueAt(this.view.jTableServidores.getSelectedRow(), 0));
            
            this.IPMultiCastServidor=String.valueOf(this.view.jTableServidores.getValueAt(this.view.jTableServidores.getSelectedRow(), 0));
            this.numeroDeGruposEnServidor=Integer.valueOf(String.valueOf(this.view.jTableServidores.getValueAt(this.view.jTableServidores.getSelectedRow(), 1)));
            
            this.view.jButtonAñadirGrupo.setEnabled(true);
            if(numeroDeGruposEnServidor >0){
                this.view.jButtonIngresarAGrupo.setEnabled(true);
            }
            
        }
    }
    
    public void estadoIncialBotones(){
        this.view.jButtonAñadirGrupo.setEnabled(false);
        this.view.jButtonIngresarAGrupo.setEnabled(false);
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
