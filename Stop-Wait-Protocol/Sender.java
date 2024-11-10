import java.io.*;
import java.net.*;

public class Sender {
    Socket sender;
    ObjectOutputStream out; //for writing data 
    ObjectInputStream in; // for reading ACK
    String packet, ack, str, msg;
    int n, i = 0, sequence = 0;

    Sender() {}

    public void run(){
        try {
            // Initialize reader to read user input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            // Waiting for the connection from the receiver
            System.out.println("Waiting for Connection....");
            sender = new Socket("localhost", 2004);
            sequence = 0;
            
            // Initialize input/output streams
            out = new ObjectOutputStream(sender.getOutputStream()); //writing
            out.flush();
            in = new ObjectInputStream(sender.getInputStream()); //reading
            
            // Receive the initial connection message from receiver
            str = (String) in.readObject();
            System.out.println("receiver > " + str);
            
            // Ask the user to input the data to be sent
            System.out.println("Enter the data to send....");
            packet = br.readLine();
            n = packet.length();
            
            // Start sending the message in packets with sequence numbers
            do {
                try {
                    if (i < n) {
                        // Send a packet with sequence number and a character
                        msg = String.valueOf(sequence);
                        msg = msg.concat(packet.substring(i, i + 1)); // a char
                    } else if (i == n) {
                        // Send "end" to indicate end of data
                        msg = "end";
                        out.writeObject(msg);
                        break;
                    }

                    // Send the packet
                    out.writeObject(msg);
                    
                    // Toggle the sequence number (0 -> 1, 1 -> 0)
                    sequence = (sequence == 0) ? 1 : 0;
                    out.flush();
                    
                    System.out.println("Data sent > " + msg);
                    
                    // Wait for acknowledgment from the receiver
                    ack = (String) in.readObject();
                    System.out.println("Waiting for ACK.....\n\n");
                    
                    // Check if the acknowledgment matches the expected sequence
                    if (ack.equals(String.valueOf(sequence))) {
                        i++;
                        System.out.println("Receiver > Packet received\n\n");
                    } else {
                        // If not, resend the packet (simulating timeout)
                        System.out.println("Timeout, resending data....\n\n");
                        sequence = (sequence == 0) ? 1 : 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (i < n + 1);

            System.out.println("All data sent. Exiting.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                in.close();
                out.close();
                sender.close();
            } catch (IOException ex) {
            }
            
        }
    }

    public static void main(String[] args) {
      
        Sender s = new Sender();
        s.run();
    }
}














































