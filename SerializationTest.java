import java.io.*;

public class SerializationTest {
    public static void main(String[] args) {
        // 直列化を行う直列化クラスオブジェクト
        SerializedData dataBefore = new SerializedData("ABC", 123, new SerializedClass("あいう", 456));

        // インスタンス -> バイト列 のシリアライズ処理・ファイル出力
        try (
            // 「バイナリファイル」に書き込むFileOutputStreamフィルタの生成
            FileOutputStream fos = new FileOutputStream("src/serializedData.dat");

            // インスタンス -> バイト列　の変換(=シリアライズ処理)を行うObjectOutputStreamフィルタの生成
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            // シリアライズ処理
            oos.writeObject(dataBefore);

            // 強制書き込み
            oos.flush();
        }
        catch (Exception e) {
            System.out.println("Serialization has failed.");
        }

        // バイナリファイルの読み込み・バイト列 -> インスタンス のデシリアライズ処理
        try (
            // 「バイナリファイル」を読み込むFileInputStreamフィルタの生成
            FileInputStream fis = new FileInputStream("src/serializedData.dat");

            // バイト列 -> インスタンス の変換(=デシリアライズ処理)を行うObjectInputStreamフィルタの生成
            ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            SerializedData dataAfter = (SerializedData) ois.readObject();

            System.out.println(dataAfter);
        }
        catch (Exception e) {
            System.out.println("Deserialization has failed.");
        }
    }
}

// 直列化クラス
// -> インスタンス ⇄ バイト列 の直列化を行うクラスはSerializableインタフェースの実装クラス
class SerializedData implements Serializable {
    // 直列化クラスの「バージョン」を表現するシリアルバージョンUID
    private static final long serialVersionUID = 123456789L;

    private String str;
    private int i;
    private SerializedClass sc;

    // コンストラクタ
    public SerializedData(String str, int i, SerializedClass sc) {
        this.str = str;
        this.i = i;
        this.sc = sc;
    }

    // ゲッタ(=アクセサ)
    public String getStr() {
        return this.str;
    }
    public int getI() {
        return this.i;
    }
    public SerializedClass getSc() {
        return this.sc;
    }

    @Override
    public String toString() {
        return "SerializedData(" + this.getStr() + ", " + this.getI() + ", " + this.getSc() + ")";
    }
}

// 直列化クラスのフィールドクラスも同様にSerializableインタフェースの実装クラスでなければならない
class SerializedClass implements Serializable {
    private String str;
    private int i;

    // コンストラクタ
    public SerializedClass(String str, int i) {
        this.str = str;
        this.i = i;
    }
    
    // ゲッタ(=アクセサ)
    public String getStr() {
        return this.str;
    }
    public int getI() {
        return this.i;
    }

    @Override
    public String toString() {
        return "SerializedClass(" + this.getStr() + ", " + this.getI() + ")";
    }
}