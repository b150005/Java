import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import org.apache.commons.lang3.builder.*;

public class TestServer {
    public static void main(String[] args) throws Exception {
        try (
            ServerSocket ss = new ServerSocket(39468);
            Socket s = ss.accept();

            
        ) {
            // server
            System.out.println("from: " + s.getInetAddress());
            s.getOutputStream().write("Hello".getBytes());
            s.getOutputStream().flush();

            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        
        
    }
}