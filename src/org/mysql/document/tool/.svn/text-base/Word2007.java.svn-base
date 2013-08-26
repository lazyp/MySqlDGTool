package org.mysql.document.tool;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

/**
 * word操作
 * 
 * @author hxl
 * @date 2012-6-27下午10:26:12
 */
public final class Word2007 {

    /**
     * 生成word，表格
     * 
     * @param data
     * @throws Exception
     */
    public static void productWordForm(Map<String, HashMap<String, LinkedHashMap<String, String>>> data,
                                       Parameters parameters) throws Exception {
        XWPFDocument xDocument = new XWPFDocument();

        Iterator<String> tableNameIter = data.keySet().iterator();
        while (tableNameIter.hasNext()) {
            String table_name = tableNameIter.next();
            XWPFParagraph xp = xDocument.createParagraph();
            XWPFRun r1 = xp.createRun();
            r1.setText(table_name);
            r1.setFontSize(18);
            r1.setTextPosition(10);

            HashMap<String, LinkedHashMap<String, String>> columns = data.get(table_name);
            int rows = columns.size();
            XWPFTable xTable = xDocument.createTable(rows + 2, 6);
            // xTable.setWidth(1024);
            int i = 0;

            xTable.getRow(i).getCell(0).setText("列名");
            xTable.getRow(i).getCell(1).setText("类型");
            xTable.getRow(i).getCell(2).setText("主键");
            xTable.getRow(i).getCell(3).setText("注明");
            xTable.getRow(i).getCell(4).setText("是否可以为null");
            xTable.getRow(i).getCell(5).setText("说明");

            i = i + 2;// 下一行
            int j = 0;// 列column索引

            Map<String, LinkedHashMap<String, String>> keyColumnMap = keyColumns(columns);
            for (Iterator<String> columnNameIter = keyColumnMap.keySet().iterator(); columnNameIter.hasNext();) {
                String column_name = columnNameIter.next();
                LinkedHashMap<String, String> columnsAtt = keyColumnMap.get(column_name);

                xTable.getRow(i).getCell(j).setText(column_name);
                ++j;

                Iterator<String> columnTypeIter = columnsAtt.keySet().iterator();

                while (columnTypeIter.hasNext()) {
                    String colum_type = columnTypeIter.next();
                    xTable.getRow(i).getCell(j).setText(columnsAtt.get(colum_type));
                    j++;
                }

                ++i;// 下一行
                j = 0;// 恢复第一列
            }

            Iterator<String> cloumnsNameIter = columns.keySet().iterator();

            while (cloumnsNameIter.hasNext()) {
                String colum_name = cloumnsNameIter.next();
                LinkedHashMap<String, String> columnsAtt = columns.get(colum_name);
                if (xTable.getRow(i) == null) continue;
                xTable.getRow(i).getCell(j).setText(colum_name);

                j++;

                Iterator<String> columnTypeIter = columnsAtt.keySet().iterator();

                while (columnTypeIter.hasNext()) {
                    String colum_type = columnTypeIter.next();

                    xTable.getRow(i).getCell(j).setText(columnsAtt.get(colum_type));
                    j++;
                }
                j = 0;// 恢复第一列
                ++i; //下一行
            }
        }

        FileOutputStream fos = new FileOutputStream(parameters.getPath() + parameters.getDatabase() + "_doc.docx");

        xDocument.write(fos);
        fos.close();
    }

    /**
     * 检索出主键key相关的列
     * 
     * @param columnsMap
     * @return
     */
    private static Map<String, LinkedHashMap<String, String>> keyColumns(HashMap<String, LinkedHashMap<String, String>> columnsMap) {
        Map<String, LinkedHashMap<String, String>> keyColumnMap = new HashMap<String, LinkedHashMap<String, String>>();

        Iterator<String> cloumnsNameIter = columnsMap.keySet().iterator();
        while (cloumnsNameIter.hasNext()) {
            String colum_name = cloumnsNameIter.next();
            LinkedHashMap<String, String> columnsAtt = columnsMap.get(colum_name);
            if (columnsAtt.get("column_key").equals("是")) {
                keyColumnMap.put(colum_name, columnsAtt);
                cloumnsNameIter.remove();
            }
        }

        return keyColumnMap;
    }
}
