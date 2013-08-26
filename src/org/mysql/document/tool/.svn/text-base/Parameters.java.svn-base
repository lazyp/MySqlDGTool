package org.mysql.document.tool;

import org.mysql.document.tool.util.StringUtils;

/**
 * 
 * @author hxl
 * @date 2012-6-24下午10:00:16
 */
public final class Parameters {
	private String host = "localhost";
	private String user;
	private String port = "3306";// 默认值
	private String password;
	private String database;
	private String table = "_NULL_";
	private String path = "";

	public Parameters() {
	}

	public Parameters(String host, String user, String port, String password,
			String database, String table, String path) {
		this.host = host;
		this.user = user;
		this.port = port;
		this.password = password;
		this.database = database;
		this.table = table;
		this.path = path;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (StringUtils.hasLength(host)) {
			this.host = host;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		if (StringUtils.hasLength(port)) {
			this.port = port;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		if (StringUtils.hasLength(table)) {
			this.table = table;
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		if (StringUtils.hasLength(path)) {
			this.path = path;
		}
	}

}
