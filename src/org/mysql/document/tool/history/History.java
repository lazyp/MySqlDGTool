package org.mysql.document.tool.history;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.mysql.document.tool.Parameters;
import org.mysql.document.tool.util.FileUtils;

/**
 * 密码
 * 
 * @author hxl
 * @version 2012-07-13
 * 
 */
public final class History {
	private static final String HISTORY_FILE_NAME= "history";
	/**
	 * user--password映射历史map
	 */
	private static final Map<String, Parameters> historyMap = new HashMap<String, Parameters>();

	/**
	 * 初始化历史
	 */
	static {
		InputStream inputStream = History.class.getResourceAsStream(HISTORY_FILE_NAME);
		BufferedReader br = FileUtils.getBufferedReader(inputStream);
		String line = null;
		try {
			System.out.println("开始初始化历史列表");
			while ((line = br.readLine()) != null) {
				String[] args = line.split(":");
				if (args.length == 8) {
					/*
					 * String host, String user, String port, String password,
					 * String database, String table, String path
					 * 
					 * mark:localhost:3306:root:123:database:table:path
					 */
					Parameters parameters = new Parameters(args[1], args[3],
							args[2], args[4], args[5], args[6], args[7]);
					historyMap.put(args[0], parameters);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("初始化历史列表结束");
	}

	/**
	 * 检索历史
	 * 
	 * @param markName
	 * @return
	 */
	public static Parameters retrieveHistory(String markName) {
		return historyMap.get(markName);
	}

	/**
	 * 返回历史类表
	 * 
	 * @return
	 */
	public static Map<String, Parameters> getHistoryMap() {
		return historyMap;
	}

	public static void saveHistory(String markName, Parameters parameters) {
		historyMap.put(markName, parameters);
		String history = markName + ":" + parameters.getHost() + ":"
				+ parameters.getPort() + ":" + parameters.getUser() + ":"
				+ parameters.getPassword() + ":" + parameters.getDatabase()
				+ ":" + parameters.getTable() + ":" + parameters.getPassword();
		BufferedWriter bw = null;
		try {
		//	System.out.println(History.class.getResource(HISTORY_FILE_NAME).getPath());
			bw = FileUtils.getBufferedWriter(FileUtils.getFileOutputStream(
					History.class.getResource(HISTORY_FILE_NAME).getPath(), true));
			bw.write(history);
			bw.write("\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
