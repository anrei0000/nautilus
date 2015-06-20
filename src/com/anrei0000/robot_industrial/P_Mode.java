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
