import java.sql.*;
import java.util.Date;

public class MySQL {
    public static void main(String[] args) {
        // -- JDBCドライバの読み込み --
        try {
            // JDBCドライバの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            // 例外の再送出
            throw new IllegalStateException("JDBC Driver is not found.");
        }

        // -- テーブルデータの更新 --
        // SQL文を保持するPreparedStatementオブジェクト
        PreparedStatement psInsert = null;
        Connection con1 = null;
        try {
            // JDBCドライバへの接続を行うConnectionオブジェクトの生成
            // -> JDBC URLは"jdbc:mysql://<ユーザ名>/<DB名>"で指定
            con1 = DriverManager.getConnection("jdbc:mysql://root@localhost/test");

            // トランザクション制御として設定
            con1.setAutoCommit(false);

            // -- SQL文(更新系) --
            // バインド変数を利用したINSERT文
            psInsert = con1.prepareStatement(
                "insert into testTable(data, date) values (?, \"2000-01-01 00:00:00\")"
            );

            // バインド変数への値のバインディング
            psInsert.setString(1, "new Data");

            // データを操作するDML文の実行
            // -> 更新したレコード数がint型の値で返却
            int r = psInsert.executeUpdate();

            if (r != 0) {
                System.out.println("Query OK, " + r + " row affected.");
            }
            else {
                System.out.println("Query NG.");
            }

            // 処理が正常終了した場合はコミット
            con1.commit();
        }
        catch (SQLException e) {
            try {
                // 処理が途中で失敗した場合はロールバック
                con1.rollback();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        finally{
            // Connectionオブジェクトがnullでないことを保証
            if (con1 != null) {
                try {
                    // Connectionオブジェクトの解放
                    con1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // PreparedStatementオブジェクトがnullでないことを保証
            if (psInsert != null) {
                try {
                    // PreparedStatementオブジェクトの解放
                    psInsert.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // -- テーブルデータの取得 --
        try (
            // JDBCドライバへの接続を行うConnectionオブジェクトの生成
            Connection con = DriverManager.getConnection("jdbc:mysql://root@localhost/test");

            // -- SQL文(検索系) --
            // バインド変数を利用したSELECT文
            PreparedStatement psSelect1 = con.prepareStatement(
                "select * from testTable where id >= ?"
            );
        ) {
            // バインド変数への値のバインディング
            psSelect1.setInt(1, 2);

            // データを抽出するクエリ文の実行
            // -> 抽出結果を保持するResultSetオブジェクトが返却
            // <- 本来はResultSetオブジェクトも解放処理が必要であるが、
            //    Connectionオブジェクトの解放処理によって連鎖的に解放される
            ResultSet res = psSelect1.executeQuery();

            // 抽出結果をコンソールに出力
            while (res.next()) {
                // 「カラム名」または「カラムのインデックス番号」を指定して値を取得
                int id = res.getInt("id");
                String data = res.getString(2);

                System.out.println("id: " + id + ", data: " + data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        }

        // -- 「日時」を表すTIMESTAMP型データ(=JavaではDate型)の取得 --
        try (
            // JDBCドライバへの接続を行うConnectionオブジェクトの生成
            Connection con = DriverManager.getConnection("jdbc:mysql://root@localhost/test");

            // -- SQL文(検索系) --
            // バインド変数を利用したSELECT文
            PreparedStatement psSelect2 = con.prepareStatement(
                "select * from testTable where id >= ? and date >= ?"
            );
        ) {
            // バインド変数への値のバインディング
            psSelect2.setInt(1, 2);
            psSelect2.setString(2, "2000-01-01 00:00:00");

            // データを抽出するクエリ文の実行
            // -> 抽出結果を保持するResultSetオブジェクトが返却
            ResultSet res = psSelect2.executeQuery();

            // 抽出結果をコンソールに出力
            while (res.next()) {
                int id = res.getInt(1);
                Timestamp resTime = res.getTimestamp(3);

                // Timestamp型 -> long型 -> Date型 への変換
                long lon = resTime.getTime();
                Date date = new Date(lon);
                
                System.out.println("id: " + id + ", date: " + date);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
