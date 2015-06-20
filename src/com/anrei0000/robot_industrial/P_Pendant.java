//The MIT License (MIT)
//
//Copyright (c) 2015 anrei0000
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package com.anrei0000.robot_industrial;

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
