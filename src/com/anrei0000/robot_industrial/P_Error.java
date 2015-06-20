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

public class P_Error {

	private Map<Integer, String> error = null;
	private Integer last_error = null;
	private Integer last_error_bk = null;

	/**
	 * Class constructor
	 */
	public P_Error() {
		Map<Integer, String> errors = new HashMap<Integer, String>();
		errors.put(0, "Error 1");
		errors.put(1, "Error 2");
		errors.put(2, "Error 3");
		errors.put(3, "Error 4");
		errors.put(4, "Error 5");
		errors.put(5, "Error 6");
		this.error = errors;

		this.last_error = 5;
		this.last_error_bk = this.last_error;

	}

	/**
	 * Function adds a new error to the stack
	 * 
	 * @param error
	 */
	public void add_error(String error) {

	}

	/**
	 * Function returns the errors stack
	 * 
	 * @return
	 */
	public Map<Integer, String> get_errors() {
		return this.error;
	}

	/**
	 * Function returns the last error from the stack
	 * 
	 * @return
	 */
	public String get_last_error() {

		String last_error_display = this.error.get(this.last_error);

		// if we showed all errrors
		if (this.last_error < 0) {
			last_error_display = "No more errors - list restarted";
			// restart the counter from the backup to restart the listing
			this.last_error = this.last_error_bk;
		}
		// else decrement the counter that remembers last shown error
		else {
			this.last_error--;
		}

		// return error text
		return last_error_display;

	}

	public void removeLastError() {
		this.error.remove(this.last_error_bk--);
	}
}
