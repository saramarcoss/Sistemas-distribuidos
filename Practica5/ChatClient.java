import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            try (Socket socket = new Socket("localhost", 12345)) {
                System.out.println("Conectado al servidor");

                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();

                try (Scanner scanner = new Scanner(System.in)) {
                    Thread receiveThread = new Thread(() -> {
                        try {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = input.read(buffer)) != -1) {
                                String message = new String(buffer, 0, bytesRead);
                                System.out.println(message);
                            }
                        } catch (IOException e) {
                            System.out.println("El servidor se ha desconectado");
                            System.exit(0);
                        }
                    });
                    receiveThread.start();

                    System.out.print("Ingresa tu nombre de usuario: ");
                    String username = scanner.nextLine();

                    // Enviar el nombre de usuario al servidor
                    output.write(username.getBytes());

                    System.out.print("Escribe tus mensajes: \n");

                    while (true) {
                        String message = scanner.nextLine();

                        // Enviar mensaje al servidor
                        output.write(message.getBytes());

                        // Verificar si el cliente desea abandonar el chat
                        if (message.equalsIgnoreCase("/salir")) {
                            System.out.println("Abandonaste el chat");
                            System.exit(0);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor");
        }
    }
}
