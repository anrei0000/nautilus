package com.anrei0000.robot_industrial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.neilalexander.jnacl.crypto.NaCl;

import android.os.AsyncTask;

class P_Chat extends AsyncTask<P_Pendant, Void, P_Pendant> {
	private static String publickey = "0cba66066896ffb51e92bc3c36ffa627c2493770d9b0b4368a2466c801b0184e";
	private static String privatekey = "176970653848be5242059e2308dfa30245b93a13befd2ebd09f09b971273b728";
	private static byte[] nonce = new byte[24];
	private static byte[] nonce2 = new byte[24];

	private final static String DEVELOPMENT = "10.0.2.2";
	private final static String DISTER = "192.168.0.103";
	private static final String String = null;

	private P_AsyncResponse delegate = null;
	private String ip = null;
	private Integer port = 6789;
	private String password = null;

	private Socket my_socket = null;
	private PrintWriter printer = null;
	private BufferedReader reader = null;

	private String message = null;
	private String answer = null;

	// setting this true will skip waiting for server return message
	private boolean noAnswer = false;

	/**
	 * Connection constructor
	 * 
	 * @param socket
	 *            - this is passed to reconnect after callback
	 * @param async
	 *            - callback
	 * @param conn
	 *            - String[ip, pass]
	 */
	public P_Chat(Socket socket, P_AsyncResponse async, String[] conn) {

		// this should be passed first
		if (conn != null) {
			if (conn[0] != null) {
				setIp(conn[0]);
				// setPassword(connection_details[1]);
			}

		}

		// this is called after the server responds the first time
		if (socket != null)
			setMy_socket(socket);

		this.delegate = async;
	}

