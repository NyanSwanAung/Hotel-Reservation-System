package com.mainview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room {
		
		public SimpleIntegerProperty rowNumber = new SimpleIntegerProperty();
		
		public static SimpleIntegerProperty staticRoomNo = new SimpleIntegerProperty();
		public static SimpleStringProperty staticRoomType = new SimpleStringProperty();
		public static SimpleIntegerProperty staticCost = new SimpleIntegerProperty();
		
		public SimpleIntegerProperty roomNo = new SimpleIntegerProperty();
		public SimpleStringProperty roomType = new SimpleStringProperty();
		public SimpleIntegerProperty cost = new SimpleIntegerProperty();
		public SimpleIntegerProperty maximumPpl = new SimpleIntegerProperty();
		public SimpleIntegerProperty maximumEbed = new SimpleIntegerProperty();
		public SimpleStringProperty availability = new SimpleStringProperty();
		public SimpleStringProperty dateIn = new SimpleStringProperty();
	

		public SimpleStringProperty dateOut = new SimpleStringProperty();
		
		public SimpleIntegerProperty extraBed = new SimpleIntegerProperty();
		public SimpleIntegerProperty NoOfPeople= new SimpleIntegerProperty();
		
		public static String temp;
		
		
		
		//public SimpleStringProperty actualCheckOut = new SimpleStringProperty();
		//private boolean checkOutStatus;
		
		public Room() {};
		public Room (int rowNumber, int roomNo, String roomType, int cost, int maximumPpl, int maximumEbed, String availability)
		{
			super();
			this.rowNumber.set(rowNumber);
			this.roomNo.set(roomNo);
			this.roomType.set(roomType);
			this.cost.set(cost);
			this.maximumPpl.set(maximumPpl);
			this.maximumEbed.set(maximumEbed);
			this.availability.set(availability);
			staticRoomNo.set(roomNo);
			staticRoomType.set(roomType);
			staticCost.set(cost);
		}
		

	//******************* Getter & Setter Methods ***************//
		public int getRowNumber() {
			return rowNumber.get();
		}
		public void setRowNumber(int rowNumber) {
			this.rowNumber.set(rowNumber);
		}

		
		public static int getStaticRoomNo() {
			return staticRoomNo.get();
		}
		public void setStaticRoomNo(int roomNo) {
			staticRoomNo.set(roomNo);
			this.roomNo.set(roomNo);
		}

		
		public static String getStaticRoomType() {
			return staticRoomType.get();
		}
		public void setStaticRoomType(String roomType) {
			staticRoomType.set(roomType);
			this.roomType.set(roomType);
		}

		
		public static int getStaticCost() {
			return staticCost.get();
		}
		public void setStaticCost(int cost) {
			staticCost.set(cost);
			this.cost.set(cost);
		}

		
		public int getMaximumPpl() {
			return maximumPpl.get();
		}
		public void setMaximumPpl(int maximumP) {
			this.maximumPpl.set(maximumP);
		}
		
		
		public int getMaximumEbed() {
			return maximumEbed.get();
		}
		public void setMaximumEbed(int maximumE) {
			this.maximumEbed.set(maximumE);
		}
		
		
		
		
		public int getNoOfPerson() {
			return NoOfPeople.get();
		}
		
		public void setNoOfPerson(int noOfPerson) {
		this.NoOfPeople.set(noOfPerson);
		}
		
		public String getAvailability() {
			return availability.get();
		}
		public void setAvailability(String availability) {
			this.availability.set(availability);
		}

		
		public int getExtraBed() {
			return extraBed.get();
		}
		public void setExtraBed(int extraBed) {
		this.extraBed.set(extraBed);
		}


		public int getNoOfPeople() {
			return NoOfPeople.get();
		}
		public void setNoOfPeople(int noOfPeople) {
				this.NoOfPeople.set(noOfPeople);
		}
		
		
		public int getRoomNo() {
			return roomNo.get();
		}


		public void setRoomNo(int roomNo) {
			staticRoomNo.set(roomNo);
			this.roomNo.set(roomNo);
		}


		public int getCost() {
			return cost.get();
		}


		public void setCost(int cost) {
			staticCost.set(cost);
			this.cost.set(cost);
		}


		public void setRoomType(String roomType) {
			staticRoomType.set(roomType);
			this.roomType.set(roomType);
		}
		
		public String getRoomType()
		{
			return roomType.get();
		}
		
	//******************************************************************//	
		
}		
			

	


