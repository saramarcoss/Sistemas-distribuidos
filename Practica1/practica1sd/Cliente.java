package practica1sd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args){
        final String HOST = "127.0.0.1";
        final int PUERTO = 5001;
        DataInputStream in; //Flujo de entrada del servidor al cliente
        DataOutputStream out; //Flujo de salida del cliente al servidor
        try {
            Socket sc = new Socket(HOST, PUERTO); 
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            out.writeUTF("Hola mundo desde el cliente"); //Envia el mensaje al servidor
            out.writeInt(3); //Envia el mensaje al servidor

            //desde el cliente se realiza la peticion al servidor y se imprime el mensaje que se recibe
            String mensaje = in.readUTF(); 
            System.out.println(mensaje);
            sc.close();

        } catch (Exception e) {
            Logger.getLogger(Servidor.class.getCanonicalName()).log(Level.SEVERE, null, e);
        }
    }
}
