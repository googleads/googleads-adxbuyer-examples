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

package com.google.api.services.samples.adexchangebuyer.cmdline;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.adexchangebuyer2.v2beta1.AdExchangeBuyerIIScopes;
import com.google.api.services.adexchangebuyer2.v2beta1.AdExchangeBuyerII;

import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.AcceptProposal;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.CreateAccountLevelFilterSet;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.CreateBidderLevelFilterSet;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.CreateClientBuyer;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.CreateInvitation;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.CreateProposal;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAccountLevelBidMetrics;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAllAccountLevelFilterSets;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAllBidderLevelFilterSets;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAllClientBuyers;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAllClientUsers;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAllInvitations;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetAllPublisherProfiles;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.GetBidderLevelBidMetrics;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.ListProposals;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.UpdateClientBuyer;
import com.google.api.services.samples.adexchangebuyer.cmdline.v2_x.UpdateClientUser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A sample application that runs multiple requests against the Authorized Buyers Ad Exchange
 * Buyer II API. These include:
 * <ul>
 * <li>Get All Client Buyers</li>
 * <li>Create Client Buyer</li>
 * <li>Update Client Buyer</li>
 * <li>Get All Invitations</li>
 * <li>Create Invitation</li>
 * <li>Get All Client Users</li>
 * <li>Update Client User</li>
 * <li>Create Bidder-level Filter Set</li>
 * <li>Get all Bidder-level Filter Sets</li>
 * <li>Get Bidder-level Bid Metrics</li>
 * <li>Create Account-level Filter Set</li>
 * <li>Get all Account-level Filter Sets</li>
 * <li>Get Account-level Bid Metrics</li>
 * </ul>
 */
public class AdExchangeBuyerIISample {
  /**
   * Be sure to specify the name of your application. If the application name is
   * {@code null} or blank, the application will log a warning. Suggested format
   * is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "";

  /** Full path to JSON Key file - include file name */
  private static final java.io.File JSON_FILE =
      new java.io.File("INSERT_PATH_TO_JSON_FILE");


  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY =
      JacksonFactory.getDefaultInstance();

  private static ArrayList<BaseSample> samples;

  /** Authorizes the installed application to access user's protected data.
   * @throws IOException
   * */
  private static Credential authorize() throws Exception {
    return GoogleCredential.fromStream(new FileInputStream(JSON_FILE))
        .createScoped(AdExchangeBuyerIIScopes.all());
  }

  /**
   * Performs all necessary setup steps for running requests against the
   * Ad Exchange Buyer II API.
   *
   * @return An initialized AdExchangeBuyerII service object.
   */
  private static AdExchangeBuyerII initAdExchangeBuyerIIClient(Credential credential) {
    AdExchangeBuyerII client = new AdExchangeBuyerII.Builder(
        httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    return client;
  }

  /**
   * Initializes the list of available code samples.
   */
  private static void initSamples() {
    samples = new ArrayList<BaseSample>();
    samples.add(new GetAllClientBuyers());
    samples.add(new CreateClientBuyer());
    samples.add(new UpdateClientBuyer());
    samples.add(new GetAllInvitations());
    samples.add(new CreateInvitation());
    samples.add(new GetAllClientUsers());
    samples.add(new UpdateClientUser());
    samples.add(new CreateBidderLevelFilterSet());
    samples.add(new GetAllBidderLevelFilterSets());
    samples.add((new GetBidderLevelBidMetrics()));
    samples.add(new CreateAccountLevelFilterSet());
    samples.add(new GetAllAccountLevelFilterSets());
    samples.add(new GetAccountLevelBidMetrics());
    samples.add(new GetAllPublisherProfiles());
    samples.add(new ListProposals());
    samples.add(new CreateProposal());
    samples.add(new AcceptProposal());
  }

  /**
   * Runs all the Ad Exchange Buyer API samples.
   *
   * @param args command-line arguments.
   */
  public static void main(String[] args) throws Exception {
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    initSamples();
    Credential credentials = authorize();
    AdExchangeBuyerII adXBuyerIIClient = initAdExchangeBuyerIIClient(
        credentials);
    BaseSample sample = null;

    while ((sample = selectSample()) != null) {
      try {
        System.out.printf("%nExecuting sample: %s%n%n", sample.getName());
        BaseSample.ClientType clientType = sample.getClientType();
        if (clientType == BaseSample.ClientType.ADEXCHANGEBUYERII) {
          sample.execute(adXBuyerIIClient);
        }
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
