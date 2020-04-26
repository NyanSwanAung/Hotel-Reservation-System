package com.mainview;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class CustomerInfo_dialog_Controller implements Initializable {


    @FXML private Label dialog_Name;
    @FXML private TextField dialog_ID;
    @FXML private TextField dialog_NRC;
    @FXML private TextField dialog_Phone1;
    @FXML private TextField dialog_Phone2;
    @FXML private TextField dialog_Gmail;
    
    
    public static SimpleIntegerProperty staticD_Id = new SimpleIntegerProperty();
    public static SimpleStringProperty staticD_Nrc = new SimpleStringProperty();
    public static SimpleIntegerProperty staticD_Phone1 = new SimpleIntegerProperty();
    public static SimpleIntegerProperty staticD_Phone2 = new SimpleIntegerProperty();
    public static SimpleStringProperty staticD_Gmail = new SimpleStringProperty();
    
    public int CID;
    public String CNrc,CName,CPh1,CPh2,CEmail;
    
    
   
     public CustomerInfo_dialog_Controller(int id, String nrc, int ph1, int ph2, String gmail ) {
    	 staticD_Id.set(id);
		 staticD_Nrc.set(nrc);
		 staticD_Phone1.set(ph1);
		 staticD_Phone2.set(ph2);
		 staticD_Gmail.set(gmail);
	}
     public CustomerInfo_dialog_Controller() {}
    
     
	public static SimpleIntegerProperty getStaticD_Id() {
		return staticD_Id;
	}
	public static void setStaticD_Id(SimpleIntegerProperty staticD_Id) {
		CustomerInfo_dialog_Controller.staticD_Id = staticD_Id;
	}
	
	public static SimpleStringProperty getStaticD_Nrc() {
		return staticD_Nrc;
	}
	public static void setStaticD_Nrc(SimpleStringProperty staticD_Nrc) {
		CustomerInfo_dialog_Controller.staticD_Nrc = staticD_Nrc;
	}

	public static SimpleIntegerProperty getStaticD_Phone1() {
		return staticD_Phone1;
	}
	public static void setStaticD_Phone1(SimpleIntegerProperty staticD_Phone1) {
		CustomerInfo_dialog_Controller.staticD_Phone1 = staticD_Phone1;
	}

	public static SimpleIntegerProperty getStaticD_Phone2() {
		return staticD_Phone2;
	}
	public static void setStaticD_Phone2(SimpleIntegerProperty staticD_Phone2) {
		CustomerInfo_dialog_Controller.staticD_Phone2 = staticD_Phone2;
	}

	public static SimpleStringProperty getStaticD_Gmail() {
		return staticD_Gmail;
	}
	public static void setStaticD_Gmail(SimpleStringProperty staticD_Gmail) {
		CustomerInfo_dialog_Controller.staticD_Gmail = staticD_Gmail;
	}

	@Override public void initialize(URL location, ResourceBundle resources) {
		loadCinfo();
		setTextFields();
	}
	
	
	private void setTextFields() {
		dialog_Name.setText(CName);
		dialog_ID.setText(""+ CID);
		dialog_NRC.setText(CNrc);
		dialog_Phone1.setText(CPh1);
		dialog_Phone2.setText(CPh2);
		dialog_Gmail.setText(CEmail);
	}

	private void loadCinfo() {
		String sql = "SELECT * FROM Customer WHERE PhoneNumber1 = \"" + CustomerListController.dummyPh1.getValue().intValue()+ "\" "; 
	   	try(Connection c = SqliteConnection.Connector();
	   	PreparedStatement preparedStatement = c.prepareStatement(sql);
	   	ResultSet rs = preparedStatement.executeQuery(); )
	   	{
	   		while(rs.next())
	   	    {
	   			CID = rs.getInt("CustomerID");
	   			CNrc = rs.getString("NRC");
	   			CName = rs.getString("CustomerName");
	   			CPh1 = rs.getString("PhoneNumber1");
	   			CPh2 = rs.getString("PhoneNumber2");
	   			CEmail = rs.getString("Email");
	   	    }
	   		
	   	}
	   	catch (SQLException ex){ ex.printStackTrace(); }
		
	}
	
	
}