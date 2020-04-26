package com.mainview;

import javafx.scene.control.Label;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.Days;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class CustomerListController implements Initializable {
		
		String sql_all = "SELECT *"
          		+ " FROM Customer"
          		+ " INNER JOIN Reservation_Details"
          		+ " ON Customer.CustomerID = Reservation_Details.CustomerID"
          		+ " INNER JOIN Reserved_Room"
          		+ " ON Reservation_Details.ReservationID = Reserved_Room.ReservationID";
	
	//************* Other Variables********//
		@FXML private TextField NameTBox;
	    @FXML private TextField PhNoTBox;
	    @FXML private TextField RoomNoTBox;
    
	    @FXML private CheckBox check_BookedList;
	    @FXML private CheckBox check_OldList;
	    @FXML private CheckBox check_CurrentList;
	    @FXML private RadioButton select_all;
	    @FXML private Label username;
   //********************************************//

    
    
    //*************Table Column Variables *************//
		@FXML private TableView<Customer> list;
	    @FXML private TableColumn<?, ?> columnCustomerNo;
	    @FXML private TableColumn<?, ?> columnRoomNo;
	    @FXML private TableColumn<?, ?> columnCustomerName;
	    @FXML private TableColumn<?, ?> columnPhNo1;
	    @FXML private TableColumn<?, ?> columnReservationTime;
	    @FXML private TableColumn<?, ?> columnCheckInDate;
	    @FXML private TableColumn<?, ?> columnCheckOutDate;
	    @FXML private TableColumn<?, ?> columnStatus;
	    @FXML private TableColumn<?, ?> columnActualCheckOutDate;
	    @FXML private TableColumn<?, ?> columnActualCheckInDate;
    //*************************************************//
    
	    
	    
		int i=1;
		
	    public int totalAmount =0, extraBed, totalPeople, totalDays, days_difference;
	        
		public String []  datein;
		public String [] dateout;
		
		DateTime startDate, endDate;
			
		
		LocalDate formattedDateInTemp;
		String formattedDateIn;
		
		
		public static SimpleIntegerProperty dummyPh1;
		CustomerInfo_dialog_Controller CI_Dialog = new CustomerInfo_dialog_Controller();
		String name,phNo,roomNo,dateIn,dateOut,status,check;
		Customer selectedCustomer;
		public SimpleStringProperty temp = new SimpleStringProperty();
		public String getTemp() {
			return temp.get();
		}
		public void setTemp(String temp) {
			this.temp.set(temp);
		}
		
		
    //************* Context Menu *******************//
		ContextMenu contextMenu1 = new ContextMenu();
		ContextMenu contextMenu2 = new ContextMenu();
		MenuItem checkIn = new MenuItem("Check In");
		MenuItem checkOut = new MenuItem("Check Out");
		MenuItem cancel = new MenuItem("Cancel");
   //*************************************************//
		
		public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		public DateTimeFormatter formatterTemp = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    @FXML private ObservableList<Customer> Customerdata;
	    
	    
    
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
	    	LoginController.username1 = null;
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
    

	    
	    
	 //******************** Action Event ************************//
	    @FXML void Rbutton_select_all(ActionEvent event) {
	    	
	    	if(select_all.isSelected() == true) 
	    	{
				check_OldList.setSelected(true);
				check_CurrentList.setSelected(true);
				check_BookedList.setSelected(true);
	    	}
			else
			{
				check_OldList.setSelected(false);
				check_CurrentList.setSelected(false);
				check_BookedList.setSelected(false);
			}

	    }
	    @FXML void Search(ActionEvent event) {
	    	
	    	i = 1;
	    	list.getItems().clear();
	    	setCellTable();
			Customerdata = FXCollections.observableArrayList();
			
			
			
			if (check_OldList.isSelected()==true)
				addOldList();
			if (check_CurrentList.isSelected()==true)
				addCurrentList();
			if (check_BookedList.isSelected()==true)
				addBookedList();
			//System.out.println("sql_all = " + sql_all);
    }
	    @FXML void Reset(ActionEvent event) {
	    	Reset();
	    }
	 //********************************************************************//
	    
	    
 
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		username.setText(LoginController.username1);
		show_CInfoDialog();
		Reset();
		contextMenu1.getItems().addAll(checkOut);
		contextMenu1.setAutoHide(true);
		contextMenu2.getItems().addAll(checkIn, cancel);
		contextMenu2.setAutoHide(true);
		CMitems_function();
		
		check_BookedList.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent e) {
				if(check_BookedList.isSelected()==true)
				{
					if(check_OldList.isSelected()==true && check_CurrentList.isSelected()==true)
					{
						select_all.setSelected(true);
					}
				}
				else
				{
					select_all.setSelected(false);
				}
			}
		});
		
		check_OldList.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent e) {
				if(check_OldList.isSelected()==true)
				{
					if(check_BookedList.isSelected()==true && check_CurrentList.isSelected()==true)
					{
						select_all.setSelected(true);
					}
				}
				else
				{
					select_all.setSelected(false);
				}
			}	
		});
		
		check_CurrentList.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent e) {
				if(check_CurrentList.isSelected()==true)
				{
					if(check_OldList.isSelected()==true && check_BookedList.isSelected()==true)
					{
						select_all.setSelected(true);
					}
				}
				else
				{
					select_all.setSelected(false);
				}
			}	
		});
	}
	
	
	//********************** Other Methods *******************//  
	    private void setCellTable() {
			
	    	columnCustomerNo.setCellValueFactory(new PropertyValueFactory<>("customerNo"));
			columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
			columnReservationTime.setCellValueFactory(new PropertyValueFactory<>("reservedTime"));
			columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnPhNo1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber1"));
			columnCheckInDate.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
			columnCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("dateOut"));
			columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			columnActualCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("actualDateOut"));
			columnActualCheckInDate.setCellValueFactory(new PropertyValueFactory<>("actualDateIn"));
			
		}
	    private void loadData(String sql_input) {
			
			
			String sql = sql_input;
			name = "'%" + NameTBox.getText() + "%'";
			roomNo = "'%" + RoomNoTBox.getText() + "%'";
			phNo = "'%" + PhNoTBox.getText() + "%'";
			
			if (NameTBox.getText().trim().isEmpty()) {System.out.println("NameBox is empty [" + NameTBox.getText() + "]");}
			else
				sql = sql.concat(" AND CustomerName LIKE " + name);
			
			if (PhNoTBox.getText().trim().isEmpty()) {}
			else
				sql = sql.concat(" AND PhoneNumber1 LIKE " + phNo);
		
			if (RoomNoTBox.getText().trim().isEmpty()) {}
			else
				sql = sql.concat(" AND RoomNo LIKE " + roomNo);
			
			sql = sql.concat(" ORDER BY CustomerName");
			
		   	try(Connection c = SqliteConnection.Connector();
		   	PreparedStatement preparedStatement = c.prepareStatement(sql);
		   	ResultSet rs = preparedStatement.executeQuery();)
		   	{
		   		while(rs.next())
		   	    {
		   	    Customerdata.add(new Customer(i,
		   	    rs.getInt("RoomNo"),
		   	    rs.getString("ReservedTime"),
		   	    rs.getString("CustomerName"),
		   	    rs.getInt("PhoneNumber1"),
		   	    rs.getString("CheckInDate"),
		   	    rs.getString("CheckOutDate"),
		   	    rs.getString("ActualCheckOutDate"), 
		   	    rs.getString("ActualCheckInDate"),
		   	    rs.getString("Status")));
		   	    i++;
		   	    }
		   	}
		   	        
		   	catch (SQLException ex){
		   	ex.printStackTrace();
			}
		   	list.setItems(Customerdata);
		   	//System.out.println("sql = " + sql);
		
		}
		private void addOldList() {
			String sql_oldList = sql_all.concat(" WHERE Status='Checked Out'");
			loadData(sql_oldList);
		}
		private void addBookedList() {
			String sql_bookedList = sql_all.concat(" WHERE Status='Booked'");
			loadData(sql_bookedList);
		}
		private void addCurrentList() {
			String sql_currentList = sql_all.concat(" WHERE Status='Checked In'");
			loadData(sql_currentList);
		}
		private void Reset() {
			NameTBox.setText("");
	    	PhNoTBox.setText("");
	    	RoomNoTBox.setText("");
	    	
	    	check_OldList.setSelected(false);
	    	check_CurrentList.setSelected(false);
	    	check_BookedList.setSelected(false);
	    	
	    	i=1;
			setCellTable();
			Customerdata = FXCollections.observableArrayList();
			//loadData(sql_all);
			check = " ";
		}
		
		public void Update(){
			selectedCustomer = list.getSelectionModel().getSelectedItem();
			String sql = "UPDATE Reserved_Room ";
			if(check == "Check In")
				sql = sql.concat("SET Status='Checked In', ActualCheckInDate = '" + LocalDate.now().format(formatter) +"'");
			if(check == "Check Out") 
				sql = sql.concat("SET Status='Checked Out', ActualCheckOutDate = '" + LocalDate.now().format(formatter) +"'");
			if(check == "Cancel") 
				sql = sql.concat("SET Status='Cancelled' ");
			
			sql = sql.concat("WHERE RoomNo = ? AND CheckInDate = ? AND CheckOutDate = ?");
			Connection c = null;
			try{
				c = SqliteConnection.Connector();
			   	PreparedStatement pstmt = c.prepareStatement(sql);
			   	pstmt.setInt(1, selectedCustomer.getRoomNo());
	        	pstmt.setString(2, selectedCustomer.dateInTemp.format(formatter));
	        	pstmt.setString(3, selectedCustomer.dateOutTemp.format(formatter));
	        	pstmt.execute();
				}	
				   	        
			catch (SQLException ex)
			{
			ex.printStackTrace();
			}
			list.getItems().clear();
			if (check_OldList.isSelected()==true)
				addOldList();
			if (check_CurrentList.isSelected()==true)
				addCurrentList();
			if (check_BookedList.isSelected()==true)
				addBookedList();
			
	}
		
		private void CMvalidation(int x, int y) { //**********  ContextMenu Validation -> Check In , Check Out, Cancel ******* NSA//
    		
    		if(getTemp().equals("Checked In")) {
    			list.setContextMenu(contextMenu1);
    			//System.out.println("context menu1");
    			contextMenu1.show(list, x, y);
    			
    		} else if (getTemp().equals("Booked")) {
    			list.setContextMenu(contextMenu2);
    			//System.out.println("context menu2");
    			contextMenu2.show(list, x, y);
    		} else if(getTemp().equals("Checked Out")) {
    			
    		}
    		
		}
		
    	private void CMitems_function() { //******* Context Menu's Items and their action events ***** NSA// 
			
			list.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() 
			{
				@Override
				public void handle(MouseEvent mouseClick)
				{
					temp = list.getSelectionModel().getSelectedItem().status;
					//System.out.println(getTemp());
					if(mouseClick.getButton() == MouseButton.SECONDARY) {
						CMvalidation(locationX(), locationY());
					}
				}
			});
			
			checkIn.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent e) 
				{
					selectedCustomer = list.getSelectionModel().getSelectedItem();
					check = "Check In";
					Update();
					 
				 	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Information Dialog");
				    alert.setHeaderText(null);
				    alert.setContentText("Customer has been Checked In ");
				    alert.showAndWait();
				}
			});
			checkOut.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent e) 
				{
					selectedCustomer = list.getSelectionModel().getSelectedItem();
					dayDifference();
					
					check = "Check Out";
					Update();
					
					formattedDateInTemp = LocalDate.parse(selectedCustomer.getDateIn(), formatterTemp);
					formattedDateIn = formattedDateInTemp.format(formatter);
					
					String sql = "Select *" + 
							" From Reservation_Details" + 
							" INNER JOIN Reserved_Room" + 
							" On Reserved_Room.ReservationID = Reservation_Details.ReservationID" + 
							" INNER JOIN Customer" + 
							" On Reservation_Details.CustomerID = Customer.CustomerID" +
							" WHERE Customer.CustomerName = " + "'" + selectedCustomer.getName() + "'" + " AND Reserved_Room.RoomNo = " + "'" + selectedCustomer.getRoomNo() + "'" + " AND Reserved_Room.CheckInDate = " + "'" + formattedDateIn + "'";
					
					
					try(Connection c = SqliteConnection.Connector();
						   	PreparedStatement preparedStatement = c.prepareStatement(sql);
						   	ResultSet rs = preparedStatement.executeQuery();
						   	
					)
					{
					
						totalAmount = rs.getInt("TotalAmount");
						extraBed = rs.getInt("ExtraBedsTaken");
						totalPeople = rs.getInt("TotalPeople");
						
					}	
					catch (SQLException ex){
					   	ex.printStackTrace();
					}
					
				 	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Information Dialog");
				    alert.setHeaderText(null);
				    alert.setContentText("\nCUSTOMER HAS BEEN CHECKED OUT\n\nCustomer Name: " + selectedCustomer.getName()+ "\nRoom No: "+ selectedCustomer.roomNo.get() + "\nExtra Bed Taken: " + extraBed +
				    "\n\nCheckIn Date: " + selectedCustomer.dateIn.get() + "\nCheckOut Date: "+ selectedCustomer.dateOut.get() +
				    "\n\nTotal People: "+ totalPeople +"\nTotal Days: "+ days_difference +"\nTotal Amount: " + totalAmount +" MMK");  
				    alert.showAndWait();
				}
			});
			cancel.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent e) 
				{
					selectedCustomer = list.getSelectionModel().getSelectedItem();
					check = "Cancel";
					Update();
					
				 	Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Information Dialog");
				    alert.setHeaderText(null);
				    alert.setContentText("Booking has been cancelled ");
				    alert.showAndWait();
					
				}
			});
			
			
		}
    	
		public int locationX() {
			PointerInfo pointer = MouseInfo.getPointerInfo();
			Point point = pointer.getLocation();
			int x = (int) point.getX();
			return x;
		}
		
		public int locationY() {
			PointerInfo pointer = MouseInfo.getPointerInfo();
			Point point = pointer.getLocation();
			int y = (int) point.getY();
			return y;
		}
		private void show_CInfoDialog() {
			
			//show Customer information dialog 
			list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				
				if(event.getClickCount() >=2) { 
					
					 dummyPh1 = list.getSelectionModel().getSelectedItem().phoneNumber1;  //get selected item's phone number1
			    	 FXMLLoader Loader = new FXMLLoader();
					 Loader.setLocation(getClass().getResource("CustomerInfo_dialog.fxml"));
				
					 try { Loader.load();} 
					 catch(IOException ex) { Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE,null,ex);}
					 Parent p = Loader.getRoot();
					 Stage stage = new Stage();
					 stage.setScene(new Scene(p));
					 stage.setTitle("Customer Information ");
					 stage.show();  
					}	
				}
		  });
		}
		private void dayDifference() { // caluculate day difference between check in date and check out daye
		 	   datein = selectedCustomer.dateIn.get().split("-");
			   dateout= selectedCustomer.dateOut.get().split("-");
			   startDate =new DateTime(Integer.parseInt(datein[2]), Integer.parseInt(datein[1]), Integer.parseInt(datein[0]), 0, 0, 0, 0);
			   endDate = new DateTime(Integer.parseInt(dateout[2]), Integer.parseInt(dateout[1]), Integer.parseInt(dateout[0]), 0, 0, 0, 0);
			   days_difference = Days.daysBetween(startDate, endDate).getDays();
	}
			
		
   //*********************************************************************//


}
