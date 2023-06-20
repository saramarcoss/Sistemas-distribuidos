import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(12345)) {
                System.out.println("Servidor iniciado. Esperando conexiones...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Nuevo cliente conectado: " + socket.getInetAddress().getHostAddress());

                    ClientHandler clientHandler = new ClientHandler(socket);
                    clients.add(clientHandler);
                    clientHandler.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private InputStream input;
        private OutputStream output;
        private String username;
        private boolean connected = true;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // Solicitar nombre de usuario solo una vez
                requestUsername();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    String message = new String(buffer, 0, bytesRead);
                    System.out.println(username + " dice: " + message);

                    // Enviar mensaje a todos los clientes conectados
                    broadcastMessage(username + " dice: " + message);

                    // Verificar si el cliente desea abandonar el chat
                    if (message.trim().equalsIgnoreCase("/salir")) {
                        connected = false;
                        break;
                    }
                }

                // Cliente desconectado
                System.out.println(username + " se ha desconectado");

                // Eliminar el cliente de la lista de clientes
                clients.remove(this);

                // Notificar a los demás clientes sobre la desconexión
                broadcastMessage(username + " se ha desconectado");

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void requestUsername() throws IOException {

            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            username = new String(buffer, 0, bytesRead).trim();

            System.out.println("Nuevo usuario: " + username);
            broadcastMessage(username + " se ha unido al chat");
        }

        private void broadcastMessage(String message) throws IOException {
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.output.write(message.getBytes());
                }
            }
        }
    }
}

