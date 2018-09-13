/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.adexchangebuyer.cmdline.v2_x;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer2.v2beta1.AdExchangeBuyerII;
import com.google.api.services.adexchangebuyer2.v2beta1.model.PublisherProfile;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all Marketplace Publisher Profiles.
 */
public class GetAllPublisherProfiles extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Get All Publisher Profiles";
  }

  @Override
  public String getDescription() {
    return "Gets all Marketplace Publisher Profiles.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    String accountId = Long.toString(getIntInput("AccountId", "Enter the Account ID"));

    List<PublisherProfile> publisherProfiles = adXClient.accounts().publisherProfiles()
        .list(accountId).execute().getPublisherProfiles();

    if (publisherProfiles != null && publisherProfiles.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of Publisher Profiles%n");
      System.out.println("========================================");
      for (PublisherProfile publisherProfile : publisherProfiles) {
        System.out.printf("Publisher Profile ID: %s%n", publisherProfile.getPublisherProfileId());
        System.out.printf("\tSeller Account ID: %s%n", publisherProfile.getSeller().getAccountId());
        System.out.printf("\tSeller Sub-Account ID: %s%n",
            publisherProfile.getSeller().getSubAccountId());
      }
    } else {
      System.out.println("No Publisher Profiles were found.");
    }
  }
}
