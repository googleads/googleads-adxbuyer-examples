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
 * This sample illustrates how to update an account.
 *
 * See the <a href="Accounts Guide">https://developers.google.com/authorized-buyers/apis/guides/v1/accounts</a>
 * for more details on the usage of this resource.
 */
public class PatchAccount extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYER;
  }

  @Override
  public String getName() {
    return "Patch account";
  }

  @Override
  public String getDescription() {
    return "Updates the given account's cookie matching URL with patch semantics";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyer adXClient = (AdExchangeBuyer) client;
    int accountId = getIntInput("AccountId", "Enter the account ID");
    String cookieMatchingUrl =
        getStringInput("CookieMatchingUrl", "Enter new cookie matching URL for the account");
    Account account = new Account();
    account.setId(accountId);
    account.setCookieMatchingUrl(cookieMatchingUrl);
    account = adXClient.accounts().patch(accountId, account).execute();

    System.out.printf("Account updated!");
    System.out.printf("Account ID: %d\n", account.getId());
    System.out.printf("- Max. total Qps: %d\n", account.getMaximumTotalQps());
    System.out.printf("- Cookie matching Nid: %s\n", account.getCookieMatchingNid());
    System.out.printf("- Cookie Matching Url: %s\n", account.getCookieMatchingUrl());
    List<BidderLocation> bidderLocations = account.getBidderLocation();
    if (bidderLocations != null && bidderLocations.size() > 0) {
      System.out.printf("- Bidder locations:\n");
      for (BidderLocation bidderLocation : bidderLocations) {
        System.out.printf("  - Bidder location Url: %s\n", bidderLocation.getUrl());
        System.out.printf("  - Bidder location Max. Qps: %d\n", bidderLocation.getMaximumQps());
      }
    } else {
      System.out.printf("- With no configured bidder locations\n");
    }
  }
}
