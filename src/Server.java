import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 1234;
    public static final int MAX_THREADS = 4;

    private static class ClientHandler implements Runnable{
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Stream error");
                return;
            }


        }
    }

    public static void main() {
        ServerSocket serverSocket;
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Server started on " + PORT);

        } catch (IOException e) {
            System.out.println("Server error");
            return;
        }

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected client: " + clientSocket.getInetAddress().getHostName());
            executor.execute(new ClientHandler(clientSocket));
        }
    }
}
