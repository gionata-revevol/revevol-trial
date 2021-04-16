package com.revevol.trial.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

public class TrialStringUtils {

	/**
	 * Remove accent char with simple char
	 */
	public static String removeAccent(String src) {
		return Normalizer
				.normalize(src, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * @return false if input doesn't contain only letters
	 */
	public static boolean hasOnlyLetters(String input) {
		String s = input.trim().replaceAll("\\s", "");;
		if (StringUtils.isBlank(s)) {
			return false;
		}
		int len = s.length();
		for (
				int i = 0;
				i < len; i++) {

			if ((!Character.isLetter(s.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	
}