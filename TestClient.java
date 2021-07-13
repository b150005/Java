import java.io.*;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try (
            Socket cs = new Socket("127.0.0.1", 39468);
            InputStream is = cs.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = cs.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
        ) {
            bw.write("Hi");
            bw.flush();
            
            // client
            String str = br.readLine();
            while (true) {
                System.out.println("response: " + str);
                str = br.readLine();
                if (str == null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
