/*
 * Copyright 2009-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.shell.util;

import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Contains common non-ui related helper methods for rendering text to the console.
 * 
 * @author Gunnar Hillert
 * @author Stephan Oudmaijer
 * 
 * @since 1.0
 */
public final class CommonUtils {

	private static final String EMAIL_VALIDATION_REGEX = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Prevent instantiation.
	 */
	private CommonUtils() {
		throw new AssertionError();
	}

	/**
	 * Right-pad a String with a configurable padding character.
	 * 
	 * @param inputString The String to pad. A {@code null} String will be treated like an empty String.
	 * @param size Pad String by the number of characters.
	 * @param paddingChar The character to pad the String with.
	 * @return The padded String. If the provided String is null, an empty String is returned.
	 */
	public static String padRight(String inputString, int size, char paddingChar) {

		final String stringToPad;

		if (inputString == null) {
			stringToPad = "";
		}
		else {
			stringToPad = inputString;
		}

		StringBuilder padded = new StringBuilder(stringToPad);
		while (padded.length() < size) {
			padded.append(paddingChar);
		}
		return padded.toString();
	}

	/**
	 * Right-pad the provided String with empty spaces.
	 * 
	 * @param string The String to pad
	 * @param size Pad String by the number of characters.
	 * @return The padded String. If the provided String is null, an empty String is returned.
	 */
	public static String padRight(String string, int size) {
		return padRight(string, size, ' ');
	}

	/**
	 * Convert a List of Strings to a comma delimited String.
	 * 
	 * @param list
	 * @return Returns the List as a comma delimited String. Returns an empty String for a Null or empty list.
	 */
	public static String collectionToCommaDelimitedString(Collection<String> list) {
		return StringUtils.collectionToCommaDelimitedString(list);
	}

	/**
	 * @param reader
	 */
	public static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			}
			catch (IOException e) {
				throw new IllegalStateException("Encountered problem closing Reader.", e);
			}
		}
	}

	/**
	 * @param emailAddress The email address to validate
	 * @return {@code true} if the provided email address is valid; {@code false} otherwise
	 */
	public static boolean isValidEmail(String emailAddress) {
		return emailAddress.matches(EMAIL_VALIDATION_REGEX);
	}

	/**
	 * Simple method to replace characters in a String with asterisks to mask the password.
	 * 
	 * @param password The password to mask
	 */
	public static String maskPassword(String password) {
		int lengthOfPassword = password.length();
		StringBuilder stringBuilder = new StringBuilder(lengthOfPassword);

		for (int i = 0; i < lengthOfPassword; i++) {
			stringBuilder.append('*');
		}

		return stringBuilder.toString();
	}

	/**
	 * Return a date/time/UTC formatted String for the provided {@link Date}. Uses {@link SimpleDateFormat} with format
	 * {@code yyyy-MM-dd HH:mm:ss,SSS}
	 * 
	 * @param date Must not be null
	 * @return Formatted date/time
	 */
	public static String getUtcTime(Date date) {
		Assert.notNull(date, "The provided date must not be null.");

		final TimeZone timeZone = TimeZone.getTimeZone("UTC");
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(date);
	}
}
