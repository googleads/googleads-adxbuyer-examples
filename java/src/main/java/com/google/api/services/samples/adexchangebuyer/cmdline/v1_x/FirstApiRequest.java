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

package com.google.api.services.samples.adexchangebuyer.cmdline.v1_x;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.adexchangebuyer.AdExchangeBuyer;
import com.google.api.services.adexchangebuyer.AdExchangeBuyerScopes;
import com.google.api.services.adexchangebuyer.model.Account;

import java.util.List;

/**
 * A sample application that authenticates and runs a request against the
 * Authorized Buyers Ad Exchange Buyer API.
 */
public class FirstApiRequest {

  /**
   * Be sure to specify the name of your application. If the application name is
   * {@code null} or blank, the application will log a warning. Suggested format
   * is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "APPLICATION_NAME_HERE";

  /**
   * Email account for the Service Account. Get this and the P12 Key File at
   * https://console.developers.google.com
   */
  private static final String SERVICE_ACCOUNT_EMAIL =
      "INSERT_SERVICE_ACCOUNT_EMAIL";

  // Full path to P12 Key file - include file name.
  private static final java.io.File P12_FILE =
      new java.io.File("INSERT_PATH_TO_P12_FILE");

  // Global instance of the HTTP transport.
  private static HttpTransport httpTransport;

  // Global instance of the JSON factory.
  private static final JsonFactory jsonFactory =
      JacksonFactory.getDefaultInstance();


  public static void main(String[] args) throws Exception {
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();

    // Create credentials using the Service email and P12 file.
    Credential oAuth2Credential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(jsonFactory)
        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
        .setServiceAccountScopes(AdExchangeBuyerScopes.all())
        .setServiceAccountPrivateKeyFromP12File(P12_FILE)
        .build();

    // Use the credentials to create a client for the API service.
    AdExchangeBuyer buyerService = new AdExchangeBuyer.Builder(httpTransport,
        jsonFactory, oAuth2Credential).setApplicationName(APPLICATION_NAME)
        .build();

    // Call the Accounts resource on the service to retrieve a list of
    // Accounts for the service account.
    List<Account> allAccounts = buyerService.accounts().list()
        .execute().getItems();

    if (allAccounts != null && allAccounts.size() > 0) {
      System.out.printf("Listing of user associated accounts%n");
      for (Account account : allAccounts) {
        System.out.printf("Account id: %d%n", account.getId());
      }
    } else {
      System.out.printf("No accounts were found associated to this user%n");
    }
  }
}
