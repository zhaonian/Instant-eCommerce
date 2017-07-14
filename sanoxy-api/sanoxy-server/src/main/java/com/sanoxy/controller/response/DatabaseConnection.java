package com.sanoxy.controller.response;

public class DatabaseConnection {
	
	private String dbName;
	
	public DatabaseConnection() { };
	
	public DatabaseConnection(String dbName) {
		this.dbName = dbName;
	}
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
