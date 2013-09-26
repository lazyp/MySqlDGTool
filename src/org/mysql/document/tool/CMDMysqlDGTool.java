package org.mysql.document.tool;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.mysql.document.tool.util.CMDHelper;

/**
 * <code>
 * mysql 文档生成工具
 * 命令行主类
 * </code>
 * 
 * @author hxl
 * @date 2012-6-14上午9:43:44
 */
public final class CMDMysqlDGTool {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Input Command:");
        String cmd = sc.nextLine();

        Parameters parameters = CMDHelper.parseCommand(cmd);

        if (parameters == null) {
            System.err.println("parameter parse exception.");
            System.exit(-1);
        }

        DBUtils dbUtils = new DBUtils(parameters);

        long startTime = System.currentTimeMillis();

        Map<String, HashMap<String, LinkedHashMap<String, String>>> data = dbUtils.getDatabaseInfo();

        Word2007.productWordForm(data, parameters);

        long endTime = System.currentTimeMillis();
        System.out.println("总共用时:" + (endTime - startTime) + "ms");

    }

}
