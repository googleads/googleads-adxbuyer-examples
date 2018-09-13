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
import com.google.api.services.adexchangebuyer2.v2beta1.model.ClientUserInvitation;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all invitations sent out for a given client buyer.
 *
 * See the <a href="Client Access Guide">https://developers.google.com/authorized-buyers/apis/guides/v2/client-access#invitations</a>
 * for more details on the usage of this resource.
 */
public class GetAllInvitations extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Get All Invitations";
  }

  @Override
  public String getDescription() {
    return "Lists all Invitations sent out for a given Account's Client Buyer.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    long accountId = getIntInput("AccountId", "Enter the Account ID");
    String clientAccountId = getStringInput("ClientAccountId", "Enter the Client Account ID");
    List<ClientUserInvitation> allInvites = adXClient.accounts().clients().invitations().list(
        accountId, clientAccountId).execute().getInvitations();

    if (allInvites != null && allInvites.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of Invitations associated with Client Account ID \"%s\"%n",
          clientAccountId);
      System.out.println("========================================");
      for (ClientUserInvitation invite : allInvites) {
        System.out.printf("Invitations ID: %s%n", invite.getInvitationId());
        System.out.printf("\tEmail: %s%n", invite.getEmail());
      }
    } else {
      System.out.println("No Invitations were found associated to this Account.");
    }
  }
}

