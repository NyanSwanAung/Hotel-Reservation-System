package com.mainview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room_Reservation {
		
	
	
		public SimpleIntegerProperty RoomNo = new SimpleIntegerProperty();
		public SimpleStringProperty RoomType = new SimpleStringProperty();
		public SimpleIntegerProperty Days = new SimpleIntegerProperty();
		public SimpleStringProperty Cost = new SimpleStringProperty(); //Room Cost
		public SimpleStringProperty DateIn = new SimpleStringProperty();
		public SimpleStringProperty DateOut = new SimpleStringProperty();
		public SimpleIntegerProperty ExtraBed = new SimpleIntegerProperty();// number of extraBed
		public SimpleStringProperty BedValue = new SimpleStringProperty(); //cost for extra bed
		public SimpleIntegerProperty NoOfPeople= new SimpleIntegerProperty();
		public SimpleIntegerProperty TotalCharges = new SimpleIntegerProperty();
		
		
		//********************* Constructor *************************//
		public Room_Reservation(int roomNo, String roomType,int day, String cost, int extraBed ,Integer nOfPeople, String bedValue, 
				String dateIn, String dateOut,  int totalCharges) {
			super();
			RoomNo.set(roomNo);
			RoomType.set(roomType);
			Days.set(day);
			Cost.set(cost);
			ExtraBed.set(extraBed);
			DateIn.set(dateIn);
			NoOfPeople.set(nOfPeople);
			DateOut.set(dateOut);
			BedValue.set(bedValue);
			TotalCharges.set(totalCharges);
		}
		public Room_Reservation() {};
		public Room_Reservation(int roomNo2, String roomType2, int cost2) {
			// TODO Auto-generated constructor stub
		}
		//**************************************************************//

		

		//******************* Getter & Setter Methods ***************//
		public int getRoomNo() {
			return RoomNo.get();
		}
		public void setRoomNo(int RoomNo) {
			this.RoomNo.set(RoomNo);
		}

		
		public String getRoomType() {
			return RoomType.get();
		}
		public void setRoomTempType(String RoomType) {
			this.RoomType.set(RoomType);
		}
		
		public void setRoomType(String roomType) {
			this.RoomType.set(roomType);
		}
		
		public int getDays() {
			return Days.get();
		}
		public void setDays(int day) {
		this.Days.set(day);
	}
		
		

		public String getCost() {
			return Cost.get();
		}
		public void setCost(String cost) {
		this.Cost.set(cost);
	}
		

		public String getDateIn() {
			return DateIn.get();
		}
		public void setDateIn(String dateIn) {
			this.DateIn.set(dateIn);
		}
	
		
		public String getDateOut() {
			return DateOut.get();
		}
		public void setDateOut(String dateOut) {
		this.DateOut.set(dateOut); }
		
		
		public int getExtraBed() {
			return ExtraBed.get();
		}
		public void setExtraBed(int extraBed) {
			this.ExtraBed.set(extraBed);
		}

		
		public String getBedValue() {
			return BedValue.get();
		}
		public void setBedValue(String bedValue) {
		this.BedValue.set(bedValue); }
		
		
		public int getNoOfPeople() {
			return NoOfPeople.get();
		}
		public void setNoOfPeople(int noOfPeople) {
					this.NoOfPeople.set(noOfPeople);
				}
				
		
		public int getTotalCharges() {
			return TotalCharges.get();
		}
		public void setTotalCharges(int totalCharges) {
			this.TotalCharges.set(totalCharges);
		}
		//******************************************************************//	
		
	}


