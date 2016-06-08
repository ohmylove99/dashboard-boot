package org.octopus.dashboard.test.data;

import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomData {

	private static Random random = new Random();

	
	public static long randomId() {
		return random.nextLong();
	}

	
	public static String randomName(String prefix) {
		return prefix + random.nextInt(10000);
	}


	public static <T> T randomOne(List<T> list) {
		Collections.shuffle(list);
		return list.get(0);
	}

	
	public static <T> List<T> randomSome(List<T> list, int n) {
		Collections.shuffle(list);
		return list.subList(0, n);
	}

	public static <T> List<T> randomSome(List<T> list) {
		int size = random.nextInt(list.size());
		if (size == 0) {
			size = 1;
		}
		return randomSome(list, size);
	}
}
