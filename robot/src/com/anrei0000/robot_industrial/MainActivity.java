package com.anrei0000.robot_industrial;

import java.util.Map;

import com.example.robot_industrial.R;
import com.neilalexander.jnacl.crypto.NaClTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements P_AsyncResponse {

	public final static String EXTRA_MESSAGE = "com.example.robot_industrial.MESSAGE";
	public P_Pendant pendant = null;

	final int[] working_button_ids = { R.id.b_mode, R.id.b_disp, R.id.b_edit,
			R.id.b_clr, R.id.b_cmd, R.id.b_slow, R.id.b_soft0, R.id.b_soft1,
			R.id.b_soft2, R.id.b_soft3, R.id.b_soft4, R.id.b_run, R.id.b_step,
			R.id.b_prog, R.id.b_sys, R.id.b_f1_disp, R.id.b_f2_disp,
			R.id.b_f3_disp, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5,
			R.id.b6, R.id.b1_minus, R.id.b1_plus, R.id.b2_minus, R.id.b2_plus,
			R.id.b3_minus, R.id.b3_plus, R.id.b4_minus, R.id.b4_plus,
			R.id.b5_minus, R.id.b5_plus, R.id.b6_minus, R.id.b6_plus,
			R.id.b_comp_pwr, R.id.b_no, R.id.b_yes, R.id.b_robot, R.id.b_t1 };

	final int[] to_enable = { R.id.b_mode, R.id.b_disp, R.id.b_edit,
			R.id.b_clr, R.id.b_cmd, R.id.b_slow, R.id.b_soft0, R.id.b_soft1,
			R.id.b_soft2, R.id.b_soft3, R.id.b_soft4, R.id.b_run, R.id.b_step,
			R.id.b_prog, R.id.b_sys, R.id.b_f1_disp, R.id.b_f2_disp,
			R.id.b_f3_disp, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5,
			R.id.b6, R.id.b1_minus, R.id.b1_plus, R.id.b2_minus, R.id.b2_plus,
			R.id.b3_minus, R.id.b3_plus, R.id.b4_minus, R.id.b4_plus,
			R.id.b5_minus, R.id.b5_plus, R.id.b6_minus, R.id.b6_plus,
			R.id.b_comp_pwr, R.id.b_no, R.id.b_yes, R.id.b_robot, R.id.b_t1,
			R.id.b_rec, R.id.b_on_off, R.id.b_stop, R.id.mode_radio0,
			R.id.mode_radio1, R.id.mode_radio2, R.id.mode_radio3,
			R.id.mode_radio4 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// communication
		// http://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// init our pendant
		P_Pendant pendant = new P_Pendant(null);
		this.pendant = pendant;

		// init display using default values
		init();
	}

	private void init() {
		set_mode_display();
		set_soft_buttons();
		set_speed();
		set_buttons_default_style();
		set_details_bar();
		buttons_set_state(false);
	}

	/**
	 * Set buttons enabled or disabled
	 * 
	 * @param value
	 *            true=enable / false=disable
	 */
	private void buttons_set_state(Boolean value) {
		Button[] buttons_array = new Button[to_enable.length];

		for (int i = 0; i < to_enable.length; i++) {
			final int b_id = i;
			buttons_array[b_id] = (Button) findViewById(to_enable[b_id]);
			buttons_array[b_id].setEnabled(value);
		}

		findViewById(R.id.seekBar1).setEnabled(value);
		;

	}

	private void set_details_bar() {
		TextView b = (TextView) findViewById(R.id.displayEnvironment);

		if (pendant.getENVIRONMENT() == "PRODUCTION") {
			b.setVisibility(View.INVISIBLE);
		} else {
			b.setText(pendant.getENVIRONMENT().toString());
		}

	}

	/**
	 * Add default style to all working buttons
	 */
	private void set_buttons_default_style() {

		Button[] buttons_array = new Button[working_button_ids.length];

		for (int i = 0; i < working_button_ids.length; i++) {
			final int b_id = i;

			buttons_array[b_id] = (Button) findViewById(working_button_ids[b_id]);
			buttons_array[b_id]
					.setBackgroundResource(android.R.drawable.btn_default);

		}

	}

	/**
	 * Init the speed display button Add the listener for the seek bar
	 * 
	 * @param pendant2
	 */
	private void set_speed() {
		Button b_speed = (Button) findViewById(R.id.b_speed);
		b_speed.setText(pendant.getSpeed().toString());
		set_speed_bar_listener();
	}

	private void set_speed_bar_listener() {
		// set the listener for the seek bar
		SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar1);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				send_message("b_speed " + arg0.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				Integer current_speed = arg1;
				pendant.setSpeed(current_speed);

				Button b_speed = (Button) findViewById(R.id.b_speed);
				b_speed.setText(current_speed.toString());
			}
		});
	}

	private void set_soft_buttons() {
		// P_Buttons buttons = pendant.getButtons();
		// soft_buttons = buttons.getSoftButtons();

		P_Buttons soft_buttons = pendant.getButtons();

		Map<Integer, String> default_buttons = soft_buttons.get_display_names();
		Map<Integer, String> default_button_names = soft_buttons
				.get_button_names();

		// set some values for the text editors above the soft buttons
		EditText editSoft0 = (EditText) findViewById(R.id.editSoft0);
		editSoft0.setText(default_buttons.get(0).toString());
		editSoft0.setEnabled(false);
		editSoft0.setAlpha(1);
		EditText editSoft1 = (EditText) findViewById(R.id.editSoft1);
		editSoft1.setText(default_buttons.get(1).toString());
		editSoft1.setEnabled(false);
		EditText editSoft2 = (EditText) findViewById(R.id.editSoft2);
		editSoft2.setText(default_buttons.get(2).toString());
		editSoft2.setEnabled(false);
		EditText editSoft3 = (EditText) findViewById(R.id.editSoft3);
		editSoft3.setText(default_buttons.get(3).toString());
		editSoft3.setEnabled(false);
		EditText editSoft4 = (EditText) findViewById(R.id.editSoft4);
		editSoft4.setText(default_buttons.get(4).toString());
		editSoft4.setEnabled(false);

		// set the names of the soft buttons
		Button soft0 = (Button) findViewById(R.id.b_soft0);
		soft0.setText(default_button_names.get(0).toString());
		Button soft1 = (Button) findViewById(R.id.b_soft1);
		soft1.setText(default_button_names.get(1).toString());
		Button soft2 = (Button) findViewById(R.id.b_soft2);
		soft2.setText(default_button_names.get(2).toString());
		Button soft3 = (Button) findViewById(R.id.b_soft3);
		soft3.setText(default_button_names.get(3).toString());
		Button soft4 = (Button) findViewById(R.id.b_soft4);
		soft4.setText(default_button_names.get(4).toString());

		EditText editSoft5 = (EditText) findViewById(R.id.editSoft5);
		editSoft5.setVisibility(View.INVISIBLE);
		EditText editSoft6 = (EditText) findViewById(R.id.editSoft6);
		editSoft6.setVisibility(View.INVISIBLE);

		EditText display = (EditText) findViewById(R.id.display);
		display.setEnabled(false);
		display.setAlpha(1);

	}

	/**
	 * Set the mode checkbox
	 * 
	 * @param pendant2
	 */
	private void set_mode_display() {
		P_Mode mode = pendant.getMode();
		Integer current_mode_id = mode.getS_mode_id();

		switch (current_mode_id) {
		case 0:
			RadioButton button0 = (RadioButton) findViewById(R.id.mode_radio0);
			button0.setChecked(true);
			// hide the buttons
			show_buttons(0);
			break;
		case 1:
			RadioButton button1 = (RadioButton) findViewById(R.id.mode_radio1);
			button1.setChecked(true);
			break;
		case 2:
			RadioButton button2 = (RadioButton) findViewById(R.id.mode_radio2);
			button2.setChecked(true);
			break;
		case 3:
			RadioButton button3 = (RadioButton) findViewById(R.id.mode_radio3);
			button3.setChecked(true);
			break;
		case 4:
			RadioButton button4 = (RadioButton) findViewById(R.id.mode_radio4);
			button4.setChecked(true);
			break;
		default:
			break;
		}

	}

	/**
	 * Function shows/hides the +/- buttons
	 * 
	 * @param show
	 */
	public void show_buttons(Integer show) {

		Button j1 = (Button) findViewById(R.id.b1);
		Button j2 = (Button) findViewById(R.id.b2);
		Button j3 = (Button) findViewById(R.id.b3);
		Button j4 = (Button) findViewById(R.id.b4);
		Button j5 = (Button) findViewById(R.id.b5);
		Button j6 = (Button) findViewById(R.id.b6);
		Button j1_minus = (Button) findViewById(R.id.b1_minus);
		Button j1_plus = (Button) findViewById(R.id.b1_plus);
		Button j2_minus = (Button) findViewById(R.id.b2_minus);
		Button j2_plus = (Button) findViewById(R.id.b2_plus);
		Button j3_minus = (Button) findViewById(R.id.b3_minus);
		Button j3_plus = (Button) findViewById(R.id.b3_plus);
		Button j4_minus = (Button) findViewById(R.id.b4_minus);
		Button j4_plus = (Button) findViewById(R.id.b4_plus);
		Button j5_minus = (Button) findViewById(R.id.b5_minus);
		Button j5_plus = (Button) findViewById(R.id.b5_plus);
		Button j6_minus = (Button) findViewById(R.id.b6_minus);
		Button j6_plus = (Button) findViewById(R.id.b6_plus);

		// show buttons
		if (show == 1) {
			j1.setVisibility(View.VISIBLE);
			j2.setVisibility(View.VISIBLE);
			j3.setVisibility(View.VISIBLE);
			j4.setVisibility(View.VISIBLE);
			j5.setVisibility(View.VISIBLE);
			j6.setVisibility(View.VISIBLE);
			j1_minus.setVisibility(View.VISIBLE);
			j1_plus.setVisibility(View.VISIBLE);
			j2_minus.setVisibility(View.VISIBLE);
			j2_plus.setVisibility(View.VISIBLE);
			j3_minus.setVisibility(View.VISIBLE);
			j3_plus.setVisibility(View.VISIBLE);
			j4_minus.setVisibility(View.VISIBLE);
			j4_plus.setVisibility(View.VISIBLE);
			j5_minus.setVisibility(View.VISIBLE);
			j5_plus.setVisibility(View.VISIBLE);
			j6_minus.setVisibility(View.VISIBLE);
			j6_plus.setVisibility(View.VISIBLE);
		} else
		// hide buttons
		if (show == 0) {
			j1.setVisibility(View.INVISIBLE);
			j2.setVisibility(View.INVISIBLE);
			j3.setVisibility(View.INVISIBLE);
			j4.setVisibility(View.INVISIBLE);
			j5.setVisibility(View.INVISIBLE);
			j6.setVisibility(View.INVISIBLE);
			j1_minus.setVisibility(View.INVISIBLE);
			j1_plus.setVisibility(View.INVISIBLE);
			j2_minus.setVisibility(View.INVISIBLE);
			j2_plus.setVisibility(View.INVISIBLE);
			j3_minus.setVisibility(View.INVISIBLE);
			j3_plus.setVisibility(View.INVISIBLE);
			j4_minus.setVisibility(View.INVISIBLE);
			j4_plus.setVisibility(View.INVISIBLE);
			j5_minus.setVisibility(View.INVISIBLE);
			j5_plus.setVisibility(View.INVISIBLE);
			j6_minus.setVisibility(View.INVISIBLE);
			j6_plus.setVisibility(View.INVISIBLE);
		}

	}

	public void b_mode(View v) throws Exception {
		P_Mode current_mode = pendant.getMode();
		Integer selected_mode = current_mode.getS_mode_id();

		// the mode to be set
		Integer next_mode = selected_mode + 1;
		if (next_mode == 5)
			next_mode = 0;

		// init buttons
		Button b1 = (Button) findViewById(R.id.b1);
		Button b2 = (Button) findViewById(R.id.b2);
		Button b3 = (Button) findViewById(R.id.b3);
		Button b4 = (Button) findViewById(R.id.b4);
		Button b5 = (Button) findViewById(R.id.b5);
		Button b6 = (Button) findViewById(R.id.b6);
		RadioButton button0 = (RadioButton) findViewById(R.id.mode_radio0);
		RadioButton button1 = (RadioButton) findViewById(R.id.mode_radio1);
		RadioButton button2 = (RadioButton) findViewById(R.id.mode_radio2);
		RadioButton button3 = (RadioButton) findViewById(R.id.mode_radio3);
		RadioButton button4 = (RadioButton) findViewById(R.id.mode_radio4);

		// send_message_once("b_mode");

		switch (next_mode) {
		// comp
		case 0:
			// hide the buttons
			show_buttons(0);
			button0.setChecked(true);
			send_message("b_comp");
			break;

		// world
		case 1:
			// show buttons
			show_buttons(1);
			button1.setChecked(true);
			b1.setText("X");
			b2.setText("Y");
			b3.setText("Z");
			b4.setText("RX");
			b5.setText("RY");
			b6.setText("RZ");

			send_message("b_world");
			break;

		// tool
		case 2:
			// show buttons
			show_buttons(1);
			button2.setChecked(true);
			b1.setText("X");
			b2.setText("Y");
			b3.setText("Z");
			b4.setText("RX");
			b5.setText("RY");
			b6.setText("RZ");
			send_message("b_tool");
			break;

		// joint
		case 3:
			// show buttons
			show_buttons(1);
			button3.setChecked(true);
			b1.setText("J1");
			b2.setText("J2");
			b3.setText("J3");
			b4.setText("J4");
			b5.setText("J5");
			b6.setText("J6");
			send_message("b_joint");
			break;

		// free
		case 4:
			// show buttons
			show_buttons(1);
			button4.setChecked(true);
			b1.setText("J1");
			b2.setText("J2");
			b3.setText("J3");
			b4.setText("J4");
			b5.setText("J5");
			b6.setText("J6");
			send_message("b_free");
			break;

		default:
			break;
		}

		current_mode.setS_mode_id(next_mode);

	}

	public void b_edit(View v) {
		EditText display = (EditText) findViewById(R.id.display);
		display.setText("");

		P_Buttons buttons = pendant.getButtons();

		reset_buttons(buttons);

		buttons.setDisplay_soft0("Real");
		buttons.setDisplay_soft1("Loc");
		buttons.setDisplay_soft2("");
		buttons.setDisplay_soft3("");
		buttons.setDisplay_soft4("");
		display(buttons);

		send_message("b_edit");
	}

	private void clear_display() {
		EditText display = (EditText) findViewById(R.id.display);
		display.setText("");
	}

	private void display(P_Buttons buttons) {
		EditText display = (EditText) findViewById(R.id.display);
		display.setText(buttons.getDisplay());

		EditText editSoft0 = (EditText) findViewById(R.id.editSoft0);
		editSoft0.setText(buttons.getDisplay_soft0());
		EditText editSoft1 = (EditText) findViewById(R.id.editSoft1);
		editSoft1.setText(buttons.getDisplay_soft1());
		EditText editSoft2 = (EditText) findViewById(R.id.editSoft2);
		editSoft2.setText(buttons.getDisplay_soft2());
		EditText editSoft3 = (EditText) findViewById(R.id.editSoft3);
		editSoft3.setText(buttons.getDisplay_soft3());
		EditText editSoft4 = (EditText) findViewById(R.id.editSoft4);
		editSoft4.setText(buttons.getDisplay_soft4());

		Button beditSoft0 = (Button) findViewById(R.id.b_soft0);
		beditSoft0.setText(buttons.getButton_soft0());
		Button beditSoft1 = (Button) findViewById(R.id.b_soft1);
		beditSoft1.setText(buttons.getButton_soft1());
		Button beditSoft2 = (Button) findViewById(R.id.b_soft2);
		beditSoft2.setText(buttons.getButton_soft2());
		Button beditSoft3 = (Button) findViewById(R.id.b_soft3);
		beditSoft3.setText(buttons.getButton_soft3());
		Button beditSoft4 = (Button) findViewById(R.id.b_soft4);
		beditSoft4.setText(buttons.getButton_soft4());

	}

	public void b_soft0(View v) {
		P_Buttons buttons = pendant.getButtons();
		String action = buttons.getDisplay_soft0();

		if (action == "Real") {
			send_message("ereal");
		}

		if (action == "Auto start") {
			send_message("cautostart");
		}

		if (action == "Status") {
			send_message("dstatus");
		}

	}

	public void b_soft1(View v) {
		P_Buttons buttons = pendant.getButtons();
		String action = buttons.getDisplay_soft1();

		if (action == "Loc") {
			send_message("eloc");
		}

		if (action == "Calib") {
			send_message("ccalib");
		}

		if (action == "Software ID") {
			send_message("dswid");
		}

	}

	public void b_soft2(View v) {
		P_Buttons buttons = pendant.getButtons();
		String action = buttons.getDisplay_soft2();

		if (action == "Status & ID") {
			buttons.setDisplay("");
			buttons.setDisplay_soft0("Status");
			buttons.setDisplay_soft1("Software ID");
			buttons.setDisplay_soft2("Cntrlr ID");
			buttons.setDisplay_soft3("Robot IDs");
			buttons.setDisplay_soft4("");
			display(buttons);
		}

		if (action == "Store all") {
			send_message("cstore");

			buttons.setDisplay("Enter last two digits of the file name: STORE auto_");
			display(buttons);

			EditText display = (EditText) findViewById(R.id.display);
			display.setEnabled(true);
		}

		if (action == "Cntrlr ID") {
			send_message("dcntrlrid");
		}
	}

	public void b_soft3(View v) {

		P_Buttons buttons = pendant.getButtons();
		String action = buttons.getDisplay_soft3();

		/**
		 * TODO: To be modified
		 */
		if (action == "Digital I/O") {
			buttons.setDisplay("---- ---- ---- ---- ---- ---- 0000 0011");

			buttons.setDisplay_soft0("0032");
			buttons.setDisplay_soft1("0001");
			buttons.setDisplay_soft2("");
			buttons.setDisplay_soft3("");
			buttons.setDisplay_soft4("");

			buttons.setButton_soft0("+");
			buttons.setButton_soft1("-");
			buttons.setButton_soft2("out");
			buttons.setButton_soft3("in");
			buttons.setButton_soft4("soft");

			display(buttons);
		}

		if (action == "CMD1") {
			send_message("ccmd1");
		}

		if (action == "Robot IDs") {
			send_message("drobotids");
		}

	}

	public void b_soft4(View v) {

		P_Buttons buttons = pendant.getButtons();
		String action = buttons.getDisplay_soft4();

		if (action == "Last error") {
			// DEPRECATED
			/**
			 * P_Error error = pendant.getError(); String display_error =
			 * error.get_last_error(); buttons.setDisplay(display_error);
			 * display(buttons);
			 */
			send_message("dlerr");
		}

		if (action == "CMD2") {
			send_message("ccmd2");
		}

	}

	public void b_disp(View v) {
		P_Buttons buttons = pendant.getButtons();

		buttons.setDisplay_soft0("Joint Values");
		buttons.setDisplay_soft1("World Locatio");
		buttons.setDisplay_soft2("Status & ID");
		buttons.setDisplay_soft3("Digital I/O");
		buttons.setDisplay_soft4("Last error");

		reset_buttons(buttons);

		display(buttons);

		send_message("b_disp");
	}

	/**
	 * Function that sets the soft buttons text as well as the display text to
	 * empty
	 * 
	 * @param buttons
	 */
	public void reset_buttons(P_Buttons buttons) {
		buttons.setDisplay("");

		buttons.setButton_soft0("");
		buttons.setButton_soft1("");
		buttons.setButton_soft2("");
		buttons.setButton_soft3("");
		buttons.setButton_soft4("");

		EditText display = (EditText) findViewById(R.id.display);
		display.setEnabled(false);
	}

	public void b_clr(View v) {
		clear_display();
		/**
		 * DEPRECATED P_Error error = pendant.getError();
		 * error.removeLastError();
		 */
	}

	public void b_cmd(View v) {
		P_Buttons buttons = pendant.getButtons();

		buttons.setDisplay_soft0("Auto start");
		buttons.setDisplay_soft1("Calib");
		buttons.setDisplay_soft2("Store all");
		buttons.setDisplay_soft3("CMD1");
		buttons.setDisplay_soft4("CMD2");

		reset_buttons(buttons);

		display(buttons);

		send_message("b_cmd");
	}

	public void b_comp_pwr(View v) throws Exception {
		P_Mode mode = pendant.getMode();
		mode.setS_mode_id(0); // comp

		RadioButton button0 = (RadioButton) findViewById(R.id.mode_radio0);
		button0.setChecked(true);
		// hide the buttons
		show_buttons(0);
		send_message("b_comp");
	}

	public void b_slow(View v) throws Exception {
		ProgressBar seekbar = (ProgressBar) findViewById(R.id.seekBar1);
		Integer current_max = seekbar.getMax();

		send_message("b_slow");

		// we need to shrink the interval
		if (current_max == 100) {
			Integer current_speed = seekbar.getProgress();
			// set the current speed relative to our new interval
			if (current_speed > 25) {
				seekbar.setProgress(25);
			}

			// set smaller max
			seekbar.setMax(25);

			// make the button pressed
			Button b_slow = (Button) findViewById(R.id.b_slow);
			// Color button_pressed = new Color();
			b_slow.setBackgroundColor(Color.GRAY);
		}

		// we need to widen the interval
		else if (current_max == 25) {
			seekbar.setMax(100);
			// make the button released
			Button b_slow = (Button) findViewById(R.id.b_slow);
			b_slow.setBackgroundResource(android.R.drawable.btn_default);
		}
	}

	/**
	 * Function sends message as well as eventually calling back a reply
	 * 
	 * @todo: send_message
	 * @param message
	 */
	public void send_message(String message) {
		P_Chat chat = null;
		chat = pendant.getChat();

		// check thread concurrency
		if (chat.getStatus() == AsyncTask.Status.RUNNING) {
			// stop the task if we need to reconnect
			if (message.contains("b_connect")) {
				// todo: cancel current running async task somehow
				// ... .cancel doesn't seem to do the trick
				// chat.cancel(true);
			} else {
				// My AsyncTask is currently doing work in doInBackground()
				show_toast("Task `"
						+ pendant.getChat().getMessage()
						+ "` started: Can't start a new task until server replies.");
			}

		} else if (chat.getStatus() == AsyncTask.Status.PENDING) {
			// My AsyncTask has not started yet
			chat.setDelegate(this);
			chat.setMessage(message);
			chat.execute(pendant);
			// chat.setNoAnswer(true);

			if (pendant.getENVIRONMENT() == "DEVELOPMENT") {
				show_toast("Task started: Answer will be displayed after server replies.");
			}

		}
	}

	/**
	 * This function will not wait for a reply from the server
	 * 
	 * @param message
	 *            message to be sent TODO: this function breaks any subsequent
	 *            calls to the server
	 */
	public void send_message_once(String message) {
		P_Chat chat = null;
		chat = pendant.getChat();

		if (chat.getStatus() == AsyncTask.Status.PENDING) {
			// My AsyncTask has not started yet
			chat.setDelegate(this);
			chat.setMessage(message);
			// chat.setNoAnswer(true);
			chat.execute(pendant);
			show_toast("Task started: Server won't reply to this call");
		}
	}

	@Override
	public void processFinish(P_Pendant pendant) {
		if (pendant.getENVIRONMENT() == "DEVELOPMENT") {
			show_toast("Task finished: Message from server is '"
					+ pendant.getChat().getAnswer() + "'");
		}

		if (pendant.getENVIRONMENT() != "TESTING")
			take_action(pendant);
	}

	/**
	 * Do one thing after the server returns
	 * 
	 * @param pendant
	 * @return true on success
	 */
	private boolean take_action(P_Pendant pendant) {
		String init_message = pendant.getChat().getMessage();
		String server_message = pendant.getChat().getAnswer();

		/**
		 * Change soft button labels
		 */
		if (init_message == "ereal" || init_message == "eloc") {
			String delims = "[ ]+";
			String[] tokens = server_message.split(delims);

			if (tokens.length == 5) {
				P_Buttons buttons = pendant.getButtons();
				reset_buttons(buttons);
				buttons.setDisplay_soft0(tokens[0]);
				buttons.setDisplay_soft1(tokens[1]);
				buttons.setDisplay_soft2(tokens[2]);
				buttons.setDisplay_soft3(tokens[3]);
				buttons.setDisplay_soft4(tokens[4]);
				display(buttons);
			} else {
				show_toast("Task finished: Wrong message from server for "
						+ init_message
						+ " should be the names for soft buttons.");
			}
			return true;
		}

		/**
		 * Display the server result
		 */
		if (init_message == "dstatus" || init_message == "dswid"
				|| init_message == "dcntrlrid" || init_message == "drobotids"
				|| init_message == "dlerr") {
			EditText display = (EditText) findViewById(R.id.display);
			display.setText(server_message);
			return true;
		}

		/**
		 * Connection
		 */
		// if we found b_connect and there is also another message from the
		// server
		if (init_message.contains("b_connect")) {
			if (server_message.equals("ack")) {
				buttons_set_state(true);
			} else {
				show_toast("Wrong password");
			}
		}

		return false;
	}

	/**
	 * Test for the aes impmlementation
	 * 
	 * @throws Exception
	 */
	private void test_aes() throws Exception {
		String encryptionKey = "MZygpewJsCpRrfOr";
		String plainText = "Hello world!";

		AdvancedEncryptionStandard advancedEncryptionStandard = new AdvancedEncryptionStandard(
				encryptionKey);
		String cipherText = advancedEncryptionStandard.encrypt(plainText);
		String decryptedCipherText = advancedEncryptionStandard
				.decrypt(cipherText);

		System.out.println(plainText);
		System.out.println(cipherText);
		System.out.println(decryptedCipherText);
	}

	/**
	 * send the full messages from +/- buttons
	 * 
	 * @param axis_direction
	 */
	public void send_movement(String axis_direction) {
		P_Mode mode = pendant.getMode();
		Integer s_mode_id = mode.getS_mode_id();

		switch (s_mode_id) {
		case 1: // world
			send_message("w" + axis_direction); // Ex: wx+
			break;
		case 2: // tool
			send_message("t" + axis_direction); // Ex: ty-
			break;
		case 3: // joint
			send_message("j" + axis_direction); // Ex: jj2+
			break;
		case 4: // free
			send_message("f" + axis_direction); // Ex: fj6-
			break;
		default:
			break;
		}
	}

	/**
	 * Turn the pendant high power on or off Initial state is off
	 * 
	 * @param v
	 */
	public void b_on_off(View v) {
		Integer current_state = pendant.getState();

		// if it's off turn in on
		if (current_state == 0) {
			pendant.setState(1);
			send_message("b_on");

		}
		// if it's on turn it off
		else {
			pendant.setState(0);
			send_message("b_off");
		}
	}

	public void b_rec(View v) {
		EditText display = (EditText) findViewById(R.id.display);
		String text = display.getText().toString();
		String send_text = new String("");

		// get only two numbers after auto_
		if (text != null) {
			String delims = "Enter last two digits of the file name: STORE auto_";
			String[] tokens = text.split(delims);
			String send_2_char = tokens[1].substring(0,
					Math.min(tokens[1].length(), 2));
			send_text = send_2_char;
			display.setText(delims + send_2_char);
		}

		send_message("b_rec_done " + send_text);
	}

	/**
	 * Send login info
	 * 
	 * @param v
	 */
	public void b_connect(View v) {
		// todo: use this to change the app environment
		EditText environment = (EditText) findViewById(R.id.displayEnvironment);
		EditText ip = (EditText) findViewById(R.id.displayIP);
		EditText password = (EditText) findViewById(R.id.displayPassword);

		// hide keyboarard
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(environment.getWindowToken(), 0);

		String user_ip = ip.getText().toString();
		String user_pass = password.getText().toString();

		Boolean entered_ip = true;
		Boolean entered_pass = true;
		// Boolean connected = false;

		if (!user_ip.equals("Enter IP")) {
			entered_ip = true;
		}

		if (!user_pass.equals("Enter Password")) {
			entered_pass = true;
		}

		// @TODO: connect
		if (pendant.getENVIRONMENT() != "TESTING") {
			if (entered_ip && entered_pass) {
				
//				user_ip = "192.168.0.103"; // testing @home
				user_ip = "192.168.0.107"; // testing @office
//				user_ip = "172.20.10.3";// testing @iphone
				
				P_Chat new_chat = pendant.getChat();
				new_chat.setIp(user_ip);
				new_chat.setPassword(user_pass);
				
				pendant.setChat(new_chat);
				
				send_message("b_connect " + user_ip + " " + user_pass);
			} else {
				show_toast("Please input IP and Password.");
			}
		}
		// testing should only send message
		else {
			send_message("b_connect TESTING");
			buttons_set_state(true);
		}

	}

	public void b_stop(View v) throws Exception {
		send_message("b_stop");
		test_salsa();
	}

	/**
	 * This will print in the logcat
	 * 
	 * @throws Exception
	 */
	private void test_salsa() throws Exception {
		NaClTest a = new NaClTest();
		a.main(null);
	}

	private void show_toast(String message) {
		if (pendant.getENVIRONMENT() != "PRODUCTION")
			Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT)
					.show();
	}

	public void b1_minus(View v) {
		Button button = (Button) findViewById(R.id.b1);
		send_movement(button.getText().toString().toLowerCase() + "-");
	}

	public void b1_plus(View v) {
		Button button = (Button) findViewById(R.id.b1);
		send_movement(button.getText().toString().toLowerCase() + "+");
	}

	public void b2_minus(View v) {
		Button button = (Button) findViewById(R.id.b2);
		send_movement(button.getText().toString().toLowerCase() + "-");
	}

	public void b2_plus(View v) {
		Button button = (Button) findViewById(R.id.b2);
		send_movement(button.getText().toString().toLowerCase() + "+");
	}

	public void b3_minus(View v) {
		Button button = (Button) findViewById(R.id.b3);
		send_movement(button.getText().toString().toLowerCase() + "-");
	}

	public void b3_plus(View v) {
		Button button = (Button) findViewById(R.id.b3);
		send_movement(button.getText().toString().toLowerCase() + "+");
	}

	public void b4_minus(View v) {
		Button button = (Button) findViewById(R.id.b4);
		send_movement(button.getText().toString().toLowerCase() + "-");
	}

	public void b4_plus(View v) {
		Button button = (Button) findViewById(R.id.b4);
		send_movement(button.getText().toString().toLowerCase() + "+");
	}

	public void b5_minus(View v) {
		Button button = (Button) findViewById(R.id.b5);
		send_movement(button.getText().toString().toLowerCase() + "-");
	}

	public void b5_plus(View v) {
		Button button = (Button) findViewById(R.id.b5);
		send_movement(button.getText().toString().toLowerCase() + "+");
	}

	public void b6_minus(View v) {
		Button button = (Button) findViewById(R.id.b6);
		send_movement(button.getText().toString().toLowerCase() + "-");
	}

	public void b6_plus(View v) {
		Button button = (Button) findViewById(R.id.b6);
		send_movement(button.getText().toString().toLowerCase() + "+");
	}

	public void b_run(View v) throws Exception {
		send_message("b_run_hold");
	}

	public void b_step(View v) {
		send_message("b_step");
	}

	public void b_prog(View v) {
		send_message("b_prog");
	}

	public void b_sys(View v) {
		send_message("b_sys");
	}

	public void b_f1_disp(View v) {
		send_message("b_f1");
	}

	public void b_f2_disp(View v) {
		send_message("b_f2");
	}

	public void b_f3_disp(View v) {
		send_message("b_f3");
	}

	public void b_t1(View v) {
		send_message("b_t1");
	}

	public void b_robot(View v) {
		send_message("b_robot");
	}

	public void b_no(View v) {
		send_message("b_no");
	}

	public void b_yes(View v) {
		send_message("b_yes");
	}

	public void b_seek_bar(View v) {
	}

	public void b1(View v) {
	}

	public void b2(View v) {
	}

	public void b3(View v) {
	}

	public void b4(View v) {
	}

	public void b5(View v) {
	}

	public void b6(View v) {
	}

	public void b_mode_display(View v) {
	}

	public void b_speed(View v) {
	}

	public void b_led(View v) {
	}

}
