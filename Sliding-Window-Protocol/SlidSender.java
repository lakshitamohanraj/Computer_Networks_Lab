import java.io.*;
import java.net.*;

public class SlidSender {
    public static void main(String a[]) throws Exception {
        // Create a server socket to listen for a connection
        ServerSocket ser = new ServerSocket(10);
        Socket s = ser.accept();

        // Create input and output streams for reading and writing data
        DataInputStream in = new DataInputStream(System.in);
        DataInputStream in1 = new DataInputStream(s.getInputStream());
        String sbuff[] = new String[8]; // Buffer to store frames (window size = 8)
        PrintStream p;

        int sptr = 0, sws = 8, nf, ano, i;
        String ch;

        do {
            p = new PrintStream(s.getOutputStream());
            System.out.print("Enter the number of frames: ");
            nf = Integer.parseInt(in.readLine());
            p.println(nf); // Send the number of frames to receiver

            if (nf <= sws - 1) {
                // If the number of frames is less than or equal to window size
                System.out.println("Enter " + nf + " Messages to be sent:");
                for (i = 1; i <= nf; i++) {
                    sbuff[sptr] = in.readLine(); // Read a message from the user
                    p.println(sbuff[sptr]); // Send the message to the receiver
                    sptr = ++sptr % 8; // Circular buffer increment
                }
                sws -= nf; // Update the sender's window size

                // Wait for acknowledgment
                System.out.print("Acknowledgment received: ");
                ano = Integer.parseInt(in1.readLine());
                System.out.println(" for " + ano + " frames");
                sws += nf; // Restore the window size after acknowledgment
            } else {
                // If the number of frames exceeds the window size
                System.out.println("The number of frames exceeds window size");
                break;
            }

            // Ask the sender if they want to send more frames
            System.out.print("\nDo you want to send more frames? (yes/no): ");
            ch = in.readLine();
            p.println(ch);

        } while (ch.equals("yes")); // Continue sending if the user says 'yes'

        s.close(); // Close the socket connection
    }
}
