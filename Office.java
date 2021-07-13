import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class Office {
    public static void main(String[] args) {
        // -- Excelファイルの書き出し --
        // 「Excelブック」を表すWorkbookオブジェクトの生成
        // -> XSSFWorkbookはWorkbookインタフェースの実装クラスであり、
        //    Excel2007以降のExcelフォーマット形式に対応
        Workbook book = new XSSFWorkbook();

        // 「Excelシート」を表すSheetオブジェクトの生成
        Sheet sheet = book.createSheet("test");

        // 「行」を表すRowオブジェクトの生成
        Row row1 = sheet.createRow(0);

        // 「セル」を表すCellオブジェクトの生成
        Cell cellA1 = row1.createCell(0);
        Cell cellB1 = row1.createCell(1);
        Cell cellC1 = row1.createCell(2);

        // セルに「値」をセット
        cellA1.setCellValue(123);
        cellB1.setCellValue(456);

        // セルに「関数」をセット
        // -> 冒頭の「=(イコール)」は不要
        cellC1.setCellFormula("A1+B1");

        // 「フォントスタイル」を表すFontオブジェクトの生成
        // -> スタイル毎にオブジェクトを作成する必要がある
        Font f = book.createFont();
        
        // 「太字」にする
        f.setBold(true);

        // 「文字色」の指定
        // -> 「色」は列挙型IndexedColorsの定数を利用
        f.setColor(IndexedColors.GREEN.getIndex());

        // 「セルスタイル」を表すCellStyleオブジェクトの生成
        // -> スタイル毎にオブジェクトを作成する必要がある
        CellStyle cs_B1 = book.createCellStyle();
        CellStyle cs_C1 = book.createCellStyle();

        // 「枠線」の指定
        // -> 枠線は上下左右個別に設定する必要がある
        // <- 「枠線の種類」は列挙型BorderStyleの定数を利用
        cs_C1.setBorderTop(BorderStyle.MEDIUM);
        cs_C1.setBorderBottom(BorderStyle.HAIR);
        cs_C1.setBorderLeft(BorderStyle.DASHED);
        cs_C1.setBorderRight(BorderStyle.DOTTED);

        // 「背景色」の指定
        cs_C1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

        // 「塗りつぶしパターン」の指定
        // -> 「パターン」は列挙型FillPatternTypeの定数を利用
        cs_C1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // セルスタイルに「フォントスタイル」をセット
        cs_B1.setFont(f);

        // セルに「セルスタイル」をセット
        cellB1.setCellStyle(cs_B1);
        cellC1.setCellStyle(cs_C1);

        // Excelファイルへの出力
        try (
            // 「バイナリファイル」への書き込み・出力を行うFileOutputStreamオブジェクトの生成
            OutputStream os = new FileOutputStream("src/test.xlsx")
        ) {
            book.write(os);
        }
        catch (Exception e) {
            System.out.println("Output to Excel File has failed.");
        }
        finally {
            try {
                // Workbookオブジェクトの解放処理
                book.close();
            }
            catch (Exception e) {
                System.out.println("Closing Workbook has failed.");
            }
        }

        // -- Excelファイルの読み込み --
        try (
            // 「バイナリファイル」を読み込むFileInputStreamオブジェクトの生成
            FileInputStream fis = new FileInputStream("src/test.xlsx");

            // 「Excelブック」の情報を格納するWorkbookオブジェクトの生成
            // -> Workbookの「ファクトリ」であるWorkbookFactoryを利用
            Workbook bookR = WorkbookFactory.create(fis);
        ) {
            // 「Excelシート」を指定して読み込む
            Sheet sheetR = bookR.getSheet("test");

            // 「行」を指定して読み込む
            Row row1R = sheetR.getRow(0);

            // 「セル」を指定して読み込む
            Cell B1 = row1R.getCell(1);
            Cell C1 = row1R.getCell(2);

            // FormulaEvaluatorオブジェクトを生成するCreationHelperオブジェクトの取得
            CreationHelper helper = bookR.getCreationHelper();

            // セルに記述された「関数を実行」するFormulaEvaluatorオブジェクトの生成
            FormulaEvaluator evaluator = helper.createFormulaEvaluator();

            // セルに記述された関数の実行・実行結果を格納するCellValueオブジェクトの取得
            CellValue resBefore = evaluator.evaluate(C1);

            // 実行結果の型に応じて値を取得
            // -> 型に応じた取得メソッドを使用しないとnullが返却される
            String resAfter = "";
            switch (resBefore.getCellType()) {
                case STRING: resAfter = resBefore.getStringValue(); break;
                case NUMERIC: resAfter = Double.toString(resBefore.getNumberValue()); break;
                case BOOLEAN: resAfter = Boolean.toString(resBefore.getBooleanValue()); break;
                default: break;
            }

            // 「取得した値」の出力
            System.out.println(B1);
            System.out.println("Before: " + C1);
            System.out.println("After: " + resAfter);
        } catch (Exception e) {
            System.out.println("Some exceptions has occurred in input from Excel File.");
        }
    }
}
