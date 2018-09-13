/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.adexchangebuyer.creativessubmit;

import com.google.api.services.samples.adexchangebuyer.cmdline.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements input utility methods used across the Authorized Buyers Ad Exchange Buyer
 * API samples.
 */
public class InputUtils {
  // Stores previously entered input values.
  private static Map<String, String> cachedValues = new HashMap<String, String>();

  /**
   * Prompts the user to enter a value.
   *
   * @param propertyKey Key of the value, used to store it for future use
   * @param message Message to print out to the user in request of input
   * @param defaultValue Default value to return in case the user does not provide one
   * @return The captured value entered by the user
   * @throws IOException
   */
  protected String getStringInput(String propertyKey, String message, String defaultValue)
      throws IOException {

    String workingValue = cachedValues.containsKey(propertyKey) ? cachedValues.get(propertyKey)
        : defaultValue;

    String input = InputUtils.getStringInput(message, workingValue);

    if (input != null && !input.isEmpty() && !input.equals(workingValue)) {
      cachedValues.put(propertyKey, input);
      return input;
    }
    return workingValue;
  }


  /**
   * Prompts the user to enter a numeric value.
   *
   * @param propertyKey Key of the value, used to store it for future use
   * @param message Message to print out to the user in request of input
   * @param defaultValue Default value to return in case the user does not provide one
   * @return The parsed numeric value entered by the user
   * @throws IOException
   */
  protected int getIntInput(String propertyKey, String message, Integer defaultValue)
      throws IOException {
    Integer input = null;
    while (input == null) {
      try {
        String sDefaultValue = defaultValue != null ? Integer.toString(defaultValue) : null;
        input = Integer.parseInt(getStringInput(propertyKey, message, sDefaultValue));
      } catch (NumberFormatException e) {
        System.out.printf("Invalid numeric input provided, please try again\n");
      }
    }
    return input;
  }

  /**
   * Prompts the user to enter a long value.
   *
   * @param propertyKey Key of the value, used to store it for future use
   * @param message Message to print out to the user in request of input
   * @param defaultValue Default value to return in case the user does not provide one
   * @return The parsed numeric value entered by the user
   * @throws IOException
   */
  protected long getLongInput(String propertyKey, String message, Long defaultValue)
      throws IOException {
    Long input = null;
    while (input == null) {
      try {
        String sDefaultValue = defaultValue != null ? Long.toString(defaultValue) : null;
        input = Long.parseLong(getStringInput(propertyKey, message, sDefaultValue));
      } catch (NumberFormatException e) {
        System.out.printf("Invalid numeric input provided, please try again\n");
      }
    }
    return input;
  }

  /**
   * Prompts the user to enter a value.
   *
   * @param message Message to print out to the user in request of input   *
   * @return The captured value entered by the user
   * @throws IOException
   */
  protected static String getStringInput(String message) throws IOException {
    return getStringInput(message, null);
  }

  /**
   * Prompts the user to enter a value.
   *
   * @param message Message to print out to the user in request of input
   * @param defaultValue Default value to return in case the user does not provide one
   * @return The captured value entered by the user
   * @throws IOException
   */
  protected static String getStringInput(String message, String defaultValue)
      throws IOException {
    if (defaultValue != null) {
      System.out.printf("%s (press enter to use value %s):\n", message, defaultValue);
    } else {
      System.out.printf("%s:\n", message);
    }
    String input = Utils.readInputLine();
    if (input != null && !input.isEmpty()) {
      return input;
    }
    if (defaultValue != null) {
      return defaultValue;
    }
    return null;
  }

  /**
   * Prompts the user to enter a numeric value.
   *
   * @param message Message to print out to the user in request of input   *
   * @return The parsed numeric value entered by the user
   * @throws IOException
   */
  protected static int getIntInput(String message) throws IOException {
    return getIntInput(message, null);
  }

  /**
   * Prompts the user to enter a numeric value.
   *
   * @param message Message to print out to the user in request of input
   * @param defaultValue Default value to return in case the user does not provide one
   * @return The parsed numeric value entered by the user
   * @throws IOException
   */
  protected static int getIntInput(String message, Integer defaultValue)
      throws IOException {
    Integer input = null;
    while (input == null) {
      try {
        String sDefaultValue = defaultValue != null ? Integer.toString(defaultValue) : null;
        input = Integer.parseInt(getStringInput(message, sDefaultValue));
      } catch (NumberFormatException e) {
        System.out.printf("Invalid numeric input provided, please try again\n");
      }
    }
    return input;
  }

  /**
   * Reads an input line from standard input.
   *
   * @return The line read
   * @throws IOException If an I/O error has occurred
   */
  protected static String readInputLine() throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    return in.readLine();
  }
}
