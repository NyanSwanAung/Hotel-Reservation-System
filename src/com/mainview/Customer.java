package com.mainview;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
	
	public SimpleIntegerProperty customerNo = new SimpleIntegerProperty();
	public SimpleIntegerProperty roomNo = new SimpleIntegerProperty();
	public SimpleStringProperty reservedTime = new SimpleStringProperty();
	public SimpleStringProperty name = new SimpleStringProperty();
	public SimpleIntegerProperty phoneNumber1 = new SimpleIntegerProperty();
	public LocalDate dateInTemp;
	public LocalDate dateOutTemp;
	public LocalDate actualDateOutTemp;
	public LocalDate actualDateInTemp;
	public SimpleStringProperty dateIn = new SimpleStringProperty();
	public SimpleStringProperty dateOut = new SimpleStringProperty();
	public SimpleStringProperty actualDateOut = new SimpleStringProperty();
	public SimpleStringProperty actualDateIn = new SimpleStringProperty();
	public SimpleStringProperty status = new SimpleStringProperty();
	
	public SimpleStringProperty testing = new SimpleStringProperty();
	public DateTimeFormatter formatterTemp = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public String username;
	

	
	
	public Customer (int customerNo, int roomNo, String reservedTime, String name, int phoneNumber1, String dateIn, String dateOut, String actualDateOut,String actualDateIn, String status)
	{
		super();
		this.customerNo.set(customerNo);
		this.roomNo.set(roomNo);
		this.reservedTime.set(reservedTime);
		this.name.set(name);
		this.phoneNumber1.set(phoneNumber1);
		this.status.set(status);
		
		if (dateIn != null) {
			if(!dateIn.trim().isEmpty())
			{
				this.dateInTemp = LocalDate.parse(dateIn, formatterTemp);
				this.dateIn.set(dateInTemp.format(formatter));
			}
		}
		
		if (dateOut != null) {
			if(!dateOut.trim().isEmpty())
			{
				this.dateOutTemp = LocalDate.parse(dateOut, formatterTemp);
				this.dateOut.set(dateOutTemp.format(formatter));
			}
		}
		
		
		if (actualDateOut != null) {
			if (!actualDateOut.trim().isEmpty())
			{
				this.actualDateOutTemp = LocalDate.parse(actualDateOut, formatterTemp);
				this.actualDateOut.set(actualDateOutTemp.format(formatter));
			}
		}
		
		if (actualDateIn != null) {
			if (!actualDateIn.trim().isEmpty())
			{
				this.actualDateInTemp = LocalDate.parse(actualDateIn, formatterTemp);
				this.actualDateIn.set(actualDateInTemp.format(formatter));
			}
		}
	}
	
	public Customer(String testing) {
		super();
		this.testing.set(testing);
	}



    //*********** Getter & Setter Methods *********/
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

		public int getCustomerNo() {
			return customerNo.get();
		}
	
		public void setCustomerNo(int customerNo) {
			this.customerNo.set(customerNo);
		}
	
		
		public int getRoomNo() {
			return roomNo.get();
		}
		public void setRoomNo(int roomNo) {
			this.roomNo.set(roomNo);
		}
	
		
		public String getReservedTime() {
			return reservedTime.get();
		}
		public void setReservedTime(String reservedTime) {
			this.reservedTime.set(reservedTime);
		}
	
		
		public String getName() {
			return name.get();
		}
		public void setName(String name) {
			this.name.set(name);
		}
	
		
		public int getPhoneNumber1() {
			return phoneNumber1.get();
		}
		public void setPhoneNumber1(int phoneNumber1) {
			this.phoneNumber1.set(phoneNumber1);
		}
		
		public String getDateIn() {
			return dateIn.get();
		}
		public void setDateIn(String dateIn) {
			this.dateIn.set(dateIn);
		}
		
		public String getDateOut() {
			return dateOut.get();
		}
		public void setDateOut(String dateOut) {
			this.dateOut.set(dateOut);
		}
		
		public String getStatus() {
			return status.get();
		}
		public void setStatus(String status) {
			this.status.set(status);
		}
		
		public String getActualDateOut() {
			return actualDateOut.get();
		}
		public void setActualDateOut(String actualDateOut) {
			this.actualDateOut.set(actualDateOut);
		}
		
		public String getActualDateIn() {
			return actualDateIn.get();
		}
		public void setActualDateIn(String actualDateIn) {
			this.actualDateIn.set(actualDateIn);
		}

	}