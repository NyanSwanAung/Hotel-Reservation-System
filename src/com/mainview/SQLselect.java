package com.mainview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 
public class SQLselect {

	
    public void selectAll(){
        String sql = "SELECT Reserved_Room.RoomNo, Customer.CustomerName, Customer.PhoneNumber1, Reservation_Details.ReservedTime, Reserved_Room.CheckInDate, Reserved_Room.CheckOutDate, Reserved_Room.ActualCheckOutDate"
        		+ " FROM Customer"
        		+ " INNER JOIN Reservation_Details"
        		+ " ON Customer.CustomerID = Reservation_Details.CustomerID"
        		+ " INNER JOIN Reserved_Room"
        		+ " ON Reservation_Details.ReservationID = Reserved_Room.ReservationID;";
    	
    //	String sql = "SELECT * FROM Customer";
        
        try (Connection conn = SqliteConnection.Connector();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(//rs.getString("RoomNo") + "\t" + 
                                   rs.getString("CustomerName") + "\t" +
                                   rs.getString("PhoneNumber1") + "\t" +
                                   rs.getString("ReservedTime") + "\t" +
                                   rs.getString("CheckInDate") + "\t" +
                                   rs.getString("CheckOutDate") + "\t" +
                                   rs.getString("ActualCheckOutDate"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SQLselect app = new SQLselect();
        app.selectAll();
    }
 
}

