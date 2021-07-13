public class Generics {
    public static void main(String[] args) {
        // NumberのサブクラスであるInteger型
        // -> GenericsTest1は<E extends Number>型であるため、
        //    "Number"クラスとそのサブクラスのインスタンスが生成可能
        GenericsTest1<Integer> gen1 = new GenericsTest1<>();
        GenericsTest1<Float> gen2 = new GenericsTest1<>();
        GenericsTest1<Number> gen3 = new GenericsTest1<>();
        // 実型引数の型ワイルドカードを用いることも可能(後述)
        GenericsTest1<? extends Number> gen4 = new GenericsTest1<Long>();
        GenericsTest2<? super Number> gen5 = new GenericsTest2<>();

        // インスタンスgen1は<Integer>型なので、Integer型整数の代入が可能
        gen1.setData(Integer.valueOf(123));
        Integer i = gen1.getData();
        System.out.println(i);

        // インスタンスgen2は<Float>型なので、Float型小数の代入が可能
        gen2.setData(Float.MIN_VALUE);
        // Float型データをfloat型として取得することも可能
        float f = gen2.getData();
        System.out.println(f);

        // インスタンスgen3は<Number>型なので、Double型小数の代入が可能
        gen3.setData(Double.MIN_VALUE);
        // Number型 -> Double型 へのダウンキャストは「明示的」に行う
        double d = (Double) gen3.getData();
        System.out.println(d);

        // インスタンスgen4は<? extends Number>型だが、"Number"およびそのサブクラスの代入は不可能
        // -> "Number"およびそのサブクラス -> <? extends Number>型 への変換は不可能
        // gen4.setData(Long.valueOf(35005L));

        // インスタンスgen5は<? super Number>型だが、"Number"およびそのサブクラスの代入が可能
        // -> "Number"およびそのサブクラス -> <? super Number>型 への変換は可能
        gen5.setData(Long.valueOf(27593405L));
        // ただし、<? super Number>型は「"Number"クラスとその全ての上位クラス」を表すため、
        // "Object型"としてのみ読み込み可能
        Object l = gen5.getData();
        System.out.println(l);
    }
}

// ジェネリクスを"Number"またはそのサブクラスに制限したクラス
class GenericsTest1<E extends Number> {
    private E data;

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return this.data;
    }
}

// ジェネリクスを制限しないクラス
class GenericsTest2<E> {
    private E data;

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return this.data;
    }
}