import java.io.File;
import java.util.Scanner;

import javax.sound.midi.*;
import javax.sound.sampled.*;

public class SoundPlay {
    public static void main(String[] args) {
        // 「MIDIファイルの停止」「wavファイルの再生・停止」で利用するScannerオブジェクト
        Scanner stdIn = new Scanner(System.in);

        // -- 「MIDIファイル」の再生 --
        try (
            // 「MIDIファイル」を再生するSequencerオブジェクト(=シーケンサ; シンセサイザ)の取得
            // -> MIDIファイルへのアクセサ(=システムリソース)を生成するMidiSystemクラスの静的メソッドを利用
            Sequencer sequencer = MidiSystem.getSequencer();
        ) {
            // シーケンサの再生準備
            sequencer.open();

            // 「シンセサイザ形式の音声データ」を保持するSequenceオブジェクトの生成
            Sequence seq = MidiSystem.getSequence(new File("src/paleBlue.MID"));

            // シーケンサに音声情報をセット
            sequencer.setSequence(seq);

            // 「ループ」の指定
            // -> ループしない場合は引数に"-1"を指定
            sequencer.setLoopCount(-1);

            // MIDIファイルの再生
            sequencer.start();

            // MIDIファイルの停止を促す
            System.out.println("Push Enter to stop MIDI Sound.");
            stdIn.nextLine();

            // MIDIファイルの停止
            sequencer.stop();
        }
        catch (Exception e) {
            System.out.println("Playing MIDI file has failed.");

            // MIDIファイルの再生・停止中に例外が送出された場合はScannerをここでクローズ
            stdIn.close();
        }

        // -- 「サンプリング形式ファイル」の再生 --
        try (
            // 「サンプリング形式ファイル」の読み込みを行うAudioInputStreamフィルタの生成
            // -> サンプリング形式ファイルへのアクセサ(=システムリソース)を生成するAudioSystemクラスの静的メソッドを利用
            // <= "mp3"ファイルは標準ライブラリでは再生不可
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/bark.wav"));
        ) {
            // 「サンプリング形式の音声データ」を再生するClipオブジェクト(=プレーヤー)
            Clip clip = AudioSystem.getClip();

            // プレーヤーの再生準備
            clip.open(ais);

            String str = "";
            do {
                // 「音声ファイル」の再生を促す
                System.out.println("Push Enter to play sound.");
                stdIn.nextLine();

                // 「音声ファイル」の再生
                clip.start();

                // 「シーク」を最初に戻す
                clip.setFramePosition(0);

                // 「音声ファイル」の再生を促す
                System.out.println("Push \"1\" to stop sound.");
                str = stdIn.nextLine();
            } while (!(str.equals("1")));
            
            // 「音声ファイル」の停止
            clip.stop();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            System.out.println("File format is not adapted.");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Playing sound file has failed.");
        }
        finally {
            // Scannerオブジェクトのクローズ処理
            stdIn.close();
        }
    }
}
