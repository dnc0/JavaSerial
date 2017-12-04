/*
 SerialRxTx v1.0
 R0.1
 */
package jserial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import jserial.Tela;
import static jserial.Tela.lblLeitura;
import static jserial.Tela.lblLeitura2;

/**
 *
 * @author marlon
 */
public class SerialRxTx implements SerialPortEventListener {

    SerialPort serialPort = null;

    //private Protocolo protocolo = new Protocolo();
    public Protocolo protocolo = new Protocolo();
    private String appName = "serial";

    private BufferedReader input;
    private OutputStream output;

    private static final int TIME_OUT = 1000;
    private static int DATA_RATE = 9600;

    private String serialPortName = "/dev/ttyUSB0";
    private static String Results; // Armazena o nome das portas seriais disponiveis

    public String inputLine;
   

    public boolean iniciaSerial() {
        boolean status = false; //Valor de retorno, Indica se a porta foi aberta corretamente
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            while (portId == null && portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (currPortId.getName().equals(serialPortName) || currPortId.getName().startsWith(serialPortName)) {
                    serialPort = (SerialPort) currPortId.open(appName, TIME_OUT);
                    portId = currPortId;
                    System.out.println("Conectado em " + currPortId.getName());
                    break;
                }
            }
            if (portId == null || serialPort == null) {
                //System.out.println("Porta não existe");
                return false;
            }
            serialPort.setSerialPortParams(DATA_RATE, serialPort.DATABITS_8, serialPort.STOPBITS_1, serialPort.PARITY_NONE);
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            status = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                status = false;
            }

        } catch (Exception e) {
        }

        return status;
    }

    public void sendData(String data) {
        try {
            output = serialPort.getOutputStream();
            output.write(data.getBytes());
        } catch (Exception e) {
            System.err.println(e.toString());

        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    //@Override
    public void serialEvent(SerialPortEvent spe) {
        try {
            switch (spe.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE:
                    if (input == null) {
                        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                    }
                    if (input.ready()) {
                        //String inputLine = input.readLine();
                        inputLine = input.readLine();
                        protocolo.interpretaComando(inputLine);
                        System.out.println("chegou: " + inputLine);
                        //leituraSerial(inputLine);
                        //System.out.println("chegou: "+ protocolo.getLeituraComando());
                    }else{
                        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                    }

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("Um erro aconteceu!");

        }

    }

    //get and set
    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }

    public void setPortName(String p) {
        this.serialPortName = p;
    }

    public String gettPortName() {
        return this.serialPortName;
    }

    static void listPorts() {
        Results = " ";
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            //System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
            //System.out.println(portIdentifier.getName() );
            Results += portIdentifier.getName() + ",";
        }
    }

    static String listPortsX() {
        Results = " ";
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            //System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
            //System.out.println(portIdentifier.getName() );
            Results += portIdentifier.getName() + ",";
        }
        return Results;
    }

    static String getPortTypeName(int portType) {
        switch (portType) {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

    public String getPorts() {
        return Results;
    }

    public static void setBaud(int DATA_RATE) {
        SerialRxTx.DATA_RATE = DATA_RATE;
    }
    /*
    public void leituraSerial(String recebido) {
        
        String leitura[] = recebido.split(",");
        
        lblLeitura.setText(leitura[0]);
        lblLeitura2.setText(leitura[1]);
    }
*/

}
