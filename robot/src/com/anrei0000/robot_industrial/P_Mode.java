package com.anrei0000.robot_industrial;

import java.util.HashMap;
import java.util.Map;

public class P_Mode {
	private Map<Integer, String> default_modes = null;
	private Integer s_mode_id = null; // selected mode id

	public P_Mode() {
		Map<Integer, String> modes = new HashMap<Integer, String>();
		modes.put(0, "comp");
		modes.put(1, "world");
		modes.put(2, "tool");
		modes.put(3, "joint");
		modes.put(4, "free");
		
		this.default_modes = modes;
	
		// set default mode
		this.setDefault_Mode();
	}
	
	public String getMode()	{ 
		Integer s_mode_id = getS_mode_id();
		Map<Integer, String> modes = getDefault_modes();		
		String selected_mode = (String) modes.get(s_mode_id);
		return selected_mode;
	}

	public Map<Integer, String> getDefault_modes() {
		return default_modes;
	}

	public void setMode(Integer mode_id) {
		this.setS_mode_id(mode_id);
	}

	public Integer getS_mode_id() {
		return s_mode_id;
	}

	public void setS_mode_id(Integer s_mode_id) {
		this.s_mode_id = s_mode_id;
	}

	/**
	 * Set default mode to world
	 */
	public void setDefault_Mode()
	{
		this.setS_mode_id(0);
	}
}
