package com.anrei0000.robot_industrial;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.widget.EditText;

public class P_Buttons {

	private String display = null;	

	private String display_soft0 = null;
	private String display_soft1 = null;
	private String display_soft2 = null;
	private String display_soft3 = null;
	private String display_soft4 = null;
	
	private String button_soft0 = null;
	private String button_soft1 = null;
	private String button_soft2 = null;
	private String button_soft3 = null;
	private String button_soft4 = null;


	public P_Buttons() {
		
		this.display = "";

		this.display_soft0 = "";
		this.display_soft1 = "";
		this.display_soft2 = "";
		this.display_soft3 = "";
		this.display_soft4 = "";
		
		this.button_soft0 = "";
		this.button_soft1 = "";
		this.button_soft2 = "";
		this.button_soft3 = "";
		this.button_soft4 = "";
		
	}



	public Map<Integer, String> get_button_names() {
		Map<Integer, String> buttons = new HashMap<Integer, String>();
		buttons.put(0, getButton_soft0());
		buttons.put(1, getButton_soft1());
		buttons.put(2, getButton_soft2());
		buttons.put(3, getButton_soft3());
		buttons.put(4, getButton_soft4());

		return buttons;
	}
	
	public Map<Integer, String> get_display_names() {
		Map<Integer, String> buttons = new HashMap<Integer, String>();
		buttons.put(0, getDisplay_soft0());
		buttons.put(1, getDisplay_soft1());
		buttons.put(2, getDisplay_soft2());
		buttons.put(3, getDisplay_soft3());
		buttons.put(4, getDisplay_soft4());

		return buttons;
	}

	
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	public String getDisplay_soft0() {
		return display_soft0;
	}

	public void setDisplay_soft0(String display_soft0) {
		this.display_soft0 = display_soft0;
	}

	public String getDisplay_soft1() {
		return display_soft1;
	}

	public void setDisplay_soft1(String display_soft1) {
		this.display_soft1 = display_soft1;
	}

	public String getDisplay_soft2() {
		return display_soft2;
	}

	public void setDisplay_soft2(String display_soft2) {
		this.display_soft2 = display_soft2;
	}

	public String getDisplay_soft3() {
		return display_soft3;
	}

	public void setDisplay_soft3(String display_soft3) {
		this.display_soft3 = display_soft3;
	}

	public String getDisplay_soft4() {
		return display_soft4;
	}

	public void setDisplay_soft4(String display_soft4) {
		this.display_soft4 = display_soft4;
	}

	public String getButton_soft0() {
		return button_soft0;
	}

	public void setButton_soft0(String button_soft0) {
		this.button_soft0 = button_soft0;
	}

	public String getButton_soft1() {
		return button_soft1;
	}

	public void setButton_soft1(String button_soft1) {
		this.button_soft1 = button_soft1;
	}

	public String getButton_soft2() {
		return button_soft2;
	}

	public void setButton_soft2(String button_soft2) {
		this.button_soft2 = button_soft2;
	}

	public String getButton_soft3() {
		return button_soft3;
	}

	public void setButton_soft3(String button_soft3) {
		this.button_soft3 = button_soft3;
	}

	public String getButton_soft4() {
		return button_soft4;
	}

	public void setButton_soft4(String button_soft4) {
		this.button_soft4 = button_soft4;
	}



}
