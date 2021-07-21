import javax.swing.*;

public class SwingFrame {
    public static void main(String[] args) {
        // フレームタイトルを指定したフレームの生成
        JFrame frame = new JFrame("Swing");

        // 「×ボタン」によるアプリケーションの終了
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // フレームサイズの指定
        frame.setSize(400, 200);

        // フレームの表示
        frame.setVisible(true);
    }
}
