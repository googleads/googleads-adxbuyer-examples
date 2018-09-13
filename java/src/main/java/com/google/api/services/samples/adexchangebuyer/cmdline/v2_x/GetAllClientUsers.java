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

package com.google.api.services.samples.adexchangebuyer.cmdline.v2_x;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer2.v2beta1.AdExchangeBuyerII;
import com.google.api.services.adexchangebuyer2.v2beta1.model.ClientUser;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all client users associated with the given client buyer.
 *
 * See the <a href="Client Access Guide">https://developers.google.com/authorized-buyers/apis/guides/v2/client-access#users</a>
 * for more details on the usage of this resource.
 */
public class GetAllClientUsers extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Get All Client Users";
  }

  @Override
  public String getDescription() {
    return "Lists Client Users associated with a given Account's Client Buyer.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    long accountId = getIntInput("AccountId", "Enter the Account ID");
    String clientAccountId = getStringInput("ClientAccountId", "Enter the Client Account ID");
    List<ClientUser> allClients = adXClient.accounts().clients().users().list(accountId,
        clientAccountId).execute().getUsers();

    if (allClients != null && allClients.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of Client Users associated with Client Account ID \"%s\"%n",
          clientAccountId);
      System.out.println("========================================");
      for (ClientUser clientUser : allClients) {
        System.out.printf("User ID: %s%n", clientUser.getUserId());
        System.out.printf("\tEmail: %s%n", clientUser.getEmail());
        System.out.printf("\tStatus: %s%n", clientUser.getStatus());
      }
    } else {
      System.out.println("No Client Users were found associated to this Client Buyer.");
    }
  }
}

