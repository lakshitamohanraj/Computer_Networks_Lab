
import java.io.*;
import java.net.*;

class UDPClient {

    public static DatagramSocket ds;
    public static int clientport = 12345, serverport = 790;

    public static void main(String args[]) throws Exception {
        byte buffer[] = new byte[1024];

        ds = new DatagramSocket(serverport); //connecting to server
        BufferedReader dis = new BufferedReader(new InputStreamReader(System.in)); //reading from console

        System.out.println("server waiting");
        InetAddress ia = InetAddress.getLocalHost();

        while (true) {
            System.out.println("Client:");
            String str = dis.readLine(); // read client msg from console

            if (str.equals("end")) {
                break;
            }
            buffer = str.getBytes();

            ds.send(new DatagramPacket(buffer, str.length(), ia, clientport));//send to server

            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            ds.receive(p); // get data from server

            String psx = new String(p.getData(), 0, p.getLength());
            System.out.println("Server:" + psx);
        }
    }
}
