
import java.io.*;
import java.net.*;

class UDPServer {

    public static DatagramSocket ds;
    public static byte buffer[] = new byte[1024];
    public static int clientport = 12345, serverport = 12346;

    public static void main(String args[]) throws Exception {

        ds = new DatagramSocket(clientport); // connecting to client
        System.out.println("press ctrl+c to quit the program");

        BufferedReader dis = new BufferedReader(new InputStreamReader(System.in)); //reading from console

        InetAddress ia = InetAddress.getLocalHost();

        while (true) {
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            ds.receive(p); // get from client - already connected client port
            String psx = new String(p.getData(), 0, p.getLength());

            System.out.println("Client:" + psx);
            System.out.println("Server:");

            String str = dis.readLine(); // from console - server message

            if (str.equals("end")) {
                break;
            }
            buffer = str.getBytes();
            ds.send(new DatagramPacket(buffer, str.length(), ia, serverport)); //send to client
        }


    }
}




