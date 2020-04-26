
package com.mainview;
	
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.management.modelmbean.RequiredModelMBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

	public class ReserveController implements Initializable {
		

	//************* FXML Variables********//
	    @FXML private TextField cName;
		@FXML private TextField cNRC;
	    @FXML private TextField cPhNo1;
	    @FXML private TextField cPhNo2;
	    @FXML private TextField cEmail;
	    @FXML private CheckBox ImmediateCheckInBtn;
	    
	    @FXML private Label vName;
	    @FXML private Label vNRC;
	    @FXML private Label vPhNo1;
	    @FXML private Label vPhNo2;
	    @FXML private Label username;
	    
  //***************Other Variables*****************//
		    
	    int selectedIndex, rowCount;
	    public  int customerIDIndex, roomIDIndex;
	    public int totalPeople = 0;
	    public int totalAmount = 0;
	    public String formattedTime;
	    
		SQLinsert sqlInsert = new SQLinsert();
		Reservation customerList = new Reservation();
		
		String status="";
		int flag;

    //************ Table Column Variables ************//
	    @FXML private TableView<Room_Reservation> reserveList;
	    @FXML private TableColumn<?, ?> columnRoomNo;
	    @FXML private TableColumn<?, ?> columnRoomType;
	    @FXML private TableColumn<?, ?> columnDays;
	    @FXML private TableColumn<?, ?> columnCost;
	    @FXML private TableColumn<?, ?> columnExtraBed;
	    @FXML private TableColumn<?, ?> columnBedValue;
	    @FXML private TableColumn<?, ?> columnPeople;
	    @FXML private TableColumn<?, ?> columnCheckInDate;
	    @FXML private TableColumn<?, ?> columnCheckOutDate;
	    @FXML private TableColumn<?, ?> columnTotal;
  //***************************************************//

		    
		    
	    
		@FXML public static ObservableList<Room_Reservation> Reservedata =  FXCollections.observableArrayList();
	    
		 
		//*************************** Show Side Bar *************************//
		@FXML private void goCustomerListPage(ActionEvent event) throws IOException {
			setCData();
			Parent home_page_parent = FXMLLoader.load(getClass().getResource("CustomerList.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		 }
	    @FXML private void goReservationPage(ActionEvent event) throws IOException {
	    	setCData();
	    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("Reserve.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
	    }
	    @FXML private void goRoomsPage(ActionEvent event) throws IOException {
	    	setCData();
	    	System.out.println("data set in room page");
	    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("Rooms.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();
	    }
	    @FXML private void goSetting(ActionEvent event) throws IOException {
	    	setCData();
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
						setCData();
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
	  
	    
	    
    //************************** Action Events ***************************//
		@FXML private void confirm_db(ActionEvent event) throws SQLException {
			
			//***********************Validation*************************//
 
			
			if(cName.getText() == null || cNRC.getText() == null || (cPhNo1.getText() == null && cPhNo2.getText() == null)) {
				Validation2();
			}
			
			else if(cName.getText().isEmpty() || cNRC.getText().isEmpty() || (cPhNo1.getText().isEmpty() && cPhNo2.getText().isEmpty()) || Reservedata.isEmpty()) {
				Validation();
			} 
		
			else {
			//**********Receiving data from UI and Inserting into DB********//
            customerList.setcName(cName.getText());
			customerList.setcNRC(cNRC.getText());
			customerList.setcPhNo1(cPhNo1.getText());
			customerList.setcPhNo2(cPhNo2.getText());
			customerList.setcEmail(cEmail.getText());
			

			
			Alert alert = new Alert(AlertType.CONFIRMATION);
		    alert.setTitle("Confirmation Dialog");
		    alert.setHeaderText(null);
		    alert.setContentText("Are you sure you want to add this customer? ");
			alert.showAndWait().ifPresent(type -> {
				
				if(type == ButtonType.CANCEL) {
					event.consume(); 
				} 
				
				else if(type == ButtonType.OK) {
					
					sqlInsert.insertCInfo(customerList.getcName(), customerList.getNRC(), customerList.getcPhNo1(), customerList.getcPhNo2(), customerList.getcEmail() );
			    	try {
						getAllRooms();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			    	
				   	Alert infoAlert = new Alert(AlertType.INFORMATION);
				    infoAlert.setTitle("Information Dialog");
				    infoAlert.setHeaderText(null);
				    infoAlert.setContentText("Customer has been added ");
				    infoAlert.showAndWait();
			    	clearData();
				}
				
			});
		}
}
	    @FXML private void addRoom(ActionEvent event) throws IOException {
	    	setCData();
	    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("Rooms.fxml") );
			Scene home_page_scene = new Scene (home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(home_page_scene);
			app_stage.show();

	    }
	    @FXML public void cancelRoom(ActionEvent event) {
	    	selectedIndex = reserveList.getSelectionModel().getSelectedIndex();
	    	reserveList.getItems().remove(reserveList.getSelectionModel().getSelectedItem());
	    	if (selectedIndex >= 0)
	    		Reservedata.remove(selectedIndex);
	    	if (selectedIndex < 0)
	    		Reservedata.clear();
	    }
    //********************************************************************//

	    
	    
	    
   //********************************************************************//
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			username.setText(LoginController.username1);
			setTBox();
			reserveList.setEditable(false);
			setReserveCellTable();
			loadRoomData();
			rowCount = reserveList.getItems().size(); // size = 8;
			getCurrentTime();
			
		
	}		
   //********************************************************************//
			
		
		
		
		//**********************Other Methods*******************//
		private void setReserveCellTable() {
			
			columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("RoomNo"));
			columnRoomType.setCellValueFactory(new PropertyValueFactory<>("RoomType"));
			columnDays.setCellValueFactory(new PropertyValueFactory<>("Days"));
			columnCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
			columnExtraBed.setCellValueFactory(new PropertyValueFactory<>("ExtraBed"));
			columnBedValue.setCellValueFactory(new PropertyValueFactory<>("BedValue"));
			columnPeople.setCellValueFactory(new PropertyValueFactory<>("NoOfPeople"));
			columnCheckInDate.setCellValueFactory(new PropertyValueFactory<>("DateIn"));
			columnCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("DateOut"));
			columnTotal.setCellValueFactory(new PropertyValueFactory<>("TotalCharges"));
		}
		private void loadRoomData() {
			reserveList.getItems().clear();
			reserveList.getItems().addAll(Reservedata);			   	
			}
	    private void loadCID() {
	    	
	    	String CID_query = "SELECT CustomerID " + 
	    					   " FROM  Customer " + 
	    					   " WHERE CustomerID = (SELECT MAX(CustomerID) FROM Customer);";
	    	
	    	try(Connection conn = SqliteConnection.Connector();
	    		PreparedStatement pstmt = conn.prepareStatement(CID_query);
	    		ResultSet rs = pstmt.executeQuery();) {
	    		
	    		customerIDIndex = rs.getInt("CustomerID");
	    		System.out.println("CustomerID" + customerIDIndex);
	    		
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    private void loadRID() {
	    	
	    	String RID_query = " SELECT ReservationID FROM Reservation_Details WHERE ReservationID = (SELECT MAX(ReservationID)  FROM Reservation_Details); ";
		
	    	
	    	try(Connection conn = SqliteConnection.Connector();
	    		PreparedStatement pstmt = conn.prepareStatement(RID_query);
	    		ResultSet rs = pstmt.executeQuery();) {
	    		
	    		roomIDIndex = rs.getInt("ReservationID");
		    		
		    	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		    	}
	    }	
	    private void getCurrentTime() {
	    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");  
	    	   LocalDateTime now = LocalDateTime.now();
	    	   formattedTime = dtf.format(now);
	    }
	    private void getAllRooms() throws SQLException {
	    	
	    	if(ImmediateCheckInBtn.isSelected()==true)
	    		status = "Checked In";
	    	
	    	else
	    		status = "Booked";
	    	
	    	if(rowCount > 1) {
	    		
	    		loadCID();
	    		
	    		for(int a=0; a<= rowCount-1; a++) {
	    			totalPeople += Reservedata.get(a).getNoOfPeople();
	    			totalAmount += Reservedata.get(a).getTotalCharges();
	    		}
    		    sqlInsert.insertCID(customerIDIndex, formattedTime, totalPeople, totalAmount);
    			loadRID();
    			insertRoomInfo2();
        
	    	} else if ( (rowCount -1) == 0)  { 
	    				
	    			Room_Reservation rt2 = reserveList.getItems().get(rowCount-1);
	    			
	    			totalPeople = rt2.getNoOfPeople();
	    			totalAmount = rt2.getTotalCharges();
	    			Reservedata.get(0).setRoomNo(rt2.getRoomNo());
	    			Reservedata.get(0).setExtraBed(rt2.getExtraBed());
	    			Reservedata.get(0).setDateIn(rt2.getDateIn());
	    			Reservedata.get(0).setDateOut(rt2.getDateOut());
	    			Reservedata.get(0).setNoOfPeople(rt2.getNoOfPeople());
	    			
	    			loadCID();
	    			sqlInsert.insertCID(customerIDIndex, formattedTime, totalPeople, totalAmount);
	    			loadRID();
	    	    	sqlInsert.insertRoomInfo(roomIDIndex, rt2.getExtraBed(), rt2.getNoOfPeople(), rt2.getRoomNo(), rt2.getDateIn(), rt2.getDateOut(), status);
	    }
	}
		private void insertRoomInfo2() {
			
			  String insertRoomQuery = "INSERT INTO Reserved_Room(RoomNo, ExtraBedsTaken, CheckInDate, CheckOutDate, NoOfPeople, Status, ReservationID) VALUES(?,?,?,?,?,?,?)";
		        
			  
		        try (Connection conn =  SqliteConnection.Connector();
		             PreparedStatement pstmt2 = conn.prepareStatement(insertRoomQuery);)
		            {	
		        		for(int a =0; a <= rowCount-1; a++) {
		        			
		        			pstmt2.setInt(1, Reservedata.get(a).getRoomNo());
		        			pstmt2.setInt(2, Reservedata.get(a).getExtraBed());
		        			pstmt2.setString(3, Reservedata.get(a).getDateIn());
		        			pstmt2.setString(4, Reservedata.get(a).getDateOut());
		        			pstmt2.setInt(5, Reservedata.get(a).getNoOfPeople());
		        			pstmt2.setString(6, status);
		        			pstmt2.setInt(7, roomIDIndex);
		        			pstmt2.execute();
		        		}
		            } catch (SQLException e) { System.out.println(e.getMessage());}
		}
		private void setCData() {
			
			Reservation.customerList.setcName(cName.getText());
			Reservation.customerList.setcNRC(cNRC.getText());
			Reservation.customerList.setcPhNo1(cPhNo1.getText());
			Reservation.customerList.setcPhNo2(cPhNo2.getText());
			Reservation.customerList.setcEmail(cEmail.getText());
		
		}
		private void setTBox() {
			
			cName.setText(Reservation.customerList.getcName());
			cNRC.setText(Reservation.customerList.getcNRC());
			cPhNo1.setText(Reservation.customerList.getcPhNo1());
			cPhNo2.setText(Reservation.customerList.getcPhNo2());
			cEmail.setText(Reservation.customerList.getcEmail());
		}
		private void Validation() {
			
			flag = 0;
			vName.setText(" ");
			vNRC.setText(" ");
			vPhNo1.setText(" ");
			
			if(cName.getText().isEmpty()) {
				vName.setText("Enter Name!");
			} 
			
			if (cNRC.getText().isEmpty()) {
				vNRC.setText("Enter NRC!");
			}
			
			if (cPhNo1.getText().isEmpty() && cPhNo2.getText().isEmpty() ) {
				vPhNo1.setText("Enter Phone Number1!");
			} 
			
			if( Reservedata.isEmpty() &&  cName.getText().isEmpty() && cNRC.getText().isEmpty() && (cPhNo1.getText().isEmpty() || cPhNo2.getText().isEmpty()) ) {
				
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Information Dialog");
			    alert.setHeaderText(null);
			    alert.setContentText("Please Enter Customer and Room Information!");
			    alert.showAndWait();
			    flag = 1;
			}
			
			if(Reservedata.isEmpty() && flag == 0) {
				
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Information Dialog");
			    alert.setHeaderText(null);
			    alert.setContentText("Please Enter Room Information!");
			    alert.showAndWait();
			}
			
			
			
		}
		
		private void Validation2() {
			
			flag = 0;
			vName.setText(" ");
			vNRC.setText(" ");
			vPhNo1.setText(" ");
			
			if(cName.getText() == null) {
				vName.setText("Enter Name!");
			} 
			
			if (cNRC.getText() == null) {
				vNRC.setText("Enter NRC!");
			}
			
			if (cPhNo1.getText() == null && cPhNo2.getText() == null ) {
				vPhNo1.setText("Enter Phone Number1!");
			} 
			
			if( Reservedata.isEmpty() &&  cName.getText() == null && cNRC.getText() == null && (cPhNo1.getText() == null || cPhNo2.getText() == null ) ) {
				
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Information Dialog");
			    alert.setHeaderText(null);
			    alert.setContentText("Please Enter Customer and Room Information!");
			    alert.showAndWait();
			    flag = 1;
			}
			
			if(Reservedata.isEmpty() && flag == 0) {
				
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Information Dialog");
			    alert.setHeaderText(null);
			    alert.setContentText("Please Enter Room Information!");
			    alert.showAndWait();
			}
			
			
			
		}
		private void clearData() {

			
			cName.setText(null);
			cNRC.setText(null);
			cPhNo1.setText(null);
			cPhNo2.setText(null);
			cEmail.setText(null);
			
			
			vName.setText(" ");
			vNRC.setText(" ");
			vPhNo1.setText(" ");
			vPhNo2.setText(" ");
			
			reserveList.getItems().clear();
			Reservedata.clear();
			ImmediateCheckInBtn.setSelected(false);
			
		}
		//********************************************************//
}
	

