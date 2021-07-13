import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class XMLWR {
    public static void main(String[] args) {
        // -- XML形式データをXMLファイルに出力 --
        /*
            try節で初期化するインスタンスをnullで初期化
            -> try節で宣言された変数はtry節外から"見えない"
        */
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;

        // XML文書(=Document)の定義
        try {
            // DocumentBuilderの「ファクトリ」の生成
            dbf = DocumentBuilderFactory.newInstance();

            // Documentを生成するDocumentBuilderオブジェクトの生成
            db = dbf.newDocumentBuilder();
        }
        catch (Exception e) {
            System.out.println("DocumentBuilder cannot be made.");
        }

        // Documentオブジェクトの生成
        Document d = db.newDocument();

        // XMLの構成要素(=タグ)の作成
        Element parent = d.createElement("parent");
        Element child1 = d.createElement("child1");
        Element child2 = d.createElement("child2");
        Element grandchild1_1 = d.createElement("grandchild1_1");
        Element grandchild2_1 = d.createElement("grandchild2_1");
        Element grandchild1_2 = d.createElement("grandchild1_2");
        
        // タグに「属性」と「属性値」を追加
        parent.setAttribute("id", "100");
        child1.setAttribute("id", "110");
        child2.setAttribute("id", "120");
        grandchild1_1.setAttribute("id", "111");
        grandchild2_1.setAttribute("id", "112");
        grandchild1_2.setAttribute("id", "121");

        // タグ内の「文字列情報」を追加
        // -> XMLは「最下位タグ」のみ文字列情報を保持
        grandchild1_1.appendChild(d.createTextNode("1st Child of child1"));
        grandchild2_1.appendChild(d.createTextNode("2nd Child of child1"));
        grandchild1_2.appendChild(d.createTextNode("Child of child2"));

        // Documentに親タグ(parentタグ)をネスト
        // <- ElementインタフェースはNodeインタフェースを継承
        d.appendChild(parent);
        
        // 親タグ(parentタグ)に子タグ(childタグ)をネスト
        parent.appendChild(child1);
        parent.appendChild(child2);

        // 子タグ(childタグ)に孫タグ(grandchildタグ)をネスト
        child1.appendChild(grandchild1_1);
        child1.appendChild(grandchild2_1);
        child2.appendChild(grandchild1_2);

        /*
            try節で初期化するインスタンスをnullで初期化
            -> try節で宣言された変数はtry節外から"見えない"
        */
        Transformer t = null;

        // Transformerオブジェクトの生成
        try {
            // Transformerの「ファクトリ」の生成
            TransformerFactory tf = TransformerFactory.newInstance();

            // 「Document -> XMLファイル への変換」を行うTransformerオブジェクトの生成
            t = tf.newTransformer();
        }
        catch (Exception e) {
            System.out.println("Transformer cannot be made.");
        }

        // -- 出力方式の設定開始 --
        // 「インデント」の有効化
        t.setOutputProperty("indent", "yes");
        // 「文字コード」の指定
        t.setOutputProperty("encoding", "UTF-8");
        // -- 出力方式の設定終了 --

        // 「Document -> DOMSource -> XMLファイル(=StreamResult)」への変換
        try {
            // Document -> DOMSource への変換
            DOMSource doms = new DOMSource(d);

            // 出力先ファイルパス(=ファイル名)の指定
            File f = new File("src/test.xml");

            // 「マークアップ形式ファイルの出力先情報」を保持するStreamResultオブジェクトの生成
            StreamResult sr = new StreamResult(f);

            // DOMSource -> XMLファイル(=StreamResult) への変換(=XMLファイルの出力)
            t.transform(doms, sr);
        }
        catch (Exception e) {
            System.out.println("Conversion from Document to XML failed");
        }

        // -- 書き込みの終了待機 --
        // -> 待機しない場合は書き込みが間に合わずIOExceptionが送出
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
            System.out.println("Exception occurred in sleep().");
        }

        // -- XML文書の読み込み(XML解析) --
        try {
            // XMLファイルを「バイトストリーム」経由で読み込むInputStreamオブジェクト
            InputStream is = new FileInputStream("src/test.xml");

            // 「XML解析結果」を保持するDocumentオブジェクトの取得(XML解析)
            //Document dp = db.parse(is);
            Document dp = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

            // 最上位タグ(親タグ)の取得
            Element ep = dp.getDocumentElement();

            // 子タグの取得
            Element ec1 = findChildByTag(ep, "child1");
            Element ec2 = findChildByTag(ep, "child2");

            // 孫タグの取得
            List<Element> grandchildList = new ArrayList<>();
            grandchildList.add(findChildByTag(ec1, "grandchild1_1"));
            grandchildList.add(findChildByTag(ec1, "grandchild2_1"));
            grandchildList.add(findChildByTag(ec2, "grandchild1_2"));

            // タグ内の「文字列情報」を取得
            List<String> textList = new ArrayList<>();
            for (Element grandchild : grandchildList) {
                textList.add(grandchild.getTextContent());
            }
            
            // XML解析結果の出力
            for (int i = 0; i < grandchildList.size(); i++) {
                System.out.println(grandchildList.get(i).getTagName() + ": " + textList.get(i));
            }
        }
        catch (Exception e) {
            System.out.println("Input-related Exception occurred.");
        }

        
    }

    // 子タグ(=Elementオブジェクト)を返却する静的メソッド
    static Element findChildByTag(Element parent, String childTagName) throws Exception {
        // 子タグを全て取得
        NodeList children = parent.getChildNodes();

        // 子タグの線形探索
        for (int i = 0; i < children.getLength(); i++) {
            // 子タグの各要素がElementオブジェクトであることを保証
            if (children.item(i) instanceof Element) {
                // 子タグの取得
                // -> NodeList.item()の返却型はNode型であるため、
                //    Node -> Element へのダウンキャストが必要
                Element child = (Element) children.item(i);

                // 引数のタグ名と一致している場合は子タグを返却
                if (child.getTagName().equals(childTagName)) {
                    return child;
                }
            }
        }
        //見つからない場合はnullを返却
        System.out.println("Cannot find designated child tag.");
        return null;
    }
}