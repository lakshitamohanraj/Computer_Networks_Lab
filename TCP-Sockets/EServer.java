
import java.io.*;
import java.net.*; 
public class EServer 
{ 
public static void main(String args[]) 
{ 
        ServerSocket sender=null; 
        String line; 
        DataInputStream data; 
        PrintStream ps; 
        Socket connection=null; 
      
        try 
        { 
            sender=new ServerSocket(9000); 

            connection=sender.accept();  // accept client request for connection

            data=new DataInputStream(connection.getInputStream()); 
            ps=new PrintStream(connection.getOutputStream()); 
        while(true) 
        { 
                line=data.readLine();  //reads data from client
                ps.println(line);  // writes back to the client
                
        } 

        } 
        catch(IOException e) 
        { 
           System.out.println(e); 
        } 
    } 
}


