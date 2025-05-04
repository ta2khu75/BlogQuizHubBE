package com.ta2khu75.quiz.util;

public class Base62 {
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int BASE = ALPHABET.length();

	public static String encodeWithSalt(Long id, SaltedType saltedType) {
		long salted = (id * saltedType.getSalt() + saltedType.getOffset());
		return Base62.encode(salted);
	}

	public static long decodeWithSalt(String str, SaltedType saltedType) {
		long salted = Base62.decode(str);
		return (salted - saltedType.getOffset()) / saltedType.getSalt();
	}

	private static String encode(long num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			sb.append(ALPHABET.charAt((int) (num % BASE)));
			num /= BASE;
		}
		return sb.reverse().toString();
	}

	private static long decode(String str) {
		long num = 0;
		for (char c : str.toCharArray()) {
			num = num * BASE + ALPHABET.indexOf(c);
		}
		return num;
	}
}
