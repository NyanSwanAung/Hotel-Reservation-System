package com.mainview;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.joda.time.Days;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;


public class PopUpRoomController implements Initializable{
	
	
	
	//************* Other Variables********//
	    @FXML private Spinner<Integer> extraBedSpinner;
	    @FXML private Spinner<Integer> PersonSpinner;
	    @FXML private Button buttonCancel;
	    @FXML private Button buttonAdd;
	    static boolean flag = false;
	//******************************************//
	    
	    
	    
	    
	//***************Variables for Extra Bed Charges *****//
		static int totalCostForExtraBed = 0, totalCharges = 0;
	    String ExtraBedCost = "x 10,000";
	//***************************************************//
    
	   private String temp,a,b,convertedRoomType;
	   private int person,extraBed;
	   Calendar calendar = Calendar.getInstance();
	    
	    
    //******************** Action Events ***********************//
	    @FXML public void popUpCancel(ActionEvent event) {
	    	   Stage stage = (Stage) buttonCancel.getScene().getWindow();
	  	       stage.close();
	    }
	    @FXML public void popUpadd(ActionEvent event) throws SQLException, IOException {
       	
	    //SQLinsert sqlINSERT = new SQLinsert();
	
    	
    	Reservation.setExtraBed(extraBedSpinner.getValue());
    	Reservation.setPersonPerRoom(PersonSpinner.getValue());
    	
    	  if(RoomsController.days_difference == 0) {
		    	 RoomsController.days_difference = 1;
		     }

    	addTotalCharges();
    	addAllValues();
    	
    	
    	//************************ Inserting into DB **********************//
    	//sqlINSERT.insertPopUpValues(CustomerTable.getExtraBed(), CustomerTable.getPersonPerRoom());
 	    buttonAdd.getScene().getWindow().hide();
    	//*****************************************************************//
	    
	    
	    
	    
	    
	    //********************* Change Scene **********************//
		   Parent p = FXMLLoader.load(getClass().getResource("Reserve.fxml") );
		   Scene pop_up_scene= new Scene (p);
		   Main.primaryStage.setScene(pop_up_scene);
		
    }
    //******************** Action Events ***********************//    
	    
	    
	    
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			addSpinnerValues();
			
		}
		
		
		
		
		//********************* Getter & Setter Methods***************//
		 public  Spinner<Integer> getExtraBedSpinner() {
			return extraBedSpinner;
		}
		 public void setExtraBedSpinner(Spinner<Integer> extraBedSpinner) {
			this.extraBedSpinner = extraBedSpinner;
		}
		
		 public Spinner<Integer> getPersonSpinner() {
			return PersonSpinner;
		}
		 public void setPersonSpinner(Spinner<Integer> personSpinner) {
			this.PersonSpinner = personSpinner;
		}
	//********************* ************************s***************//
		 
		 
		 
		 
		 
    //********************** Other Methods *******************//  
		 private void addSpinnerValues() {
		
			//********************** Splitting simple string property into roomType *******************// 
			 Room r = new Room();
	         temp = r.staticRoomType.toString();
	         String [] Value = temp.split("e:");
	         b = Value[1];
	         String [] Value2 = b.split("]");
	         a = Value2[0];
	        
	         a = a.trim();
	         convertedRoomType = a.trim();
		     convertedRoomType = "\"" + a + "\"";
		     
		   //**********************  Getting extraBed and number of bed values from db *******************//  
	         String sql  = "SELECT NumberOfBeds,AvailableExtraBeds FROM Room_Type WHERE RoomType = " + convertedRoomType ;
	         try(Connection c = SqliteConnection.Connector();
  	    	 PreparedStatement preparedStatement = c.prepareStatement(sql);
  	    	 ResultSet rs = preparedStatement.executeQuery();)
  	       {
  	           while(rs.next())
  	           {
  	        	  person = rs.getInt("NumberOfBeds");
  	        	  extraBed = rs.getInt("AvailableExtraBeds");
  	           }
  	           
	         extraBedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, extraBed));
	         PersonSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, person + extraBed));
  	       }
  	        
  	       catch (SQLException ex){
  	    	   	 ex.printStackTrace();
	       }
		  //************************************************************************************************//  
	         
	         
	 }
		 public void addTotalCharges() {
			 
			 totalCostForExtraBed = extraBedSpinner.getValue() * 10000;
			 totalCharges = totalCostForExtraBed + (Room.getStaticCost() * RoomsController.days_difference);
			 
		 }

		 public void addAllValues() {
			 
			 //converting 45000 to "x 45,000"
			 int q = Room.getStaticCost();
			 NumberFormat myFormat = NumberFormat.getInstance();
		     myFormat.setGroupingUsed(true);
		     String costPerday = "x " + myFormat.format(q);
		     System.out.println(costPerday);
		     
		   
			ReserveController.Reservedata.add(new Room_Reservation(Room.getStaticRoomNo(), Room.getStaticRoomType(), RoomsController.days_difference, costPerday, extraBedSpinner.getValue(),PersonSpinner.getValue(),
					ExtraBedCost, RoomsController.stringDateIn, RoomsController.stringDateOut, totalCharges ));
			 
		 }
   //*********************************************************// 

		 
}
