import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;

public class HTTPGetPost {
    public static void main(String[] args) {
        // HTTPリクエストを送信するHttpClientオブジェクトの生成
        HttpClient client = HttpClient
            // HttpClient.Builderオブジェクトの生成
            .newBuilder()
            // HTTPプロトコルのバージョン指定
            .version(Version.HTTP_1_1)
            // リダイレクトポリシーの指定
            .followRedirects(Redirect.NORMAL)
            // HttpClientオブジェクトの生成
            .build();
        

        // -- GETメソッド --
        // HTTPリクエスト内容を保持するHttpRequestオブジェクトの生成
        HttpRequest requestGet = HttpRequest
            // HttpRequest.Builderオブジェクトの生成
            .newBuilder()
            // URLの指定
            .uri(URI.create("http://httpbin.org/get"))
            // HTTPリクエストメソッドの指定
            .GET()
            // HttpRequestオブジェクトの生成
            .build();
        
        // HTTPリクエストの送信
        try {
            HttpResponse<String> response = client.send(requestGet, 
                // レスポンスボディ(=HTML情報)を「文字列」として処理するHttpResponse.BodyHandlersクラスのメソッドを利用
                HttpResponse.BodyHandlers.ofString()
            );

            // レスポンスボディの取得
            String body = response.body();

            // ステータスコードの取得
            int status = response.statusCode();

            // レスポンス内容の表示
            System.out.println("status code: " + status);
            System.out.println("-- response body(GET) --");
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // -- POSTメソッド --
        // HTTPリクエスト内容を保持するHttpRequestオブジェクトの生成
        HttpRequest requestPost = HttpRequest
            // HttpRequest.Builderオブジェクトの生成
            .newBuilder()
            // URLの指定
            .uri(URI.create("http://httpbin.org/post"))
            // リクエストヘッダの追加
            .header("Content-Type", "application/json")
            // リクエストヘッダの追加
            .header("Accept", "application/json")
            // HTTPリクエストメソッドの指定
            .POST(HttpRequest.BodyPublishers.ofString(
                "Hello!"
            ))
            // HttpRequestオブジェクトの生成
            .build();

        // HTTPリクエストの送信
        try {
            HttpResponse<String> response = client.send(requestPost, 
                // レスポンスボディ(=HTML情報)を「文字列」として処理するHttpResponse.BodyHandlersクラスのメソッドを利用
                HttpResponse.BodyHandlers.ofString()
            );

            // レスポンスボディの取得
            String body = response.body();

            // ステータスコードの取得
            int status = response.statusCode();

            // レスポンス内容の表示
            System.out.println("status code: " + status);
            System.out.println("-- response body(POST) --");
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
