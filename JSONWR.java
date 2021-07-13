import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

public class JSONWR {
    public static void main(String[] args) {
        // JSONデータ
        JSONGrandchild g1c1 = new JSONGrandchild("1st Child of Child1", 111);
        JSONGrandchild g2c1 = new JSONGrandchild("2nd Child of Child1", 112);
        JSONGrandchild gc2 = new JSONGrandchild("Child of Child2", 121);
        JSONChild c1 = new JSONChild("1st Child of Parent", 110, List.of(g1c1, g2c1));
        JSONChild c2 = new JSONChild("2nd Child of Parent", 120, List.of(gc2));
        JSONChild c3 = new JSONChild("3rd Child of Parent", 130, List.of());
        JSONParent p = new JSONParent("Parent", 100, List.of(c1, c2, c3));
        JSONData data = new JSONData(p);

        // JSONデータにアクセスするObjectMapperオブジェクトの生成
        ObjectMapper mapper = new ObjectMapper();

        // -- JSONデータ -> JSON文字列 に変換する場合 --
        String json = "";
        try {
            // JSONデータ -> JSON文字列への変換
            json = mapper
                // JSONデータの整形
                .writerWithDefaultPrettyPrinter()
                // JSONデータ -> JSON文字列(String型) への変換
                .writeValueAsString(data);

            System.out.println(json);
        }
        catch (Exception e) {
            System.out.println("Conversion from JSONData to String has failed.");
        }

        // -- JSONデータをJSONファイルに出力する場合 --
        // JSONデータの「整形」を行うDefaultPrettyPrinterオブジェクトの取得
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();

        // JSONデータの「書き込み」を行うObjectWriterオブジェクトの取得
        ObjectWriter writer = mapper.writer(printer);

        // JSONファイルへの出力
        try {
            // JSONデータの書き込み、JSONファイルへの出力
            writer.writeValue(new File("src/test.json"), data);
        }
        catch (Exception e) {
            System.out.println("Output to JSON File has failed.");
        }
        
        JSONData resData = null;
        // -- JSONファイルの読み込み --
        try {
            // JSONデータオブジェクトの取得
            resData = mapper.readValue(new File("src/test.json"), JSONData.class);
        }
        catch (InvalidDefinitionException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Input from JSON File has failed.");
        }

        // 「データバインディング」によるJSONデータの取得
        JSONParent resP = resData.getParent();
        JSONChild resC1 = resP.getChildren().get(0);
        JSONChild resC2 = resP.getChildren().get(1);
        JSONChild resC3 = resP.getChildren().get(2);
        JSONGrandchild resG1C1 = resC1.getGrandchildren().get(0);
        JSONGrandchild resG2C1 = resC1.getGrandchildren().get(1);
        JSONGrandchild resGC2 = resC2.getGrandchildren().get(0);

        System.out.println("parent: " + resP);
        System.out.println("child1: " + resC1);
        System.out.println("child2: " + resC2);
        System.out.println("child3: " + resC3);
        System.out.println("child1 of child1: " + resG1C1);
        System.out.println("child2 of child1: " + resG2C1);
        System.out.println("child of child2: " + resGC2);
    }
}

// -- JSONデータ構造を定義するクラス --
// -> JSONファイルの「読み込み」時の「データバインディング」でも利用

// 「データ順序」は@JsonPropertyOrder()アナテイションで記述
@JsonPropertyOrder({
    "parent"
})
// JSONデータを保持するクラス
class JSONData {
    // 「キー」は@JsonProperty()アナテイションで記述
    @JsonProperty("parent")
    private JSONParent parent;
    
    // コンストラクタ
    // -> JSONファイルの読み込み時に「Class<E>」として参照するため、
    //    各クラスにデフォルトコンストラクタを用意しておく必要がある
    public JSONData() {}
    public JSONData(JSONParent p) {
        this.parent = p;
    }

    // ゲッタ(=アクセサ)
    public JSONParent getParent() {
        return this.parent;
    }

    @Override
    public String toString() {
        return this.parent.toString();
    }
}

@JsonPropertyOrder({
    "relationship", 
    "id", 
    "child"
})
class JSONParent {
    @JsonProperty("relationship")
    private String ps;
    @JsonProperty("id")
    private int pi;
    @JsonProperty("child")
    private List<JSONChild> children;

    // コンストラクタ
    public JSONParent() {}
    public JSONParent(String ps, int pi, List<JSONChild> cs) {
        this.ps = ps;
        this.pi = pi;
        this.children = cs;
    }

    // ゲッタ(=アクセサ)
    public String getPs() {
        return this.ps;
    }
    public int getPi() {
        return this.pi;
    }
    public List<JSONChild> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        return JSONParent.class.getSimpleName() + "(" + this.getPs() + ", " + this.getPi() + ", " + this.getChildren() + ")";
    }
}

@JsonPropertyOrder({
    "relationship", 
    "id", 
    "child"
})
class JSONChild {
    @JsonProperty("relationship")
    private String cs;
    @JsonProperty("id")
    private int ci;
    @JsonProperty("child")
    private List<JSONGrandchild> grandchildren;

    // コンストラクタ
    public JSONChild() {}
    public JSONChild(String cs, int ci, List<JSONGrandchild> gs) {
        this.cs = cs;
        this.ci = ci;
        this.grandchildren = gs;
    }

    // ゲッタ(=アクセサ)
    public String getCs() {
        return this.cs;
    }
    public int getCi() {
        return this.ci;
    }
    public List<JSONGrandchild> getGrandchildren() {
        return this.grandchildren;
    }

    @Override
    public String toString() {
        return JSONChild.class.getSimpleName() + "(" + this.getCs() + ", " + this.getCi() + ", " + this.getGrandchildren() + ")";
    }
}

@JsonPropertyOrder({
    "relationship", 
    "id"
})
class JSONGrandchild {
    @JsonProperty("relationship")
    private String gs;
    @JsonProperty("id")
    private int gi;

    // コンストラクタ
    public JSONGrandchild() {}
    public JSONGrandchild(String gs, int gi) {
        this.gs = gs;
        this.gi = gi;
    }

    // ゲッタ(=アクセサ)
    public String getGs() {
        return this.gs;
    }
    public int getGi() {
        return this.gi;
    }

    @Override
    public String toString() {
        return JSONGrandchild.class.getSimpleName() + "(" + this.getGs() + ", " + this.getGi() + ")";
    }
}