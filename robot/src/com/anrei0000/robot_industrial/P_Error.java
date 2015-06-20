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
