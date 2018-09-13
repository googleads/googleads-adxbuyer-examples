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
import com.google.api.services.adexchangebuyer2.v2beta1.model.Client;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all client buyers associated with a given account.
 *
 * See the <a href="Client Access Guide">https://developers.google.com/authorized-buyers/apis/guides/v2/client-access#clients</a>
 * for more details on the usage of this resource.
 */
public class GetAllClientBuyers extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Get All Client Buyers";
  }

  @Override
  public String getDescription() {
    return "Lists Client Buyers associated with the given Account.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    long accountId = getIntInput("AccountId", "Enter the Account ID");
    List<Client> allClients = adXClient.accounts().clients().list(accountId).execute().getClients();

    if (allClients != null && allClients.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of Client Buyers associated with AdX Account \"%s\"%n",
          accountId);
      System.out.println("========================================");
      for (Client clientBuyer : allClients) {
        System.out.printf("Client Account ID: %s%n", clientBuyer.getClientAccountId());
        System.out.printf("\tClient Name: %s%n", clientBuyer.getClientName());
        System.out.printf("\tEntity ID: %s%n", clientBuyer.getEntityId());
        System.out.printf("\tEntity Name: %s%n", clientBuyer.getEntityName());
        System.out.printf("\tEntity Type: %s%n", clientBuyer.getEntityType());
        System.out.printf("\tRole: %s%n", clientBuyer.getRole());
        System.out.printf("\tStatus: %s%n", clientBuyer.getStatus());
      }
    } else {
      System.out.println("No Client Buyers were found associated to this Account.");
    }
  }
}
