package org.octopus.dashboard.shared.utils;

import org.octopus.dashboard.shared.security.Digests;

public class PasswordUtils {

	public static final Long Supper_User_Key = 1L;
	public static final Long Role_Admin_Key = 1L;
	public static final Long Role_User_Key = 2L;
	public static final Long Role_Readonly_Key = 3L;

	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	public static String hashPassword(String password) {
		return Encodes.encodeBase64(Digests.sha1(password));
	}

	public static String generateSalt() {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		return Encodes.encodeHex(salt);
	}

	public static String entryptPassword(String salt, String password) {
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt.getBytes(), HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}

	public static String entryptPassword(String password) {
		return entryptPassword(generateSalt(), password);
	}
}
