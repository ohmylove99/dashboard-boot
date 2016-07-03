package org.octopus.dashboard.domain;

import org.octopus.dashboard.domain.entity.User;
import org.octopus.dashboard.shared.security.Digests;
import org.octopus.dashboard.shared.utils.Encodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

	private static Logger logger = LoggerFactory.getLogger(Account.class);
	public static final Long Supper_User_Key = 1L;
	public static final Long Role_Admin_Key = 1L;
	public static final Long Role_User_Key = 2L;
	public static final Long Role_Readonly_Key = 3L;

	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	public void entryptPassword(User user) {
		// do not log
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

}
