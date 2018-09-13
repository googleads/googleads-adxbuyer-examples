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

/**
 * This sample illustrates how to create and send out an invitation for a given client buyer.
 *
 * See the <a href="Client Access Guide">https://developers.google.com/authorized-buyers/apis/guides/v2/client-access#invitations</a>
 * for more details on the usage of this resource.
 */
public class CreateInvitation extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Create Invitation";
  }

  @Override
  public String getDescription() {
    return "Create and send an Invitation for a given Client Buyer.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    long accountId = getIntInput("AccountId", "Enter the Account ID");
    long clientAccountId = getIntInput("ClientAccountId", "Enter the Client Account ID");
    String email = getStringInput("Email", "Enter the email address");

    ClientUserInvitation invite = new ClientUserInvitation();
    invite.setEmail(email);

    invite = adXClient.accounts().clients().invitations().create(accountId, clientAccountId,
        invite).execute();

    System.out.println("========================================");
    System.out.printf("Created new Invitation for Client Account ID \"%s\"%n",
        clientAccountId);
    System.out.println("========================================");
    System.out.printf("Invitations ID: %s%n", invite.getInvitationId());
    System.out.printf("\tEmail: %s%n", invite.getEmail());
  }
}
