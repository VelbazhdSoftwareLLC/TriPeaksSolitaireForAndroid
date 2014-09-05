/*
 * This file is a part of Tri Peaks Solitaire for Android
 *
 * Copyright (C) 2013-2014 by Valera Trubachev, Christian d'Heureuse, Todor 
 * Balabanov, Ina Baltadzhieva
 *
 * Tri Peaks Solitaire for Android is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 *
 * Tri Peaks Solitaire for Android is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General 
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * Tri Peaks Solitaire for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.tri.peaks;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * a class used to encrypt and decrypt stuff
 * 
 * @author Valera Trubachev
 */
class Encryptor {
	/**
	 * two cipher objects - one for encrypting and one for decrypting
	 */
	Cipher encipher;

	/**
	 * 
	 */
	Cipher decipher;

	/**
	 * salt for the encryption (more secure)
	 */
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	/**
	 * number of iterations for encryption
	 */
	int iterCt = 19;

	/**
	 * create the encryptor object
	 * 
	 * @param passPhrase
	 */
	public Encryptor(String passPhrase) {
		/*
		 * lots of things can go wrong
		 */
		try {
			/*
			 * create the PBE (Password-based ecryption) key specification
			 */
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
					iterCt);

			/*
			 * generate a secret encryption key (Password-Based Encryption with
			 * Message Digest 5 and Data Encryption Standard)
			 */
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec);

			/*
			 * create the Cipher objects
			 */
			encipher = Cipher.getInstance(key.getAlgorithm());
			decipher = Cipher.getInstance(key.getAlgorithm());

			/*
			 * create the encryption algorithm
			 */
			AlgorithmParameterSpec pSpec = new PBEParameterSpec(salt, iterCt);

			/*
			 * initialize the two Ciphers, with the same keya and algorithm, but
			 * different modes
			 */
			decipher.init(Cipher.DECRYPT_MODE, key, pSpec);
			encipher.init(Cipher.ENCRYPT_MODE, key, pSpec);

			/*
			 * catch all the exceptions that can get thrown.
			 */
		} catch (Exception e) {
		}
	}

	/**
	 * encrypts a string
	 * 
	 * @param in
	 * @return
	 */
	public String encrypt(String in) {
		/*
		 * lots of things that can go wrong
		 */
		try {
			/*
			 * Convert the string to UTF-8 bytecodes
			 */
			byte[] utf8 = in.getBytes("UTF8");

			/*
			 * have the Cipher encrypt the bytes
			 */
			byte[] enBytes = encipher.doFinal(utf8);

			/*
			 * Create a string from the bytes using Base64 encoding
			 */
			String out = new String(Base64Coder.encode(enBytes));

			/*
			 * return the encrypted string
			 */
			return out;

			/*
			 * catch all the exceptions
			 */
		} catch (Exception e) {
		}

		/*
		 * return null if there was an exception
		 */
		return null;
	}

	/**
	 * decrypts a string
	 * 
	 * @param in
	 * @return
	 */
	public String decrypt(String in) {
		/*
		 * lots of things that can go wrong
		 */
		try {
			/*
			 * get the encrypted bytes by decoding the Base64-encoded text
			 */
			byte[] deBytes = Base64Coder.decode(in);

			/*
			 * use the Cipher to decrypt the bytes into UTF-8 bytecodes
			 */
			byte[] utf8 = decipher.doFinal(deBytes);

			/*
			 * create a new string from those bytes
			 */
			String out = new String(utf8, "UTF8");

			/*
			 * return the decrypted string
			 */
			return out;

			/*
			 * catch all the exceptions
			 */
		} catch (Exception e) {
		}

		/*
		 * return null if there was an exception
		 */
		return null;
	}
}
