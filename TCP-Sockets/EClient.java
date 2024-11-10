
import java.io.*;
import java.net.*; 
public class EClient 
{ 
public static void main(String arg[]) 
{ 
        Socket connection=null; 
        String line; 
        DataInputStream is,is1; 
        PrintStream os;   // can perform read , write by printing   
            try 
            { 
                InetAddress ia = InetAddress.getLocalHost(); 
            connection=new Socket(ia,9000); // client will be connected to server

                os=new PrintStream(connection.getOutputStream()); // for writing back to the consoe 
                is=new DataInputStream(System.in);  // from the client , reading from console
                is1=new DataInputStream(connection.getInputStream()); // Reading from server
             while(true) 
              { 
                    System.out.println("Client:"); 
                    line=is.readLine(); 
                    os.println(line); // writes it to the server
                    System.out.println("Server:" + is1.readLine()); 
              }
            } 
                catch(IOException e) 
                { 
                System.out.println("Socket Closed!");
                }
}
}





