package com.mainview;

import java.time.LocalDate;

public class Reservation {
	
	private String cName;
	private String cPhNo1;
	private String cPhNo2;
	private String cNRC;
	private String cEmail;
	public static int CIndex = 8;
	
	private static Integer personPerRoom;
	private int roomNo;
	private static Integer extraBed;
	private LocalDate reservedTime;
	private String dateIn;
	private  String dateOut;
	
	
	public Reservation() {}

	public Reservation(String cName, String cPhNo1, String cPhNo2, String cNRC, String cEmail) {
		super();
		this.cName = cName;
		this.cPhNo1 = cPhNo1;
		this.cPhNo2 = cPhNo2;
		this.cNRC = cNRC;
		this.cEmail = cEmail;
	}
	public Reservation(Integer personPerRoom, Integer extraBed) {
		super();
		this.personPerRoom = personPerRoom;
		this.extraBed = extraBed;
	}
	
	public static Reservation customerList = new Reservation();


	public String getcName() {
		return cName;
	}
	
	public void setcName(String cName) {
		this.cName = cName;
	}
	
	public String getcPhNo1() {
		return cPhNo1;
	}
	
	public void setcPhNo1(String cPhNo1) {
		this.cPhNo1 = cPhNo1;
	}
	
	public String getNRC() {
		return cNRC;
	}
	
	public void setcNRC(String cNRC) {
		this.cNRC = cNRC;
	}

	public String getcEmail() {
		return cEmail;
	}

	public void setcEmail(String cEmail) {
		this.cEmail = cEmail;
	}

	public String getcPhNo2() {
		return cPhNo2;
	}

	public void setcPhNo2(String cPhNo2) {
		this.cPhNo2 = cPhNo2;
	}

	public LocalDate getReservedTime() {
		return reservedTime;
	}

	public void setReservedTime(LocalDate reservedTime) {
		this.reservedTime = reservedTime;
	}

	public  String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String DateIn) {
		dateIn = DateIn;
	}

	public String getDateOut() {
		return dateOut;
	}

	public void setDateOut(String DateOutTBox) {
		dateOut = DateOutTBox;
	}

	public static Integer getExtraBed() {
		return extraBed;
	}

	public static void setExtraBed(Integer ExtraBed) {
		extraBed = ExtraBed;
	}

	public static Integer getPersonPerRoom() {
		return personPerRoom;
	}

	public static void setPersonPerRoom(Integer PersonByRoom) {
		personPerRoom = PersonByRoom;
	}

	public String getcNRC() {
		return cNRC;
	}
	
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
}	
	
		

