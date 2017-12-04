/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jserial;

import static jserial.Tela.cboPortas;


/**
 *
 * @author marlon
 */
public class Jserial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //mostra as portas disponiveis quando iniciar o programa
        String aux[] = SerialRxTx.listPortsX().split(",");
        //System.out.println(SerialRxTx.listPortsX());
        Tela tela = new Tela();
        tela.setVisible(true);
        for(int x=0;x<aux.length;x++){
            cboPortas.addItem(aux[x].replaceAll(" ",""));
        }   
    }
    
    
    
}
