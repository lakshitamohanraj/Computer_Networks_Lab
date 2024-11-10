import java.io.*;
import java.net.*;

public class Receiver {
    ServerSocket receiver;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String packet, ack, data = "";
    int i = 0, sequence = 0;

    Receiver() {}

    public void run(){
        try {
            // Initialize server socket to listen on port 2004
            receiver = new ServerSocket(2004);
            System.out.println("Waiting for connection...");
            
            // Accept incoming connection from sender
            connection = receiver.accept();
            sequence = 0;
            
            // Initial connection established message
            System.out.println("Connection established:");
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            
            // Send a message back to the sender confirming the connection
            out.writeObject("connected .");
            
            // Continuously receive and process packets
            do {
                try {
                    // Read the incoming packet
                    packet = (String) in.readObject();
                    
                    // Check if the sequence number matches the expected sequence
                    if (Integer.valueOf(packet.substring(0, 1)) == sequence) {
                        // Append data and toggle sequence number
                        data += packet.substring(1);
                        sequence = (sequence == 0) ? 1 : 0;
                        System.out.println("\n\nReceiver > " + packet);
                    } 
                    else {
                        // Handle duplicate data (out-of-order packet)
                        System.out.println("\n\nReceiver > " + packet + " Duplicate data");
                    }
                    
                    // Send acknowledgment back to the sender
                    if (i < 3) {
                        out.writeObject(String.valueOf(sequence));
                        i++;
                    } else {
                        // Every 3rd packet, switch the sequence number
                        out.writeObject(String.valueOf((sequence + 1) % 2));
                        i = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (!packet.equals("end"));
            
            // Print the received data after "end" message
            System.out.println("Data received = " + data);
            
            // Notify the sender that the connection is ending
            out.writeObject("connection ended .");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          
            try {
                in.close();
                out.close();
                receiver.close();
            } catch (IOException ex) {
            }
           
        }
    }

    public static void main(String[] args){
        Receiver r = new Receiver();
        while (true) {
            r.run();
        }
    }
}



