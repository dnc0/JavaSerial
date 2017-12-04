/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jserial;

import static jserial.Tela.lblLeitura;
import static jserial.Tela.lblLeitura2;
import jserial.Tela.*;

/**
 *
 * @author marlon
 */
public class Protocolo {
    private String valor1;
    private String valor2;
    public void interpretaComando(String recebido){
        String leitura[] = recebido.split(",");
        if(leitura.length == 2){
            valor1 = leitura[0];
            valor2 = leitura[1];
            lblLeitura.setText(leitura[0]);
            lblLeitura2.setText(leitura[1]);
        }else if (leitura.length >2){
            System.out.println("Muitos comandos!");
        }else if (leitura.length <2){
            System.out.println("Falta comandos!");
        }
    }
    
    //gets and sett

    public String getValor1() {
        return valor1;
    }

    public String getValor2() {
        return valor2;
    }
    
    
    public Protocolo getObjeto(){
        return this;
    }
    
    
}
