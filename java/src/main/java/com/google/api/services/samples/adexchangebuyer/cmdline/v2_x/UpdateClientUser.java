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

/**
 * This sample illustrates how to update an existing client user.
 *
 * See the <a href="Client Access Guide">https://developers.google.com/authorized-buyers/apis/guides/v2/client-access#users</a>
 * for more details on the usage of this resource.
 */
public class UpdateClientUser extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Update Client User";
  }

  @Override
  public String getDescription() {
    return "Update an existing Client User.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    long accountId = getIntInput("AccountId", "Enter the Account ID");
    long clientAccountId = getIntInput("ClientAccountId", "Enter the Client Account ID");
    long userId = getIntInput("userId", "Enter the User ID");
    String status = getStringInput("Status", "Enter the Status");

    ClientUser clientUser = new ClientUser();
    clientUser.setStatus(status);
    clientUser = adXClient.accounts().clients().users().update(accountId, clientAccountId, userId,
        clientUser).execute();

    System.out.println("========================================");
    System.out.printf("Updated Client User with Client Account ID \"%s\" and User ID \"%s\"%n",
        clientAccountId, clientUser.getUserId());
    System.out.println("========================================");
    System.out.printf("User ID: %s%n", clientUser.getUserId());
    System.out.printf("\tEmail: %s%n", clientUser.getEmail());
    System.out.printf("\tStatus: %s%n", clientUser.getStatus());
  }
}

