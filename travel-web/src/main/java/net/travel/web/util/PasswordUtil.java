package net.travel.web.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordUtil {

	private static final Logger logger = Logger.getLogger(PasswordUtil.class
			.getName());

	public static String getShaSumTry(String convertMe) {
		try {
			return getSHASum(convertMe);
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "cannot percess the password string");
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, "cannot percess the password string");
			throw new RuntimeException(e);
		}
	}

	public static String getSHASum(String convertMe)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return getSHASum(convertMe.getBytes("UTF-8"));
	}

	public static String getSHASum(byte[] convertMe)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		return byteArray2Hex(md.digest(convertMe));
	}

	private static String byteArray2Hex(final byte[] hash) {
		Formatter formatter = new Formatter();
		try {
			for (byte b : hash) {
				formatter.format("%02x", b);
			}
			return formatter.toString();
		} finally {
			formatter.close();
		}
	}
}
