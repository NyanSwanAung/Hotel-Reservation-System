package com.mainview;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.Days;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RoomsController extends LoginController implements Initializable {
			
	
	
	//************* Other Variables********//
		@FXML private ComboBox<String> RoomTypeCombo;
		@FXML private DatePicker DateInTBox;
	    @FXML private DatePicker DateOutTBox;
	    @FXML private Button buttonReset;
	    @FXML private Label username;

   //******************************************//
    
	    
	    
    
    //***********Table Column Variables*****************//
	    @FXML private TableView<Room> roomTable;
	    @FXML private TableColumn<?, ?> columnNo;
		@FXML private TableColumn<?, ?> columnRoomNo;
	    @FXML private TableColumn<?, ?> columnRoomType;
	    @FXML private TableColumn<?, ?> columnCost;
	    @FXML private TableColumn<?, ?> columnMaxPeople;
	    @FXML private TableColumn<?, ?> columnMaxBed;
	    @FXML private TableColumn<?, ?> columnAvailability;
	    
    //***************************************************//
	 
 
    @FXML private ObservableList<Room> data;
    
	    //*********************** Variables for Data Base******************//
	    public static int i;
	    int maxPeople, maxBed;
	    String availability="";
	    String sql_all = "Select *" + 
				"From Room_Type " + 
				"INNER JOIN Room " + 
				"On Room.RoomTypeID = Room_Type.RoomTypeID " + 
				"INNER JOIN Reserved_Room " + 
				"On Reserved_Room.RoomNo = Room.RoomNo " +
				"WHERE Reserved_Room.Status!='Checked Out'";
	    
	    String sql_add= "Select * From Room_Type " + 
	    		"INNER JOIN Room " + 
	    		"On Room.RoomTypeID = Room_Type.RoomTypeID";
	    //****************************************************************//
	    
		
		
		
	
    //*********************** Variables for Date ****************************//
		final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final DateTimeFormatter month_formatter= DateTimeFormatter.ofPattern("yyyy-MMMM-dd");
		
		LocalDate roomDateOut, roomDateIn;
		static String stringDateIn, stringDateOut;
		static String [] arrayDateIn;
		static String [] arrayDateOut;
		public static int days_difference;
		DateTime startDate, endDate;
		LocalDate dateIn, dateOut;
    //********************************************************************//    
    
		
		String chosenType;
		Room r = new Room();
		Customer c;
		
		
	
  
	 
	//*************************** Show Side Bar *************************//
	    @FXML private void goCustomerListPage(ActionEvent event) throws IOException {
			Parent home_page_parent = FXMLLoader.load(getClass().getResource("CustomerList.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
			
	    }
	    @FXML private void goReservationPage(ActionEvent event) throws IOException {
	    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("Reserve.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
	
	    }
	    @FXML private void goRoomsPage(ActionEvent event) throws IOException {
	    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("Rooms.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
	
	    }
	    @FXML private void goSetting(ActionEvent event) throws IOException {
			Parent home_page_parent = FXMLLoader.load(getClass().getResource("AccountSetting.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		
	    }
	    @FXML private void goSignOut(ActionEvent event) throws IOException {
	    	

	     	Alert alert = new Alert(AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText(null);
		    alert.setContentText("Are you sure you want to signout?");
			alert.showAndWait().ifPresent(type -> {
				
				if(type == ButtonType.CANCEL) {
					event.consume(); 
				} else if (type == ButtonType.OK) {
					Parent home_page_parent;
					try {
				    	LoginController.username1 = null;
						home_page_parent = FXMLLoader.load(getClass().getResource("Login.fxml") );
						Scene home_page_scene = new Scene (home_page_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						app_stage.setScene(home_page_scene);
						app_stage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
	
    }
    //*********************************************************************//
    
    
    
    
    //******************** Action Events ************************//
    	@FXML public void Search(ActionEvent event) throws SQLException {
		   
	    //Converting LocalDate   to a user specific format - dd/MM/yyyy
    		
    		
    	   stringDateIn=(DateInTBox.getValue()).format(formatter); 
    	   stringDateOut=(DateOutTBox.getValue()).format(formatter);
    
//    	   to calculate day difference
    	   arrayDateIn = stringDateIn.split("-");
   		   arrayDateOut = stringDateOut.split("-");
   		   startDate =new DateTime(Integer.parseInt(arrayDateIn[0]), Integer.parseInt(arrayDateIn[1]), Integer.parseInt(arrayDateIn[2]), 0, 0, 0, 0);
   		   endDate = new DateTime(Integer.parseInt(arrayDateOut[0]), Integer.parseInt(arrayDateOut[1]), Integer.parseInt(arrayDateOut[2]), 0, 0, 0, 0);
   		   days_difference = Days.daysBetween(startDate, endDate).getDays();
   		    
    	   
    	   dateIn = LocalDate.parse(stringDateIn, formatter);
    	   dateOut =  LocalDate.parse(stringDateOut, formatter);
    	   
    	   
    	   
    	   
		   i=1;
		   roomTable.getItems().clear();
		   
		   setCellTable();
		   data = FXCollections.observableArrayList();
		   dateValidation();
	   
    }
    //*********************************************************//	
    	
    
    
    	
      //*********************Initialize**********************//
    	@Override public void initialize(URL location, ResourceBundle resources) {
    		
    		
    		
    		username.setText(LoginController.username1);
	        DateInTBox.setValue(LocalDate.now());
	    	DateOutTBox.setValue(LocalDate.now());
			fillComboBox();
			setCellTable();
			
			
			
			//Pop up window appearing
			roomTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				
				if(event.getClickCount() >=2) { 
					
					if(roomTable.getSelectionModel().getSelectedItem().availability.getValue().toString() == "Available") {
						 FXMLLoader Loader = new FXMLLoader();
						 Loader.setLocation(getClass().getResource("PopUpRoom.fxml"));
					
						 try { Loader.load();} 
						 catch(IOException ex) { Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE,null,ex);}
						 Parent p = Loader.getRoot();
						 Stage stage = new Stage();
						 stage.setScene(new Scene(p));
						 stage.setTitle("Extra Information");
						 stage.show();  
						
						 try { fetchData();} 
					     catch (IOException e) { e.printStackTrace();}
						
					} else {
						Alert alert = new Alert(AlertType.WARNING);
					    alert.setTitle("Room Information");
					    alert.setHeaderText(null);
					    alert.setContentText("This room is unavailable");
					    alert.show();
						}
				}	
				
			}
		  });
    	}
	
	
	
	  //********************** Other Methods *******************//
	    private void setCellTable() {
			
			columnNo.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
			columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
			columnRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
			columnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
			columnMaxPeople.setCellValueFactory(new PropertyValueFactory<>("maximumPpl"));
			columnMaxBed.setCellValueFactory(new PropertyValueFactory<>("maximumEbed"));
			columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
			
		}
		private void loadData(String sql_input, String sql_input2) {
			
			String sql = sql_input; //sql_add
			String sql2 = sql_input2; // sql_all
			chosenType = RoomTypeCombo.getValue();
			int j = 0;
			int[] unavailableRoomNum = new int[50];
			if (chosenType != "Any")
			{
				sql = sql.concat(" AND RoomType = "+ "'" + chosenType + "'");
				sql2 = sql2.concat(" AND RoomType = "+ "'" + chosenType + "'");
			}
			sql2 = sql2.concat("GROUP BY Room.RoomNo ORDER BY Room_Type.RoomType , Reserved_Room.CheckOutDate DESC");

			
		   	try(Connection c = SqliteConnection.Connector();
		   	PreparedStatement preparedStatement = c.prepareStatement(sql);
		   	ResultSet rs = preparedStatement.executeQuery();
		   	
		   			
		   	PreparedStatement preparedStatement2 = c.prepareStatement(sql2);
			ResultSet rs2 = preparedStatement2.executeQuery();
		   	)
		   	
		   	
		   	{
		   		while(rs.next())
		   	    {
		   			data.add(new Room(i,
		   					rs.getInt("RoomNo"),
		   					rs.getString("RoomType"),
		   					rs.getInt("Cost"),
		   					rs.getInt("NumberOfBeds"),
		   					rs.getInt("AvailableExtraBeds"),
		   					"Available"));
		   			i++;
		   	    }
		   		while (rs2.next())
		   		{
		   			roomDateOut = LocalDate.parse(rs2.getString("CheckOutDate"), formatter);
		   			roomDateIn = LocalDate.parse(rs2.getString("CheckInDate"), formatter);
		   			if(roomDateOut.isBefore(dateOut) && roomDateOut.isAfter(dateIn) || roomDateIn.isEqual(dateIn) || roomDateIn.isBefore(dateOut) && roomDateIn.isAfter(dateIn)) {
		   				unavailableRoomNum[j] = rs2.getInt("RoomNo");
		   			}
		   			
		   			j++;
		   		}
		   		for(int k=0; k<=j;k++)
		   		{
		   			for(int l=0; l<i-1; l++)
		   			{
		   				if(unavailableRoomNum[k]== data.get(l).getRoomNo())
		   				{
		   					data.get(l).setAvailability("Not Available");
		   				}
		   			}
		   		}
		   	}
		   	catch (SQLException ex){
		   	ex.printStackTrace();
			}
		   		 
		   	     roomTable.setItems(data);
		}
		
	    public void fillComboBox() {
	    	RoomTypeCombo.getItems().add("Any");
	    	    String sql  = "SELECT RoomType from Room_Type";
		   	       try(Connection c = SqliteConnection.Connector();
		   	    	   PreparedStatement preparedStatement = c.prepareStatement(sql);
		   	    	   ResultSet rs = preparedStatement.executeQuery();)
		   	       {
		   	           while(rs.next())
		   	        	   
		   	           {
		   	        	 String s = rs.getString("RoomType");
		   	        	 RoomTypeCombo.getItems().add(s);
		   	           }
		   	       }
		   	        
		   	       catch (SQLException ex){
		   	    	   	 ex.printStackTrace();
			       }
		   	    RoomTypeCombo.setValue("Any");
	    }
	    public void fetchData() throws IOException {
	    	
	         r.staticCost = roomTable.getSelectionModel().getSelectedItem().cost;
	         r.staticRoomNo = roomTable.getSelectionModel().getSelectedItem().roomNo;
	         r.staticRoomType = roomTable.getSelectionModel().getSelectedItem().roomType;

	   }
	    
	    public void reset() {
	    	
	    	i=1;
	    	roomTable.getItems().clear();
	    	
	        DateInTBox.setValue(LocalDate.now());
	    	DateOutTBox.setValue(LocalDate.now());
	    	
	    	RoomTypeCombo.setValue("Any");
	    	
	    	stringDateIn=(DateInTBox.getValue()).format(formatter); 
	    	stringDateOut=(DateOutTBox.getValue()).format(formatter);
	    	   
	    	dateIn = LocalDate.parse(stringDateIn, formatter);
	    	dateOut =  LocalDate.parse(stringDateOut, formatter);
	    }
	    private void dateValidation() {
	    	if(DateOutTBox.getValue().isBefore(DateInTBox.getValue()) == true) {

		    	Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Calendar Date Alert");
			    alert.setHeaderText(null);
			    alert.setContentText("Check Out date should not be earlier than Check In Date. Please try again.");
			    alert.show();
	    	}
	    	else {
	    		 loadData(sql_add, sql_all);
	    	}
	    	
	    }
	    //*********************************************************//  
	
	   
	
}

