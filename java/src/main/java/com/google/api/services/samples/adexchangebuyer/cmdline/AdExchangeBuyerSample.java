/*
 * Copyright (c) 2012 Google Inc.
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

package com.google.api.services.samples.adexchangebuyer.cmdline;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.adexchangebuyer.AdExchangeBuyer;
import com.google.api.services.adexchangebuyer.AdExchangeBuyerScopes;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A sample application that runs multiple requests against the Ad Exchange
 * Buyer API. These include:
 * <ul>
 * <li>Get All Accounts</li>
 * <li>Update Account</li>
 * <li>Get Creative</li>
 * <li>Submit Creative</li>
 * <li>Get All Direct Deals</li>
 * <li>Get All Performance Reports</li>
 * <li>Insert PreTargetConfig</li>
 * </ul>
 */
public class AdExchangeBuyerSample {

  /**
   * Be sure to specify the name of your application. If the application name is
   * {@code null} or blank, the application will log a warning. Suggested format
   * is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "";

  /**
   * Email account for the Service Account.
   * Get this and the P12 Key File at https://console.developers.google.com
   */
  private static final String SERVICE_ACCOUNT_EMAIL =
      "INSERT_SERVICE_ACCOUNT_EMAIL";

  /** Full path to P12 Key file - include file name */
  private static final java.io.File P12_FILE =
      new java.io.File("INSERT_PATH_TO_P12_FILE");

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to
   * make it a single globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;

  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY =
      JacksonFactory.getDefaultInstance();

  private static ArrayList<BaseSample> samples;

  /** Authorizes the installed application to access user's protected data. */
  private static Credential authorize() throws Exception {
    return new GoogleCredential.Builder().setTransport(httpTransport)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
        .setServiceAccountScopes(AdExchangeBuyerScopes.all())
        .setServiceAccountPrivateKeyFromP12File(P12_FILE)
        .build();
  }

  /**
   * Performs all necessary setup steps for running requests against the API.
   *
   * @return An initialized AdExchangeBuyer service object.
   */
  private static AdExchangeBuyer initClient() throws Exception {
    // Authorization.
    Credential credential = authorize();

    // Set up API client.
    AdExchangeBuyer client = new AdExchangeBuyer.Builder(
        httpTransport, JSON_FACTORY, credential)
          .setApplicationName(APPLICATION_NAME).build();

    return client;
  }

  /**
   * Initializes the list of available code samples.
   */
  private static void initSamples() {
    samples = new ArrayList<BaseSample>();
    samples.add(new GetAllAccounts());
    samples.add(new UpdateAccount());
    samples.add(new GetCreative());
    samples.add(new SubmitCreative());
    samples.add(new GetAllDirectDeals());
    samples.add(new GetAllPerformanceReports());
    samples.add(new InsertPretargetingConfig());
  }

  /**
   * Runs all the Ad Exchange Buyer API samples.
   *
   * @param args command-line arguments.
   */
  public static void main(String[] args) throws Exception {
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    initSamples();
    AdExchangeBuyer client = initClient();
    BaseSample sample = null;

    while ((sample = selectSample()) != null) {
      try {
        System.out.printf("%nExecuting sample: %s%n%n", sample.getName());
        sample.execute(client);
        System.out.println();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Prints the list of available code samples and prompts the user to
   * select one.
   *
   * @return The selected sample or null if the user selected to exit.
   */
  private static BaseSample selectSample() throws IOException {
    System.out.printf("Samples:%n");
    int counter = 1;
    for (BaseSample sample : samples) {
      System.out.printf("%d) %s - %s%n", counter++, sample.getName(),
          sample.getDescription());
    }
    System.out.printf("%d) Exit the program%n", counter++);
    Integer sampleNumber = null;
    while (sampleNumber == null) {
      try {
        System.out.println("Select a sample number and press enter:");
        sampleNumber = Integer.parseInt(Utils.readInputLine());
        if (sampleNumber < 1 || sampleNumber > samples.size()) {
          if (sampleNumber == samples.size() + 1) {
            return null;
          }
          System.out.printf("Invalid number provided, try again%n");
          sampleNumber = null;
        }
      } catch (NumberFormatException e) {
        System.out.printf("Invalid number provided, try again%n");
      }
    }
    return samples.get(sampleNumber - 1);
  }
}
