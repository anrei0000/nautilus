//
//  Copyright (c) 2011, Neil Alexander T.
//  All rights reserved.
// 
//  Redistribution and use in source and binary forms, with
//  or without modification, are permitted provided that the following
//  conditions are met:
// 
//  - Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
//  - Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
// 
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
//  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
//  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
//  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
//  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
//  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
//  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
//  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
//  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
//  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
//  POSSIBILITY OF SUCH DAMAGE.
//

package com.anrei0000.robot_server;

import com.neilalexander.jnacl.crypto.NaCl;

public class NaClTest {
	private static String publickey = "0cba66066896ffb51e92bc3c36ffa627c2493770d9b0b4368a2466c801b0184e";
	private static String privatekey = "176970653848be5242059e2308dfa30245b93a13befd2ebd09f09b971273b728";
	private static byte[] nonce = new byte[24];
	private static byte[] nonce2 = new byte[24];

	public static void main(String[] args) throws Exception {
		NaCl test = new NaCl(privatekey, publickey);
		NaCl test2 = new NaCl(privatekey, publickey);
		
		// string to send
		String message = new String("hi");
		
		// get string bytes
		byte[] in = message.getBytes();
		
		// encrypt bytes
		byte[] foo = test.encrypt(in, nonce);
		
		// make a new string with the bytes
		String a = new String(foo);
		
		// message is received and cast to bytes
		byte[] b = a.getBytes();
		
		// message bytes decripted
		byte[] bar = test2.decrypt(b, nonce2);

		// decripted bytes cast to string
		String decrypted = new String(bar);

		System.out.println("string:" + message);
		System.out.println("in:  " + NaCl.asHex(in));
		System.out.println("encoded: " + NaCl.asHex(foo));
		System.out.println("decoded: " + NaCl.asHex(bar));
		System.out.println("new string: " + decrypted);
	}
}
