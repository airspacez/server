import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("Ожидание подключения клиента");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключен: " + socket.getInetAddress().getHostAddress());

                try (InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream()) {

                    byte[] buffer = new byte[65536];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        String msg = new String(buffer, 0, bytesRead);
                        System.out.println("СИСТЕМНОЕ СООБЩЕНИЕ: " + msg);
                        outputStream.write(msg.getBytes());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Клиент отключен");
        }
    }
}