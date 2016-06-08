package org.octopus.dashboard.shared.utils;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class Numbers {
	public static byte[] longToBytes(long value) {
		return Longs.toByteArray(value);
	}

	public static long bytesToLong(byte[] bytes) {
		return Longs.fromByteArray(bytes);
	}

	public static byte[] intToBytes(int value) {
		return Ints.toByteArray(value);
	}

	public static int bytesToInt(byte[] bytes) {
		return Ints.fromByteArray(bytes);
	}
}
