import java.io.*;
import java.net.*;

public class _10_ {
    //Simple Mail Transfer (SMTP) Client
    public static void main(String[] args) {
        if (args.length == 0) {
            printPreview();
            return;
        }

        if (args.length != 4) {
            System.out.println("Usage: java _10_ <smtp-server> <port> <sender> <recipient>");
            System.out.println("Example: java _10_ localhost 2525 sender@example.com recipient@example.com");
            return;
        }

        String smtpServer = args[0];
        String sender = args[2];
        String recipient = args[3];

        try {
            int port = Integer.parseInt(args[1]);

            try (Socket socket = new Socket(smtpServer, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8")) {

            System.out.println("S: " + reader.readLine());

            sendCommand(writer, reader, "HELO localhost");
            sendCommand(writer, reader, "MAIL FROM:<" + sender + ">");
            sendCommand(writer, reader, "RCPT TO:<" + recipient + ">");
            sendCommand(writer, reader, "DATA");
            
            // Send email body
            writer.write("Subject: Test Mail\r\n\r\nThis is a test message from raw Java SMTP Client.\r\n.\r\n");
            writer.flush();
            System.out.println("S: " + reader.readLine());

            sendCommand(writer, reader, "QUIT");

            }
        } catch (IOException e) {
            System.err.println("SMTP Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Port must be a number.");
        }
    }

    private static void printPreview() {
        System.out.println("SMTP preview only; no server connection was attempted.");
        System.out.println("To send a message, provide: <smtp-server> <port> <sender> <recipient>");
        System.out.println("Example: java _10_ localhost 2525 sender@example.com recipient@example.com");
        System.out.println();
        System.out.println("Subject: Test Mail");
        System.out.println();
        System.out.println("This is a test message from raw Java SMTP Client.");
    }

    private static void sendCommand(OutputStreamWriter out, BufferedReader in, String cmd) throws IOException {
        System.out.println("C: " + cmd);
        out.write(cmd + "\r\n");
        out.flush();
        System.out.println("S: " + in.readLine());
    }
}