package com.anrei0000.robot_industrial;

import android.app.Activity;

public class P_Pendant {
	/**
	 * DEVELOPMENT = replicates production but displays some error messages
	 * TESTING = doesn't use the server
	 * PRODUCTION = all functionalities
	 */
	private String ENVIRONMENT = "TESTING";
	
	private Integer speed = null;
	private Integer state = null;
	// set to true to reconnect the pendant to server
	private boolean toBeReconnected = false; 
	
	private P_Error error = null;
	private P_Mode mode = null;
	private P_Buttons buttons = null;
	private P_Chat chat = null;
	
	/**
	 * Pendat constructor
	 * @param conn String[ip, pass]		- pass conection destails here
	 */
	public P_Pendant(String[] conn)
	{
		setSpeed(0);
		setError(new P_Error());
		setMode(new P_Mode());
		setButtons(new P_Buttons());
		setChat(new P_Chat(null, null, conn));
		//initial state is off
		setState(0); 
		setENVIRONMENT("PRODUCTION");
		setENVIRONMENT("DEVELOPMENT");
//		setENVIRONMENT("TESTING");
	}

//	public P_Pendant(String[] connection_details) {
//		setSpeed(0);
//		setError(new P_Error());
//		setMode(new P_Mode());
//		setButtons(new P_Buttons());		
//		setChat(new P_Chat(null, null, connection_details));
//		//initial state is off
//		setState(0); 
//		// don't wait for return messages
//		setENVIRONMENT("DEVELOPMENT");
//	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	
	public P_Error getError() {
		return error;
	}

	public void setError(P_Error error) {
		this.error = error;
	}

	public P_Mode getMode() {
		return mode;
	}

	public void setMode(P_Mode mode) {
		this.mode = mode;
	}

	public P_Buttons getButtons() {
		return buttons;
	}

	public void setButtons(P_Buttons buttons) {
		this.buttons = buttons;
	}

	public P_Chat getChat() {
		return chat;
	}

	public void setChat(P_Chat chat) {
		this.chat = chat;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getENVIRONMENT() {
		return ENVIRONMENT;
	}

	public void setENVIRONMENT(String eNVIRONMENT) {
		ENVIRONMENT = eNVIRONMENT;
	}

	public boolean isToBeReconnected() {
		return toBeReconnected;
	}

	public void setToBeReconnected(boolean toBeReconnected) {
		this.toBeReconnected = toBeReconnected;
	}


}
