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

/**
 * This sample illustrates how to create a new client buyer for a given account.
 *
 * See the <a href="Client Buyers Guide">https://developers.google.com/authorized-buyers/apis/guides/v2/client-access</a>
 * for more details on the usage of this resource.
 */
public class CreateClientBuyer extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Create Client Buyer";
  }

  @Override
  public String getDescription() {
    return "Creates a new Client Buyer for the given Account ID.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    long accountId = getIntInput("AccountId", "Enter the Account ID");
    String clientName = getStringInput("ClientName", "Enter the Client Name");
    long entityId = getIntInput("EntityId", "Enter the Entity ID");
    String entityName = getStringInput("EntityName", "Enter the Entity Name");
    String entityType = getStringInput("Entity Type", "Enter the Entity Type");
    String role = getStringInput("Role", "Enter the Role");
    String status = getStringInput("Status", "Enter the Status");
    boolean visibleToSeller = getBooleanInput("VisibleToSeller", "Enter VisibleToSeller", false);

    Client clientBuyer = new Client();
    clientBuyer.setClientName(clientName);
    clientBuyer.setEntityId(entityId);
    clientBuyer.setEntityName(entityName);
    clientBuyer.setEntityType(entityType);
    clientBuyer.setRole(role);
    clientBuyer.setStatus(status);
    clientBuyer.setVisibleToSeller(visibleToSeller);

    clientBuyer = adXClient.accounts().clients().create(accountId, clientBuyer).execute();

    System.out.println("========================================");
    System.out.printf("Created Client Buyer for Account ID \"%s\"%n",
        accountId);
    System.out.println("========================================");
    System.out.printf("Client Account ID: %s%n", clientBuyer.getClientAccountId());
    System.out.printf("\tClient Name: %s%n", clientBuyer.getClientName());
    System.out.printf("\tEntity ID: %s%n", clientBuyer.getEntityId());
    System.out.printf("\tEntity Name: %s%n", clientBuyer.getEntityName());
    System.out.printf("\tEntity Type: %s%n", clientBuyer.getEntityType());
    System.out.printf("\tRole: %s%n", clientBuyer.getRole());
    System.out.printf("\tStatus: %s%n", clientBuyer.getStatus());
  }
}
