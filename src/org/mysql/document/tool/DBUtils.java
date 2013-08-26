package org.mysql.document.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库操作
 * 
 * @author hxl
 * @date 2012-6-28上午10:21:35
 */
public final class DBUtils {

    private Parameters parameters = null;

    private Connection conn       = null;

    private Statement  stm        = null;

    private ResultSet  rs         = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");// 加载数据库驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBUtils(Parameters parameters){
        this.parameters = parameters;
    }

    public Map<String, HashMap<String, LinkedHashMap<String, String>>> getDatabaseInfo() throws Exception {
        // table_schema='数据库名'
        String sql = "select table_name , column_name ,  column_type , column_key , extra , is_nullable ,column_comment from columns where table_schema='"
                     + parameters.getDatabase() + "'";

        if (!parameters.getTable().equals("_NULL_")) {
            sql += " and table_name = '" + parameters.getTable() + "'";
        }
        // System.out.println(sql);

        conn = DriverManager.getConnection("jdbc:mysql://" + parameters.getHost() + ":" + parameters.getPort()
                                           + "/information_schema", parameters.getUser(), parameters.getPassword());
        stm = conn.createStatement();
        rs = stm.executeQuery(sql);

        Map<String, HashMap<String, LinkedHashMap<String, String>>> info = new HashMap<String, HashMap<String, LinkedHashMap<String, String>>>();

        while (rs.next()) {
            String table_name = rs.getString("table_name");
            HashMap<String, LinkedHashMap<String, String>> tablesMap = info.get(table_name);
            if (tablesMap == null) {
                tablesMap = new HashMap<String, LinkedHashMap<String, String>>();
            }
            String column_name = rs.getString("column_name");
            LinkedHashMap<String, String> columnInfo = tablesMap.get(column_name);
            if (columnInfo == null) columnInfo = new LinkedHashMap<String, String>();
            columnInfo.put("column_type", rs.getString("column_type"));
            String key = rs.getString("column_key");
            if ("PRI".equals(key)) {
                key = "是";
            }

            columnInfo.put("column_key", key);
            columnInfo.put("extra", rs.getString("extra"));
            columnInfo.put("is_nullable", rs.getString("is_nullable"));
            columnInfo.put("column_comment", rs.getString("column_comment"));

            tablesMap.put(column_name, columnInfo);
            info.put(table_name, tablesMap);

        }

        releaseDBResource();
        return info;
    }

    /**
     * 释放资源
     * 
     * @throws SQLException
     */
    public void releaseDBResource() throws SQLException {
        if (null != rs) {
            rs.close();
        }

        if (null != stm) {
            stm.close();
        }

        if (null != conn) {
            conn.close();
        }
    }
}
