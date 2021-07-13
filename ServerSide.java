import java.io.*;
import java.net.*;

public class ServerSide {
    public static void main(String[] args) {
        try (
            // 「待ち受けポート番号」の指定
            ServerSocket svSocket = new ServerSocket(39648);

            // クライアントサイドからの接続要求の待機
            // -> 接続確立後は「クライアントIPアドレス」と「ポート番号」を保持するSocketオブジェクトが生成
            Socket socket = svSocket.accept();

            // サーバー -> クライアント への出力バイトストリームの生成
            OutputStream os = socket.getOutputStream();

            // 文字ストリーム -> バイトストリーム への変換を行うOutputStreamWriterフィルタの生成
            OutputStreamWriter osw = new OutputStreamWriter(os);

            // バッファリングフィルタの生成
            BufferedWriter bw = new BufferedWriter(osw);
        ) {
            // クライアントの情報の取得・出力
            System.out.println("from: " + socket.getInetAddress());

            // クライアントへの出力
            bw.write("Hello!");

            // 強制書き込み
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}