	@Override
	public P_Pendant doInBackground(P_Pendant... arg0) {
		P_Pendant current_pendant = (P_Pendant) arg0[0];
		message = current_pendant.getChat().getMessage();

		// for first time connecting
		if (getMy_socket() == null) {
			open_socket();
			open_printer();
			open_reader();
		}

		// send message
		try {
			String encrypted_message = encrypt(message);
			
			send_message(current_pendant, encrypted_message);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get message unless noAnswer is set
		// if (!isNoAnswer()) {
		if (current_pendant.getENVIRONMENT() == "DEVELOPMENT"
				|| current_pendant.getENVIRONMENT() == "PRODUCTION") {
			try {
				current_pendant.getChat().setAnswer(
						get_server_message(current_pendant));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return current_pendant;
	}

	private java.lang.String encrypt(java.lang.String message) throws Exception {
		// get string bytes
		byte[] in = message.getBytes();
		
		NaCl test = new NaCl(privatekey, publickey);
		
		// encrypt bytes
		byte[] foo1 = test.encrypt(in, nonce);
		
		// make a new string with the bytes
		String a = Arrays.toString(foo1);
		
		return a;
	}

	// public static void crypto(String message) throws Exception
	// {
	//
	// NaCl test = new NaCl(privatekey, publickey);
	// byte[] in = message.getBytes();
	//
	// byte[] foo = test.encrypt(in, nonce);
	// byte[] bar = test.decrypt(foo, nonce);
	//
	// System.out.println("in:  " + NaCl.asHex(in));
	// System.out.println("encoded: " + NaCl.asHex(foo));
	// System.out.println("decoded: " + NaCl.asHex(bar));
	// }

	protected void onProgressUpdate(Integer... progress) {
		// setProgressPercent(progress[0]);
	}

	protected void onPostExecute(P_Pendant pendant) {
		P_Chat oldChat = pendant.getChat();

		// we need to reinit the chat to run it multiple times in the main
		// thread
		pendant.setChat(new P_Chat(oldChat.getMy_socket(), delegate, null));

		// however, the reader must be maintained so we pass it here along with
		// other stuff
		pendant.getChat().setReader(oldChat.getReader());
		pendant.getChat().setPrinter(oldChat.getPrinter());
		pendant.getChat().setAnswer(oldChat.getAnswer());
		pendant.getChat().setMessage(oldChat.getMessage());

		// callback
		delegate.processFinish(pendant);
	}

	protected void onPreExecute(Long result) {
		// showDialog("Downloaded " + result + " bytes");
	}

	/**
	 * Send message to server
	 * 
	 * @param message2
	 * @return
	 * @throws IOException
	 */
	public boolean send_message(P_Pendant current_pendant, String message2)
			throws IOException {
		Socket socket = current_pendant.getChat().getMy_socket();

		if (socket != null) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(message2);
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param current_pendant
	 * @param foo
	 * @return
	 * @throws IOException
	 */
	private boolean send_message_encrypted(P_Pendant current_pendant, byte[] foo)
			throws IOException {
		// TODO Auto-generated method stub

		Socket socket = current_pendant.getChat().getMy_socket();

		if (socket != null) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.print(foo);
			return true;
		}

		return false;
	}

	/**
	 * Get message from server
	 * 
	 * @param current_pendant
	 * @return
	 * @throws Exception 
	 */
	public String get_server_message(P_Pendant current_pendant)
			throws Exception {
		String message = null;
		Socket socket = current_pendant.getChat().getMy_socket();

		// if a socket is set different from null
		if (socket != null) {
			// this is empty on second read
			BufferedReader in = current_pendant.getChat().getReader();
			message = in.readLine();
			
			String decrypted_message = decrypt(message);
			
			return decrypted_message;
		}

		// couldn't get message because the
		// socket isn't
		// connected
		return "Socket not connected";

	}

	private java.lang.String decrypt(java.lang.String encrypted_string) throws Exception {

			NaCl salt = new NaCl(privatekey, publickey);
			String[] encrypted_bytes_string = encrypted_string.substring(1,
					encrypted_string.length() - 1).split(",");
			byte[] encrypted_bytes = new byte[encrypted_bytes_string.length];

			for (int i = 0, len = encrypted_bytes.length; i < len; i++) {
				encrypted_bytes[i] = Byte.parseByte(encrypted_bytes_string[i]
						.trim());
			}

			byte[] decrypted_bytes = salt.decrypt(encrypted_bytes, nonce2);

			String decrypted_string = new String(decrypted_bytes);

			return decrypted_string;

	}

	/**
	 * Create a new socket for the chat
	 */
	private void open_socket() {
		try {
			setMy_socket(new Socket(ip, port));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new socket for the chat
	 */
	public void reopen_socket() {
		try {
			setMy_socket(new Socket(ip, port));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recreate a connection for the current pendant
	 * 
	 * @param current_pendant
	 */
	@SuppressWarnings("unused")
	private void set_new_connection(P_Pendant current_pendant) {
		try {
			current_pendant.setChat(new P_Chat(new Socket(ip, port), delegate,
					null));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		open_printer();
		open_reader();
	}

	private void open_printer() {
		if (getMy_socket() != null) {
			try {
				setPrinter(new PrintWriter(getMy_socket().getOutputStream(),
						true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void open_reader() {
		if (getMy_socket() != null) {
			try {
				setReader(new BufferedReader(new InputStreamReader(
						getMy_socket().getInputStream())));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Socket getMy_socket() {
		return my_socket;
	}

	public void setMy_socket(Socket my_socket) {
		this.my_socket = my_socket;
	}

	public PrintWriter getPrinter() {
		return printer;
	}

	public void setPrinter(PrintWriter printer) {
		this.printer = printer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setNoAnswer() {
		this.noAnswer = true;
	}

	public boolean isNoAnswer() {
		return noAnswer;
	}

	public void setNoAnswer(boolean noAnswer) {
		this.noAnswer = noAnswer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public P_AsyncResponse getDelegate() {
		return delegate;
	}

	public void setDelegate(P_AsyncResponse delegate) {
		this.delegate = delegate;
	}
}