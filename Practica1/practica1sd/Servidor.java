package practica1sd;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args){
    ServerSocket servidor = null;
    Socket sc = null;
    DataInputStream in;//Flujo de entrada del cliente al servidor
    DataOutputStream out; //Flujo de salida del servidor al cliente
    final int PUERTO = 5001;
    
    try {
        servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado");
        while(true) {
            sc = servidor.accept();
            System.out.println("Cliente conectado");

            in = new DataInputStream(sc.getInputStream()); 
            out = new DataOutputStream(sc.getOutputStream());

            String mensaje = in.readUTF(); //Lee el mensaje del cliente y lo guarda en la variable mensaje
            System.out.println( mensaje);
            out.writeUTF("Hola mundo desde el servidor");

            //desde el servidor se llama al metodo que realiza la operacion y se envia el resultado al cliente
            int num = in.readInt();
            Circulo c = new Circulo(num);
            out.writeUTF("El area del circulo es: " + c.getArea());
            c.showArea();
                        
            sc.close();
            System.out.println("Cliente desconectado");
        }
    } catch (Exception e) {
       Logger.getLogger(Servidor.class.getCanonicalName()).log(Level.SEVERE, null, e);
         }
    }
}
