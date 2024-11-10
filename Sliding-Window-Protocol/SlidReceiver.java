import java.io.*;
import java.net.*;

class SlidReceiver {
    public static void main(String a[]) throws Exception {
        // Create a socket connection to the sender
        Socket s = new Socket(InetAddress.getLocalHost(), 10);

        // Create input and output streams for reading and writing data
        DataInputStream in = new DataInputStream(s.getInputStream());
        PrintStream p = new PrintStream(s.getOutputStream());

        int i = 0, rptr = -1, nf, rws = 8; // Receiver's buffer and window size
        String rbuf[] = new String[8]; // Buffer to store received frames
        String ch;

        do {
            nf = Integer.parseInt(in.readLine()); // Receive the number of frames

            if (nf <= rws - 1) {
                // If the number of frames is less than or equal to window size
                for (i = 1; i <= nf; i++) {
                    rptr = ++rptr % 8; // Update the receiver pointer in a circular way
                    rbuf[rptr] = in.readLine(); // Read the frame into the buffer
                    System.out.println("The received Frame " + rptr + " is: " + rbuf[rptr]);
                }
                rws -= nf; // Update the receiver's window size

                // Acknowledge the received frames
                System.out.println("\nAcknowledgment sent\n");
                p.println(rptr + 1); // Send acknowledgment (next expected frame)
                rws += nf; // Restore the window size

            } else {
                // If the number of frames exceeds the window size
                break;
            }

            // Ask the receiver if they want to continue receiving frames
            ch = in.readLine();

        } while (ch.equals("yes")); // Continue receiving if the receiver says 'yes'

        s.close(); // Close the socket connection
    }
}
