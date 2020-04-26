package com.mainview;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SQLinsert{
	
	static int condition; ////PTK/////
	private static Connection connect() {
		
        String url = "jdbc:sqlite:HRS-OC.db";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
	public void insertRoomInfo( int RID ,Integer extraBed, Integer person, int roomNo, String dateIN, String dateOUT, String status) throws SQLException {
	    
	    String insertRoomQuery = "INSERT INTO Reserved_Room(RoomNo, ExtraBedsTaken, Status, CheckInDate, CheckOutDate, NoOfPeople, ReservationID) VALUES(?,?,?,?,?,?,?)";
        
        try (Connection conn = this.connect();
             PreparedStatement pstmt2 = conn.prepareStatement(insertRoomQuery);)
            {	
        	
        		pstmt2.setInt(1, roomNo);
        		pstmt2.setInt(2, extraBed);
        		pstmt2.setString(3, status);
	        	pstmt2.setString(4, dateIN);
		        pstmt2.setString(5, dateOUT);
		        pstmt2.setInt(6, person);
		        pstmt2.setInt(7, RID);
		        
                pstmt2.execute();
            } 
        catch (SQLException e) { System.out.println(e.getMessage());}
	}    
	public void insertCID (int CID, String reservedTime, int totalPeople, int totalAmount) {
		
		   String insertIDQuery = "INSERT INTO Reservation_Details (CustomerID, ReservedTime, TotalPeople, TotalAmount) VALUES(?,?,?,?);";
		    
	        try (Connection conn = this.connect();
	            PreparedStatement pstmt = conn.prepareStatement(insertIDQuery);)
	        {
		  	        pstmt.setInt(1, CID);
		  	        pstmt.setString(2, reservedTime);
		  	        pstmt.setInt(3, totalPeople);
		  	        pstmt.setInt(4, totalAmount);
	                pstmt.execute();
	                
	                
	        } catch (SQLException e) { System.out.println(e.getMessage()); }
	}
	public void insertRID (int RID, int roomNO) {
		
		   String insertIDQuery = "UPDATE Reserved_Room SET ReservationID = ? WHERE	RoomNo = ?";
		    
	        try (Connection conn = this.connect();
	            PreparedStatement pstmt = conn.prepareStatement(insertIDQuery);)
	        {
		  	        pstmt.setInt(1, RID);
		  	        pstmt.setInt(2, roomNO);
	                pstmt.execute();
	                
	                System.out.println("RID added");
	                
	        } catch (SQLException e) { System.out.println(e.getMessage()); }
		
	}
	public void insertCInfo(String name, String NRC, String Ph1, String Ph2, String Email ) {
		
		String insertCustomerQuery = "INSERT INTO Customer(CustomerName,NRC,PhoneNumber1,PhoneNumber2,Email) VALUES(?,?,?,?,?)";
		
	    
        try (Connection conn = this.connect();
            PreparedStatement pstmt1 = conn.prepareStatement(insertCustomerQuery);)
        {
	  	        pstmt1.setString(1, name);
	  	        pstmt1.setString(2, NRC);
	  	        pstmt1.setString(3, Ph1);
	  	        pstmt1.setString(4, Ph2);
	  	        pstmt1.setString(5, Email);
                pstmt1.execute();
                
                
        } catch (SQLException e) { System.out.println(e.getMessage()); }
		
		
	}
	////////// PTK ////////////
	public void checkOldPass(String olpass) {
		try (Connection conn = this.connect();) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from User");

			String oPass;

			while (rs.next()) {
				oPass = rs.getString("password");
				if (oPass.equals(olpass)) {
					condition = 1;
				} else
					condition = 0;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateAcc(String uplAcc) {
		try (Connection conn = this.connect();) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from User");
			String sql = "Update User set password='" + uplAcc + "' where username='novotel@user1'";
			int success = st.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	//////////PTK ////////////
	public static void main(String[] args) throws SQLException {
	    	connect();
	    }

	}


        

	