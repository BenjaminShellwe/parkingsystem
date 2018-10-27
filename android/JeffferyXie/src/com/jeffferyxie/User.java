package com.jeffferyxie;

public class User {
	private int id;
	private String number;
	private String password;
	private int power;
	public static String NUMBER = "number";
    public static String PASSWORD = "password";
    public static String USERNAME = "username";
    public static String NAME = "name";
    public static String POWER = "power";
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	

}
