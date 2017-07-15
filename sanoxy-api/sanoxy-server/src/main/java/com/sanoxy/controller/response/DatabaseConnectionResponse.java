package com.sanoxy.controller.response;

import com.sanoxy.service.util.DatabaseConnection;

public class DatabaseConnectionResponse extends DatabaseConnection {
	
	public DatabaseConnectionResponse(Integer dbid) {
                super(dbid);
        }
}
