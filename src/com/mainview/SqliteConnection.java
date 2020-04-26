package com.mainview;
import java.sql.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class SqliteConnection extends Application {
	
public static Connection Connector() { 
		try {
		  Class.forName("org.sqlite.JDBC");
		  Connection conn = DriverManager.getConnection("jdbc:sqlite:HRS-OC.db");
		  return conn;
			} catch (Exception e) {
			System.out.println(e); return null;
			} 
	}	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
}