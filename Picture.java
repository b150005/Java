import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

public class Picture {
    public static void main(String[] args) {
        try (
            // 「バイナリファイルの出力先情報」を保持するFileOutputStreamオブジェクトの生成
            FileOutputStream fos = new FileOutputStream("src/dog.png")
        ) {
            // 「画像データ」を保持するBufferedImageオブジェクト
            // -> ImageIOの静的メソッドを利用して「画像ファイル」を読み込む
            BufferedImage image = ImageIO.read(new File("src/dog.jpg"));

            // 画像データの「サイズ情報」の取得
            int h = image.getHeight();
            int w = image.getWidth();

            System.out.println("image size: " + w + "x" + h);

            // 「画像データ」の変換
            // -> ImageIOの静的メソッドを利用して「画像データ」を出力
            // <- 「変換元画像データ」「画像フォーマット形式」「出力先情報」を引数にとる
            ImageIO.write(image, "png", fos);
        }
        catch (Exception e) {
            System.out.println("Some exception has occurred.");
        }
    }
}