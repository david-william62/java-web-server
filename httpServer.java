import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SimpleHTTPServer {

  public static void main(String args[]) throws IOException {

    // Create a ServerSocket listening on port 8080
    ServerSocket server = new ServerSocket(8080);
    System.out.println("Listening for connection on port 8080 ....");

    // Run an infinite loop to keep the server running
    while (true) {
      try (Socket socket = server.accept()) {

        // Read the index.html file using Scanner
        File file = new File("index.html");
        StringBuilder contentBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
          while (scanner.hasNextLine()) {
            contentBuilder.append(scanner.nextLine()).append("\n");
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        // Create HTTP response header and body
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: " + contentBuilder.length() + "\r\n" +
            "\r\n" + contentBuilder.toString();
        // Send the response to the client
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.print(httpResponse);
        out.flush();
      }
    }
  }

}

