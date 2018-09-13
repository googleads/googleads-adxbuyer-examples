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

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer.AdExchangeBuyer;
import com.google.api.services.adexchangebuyer.model.Account;
import com.google.api.services.adexchangebuyer.model.Account.BidderLocation;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all accounts associated to the user.
 *
 * See the <a href="Accounts Guide">https://developers.google.com/authorized-buyers/apis/guides/v1/accounts</a>
 * for more details on the usage of this resource.
 */
public class GetAllAccounts extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYER;
  }

  @Override
  public String getName() {
    return "Get All Accounts";
  }

  @Override
  public String getDescription() {
    return "Lists user associated accounts";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyer adXClient = (AdExchangeBuyer) client;
    List<Account> allAccounts = adXClient.accounts().list().execute().getItems();

    if (allAccounts != null && allAccounts.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of user associated accounts\n");
      System.out.println("========================================");
      for (Account account : allAccounts) {
        System.out.printf("Account id: %d\n", account.getId());
        System.out.printf("- Max. total Qps: %d\n", account.getMaximumTotalQps());
        System.out.printf("- Cookie matching Nid: %s\n", account.getCookieMatchingNid());
        System.out.printf("- Cookie Matching Url: %s\n", account.getCookieMatchingUrl());
        List<BidderLocation> bidderLocations = account.getBidderLocation();
        if (bidderLocations != null && bidderLocations.size() > 0) {
          System.out.printf("- Bidder locations:\n");
          for (BidderLocation bidderLocation : bidderLocations) {
            System.out.printf("  - Bidder location Url: %s\n", bidderLocation.getUrl());
            System.out.printf(
                "  - Bidder location Max. Qps: %d\n", bidderLocation.getMaximumQps());
          }
        } else {
          System.out.printf("- With no configured bidder locations\n");
        }
      }
    } else {
      System.out.printf("No accounts were found associated to this user\n");
    }
  }
}
