package eu.veldsoft.tri.peaks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

class Encryptor { // a class used to encrypt and decrypt stuff
	Cipher encipher; // two cipher objects - one for encrypting and one for
						// decrypting
	Cipher decipher;
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 }; // salt for
																	// the
																	// encryption
																	// (more
																	// secure)
	int iterCt = 19; // number of iterations for encryption

	public Encryptor(String passPhrase) { // create the encryptor object
		try { // lots of things can go wrong
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
					iterCt); // create the PBE (Password-based ecryption) key
								// specification
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec); // generate a secret encryption
												// key (Password-Based
												// Encryption with Message
												// Digest 5 and Data Encryption
												// Standard)
			encipher = Cipher.getInstance(key.getAlgorithm()); // create the
																// Cipher
																// objects
			decipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec pSpec = new PBEParameterSpec(salt, iterCt); // create
																				// the
																				// encryption
																				// algorithm
			decipher.init(Cipher.DECRYPT_MODE, key, pSpec); // initialize the
															// two Ciphers, with
															// the same keya and
															// algorithm, but
															// different modes
			encipher.init(Cipher.ENCRYPT_MODE, key, pSpec);
		} catch (InvalidAlgorithmParameterException eIAP) {
		} // catch all the exceptions that can get thrown.
		catch (InvalidKeySpecException eIKS) {
		} catch (NoSuchPaddingException eNSP) {
		} catch (NoSuchAlgorithmException eNSA) {
		} catch (InvalidKeyException eIK) {
		}
	}

	public String encrypt(String in) { // encrypts a stirng
		try { // lots of things that can go wrong
			byte[] utf8 = in.getBytes("UTF8"); // Convert the string to UTF-8
												// bytecodes
			byte[] enBytes = encipher.doFinal(utf8); // have the Cipher encrypt
														// the bytes
			String out = new String(Base64Coder.encode(enBytes)); // Create a
																	// string
																	// from the
																	// bytes
																	// using
																	// Base64
																	// encoding
			return out; // return the encrypted string
		} catch (BadPaddingException eBP) {
		} // catch all the exceptions
		catch (IllegalBlockSizeException eIBS) {
		} catch (UnsupportedEncodingException eUE) {
		} catch (IOException eIO) {
		}
		return null; // return null if there was an exception
	}

	public String decrypt(String in) { // decrypts a string
		try { // lots of things that can go wrong
			byte[] deBytes = Base64Coder.decode(in); // get the encrypted bytes
														// by decoding the
														// Base64-encoded text
			byte[] utf8 = decipher.doFinal(deBytes); // use the Cipher to
														// decrypt the bytes
														// into UTF-8 bytecodes
			String out = new String(utf8, "UTF8"); // create a new string from
													// those bytes
			return out; // return the decrypted string

			// catch all the exceptions
		} catch (BadPaddingException eBP) {
		} catch (IllegalBlockSizeException eIBS) {
		} catch (UnsupportedEncodingException eUE) {
		} catch (IOException eIO) {
		}
		return null; // return null if there was an exception
	}
} // end Encryptor class