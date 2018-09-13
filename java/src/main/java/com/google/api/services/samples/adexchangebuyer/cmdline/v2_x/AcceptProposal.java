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
import com.google.api.services.adexchangebuyer2.v2beta1.model.AcceptProposalRequest;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Proposal;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;

/**
 * This sample illustrates how to accept a Marketplace proposal.
 */
public class AcceptProposal extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Accept Marketplace Proposal.";
  }

  @Override
  public String getDescription() {
    return "Accepts the given Marketplace Proposal.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    String accountId = getStringInput("AccountId", "Enter the Account ID");
    String proposalId = getStringInput("ProposalId", "Enter the Proposal ID");
    long proposalRevision = getIntInput("ProposalRevision",
        "Enter the Proposal's revison number");

    AcceptProposalRequest acceptBody = new AcceptProposalRequest();
    acceptBody.setProposalRevision(proposalRevision);
    Proposal acceptedProposal = adXClient.accounts().proposals().accept(
        accountId, proposalId, acceptBody).execute();

    System.out.println("========================================");
    System.out.printf("Proposal with ID \"%s\" was accepted.%n", proposalId);
    System.out.println("========================================");
    System.out.printf("\tBuyer Account ID: %s%n",
        acceptedProposal.getBuyer().getAccountId());
    System.out.printf("\tSeller Account ID: %s%n",
        acceptedProposal.getSeller().getAccountId());
    System.out.printf("\tSeller Sub-Account ID: %s%n",
        acceptedProposal.getSeller().getSubAccountId());
    System.out.printf("\tProposal State: %s%n",
        acceptedProposal.getProposalState());
    System.out.printf("\tProposal Revision: %s%n",
        acceptedProposal.getProposalRevision());
  }
}
